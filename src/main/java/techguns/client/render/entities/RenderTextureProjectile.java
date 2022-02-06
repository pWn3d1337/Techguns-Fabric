package techguns.client.render.entities;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.math.TGMatrixOps;

public class RenderTextureProjectile <T extends Entity> extends EntityRenderer<T> {

	protected Identifier textureLoc;
	protected float baseSize=0.1f;
	protected float scale=1.0f;

	public RenderTextureProjectile(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	public void render(T entity, float yaw, float tickDelta, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light) {
		if (textureLoc!=null) {
			  matrices.push();
			  //GlStateManager.translate(x, y, z);
			  //GlStateManager.enableRescaleNormal();
			  float scale = this.getScale(entity);
			  matrices.scale(baseSize*scale, baseSize*scale, baseSize*scale);
			  TGRenderHelper.enableFXLighting();
	         
			  VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.getRenderLayer(entity));
				
	          this.drawProjectile(matrices, vertexConsumer, light);
	          TGRenderHelper.disableFXLighting();
	          //GlStateManager.disableRescaleNormal();
	          matrices.pop();
			}
	}

	protected RenderLayer getRenderLayer(T entity) {
		return TGRenderHelper.get_fx_particlelayer(this.getTexture(entity));
	}
	
	protected float getScale(T entity) {
		return scale;
	}
	
	protected void drawProjectile(MatrixStack matrices, VertexConsumer vertexConsumer, int light)
    {
        float f = 0.f;
        float f1 = 1.f;
        float f2 = 0.f;
        float f3 = 1.f;
   
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        
        
        
        TGMatrixOps.rotate(matrices, 180.0F - this.dispatcher.camera.getYaw(), 0.0F, 1.0F, 0.0F);
        TGMatrixOps.rotate(matrices, -this.dispatcher.camera.getPitch(), 1.0F, 0.0F, 0.0F);
        
		MatrixStack.Entry entry = matrices.peek();
		Matrix4f model_mat = entry.getPositionMatrix();
		//Matrix3f normal_mat = entry.getNormal();
              
		vertexConsumer.vertex(model_mat,(0.0F - f5), (0.0F - f6), 0.0f).texture(f, f3).color(1f, 1f, 1f, 1f).light(light).next();
		vertexConsumer.vertex(model_mat,(f4 - f5), (0.0F - f6), 0.0f).texture(f1, f3).color(1f, 1f, 1f, 1f).light(light).next();
		vertexConsumer.vertex(model_mat,(f4 - f5), (f4 - f6), 0.0f).texture(f1, f2).color(1f, 1f, 1f, 1f).light(light).next();
		vertexConsumer.vertex(model_mat,(0.0f - f5), (f4 - f6), 0.0f).texture(f, f2).color(1f, 1f, 1f, 1f).light(light).next();

    }

	@Override
	public Identifier getTexture(T entity) {
		return textureLoc;
	}
}
