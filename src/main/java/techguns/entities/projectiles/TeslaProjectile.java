package techguns.entities.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;

public class TeslaProjectile extends GenericBeamProjectile {
    public static final int TTL = 10;
    public static final float CHAIN_RANGE = 8.0f;
    public static final int CHAIN_TARGETS = 4;
    public static final float CHAIN_DAMAGE_FACTOR = 0.75f;

    public long seed = 0;

    protected int chainTargets = CHAIN_TARGETS;
    protected Entity prevTarget = null;

    public TeslaProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter) {
        super(T, world, shooter);
        this.seed = world.random.nextInt();
    }

    public TeslaProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, int chainTargets) {
        super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos, (short) 1, false, (byte)0, "TeslaFlare");
        this.chainTargets = chainTargets;
    }

    public TeslaProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.seed = world.random.nextInt();
    }


    @Override
    protected TGDamageSource getProjectileDamageSource() {
        TGDamageSource src = TGDamageSource.causeLightningDamage(this, this.getOwner(), EntityDeathUtils.DeathType.LASER);
        src.armorPenetration = this.penetration;
        src.setNoKnockback();
        src.goreChance=0.5f;
        return src;
    }

    public static class Factory implements IProjectileFactory<TeslaProjectile> {

        @Override
        public TeslaProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
            return new TeslaProjectile(TGEntities.TESLA_PROJECTILE, world, p, damage, speed, TeslaProjectile.TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos, CHAIN_TARGETS);
        }

        @Override
        public byte getProjectileType() {
            return GenericBeamProjectile.BEAM_TYPE_TESLA;
        }

        @Override
        public DamageType getDamageType() {
            return DamageType.LIGHTNING;
        }
    }
}
