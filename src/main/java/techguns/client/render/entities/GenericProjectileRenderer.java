package techguns.client.render.entities;

import java.util.Random;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import techguns.TGIdentifier;
import techguns.client.render.TGRenderHelper;
import techguns.entities.projectiles.GenericProjectile;

public class GenericProjectileRenderer extends RenderLateEntityRenderer<GenericProjectile>{
	private static final Identifier bulletTextures = new TGIdentifier("textures/entity/bullet1.png");
	private static final Identifier blasterTextures = new TGIdentifier("textures/fx/laser3.png");
	private static final Identifier advancedTextures = new TGIdentifier("textures/entity/bullet_blue.png");

	public GenericProjectileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	public Identifier getTexture(GenericProjectile entity) {
		switch (entity.getProjectileTypeEnum()){
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
		switch (entity.getProjectileTypeEnum()){
			case BLASTER:
			case ADVANCED:
			case DEFAULT:
			default:
				return TGRenderHelper.getProjectileAdditive(this.getTexture(entity));
			//default:
			//	return TGRenderHelper.getProjectileCutout(this.getTexture(entity));
		}
	}

	@Override
	public void renderLate(GenericProjectile entity, float yaw, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {
		
		long seed = entity.getId();
		Random rand = new Random(seed);
		float angleX = (float)rand.nextGaussian();
		
		if (entity.age >= 2 || (entity.age == 1 && tickDelta > 0.35f /*0.25f*/)) {

			//System.out.println("Yaw: "+ entity.getYaw() + " Pitch: "+ entity.getPitch());
			matrixStack.push();
			matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(
					MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
			matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(
					MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())));

	        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(360.0f * angleX));
			//matrixStack.multiply(Vector3d.POSITIVE_X.getDegreesQuaternion(45.0F));
			matrixStack.scale(0.05625F, 0.05625F, 0.05625F);
			matrixStack.translate(-4.0D, 0.0D, 0.0D);
			VertexConsumer vertexConsumer = vertexConsumerProvider
					.getBuffer(this.getRenderLayer(entity));
			MatrixStack.Entry entry = matrixStack.peek();
			Matrix4f model_mat = entry.getPositionMatrix();

			float length = 10f; //10f;
			float width = 1.25f; //1.5f;
			
			float u1 = 0.0f;
	        float u2 = 1.0f;
	        float v1 = 0.0f;
	        float v2 = 1.0f;
			
			for (int u = 0; u < 4; ++u) {
				matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45.0F));
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
		vertexConsumer.vertex(model_mat, x, y,  z).color(255, 255, 255, 255).texture(u, v).light(light).next();
	}

}
