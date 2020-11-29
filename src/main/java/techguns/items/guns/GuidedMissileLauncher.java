package techguns.items.guns;

import java.util.List;
import java.util.Optional;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import techguns.TGSounds;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.GuidedMissileProjectile;
import techguns.sounds.TGSoundCategory;
import techguns.util.SoundUtil;

public class GuidedMissileLauncher extends GenericGunCharge {
	
	public static final float LOCK_RANGE = 150.0f;
	public static final float LOCK_ERROR_THRESHOLD = 0.5f;
	//public static final int LOCK_TICKS = 10; //ticks required for lock completion
	//public static final int LOCK_COMPLETE_TICKS = 20; //This is value is used when the lock is complete

	public GuidedMissileLauncher(String name, ChargedProjectileSelector projectile_selector, boolean semiAuto,
			int minFiretime, int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound,
			int TTL, float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy, fullChargeTime, ammoConsumedOnFullCharge);
	}

	
	
	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		super.usageTick(world, user, stack, remainingUseTicks);
			
		if (world.isClient) {
			if (user instanceof PlayerEntity) {
				ITGExtendedPlayer epc = (ITGExtendedPlayer)user;
				traceTarget(user);			
				//Handle sounds
		
				if (epc.getLockOnTicks() >= this.lockOnTicks) {
					if (remainingUseTicks % 4 == 0) SoundUtil.playSoundOnEntityGunPosition(world, user, TGSounds.LOCKED_BEEP, SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.PLAYER_EFFECT);
				}else if (epc.getLockOnTicks() > 0) {
					if (remainingUseTicks % 4 == 0) SoundUtil.playSoundOnEntityGunPosition(world, user, TGSounds.LOCKON_BEEP, SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.PLAYER_EFFECT);
				}else {
					if (remainingUseTicks % 12 == 0) SoundUtil.playSoundOnEntityGunPosition(world, user, TGSounds.LOCKON_BEEP, SOUND_DISTANCE, 0.5F, false, false, TGSoundCategory.PLAYER_EFFECT);
				}
				
			}
		}
	}
	
	protected void traceTarget(LivingEntity shooter) {
		Vec3d vec3d1 = new Vec3d(shooter.getPos().x, shooter.getPos().y+shooter.getEyeHeight(shooter.getPose()), shooter.getPos().z);
		Vec3d vec3d = vec3d1.add(shooter.getRotationVector().multiply(LOCK_RANGE));
		
		
		//RayTraceResult raytraceresult = shooter.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);

		BlockHitResult res = shooter.world.raycast(new RaycastContext(vec3d1, vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, shooter));
		
		if(res.getType() != Type.MISS) {
			vec3d = res.getPos();
		}

		Entity entity = this.findEntityOnPath(shooter, vec3d1, vec3d);

		EntityHitResult entityHit = null;
		if (entity != null) {
			entityHit = new EntityHitResult(entity);
			
			if (entity instanceof PlayerEntity) {
				PlayerEntity entityplayer = (PlayerEntity) entity;
	
				if (shooter instanceof PlayerEntity && !((PlayerEntity) shooter).shouldDamagePlayer(entityplayer)) {
					entityHit = null;
				}
			}
		}

		if (shooter instanceof PlayerEntity ) {
			ITGExtendedPlayer epc = (ITGExtendedPlayer) shooter;
			if (entityHit != null && entityHit.getEntity() != null) {
				if (epc.getLockOnEntity() != null && epc.getLockOnEntity().isAlive()) {
					if (epc.getLockOnEntity() == entityHit.getEntity()) {
						epc.setLockOnTicks(epc.getLockOnTicks()+1);
						if(epc.getLockOnTicks() >= this.lockOnTicks) {
							//LOCK COMPLETED!
							epc.setLockOnTicks(this.lockOnTicks+this.lockOnPersistTicks);
						}
					}else {
						if (epc.getLockOnTicks() > 0) {
							epc.setLockOnTicks(epc.getLockOnTicks()-1);
						}else {
							epc.setLockOnEntity(null);
							epc.setLockOnTicks(0);
						}
					}
				}else {
					epc.setLockOnEntity(entityHit.getEntity());
					epc.setLockOnTicks(1);
				}
			}else {
				if (epc.getLockOnEntity() != null && !epc.getLockOnEntity().isAlive()) {
					epc.setLockOnEntity(null);
					epc.setLockOnTicks(0);
				}else {
					if (epc.getLockOnTicks() > 0) {
						epc.setLockOnTicks(epc.getLockOnTicks()-1);
					}else {
						epc.setLockOnEntity(null);
					}
				}
			}
//			if (epc.lockOnEntity != null) {
//				System.out.println("Locking on: "+epc.lockOnEntity.getName()+" - Status: "+epc.lockOnTicks);
//			}
		}
	}
	
	protected Entity findEntityOnPath(LivingEntity shooter, Vec3d start, Vec3d end) {
		Entity entity = null;
		Vec3d ray = shooter.getRotationVector().multiply((double)LOCK_RANGE);
		
		List<Entity> list = shooter.world.getOtherEntities(shooter, shooter.getBoundingBox().expand(ray.x, ray.y, ray.z).expand(1.0D), (ent) -> {
			if (!ent.isSpectator() && ent.isAlive() && ent.collides()) {
		         return shooter == null || !shooter.isConnectedThroughVehicle(ent);
		      } else {
		         return false;
		      }
		});
		
		double d0 = 0.0D;
		
		Entity prevTarget = null;
		if (shooter instanceof PlayerEntity ) {
			ITGExtendedPlayer epc = (ITGExtendedPlayer)shooter;
			prevTarget = epc.getLockOnEntity();
		}
		

		for (int i = 0; i < list.size(); ++i) {
			Entity entity1 = list.get(i);

			Box axisalignedbb = entity1.getBoundingBox().expand(LOCK_ERROR_THRESHOLD);
			//RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);

			Optional<Vec3d> hit = axisalignedbb.raycast(start, end);
			if (hit.isPresent()) {
				if(prevTarget == null || entity  != prevTarget ) {
					if (entity1 == prevTarget) {
						entity = prevTarget;
					}else {		
						double d1 = start.squaredDistanceTo(hit.get());
		
						if (d1 < d0 || d0 == 0.0D) {
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

		}

		return entity;
	}
	
	
	
	/*@Override
	protected void spawnProjectile(World world, LivingEntity player, ItemStack itemstack, float spread,
			float offset, float damagebonus, EnumBulletFirePos firePos, Entity target) {
		IProjectileFactory<GuidedMissileProjectile> projectile = this.projectile_selector.getFactoryForType(this.getCurrentAmmoVariantKey(itemstack));
		
		GuidedMissileProjectile proj = projectile.createProjectile(this, world, player, damage * damagebonus, speed, this.getScaledTTL(), spread, this.damageDropStart,
				this.damageDropEnd, this.damageMin * damagebonus, this.penetration, getDoBlockDamage(player), firePos, radius, gravity);

		proj.setProperties(player, player.pitch, player.yaw, 0.0F, speed, 1.0F);
		
		float f=1.0f;
		//TODO lighpulses
		/*if(this.muzzelight) {
			Techguns.proxy.createLightPulse(proj.posX+player.getLookVec().x*f, proj.posY+player.getLookVec().y*f, proj.posZ+player.getLookVec().z*f, this.light_lifetime, this.light_radius_start, this.light_radius_end, this.light_r, this.light_g, this.light_b);
		}*/ /*
		if (silenced) {
			proj.setSilenced();
		}
		if (offset > 0.0f) {
			proj.shiftForward(offset/speed); 
		}
		
		proj.target = target;
		
		world.spawnEntity(proj);
	}*/
	
	@Override
	protected void onProjectileSpawn(GenericProjectile proj, World world, LivingEntity player, ItemStack itemstack,
			float spread, float offset, float damagebonus, EnumBulletFirePos firePos, Entity target) {
		super.onProjectileSpawn(proj, world, player, itemstack, spread, offset, damagebonus, firePos, target);
		
		if(proj instanceof GuidedMissileProjectile) {
			GuidedMissileProjectile missile = (GuidedMissileProjectile) proj;
			missile.target = target;
		}
	}
	



	@Override
	public int consumeAmmoCharge(ItemStack item, float f, boolean creative) {
		return 0;
	}
	
}
