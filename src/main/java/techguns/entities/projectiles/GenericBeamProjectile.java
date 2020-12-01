package techguns.entities.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.api.damagesystem.DamageType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;

public class GenericBeamProjectile extends GenericProjectile{

	public static final byte BEAM_TYPE_NDR = 1;
	
	
	public double distance = -1d;
	public float laserPitch = 0.0f;
	public float laserYaw = 0.0f;
	public short maxTicks = 0;
	
	public int damageTicks = 1;
	public boolean moveWithShooter = false;
	public EnumBulletFirePos firePos;
	
	protected boolean traceDone = false;
	
	public GenericBeamProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter,
			CompoundTag data) {
		super(T, world, shooter, data);
		this.maxTicks = (short)this.ticksToLive;
	}

	public GenericBeamProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage,
			float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
			boolean blockdamage, EnumBulletFirePos firePos) {
		super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
		this.maxTicks = (short)this.ticksToLive;
	}

	public GenericBeamProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
		this.maxTicks = (short)this.ticksToLive;
	}

	public GenericBeamProjectile(World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos) {
		this(TGEntities.GENERIC_BEAM_PROJECTILE, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
	}

	@Override
	public void tick() {		
		if (this.moveWithShooter) {
			this.updateBeamPosition();
		}
		
		if (this.damageTicks-- > 0) {
			this.traceBeamHit();
		}

		
		--this.ticksToLive;
		if (this.ticksToLive <= 0) {
			this.markForRemoval();
		}
	}
	
	protected void updateBeamPosition() {
		LivingEntity p = (LivingEntity) this.getOwner();
		this.updatePosition(p.getX(), p.getY()+p.getEyeHeight(p.getPose()), p.getZ());
		
		//Spread for Laser? Perhaps?
		float spread = 0f;
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
		
		Vec3d motion = this.getVelocity();
		double motionX = motion.x;
		double motionY = motion.y;
		double motionZ = motion.z;

		float f = 0.4F;
		motionX = (double) (-MathHelper.sin(this.yaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.pitch / 180.0F * (float) Math.PI) * f);
		motionZ = (double) (MathHelper.cos(this.yaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.pitch / 180.0F * (float) Math.PI) * f);
		motionY = (double) (-MathHelper.sin((this.pitch) / 180.0F * (float) Math.PI) * f);

		this.setVelocity(new Vec3d(motionX, motionY, motionZ).normalize().multiply(speed));
	
	}
	
	
	@Override
	protected void removeOnHit(HitResult hitResult) {
		//Don't remove, but track that we are done
		this.traceDone = true;
	}

	protected void traceBeamHit() {	
		Vec3d pos_src = this.getPos();
		Vec3d pos_dst = new Vec3d(this.getX() + this.getVelocity().x, this.getY() + this.getVelocity().y, this.getZ() + this.getVelocity().z);

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
			}

			if (entityHitResult == null) {
				break;
			}

			hitResult = null;
		}
		this.traceDone = false;
		
		laserPitch = this.pitch;
		laserYaw = this.yaw;
		if (distance <= 0) {
			distance = this.speed;
		}
		
	}
	
	@Override
	protected void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		tag.putDouble("laserLength",this.distance);
		tag.putFloat("laserPitch",this.laserPitch);
		tag.putFloat("laserYaw", this.laserYaw);
		tag.putShort("maxTicks", this.maxTicks);
	}

	@Override
	protected void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		this.distance = tag.getDouble("laserLength");
		this.laserPitch = tag.getFloat("laserPitch");
		this.laserYaw = tag.getFloat("laserYaw");
		this.maxTicks = tag.getShort("maxTicks");
	}

	@Override
	public void getAdditionalSpawnData(CompoundTag tag) {
		super.getAdditionalSpawnData(tag);
		tag.putDouble("laserLength",this.distance);
		tag.putFloat("laserPitch",this.laserPitch);
		tag.putFloat("laserYaw", this.laserYaw);
		tag.putShort("maxTicks", this.maxTicks);
	}
	
	@Override
	protected void parseAdditionalData(CompoundTag tag) {
		super.parseAdditionalData(tag);
		this.distance = tag.getDouble("laserLength");
		this.laserPitch = tag.getFloat("laserPitch");
		this.laserYaw = tag.getFloat("laserYaw");
		this.maxTicks = tag.getShort("maxTicks");
	}
	
	public static class Factory implements IProjectileFactory<GenericBeamProjectile> {

		@Override
		public GenericBeamProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new GenericBeamProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.ENERGY;
		}
		
	}
	
}
