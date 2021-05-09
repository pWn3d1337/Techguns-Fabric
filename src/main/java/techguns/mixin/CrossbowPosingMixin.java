package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import techguns.api.entity.AttackTime;
import techguns.api.entity.ITGShooterValues;

@Mixin(CrossbowPosing.class)
public class CrossbowPosingMixin {

	@Inject(at = @At("INVOKE"), method = "charge(Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/entity/LivingEntity;Z)V", cancellable = true)
	private static void charge(ModelPart holdingArm, ModelPart pullingArm, LivingEntity actor, boolean rightArmed, CallbackInfo info) {
		ITGShooterValues shooter = (ITGShooterValues) actor;
		AttackTime attackTimes = shooter.getAttackTime(!rightArmed);
		if (attackTimes != null && attackTimes.isReloading()) {

			ModelPart modelPart = rightArmed ? holdingArm : pullingArm;
			ModelPart modelPart2 = rightArmed ? pullingArm : holdingArm;
			modelPart.yaw = rightArmed ? -0.8F : 0.8F;
			modelPart.pitch = -0.97079635F;
			modelPart2.pitch = modelPart.pitch;

			long diff = attackTimes.getReloadTime() - System.currentTimeMillis();
			float h = 1.0f - ((float) diff / (float) attackTimes.getReloadTimeTotal());
			if (h<0.75f) {
				h=0;
			} else {
				h -= 0.75f;
				h *= 1.0f/0.25f;
			}
			
			modelPart2.yaw = MathHelper.lerp(h, 0.4F, 0.85F) * (float) (rightArmed ? 1 : -1);
			modelPart2.pitch = MathHelper.lerp(h, modelPart2.pitch, -1.5707964F);

			info.cancel();
		}
	}
}
