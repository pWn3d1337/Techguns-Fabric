package techguns.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.TGPacketsS2C;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGExplosion;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;

public class GrenadeProjectile extends GenericProjectile {

    protected int bounces=3;

    public GrenadeProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    protected GrenadeProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity, int maxbounces) {
        super(T, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
        this.gravity = gravity;
        this.radius = radius;
        this.bounces = maxbounces;
    }

    public GrenadeProjectile(EntityType<? extends GenericProjectile> T, World world, LivingEntity shooter) {
        super(T, world, shooter);
    }

    protected void playBounceSound() {
        world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_IRON_GOLEM_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
    }

    protected void explode(){
        if (!this.world.isClient){
            TGPacketsS2C.sendToAllAroundEntity(new PacketSpawnParticle("RocketExplosion", this.getX(), this.getY(), this.getZ()), this, 50.0f);
            TGExplosion explosion = new TGExplosion(world, this.getOwner(), this, this.getX(), this.getY(), this.getZ(), this.damage, this.damageMin, this.damageDropStart,this.damageDropEnd, this.blockdamage?0.05:0.0);
            explosion.doExplosion(true);
        }else {
            //Techguns.proxy.createLightPulse(this.posX, this.posY, this.posZ, 5, 15, 10.0f, 1.0f, 1f, 0.9f, 0.5f);
        }
        this.markForRemoval();
    }

    @Override
    protected void onHitEffect(LivingEntity livingEntity, EntityHitResult hitResult) {
        this.explode();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        Direction sideHit = blockHitResult.getSide();

        if (this.bounces > 0) {
            Vec3d motion = this.getVelocity();

            float len = this.getBounceSpeedFactor();

            Vec3d refl;
            switch (sideHit) {
                case EAST:
                case WEST:
                    refl = new Vec3d(-motion.x, motion.y, motion.z);
                    break;
                case NORTH:
                case SOUTH:
                    refl = new Vec3d(motion.x, motion.y, -motion.z);
                    break;
                case DOWN:
                case UP:
                default:
                    refl = new Vec3d(motion.x, -motion.y, motion.z);
                    break;
            }
            this.setVelocity(refl.multiply(len));
            this.playBounceSound();

            this.bounces--;
        } else {
            this.explode();
        }
    }

    protected float getBounceSpeedFactor() {
        return 0.75f;
    }

    public static class Factory implements IProjectileFactory<GrenadeProjectile> {
        protected byte projectile_type;
        protected int maxbounces;

        public Factory(int projectile_type, int maxbounces) {
            this.projectile_type = (byte)projectile_type;
            this.maxbounces=maxbounces;
        }

        @Override
        public GrenadeProjectile createProjectile(GenericGun gun, World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
            return new GrenadeProjectile(TGEntities.GRENADE_PROJECTILE, world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos, radius, gravity, this.maxbounces);
        }

        @Override
        public DamageType getDamageType() {
            return DamageType.EXPLOSION;
        }

        @Override
        public byte getProjectileType() {
            return projectile_type;
        }
    }

    @Override
    public void clientInitializeFinal() {
        ClientProxy.get().createFXOnEntity("GrenadeLauncherTrail", this);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putByte("bounces", (byte)this.bounces);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.bounces = tag.getByte("bounces");
    }

    @Override
    public void getAdditionalSpawnData(NbtCompound data) {
        super.getAdditionalSpawnData(data);
        data.putByte("bounces", (byte)this.bounces);
    }

    @Override
    public void parseAdditionalData(NbtCompound data) {
        super.parseAdditionalData(data);
        this.bounces = data.getByte("bounces");
    }
}
