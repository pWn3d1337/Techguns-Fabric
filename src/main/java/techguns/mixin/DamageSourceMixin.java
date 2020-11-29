package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import techguns.items.guns.GenericGunMeleeCharge;


@Mixin(DamageSource.class)
public abstract class DamageSourceMixin {

	
	@Inject(at = @At(value="INVOKE"), method ="player(Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/entity/damage/DamageSource;", cancellable=true)
	private static void TGPlayerDamageSource(PlayerEntity player, CallbackInfoReturnable<DamageSource> callback) {
		ItemStack stack = player.getMainHandStack();
		if (!stack.isEmpty() && stack.getItem() instanceof GenericGunMeleeCharge) {	
			GenericGunMeleeCharge gun = (GenericGunMeleeCharge) stack.getItem();
			DamageSource newSrc = gun.getMeleeDamageSource(player, stack);
			callback.setReturnValue(newSrc);
			callback.cancel();
		}
	}
}
