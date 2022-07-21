package techguns.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import techguns.client.ClientProxy;
import techguns.client.render.entities.RenderLateEntityRenderer;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.util.BlockUtil;

import java.util.List;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	private ClientWorld world;

	@Shadow
	public BufferBuilderStorage bufferBuilders;

	@Shadow
	private static void drawShapeOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double e, double f, float g, float h, float i, float j) {
		throw new AssertionError();
	}

	@Inject(at = @At("RETURN"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V", cancellable = false)
	public void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo info) {
		//Render TG particles at the end, similar to RenderWorldLastEvent from forge

		//System.out.println("tD: "+tickDelta);

		VertexConsumerProvider.Immediate immediate = this.bufferBuilders.getEntityVertexConsumers();
		
		MinecraftClient mc = MinecraftClient.getInstance();
		ClientProxy.get().particleManager.renderParticles(immediate, mc.cameraEntity, tickDelta, matrices, camera, matrix4f);

		RenderLateEntityRenderer.renderEntities(immediate, mc.cameraEntity, tickDelta, matrices, camera, matrix4f);
	}

	@Inject(at=@At("RETURN"), method = "drawBlockOutline")
	private void drawBlockOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState, CallbackInfo ci){
		if(world==null || client.player == null) return;

		ItemStack stack = client.player.getMainHandStack();
		if(!stack.isEmpty() && stack.getItem() instanceof GenericGunMeleeCharge){
			GenericGunMeleeCharge gun = (GenericGunMeleeCharge) stack.getItem();
			int miningradius = gun.getMiningRadius(client.player, stack);
			if(miningradius > 0){

				BlockHitResult blockHitResult = gun.getMiningTarget(client.player, world);
				if(blockHitResult !=null){

					Direction sideHit = blockHitResult.getSide();
					List<BlockPos> blocks = BlockUtil.getBlockPlaneAroundAxisForMining(world, client.player, blockHitResult.getBlockPos(), sideHit.getAxis(), miningradius, false, gun, stack);

					for (BlockPos p: blocks) {
						BlockState state = world.getBlockState(p);
						drawShapeOutline(matrixStack, vertexConsumer, state.getOutlineShape(this.world, p, ShapeContext.of(entity)), (double)p.getX() - d, (double)p.getY() - e, (double)p.getZ() - f, 0.0F, 0.0F, 0.0F, 0.4F);
					}
				}
			}
		}
	}
}
