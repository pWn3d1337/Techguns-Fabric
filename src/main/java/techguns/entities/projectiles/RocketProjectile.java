package techguns.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.TGPacketsS2C;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.damagesystem.TGExplosion;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;

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
	

	@Override
	protected void onHitEffect(LivingEntity livingEntity) {
		this.explodeRocket();
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		this.explodeRocket();
	}

	protected void explodeRocket(){
		if (!this.world.isClient){
			//TGPackets.network.sendToAllAround(new PacketSpawnParticle("RocketExplosion", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
			//TGPackets.network.sendToAllAround(new PacketSpawnParticle("TestFX", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
		
			TGPacketsS2C.sendToAllAroundEntity(new PacketSpawnParticle("RocketExplosion", this.getX(), this.getY(), this.getZ()), this, 50.0f);
			
			TGExplosion explosion = new TGExplosion(world, this.shooter, this, this.getX(), this.getY(), this.getZ(), this.damage, this.damageMin, this.damageDropStart,this.damageDropEnd, this.blockdamage?0.25:0.0);
			
			explosion.doExplosion(true);
		}else {
			//Techguns.proxy.createLightPulse(this.posX, this.posY, this.posZ, 5, 15, 10.0f, 1.0f, 1f, 0.9f, 0.5f);
		}
		this.markForRemoval();
	}
		
	
	
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
