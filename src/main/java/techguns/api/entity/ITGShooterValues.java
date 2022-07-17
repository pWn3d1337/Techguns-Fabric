package techguns.api.entity;

import net.minecraft.util.Hand;

public interface ITGShooterValues {
	AttackTime getAttackTime(boolean offHand);

	default AttackTime getAttackTime(Hand hand){
		return getAttackTime(hand==Hand.OFF_HAND);
	}
	default boolean isRecoiling(boolean offHand){
		return this.getAttackTime(offHand).isRecoiling();
	}
	default boolean isReloading(boolean offHand){
		return this.getAttackTime(offHand).isReloading();
	}
}
