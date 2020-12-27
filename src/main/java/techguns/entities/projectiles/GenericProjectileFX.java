package techguns.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
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
import techguns.items.guns.ammo.DamageModifier;
import techguns.packets.PacketSpawnParticle;

public class GenericProjectileFX extends GenericProjectile {

    public static final byte PROJECTILE_TYPE_DEATOMIZER = 0;
    public static final byte PROJECTILE_TYPE_ALIENBLASTER = 1;
    public static final byte PROJECTILE_TYPE_NETHERBLASTER = 2;

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
        super.clientInitializeFinal();
        switch (getProjectileTypeId()){
            case PROJECTILE_TYPE_ALIENBLASTER:
                ClientProxy.get().createFXOnEntity("AlienBlasterTrail", this);
                break;
            case PROJECTILE_TYPE_NETHERBLASTER:
                ClientProxy.get().createFXOnEntity("CyberDemonBlasterTrail", this);
                break;
            case PROJECTILE_TYPE_DEATOMIZER:
            default:
                ClientProxy.get().createFXOnEntity("BlueBlasterTrail", this);
                break;
        }
    }

    @Override
    protected TGDamageSource getProjectileDamageSource() {
        TGDamageSource src=null;

        switch (this.getProjectileTypeId()) {
            case PROJECTILE_TYPE_NETHERBLASTER:
                src = TGDamageSource.causeFireDamage(this, this.shooter, EntityDeathUtils.DeathType.DEFAULT);
                break;
            case PROJECTILE_TYPE_ALIENBLASTER:
                src = TGDamageSource.causeFireDamage(this, this.shooter, EntityDeathUtils.DeathType.LASER);
                src.goreChance = 0.25f;
                break;
            case PROJECTILE_TYPE_DEATOMIZER:
            default:
                src = TGDamageSource.causeEnergyDamage(this, this.shooter, EntityDeathUtils.DeathType.GORE);
                src.goreChance=1.0f;
                break;
        }
        src.armorPenetration = this.penetration;
        return src;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.getProjectileTypeId() == PROJECTILE_TYPE_ALIENBLASTER && this.blockdamage) {
            burnBlocks(world, blockHitResult, 0.35);
        }
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void doImpactEffects(BlockHitResult rayTraceResult) {
        if(!this.world.isClient){
            Vec3d pos = rayTraceResult.getPos();
            switch (this.getProjectileTypeId()) {
                case PROJECTILE_TYPE_ALIENBLASTER:
                    TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("AlienExplosion", pos.x, pos.y, pos.z), this.world, pos, 32.0f);
                    break;
                case PROJECTILE_TYPE_NETHERBLASTER:
                    TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("CyberdemonBlasterImpact", pos.x, pos.y, pos.z), this.world, pos, 32.0f);
                    break;
                case PROJECTILE_TYPE_DEATOMIZER:
                default:
                    TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("BlueBlasterExplosion", pos.x, pos.y, pos.z), this.world, pos, 32.0f);
                    break;
            }
        }
    }

    @Override
    protected void onHitEffect(LivingEntity livingEntity, EntityHitResult entityHitResult) {
       switch(this.getProjectileTypeId()){
           case PROJECTILE_TYPE_ALIENBLASTER:
               livingEntity.setOnFireFor(3);
           default:
               break;
       }
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
            switch (this.projectileType){
                case PROJECTILE_TYPE_NETHERBLASTER:
                case PROJECTILE_TYPE_ALIENBLASTER:
                    return DamageType.FIRE;
                case PROJECTILE_TYPE_DEATOMIZER:
                default:
                    return DamageType.ENERGY;
            }
        }

        @Override
        public byte getProjectileType() {
            return this.projectileType;
        }
    }
}
