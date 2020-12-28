package techguns.items.guns;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGPacketsS2C;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.SonicShotgunProjectile;
import techguns.items.guns.ammo.DamageModifier;
import techguns.packets.PacketEntityAdditionalSpawnData;
import techguns.util.MathUtil;

import java.util.HashSet;

public class SonicShotgun extends GenericGun {
    public SonicShotgun(String name, ProjectileSelector projectileSelector, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL, float accuracy) {
        super(name, projectileSelector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL, accuracy);
    }

    public SonicShotgun(boolean addToGunList, String name, ProjectileSelector projectile_selector, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL, float accuracy) {
        super(addToGunList, name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL, accuracy);
    }

    @Override
    protected void spawnProjectile(World world, LivingEntity player, ItemStack itemstack, float spread, float offset, float damagebonus, EnumBulletFirePos firePos, Entity target) {
        String ammoVariantKey = this.getCurrentAmmoVariantKey(itemstack);

        IProjectileFactory<SonicShotgunProjectile> projectileFactory = this.projectile_selector.getFactoryForType(ammoVariantKey);

        DamageModifier mod = projectileFactory.getDamageModifier();
        byte projectileType = projectileFactory.getProjectileType();

        float modified_speed = mod.getVelocity(speed);

        SonicShotgunProjectile projectile = projectileFactory.createProjectile(this, world, player, mod.getDamage(damage) * damagebonus, modified_speed, mod.getTTL(this.getScaledTTL()), spread, mod.getRange(this.damageDropStart),
                mod.getRange(this.damageDropEnd), mod.getDamage(this.damageMin) * damagebonus, this.penetration, getDoBlockDamage(player), firePos, mod.getRadius(radius), gravity);

        IntOpenHashSet entitiesHit = new IntOpenHashSet();
        projectile.setProjectileType(projectileType);
        projectile.setProperties(player, projectile.pitch, projectile.yaw, 0.0f, modified_speed, 0.0F);
        projectile.mainProjectile = true;
        projectile.entitiesHit = entitiesHit;

        if (offset > 0.0f) {
            projectile.shiftForward(offset/modified_speed);
        }
        // callback for subclasses
        this.onProjectileSpawn(projectile, world, player, itemstack, spread, offset, damagebonus, firePos, target);

        world.spawnEntity(projectile);

        if(!world.isClient) {
            TGPacketsS2C.sentToAllTrackingPos(new PacketEntityAdditionalSpawnData(projectile), world, new BlockPos(projectile.getPos()));
        }

        int count = 5; //Base projectile count per ring
        int rings = 2; //number of rings

        spread = 0.075f;


        //Rings
        for (int j = 1; j <= rings; j++) {

            double angle =(Math.PI*2.0)/(double)(count*j);

            Vec3d FORWARD = projectile.getVelocity();
            Vec3d UP = new Vec3d(0,1,0); //Vec3.createVectorHelper(1, 0, 0);

            Vec3d sideVec = FORWARD.crossProduct(UP).normalize();

            Vec3d rotated = MathUtil.rotateVec3dAroundAxis(FORWARD, sideVec, 10.0*MathUtil.D2R);

            for (int i = 0; i < (count*j); i++) {

                projectile = projectileFactory.createProjectile(this, world, player, mod.getDamage(damage) * damagebonus, modified_speed, mod.getTTL(this.getScaledTTL()), spread, mod.getRange(this.damageDropStart),
                        mod.getRange(this.damageDropEnd), mod.getDamage(this.damageMin) * damagebonus, this.penetration, getDoBlockDamage(player), firePos, mod.getRadius(radius), gravity);

                projectile.entitiesHit = entitiesHit;
                projectile.mainProjectile = false;

                Vec3d newRotation = MathUtil.rotateVec3dAroundAxis(rotated, FORWARD.normalize(), angle);
                projectile.setPos(projectile.getX(), projectile.getY(), projectile.getZ());
                projectile.setVelocity(newRotation);

                if (offset>0.0f){
                    projectile.shiftForward(offset);
                }
                // callback for subclasses
                this.onProjectileSpawn(projectile, world, player, itemstack, spread, offset, damagebonus, firePos, target);

                world.spawnEntity(projectile);
            }
        }
    }
}
