package techguns.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderStage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceFactory;
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

    @Inject(at = @At(value="INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0), method = "loadPrograms", locals = LocalCapture.CAPTURE_FAILHARD)
    protected void loadPrograms(ResourceFactory factory, CallbackInfo ci, List list, List list2) throws IOException {
        ((ArrayList<Pair<ShaderProgram, Consumer<ShaderProgram>>>)list2).add(Pair.of(new ShaderProgram(factory, particle_alpha_shader, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL), (ShaderProgram shader) -> {
            TGRenderHelper.particleShader = shader;
        }));
    }
}
