package techguns.entities.projectiles;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.TGPacketsS2C;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;

import java.util.ArrayList;

public class SonicShotgunProjectile extends GenericProjectile {

    public IntOpenHashSet entitiesHit;
    public boolean mainProjectile = false;

    public SonicShotgunProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    protected SonicShotgunProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos) {
        super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
    }

    public SonicShotgunProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter, CompoundTag data) {
        super(T, world, shooter, data);
    }

    @Override
    protected TGDamageSource getProjectileDamageSource() {
        TGDamageSource src = new TGDamageSource("tg_sonic",this, shooter, DamageType.PHYSICAL, EntityDeathUtils.DeathType.GORE);
        src.ignoreHurtresistTime=true;
        src.armorPenetration = this.penetration;
        src.setKnockback(2.0f);
        src.goreChance=1.0f;
        return src;
    }

    @Override
    protected void doImpactEffects(BlockHitResult rayTraceResult)
    {
        this.mainProjectile=true;
        if (mainProjectile && !this.world.isClient) {
             TGPacketsS2C.sendToAllTracking(new PacketSpawnParticle("SonicShotgunImpact", this.getPos().x, this.getPos().y, this.getPos().z), this, false);
        }
    }

    @Override
    public void getAdditionalSpawnData(CompoundTag data) {
        super.getAdditionalSpawnData(data);
        data.putBoolean("mainprojectile", this.mainProjectile);
    }

    @Override
    public void parseAdditionalData(CompoundTag data) {
        super.parseAdditionalData(data);
        this.mainProjectile = data.getBoolean("mainprojectile");
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putBoolean("mainprojectile", this.mainProjectile);
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.mainProjectile = tag.getBoolean("mainprojectile");
    }

    public static class Factory implements IProjectileFactory<SonicShotgunProjectile>{

        @Override
        public SonicShotgunProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
            return new SonicShotgunProjectile(TGEntities.SONIC_SHOTGUN_PROJECTILE, world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
        }

        @Override
        public DamageType getDamageType() {
            return DamageType.PHYSICAL;
        }
    }
}
