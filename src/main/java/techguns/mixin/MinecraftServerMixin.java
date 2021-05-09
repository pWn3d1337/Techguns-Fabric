package techguns.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import techguns.server.ServerProxy;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin  {

    @Inject(method="<init>*", at=@At("TAIL"))
    private void onConstructed(CallbackInfo ci){
        ServerProxy.SERVER_INSTANCE = (MinecraftServer) (Object)this;
    }

}
