package techguns.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;

public class RocketProjectile extends GenericProjectile {

	public RocketProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
		if (world.isClient) {
			this.createTrailFX();
		}
	}

	protected RocketProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos, float radius, double gravity) {
		super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
		this.radius=radius;
		this.gravity=gravity;
	}
	
	public RocketProjectile(World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos, float radius, double gravity) {
		this(TGEntities.ROCKET_PROJECTILE, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos, radius, gravity);
	}
	
	public RocketProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter, CompoundTag data) {
		super(T, world, shooter, data);
		if (world.isClient) {
			this.createTrailFX();
		}
	}

	protected void createTrailFX() {
		ClientProxy.get().createFXOnEntity("RocketLauncherExhaust", this);
	}
	

	//TODO
	/*@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResult) {
		this.explodeRocket();
	}

	@Override
	protected void hitBlock(RayTraceResult raytraceResultIn) {
		this.explodeRocket();
	}*/

	/*protected void explodeRocket(){
		if (!this.world.isClient){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("RocketExplosion", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
			//TGPackets.network.sendToAllAround(new PacketSpawnParticle("TestFX", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));

    		//ProjectileExplosion explosion = new ProjectileExplosion(worldObj, this.posX, this.posY, this.posZ, this.shooter, radius, (int)damage, radius*0.5f, radius*1.5f);
			//Explosion explosion = new Explosion(world, this,this.posX,this.posY, this.posZ, 5, blockdamage, blockdamage);
			//explosion.doExplosionA();
			//explosion.doExplosionB(true);
			
			TGExplosion explosion = new TGExplosion(world, this.shooter, this, posX, posY, posZ, this.damage, this.damageMin, this.damageDropStart,this.damageDropEnd, this.blockdamage?0.5:0.0);
			
			explosion.doExplosion(true);
		}else {
			//Techguns.proxy.createLightPulse(this.posX, this.posY, this.posZ, 5, 15, 10.0f, 1.0f, 1f, 0.9f, 0.5f);
		}
		this.setDead();
	}
	*/
	
	
	
	
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource dmgsrc = TGDamageSource.causeExplosionDamage(this, this.shooter, DeathType.GORE);
		dmgsrc.goreChance = 1.0f;
		dmgsrc.knockbackMultiplier=3.0f;
		return dmgsrc;
	}
	
	public static class Factory implements IProjectileFactory<RocketProjectile> {

		@Override
		public DamageType getDamageType() {
			return DamageType.EXPLOSION;
		}

		@Override
		public RocketProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed,
				int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
				boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new RocketProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,radius,gravity);
		}
		
	}

}
