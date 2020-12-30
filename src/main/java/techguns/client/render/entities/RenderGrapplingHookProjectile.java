package techguns.client.render.entities;

import java.util.Random;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import techguns.TGIdentifier;
import techguns.client.models.projectiles.ModelRocket;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.math.TGMatrixOps;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.GrapplingHookProjectile;
import techguns.entities.projectiles.GrapplingHookProjectile.GrapplingStatus;
import techguns.entities.projectiles.RocketProjectile;
import techguns.util.MathUtil;

public class RenderGrapplingHookProjectile extends EntityRenderer<GrapplingHookProjectile>{

	public RenderGrapplingHookProjectile(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	private Identifier texture_projectile = new TGIdentifier("textures/guns/rocket.png");
	private Identifier texture_chain = new TGIdentifier("textures/entity/chain.png");
	
	private Model model = new ModelRocket();

	
	@Override
	public void render(GrapplingHookProjectile entity, float yaw, float tickDelta, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light) {
		
		//Render Projectile
		matrices.push();
		
		//System.out.println("Render Grappling Hook with TickDelta = "+tickDelta);
	
       	TGMatrixOps.rotate(matrices, entity.prevYaw + (entity.yaw-entity.prevYaw)*tickDelta -90.0f, 0F, 1F, 0F);
       	TGMatrixOps.rotate(matrices, entity.prevPitch + (entity.pitch-entity.prevPitch)*tickDelta, 0F, 0F, 1F);

		matrices.scale(0.5f, 0.5f, 0.5f);

		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity)));	
		
		model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

		matrices.pop();
		
		//Render Chain
		if (entity.status != GrapplingStatus.NONE) {
			Entity shooter = entity.getOwner();
			if (shooter != null) {
				matrices.push();
				Vec3d src = shooter.getCameraPosVec(tickDelta);
				src = src.add(new Vec3d(0, -0.15, 0)); //Offset to avoid stabbing laser into the eye; TODO: Firepos offset
				
				double ex = MathHelper.lerp(tickDelta, entity.prevX, entity.getX());
				double ey = MathHelper.lerp(tickDelta, entity.prevY, entity.getY());
				double ez = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ());
				
				Vec3d dst = new Vec3d(ex, ey, ez);
				Vec3d diff = dst.subtract(src);
				float distance = (float) diff.length();
				Vec3d dir = diff.normalize();
				
				double c_pitch = MathUtil.R2D*Math.asin(dir.getY());
				double c_yaw = MathUtil.R2D*Math.atan2(dir.getX(), dir.getZ());
				float width = 0.035f;
				
		       	matrices.translate(src.x-ex, src.y-ey, src.z-ez);
				
				TGMatrixOps.rotate(matrices, (float)c_yaw -90.0f, 0F, 1F, 0F);
		       	TGMatrixOps.rotate(matrices, (float)c_pitch, 0F, 0F, 1F);
		       	TGMatrixOps.rotate(matrices, 45f, 1f, 0f, 0f);
		       	Matrix4f model_mat = matrices.peek().getModel();
		       	
		       	float u1 = 0f;
		       	float v1 = 0f;
		       	float u2 = (distance / width) * 0.25f;
		       	float v2 = 1f;
				
		       	
		       	vertexConsumer = vertexConsumers.getBuffer(TGRenderHelper.get_fx_renderlayer_alpha(texture_chain));	
		       	
		       	for (int i = 0; i < 2; ++i) {
					TGMatrixOps.rotate(matrices, 90f, 1f, 0f, 0f);

					// POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL
					vertexConsumer.vertex(model_mat, 0, -width, 0.0f).texture(u1, v1)
							.color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
					vertexConsumer.vertex(model_mat, distance, -width, 0.0f).texture(u2, v1)
							.color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
					vertexConsumer.vertex(model_mat, distance, width, 0.0f).texture(u2, v2)
							.color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();
					vertexConsumer.vertex(model_mat, 0, width, 0.0f).texture(u1, v2)
							.color(1.0f, 1.0f, 1.0f, 1.0f).light(light).next();

				}
		       	
				matrices.pop();
			}
		}
	}




	@Override
	public Identifier getTexture(GrapplingHookProjectile entity) {
		return texture_projectile;
	}

}
