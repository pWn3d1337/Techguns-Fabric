package techguns.entities.projectiles;

import java.util.Iterator;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
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
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketGunImpactFX;
import techguns.packets.PacketSpawnEntity;

public class GenericProjectile extends ProjectileEntity {
	
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
		this.speed=speed;
		this.ticksToLive=TTL;
		this.lifetime=TTL;
		this.damageDropStart=dmgDropStart;
		this.damageDropEnd=dmgDropEnd;
		this.penetration=penetration;
		this.blockdamage = blockdamage;
		//TODO spread?
		this.setOwner(p);
		this.updatePosition(p.getX(), p.getY()+p.getEyeHeight(p.getPose()), p.getZ());
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
		this.parseAdditionalData(data);
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
		}

		this.setVelocity(vec3d.multiply((double) m));
		if (!this.hasNoGravity()) {
			Vec3d vec3d5 = this.getVelocity();
			this.setVelocity(vec3d5.x, vec3d5.y - 0.005000000074505806D, vec3d5.z);
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
		System.out.println("HIT ENTITY!");
		super.onEntityHit(entityHitResult);
		
		this.markForRemoval();

	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		System.out.println("HIT BLOCK!");
		super.onBlockHit(blockHitResult);
		
		doImpactEffects(blockHitResult);
		
		this.markForRemoval();
	}
	
	protected void doImpactEffects(BlockHitResult rayTraceResult) {
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
    	
    	int type =-1;
    	if(sound==BlockSoundGroup.STONE) {
			type=0;
			
		} else if(sound==BlockSoundGroup.WOOD || sound==BlockSoundGroup.LADDER) {
			type=1;
			
		} else if(sound==BlockSoundGroup.GLASS) {
			type=2;
			
		} else if(sound==BlockSoundGroup.METAL || sound==BlockSoundGroup.ANVIL) {
			type=3;
			
		} else if(sound ==BlockSoundGroup.GRAVEL || sound == BlockSoundGroup.SAND) {
			type=4;
			
		} 
    	this.sendImpactFX(x, y, z, pitch, yaw, type);
	}

    protected void sendImpactFX(double x, double y, double z, float pitch, float yaw, int type) {
    	sendImpactFX(x, y, z, pitch, yaw, type, false);
    }
    
    protected void sendImpactFX(double x, double y, double z, float pitch, float yaw, int type, boolean incendiary) {
    	if(!this.world.isClient) {
    		TGPacketsS2C.sendToAllAround(new PacketGunImpactFX((short) type,x,y,z,pitch,yaw, incendiary), world, new Vec3d(x, y, z), TGEntities.bulletTrackRange);
    	}
    }

	protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
		return ProjectileUtil.getEntityCollision(this.world, this, currentPosition, nextPosition,
				this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D), this::method_26958);
	}
	
	/**
	 * Override to change damage type etc
	 * 
	 * @return
	 */
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeBulletDamage(this, this.shooter, DeathType.GORE);
		src.armorPenetration = this.penetration;
		src.setNoKnockback();
		return src;
	}
	
	public static class Factory implements IProjectileFactory<GenericProjectile> {

		@Override
		public GenericProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new GenericProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.PROJECTILE;
		}
		
	}
	
    @Override
	protected void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		tag.putInt("lifetime", this.ticksToLive);
		tag.putFloat("speed", this.speed);
	}

	@Override
	protected void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		this.ticksToLive = tag.getInt("lifetime");
		this.speed = tag.getFloat("speed");
	}

	public GenericProjectile setSilenced(){
    	this.silenced=true;
    	return this;
    }

	public void getAdditionalSpawnData(CompoundTag data) {
		data.putInt("lifetime", this.ticksToLive);
		data.putFloat("speed", this.speed);
	}
	
	/**
	 * Parse additional data from packet in construtor, extend in subclasses
	 * @param data
	 */
	protected void parseAdditionalData(CompoundTag data) {
		this.ticksToLive = data.getInt("lifetime");
		this.speed = data.getFloat("speed");
	}
	
	@Override
	public Packet<?> createSpawnPacket() {
		Entity owner = this.getOwner();
		CompoundTag data = new CompoundTag();
		this.getAdditionalSpawnData(data);
	    return new PacketSpawnEntity(this, owner == null ? 0 : owner.getEntityId(), data);//new EntitySpawnS2CPacket(this, entity == null ? 0 : entity.getEntityId());
		//return new PacketSpawnEntity(this.getEntityId(), this.uuid, this.getX(), this.getY(), this.getZ(), this.pitch, this.yaw, this.getType(), owner == null ? 0 : owner.getEntityId(), this.getVelocity(), data);
	}

	@Override
	protected void initDataTracker() {
	}
}
