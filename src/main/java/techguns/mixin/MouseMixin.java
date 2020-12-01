package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.api.guns.GunManager;
import techguns.api.guns.IGenericGun;
import techguns.client.ClientProxy;
import techguns.items.guns.GenericGunCharge;
import techguns.client.ShooterValues;

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
					
					if (((IGenericGun)mc.player.getMainHandStack().getItem()).isShootWithLeftClick()) {
					
						if (!cp.keyFirePressedMainhand) {
							cp.keyFirePressedMainhand = true;
						}
						info.cancel();
					} else if (ShooterValues.getReloadtime(mc.player, false) > 0) {
						long diff = ShooterValues.getReloadtime(ClientProxy.get().getPlayerClient(), false) - System.currentTimeMillis();
						if (diff > 0) {
							info.cancel();
						}
					}
					
				} else {
					cp.keyFirePressedMainhand=false;
				}
			} else if (button == 1 && !mc.player.isSneaking() && !mc.player.getOffHandStack().isEmpty() && mc.player.getOffHandStack().getItem() instanceof IGenericGun && GunManager.canUseOffhand(mc.player)) {
					
					ClientProxy cp = ClientProxy.get();
					
					if (((IGenericGun) mc.player.getOffHandStack().getItem()).isShootWithLeftClick()) {
						cp.keyFirePressedOffhand = pressed;
						info.cancel();
		
						// can't mine/attack while reloading
					} else if (ShooterValues.getReloadtime(ClientProxy.get().getPlayerClient(), true) > 0) {
						long diff = ShooterValues.getReloadtime(ClientProxy.get().getPlayerClient(), true) - System.currentTimeMillis();
						if (diff > 0) {
							if (pressed) {
								info.cancel();
							}
						}
		
					}
			} else if (button == 1) {
				//Lock On
				if (!mc.player.getMainHandStack().isEmpty() && mc.player.getMainHandStack().getItem() instanceof GenericGunCharge 
						&& ((GenericGunCharge)mc.player.getMainHandStack().getItem()).getLockOnTicks() > 0) {
					ITGExtendedPlayer props = (ITGExtendedPlayer)mc.player;
					props.setLockOnEntity(null);
					props.setLockOnTicks(-1);
				}
			}
		}
	}
}
