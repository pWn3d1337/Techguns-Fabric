package techguns.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.world.World;
import techguns.api.guns.GunManager;
import techguns.api.render.IItemRenderer;
import techguns.client.render.ITGItemRenderer;
import techguns.client.render.TGRenderRegistries;
import techguns.items.armors.PoweredArmor;
import techguns.items.guns.GenericGun;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

	@Shadow
	public float zOffset;

	@Shadow
	protected abstract void renderGuiQuad(BufferBuilder buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha);

	@Inject(at = @At("HEAD"), method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", cancellable = true)
	public void renderItem(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo info) {
		this.renderHack((LivingEntity)null, stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, info);
	}

	@Inject(at = @At("HEAD"), method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V", cancellable = true)
	public void renderItem(LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int light, int overlay, int seed, CallbackInfo info) {
		this.renderHack(entity, stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, info);
	}
	
	private void renderHack(LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo info) {
		if (entity != null && !shouldRenderItem(stack, entity, renderMode, leftHanded)) {
			info.cancel();
		} else {
			if (!stack.isEmpty()) {
				Item it = stack.getItem();
				if (it instanceof ITGItemRenderer) {
					if(((ITGItemRenderer) it).shouldUseRenderHack(stack)) {
						info.cancel();
						IItemRenderer r = TGRenderRegistries.getRendererForItem(it);
						if (r != null) {
							r.renderItem(entity, renderMode, matrices, stack, leftHanded, vertexConsumers, light, overlay, null);
						}
					}
				}
			}
		}
	}
	
	private static boolean shouldRenderItem(ItemStack stack, LivingEntity elb, ModelTransformationMode transform, boolean leftHanded) {
		if( !(transform==ModelTransformationMode.FIRST_PERSON_LEFT_HAND || transform==ModelTransformationMode.THIRD_PERSON_LEFT_HAND ||
				transform==ModelTransformationMode.FIRST_PERSON_RIGHT_HAND || transform==ModelTransformationMode.THIRD_PERSON_RIGHT_HAND)) {
			return true;
		}
		
		boolean mainhand = transform==ModelTransformationMode.FIRST_PERSON_RIGHT_HAND || transform==ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
		if (elb.getMainArm()==Arm.LEFT) {
			mainhand=!mainhand;
		}
		if (mainhand) {
			return true;
		} else {
			return GunManager.canUseOffhand(elb.getMainHandStack(), stack, elb);
		}
	}

	@Inject(at = @At("HEAD"), method = "renderGuiItemOverlay(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V")
	public void renderGuiItemOverlay(MatrixStack matrices, TextRenderer renderer, ItemStack stack, int x, int y, String countLabel, CallbackInfo info) {
		if (!stack.isEmpty() && stack.getItem() instanceof GenericGun) {
			info.cancel();
			
			GenericGun gun = (GenericGun) stack.getItem();
			
			//Draw Ammo Left
			MatrixStack matrixStack = new MatrixStack();
			matrixStack.translate(x, y, 0);
			matrixStack.scale(0.5f, 0.5f, 1.0f);
			matrixStack.translate(-x, -y, 0);
			
	        String string = ""+gun.getAmmoLeft(stack);
	        matrixStack.translate(0.0D, 0.0D, (double)(this.zOffset + 200.0F));
	        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
	        renderer.draw((String)string, (float)(x + 19 +9 - 2 - renderer.getWidth(string)/2), (float)(y ), 16777215, true, matrixStack.peek().getPositionMatrix(), immediate, false, 0, 15728880);

	        immediate.draw();
		} else if (!stack.isEmpty() && stack.getItem() instanceof PoweredArmor){
			PoweredArmor armor = (PoweredArmor) stack.getItem();

			info.cancel();

			//draw second bar
			RenderSystem.disableDepthTest();
			RenderSystem.disableTexture();
			RenderSystem.disableBlend();
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tessellator.getBuffer();
			float f = Math.max(0.0f, (float)PoweredArmor.getPower(stack) / (float)armor.maxpower);
			if (stack.isDamaged()) {
				int i = stack.getItemBarStep();
				int j = stack.getItemBarColor();
				this.renderGuiQuad(bufferBuilder, x + 2, y + 13, 13, 2, 0, 0, 0, 255);
				this.renderGuiQuad(bufferBuilder, x + 2, y + 13, i, 1, j >> 16 & 0xFF, j >> 8 & 0xFF, j & 0xFF, 255);
			}
			if (!armor.isFullyPowered(stack)) {
				int k = Math.round(f * 13.0F);
				int l = MathHelper.hsvToRgb(f * 2F / 3F, 1.0f, 1.0f);
				this.renderGuiQuad(bufferBuilder, x + 2, y + 14, 13, 2, 0, 0, 0, 255);
				this.renderGuiQuad(bufferBuilder, x + 2, y + 14, k, 1, l >> 16 & 0xFF, l >> 8 & 0xFF, l & 0xFF, 255);
			}
			RenderSystem.enableBlend();
			RenderSystem.enableTexture();
			RenderSystem.enableDepthTest();
		}
	}
	
}
