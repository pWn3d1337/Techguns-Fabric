package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.util.snooper.SnooperListener;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import techguns.api.client.ClientDisconnectEvent;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin extends ReentrantThreadExecutor<Runnable> implements SnooperListener, WindowEventHandler{

	public MinecraftClientMixin(String string) {
		super(string);
	}
	
	@Inject(at = @At("HEAD"), method ="disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", cancellable=false)
	public void disconnect(CallbackInfo info) {
		ClientDisconnectEvent.EVENT.invoker().onDisconnect((MinecraftClient)(Object)this);
	}
}
