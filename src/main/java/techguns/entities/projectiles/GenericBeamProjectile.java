package techguns.entities.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import techguns.api.damagesystem.DamageType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;

public class GenericBeamProjectile extends GenericProjectile{

	public double distance = -1d;
	public float laserPitch = 0.0f;
	public float laserYaw = 0.0f;
	public short maxTicks = 0;
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
		this.traceBeamHit();
	}

	public GenericBeamProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
		this.maxTicks = (short)this.ticksToLive;
	}

	public GenericBeamProjectile(World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos) {
		//TODO
		super(world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
	}

	@Override
	public void tick() {
		--this.ticksToLive;
		if (this.ticksToLive <= 0) {
			this.markForRemoval();
		}
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
