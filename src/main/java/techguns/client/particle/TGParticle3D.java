package techguns.client.particle;

import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.api.render.ITGModelPart;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.math.TGMatrixOps;
import techguns.util.MathUtil;

public class TGParticle3D extends TGParticle {

	protected static final float MODEL_SCALE = 0.0625f;
	
	public ModelPart model;
	public Identifier texture; //This needs to be stored per particle, since we are reusing the same particleSystemType for different entities
	
	float modelMinX = Float.POSITIVE_INFINITY;
	float modelMinY = Float.POSITIVE_INFINITY;
	float modelMinZ = Float.POSITIVE_INFINITY;
	float modelMaxX = Float.NEGATIVE_INFINITY;
	float modelMaxY = Float.NEGATIVE_INFINITY;
	float modelMaxZ = Float.NEGATIVE_INFINITY;
	
	float startSinkTime = 0.75f;
	float sinkDistance = 0.5f;
	
	int modelLight = 0;
	
	float pitch;
	float yaw;
	
	float anglePitch;
	float anglePitchRate;
	float anglePitchRateDamping;
	
	float angleRoll;
	float angleRollRate;
	float angleRollRateDamping;
	
	Vec3d prevDir;
	float prevPitch;
	float prevYaw;
	float prevRoll;
	
	public TGParticle3D(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn, TGParticleSystem particleSystem, ModelPart model, Identifier texture) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, particleSystem);
		this.model = model;
		this.texture = texture;
		this.calcModelBounds(model, 0f, 0f, 0f);
		this.setBoundingBox(new Box(modelMinX, modelMinY, modelMinZ, modelMaxX, modelMaxY, modelMaxZ));
		
		this.anglePitch = MathUtil.randomFloat(random, type.anglePitchMin, type.anglePitchMax);
	    this.anglePitchRate = MathUtil.randomFloat(random, type.anglePitchRateMin, type.anglePitchRateMax);
	    this.anglePitchRateDamping = MathUtil.randomFloat(random, type.anglePitchRateDampingMin, type.anglePitchRateDampingMax);
	    
	    this.angleRoll = MathUtil.randomFloat(random, type.angleRollMin, type.angleRollMax);
	    this.angleRollRate = MathUtil.randomFloat(random, type.angleRollRateMin, type.angleRollRateMax);
	    this.angleRollRateDamping = MathUtil.randomFloat(random, type.angleRollRateDampingMin, type.angleRollRateDampingMax);
	    
	    this.collidesWithWorld = true;
	    
		Vec3d dir = new Vec3d(xSpeedIn,  ySpeedIn, zSpeedIn).normalize();
		this.prevDir = dir;
		this.yaw = (float) (MathUtil.R2D*Math.atan2(dir.x, dir.z));
		this.pitch = (float) (MathUtil.R2D*Math.asin(dir.y));
	}
	
	protected void calcModelBounds(ModelPart model, float pX, float pY, float pZ) {
		ObjectList<ModelPart.Cuboid> cuboids = ((ITGModelPart)model).getCuboids();

		pX+= model.pivotX;
		pY+= model.pivotY;
		pZ+= model.pivotZ;
		
		for (ModelPart.Cuboid cube : cuboids) {
//			if (cube.minX-model.pivotX < modelMinX) modelMinX = cube.minX-model.pivotX;
//			if (cube.minY-model.pivotY < modelMinY) modelMinY = cube.minY-model.pivotY;
//			if (cube.minZ-model.pivotZ < modelMinZ) modelMinZ = cube.minZ-model.pivotZ;
//			
//			if (cube.maxX-model.pivotX > modelMaxX) modelMaxX = cube.maxX-model.pivotX;
//			if (cube.maxY-model.pivotY > modelMaxY) modelMaxY = cube.maxY-model.pivotY;
//			if (cube.maxZ-model.pivotZ > modelMaxZ) modelMaxZ = cube.maxZ-model.pivotZ;		
			
			if (cube.minX+pX < modelMinX) modelMinX = cube.minX+pX;
			if (cube.minY+pY < modelMinY) modelMinY = cube.minY+pY;
			if (cube.minZ+pZ < modelMinZ) modelMinZ = cube.minZ+pZ;
			
			if (cube.maxX+pX > modelMaxX) modelMaxX = cube.maxX+pX;
			if (cube.maxY+pY > modelMaxY) modelMaxY = cube.maxY+pY;
			if (cube.maxZ+pZ > modelMaxZ) modelMaxZ = cube.maxZ+pZ;		
		}
		
		ObjectList<ModelPart> children = ((ITGModelPart)model).getChildren();
		
		for (ModelPart child : children) {
			calcModelBounds(child, pZ, pY, pZ);
		}
	}
	
	
	
	@Override
	public void onUpdate() {
		this.prevPitch = this.pitch;
		this.prevYaw = this.yaw;
		this.prevRoll = this.angleRoll;
		
		
		super.onUpdate();
		
		if (!this.onGround) {
			this.modelLight = TGRenderHelper.getLightAtPos(this.getPos());
		}else {
			this.anglePitchRate *= this.angleRateDampingOnGround;
			this.angleRollRate *= this.angleRateDampingOnGround;
		}

		anglePitch = (anglePitch + anglePitchRate) % 360.0f;
		anglePitchRate *= anglePitchRateDamping;
		
		angleRoll = (angleRoll + angleRollRate) % 360.0f;
		angleRollRate *= angleRollRateDamping;
		
//		Vec3d dir;
//		if (!this.onGround) {
//			dir = (new Vec3d(this.prevPosX, this.prevPosY, this.prevPosZ).subtract(this.getPos())).normalize();
//			prevDir = dir;
//		}else {
//			dir = prevDir;
//		}
//		yaw = angle + (float) (MathUtil.R2D*Math.atan2(dir.x, dir.z));
//		pitch = anglePitch + (float) (MathUtil.R2D*Math.asin(dir.y));
		
		yaw = angle;
		pitch = anglePitch;
				
	}

	
	@Override
	public void doRender(Immediate vertexConsumerProvider, Entity entityIn, float partialTickTime, float rotX,
			float rotZ, float rotYZ, float rotXY, float rotXZ, MatrixStack matrices, Camera camera) {
		
		float progress = ((float)this.age+partialTickTime) / (float)this.maxAge;
		float sinkProgress = 0;
		if (startSinkTime > 0.0f && progress > startSinkTime) {
			sinkProgress = (progress-startSinkTime) / (1f-startSinkTime);
		}
		
		
		RenderLayer renderLayer = RenderLayer.getEntityCutoutNoCull(this.texture);
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
		
		matrices.push();
		
		//matrices.translate(this.posX()-model.pivotX, this.posY()-model.pivotY, this.posZ()-model.pivotZ);
		
		Vec3d camPos = camera.getPos();
        float fPosX = (float)(MathHelper.lerp((double)partialTickTime, this.prevPosX, this.x) - camPos.getX());
        float fPosY = (float)(MathHelper.lerp((double)partialTickTime, this.prevPosY, this.y) - camPos.getY());
        float fPosZ = (float)(MathHelper.lerp((double)partialTickTime, this.prevPosZ, this.z) - camPos.getZ());
		
		matrices.translate(fPosX, fPosY - sinkProgress*sinkDistance, fPosZ);
        
//        matrices.translate(fPosX-model.pivotX, fPosY-model.pivotY, fPosZ-model.pivotZ);
//        matrices.translate(fPosX, fPosY, fPosZ);
        float offsetX = (-(this.modelMinX + (this.modelMaxX - this.modelMinX)*0.5f) ) * MODEL_SCALE;
        float offsetY = (-(this.modelMinY + (this.modelMaxY - this.modelMinY)*0.5f) ) * MODEL_SCALE;
        float offsetZ = (-(this.modelMinZ + (this.modelMaxZ - this.modelMinZ)*0.5f) ) * MODEL_SCALE;

		

        Vec3d dir;
		if (!this.onGround) {
			dir = (new Vec3d(this.prevPosX, this.prevPosY, this.prevPosZ).subtract(this.getPos())).normalize();
			prevDir = dir;
		}else {
			dir = prevDir;
		}

		float yaw = (float)(MathHelper.lerp((double)partialTickTime, this.prevYaw, this.yaw));
		float pitch = (float)(MathHelper.lerp((double)partialTickTime, this.prevPitch, this.pitch));
		float roll = (float)(MathHelper.lerp((double)partialTickTime, this.prevRoll, this.angleRoll));
		
		TGMatrixOps.rotate(matrices, pitch, 1f, 0f, 0f);
		TGMatrixOps.rotate(matrices, yaw, 0f, 1f, 0f);
		TGMatrixOps.rotate(matrices, roll, 0f, 0f, 1f);
		
		matrices.translate(offsetX, offsetY, offsetZ);
		
		model.render(matrices, vertexConsumer, this.modelLight, OverlayTexture.DEFAULT_UV);
		
		matrices.pop();
	}
	
}
