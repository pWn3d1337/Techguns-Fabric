package techguns.damagesystem;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.deatheffects.EntityDeathUtils;

import java.util.List;

public class TGExplosionIgnoreBlocks {

    public static void createExplosion(World world, double x, double y, double z, TGDamageSource dmgSrc, double primaryDamage, double secondaryDamage, double primaryRadius, double secondaryRadius, List<Entity> excludedEntities) {
        double totalRadius = Math.max(primaryRadius, secondaryRadius);
        float f3 = (float) (totalRadius);
        int k1 = MathHelper.floor(x - (double)f3 - 1.0D);
        int l1 = MathHelper.floor(x + (double)f3 + 1.0D);
        int i2 = MathHelper.floor(y - (double)f3 - 1.0D);
        int i1 = MathHelper.floor(y + (double)f3 + 1.0D);
        int j2 = MathHelper.floor(z - (double)f3 - 1.0D);
        int j1 = MathHelper.floor(z + (double)f3 + 1.0D);
        List<Entity> list = world.getOtherEntities(null, new Box((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));

        Vec3d position = new Vec3d(x, y, z);

        for (Entity entity : list) {
            if (!excludedEntities.contains(entity)) {
                double damage;
                //Check distance
                Vec3d pos = entity.getPos();
                double distance = position.distanceTo(new Vec3d(pos.x, entity.getEyeY(), pos.z));
                if (distance <= primaryRadius) damage = primaryDamage;
                else if (distance <= secondaryRadius) damage = secondaryDamage + ((distance-primaryRadius)/(secondaryRadius-primaryRadius)) * (primaryDamage-secondaryDamage);
                else damage = 0.0;

                entity.damage(dmgSrc,  (float)Math.max(0, damage));
            }
        }
    }

}