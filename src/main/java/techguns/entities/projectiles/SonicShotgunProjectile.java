package techguns.entities.projectiles;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
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

public class SonicShotgunProjectile extends GenericProjectile {

    public IntOpenHashSet entitiesHit;
    public boolean mainProjectile = false;

    public SonicShotgunProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    protected SonicShotgunProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos) {
        super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
    }

    public SonicShotgunProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter) {
        super(T, world, shooter);
    }

    @Override
    protected TGDamageSource getProjectileDamageSource() {
        TGDamageSource src = new TGDamageSource("tg_sonic",this, this.getOwner(), DamageType.PHYSICAL, EntityDeathUtils.DeathType.GORE);
        src.ignoreHurtresistTime=true;
        src.armorPenetration = this.penetration;
        src.setKnockback(2.0f);
        src.goreChance=1.0f;
        return src;
    }

    @Override
    protected void doImpactEffects(BlockHitResult rayTraceResult)
    {
        if (mainProjectile && !this.world.isClient) {
             TGPacketsS2C.sendToAllTracking(new PacketSpawnParticle("SonicShotgunImpact", this.getPos().x, this.getPos().y, this.getPos().z), this, false);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity target = entityHitResult.getEntity();

        int targetid = target.getId();
        if(this.entitiesHit == null){
            this.entitiesHit = new IntOpenHashSet();

        } else if (this.entitiesHit.contains(targetid)) {
            return;
        }
        this.entitiesHit.add(targetid);

        Entity shooter = this.getOwner();
        TGDamageSource damageSource;
        TGDamageSource damageSourceKnockback;
        if (shooter == null) {
            damageSource = this.getProjectileDamageSource();
            damageSource.setNoKnockback();
            damageSourceKnockback = TGDamageSource.getKnockbackDummyDmgSrc(this, this);
        } else {
            damageSource = this.getProjectileDamageSource();
            damageSource.setNoKnockback();
            damageSourceKnockback = TGDamageSource.getKnockbackDummyDmgSrc(this, shooter);
            if (shooter instanceof LivingEntity) {
                ((LivingEntity) shooter).onAttacking(target);
            }
        }

        target.damage(damageSourceKnockback, 0.1f);
        if (target.damage(damageSource, this.getDamage())) {

            if (target instanceof LivingEntity) {
                LivingEntity livingTarget = (LivingEntity) target;

                if (!this.world.isClient && shooter instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingTarget, shooter);
                    EnchantmentHelper.onTargetDamaged((LivingEntity) shooter, livingTarget);
                }

                this.onHitEffect(livingTarget, entityHitResult);
                if (shooter != null && livingTarget != shooter && livingTarget instanceof PlayerEntity
                        && shooter instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity) shooter).networkHandler.sendPacket(
                            new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                }

                if (!target.isAlive() && this.piercingKilledEntities != null) {
                    this.piercingKilledEntities.add(livingTarget);
                }
            }
        }

    }

    @Override
    public void getAdditionalSpawnData(NbtCompound data) {
        super.getAdditionalSpawnData(data);
        data.putBoolean("mainprojectile", this.mainProjectile);
    }

    @Override
    public void parseAdditionalData(NbtCompound data) {
        super.parseAdditionalData(data);
        this.mainProjectile = data.getBoolean("mainprojectile");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putBoolean("mainprojectile", this.mainProjectile);
    }

    @Override
    protected boolean canHit(Entity entity) {
        return super.canHit(entity) && (this.entitiesHit==null || !(this.entitiesHit.contains(entity.getId())));
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
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

    @Override
    public void clientInitializeFinal() {
        if(this.mainProjectile){
            ClientProxy.get().createFXOnEntity("SonicShotgunTrail", this);
        }
    }
}
