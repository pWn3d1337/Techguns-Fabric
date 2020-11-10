package techguns.client.particle;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.client.render.TGRenderHelper;
import techguns.mixin.CameraAccessor;

public class TGParticleStreak extends TGParticle{

	protected TGParticleStreak prev;
	protected TGParticleStreak next;
	
	protected Vec3d pos1; //This streak segment's vertices
	protected Vec3d pos2;
	protected float particleScale=1.0f; 
	
	public TGParticleStreak(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn, TGParticleSystem particleSystem) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, particleSystem);
	}

	/**
     * Renders the particle
     */
	 @Override
		public void renderParticle(VertexConsumerProvider.Immediate vertexConsumerProvider, Entity cameraEntity, float partialTickTime, float rotX,
				float rotZ, float rotYZ, float rotXY, float rotXZ, Matrix4f mat, Camera camera) {
    	float progress = ((float)this.age+partialTickTime) / (float)this.maxAge;
    	
    	preRenderStep(progress);
    	
    	if (this.next == null) {
    		this.colorAlpha = 0.0f;
    	}
    	
		/*-------------------
		 * ANIMATION
		 */	
		int currentFrame = 0;
        if (type.hasVariations) {
        	currentFrame = variationFrame;
        }else {
        	currentFrame = ((int)((float)type.frames*(progress * this.animationSpeed))) % type.frames;
        }
    	
    	/* -------------
         * RENDER PARTICLE
         */
        this.particleScale = sizePrev + (size-sizePrev)*partialTickTime;
    	
        float fscale = 0.1F * this.particleScale;

        CameraAccessor cam = (CameraAccessor) camera;
        float cam_y = cam.getCameraY() + (cam.getCameraY()-cam.getLastCameraY()) * partialTickTime;
        
        float fPosX = (float)(this.prevPosX + (this.x - this.prevPosX) * (double)partialTickTime - TGParticleManager.interpPosX);
        float fPosY = (float)(this.prevPosY + (this.y - this.prevPosY) * (double)partialTickTime - TGParticleManager.interpPosY);
        float fPosZ = (float)(this.prevPosZ + (this.z - this.prevPosZ) * (double)partialTickTime - TGParticleManager.interpPosZ);

        
        //fPosY -= cam_y;
        //float r = fscale;
        
		int col = currentFrame % type.columns;
		int row = (currentFrame / type.columns);
		
		float u = 1.f/type.columns;
		float v = 1.f/type.columns;
		
		float U1 = col*u;
		float V1 = row*v;
		float U2 = (col+1)*u;
		float V2 = (row+1)*v;
		
		float ua, va, ub, vb, uc, vc, ud, vd;
		ua=U2; va=V2; ub = U2; vb= V1; uc = U1; vc = V1; ud=U1; vd = V2;
		

        //double a = (angle + (partialTickTime * angleRate)) * MathUtil.D2R;
		Vec3d p1, p2, p3, p4;
				
        if (prev == null) {
            this.pos1 = null; //new Vec3d(fPosX, fPosY, fPosZ);
            this.pos2 = null; //new Vec3d(fPosX, fPosY, fPosZ);
        }else {
        	Vec3d v_view = camera.getFocusedEntity().getCameraPosVec(partialTickTime);
        	Vec3d v_prev = new Vec3d(prev.x, prev.y, prev.z).subtract(camera.getPos());
    		//Vec3d v_view = ClientProxy.get().getPlayerClient().getLook(partialTickTime);
    		
    		//Vec3d v_prev = new Vec3d(prev.x, prev.y, prev.z).subtract(ClientProxy.get().getPlayerClient().getPositionVector());

    		Vec3d v_dir = v_prev.subtract(fPosX, fPosY, fPosZ).normalize();
            
    		Vec3d v_cross = v_view.crossProduct(v_dir).normalize();
    		
            p1 = new Vec3d(v_cross.x*fscale + fPosX, v_cross.y*fscale  + fPosY, v_cross.z*fscale  + fPosZ);
            p2 = new Vec3d(v_cross.x* -fscale + fPosX, v_cross.y* -fscale  + fPosY, v_cross.z* -fscale  + fPosZ);
        	
            this.pos1 = p1;
            this.pos2 = p2;
            
            RenderLayer layer = TGRenderHelper.get_fx_particlelayer(type.texture);
    		VertexConsumer buffer = vertexConsumerProvider.getBuffer(layer);
            
            float fscaleP = prev.particleScale *0.1f;
            
            if (prev.pos1 != null && prev.pos2 != null) {
            	p3 = prev.pos2;
            	p4 = prev.pos1;
            }else {
            	p4 = new Vec3d(v_cross.x*fscaleP + v_prev.x, v_cross.y*fscaleP  + v_prev.y, v_cross.z*fscaleP  + v_prev.z);
                p3 = new Vec3d(v_cross.x* -fscaleP + v_prev.x, v_cross.y* -fscaleP  + v_prev.y, v_cross.z* -fscaleP  + v_prev.z);
                
                prev.pos1 = p4;
                prev.pos2 = p3;
            }

			buffer.vertex(mat, (float)p1.x, (float)p1.y, (float)p1.z).texture(ua, va).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(0, 240).next(); //.normal(0.0f, 1.0f, 0.0f).endVertex();
			buffer.vertex(mat, (float)p2.x, (float)p2.y, (float)p2.z).texture(ub, vb).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(0, 240).next(); //.normal(0.0f, 1.0f, 0.0f).endVertex();
			buffer.vertex(mat, (float)p3.x, (float)p3.y, (float)p3.z).texture(uc, vc).color(prev.colorRed, prev.colorGreen, prev.colorBlue, prev.colorAlpha).light(0, 240).next(); //.normal(0.0f, 1.0f, 0.0f).endVertex();
			buffer.vertex(mat, (float)p4.x, (float)p4.y, (float)p4.z).texture(ud, vd).color(prev.colorRed, prev.colorGreen, prev.colorBlue, prev.colorAlpha).light(0, 240).next(); //.normal(0.0f, 1.0f, 0.0f).endVertex();
	
        }

    }

	public TGParticleStreak getPrev() {
		return prev;
	}

	public void setPrev(TGParticleStreak prev) {
		this.prev = prev;
	}

	public TGParticleStreak getNext() {
		return next;
	}

	public void setNext(TGParticleStreak next) {
		this.next = next;
	}

	@Override
	public boolean doNotSort() {
		return true;
	}
	
}
