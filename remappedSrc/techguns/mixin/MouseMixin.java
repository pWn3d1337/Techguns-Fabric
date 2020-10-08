package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import techguns.api.guns.IGenericGun;
import techguns.client.ClientProxy;

@Mixin(Mouse.class)
public class MouseMixin {

	@Inject(at = @At("INVOKE"), method ="onMouseButton", cancellable=true)
	public void onMouseButton(long window, int button, int action, int mods, CallbackInfo info) {
		MinecraftClient mc = MinecraftClient.getInstance();
		boolean pressed = action==1;
		
		if (window == mc.getWindow().getHandle() && mc.currentScreen==null && mc.player!=null) {

			if (button == 0) {
			
				ClientProxy cp = ClientProxy.get();
				
				if(pressed && !mc.player.getMainHandStack().isEmpty() && mc.player.getMainHandStack().getItem() instanceof IGenericGun) {
					
					if (!cp.keyFirePressedMainhand) {
						cp.keyFirePressedMainhand = true;
					}
					info.cancel();
					
				} else {
					cp.keyFirePressedMainhand=false;
				}
			}
		}
	}
}
