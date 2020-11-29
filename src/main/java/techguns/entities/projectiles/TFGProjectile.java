package techguns.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.TGPacketsS2C;
import techguns.TGSounds;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.sounds.TGSoundCategory;
import techguns.damagesystem.TGDamageSource;
import techguns.damagesystem.TGExplosion;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IChargedProjectileFactory;
import techguns.packets.PacketPlaySound;
import techguns.packets.PacketSpawnParticle;


public class TFGProjectile extends GenericProjectile{

	public float size;

	
	public TFGProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
		if (world.isClient) {	
			this.createTrailFX();
		}
	}

	public TFGProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos, double gravity, float size) {
		super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
		this.size=size;
		this.gravity=gravity;
	}

	public TFGProjectile(World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos, double gravity, float size) {
		this(TGEntities.TFG_PROJECTILE, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos, gravity, size);
	}
	
	public TFGProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter, CompoundTag data) {
		super(T, world, shooter, data);
		if (world.isClient) {	
			this.createTrailFX();
		}
	}

	protected void createTrailFX() {
		ClientProxy.get().createFXOnEntity("TFGTrail", this, this.size*0.75f );
	}
	
	@Override
	protected void onHitEffect(LivingEntity livingEntity, EntityHitResult hitResult) {
		this.explode();
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		this.explode();
	}

	protected void explode(){
		if (!this.world.isClient){
			float exp_dmgMax = this.damage*0.66f * size;
			float exp_dmgMin = this.damage*0.33f * size;
			float exp_r1 = size*1.0f;
			float exp_r2 = size*2.0f;
			
			TGPacketsS2C.sendToAllAroundEntity(new PacketSpawnParticle("TFGExplosion", this.getX(), this.getY(), this.getZ(), size*0.75f), this, 100.0f);
			
			
			TGExplosion explosion = new TGExplosion(world, this.shooter, this, this.getX(), this.getY(), this.getZ(), exp_dmgMax, exp_dmgMin, exp_r1, exp_r2, this.blockdamage?0.5:0.0);		
			explosion.blockDropChance = 0.1f;			
			explosion.doExplosion(false);

			TGPacketsS2C.sendToAllAroundEntity(new PacketPlaySound(TGSounds.TFG_EXPLOSION, this, 5.0f, 1.0f, false, false, false, TGSoundCategory.EXPLOSION), this, 100.0f);
			
			if (this.size > 3.0f) {
				TGPacketsS2C.sendToAllAroundEntity(new PacketPlaySound(TGSounds.TFG_EXPLOSION, this, 5.0f, 1.0f, false, false, false, TGSoundCategory.EXPLOSION), this, 200.0f);
			}
			
		}else {
			//Techguns.proxy.createLightPulse(this.posX, this.posY, this.posZ, 5, 15, 5f+(size), 1f+(size*0.5f), 0.5f, 1.0f, 0.5f);
		}
		this.markForRemoval();
	}

	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource dmgsrc = TGDamageSource.causeEnergyDamage(this, this.shooter, DeathType.GORE);
		dmgsrc.goreChance = 1.0f;
		dmgsrc.knockbackMultiplier=3.0f;
		return dmgsrc;
	}
	
	
	@Override
	protected void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		tag.putFloat("size", this.size);
	}

	@Override
	protected void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		this.size = tag.getFloat("size");
	}
		
	@Override
	public void getAdditionalSpawnData(CompoundTag tag) {
		super.getAdditionalSpawnData(tag);
		tag.putFloat("size", this.size);
	}
	
	@Override
	protected void parseAdditionalData(CompoundTag tag) {
		super.parseAdditionalData(tag);
		System.out.println("Size = "+tag.getFloat("size"));
		this.size = tag.getFloat("size");
	}
	
//	@Override
//	public void writeSpawnData(ByteBuf buffer) {
//		super.writeSpawnData(buffer);
//		buffer.writeFloat(this.size);
//	}
//
//	@Override
//	public void readSpawnData(ByteBuf additionalData) {
//		super.readSpawnData(additionalData);
//		this.size=additionalData.readFloat();
//		Techguns.proxy.createFXOnEntity("TFGTrail", this, this.size*0.75f);
//	}
	
	public static class Factory implements IChargedProjectileFactory<TFGProjectile> {

		@Override
		public DamageType getDamageType() {
			return DamageType.ENERGY;
		}
		

		@Override
		public TFGProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed,
				int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
				boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return this.createChargedProjectile(world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos, radius, gravity, 0f, 1);
		}

		@Override
		public TFGProjectile createChargedProjectile(World world, LivingEntity p, float damage, float speed, int TTL,
				float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
				boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity, float charge,
				int ammoConsumed) {
			
			float size = 1.0f+(charge*3.0f);
			if (charge >= 1.0f) size += 1.0f;
			
			TFGProjectile proj = new TFGProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,gravity, size); //ammoConsumed);
			
			//proj.size = 1.0f+(charge*3.0f);
			//if (charge >= 1.0f) proj.size += 1.0f;
			
			return proj;
		}
		
	}
	
//	@Optional.Method(modid="albedo")
//	@Override
//	public Light provideLight() {
//		return Light.builder()
//				.pos(MathUtil.getInterpolatedEntityPos(this))
//				.color(0.5f,1.0f, 0.5f)
//				.radius(2f+(size*0.5f))
//				.build();
//	}
}