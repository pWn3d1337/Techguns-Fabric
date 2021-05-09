package techguns.entities.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.api.damagesystem.DamageType;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.client.ClientProxy;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IChargedProjectileFactory;
import techguns.util.MathUtil;

public class GuidedMissileProjectile extends RocketProjectile{

	public static final double MAX_TURN_ANGLE = 9.0 *MathUtil.D2R; //= 180 degree per second
	
	public Entity target;
	
	
	public GuidedMissileProjectile(World par2World, LivingEntity p, float damage, float speed, int TTL,
			float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos leftGun, float radius, double gravity) {
		this(TGEntities.GUIDED_MISSILE, par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun,
				radius, gravity);
	}
	
	public GuidedMissileProjectile(World par2World, LivingEntity p, float damage, float speed, int TTL,
			float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos leftGun, float radius, Entity target) {
		this(TGEntities.GUIDED_MISSILE, par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun,
				radius, target);
	}
	
	protected GuidedMissileProjectile(EntityType<? extends GuidedMissileProjectile> T, World par2World, LivingEntity p, float damage, float speed, int TTL,
			float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos leftGun, float radius, double gravity) {
		super(T, par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun,
				radius, gravity);
	}
	
	protected GuidedMissileProjectile(EntityType<? extends GuidedMissileProjectile> T, World par2World, LivingEntity p, float damage, float speed, int TTL,
			float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos leftGun, float radius, Entity target) {
		super(T, par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun,
				radius, 0.0f);
		this.target = target;
	}
	
	public GuidedMissileProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
	}
	public GuidedMissileProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter) {
		super(T, world, shooter);
	}

	@Override
	protected void createTrailFX() {
		ClientProxy.get().createFXOnEntity("GuidedMissileExhaust", this);
	}

	//TODO
	/*@Override
	protected void explodeRocket() {
		if (!this.world.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("GuidedMissileExplosion", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
			TGExplosion explosion = new TGExplosion(world, this.shooter, this, posX, posY, posZ, this.damage, this.damageMin, this.damageDropStart, this.damageDropEnd, this.blockdamage?0.25:0.0);
			
			explosion.doExplosion(true);
		}else {
			Techguns.proxy.createLightPulse(this.posX, this.posY, this.posZ, 5, 15, 10.0f, 1.0f, 1f, 0.9f, 0.5f);
		}
		this.setDead();
	}*/
	
	@Override
	protected void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		if (target != null)
			tag.putInt("techguns_entityid",target.getEntityId());
		else
			tag.putInt("techguns_entityid",-1);
	}

	@Override
	protected void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		int entityID = tag.getInt("techguns_entityid");
		if (entityID > -1) {
			this.target = this.world.getEntityById(entityID); 
		}
	}
		
	@Override
	public void getAdditionalSpawnData(CompoundTag data) {
		super.getAdditionalSpawnData(data);
		data.putInt("techguns_entityid",target!=null ? target.getEntityId() : -1);
	}

	
	
	@Override
	public void parseAdditionalData(CompoundTag data) {
		super.parseAdditionalData(data);
		this.target = this.world.getEntityById(data.getInt("techguns_entityid"));
	}

	@Override
	public void tick() {
		//Update Motion
		if (this.target != null) {
			Vec3d motion = new Vec3d(getVelocity().x, getVelocity().y, getVelocity().z);
			double speed = motion.length();
			
			Vec3d v2 = new Vec3d(target.getPos().x, target.getPos().y+target.getHeight()*0.5f, target.getPos().z).subtract(this.getPos()).normalize();
			Vec3d v1 = motion.normalize();
			
			double angle = Math.acos(v1.dotProduct(v2));
			Vec3d axis = v1.crossProduct(v2).normalize();
			
			//angle = Math.min(angle, MAX_TURN_ANGLE);
			
			if (angle < MAX_TURN_ANGLE) {
				motion = v2.multiply(speed);
			}else {
				motion = MathUtil.rotateVector(v1, axis, MAX_TURN_ANGLE).multiply(speed);
			}
			
			this.setVelocity(motion);
		}
		super.tick();		
	}
	

//	@Override
//	public void setVelocity(double x, double y, double z) {
//		
//	}

	public static class Factory implements IChargedProjectileFactory<GuidedMissileProjectile> {

		@Override
		public GuidedMissileProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
		
			Entity target = null;
			if (p instanceof PlayerEntity) {
				ITGExtendedPlayer epc = (ITGExtendedPlayer) p;
				if (epc.getLockOnEntity() != null && epc.getLockOnTicks() >= ((GenericGun)p.getActiveItem().getItem()).getLockOnTicks()) {
					target = epc.getLockOnEntity();
				}
			}

			if (target != null) {
				return new GuidedMissileProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,radius, target);
			}else {
				return new GuidedMissileProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,radius,0.01f);
			}
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.EXPLOSION;
		}

		@Override
		public GuidedMissileProjectile createChargedProjectile(World world, LivingEntity p, float damage,
				float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
				boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity, float charge, int ammoConsumed) {	
			return null;
		}
		
	}
}
