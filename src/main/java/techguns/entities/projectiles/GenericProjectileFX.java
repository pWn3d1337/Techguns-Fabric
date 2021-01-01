package techguns.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.TGPacketsS2C;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GenericProjectileFX extends GenericProjectile {

    public static final byte PROJECTILE_TYPE_DEATOMIZER = 0;
    public static final byte PROJECTILE_TYPE_ALIENBLASTER = 1;
    public static final byte PROJECTILE_TYPE_NETHERBLASTER = 2;

    public enum ProjectileFXType {
        DEATOMIZER(0, DamageType.PROJECTILE, EntityDeathUtils.DeathType.GORE, 1.0f, TGDamageSource::causeEnergyDamage, (entity, hitres) ->{},
                (world, x, y, z, pitch, yaw, soundGroup)->{
            TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("BlueBlasterExplosion", x, y, z), world, new Vec3d(x,y,z), 32.0f);
        }, (entity) -> ClientProxy.get().createFXOnEntity("BlueBlasterTrail", entity)),

        ALIENBLASTER(1, DamageType.FIRE, EntityDeathUtils.DeathType.DEFAULT, 0f, TGDamageSource::causeFireDamage, (entity, hitres) ->{},
                (world, x, y, z, pitch, yaw, soundGroup)->{
            TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("AlienExplosion", x, y, z), world, new Vec3d(x,y,z), 32.0f);
        }, (entity) -> ClientProxy.get().createFXOnEntity("AlienBlasterTrail", entity)),

        NETHERBLASTER(2, DamageType.FIRE, EntityDeathUtils.DeathType.DEFAULT, 0f, TGDamageSource::causeFireDamage,(entity, hitres)-> {
            if(!entity.isFireImmune()) {
                entity.setOnFireFor(3);
            }
        }, (world, x, y, z, pitch, yaw, soundGroup)->{
            TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("CyberdemonBlasterImpact", x, y, z), world, new Vec3d(x,y,z), 32.0f);
        }, (entity) -> ClientProxy.get().createFXOnEntity("CyberDemonBlasterTrail", entity));

        public final byte id;
        protected DamageType damageType;
        protected EntityDeathUtils.DeathType deathType;
        protected float goreChance;
        protected DamageSourceGetter dmgSourceGetter;
        protected BiConsumer<LivingEntity, EntityHitResult> onHitCode;
        protected ImpactFXCode impactFXCode;
        protected Consumer<GenericProjectile> clientTrailFXCode;

        ProjectileFXType(int id, DamageType damageType, EntityDeathUtils.DeathType deathType, float goreChance, DamageSourceGetter dmgSourceGetter) {
            this.id = (byte)id;
            this.damageType = damageType;
            this.dmgSourceGetter = dmgSourceGetter;
            this.deathType = deathType;
            this.goreChance = goreChance;
            this.onHitCode = (entity, hitres)-> {};
            this.impactFXCode = (world, x, y, z, pitch, yaw, soundGroup) -> {};
            this.clientTrailFXCode = (entity)->{};
        };

        ProjectileFXType(int id, DamageType damageType, EntityDeathUtils.DeathType deathType, float goreChance, DamageSourceGetter dmgSourceGetter,
                              BiConsumer<LivingEntity, EntityHitResult> onHitCode, ImpactFXCode impactFXCode) {
            this(id, damageType, deathType, goreChance, dmgSourceGetter);
            this.onHitCode = onHitCode;
            this.impactFXCode = impactFXCode;
        }

        ProjectileFXType(int id, DamageType damageType, EntityDeathUtils.DeathType deathType, float goreChance, DamageSourceGetter dmgSourceGetter,
                              ImpactFXCode impactFXCode) {
            this(id, damageType, deathType, goreChance, dmgSourceGetter);
            this.impactFXCode = impactFXCode;
        }

        ProjectileFXType(int id, DamageType damageType, EntityDeathUtils.DeathType deathType, float goreChance, DamageSourceGetter dmgSourceGetter,
                              BiConsumer<LivingEntity, EntityHitResult> onHitCode, ImpactFXCode impactFXCode,
                              Consumer<GenericProjectile> clientTrailFXCode) {
            this(id,damageType,deathType, goreChance, dmgSourceGetter,onHitCode,impactFXCode);
            this.clientTrailFXCode = clientTrailFXCode;
        }

    }


    public GenericProjectileFX(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    protected GenericProjectileFX(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos) {
        super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
    }

    public GenericProjectileFX(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter, CompoundTag data) {
        super(T, world, shooter, data);
    }

    @Override
    public void clientInitializeFinal() {
        this.getProjectileTypeEnnum().clientTrailFXCode.accept(this);
    }

    @Override
    protected TGDamageSource getProjectileDamageSource() {
        ProjectileFXType type = this.getProjectileTypeEnnum();
        TGDamageSource src = type.dmgSourceGetter.getDamageSource(this, this.getOwner(), type.deathType);
        src.goreChance = type.goreChance;
        return src;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.getProjectileType() == PROJECTILE_TYPE_ALIENBLASTER && this.blockdamage) {
            burnBlocks(world, blockHitResult, 0.35);
        }
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void doImpactEffectForType(double x, double y, double z, float pitch, float yaw, BlockSoundGroup blockSoundGroup) {
         this.getProjectileTypeEnnum().impactFXCode.handleImpactFX(this.world, x, y, z, pitch, yaw, blockSoundGroup);
    }

    @Override
    protected void onHitEffect(LivingEntity livingEntity, EntityHitResult entityHitResult) {
       this.getProjectileTypeEnum().onHitCode.accept(livingEntity,entityHitResult);
    }

    public ProjectileFXType getProjectileTypeEnnum() {
        if(this.projectileType>=0&& projectileType<ProjectileFXType.values().length) {
            return ProjectileFXType.values()[projectileType];
        }
        return ProjectileFXType.DEATOMIZER;
    }

    public static class Factory implements IProjectileFactory<GenericProjectileFX>{
        protected byte projectileType;

        public Factory(byte projectileType) {
            this.projectileType = projectileType;
        }

        @Override
        public GenericProjectileFX createProjectile(GenericGun gun, World world, LivingEntity p, float damage,
                                                    float speed, int TTL, float spread, float dmgDropStart,
                                                    float dmgDropEnd, float dmgMin, float penetration,
                                                    boolean blockdamage, EnumBulletFirePos firePos, float radius,
                                                    double gravity) {

            return new GenericProjectileFX(TGEntities.GENERIC_FX_PROJECTILE, world,p,damage,speed,TTL,spread,
                    dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
        }

        @Override
        public DamageType getDamageType() {
            return ProjectileFXType.values()[projectileType].damageType;
        }

        @Override
        public byte getProjectileType() {
            return this.projectileType;
        }
    }
}
