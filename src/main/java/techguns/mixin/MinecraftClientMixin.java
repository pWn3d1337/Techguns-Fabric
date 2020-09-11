package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin extends MinecraftClient{

	
	public MinecraftClientMixin(RunArgs args) {
		super(args);
	}

	@Inject(at = @At("INVOKE"), method ="handleInputEvents", cancellable=true)
	public void handleInputEvents(CallbackInfo info) {
		
	}
	
}
