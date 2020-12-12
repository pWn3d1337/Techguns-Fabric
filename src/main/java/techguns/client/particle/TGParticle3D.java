package techguns.client.particle;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.math.TGMatrixOps;
import techguns.util.MathUtil;

public class TGParticle3D extends TGParticle {

	public ModelPart model;
	public Identifier texture; //This needs to be stored per particle, since we are reusing the same particleSystemType for different entities
	
	float anglePitch;
	float anglePitchRate;
	float anglePitchRateDamping;
	
	float angleRoll;
	float angleRollRate;
	float angleRollRateDamping;
	
	
	public TGParticle3D(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn, TGParticleSystem particleSystem, ModelPart model, Identifier texture) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, particleSystem);
		this.model = model;
		this.texture = texture;
		
		this.anglePitch = MathUtil.randomFloat(random, type.anglePitchMin, type.anglePitchMax);
	    this.anglePitchRate = MathUtil.randomFloat(random, type.anglePitchRateMin, type.anglePitchRateMax);
	    this.anglePitchRateDamping = MathUtil.randomFloat(random, type.anglePitchRateDampingMin, type.anglePitchRateDampingMax);
	    
	    this.angleRoll = MathUtil.randomFloat(random, type.angleRollMin, type.angleRollMax);
	    this.angleRollRate = MathUtil.randomFloat(random, type.angleRollRateMin, type.angleRollRateMax);
	    this.angleRollRateDamping = MathUtil.randomFloat(random, type.angleRollRateDampingMin, type.angleRollRateDampingMax);
	    
	}
	
	
	
	@Override
	public void onUpdate() {
		super.onUpdate();

		anglePitch = (anglePitch + anglePitchRate) % 360.0f;
		anglePitchRate *= anglePitchRateDamping;
		
		angleRoll = (angleRoll + angleRollRate) % 360.0f;
		angleRollRate *= angleRollRateDamping;
	}

	
	@Override
	public void doRender(Immediate vertexConsumerProvider, Entity entityIn, float partialTickTime, float rotX,
			float rotZ, float rotYZ, float rotXY, float rotXZ, MatrixStack matrices, Camera camera) {
		
		RenderLayer renderLayer = RenderLayer.getEntityCutoutNoCull(this.texture);
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
		
		matrices.push();
		
		//matrices.translate(this.posX()-model.pivotX, this.posY()-model.pivotY, this.posZ()-model.pivotZ);
		
		Vec3d camPos = camera.getPos();
        float fPosX = (float)(MathHelper.lerp((double)partialTickTime, this.prevPosX, this.x) - camPos.getX());
        float fPosY = (float)(MathHelper.lerp((double)partialTickTime, this.prevPosY, this.y) - camPos.getY());
        float fPosZ = (float)(MathHelper.lerp((double)partialTickTime, this.prevPosZ, this.z) - camPos.getZ());
		
		matrices.translate(fPosX-model.pivotX, fPosY-model.pivotY, fPosZ-model.pivotZ);
		
		Vec3d dir = (new Vec3d(this.prevPosX, this.prevPosY, this.prevPosZ).subtract(this.getPos())).normalize();
		float yaw = (float) (MathUtil.R2D*Math.atan2(dir.x, dir.z));
		float pitch = (float) (MathUtil.R2D*Math.asin(dir.y));

		TGMatrixOps.rotate(matrices, this.anglePitch + pitch, 1f, 0f, 0f);
		TGMatrixOps.rotate(matrices, this.angle + yaw, 0f, 1f, 0f);
		TGMatrixOps.rotate(matrices, this.angleRoll, 0f, 0f, 1f);
		
		int light = TGRenderHelper.getLightAtPos(this.getPos());
		model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
		
		matrices.pop();
	}
	
}
