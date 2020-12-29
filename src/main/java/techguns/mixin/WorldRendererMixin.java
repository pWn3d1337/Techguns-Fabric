package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import techguns.client.ClientProxy;
import techguns.client.render.entities.RenderLateEntityRenderer;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

	@Shadow
	public BufferBuilderStorage bufferBuilders;
	
	@Inject(at = @At("RETURN"), method = "(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V", cancellable = false)
	public void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo info) {
		//Render TG particles at the end, similar to RenderWorldLastEvent from forge
		
		VertexConsumerProvider.Immediate immediate = this.bufferBuilders.getEntityVertexConsumers();
		
		MinecraftClient mc = MinecraftClient.getInstance();
		ClientProxy.get().particleManager.renderParticles(immediate, mc.cameraEntity, tickDelta, matrices, camera, matrix4f);

		RenderLateEntityRenderer.renderEntities(immediate, mc.cameraEntity, tickDelta, matrices, camera, matrix4f);
	}
}
