package techguns.api.npc;

import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import techguns.entities.projectiles.EnumBulletFirePos;

/**
 *	Must only be implemented by subclasses of LivingEntity
 */
public interface INPCTechgunsShooter {
    default Vec3d getWeaponPos() {
        return new Vec3d(0,0,0);
    }
    default boolean hasWeaponArmPose() {
        return true;
    }
    default float getGunScale() {
        return 1.0f;
    }
    default float getBulletOffsetSide() {
        return 0f;
    }
    default float getBulletOffsetHeight() {
        return 0f;
    }
    default EnumBulletFirePos getFirePosForHand(Hand hand){
        return hand == Hand.MAIN_HAND ? EnumBulletFirePos.RIGHT : EnumBulletFirePos.LEFT;
    }
}