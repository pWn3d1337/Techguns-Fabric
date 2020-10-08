package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import techguns.api.guns.IGenericGun;
import techguns.client.ShooterValues;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

	@Inject(at = @At("INVOKE"), method = "(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;", cancellable = true)
	private static void getArmPose(AbstractClientPlayerEntity abstractClientPlayerEntity, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> info) {
		ItemStack itemStack = abstractClientPlayerEntity.getStackInHand(hand);
		if (!itemStack.isEmpty() && itemStack.getItem() instanceof IGenericGun) {
			IGenericGun gun = (IGenericGun) itemStack.getItem();
			if(gun.isAimed(abstractClientPlayerEntity, itemStack)) {
				if (ShooterValues.getPlayerIsReloading(abstractClientPlayerEntity, hand==Hand.OFF_HAND)) {
					info.setReturnValue(ArmPose.CROSSBOW_CHARGE);
				} else {
					info.setReturnValue(ArmPose.CROSSBOW_HOLD);
				}
			}
		
		}	
	}
}
