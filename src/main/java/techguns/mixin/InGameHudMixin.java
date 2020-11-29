package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import techguns.client.render.TGGuiRender;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

	@Shadow
	private MinecraftClient client;
	
	@Shadow
	private int scaledWidth;
	@Shadow
	private int scaledHeight;
	
	@Inject(at = @At("HEAD"), method = "renderCrosshair", cancellable = true)
	public void renderCrosshair(MatrixStack matrices, CallbackInfo info) {
		
		TGGuiRender.renderTechgunsHUD(this, matrices, client, scaledWidth, scaledHeight);
		
		boolean showCustomCrosshair = TGGuiRender.renderCrosshair(this, matrices, client, scaledWidth, scaledHeight);
		if (showCustomCrosshair) {
			info.cancel();
		}
	}
	
}
