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
import techguns.client.render.TGRenderHelper;
import techguns.entities.projectiles.GenericProjectile;

public class GenericProjectileRenderer extends EntityRenderer<GenericProjectile>{
	private static final Identifier bulletTextures = new TGIdentifier("textures/entity/bullet1.png");
	private static final Identifier blasterTextures = new TGIdentifier("textures/fx/laser3.png");
	private static final Identifier advancedTextures = new TGIdentifier("textures/entity/bullet_blue.png");

	public GenericProjectileRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public Identifier getTexture(GenericProjectile entity) {
		switch (entity.getProjectileType()){
			case BLASTER:
				return blasterTextures;
			case ADVANCED:
				return advancedTextures;
			case DEFAULT:
			default:
				return bulletTextures;
		}

	}

	public RenderLayer getRenderLayer(GenericProjectile entity){
		switch (entity.getProjectileType()){
			case BLASTER:
			case ADVANCED:
				return TGRenderHelper.getProjectileAdditive(this.getTexture(entity));
			case DEFAULT:
			default:
				return TGRenderHelper.getProjectileCutout(this.getTexture(entity));
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
					.getBuffer(this.getRenderLayer(entity));
			MatrixStack.Entry entry = matrixStack.peek();
			Matrix4f model_mat = entry.getModel();

			float length = 10f;
			float width = 1.5f;
			
			float u1 = 0.0f;
	        float u2 = 1.0f;
	        float v1 = 0.0f;
	        float v2 = 1.0f;
			
			for (int u = 0; u < 4; ++u) {
				matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
				this.addVertex(model_mat, vertexConsumer, -length, -width, 0f, u1,v1, light);
				this.addVertex(model_mat, vertexConsumer, length, -width, 0f, u2,v1, light);
				this.addVertex(model_mat, vertexConsumer, length, width, 0f, u2,v2, light);
				this.addVertex(model_mat, vertexConsumer, -length, width, 0f, u1,v2, light);
			}

			matrixStack.pop();
		}
	}

	public void addVertex(Matrix4f model_mat, VertexConsumer vertexConsumer, float x, float y, float z,
			float u, float v, int light) {
		vertexConsumer.vertex(model_mat, x, y,  z).texture(u, v).color(255, 255, 255, 255).light(light).next();
	}

}
