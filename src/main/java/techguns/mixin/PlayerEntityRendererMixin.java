package techguns.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import techguns.api.guns.GunManager;
import techguns.api.guns.IGenericGun;
import techguns.client.ShooterValues;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

	private PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(at = @At("HEAD"), method = "getArmPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;", cancellable = true)
	private static void getArmPose(AbstractClientPlayerEntity abstractClientPlayerEntity, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> info) {
		ItemStack itemStack = abstractClientPlayerEntity.getStackInHand(hand);
		if (!itemStack.isEmpty() && itemStack.getItem() instanceof IGenericGun) {
			IGenericGun gun = (IGenericGun) itemStack.getItem();
			if(gun.isAimed(abstractClientPlayerEntity, itemStack)) {
				if (ShooterValues.getPlayerIsReloading(abstractClientPlayerEntity, hand==Hand.OFF_HAND)) {
					info.setReturnValue(ArmPose.CROSSBOW_CHARGE);
				} else {
					boolean akimbo = GunManager.isAkimbo(abstractClientPlayerEntity, hand, itemStack);
					info.setReturnValue(gun.getArmPose(akimbo));
				}
			}
		
		}	
	}
	
}
