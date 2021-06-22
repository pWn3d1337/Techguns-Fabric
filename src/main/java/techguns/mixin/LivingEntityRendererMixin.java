package techguns.mixin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.client.render.entity.EntityRendererFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Lists;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import techguns.api.render.ITGAnimalModel;
import techguns.client.deatheffects.DeathEffectHandler;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.fx.IScreenEffect.RenderType;
import techguns.deatheffects.EntityDeathUtils.DeathType;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>>
		extends EntityRenderer<T> implements FeatureRendererContext<T, M> {

	private static final float MAX_DEATH_TIME = 20;

	@Shadow
	private M model;

//	@Shadow
//	protected final List<FeatureRenderer<T, M>> features = null;

	@Shadow
	protected abstract float getHandSwingProgress(T livingEntity, float g);

	@Shadow
	protected abstract float getAnimationCounter(T livingEntity, float g);

	@Shadow
	protected abstract RenderLayer getRenderLayer(T livingEntity, boolean bl, boolean bl2, boolean bl3);

	@Shadow
	protected abstract boolean isVisible(T livingEntity);

	@Shadow
	protected abstract void scale(T livingEntity, MatrixStack matrixStack, float g);

	@Shadow
	protected abstract void setupTransforms(T livingEntity, MatrixStack matrixStack, float o, float h, float g);

	@Shadow
	protected abstract float getAnimationProgress(T livingEntity, float g);

	protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Inject(at = @At("INVOKE"), method = "render", cancellable = true)
	public void render(T livingEntity, float yaw, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo info) {

		// TODO: Adjust light parameter

		/*
		 * Death Effects
		 */
		if (livingEntity.deathTime > 0) {
			DeathType dt = DeathEffectHandler.getEntityDeathType(livingEntity);
			if (dt != null) {
				switch (dt) {
				case GORE:
					info.cancel();
					break;
				case DISMEMBER:
				case BIO:
				case LASER:
					// TODO
					info.cancel();
					this.renderDead(livingEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
					break;
				case DEFAULT:
				default:
					break;
				}
			}
		}
	}

	public void renderDead(T livingEntity, float yaw, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {

		DeathType dt = DeathEffectHandler.getEntityDeathType(livingEntity);
		if (dt == null)
			return;

		matrixStack.push();
		this.model.handSwingProgress = this.getHandSwingProgress(livingEntity, tickDelta);
		this.model.riding = livingEntity.hasVehicle();
		this.model.child = livingEntity.isBaby();
		float h = MathHelper.lerpAngleDegrees(tickDelta, livingEntity.prevBodyYaw, livingEntity.bodyYaw);
		float j = MathHelper.lerpAngleDegrees(tickDelta, livingEntity.prevHeadYaw, livingEntity.headYaw);
		float k = j - h;
		float o;

		float m = MathHelper.lerp(tickDelta, livingEntity.prevPitch, livingEntity.getPitch());
		float p;

		o = this.getAnimationProgress(livingEntity, tickDelta);
		//this.setupTransforms(livingEntity, matrixStack, o, h, tickDelta);
		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		this.scale(livingEntity, matrixStack, tickDelta);
		matrixStack.translate(0.0D, -1.5010000467300415D, 0.0D);
		p = 0.0F;
		float q = 0.0F;

		this.model.animateModel(livingEntity, q, p, tickDelta);
		this.model.setAngles(livingEntity, q, p, o, k, m);

		
		switch (dt) {
		case BIO:
			renderModelDeathBio(livingEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
			break;
		case LASER:
			renderModelDeathLaser(livingEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
			break;
		case DEFAULT:
		case DISMEMBER:
		case GORE:
		default:
			break;
		}

		/*
		 * //TODO: RenderLayer for deathtype RenderLayer renderLayer =
		 * this.getRenderLayer(livingEntity, true, true, false);
		 * 
		 * 
		 * if (renderLayer != null) { VertexConsumer vertexConsumer =
		 * vertexConsumerProvider.getBuffer(renderLayer);
		 * 
		 * this.model.render(matrixStack, vertexConsumer, light,
		 * OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0f); }
		 * 
		 * if (!livingEntity.isSpectator()) { Iterator var23 = this.features.iterator();
		 * 
		 * while (var23.hasNext()) { FeatureRenderer<T, M> featureRenderer =
		 * (FeatureRenderer) var23.next(); featureRenderer.render(matrixStack,
		 * vertexConsumerProvider, light, livingEntity, q, p, tickDelta, o, k, m); } }
		 */

		matrixStack.pop();
		super.render(livingEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
	}

	protected void renderModelDeathBio(T entity, float yaw, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {
		
		float prog = ((float) entity.deathTime / (float) MAX_DEATH_TIME);
		float mainColor = (float) (1.0 - Math.pow(prog, 2.0));
		float mainAlpha = (float) Math.pow(1.0 - prog, 2.0);
		float overlayColor = (float) (0.5 + (Math.sin((Math.sqrt(prog) + 0.75) * 2.0 * Math.PI) / 2));
		
		//TODO 1.17: RenderLayer renderLayerBase = RenderLayer.getEntityAlpha(this.getTexture(entity), mainAlpha);
		RenderLayer renderLayerBase = RenderLayer.getEntityAlpha(this.getTexture(entity));
		RenderLayer renderLayerFX = RenderLayer.getItemEntityTranslucentCull(DeathEffectHandler.BIO_DEATH_TEXTURE);
//		RenderLayer renderLayerFX = TGRenderHelper.get_fx_layerForType(DeathEffectHandler.BIO_DEATH_TEXTURE,
//				RenderType.ADDITIVE);
		VertexConsumer vertexConsumerMain = vertexConsumerProvider.getBuffer(renderLayerBase);
		VertexConsumer vertexConsumerFx = vertexConsumerProvider.getBuffer(renderLayerFX);

		Random rand = new Random(entity.getId());

		if (renderLayerBase != null && renderLayerFX != null) {

			Iterable<ModelPart> parts = DeathEffectHandler.getModelParts(this.model);
			if (parts != null) {
				for (ModelPart part : parts) {
					matrixStack.push();

					float scale1 = 1.0f + (rand.nextFloat() * prog);
					float scale2 = 1.0f + (rand.nextFloat() * prog);
					float scale3 = 1.0f + (rand.nextFloat() * prog);
					
					//matrixStack.translate(-part.pivotX, -part.pivotY, -part.pivotZ);
					matrixStack.scale(scale1, scale2, scale3);
					//matrixStack.translate(part.pivotX, part.pivotY, part.pivotZ);

					
					part.render(matrixStack, vertexConsumerMain, light, OverlayTexture.DEFAULT_UV, mainColor, 1.0f,
							mainColor, mainAlpha);

					part.render(matrixStack, vertexConsumerFx, TGRenderHelper.BRIGHT_LIGHT, OverlayTexture.DEFAULT_UV,
							1, 1, 1, overlayColor);

					matrixStack.pop();
				}
			} else {
				matrixStack.push();

				float scale1 = 1.0f + (rand.nextFloat() * prog);
				float scale2 = 1.0f + (rand.nextFloat() * prog);
				float scale3 = 1.0f + (rand.nextFloat() * prog);
				
				//matrixStack.translate(-part.pivotX, -part.pivotY, -part.pivotZ);
				matrixStack.scale(scale1, scale2, scale3);
				// matrixStack.translate(part.pivotX, part.pivotY, part.pivotZ);

				model.render(matrixStack, vertexConsumerMain, light, OverlayTexture.DEFAULT_UV, mainColor, 1.0f,
						mainColor, mainAlpha);

				model.render(matrixStack, vertexConsumerFx, TGRenderHelper.BRIGHT_LIGHT, OverlayTexture.DEFAULT_UV,
						overlayColor, overlayColor, overlayColor, 1);

				matrixStack.pop();
			}
		}
	}
	
	protected void renderModelDeathLaser(T entity, float yaw, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {
		
		float prog = ((float) entity.deathTime / (float) MAX_DEATH_TIME);
		float mainColor = (float) (1.0 - Math.pow(prog, 2.0));
		float mainAlpha = (float) Math.pow(1.0 - prog, 2.0);
		float overlayColor = (float) (0.5 + (Math.sin((Math.sqrt(prog) + 0.75) * 2.0 * Math.PI) / 2));
		
		//TODO 1.17: RenderLayer renderLayerBase = RenderLayer.getEntityAlpha(this.getTexture(entity), mainAlpha);
		RenderLayer renderLayerBase = RenderLayer.getEntityAlpha(this.getTexture(entity));
		RenderLayer renderLayerFX = RenderLayer.getItemEntityTranslucentCull(DeathEffectHandler.LASER_DEATH_TEXTURE);
//		RenderLayer renderLayerFX = TGRenderHelper.get_fx_layerForType(DeathEffectHandler.BIO_DEATH_TEXTURE,
//				RenderType.ADDITIVE);
		VertexConsumer vertexConsumerMain = vertexConsumerProvider.getBuffer(renderLayerBase);
		VertexConsumer vertexConsumerFx = vertexConsumerProvider.getBuffer(renderLayerFX);

		if (renderLayerBase != null && renderLayerFX != null) {
			
			model.render(matrixStack, vertexConsumerMain, light, OverlayTexture.DEFAULT_UV, 1.0f, mainColor,
					mainColor, mainAlpha);

			model.render(matrixStack, vertexConsumerFx, TGRenderHelper.BRIGHT_LIGHT, OverlayTexture.DEFAULT_UV,
					1, 1, 1, overlayColor);
			
		}
	}
	


//	@Inject(at = @At("INVOKE"), method = "getTexture", cancellable = true)
//	public Identifier getTexture(T entity, CallbackInfoReturnable<Identifier> info) {
//		if (entity.deathTime > 0) {
//			Identifier texture = DeathEffectHandler.getTexture((LivingEntity)entity);
//			if (texture != null) {
//				info.setReturnValue(texture);
//				info.cancel();
//				return null;
//			}
//		}
//		return null;
//	}

}
