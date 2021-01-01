package techguns.entities.projectiles;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import techguns.*;
import techguns.api.damagesystem.DamageType;
import techguns.blocks.BlockBioBlob;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.damagesystem.TGExplosion;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IChargedProjectileFactory;
import techguns.packets.PacketPlaySound;
import techguns.packets.PacketSpawnParticle;
import techguns.sounds.TGSoundCategory;

import java.time.Clock;

public class BioGunProjectile extends GenericProjectile{

	public int level;


	public BioGunProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
		if (world.isClient) {
			this.createTrailFX();
		}
	}

	protected BioGunProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos, double gravity, int level) {
		super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
		this.gravity=gravity;
		this.level=level;
	}
	
	public BioGunProjectile(World world, LivingEntity p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos, double gravity, int level) {
		this(TGEntities.BIOGUN_PROJECTILE, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos, gravity, level);
	}
	
	public BioGunProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter, CompoundTag data) {
		super(T, world, shooter, data);
		if (world.isClient) {
			this.createTrailFX();
		}
	}

	protected void createTrailFX() {
		ClientProxy.get().createFXOnEntity("BioGunTrail", this);
	}
	
	@Override
	protected void onHitEffect(LivingEntity livingEntity, EntityHitResult hitResult) {
		 livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 3, false, true));
	}
	 
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causePoisonDamage(this, this.getOwner(), DeathType.BIO);
    	src.goreChance = 1.0f;
    	src.armorPenetration = this.penetration;
    	src.setNoKnockback();
    	return src;
	}

	@Override
	protected void doImpactEffects(BlockHitResult rayTraceResult) {
		if(!this.world.isClient) {
			double x = rayTraceResult.getPos().x;
			double y = rayTraceResult.getPos().y;
			double z = rayTraceResult.getPos().z;

			float pitch = 0.0f;
			float yaw = 0.0f;

			if (rayTraceResult.getSide() == Direction.UP) {
				pitch = -90.0f;
			} else if (rayTraceResult.getSide() == Direction.DOWN) {
				pitch = 90.0f;
			} else {
				yaw = rayTraceResult.getSide().getHorizontal() * 90.0f;
			}


			TGPacketsS2C.sentToAllTrackingPos(new PacketPlaySound(TGSounds.BIOGUN_IMPACT, this, 1.0f, 1.0f, false, false, false, true, TGSoundCategory.EXPLOSION), this.world, rayTraceResult.getBlockPos());
			TGPacketsS2C.sentToAllTrackingPos(new PacketSpawnParticle("biogunImpact", x, y, z, 0f, 0f, 0f, pitch, yaw, 1.0f), this.world, rayTraceResult.getBlockPos());
		}
	}

	private void hitExistingBlob(BlockState statehit, BlockPos blockPos){

		int blob_size = statehit.get(BlockBioBlob.SIZE);

		int new_size = blob_size + this.level;

		if (new_size <= 2){
			BlockState newState = statehit.with(BlockBioBlob.SIZE, new_size);
			world.setBlockState(blockPos, newState);
			((BlockBioBlob)TGBlocks.BIOBLOB).scheduleTick(this.world, blockPos, this.world.random);
		} else {
			//KABOOM

			float radius = 3.0f;

			TGDamageSource dmgSrc = TGDamageSource.causePoisonDamage(null, this.getOwner(), DeathType.BIO);
			dmgSrc.goreChance=1.0f;
			dmgSrc.armorPenetration=0.35f;

			TGExplosion explosion = new TGExplosion(world, this.getOwner(), null, blockPos.getX()+0.5, blockPos.getY()+0.5, blockPos.getZ()+0.5, 30, 15, radius, radius*1.5f,0.0f);
			explosion.setDmgSrc(dmgSrc);

			world.setBlockState(blockPos, Blocks.AIR.getDefaultState());

			explosion.doExplosion(false);
			this.world.playSound((PlayerEntity) null, blockPos, TGSounds.DEATH_BIO, SoundCategory.BLOCKS, 4.0F, 1.0F);

			if(!this.world.isClient){
				TGPacketsS2C.sentToAllTrackingPos(new PacketSpawnParticle("bioblobExplosion", blockPos.getX()+0.5,blockPos.getY()+0.5, blockPos.getZ()+0.5), world, blockPos);
			}
		}
	}

	/**
	 * just to access the protected constructor without a player
	 */
	private static class ItemPlacementContextAccessor extends ItemPlacementContext {
		protected ItemPlacementContextAccessor(World world, @Nullable PlayerEntity playerEntity, Hand hand, ItemStack itemStack, BlockHitResult blockHitResult) {
			super(world, playerEntity, hand, itemStack, blockHitResult);
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		BlockPos hitpos = blockHitResult.getBlockPos();
		BlockState statehit = this.world.getBlockState(hitpos);
		if(statehit.getBlock() == TGBlocks.BIOBLOB){

			this.hitExistingBlob(statehit, hitpos);
		} else {
			if (!this.world.isClient){

				BlockPos blobPos = hitpos.offset(blockHitResult.getSide());
				BlockState state =world.getBlockState(blobPos);
				if(state.getBlock() == TGBlocks.BIOBLOB){
					this.hitExistingBlob(state, blobPos);
				} else {
					BlockHitResult newHitRes = new BlockHitResult(blockHitResult.getPos(), blockHitResult.getSide(), blobPos,false);
					if (this.blockdamage && state.canReplace(new ItemPlacementContextAccessor(this.world, null, Hand.MAIN_HAND, new ItemStack(TGItems.BIOBLOB), newHitRes))) {

						BlockState newState = TGBlocks.BIOBLOB.getDefaultState().with(BlockBioBlob.FACING, blockHitResult.getSide().getOpposite()).with(BlockBioBlob.SIZE, this.level-1);
						this.world.setBlockState(blobPos, newState);
						((BlockBioBlob) TGBlocks.BIOBLOB).scheduleTick(this.world, blobPos, this.world.random);
					}
				}

			}
		}

		super.onBlockHit(blockHitResult);
	}

	@Override
	public void tick() {
		super.tick();
		
		Vec3d velocity = this.getVelocity();
        double d = velocity.x;
        double e = velocity.y;
        double g = velocity.z;

        double h = this.getX() + d;
        double j = this.getY() + e;
        double k = this.getZ() + g;
		
		if (this.isTouchingWater())
        {
			 for(int o = 0; o < 4; ++o) {
	               this.world.addParticle(ParticleTypes.BUBBLE, h - d * 0.25D, j - e * 0.25D, k - g * 0.25D, d, e, g);
	            }
			 if (this.isSubmergedInWater()) {
				 this.markForRemoval();
			 }
        }
	}


	@Override
	protected void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		tag.putByte("level", (byte)this.level);
	}

	@Override
	public void getAdditionalSpawnData(CompoundTag data) {
		super.getAdditionalSpawnData(data);
		data.putByte("level", (byte)this.level);
	}
	
	@Override
	protected void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		this.level = tag.getByte("level");
	}

	@Override
	public void parseAdditionalData(CompoundTag data) {
		super.parseAdditionalData(data);
		this.level = data.getByte("level");
	}

	public static class Factory implements IChargedProjectileFactory<BioGunProjectile> {

		@Override
		public BioGunProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return this.createChargedProjectile(world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos, radius, gravity, 0f, 1);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.POISON;
		}
		
		@Override
		public BioGunProjectile createChargedProjectile(World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity, float charge, int ammoConsumed) {
			return new BioGunProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,gravity,ammoConsumed);
		}
		
	}
}