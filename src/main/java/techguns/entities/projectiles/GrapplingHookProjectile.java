package techguns.entities.projectiles;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.api.damagesystem.DamageType;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IChargedProjectileFactory;

public class GrapplingHookProjectile extends GenericProjectile{

	public GrapplingStatus status = GrapplingStatus.NONE;
	public Entity targetEntity = null;
	public BlockPos targetBlock = null;
	public Vec3d targetPos;
	
	//TODO: Set flag if projectile has collided to avoid infinity loop
	
	//Grappling Hook Parameters
	public float pullSpeed = 2.0f; //Distance per second
	public float pullMaxSpeedDistance = 5.0f; //Use pullSpeed above this distance
	public float pullTargetDistance = 1.0f; //Pull until there is 1 block distance;
	public int maxPullTicks = 100;
	
	public enum GrapplingStatus {
		NONE, LAUNCHING, GRAPPLING_BLOCK, GRAPPLING_ENTITY, PULL_ENTITY;
	}
	
	public GrapplingHookProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public GrapplingHookProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter) {
		super(T, world, shooter);
		this.status = GrapplingStatus.LAUNCHING;
	}	
	
	public GrapplingHookProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos, double gravity) {
		super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
		this.status = GrapplingStatus.LAUNCHING;
	}
	
	
	protected boolean isShooterGrappling() {
		LivingEntity shooter = (LivingEntity)this.getOwner();
		
		if (shooter != null && shooter.isAlive() && shooter instanceof PlayerEntity) {
			ITGExtendedPlayer txp = (ITGExtendedPlayer)shooter;
			return txp.isChargingWeapon();
		}
		return false;
	}
	
	
	protected void stopGrappling() {
		this.status = GrapplingStatus.NONE;
		this.targetBlock = null;
		this.targetEntity = null;
		this.targetPos = null;
	}
	
	
	@Override
	public void tick() {
		//TODO: Client?
//		if (this.world.isClient) {
//			super.tick();
//			return;
//		}
		System.out.println("GrapplingStatus = "+status);
		
		// ---
		if (this.status == GrapplingStatus.LAUNCHING) {
			if (isShooterGrappling()) {
				super.tick();
			}else {
				stopGrappling();
			}
		}
		else if (this.status == GrapplingStatus.NONE) {
			this.remove();
		}else { // if (!this.world.isClient){
			if (!isShooterGrappling() || this.maxPullTicks-- <= 0) {
				stopGrappling();
			}
			
			Vec3d dst = null;
			Entity movingEntity = null;
			
			if (this.status == GrapplingStatus.GRAPPLING_ENTITY) {
				if (this.targetEntity != null && this.targetEntity.isAlive()) {
					dst = this.targetEntity.getCameraPosVec(1.0f);
					movingEntity = this.getOwner();
					pullEntity(movingEntity, dst);
					this.updatePosition(dst.x, dst.y, dst.z);
				}else {
					stopGrappling();
				}
			}else if (this.status == GrapplingStatus.GRAPPLING_BLOCK) {
				if (this.targetPos != null && !this.world.getBlockState(this.targetBlock).isAir()) {
					dst = this.targetPos;
					movingEntity = this.getOwner();
					pullEntity(movingEntity, dst);
				}else {
					stopGrappling();
				}
			}else if (this.status == GrapplingStatus.PULL_ENTITY) {
				if (this.targetEntity != null && this.targetEntity.isAlive()) {
					dst = this.getOwner().getPos(); //getCameraPosVec(1.0f);
					movingEntity = this.targetEntity;
					//Vec3d prevPos = movingEntity.getPos();
					pullEntity(movingEntity, dst);
					//Vec3d newPos = this.getPos().add(movingEntity.getPos().subtract(prevPos));
					
					this.updatePosition(movingEntity.getX(), movingEntity.getY()+movingEntity.getStandingEyeHeight()*0.5, movingEntity.getZ());
				}else {
					stopGrappling();
				}
			}

		}
	}
	
	
	protected void pullEntity(Entity entity, Vec3d dst) {
		Vec3d src = entity.getPos();
		Vec3d diff = dst.subtract(src);
		double dist = diff.length();
		Vec3d dir = diff.normalize();
		
		float speed;
		if (dist >= this.pullMaxSpeedDistance) {
			speed = pullSpeed;
		}else {
			speed = (float) (pullSpeed * dist/pullMaxSpeedDistance);
		}
		
		if (dist <= this.pullTargetDistance) {
			entity.setVelocity(0,0,0);
			entity.velocityModified = true;
			this.status = GrapplingStatus.NONE;
		}else {
			entity.setVelocity(dir.multiply(speed));
			entity.velocityModified = true;
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		
//		if (entity == null || entity.world.isClient) {
//			this.status = GrapplingStatus.NONE;
//			this.removed = true;
//			return;
//		}
		
		if(isShooterGrappling() && entity != null && entity.isAlive()) {
			if (this.getOwner().isSneaking()) {
				this.status = GrapplingStatus.PULL_ENTITY;
				this.shouldCollide = false;
			}else {
				this.status = GrapplingStatus.GRAPPLING_ENTITY;
				this.shouldCollide = false;
				System.out.println("New GrapplingStatus = "+status);
			}
			this.targetEntity = entity;
		}else {
			this.status = GrapplingStatus.NONE;
			//this.removed = true;
		}
	}
	
	
	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		//If block was hit --> Set block to grappling target (block)
		BlockState block = this.world.getBlockState(blockHitResult.getBlockPos());
		if (!block.isAir() && !this.getOwner().isSneaking()) {
			//TODO: Adjust target pos depending on which side of block was hit
			this.targetBlock = blockHitResult.getBlockPos();
			if (blockHitResult.getSide() == Direction.UP) {
				this.targetPos = blockHitResult.getPos().add(0, 0.15d, 0);
			}else {
				this.targetPos = blockHitResult.getPos();
			}
			this.status = GrapplingStatus.GRAPPLING_BLOCK;
			this.shouldCollide = false;
		}else {
			this.status = GrapplingStatus.NONE;
		}
	}
	
	@Override
	protected void markForRemoval() {
		//Check Grappling status
		if (this.status == GrapplingStatus.NONE || this.status == GrapplingStatus.LAUNCHING) {
			//Projectile missed or expired;
			this.remove();
		}
	}
	
	@Override
	protected void removeOnHit(HitResult hitResult) {
		if (this.status == GrapplingStatus.NONE) {
			this.markForRemoval();
		}
		//Keep entity after hit to handle grappling
	}
	
	

	public static class Factory implements IChargedProjectileFactory<GrapplingHookProjectile> {

		@Override
		public GrapplingHookProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage,
				float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
				float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			GrapplingHookProjectile proj = new GrapplingHookProjectile(TGEntities.GRAPPLING_HOOK_PROJECTILE, world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,gravity);
			System.out.println("Spawn Grappling Hook Projectile");
			return proj;
		}

		@Override
		public DamageType getDamageType() {
			 return DamageType.PROJECTILE;
		}

		@Override
		public GrapplingHookProjectile createChargedProjectile(World world, LivingEntity p, float damage, float speed,
				int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
				boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity, float charge,
				int ammoConsumed) {
			return null;
		}

		
		
	}

}
