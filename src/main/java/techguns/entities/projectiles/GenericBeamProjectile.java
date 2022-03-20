package techguns.entities.projectiles;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.TGPacketsS2C;
import techguns.api.damagesystem.DamageType;
import techguns.api.entity.ITGLivingEntity;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketGunImpactFX;
import techguns.packets.PacketSpawnParticle;
import techguns.util.MathUtil;

public class GenericBeamProjectile extends GenericProjectile{

	public static final byte BEAM_TYPE_NDR = 1;	
	public static final byte BEAM_TYPE_LASER=0;
	public static final byte BEAM_TYPE_TESLA=2;

	public double distance;
	public float laserPitch = 0.0f;
	public float laserYaw = 0.0f;
	public short maxTicks = 0;
	
	public short damageTicks;
	public boolean moveWithShooter;
	public EnumBulletFirePos firePos;
	
	public boolean traceDone = false;
	
	public String impactFX = "";
	
	public GenericBeamProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter) {
		super(T, world, shooter);
		this.maxTicks = (short)this.ticksToLive;
		this.updateBeamPosition();
	}

	public GenericBeamProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage,
			float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
			boolean blockdamage, EnumBulletFirePos firePos, short damageTicks, boolean moveWithShooter, byte projectileType, String impactFX) {
		super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
		this.maxTicks = (short)this.ticksToLive;
		this.damageTicks = damageTicks;
		this.moveWithShooter = moveWithShooter;
		this.projectileType = projectileType;
		this.firePos = firePos;
		this.impactFX = impactFX;
		this.updateBeamPosition();
	}

	public GenericBeamProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
		this.maxTicks = (short)this.ticksToLive;
		this.updateBeamPosition();
	}

	public GenericBeamProjectile(World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos, short damageTicks, boolean moveWithShooter, byte projectileType, String impactFX) {
		this(TGEntities.GENERIC_BEAM_PROJECTILE, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos,  damageTicks, moveWithShooter, projectileType, impactFX);
	}

	
	
	@Override
	public void tick() {
		if (this.moveWithShooter) {
			this.updateBeamPosition();
		}
		
		if (this.damageTicks-- > 0) {
			Vec3d dst = this.traceBeamHit();
			this.setBoundingBox(new Box(this.getPos(), dst));
		}
		
		--this.ticksToLive;
		if (this.ticksToLive <= 0) {
			this.markForRemoval();
		}
		
		this.baseTick();
	}
	
	protected void updateBeamPosition() {
		this.prevPitch = this.getPitch();
		this.prevYaw = this.getYaw();
		this.prevX = this.getX();
		this.prevY = this.getY();
		this.prevZ = this.getZ();
		LivingEntity p = (LivingEntity) this.getOwner();
		if (p != null) {
			this.updatePosition(p.getX(), p.getY()+((ITGLivingEntity)p).getEyeHeight_ServerSide(p.getPose()), p.getZ());

			//Spread for Laser? Perhaps?
			//float spread = 0f;
			this.setRotation(p.headYaw, p.getPitch());
//			this.setRotation(p.headYaw +(float) (spread - (2 * Math.random() * spread)) * 40.0f,
//					p.pitch + (float) (spread - (2 * Math.random() * spread)) * 40.0f);
			//System.out.println("UPDATE BEAM - pitch:"+pitch+" yaw:"+yaw);
		}else {
			//System.out.println("OWNER = NULL!");
		}

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
		
//		if (firePos==EnumBulletFirePos.RIGHT) {
//			posX -= (double) (MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * offsetSide);
//			//this.posY -= 0.10000000149011612D;
//			posZ -= (double) (MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * offsetSide);
//		} else if(firePos==EnumBulletFirePos.LEFT) {
//			posX += (double) (MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * offsetSide);
//			//this.posY -= 0.10000000149011612D;
//			posZ += (double) (MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * offsetSide);
//		} 
//		posY += (-0.10000000149011612D+offsetHeight);
		
		float offsetSide = 0.16F;
		float offsetHeight = -0.10000000149011612F;
		float offsetForward = 0.35f;
		
		float offsetZ = 0.0F;
		if (this.firePos == EnumBulletFirePos.RIGHT) {
			offsetZ = offsetSide;
		} else if (this.firePos == EnumBulletFirePos.LEFT) {
			offsetZ = -offsetSide;
		}

		Vec3d offset = MathUtil.rotateVec3dAroundZ(new Vec3d(offsetForward, offsetHeight, offsetZ), (float)(MathUtil.D2R*this.getPitch())).rotateY((float) (MathUtil.D2R*-(this.getYaw()+90f)));

		this.updatePosition(posX+offset.x, posY+offset.y, posZ+offset.z);
		
		Vec3d motion = this.getVelocity();
		double motionX = motion.x;
		double motionY = motion.y;
		double motionZ = motion.z;

		float f = 0.4F;
		motionX = (double) (-MathHelper.sin(this.getYaw() / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.getPitch() / 180.0F * (float) Math.PI) * f);
		motionZ = (double) (MathHelper.cos(this.getYaw() / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.getPitch() / 180.0F * (float) Math.PI) * f);
		motionY = (double) (-MathHelper.sin((this.getPitch()) / 180.0F * (float) Math.PI) * f);

		this.setVelocity(new Vec3d(motionX, motionY, motionZ).normalize().multiply(speed));
		this.laserPitch = this.getPitch();
		this.laserYaw = this.getYaw();
	}
	
	
	@Override
	protected void removeOnHit(HitResult hitResult) {
		//Don't remove, but track that we are done
		this.traceDone = true;
	}

	protected Vec3d traceBeamHit() {	
		Vec3d pos_src = this.getPos();
		Vec3d pos_dst = new Vec3d(this.getX() + this.getVelocity().x, this.getY() + this.getVelocity().y, this.getZ() + this.getVelocity().z);
		Vec3d pos_dst_final = pos_dst;
		HitResult hitResult = this.world.raycast(new RaycastContext(pos_src, pos_dst,
				RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
		
		if (hitResult.getType() != HitResult.Type.MISS) {
			pos_dst = ((HitResult) hitResult).getPos();
		}
		
		while (!this.traceDone) {
			EntityHitResult entityHitResult = this.getEntityCollision(pos_src, pos_dst);
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
				Vec3d hitVec = hitResult.getPos();
				distance = pos_src.distanceTo(hitVec);
				pos_dst_final = hitVec;
			}

			if (entityHitResult == null) {
				break;
			}

			hitResult = null;
		}
		this.traceDone = false;
		
		laserPitch = this.getPitch();
		laserYaw = this.getYaw();
		if (distance <= 0) {
			distance = this.speed;
		}
		//System.out.println("distance="+distance);
		
		return pos_dst_final;
			
	}
	
	@Override
	protected void onHitEffect(LivingEntity livingEntity, EntityHitResult entityHitResult) {
		//Vec3d dir = this.getVelocity().normalize().negate().multiply(0.1);
		Vec3d pos = entityHitResult.getPos();
		System.out.println("EntityHit pos:"+pos+", EntityPos:"+entityHitResult.getEntity().getPos());
		if(!this.world.isClient) {
			TGPacketsS2C.sendToAllTracking(new PacketSpawnParticle(this.impactFX, pos.x, pos.y, pos.z), this, true);
			//TGPacketsS2C.sendToAllTracking(new PacketSpawnParticle(this.impactFX, pos.x, pos.y, pos.z, dir.x, dir.y, dir.z), this, true);
			//TGPacketsS2C.sendToAllAroundEntity(new PacketSpawnParticle(this.impactFX, pos.x, pos.y, pos.z, dir.x, dir.y, dir.z), this, 50.0f);
    	}
	}
	
	@Override
	protected void doImpactEffects(BlockHitResult rayTraceResult) {
		if (rayTraceResult.getType() == Type.MISS) return;
		
    	Vec3d dir;
    	if (rayTraceResult.getType() == Type.BLOCK) {
    		Vec3i dir_i = rayTraceResult.getSide().getVector();
    		dir = new Vec3d(dir_i.getX(), dir_i.getY(), dir_i.getZ()).multiply(0.1);
    	}else {
    		dir = this.getVelocity().normalize().negate().multiply(0.1);
    	}
		Vec3d pos = rayTraceResult.getPos();
		if(!this.world.isClient) {
			TGPacketsS2C.sendToAllTracking(new PacketSpawnParticle(this.impactFX, pos.x, pos.y, pos.z, dir.x, dir.y, dir.z), this, true);
			//TGPacketsS2C.sendToAllAroundEntity(new PacketSpawnParticle(this.impactFX, pos.x, pos.y, pos.z, dir.x, dir.y, dir.z), this, 50.0f);
    	}
	}
	
	@Override
    protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src;
		if (this.projectileType == BEAM_TYPE_NDR) {
			src = TGDamageSource.causeRadiationDamage(this, this.getOwner(), EntityDeathUtils.DeathType.LASER);
		}else {
			src = TGDamageSource.causeEnergyDamage(this, this.getOwner(), EntityDeathUtils.DeathType.LASER);
		}
		src.armorPenetration = this.penetration;
        src.setNoKnockback();
        return src;
    }
	
	@Override
	protected void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putDouble("laserLength",this.distance);
		tag.putFloat("laserPitch",this.laserPitch);
 		tag.putFloat("laserYaw", this.laserYaw);
		tag.putShort("maxTicks", this.maxTicks);
		tag.putBoolean("moveWithShooter", this.moveWithShooter);
		tag.putByte("firePos", (byte)this.firePos.ordinal());
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.distance = tag.getDouble("laserLength");
		this.laserPitch = tag.getFloat("laserPitch");
		this.laserYaw = tag.getFloat("laserYaw");
		this.maxTicks = tag.getShort("maxTicks");
		this.moveWithShooter = tag.getBoolean("moveWithShooter");
		byte firepos = tag.getByte("firePos");
		if (firepos >= 0 && firepos < EnumBulletFirePos.values().length) {
			this.firePos = EnumBulletFirePos.values()[firepos];
		}
				
	}

	@Override
	public void getAdditionalSpawnData(NbtCompound tag) {
		super.getAdditionalSpawnData(tag);
		tag.putDouble("laserLength",this.distance);
		tag.putFloat("laserPitch",this.laserPitch);
		tag.putFloat("laserYaw", this.laserYaw);
		tag.putShort("maxTicks", this.maxTicks);
		tag.putBoolean("moveWithShooter", this.moveWithShooter);
		tag.putByte("firePos", (byte)this.firePos.ordinal());
	}
	
	@Override
	public void parseAdditionalData(NbtCompound tag) {
		super.parseAdditionalData(tag);
		this.distance = tag.getDouble("laserLength");
		this.laserPitch = tag.getFloat("laserPitch");
		this.laserYaw = tag.getFloat("laserYaw");
		this.maxTicks = tag.getShort("maxTicks");
		this.moveWithShooter = tag.getBoolean("moveWithShooter");
		byte firepos = tag.getByte("firePos");
		if (firepos >= 0 && firepos < EnumBulletFirePos.values().length) {
			this.firePos = EnumBulletFirePos.values()[firepos];
		}
	}
	
	public static class Factory implements IProjectileFactory<GenericBeamProjectile> {

		protected short damageTicks = 1;
		protected boolean moveWithShooter = false;
		protected byte projectileType = 0;
		protected String impactFX;
		
		public Factory(int damageTicks, boolean moveWithShooter, byte projectileType, String impactFX) {
			this.damageTicks = (short)damageTicks;
			this.moveWithShooter = moveWithShooter;
			this.projectileType = projectileType;
			this.impactFX = impactFX;
		}

		@Override
		public byte getProjectileType() {
			return this.projectileType;
		}
		
		@Override
		public GenericBeamProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			TTL = 10;
			return new GenericBeamProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos, damageTicks, moveWithShooter, projectileType, impactFX);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.ENERGY;
		}
		
	}
	
}
