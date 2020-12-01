package techguns.client.render.entities;

import java.util.Random;

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
import net.minecraft.util.math.Vec3d;
import techguns.TGIdentifier;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.math.TGMatrixOps;
import techguns.entities.projectiles.BioGunProjectile;
import techguns.entities.projectiles.GenericBeamProjectile;
import techguns.entities.projectiles.GenericProjectile;

public class RenderGenericBeamProjectile extends EntityRenderer<GenericBeamProjectile> {
	
	protected static final Identifier beamTextureNDR = new TGIdentifier("textures/fx/nukebeam.png");
	
	public RenderGenericBeamProjectile(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	
	@Override
	public void render(GenericBeamProjectile entity, float yaw, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {
		
		//Generic stuff for all beam types
		 //Random rand = new Random(entity.getEntityId());
		 float prog = ((float)entity.age + tickDelta) / ((float)entity.maxTicks);		 
		 
		 Vec3d pos = entity.getPos();
		 float laser_pitch = entity.laserPitch;
		 float laser_yaw = entity.laserYaw;
		 
		 switch(entity.getProjectileType()) {
			case GenericBeamProjectile.BEAM_TYPE_NDR:
			default:
				float maxWidth = 10.0f;
				renderBeam(entity, prog, maxWidth, pos, laser_pitch, laser_yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
		 }
		 
	}
	
	
	protected void renderBeam(GenericBeamProjectile entity, float prog, float maxWidth, Vec3d pos, float pitch, float yaw, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {
		float distance = (float) entity.distance;
		float intensity = (float) ((Math.sin(Math.sqrt(prog)*Math.PI))*2);
		float width = maxWidth * intensity;
        
		// RENDER BEAM
        double UVscale = 2.0D; 
        int numFrames = 17;
        float frametime = 0.5f; //ticks per frame
        float u = (float) (distance / (width*8.0 * UVscale));
        	
        int frame = (int) ((((float)entity.age+tickDelta) * frametime) % numFrames);
        float v1 = (1.0f / numFrames) * frame;
        float v2 = (1.0f / numFrames) * (frame+1);
        
        VertexConsumer vertexConsumer = vertexConsumerProvider
				.getBuffer(TGRenderHelper.get_fx_renderlayer(getTexture(entity)));
       
        for (int i = 0; i < 2; ++i)
        {
       	 	TGMatrixOps.rotate(matrixStack, 90f, 1f, 0f, 0f);
            
			Matrix4f model_mat = matrixStack.peek().getModel();
       	 	
       	 	//POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL
       	 	vertexConsumer.vertex(model_mat, -distance, -width, 0.0f).texture((float)(0+i), v1).color(1.0f, 1.0f, 1.0f, intensity).light(light).next();
       	 	vertexConsumer.vertex(model_mat, 0f, -width, 0.0f).texture((float)(u+i), v1).color(1.0f, 1.0f, 1.0f, intensity).light(light).next();
       	 	vertexConsumer.vertex(model_mat, 0f, width, 0.0f).texture((float)(u+i), v2).color(1.0f, 1.0f, 1.0f, intensity).light(light).next();
       	 	vertexConsumer.vertex(model_mat, -distance, width, 0.0f).texture((float)(0+i), v2).color(1.0f, 1.0f, 1.0f, intensity).light(light).next(); 	 	
            
        }
	}
	

	@Override
	public Identifier getTexture(GenericBeamProjectile entity) {
		switch(entity.getProjectileType()) {
			case GenericBeamProjectile.BEAM_TYPE_NDR:
			default:
				return beamTextureNDR;
			
		}
	}	
	
}
