package techguns.mixin;

import net.minecraft.entity.attribute.EntityAttributes;
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

		//TODO CONFIG OPTION

		//Calculate FOV Multiplier
		float f2 = 1.0f;
		if (self.getAbilities().flying) {
			f2 *= 1.1f;
		}
		if (self.getAbilities().getWalkSpeed() == 0.0f || Float.isNaN(f2 *= ((float)self.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) / self.getAbilities().getWalkSpeed() + 1.0f) / 2.0f) || Float.isInfinite(f)) {
			f2 = 1.0f;
		}

		//Counter speed dependant fov change
		f /= f2;
		if (self.isSprinting()){ //15% fov increase for sprinting
			f*=1.15f;
		}

		if(!self.getMainHandStack().isEmpty() && self.getMainHandStack().getItem() instanceof GenericGun) {
			f*=ClientProxy.get().getZoomfactor();
		}
		info.setReturnValue(f);
	}

}
