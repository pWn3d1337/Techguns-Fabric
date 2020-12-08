package techguns.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.TGPacketsS2C;
import techguns.TGSounds;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IChargedProjectileFactory;
import techguns.packets.PacketSpawnParticle;

public class ChainsawProjectile extends GenericProjectile {

	public static final byte PROJECTILE_TYPE_CHAINSAW = 0;
	public static final byte PROJECTILE_TYPE_POWERHAMMER = 1;

	public ChainsawProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter,
			CompoundTag data) {
		super(T, world, shooter, data);
	}

	public ChainsawProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage,
			float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
			boolean blockdamage, EnumBulletFirePos firePos) {
		super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
	}

	public ChainsawProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public ChainsawProjectile(World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos) {
		this(TGEntities.CHAINSAW_PROJECTILE, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
	}



	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = new TGDamageSource("chainsaw", this, this.shooter, DamageType.PHYSICAL, DeathType.GORE);
    	src.goreChance=1.0f;
    	src.armorPenetration=this.penetration;
    	return src;
	}

	@Override
	protected void onHitEffect(LivingEntity livingEntity, EntityHitResult hitResult) {
		if(!this.world.isClient) {
	    	double x = hitResult.getPos().x;
	    	double y = hitResult.getPos().y;
	    	double z = hitResult.getPos().z;
			this.world.playSound(x, y, z, TGSounds.POWERHAMMER_IMPACT, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
			TGPacketsS2C.sendToAllTracking(new PacketSpawnParticle("PowerhammerImpact",x,y,z), livingEntity, true);
		}
	}
	
	@Override
	protected void doImpactEffects(BlockHitResult rayTraceResult) {
		if(!this.world.isClient) {
	    	double x = rayTraceResult.getPos().x;
	    	double y = rayTraceResult.getPos().y;
	    	double z = rayTraceResult.getPos().z;
			this.world.playSound(x, y, z, TGSounds.POWERHAMMER_IMPACT, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
			TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("PowerhammerImpact",x,y,z), world, rayTraceResult.getPos(), 32.0);
		}
	}

	
	@Override
	protected float getDragInWater() {
		//regular slowdown
		return 0.99f;
	}

	public static class Factory implements IChargedProjectileFactory<ChainsawProjectile> {
		protected byte projectile_type;

		public Factory(byte projectile_type) {
			this.projectile_type = projectile_type;
		}

		@Override
		public ChainsawProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new ChainsawProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.PHYSICAL;
		}

		@Override
		public ChainsawProjectile createChargedProjectile(World world, LivingEntity p, float damage, float speed,
				int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
				boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity, float charge,
				int ammoConsumed) {
			return new ChainsawProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public byte getProjectileType() {
			return projectile_type;
		}
	}
}
