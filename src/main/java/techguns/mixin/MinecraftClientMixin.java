package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.snooper.SnooperListener;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import techguns.api.client.ClientDisconnectEvent;
import techguns.items.guns.GenericGunMeleeCharge;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin extends ReentrantThreadExecutor<Runnable> implements SnooperListener, WindowEventHandler{

	public MinecraftClientMixin(String string) {
		super(string);
	}
	
	@Inject(at = @At("HEAD"), method ="disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", cancellable=false)
	public void disconnect(CallbackInfo info) {
		ClientDisconnectEvent.EVENT.invoker().onDisconnect((MinecraftClient)(Object)this);
	}
	
	@Inject(at = @At(value="INVOKE", target="swingHand(Lnet/minecraft/util/Hand;)V"), method ="doAttack()V", cancellable=true)
	public void doAttack(CallbackInfo info) {
		ItemStack stack = ((MinecraftClient)(Object)this).player.getMainHandStack();
		
		if (stack!= ItemStack.EMPTY) {
			if (stack.getItem() instanceof GenericGunMeleeCharge) {
				GenericGunMeleeCharge weapon = (GenericGunMeleeCharge) stack.getItem();
				
				if (!weapon.isShootWithLeftClick() && !weapon.shouldSwing(stack)) {
					info.cancel();
				}
			}
		}
	}
	
	@Inject(at = @At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V"), method ="handleBlockBreaking(Z)V", cancellable=true, locals = LocalCapture.CAPTURE_FAILHARD)
	public void handleBlockBreaking(boolean bl, CallbackInfo info, BlockHitResult blockHitResult, BlockPos blockPos, Direction direction) {
		ItemStack stack = ((MinecraftClient)(Object)this).player.getMainHandStack();
		
		if (stack!= ItemStack.EMPTY) {
			if (stack.getItem() instanceof GenericGunMeleeCharge) {
				GenericGunMeleeCharge weapon = (GenericGunMeleeCharge) stack.getItem();
				
				if (!weapon.isShootWithLeftClick() && !weapon.shouldSwing(stack)) {
					weapon.onMining(((MinecraftClient)(Object)this).player, blockHitResult);
					info.cancel();
				}
			}
		}
	}
}
