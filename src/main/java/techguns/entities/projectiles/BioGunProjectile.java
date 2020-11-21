package techguns.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IChargedProjectileFactory;

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
	protected void onHitEffect(LivingEntity livingEntity) {
		 livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 3, false, true));
	}
	 
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causePoisonDamage(this, this.shooter, DeathType.BIO);
    	src.goreChance = 1.0f;
    	src.armorPenetration = this.penetration;
    	src.setNoKnockback();
    	return src;
	}

	/*@Override
	protected void doImpactEffects(Material mat, RayTraceResult rayTraceResult, SoundType sound) {
		double x = rayTraceResult.hitVec.x;
    	double y = rayTraceResult.hitVec.y;
    	double z = rayTraceResult.hitVec.z;
    	boolean distdelay=true;
    	
    	float pitch = 0.0f;
    	float yaw = 0.0f;
    	if (rayTraceResult.typeOfHit == Type.BLOCK) {
    		if (rayTraceResult.sideHit == EnumFacing.UP) {
    			pitch = -90.0f;
    		}else if (rayTraceResult.sideHit == EnumFacing.DOWN) {
    			pitch = 90.0f;
    		}else {
    			yaw = rayTraceResult.sideHit.getHorizontalAngle();
    		}
    	}else {
    		pitch = -this.rotationPitch;
    		yaw = -this.rotationYaw;
    	}
    	
		this.world.playSound(x, y, z, TGSounds.BIOGUN_IMPACT, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
		Techguns.proxy.createFX("biogunImpact", world, x, y, z, 0.0D, 0.0D, 0.0D, pitch, yaw);
			
	}
	
	
	@Override
	protected void hitBlock(RayTraceResult mop) {
		 IBlockState statehit = this.world.getBlockState(mop.getBlockPos());
		 
		 if(statehit.getBlock() == TGBlocks.BIOBLOB){
			 //System.out.println("Hit Bioblob, increase size");       	
			 
			 TileEntity tile = this.world.getTileEntity(mop.getBlockPos());
			 if(tile!=null && tile instanceof BioBlobTileEnt){
				 
				 ((BioBlobTileEnt)tile).hitBlob(level, this.shooter);
			 }
			 
			 
		 } else {
			 if (!this.world.isRemote){
   
    			 BlockPos blobPos = mop.getBlockPos().offset(mop.sideHit);
    			 
    			 if (this.world.isAirBlock(blobPos)){
    				 
    				 if(this.blockdamage) {
	    				 boolean canPlace = true;
	    				 
	    				 if ( this.shooter instanceof EntityPlayer){
		    				 final BlockEvent.PlaceEvent placeEvent = new BlockEvent.PlaceEvent(BlockSnapshot.getBlockSnapshot(world, blobPos), statehit, (EntityPlayer) this.shooter,EnumHand.MAIN_HAND);
		    				 MinecraftForge.EVENT_BUS.post(placeEvent);
		    				 canPlace = !placeEvent.isCanceled();
	    				 }
	    				 
	    				 if (canPlace){
	    					 IBlockState state = TGBlocks.BIOBLOB.getDefaultState().withProperty(TGBlocks.BIOBLOB.FACING_ALL, mop.sideHit.getOpposite()).withProperty(TGBlocks.BIOBLOB.SIZE, 0);
	    					 int lvl = 0;
		    				 this.world.setBlockState(blobPos, TGBlocks.BIOBLOB.getDefaultState());
		    				 if (this.level>1){
		    					 TileEntity tile = this.world.getTileEntity(blobPos);
		    					 if(tile!=null && tile instanceof BioBlobTileEnt){
		    						 BioBlobTileEnt blob = (BioBlobTileEnt) tile;
		    						 blob.hitBlob(level-1, this.shooter);
		    					 }
		    					 lvl = level-1;
		    				 }
		    				 this.world.setBlockState(blobPos, state.withProperty(TGBlocks.BIOBLOB.SIZE, lvl), 3);
	    				 }
    				 }
    			 } else {
    				 
    				 TileEntity tile = this.world.getTileEntity(blobPos);
        			 if(tile!=null && tile instanceof BioBlobTileEnt){
        				 
        				 ((BioBlobTileEnt)tile).hitBlob(level, this.shooter);
        			 }
    				 
    			 }
    			 
    			 
    		 }
		 }
	
		 super.hitBlock(mop);
	    //this.world.spawnParticle(EnumParticleTypes.SLIME,mop.hitVec.x,mop.hitVec.y,mop.hitVec.z,0.0D, 0.0D, 0.0D);
		//TGPackets.network.sendToAllAround(new PacketSpawnParticle("biogunImpact", mop.hitVec.x,mop.hitVec.y,mop.hitVec.z), TGPackets.targetPointAroundEnt(this, 25.0f));
			
	}*/

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