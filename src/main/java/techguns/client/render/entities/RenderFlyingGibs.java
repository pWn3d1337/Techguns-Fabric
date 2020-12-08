package techguns.client.render.entities;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import techguns.client.render.math.TGMatrixOps;
import techguns.entities.projectiles.FlyingGibs;

public class RenderFlyingGibs extends EntityRenderer<FlyingGibs> {

	public RenderFlyingGibs(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	
	@Override
	public void render(FlyingGibs entity, float yaw, float tickDelta, MatrixStack matrices,
			VertexConsumerProvider vertexConsumerProvider, int light) {

		ModelPart modelPart = entity.modelPart;
		Identifier texture = entity.texture;
		
		RenderLayer renderLayer = RenderLayer.getEntityCutoutNoCull(texture);
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
		
		matrices.push();
		
		matrices.translate(-modelPart.pivotX, -modelPart.pivotY, -modelPart.pivotZ);
		
		float angle = 0.0f;
		float rot_angle = 90.0f;
		if (entity.isOnGround()) {
			angle = 5 + ((float) entity.hitGroundTTL / (float) entity.maxTimeToLive) * 15.0f;
			rot_angle += ((float) (entity.maxTimeToLive - entity.hitGroundTTL) * angle);
			
			if (entity.timeToLive <= 20) {
				float offsetY = ((20-entity.timeToLive) + tickDelta) * -0.05f;
				matrices.translate(0, offsetY, 0);
			}

		} else {
			angle = 5 + ((float) entity.timeToLive / (float) entity.maxTimeToLive) * 15.0f;
			rot_angle += ((float) entity.age + tickDelta) * angle;
		}

		TGMatrixOps.rotate(matrices, rot_angle, (float) entity.rotationAxis.x, (float) entity.rotationAxis.y,
				(float) entity.rotationAxis.z);
		
		modelPart.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
		
		matrices.pop();
	}


	@Override
	public Identifier getTexture(FlyingGibs entity) {
		return entity.texture;
	}

}
