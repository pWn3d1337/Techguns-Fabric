package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import techguns.client.ClientProxy;
import techguns.items.guns.GenericGun;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {
	
	@Inject(at = @At("RETURN"), method = "getFovMultiplier", cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	public void getSpeed(CallbackInfoReturnable<Float> info, float f) {
		AbstractClientPlayerEntity self = (AbstractClientPlayerEntity)(Object)this;

		//TODO cancel speedfov
		//float speed_fov = (float)((double)1.0 * ((self.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) / (double)self.abilities.getWalkSpeed() + 1.0D) / 2.0D));
		
		if(!self.getMainHandStack().isEmpty() && self.getMainHandStack().getItem() instanceof GenericGun) {
			info.setReturnValue(f* ClientProxy.get().getZoomfactor());
		}
		
	}

}
