package techguns.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
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
import techguns.packets.PacketSpawnParticleOnEntity;

public class FlamethrowerProjectile extends GenericProjectile {

    float chanceToIgnite = 0.5f;
    int entityIgniteTime = 3;

    public FlamethrowerProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        ClientProxy.get().createFXOnEntity("FlamethrowerTrail", this);
    }

    protected FlamethrowerProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, double gravity) {
        super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
        this.gravity=gravity;
    }

    public FlamethrowerProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter) {
        super(T, world, shooter);
        ClientProxy.get().createFXOnEntity("FlamethrowerTrail", this);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.blockdamage) {
            burnBlocks(world, blockHitResult, chanceToIgnite);
        }
        doImpactEffects(blockHitResult);

        this.removeOnHit(blockHitResult);
    }

    @Override
    protected void inWaterTick() {
        this.markForRemoval();
    }

    @Override
    protected TGDamageSource getProjectileDamageSource() {
        TGDamageSource src = TGDamageSource.causeFireDamage(this, this.getOwner(), EntityDeathUtils.DeathType.DEFAULT);
        src.armorPenetration = this.penetration;
        src.setNoKnockback();
        return src;
    }

    @Override
    protected void onHitEffect(LivingEntity livingEntity, EntityHitResult entityHitResult) {
        livingEntity.setOnFireFor(this.entityIgniteTime);
    }

    @Override
    protected void doImpactEffects(BlockHitResult rayTraceResult) {
        if(!this.world.isClient) {
            Vec3d targetPos = rayTraceResult.getPos();
            TGPacketsS2C.sendToAllAround(new PacketSpawnParticle("FlamethrowerImpact", targetPos.x, targetPos.y, targetPos.z), this.world, targetPos, 32.0f);
        }
    }

    public static class Factory implements IProjectileFactory<FlamethrowerProjectile> {

        @Override
        public FlamethrowerProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
            float offsetX = 0.0f;
            if (firePos == EnumBulletFirePos.RIGHT) offsetX = -0.15f;
            else if (firePos == EnumBulletFirePos.LEFT) offsetX = 0.15f;
            float offsetY = -0.05f;
            float offsetZ = 0.5f;

            TGPacketsS2C.sendToAllAround(new PacketSpawnParticleOnEntity("FlamethrowerFireFX", p, offsetX, offsetY, offsetZ, true), world, p.getPos(), 32.0f);

            return new FlamethrowerProjectile(TGEntities.FLAMETHROWER_PROJECTILE, world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,gravity);
        }

        @Override
        public DamageType getDamageType() {
            return DamageType.FIRE;
        }

    }
}
