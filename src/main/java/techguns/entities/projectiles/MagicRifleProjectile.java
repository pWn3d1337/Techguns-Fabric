package techguns.entities.projectiles;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.TGPacketsS2C;
import techguns.TGSounds;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.damagesystem.TGExplosion;
import techguns.damagesystem.TGExplosionIgnoreBlocks;
import techguns.deatheffects.EntityDeathUtils;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IChargedProjectileFactory;
import techguns.items.guns.ammo.AmmoType;
import techguns.items.guns.ammo.AmmoTypes;
import techguns.items.guns.ammo.AmmoVariant;
import techguns.packets.PacketPlaySound;
import techguns.packets.PacketSpawnParticle;
import techguns.sounds.TGSoundCategory;
import techguns.util.MathUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MagicRifleProjectile extends GenericProjectile{

    public static final byte TYPE_DEFAULT = 0;
    public static final byte TYPE_FIRE = 1;
    public static final byte TYPE_LIGHTNING = 2;

    static HashMap<Byte, MagicRifleProjectileType> ProjectileTypeMap = new HashMap<Byte, MagicRifleProjectileType>();
    static {
        ProjectileTypeMap.put(TYPE_DEFAULT, new MagicRifleProjectileTypeArcane());
        ProjectileTypeMap.put(TYPE_FIRE, new MagicRifleProjectileTypeFire());
        ProjectileTypeMap.put(TYPE_LIGHTNING, new MagicRifleProjectileTypeLightning()); //TODO
    }

    public float chargeAmount = 0.0f;
    public int postImpactTicks = 0;

    public MagicRifleProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    protected MagicRifleProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos) {
        super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
    }

    public MagicRifleProjectile(World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos) {
        super(world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
    }

    public MagicRifleProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter) {
        super(T, world, shooter);
    }
    
    protected MagicRifleProjectileType getProjType() {
        return MagicRifleProjectile.ProjectileTypeMap.get(this.getProjectileType());
    }

    protected TGDamageSource getProjectileDamageSource() {
        return this.getProjType().getDamageSource(this, this.getOwner());
    }

    @Override
    public void tick() {
        //System.out.println("post_impact_ticks: "+this.postImpactTicks);
        if (this.postImpactTicks-- > 0) {
            getProjType().handlePostImpactTick(this);
            if (this.postImpactTicks <= 0) {
                super.markForRemoval();
            }
        }else {
            super.tick();
        }
    }

    @Override
    protected void markForRemoval() {
        if (!getProjType().handleRemoval(this)) {
            super.markForRemoval();
        }
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        getProjType().handleBlockHit(this, blockHitResult);
    }

    protected void onHitEffect(LivingEntity livingEntity, EntityHitResult entityHitResult) {
       super.onHitEffect(livingEntity, entityHitResult);
       getProjType().handleEntityHit(this, livingEntity, entityHitResult);
    }

    protected void doImpactEffectForType(double x, double y, double z, float pitch, float yaw, BlockSoundGroup blockSoundGroup){
       getProjType().handleImpactEffects(this, x, y, z, pitch, yaw, blockSoundGroup);
    }

    @Override
    public void clientInitializeFinal() {
        getProjType().createFXTrail(this);
    }


    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putFloat("chargeAmount", (float)this.chargeAmount);
    }

    @Override
    public void getAdditionalSpawnData(NbtCompound data) {
        super.getAdditionalSpawnData(data);
        data.putFloat("chargeAmount", (float)this.chargeAmount);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.chargeAmount = tag.getFloat("chargeAmount");
    }

    @Override
    public void parseAdditionalData(NbtCompound data) {
        super.parseAdditionalData(data);
        this.chargeAmount = data.getByte("chargeAmount");
    }


    public abstract static class MagicRifleProjectileType {
        public TGDamageSource getDamageSource(MagicRifleProjectile proj, Entity shooter) {
            return TGDamageSource.causeBulletDamage(proj, shooter, EntityDeathUtils.DeathType.DEFAULT);
        }
        public void handlePostImpactTick(MagicRifleProjectile proj) {
            return;
        }

        public boolean handleRemoval(MagicRifleProjectile proj) {
            return false;
        }

        public void handleEntityHit(MagicRifleProjectile proj, LivingEntity livingEntity, EntityHitResult entityHitResult) {
            return;
        }
        public void handleBlockHit(MagicRifleProjectile proj, BlockHitResult blockHitResult) {
            return;
        }
        public void handleImpactEffects(MagicRifleProjectile proj, double x, double y, double z, float pitch, float yaw, BlockSoundGroup blockSoundGroup) {
            GenericProjectile.handleBulletImpact(proj.world, x, y, z, pitch, yaw, blockSoundGroup,false);
        }
        public void createFXTrail(MagicRifleProjectile proj) {
            return;
        }

        protected void createScaledProjectileTrail(MagicRifleProjectile proj, String fxlist) {
            float scale = 0.5f + proj.chargeAmount * 2.5f;
            ClientProxy.get().createFXOnEntity(fxlist, proj, scale);
        }
    }

    static class MagicRifleProjectileTypeArcane extends MagicRifleProjectileType {

        public TGDamageSource getDamageSource(MagicRifleProjectile proj, Entity shooter) {
            return TGDamageSource.causeEnergyDamage(proj, shooter, EntityDeathUtils.DeathType.GORE);
        }

        public void handleEntityHit(MagicRifleProjectile proj, LivingEntity livingEntity, EntityHitResult entityHitResult) {
            if (!proj.world.isClient)
                createExplosion(proj, livingEntity);
        }
        public void handleBlockHit(MagicRifleProjectile proj, BlockHitResult blockHitResult) {
            if (!proj.world.isClient)
                createExplosion(proj, null);
        }
        public void createFXTrail(MagicRifleProjectile proj) {
            this.createScaledProjectileTrail(proj,"MagicRifleProjectileTrail_Arcane");
        }

        private void createExplosion(MagicRifleProjectile proj, LivingEntity targetHit) {
            float size =  0.5f + proj.chargeAmount * 2.5f;
            float exp_dmgMax = proj.damage*0.66f * size;
            float exp_dmgMin = proj.damage*0.33f * size;
            float exp_r1 = size*1.0f;
            float exp_r2 = size*2.0f;

            List<Entity> excludedEntities;
            if (targetHit != null) {
                excludedEntities = new ArrayList<>(Arrays.asList(proj, targetHit));
            } else {
                excludedEntities = new ArrayList<>(Arrays.asList(proj));
            }

            TGDamageSource dmgSrc = proj.getProjectileDamageSource();

            TGPacketsS2C.sendToAllAroundEntity(new PacketSpawnParticle("MagicRifleExplosion_Arcane", proj.getX(), proj.getY(), proj.getZ(), size), proj, 100.0f);
            TGExplosionIgnoreBlocks.createExplosion(proj.world, proj.getX(), proj.getY(), proj.getZ(), dmgSrc, exp_dmgMax, exp_dmgMin, exp_r1, exp_r2, excludedEntities);
            TGPacketsS2C.sendToAllAroundEntity(new PacketPlaySound(TGSounds.TFG_EXPLOSION, proj, 5.0f, 1.0f, false, false, false, TGSoundCategory.EXPLOSION), proj, 100.0f);
        }

    }

    static class MagicRifleProjectileTypeFire extends MagicRifleProjectileType {
        public TGDamageSource getDamageSource(MagicRifleProjectile proj, Entity shooter) {
            return TGDamageSource.causeFireDamage(proj, shooter, EntityDeathUtils.DeathType.LASER);
        }
        public void handleEntityHit(MagicRifleProjectile proj, LivingEntity livingEntity, EntityHitResult entityHitResult) {
            int fireTime = 3 + (int) proj.chargeAmount * 7;
            if(!livingEntity.isFireImmune()) {
                livingEntity.setOnFireFor(fireTime);
            }
            if (!proj.world.isClient)
                createExplosion(proj, livingEntity);
        }
        public void handleBlockHit(MagicRifleProjectile proj, BlockHitResult blockHitResult) {
            if (proj.blockdamage) {
                burnBlocks(proj.world, blockHitResult, 1.0f);
            }
            if (!proj.world.isClient)
                createExplosion(proj, null);
        }
        public void handleImpactEffects(MagicRifleProjectile proj, double x, double y, double z, float pitch, float yaw, BlockSoundGroup blockSoundGroup) {
            GenericProjectile.handleBulletImpact(proj.world, x, y, z, pitch, yaw, blockSoundGroup,true);
        }

        protected void createScaledProjectileTrail(MagicRifleProjectile proj, String fxlist) {
            float scale = 0.5f + proj.chargeAmount * 1.5f;
            ClientProxy.get().createFXOnEntity(fxlist, proj, scale);
        }
        public void createFXTrail(MagicRifleProjectile proj) {
            this.createScaledProjectileTrail(proj,"MagicRifleProjectileTrail_Fire");
        }

        private void createExplosion(MagicRifleProjectile proj, LivingEntity targetHit) {
            float size =  0.5f + proj.chargeAmount * 1.5f;
            float exp_dmgMax = proj.damage*0.5f * size;
            float exp_dmgMin = proj.damage*0.25f * size;
            float exp_r1 = size*1.75f;
            float exp_r2 = size*3.5f;

//            List<Entity> excludedEntities;
//            if (targetHit != null) {
//                excludedEntities = new ArrayList<>(Arrays.asList(proj, targetHit));
//            } else {
//                excludedEntities = new ArrayList<>(Arrays.asList(proj));
//            }

            int fireTime = 3 + (int) proj.chargeAmount * 7;

            if (proj.chargeAmount > 0.8) {
                TGPacketsS2C.sendToAllAroundEntity(new PacketSpawnParticle("MagicRifleExplosion_Fire_Charged", proj.getX(), proj.getY(), proj.getZ(), size), proj, 100.0f);
            }else {
                TGPacketsS2C.sendToAllAroundEntity(new PacketSpawnParticle("MagicRifleExplosion_Fire", proj.getX(), proj.getY(), proj.getZ(), size), proj, 100.0f);
            }
            TGExplosion explosion = new TGExplosion(proj.world, proj.getOwner(), proj, proj.getX(), proj.getY(), proj.getZ(), exp_dmgMax, exp_dmgMin, exp_r1, exp_r2, 0.0f);
            if (proj.blockdamage) {
                explosion.setIncendiary(true, true, 1.0, fireTime);
            }else {
                explosion.setIncendiary(false, true, 1.0, fireTime);
            }
            explosion.setDmgSrc(proj.getProjectileDamageSource());
            explosion.doExplosion(false);

            TGPacketsS2C.sendToAllAroundEntity(new PacketPlaySound(TGSounds.TFG_EXPLOSION, proj, 5.0f, 1.0f, false, false, false, TGSoundCategory.EXPLOSION), proj, 100.0f);
        }

    }
    static class MagicRifleProjectileTypeLightning extends MagicRifleProjectileType {
        public TGDamageSource getDamageSource(MagicRifleProjectile proj, Entity shooter) {
            return TGDamageSource.causeLightningDamage(proj, shooter, EntityDeathUtils.DeathType.GORE);
        }
        public void handleEntityHit(MagicRifleProjectile proj, LivingEntity livingEntity, EntityHitResult entityHitResult) {
            //if (proj.chargeAmount > 0.25f) {
                BlockPos blockPos = getFreeBlockPos(proj.world, (float) livingEntity.getX(), (float) livingEntity.getY(), (float) livingEntity.getZ());
                if (blockPos != null) {
                    createLightning(proj, blockPos);
                }
            //}
        }
        public void handleBlockHit(MagicRifleProjectile proj, BlockHitResult blockHitResult) {
            //if (proj.chargeAmount > 0.25f) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                createLightning(proj, blockPos);
            //}
        }

        public boolean handleRemoval(MagicRifleProjectile proj) {
            if (proj.ticksToLive > 0 && proj.chargeAmount > 0.25f) {
                proj.postImpactTicks = 10 + (int)(proj.chargeAmount* 30);
                //System.out.println("set post_impact_ticks: "+proj.postImpactTicks);
                return true;
            }
            return false;
        }
        public void handlePostImpactTick(MagicRifleProjectile proj) {
            if (proj.postImpactTicks % 8 == 0) { //Spawn lightning every 8 ticks
                BlockPos blockPos = getFreeBlockPos(proj.world, (float) proj.getX(), (float) proj.getY(), (float) proj.getZ());
                if (blockPos != null) {
                    createLightning(proj, blockPos);
                }
            }
            if (proj.postImpactTicks % 4 == 0 && proj.chargeAmount*0.5f > proj.world.random.nextFloat()) {
                Vec2f pos = MathUtil.polarOffsetXZ(proj.getX(), proj.getZ(),
                        proj.world.random.nextFloat() * proj.chargeAmount*5.0f,
                        proj.random.nextFloat()*(float)(Math.PI*2.0));
                BlockPos blockPos = getFreeBlockPos(proj.world, pos.x, (float) proj.getY(), pos.y);
                if (blockPos != null) {
                    createLightning(proj, blockPos);
                }
            }
        }

        private void createLightning(MagicRifleProjectile proj, BlockPos blockPos) {
            //if (world.isThundering() && projectile instanceof TridentEntity && ((TridentEntity)projectile).hasChanneling() && world.isSkyVisible(blockPos = hit.getBlockPos())) {
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(proj.world);
            lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos.up()));
            Entity entity = proj.getOwner();
            lightningEntity.setChanneler(entity instanceof ServerPlayerEntity ? (ServerPlayerEntity) entity : null);
            proj.world.spawnEntity(lightningEntity);
            proj.world.playSound(null, blockPos, SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.WEATHER, 5.0f, 1.0f);
        }

        public void createFXTrail(MagicRifleProjectile proj) {
            this.createScaledProjectileTrail(proj,"MagicRifleProjectileTrail_Lightning");
        }

    }

    public static BlockPos getFreeBlockPos(World world, float x, float y, float z) {
        // Get nearest blockpos that is not Air and has Air above it
        int a = MathHelper.floor(x);
        int b = MathHelper.floor(y);
        int c = MathHelper.floor(z);
        BlockPos blockPos = new BlockPos(a, b, c);

        if (!world.getBlockState(blockPos).isAir() && (world.getBlockState(blockPos.up()).isAir() || world.getBlockState(blockPos.up()).getBlock() == Blocks.FIRE)) {
            return blockPos;
        }

        //check down
        int checkblocks = 3;
        for (int i = 0; i < checkblocks; i++) {
            BlockPos bpos = blockPos.down(i+1);
            if (!world.getBlockState(bpos).isAir() && (world.getBlockState(bpos.up()).isAir() || world.getBlockState(bpos.up()).getBlock() == Blocks.FIRE)) {
                return bpos;
            }
        }
        //check up
        for (int i = 0; i < checkblocks; i++) {
            BlockPos bpos = blockPos.up(i+1);
            if (!world.getBlockState(bpos).isAir() && (world.getBlockState(bpos.up()).isAir() || world.getBlockState(bpos.up()).getBlock() == Blocks.FIRE)) {
                return bpos;
            }
        }
        return null;
    }

//
//    public enum MagicProjectileType {
//        DEFAULT(0, DamageType.PROJECTILE, EntityDeathUtils.DeathType.GORE, TGDamageSource::causeBulletDamage, GenericProjectile::handleBulletImpactDefault), //GenericProjectile::handleBulletImpactDefault);
//        FIRE(1, DamageType.FIRE, EntityDeathUtils.DeathType.DEFAULT, TGDamageSource::causeFireDamage, (entity, hitResult)->{
//            if(!entity.isFireImmune()) {
//                entity.setOnFireFor(3);
//            }
//        }, GenericProjectile::handleBulletImpactIncendiary, (projectile)->{}),
//        LIGHTNING(2, DamageType.EXPLOSION, EntityDeathUtils.DeathType.GORE, TGDamageSource::causeExplosionDamage, GenericProjectile::handleBulletImpactDefault);
//
//
//        public final byte id;
//        protected DamageType damageType;
//        protected EntityDeathUtils.DeathType deathType;
//        protected DamageSourceGetter dmgSourceGetter;
//        protected BiConsumer<LivingEntity, EntityHitResult> onHitCode;
//        protected ImpactFXCode impactFXCode;
//        protected Consumer<GenericProjectile> clientTrailFXCode;
//
//        MagicProjectileType(int id, DamageType damageType, EntityDeathUtils.DeathType deathType, DamageSourceGetter dmgSourceGetter) {
//            this.id = (byte)id;
//            this.damageType = damageType;
//            this.dmgSourceGetter = dmgSourceGetter;
//            this.deathType = deathType;
//            this.onHitCode = (entity, hitres)-> {};
//            this.impactFXCode = (world, x, y, z, pitch, yaw, soundGroup) -> {};
//            this.clientTrailFXCode = (entity)->{};
//        };
//
//        MagicProjectileType(int id, DamageType damageType, EntityDeathUtils.DeathType deathType, DamageSourceGetter dmgSourceGetter,
//                              BiConsumer<LivingEntity, EntityHitResult> onHitCode, ImpactFXCode impactFXCode) {
//            this(id, damageType, deathType, dmgSourceGetter);
//            this.onHitCode = onHitCode;
//            this.impactFXCode = impactFXCode;
//        }
//
//        MagicProjectileType(int id, DamageType damageType, EntityDeathUtils.DeathType deathType, DamageSourceGetter dmgSourceGetter,
//                              ImpactFXCode impactFXCode) {
//            this(id, damageType, deathType, dmgSourceGetter);
//            this.impactFXCode = impactFXCode;
//        }
//
//        MagicProjectileType(int id, DamageType damageType, EntityDeathUtils.DeathType deathType, DamageSourceGetter dmgSourceGetter,
//                              BiConsumer<LivingEntity, EntityHitResult> onHitCode, ImpactFXCode impactFXCode,
//                              Consumer<GenericProjectile> clientTrailFXCode) {
//            this(id,damageType,deathType, dmgSourceGetter,onHitCode,impactFXCode);
//            this.clientTrailFXCode = clientTrailFXCode;
//        }
//
//    }


    public static class Factory implements IChargedProjectileFactory<MagicRifleProjectile> {

        protected byte projectileType;

        public Factory(){};
        public Factory( byte id) {
            this.projectileType = id;
        }

        @Override
        public MagicRifleProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
                                                 float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
            return this.createChargedProjectile(world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos, radius, gravity, 0f, 1);
        }

        @Override
        public DamageType getDamageType() {
            return DamageType.PROJECTILE;
        }

        @Override
        public MagicRifleProjectile createChargedProjectile(World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
                                                        float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity, float charge, int ammoConsumed) {
            MagicRifleProjectile proj = new MagicRifleProjectile(TGEntities.MAGIC_RIFLE_PROJECTILE, world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
            proj.chargeAmount = charge;
            return proj;
        }

        public byte getProjectileType() {
            return this.projectileType;
        }

    }
}
