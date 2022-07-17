package techguns.mixin;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import techguns.api.guns.GunManager;
import techguns.api.guns.IGenericGun;
import techguns.client.ShooterValues;
import techguns.client.models.armor.ModelT3PowerArmor;
import techguns.client.render.entities.npcs.RenderGenericNPC;
import techguns.client.render.entities.npcs.TGArmorFeatureRenderer;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

	private PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Z)V")
	private void ctor(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci){
		this.addFeature(new TGArmorFeatureRenderer(this, new ModelT3PowerArmor(ctx.getPart(RenderGenericNPC.tgArmorLayer))));
	}

	@Inject(at = @At("INVOKE"), method = "getArmPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;", cancellable = true)
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
