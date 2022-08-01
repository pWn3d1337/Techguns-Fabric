package techguns.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import techguns.Techguns;
import techguns.client.render.TGRenderHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements SynchronousResourceReloader,
        AutoCloseable {

    private static final String particle_alpha_shader = Techguns.MODID+"_particle_alpha";

    @Inject(at = @At(value="INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0), method = "loadShaders", locals = LocalCapture.CAPTURE_FAILHARD)
    protected void loadShaders(ResourceManager manager, CallbackInfo ci, List list, List list2) throws IOException {
        ((ArrayList<Pair<Shader, Consumer<Shader>>>)list2).add(Pair.of(new Shader(manager, particle_alpha_shader, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL), (Shader shader) -> {
            TGRenderHelper.particleShader = shader;
        }));
    }
}
