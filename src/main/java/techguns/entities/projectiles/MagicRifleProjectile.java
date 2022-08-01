package techguns.entities.projectiles;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.TGPacketsS2C;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IChargedProjectileFactory;
import techguns.items.guns.ammo.AmmoType;
import techguns.items.guns.ammo.AmmoTypes;
import techguns.items.guns.ammo.AmmoVariant;
import techguns.packets.PacketSpawnParticle;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MagicRifleProjectile extends GenericProjectile{

    public static final byte TYPE_DEFAULT = 0;
    public static final byte TYPE_FIRE = 1;
    public static final byte TYPE_LIGHTNING = 2;

    static HashMap<Byte, MagicRifleProjectileType> ProjectileTypeMap = new HashMap<Byte, MagicRifleProjectileType>();
    static {
        ProjectileTypeMap.put(TYPE_DEFAULT, new MagicRifleProjectileType());
        ProjectileTypeMap.put(TYPE_FIRE, new MagicRifleProjectileTypeFire());
        ProjectileTypeMap.put(TYPE_LIGHTNING, new MagicRifleProjectileTypeLightning()); //TODO
    }

    public float chargeAmount = 0.0f;

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


    public static class MagicRifleProjectileType {

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
            this.createScaledProjectileTrail(proj,"GaussProjectileTrail");
        }

        protected void createScaledProjectileTrail(MagicRifleProjectile proj, String fxlist) {
            float scale = 0.5f + proj.chargeAmount * 0.5f;
            ClientProxy.get().createFXOnEntity(fxlist, proj, scale);
        }
    }

    static class MagicRifleProjectileTypeFire extends MagicRifleProjectileType {
        public void handleEntityHit(MagicRifleProjectile proj, LivingEntity livingEntity, EntityHitResult entityHitResult) {
            int fireTime = 3 + (int) proj.chargeAmount * 7;
            if(!livingEntity.isFireImmune()) {
                livingEntity.setOnFireFor(fireTime);
            }
        }
        public void handleBlockHit(MagicRifleProjectile proj, BlockHitResult blockHitResult) {
            if (proj.blockdamage) {
                burnBlocks(proj.world, blockHitResult, 1.0f);
            }
        }
        public void handleImpactEffects(MagicRifleProjectile proj, double x, double y, double z, float pitch, float yaw, BlockSoundGroup blockSoundGroup) {
            GenericProjectile.handleBulletImpact(proj.world, x, y, z, pitch, yaw, blockSoundGroup,true);
        }
        public void createFXTrail(MagicRifleProjectile proj) {
            this.createScaledProjectileTrail(proj,"FlamethrowerTrail");
        }

    }
    static class MagicRifleProjectileTypeLightning extends MagicRifleProjectileType {
        public void handleBlockHit(MagicRifleProjectile proj, BlockHitResult blockHitResult) {
            if (proj.chargeAmount > 0.5f) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                //if (world.isThundering() && projectile instanceof TridentEntity && ((TridentEntity)projectile).hasChanneling() && world.isSkyVisible(blockPos = hit.getBlockPos())) {
                LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(proj.world);
                lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos.up()));
                Entity entity = proj.getOwner();
                lightningEntity.setChanneler(entity instanceof ServerPlayerEntity ? (ServerPlayerEntity) entity : null);
                proj.world.spawnEntity(lightningEntity);
                proj.world.playSound(null, blockPos, SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.WEATHER, 5.0f, 1.0f);
            }
        }

//        public void createFXTrail(MagicRifleProjectile proj) {
//            this.createScaledProjectileTrail(proj,"FlamethrowerTrail");
//        }

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
