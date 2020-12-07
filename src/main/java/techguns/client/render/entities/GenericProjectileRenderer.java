package techguns.client.render.entities;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import techguns.TGIdentifier;
import techguns.entities.projectiles.GenericProjectile;

public class GenericProjectileRenderer extends EntityRenderer<GenericProjectile>{
	private static final Identifier bulletTextures = new TGIdentifier("textures/entity/bullet1.png");
	private static final Identifier blasterTextures = new TGIdentifier("textures/fx/laser3.png");


	public GenericProjectileRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public Identifier getTexture(GenericProjectile entity) {
		switch (entity.getProjectileType()){
			case GenericProjectile.PROJECTILE_TYPE_BLASTER:
				return blasterTextures;
			case GenericProjectile.PROJECTILE_TYPE_DEFAULT:
			default:
				return bulletTextures;
		}

	}

	public RenderLayer getRenderLayer(GenericProjectile entity){
		switch (entity.getProjectileType()){
			case GenericProjectile.PROJECTILE_TYPE_BLASTER:
				return RenderLayer.getEntityAlpha(this.getTexture(entity), 0.85f);
			case GenericProjectile.PROJECTILE_TYPE_DEFAULT:
			default:
				return RenderLayer.getEntityCutout(this.getTexture(entity));
		}
	}

	@Override
	public void render(GenericProjectile entity, float yaw, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {
		
		if (entity.age >= 2 || (entity.age == 1 && tickDelta > 0.25f)) {

			matrixStack.push();
			matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(
					MathHelper.lerp(tickDelta, entity.prevYaw, entity.yaw) - 90.0F));
			matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(
					MathHelper.lerp(tickDelta, entity.prevPitch, entity.pitch)));

			matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(45.0F));
			matrixStack.scale(0.05625F, 0.05625F, 0.05625F);
			matrixStack.translate(-4.0D, 0.0D, 0.0D);
			VertexConsumer vertexConsumer = vertexConsumerProvider
					.getBuffer(RenderLayer.getEntityCutout(this.getTexture(entity)));
			MatrixStack.Entry entry = matrixStack.peek();
			Matrix4f model_mat = entry.getModel();
			Matrix3f normal_mat = entry.getNormal();

			int length = 8;
			int width = 2;
			
			float u1 = 0.0f;
	        float u2 = 1.0f;
	        float v1 = 0.0f;
	        float v2 = 1.0f;
			
			for (int u = 0; u < 4; ++u) {
				matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
				this.addVertex(model_mat, normal_mat, vertexConsumer, -length, -width, 0, u1,v1, 0, 1, 0, light);
				this.addVertex(model_mat, normal_mat, vertexConsumer, length, -width, 0, u2,v1, 0, 1, 0, light);
				this.addVertex(model_mat, normal_mat, vertexConsumer, length, width, 0, u2,v2, 0, 1, 0, light);
				this.addVertex(model_mat, normal_mat, vertexConsumer, -length, width, 0, u1,v2, 0, 1, 0, light);
			}

			matrixStack.pop();
		}
	}

	public void addVertex(Matrix4f model_mat, Matrix3f normal_mat, VertexConsumer vertexConsumer, int x, int y, int z,
			float u, float v, int nx, int ny, int nz, int light) {
		vertexConsumer.vertex(model_mat, (float) x, (float) y, (float) z).color(255, 255, 255, 255).texture(u, v)
				.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal_mat, (float) nx, (float) ny, (float) nz).next();
	}

}
