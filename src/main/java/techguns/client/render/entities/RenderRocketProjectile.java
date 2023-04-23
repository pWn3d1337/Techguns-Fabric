package techguns.client.render.entities;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import techguns.TGIdentifier;
import techguns.client.models.projectiles.ModelRocket;
import techguns.client.render.math.TGMatrixOps;
import techguns.entities.projectiles.RocketProjectile;

public class RenderRocketProjectile extends EntityRenderer<RocketProjectile>{

	public RenderRocketProjectile(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	private float scale;
	private Identifier textureLoc = new TGIdentifier("textures/guns/rocket.png");
	//private Identifier textureLocNuke = new TGIdentifier("textures/guns/rocket_nuke.png");
	//private Identifier textureLocHV = new TGIdentifier("textures/guns/rocket_hv.png");
	
	private Model model = new ModelRocket();

	
	@Override
	public void render(RocketProjectile entity, float yaw, float tickDelta, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light) {
		
		matrices.push();
		
		//GlStateManager.translate(par2,par4,par6);
		//matrices.translate(x, y, z);
	
       	TGMatrixOps.rotate(matrices, entity.prevYaw + (entity.getYaw()-entity.prevYaw)*tickDelta -90.0f, 0F, 1F, 0F);
       	TGMatrixOps.rotate(matrices, entity.prevPitch + (entity.getPitch()-entity.prevPitch)*tickDelta, 0F, 0F, 1F);

		matrices.scale(0.9f, 0.9f, 0.9f);
		//model.render(par1Entity, 0, 0, 0, 0, 0, 0.0625F);

		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity)));	
		
		model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

		matrices.pop();
	}




	@Override
	public Identifier getTexture(RocketProjectile entity) {
		/*if(entity instanceof RocketProjectileNuke) {
			return textureLocNuke;
		} else if (entity instanceof RocketProjectileHV || entity instanceof GuidedMissileProjectileHV) {
			return textureLocHV;
		}*/
		return textureLoc;
	}
}
