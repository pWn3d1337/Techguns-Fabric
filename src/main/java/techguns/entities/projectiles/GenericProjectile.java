package techguns.entities.projectiles;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.block.Blocks;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.TGPacketsS2C;
import techguns.TGSounds;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketGunImpactFX;
import techguns.packets.PacketSpawnEntity;
import techguns.packets.PacketSpawnParticle;

public class GenericProjectile extends ProjectileEntity {

	@FunctionalInterface
	protected interface ImpactFXCode {
		void handleImpactFX(World world, double x, double y, double z, float pitch, float yaw, BlockSoundGroup soundGroup);
	}

	@FunctionalInterface
	protected interface DamageSourceGetter {
		TGDamageSource getDamageSource(Entity projectile, Entity shooter, DeathType deathType);
	}

	public enum GenericProjectileType {
		DEFAULT(0, DamageType.PROJECTILE, DeathType.GORE, TGDamageSource::causeBulletDamage, GenericProjectile::handleBulletImpactDefault), //GenericProjectile::handleBulletImpactDefault);
		INCENDIARY(1, DamageType.FIRE, DeathType.DEFAULT, TGDamageSource::causeFireDamage, (entity, hitResult)->{
			if(!entity.isFireImmune()) {
				entity.setOnFireFor(3);
			}
		}, GenericProjectile::handleBulletImpactIncendiary, (projectile)->{}),
		EXPLOSIVE(2, DamageType.EXPLOSION, DeathType.GORE, TGDamageSource::causeExplosionDamage, GenericProjectile::handleBulletImpactDefault),
		ADVANCED(3, DamageType.PROJECTILE, DeathType.GORE, TGDamageSource::causeBulletDamage, GenericProjectile::handleBulletImpactBlue),
		BLASTER(4, DamageType.ENERGY, DeathType.LASER, TGDamageSource::causeEnergyDamage, (world,x,y,z,pitch,yaw,sound)->{
			if (!world.isClient) {
				TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("LaserGunImpact", x, y, z), world, new Vec3d(x,y,z), 32.0f);
			}
		}),
		GAUSS(5, DamageType.PROJECTILE, DeathType.GORE, TGDamageSource::causeBulletDamage, (entity, hitResult)->{}, (world,x,y,z,pitch,yaw,sound)->{
			if (!world.isClient) {
				handleBulletImpactBlue(world,x,y,z,pitch,yaw,sound);
				TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("GaussRifleImpact_Block", x, y, z, 0,0,0, pitch, yaw, 1.0f), world, new Vec3d(x,y,z), 64.0f);
			}
		}, (projectile)->{
			ClientProxy.get().createFXOnEntity("GaussProjectileTrail", projectile);
		});

		public final byte id;
		protected DamageType damageType;
		protected DeathType deathType;
		protected DamageSourceGetter dmgSourceGetter;
		protected BiConsumer<LivingEntity, EntityHitResult> onHitCode;
		protected ImpactFXCode impactFXCode;
		protected Consumer<GenericProjectile> clientTrailFXCode;

		GenericProjectileType(int id, DamageType damageType, DeathType deathType, DamageSourceGetter dmgSourceGetter) {
			this.id = (byte)id;
			this.damageType = damageType;
			this.dmgSourceGetter = dmgSourceGetter;
			this.deathType = deathType;
			this.onHitCode = (entity, hitres)-> {};
			this.impactFXCode = (world, x, y, z, pitch, yaw, soundGroup) -> {};
			this.clientTrailFXCode = (entity)->{};
		};

		GenericProjectileType(int id, DamageType damageType, DeathType deathType, DamageSourceGetter dmgSourceGetter,
							  BiConsumer<LivingEntity, EntityHitResult> onHitCode, ImpactFXCode impactFXCode) {
			this(id, damageType, deathType, dmgSourceGetter);
			this.onHitCode = onHitCode;
			this.impactFXCode = impactFXCode;
		}

		GenericProjectileType(int id, DamageType damageType, DeathType deathType, DamageSourceGetter dmgSourceGetter,
							  ImpactFXCode impactFXCode) {
			this(id, damageType, deathType, dmgSourceGetter);
			this.impactFXCode = impactFXCode;
		}

		GenericProjectileType(int id, DamageType damageType, DeathType deathType, DamageSourceGetter dmgSourceGetter,
							  BiConsumer<LivingEntity, EntityHitResult> onHitCode, ImpactFXCode impactFXCode,
							  Consumer<GenericProjectile> clientTrailFXCode) {
			this(id,damageType,deathType, dmgSourceGetter,onHitCode,impactFXCode);
			this.clientTrailFXCode = clientTrailFXCode;
		}

	}

	protected static void handleBulletImpactIncendiary(World world, double x, double y, double z, float pitch, float yaw, BlockSoundGroup sound){
		handleBulletImpact(world, x,y,z, pitch,yaw, sound,true);
	}
	protected static void handleBulletImpactDefault(World world, double x, double y, double z, float pitch, float yaw, BlockSoundGroup sound){
		handleBulletImpact(world, x,y,z, pitch,yaw, sound,false);
	}
	protected static void handleBulletImpact(World world, double x, double y, double z, float pitch, float yaw, BlockSoundGroup sound, boolean incendiary) {
		if (!world.isClient()) {
			//Default bullets
			int type = -1;
			if (sound == BlockSoundGroup.STONE) {
				type = 0;
			} else if (sound == BlockSoundGroup.WOOD || sound == BlockSoundGroup.LADDER) {
				type = 1;
			} else if (sound == BlockSoundGroup.GLASS) {
				type = 2;
			} else if (sound == BlockSoundGroup.METAL || sound == BlockSoundGroup.ANVIL) {
				type = 3;
			} else if (sound == BlockSoundGroup.GRAVEL || sound == BlockSoundGroup.SAND) {
				type = 4;
			}
			TGPacketsS2C.sendToAllAround(new PacketGunImpactFX((short) type, x, y, z, pitch, yaw, incendiary), world, new Vec3d(x, y, z), TGEntities.bulletTrackRange);
		}
	}

	protected static void handleBulletImpactBlue(World world, double x, double y, double z, float pitch, float yaw, BlockSoundGroup sound) {
		if (sound == BlockSoundGroup.STONE) {
			world.playSound(null, x, y, z, TGSounds.BULLET_IMPACT_STONE, SoundCategory.AMBIENT, 1.0f, 1.0f);
			TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("Impact_BulletRock_Blue", x, y, z, 0, 0, 0, pitch, yaw, 1.0f), world, new Vec3d(x, y, z), 32.0f);

		} else if (sound == BlockSoundGroup.WOOD || sound == BlockSoundGroup.LADDER) {
			world.playSound(null, x, y, z, TGSounds.BULLET_IMPACT_WOOD, SoundCategory.AMBIENT, 1.0f, 1.0f);
			TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("Impact_BulletWood_Blue", x, y, z, 0, 0, 0, pitch, yaw, 1.0f), world, new Vec3d(x, y, z), 32.0f);

		} else if (sound == BlockSoundGroup.GLASS) {
			world.playSound(null, x, y, z, TGSounds.BULLET_IMPACT_GLASS, SoundCategory.AMBIENT, 1.0f, 1.0f);
			TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("Impact_BulletGlass_Blue", x, y, z, 0, 0, 0, pitch, yaw, 1.0f), world, new Vec3d(x, y, z), 32.0f);

		} else if (sound == BlockSoundGroup.METAL || sound == BlockSoundGroup.ANVIL) {
			world.playSound(null, x, y, z, TGSounds.BULLET_IMPACT_METAL, SoundCategory.AMBIENT, 1.0f, 1.0f);
			TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("Impact_BulletMetal_Blue", x, y, z, 0, 0, 0, pitch, yaw, 1.0f), world, new Vec3d(x, y, z), 32.0f);

		} else if (sound == BlockSoundGroup.GRAVEL || sound == BlockSoundGroup.SAND) {
			world.playSound(null, x, y, z, TGSounds.BULLET_IMPACT_DIRT, SoundCategory.AMBIENT, 1.0f, 1.0f);
			TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("Impact_BulletDirt_Blue", x, y, z, 0, 0, 0, pitch, yaw, 1.0f), world, new Vec3d(x, y, z), 32.0f);

		} else {
			world.playSound(null, x, y, z, TGSounds.BULLET_IMPACT_DIRT, SoundCategory.AMBIENT, 1.0f, 1.0f);
			TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("Impact_BulletDefault_Blue", x, y, z, 0, 0, 0, pitch, yaw, 1.0f), world, new Vec3d(x, y, z), 32.0f);
		}
	}

	public GenericProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * Need this for inheritance to pass EntityType
	 */
	protected GenericProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos) {
		super(T, world);
		this.damage = damage;
		this.damageMin = dmgMin;
		this.speed=speed;
		this.ticksToLive=TTL;
		this.lifetime=TTL;
		this.damageDropStart=dmgDropStart;
		this.damageDropEnd=dmgDropEnd;
		this.penetration=penetration;
		this.blockdamage = blockdamage;
		
		this.setOwner(p);
		this.updatePosition(p.getX(), p.getY()+p.getEyeHeight(p.getPose()), p.getZ());
		
		this.setRotation(p.headYaw +(float) (spread - (2 * Math.random() * spread)) * 40.0f,
				p.pitch + (float) (spread - (2 * Math.random() * spread)) * 40.0f);
		
		float offsetSide=0.16F;
		float offsetHeight=0f;
		
		//TODO NPC Shooter
		/*if(this.shooter!=null && shooter instanceof INPCTechgunsShooter) {
			INPCTechgunsShooter tgshooter = (INPCTechgunsShooter) this.shooter;
			offsetSide += tgshooter.getBulletOffsetSide();
			offsetHeight += tgshooter.getBulletOffsetHeight();
		}*/
		
		double posX, posY, posZ;
		Vec3d pos = this.getPos();
		posX = pos.x;
		posY = pos.y;
		posZ = pos.z;
		
		if (firePos==EnumBulletFirePos.RIGHT) {
			posX -= (double) (MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * offsetSide);
			//this.posY -= 0.10000000149011612D;
			posZ -= (double) (MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * offsetSide);
		} else if(firePos==EnumBulletFirePos.LEFT) {
			posX += (double) (MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * offsetSide);
			//this.posY -= 0.10000000149011612D;
			posZ += (double) (MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * offsetSide);
		} 
		posY += (-0.10000000149011612D+offsetHeight);
		
		this.updatePosition(posX, posY, posZ);
		this.startX = posX;
		this.startY = posY;
		this.startZ = posZ;
		
		Vec3d motion = this.getVelocity();
		double motionX = motion.x;
		double motionY = motion.y;
		double motionZ = motion.z;
		// this.yOffset = 0.0F;
		float f = 0.4F;
		motionX = (double) (-MathHelper.sin(this.yaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.pitch / 180.0F * (float) Math.PI) * f);
		motionZ = (double) (MathHelper.cos(this.yaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.pitch / 180.0F * (float) Math.PI) * f);
		motionY = (double) (-MathHelper.sin((this.pitch) / 180.0F * (float) Math.PI) * f);
		//this.setVelocity(this.motionX, this.motionY, this.motionZ, 1.5f, 0F);

		this.setVelocity(new Vec3d(motionX, motionY, motionZ).normalize().multiply(speed));
	}
	
	public GenericProjectile(World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos) {
		this(TGEntities.GENERIC_PROJECTILE, world, p, damage, speed, TTL, spread,
				dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
	}

	/**
	 * Client side constructor from spawn packet
	 * @param world
	 * @param shooter
	 * @param data
	 */
	public GenericProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter, CompoundTag data) {
		this(T, world, shooter, 0, 0,0,0,0,0,0,0,false, EnumBulletFirePos.CENTER);
		//this.parseAdditionalData(data);
	}
	
	float damage;
	public float speed = 1.0f; // speed in blocks per tick

	protected int ticksToLive = 100;
	protected int lifetime = 100; // TTL but not reduced per tick

	// DMG drop stats
	float damageDropStart;
	float damageDropEnd;
	float damageMin;
	protected LivingEntity shooter;

	float penetration = 0.0f;

	boolean silenced = false;
	protected boolean blockdamage = false;

	boolean posInitialized = false;
	double startX;
	double startY;
	double startZ;

	float radius=0.0f;
	double gravity=0.0f;
	
	boolean clientSlowDeath = false;
	
	protected IntOpenHashSet piercedEntities;
	protected List<Entity> piercingKilledEntities;
	
	protected byte projectileType=0;
		
	public GenericProjectileType getProjectileType() {
		if(this.projectileType>=0&& projectileType<GenericProjectileType.values().length) {
			return GenericProjectileType.values()[projectileType];
		}
		return GenericProjectileType.DEFAULT;
	}

	public byte getProjectileTypeId() {
		return this.projectileType;
	}

	public void setProjectileType(byte projectileType) {
		this.projectileType = projectileType;
	}

	public void tick() {
		super.tick();
				
		Vec3d vec3d = this.getVelocity();
		if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
			float f = MathHelper.sqrt(squaredHorizontalLength(vec3d));
			this.yaw = (float) (MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875D);
			this.pitch = (float) (MathHelper.atan2(vec3d.y, (double) f) * 57.2957763671875D);
			this.prevYaw = this.yaw;
			this.prevPitch = this.pitch;
		}

		if (!this.clientSlowDeath) {
		
			BlockPos blockPos = this.getBlockPos();
			BlockState blockState = this.world.getBlockState(blockPos);
			Vec3d vec3d4;
			if (!blockState.isAir()) {
				VoxelShape voxelShape = blockState.getCollisionShape(this.world, blockPos);
				if (!voxelShape.isEmpty()) {
					vec3d4 = this.getPos();
					Iterator<Box> var7 = voxelShape.getBoundingBoxes().iterator();
	
					while (var7.hasNext()) {
						Box box = (Box) var7.next();
						if (box.offset(blockPos).contains(vec3d4)) {
							break;
						}
					}
				}
			}
	
			if (this.isTouchingWaterOrRain()) {
				this.extinguish();
			}
	
			Vec3d vec3d3 = this.getPos();
			vec3d4 = vec3d3.add(vec3d);
			HitResult hitResult = this.world.raycast(new RaycastContext(vec3d3, vec3d4,
					RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
			if (hitResult.getType() != HitResult.Type.MISS) {
				vec3d4 = ((HitResult) hitResult).getPos();
			}
	
			while (!this.removed & !this.clientSlowDeath) {
				EntityHitResult entityHitResult = this.getEntityCollision(vec3d3, vec3d4);
				if (entityHitResult != null) {
					hitResult = entityHitResult;
				}
	
				if (hitResult != null && ((HitResult) hitResult).getType() == HitResult.Type.ENTITY) {
					Entity entity = ((EntityHitResult) hitResult).getEntity();
					Entity entity2 = this.getOwner();
					if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity
							&& !((PlayerEntity) entity2).shouldDamagePlayer((PlayerEntity) entity)) {
						hitResult = null;
						entityHitResult = null;
					}
				}
	
				if (hitResult != null) {
					this.onCollision((HitResult) hitResult);
					this.velocityDirty = true;
				}
	
				if (entityHitResult == null) {
					break;
				}
	
				hitResult = null;
			}
		}
		vec3d = this.getVelocity();
		double d = vec3d.x;
		double e = vec3d.y;
		double g = vec3d.z;

		double h = this.getX() + d;
		double j = this.getY() + e;
		double k = this.getZ() + g;
		float l = MathHelper.sqrt(squaredHorizontalLength(vec3d));

		this.yaw = (float) (MathHelper.atan2(d, g) * 57.2957763671875D);

		this.pitch = (float) (MathHelper.atan2(e, (double) l) * 57.2957763671875D);
		this.pitch = updateRotation(this.prevPitch, this.pitch);
		this.yaw = updateRotation(this.prevYaw, this.yaw);
		float m = 0.99F;
		//float n = 0.05F;
		if (this.isTouchingWater()) {
			for (int o = 0; o < 4; ++o) {
				//float p = 0.25F;
				this.world.addParticle(ParticleTypes.BUBBLE, h - d * 0.25D, j - e * 0.25D, k - g * 0.25D, d, e, g);
			}

			m = this.getDragInWater();
			this.inWaterTick();
		}

		this.setVelocity(vec3d.multiply((double) m));
		if (!this.hasNoGravity() && this.gravity>=0.0d) {
			Vec3d vec3d5 = this.getVelocity();
			this.setVelocity(vec3d5.x, vec3d5.y - this.gravity, vec3d5.z);
		}

		this.updatePosition(h, j, k);
		
		if (this.clientSlowDeath) {
			
			if (this.age>=this.ticksToLive+2) {
				this.remove();
			}
			
		} else {
			this.checkBlockCollision();
			
			if(this.age>=this.ticksToLive) {
				this.markForRemoval();
			}
		}
		
	}
	
	/**
	 * On server instantly set remove flag, on client keep alive for extra ticks
	 */
	protected void markForRemoval() {
		if (this.world.isClient) {
			this.clientSlowDeath = true;
		} else {
			this.remove();
		}
	}

	/**
	 * override for additional logic when in water, like dying
	 */
	protected void inWaterTick(){
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		double d = this.getBoundingBox().getAverageSideLength() * 10.0D;
		if (Double.isNaN(d)) {
			d = 1.0D;
		}

		d *= 64.0D * getRenderDistanceMultiplier();
		return distance < d * d;
	}
	
	protected float getDragInWater() {
		return 0.85F;
	}
	
	public boolean isAttackable() {
		return false;
	}

	protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.13F;
	}
	
	protected boolean canClimb() {
		return false;
	}
	
	public void shiftForward(float factor) {
		this.setPos(this.getPos().x+this.getVelocity().x * factor,
				    this.getPos().y+this.getVelocity().y * factor,
				    this.getPos().z+this.getVelocity().z * factor);
	}

	
	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity target = entityHitResult.getEntity();

		if (this.getPierceLevel() > 0) {
			if (this.piercedEntities == null) {
				this.piercedEntities = new IntOpenHashSet(5);
			}

			if (this.piercingKilledEntities == null) {
				this.piercingKilledEntities = Lists.newArrayListWithCapacity(5);
			}

			if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
				this.removeOnHit(entityHitResult);
				return;
			}

			this.piercedEntities.add(target.getEntityId());
		}

		Entity shooter = this.shooter;
		TGDamageSource damageSource;
		TGDamageSource damageSourceKnockback;
		if (shooter == null) {
			damageSource = this.getProjectileDamageSource();
			damageSource.setNoKnockback();
			damageSourceKnockback = TGDamageSource.getKnockbackDummyDmgSrc(this, this);
		} else {		
			damageSource = this.getProjectileDamageSource();
			damageSource.setNoKnockback();
			damageSourceKnockback = TGDamageSource.getKnockbackDummyDmgSrc(this, shooter);
			if (shooter instanceof LivingEntity) {
				((LivingEntity) shooter).onAttacking(target);
			}
		}
		
		target.damage(damageSourceKnockback, 0.1f);
		if (target.damage(damageSource, this.getDamage())) {

			if (target instanceof LivingEntity) {
				LivingEntity livingTarget = (LivingEntity) target;

				if (!this.world.isClient && shooter instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingTarget, shooter);
					EnchantmentHelper.onTargetDamaged((LivingEntity) shooter, livingTarget);
				}

				this.onHitEffect(livingTarget, entityHitResult);
				if (shooter != null && livingTarget != shooter && livingTarget instanceof PlayerEntity
						&& shooter instanceof ServerPlayerEntity && !this.isSilent()) {
					((ServerPlayerEntity) shooter).networkHandler.sendPacket(
							new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
				}

				if (!target.isAlive() && this.piercingKilledEntities != null) {
					this.piercingKilledEntities.add(livingTarget);
				}
			}
		}
		
		if (this.getPierceLevel() <= 0) {
			this.removeOnHit(entityHitResult);
		}
	}

	/**
	 * Get Damage for distance
	 * 
	 * @return
	 */
    protected float getDamage() {

		if (this.damageDropEnd==0f) { //not having damage drop
			return this.damage;
		}
		
		double distance = this.getDistanceTravelled();

		if (distance <= this.damageDropStart) {
			return this.damage;
		} else if (distance > this.damageDropEnd) {
			return this.damageMin;
		} else {
			float factor = 1.0f - (float) ((distance - this.damageDropStart) / (this.damageDropEnd - this.damageDropStart));
			return (this.damageMin + (this.damage - this.damageMin) * factor);
		}
	}
	
	/**
	 * Return the travelled distance
	 * 
	 * @return
	 */
	protected double getDistanceTravelled() {
		Vec3d start = new Vec3d(this.startX, this.startY, this.startZ);
		return start.distanceTo(this.getPos());
	}
    
	/**
	 * Override in subclass for extra hit effects, like burn, etc
	 * 
	 * @param livingEntity
	 * @param entityHitResult 
	 */
	protected void onHitEffect(LivingEntity livingEntity, EntityHitResult entityHitResult) {
		this.getProjectileType().onHitCode.accept(livingEntity,entityHitResult);
	}

	protected int getPierceLevel() {
		return 0;
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		
		doImpactEffects(blockHitResult);

		this.removeOnHit(blockHitResult);
	}
	
	protected void removeOnHit(HitResult hitResult) {
		this.markForRemoval();
	}


	protected void doImpactEffects(BlockHitResult rayTraceResult) {
		if (world.isClient) return;
		if (rayTraceResult.getType() == Type.MISS) return;

		BlockState blockhit = this.world.getBlockState(rayTraceResult.getBlockPos());
		BlockSoundGroup sound = blockhit.getSoundGroup();

    	double x = rayTraceResult.getPos().x;
    	double y = rayTraceResult.getPos().y;
    	double z = rayTraceResult.getPos().z;

    	float pitch = 0.0f;
    	float yaw = 0.0f;
    	if (rayTraceResult.getType() == Type.BLOCK) {
    		if (rayTraceResult.getSide() == Direction.UP) {
    			pitch = -90.0f;
    		} else if (rayTraceResult.getSide() == Direction.DOWN) {
    			pitch = 90.0f;
    		} else {
    			yaw = rayTraceResult.getSide().asRotation();
    		}
    	}else {
    		pitch = -this.pitch;
    		yaw = -this.yaw;
    	}

		this.getProjectileType().impactFXCode.handleImpactFX(this.world, x,y,z, pitch,yaw, sound);
	}

    public EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
		double d = Double.MAX_VALUE;
		Entity entity2 = null;
		Box box = this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D);
		Iterator<Entity> var9 = world.getOtherEntities(this, box, this::method_26958).iterator();

		Vec3d hitVec = null;
		while (var9.hasNext()) {
			Entity entity3 = (Entity) var9.next();
			Box box2 = entity3.getBoundingBox().expand(0.30000001192092896D);
			Optional<Vec3d> optional = box2.raycast(currentPosition, nextPosition);
			if (optional.isPresent()) {
				double e = currentPosition.squaredDistanceTo((Vec3d) optional.get());
				if (e < d) {
					entity2 = entity3;
					d = e;
					hitVec = optional.get();
				}
			}
		}

		if (entity2 == null) {
			return null;
		} else {
			return new EntityHitResult(entity2, hitVec);
		}
	}

	public static void burnBlocks(World world, BlockHitResult hitResult, double chanceToIgnite) {
		if(world.isClient) return;

		if (Math.random() <= chanceToIgnite) {
			BlockPos hit = hitResult.getBlockPos();

			switch (hitResult.getSide()) {
				case DOWN:
					if (world.isAir(hit.down())) world.setBlockState(hit.down(), Blocks.FIRE.getDefaultState());
					break;
				case UP:
					if (world.isAir(hit.up())) {
						if (world.getBlockState(hit) == Blocks.FARMLAND.getDefaultState()) world.setBlockState(hit, Blocks.DIRT.getDefaultState());
						world.setBlockState(hit.up(), Blocks.FIRE.getDefaultState());
					}
					break;
				default:
					BlockPos p = hit.offset(hitResult.getSide());
					if(world.isAir(p)) {
						world.setBlockState(p, Blocks.FIRE.getDefaultState());
					}
			}

		}
	}

	//Check which entities are valid targets
	protected boolean method_26958(Entity entity) {
		return super.method_26958(entity) && (this.piercedEntities == null || !this.piercedEntities.contains(entity.getEntityId()));
	}
	
	
	/**
	 * Override to change damage type etc
	 * 
	 * @return
	 */
	protected TGDamageSource getProjectileDamageSource() {
		GenericProjectileType type = this.getProjectileType();
		TGDamageSource src=type.dmgSourceGetter.getDamageSource(this, this.shooter, type.deathType);
		src.armorPenetration = this.penetration;
		src.setNoKnockback();
		return src;
	}
	
	public static class Factory implements IProjectileFactory<GenericProjectile> {

		protected byte projectileType;

		public Factory(){};
		public Factory(GenericProjectileType type) {
			this.projectileType = type.id;
		}

		@Override
		public GenericProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new GenericProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return GenericProjectileType.values()[projectileType].damageType;
		}

		@Override
		public byte getProjectileType() {
			return this.projectileType;
		}
	}
	
    @Override
	protected void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		tag.putDouble("gravity", this.gravity);
		tag.putInt("lifetime", this.ticksToLive);
		tag.putFloat("speed", this.speed);
		tag.putByte("projectile_type", this.projectileType);
	}

	@Override
	protected void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		this.gravity = tag.getDouble("gravity");
		this.ticksToLive = tag.getInt("lifetime");
		this.speed = tag.getFloat("speed");
		this.projectileType = tag.getByte("projectile_type");
	}

	public GenericProjectile setSilenced(){
    	this.silenced=true;
    	return this;
    }

	public void getAdditionalSpawnData(CompoundTag data) {
		data.putDouble("gravity", this.gravity);
		data.putInt("lifetime", this.ticksToLive);
		data.putFloat("speed", this.speed);
		data.putByte("projectile_type", this.projectileType);
	}
	
	/**
	 * Parse additional data from packet in construtor, extend in subclasses
	 * @param data
	 */
	public void parseAdditionalData(CompoundTag data) {
		this.gravity = data.getDouble("gravity");
		this.ticksToLive = data.getInt("lifetime");
		this.speed = data.getFloat("speed");
		this.projectileType = data.getByte("projectile_type");
	}
	
	@Override
	public Packet<?> createSpawnPacket() {
		Entity owner = this.getOwner();
		CompoundTag data = new CompoundTag();
		this.getAdditionalSpawnData(data);
	    return new PacketSpawnEntity(this, owner == null ? 0 : owner.getEntityId(), data);
	}

	/**
	 * Called after additional spawn data packet is parsed
	 */
	@Environment(EnvType.CLIENT)
	public void clientInitializeFinal(){
		this.getProjectileType().clientTrailFXCode.accept(this);
	}

	@Override
	protected void initDataTracker() {
	}
}
