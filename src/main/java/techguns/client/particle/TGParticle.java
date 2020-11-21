package techguns.client.particle;

import java.awt.Color;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.client.particle.TGParticleSystemType.AlphaEntry;
import techguns.client.particle.TGParticleSystemType.ColorEntry;
import techguns.client.render.TGRenderHelper;
import techguns.mixin.CameraAccessor;
import techguns.util.MathUtil;


/**
 * An actual spawned particle
 */
@Environment(EnvType.CLIENT)
public class TGParticle extends Particle implements ITGParticle {
	   
	int lifetime;
	
	float angle;
	float angleRate;
	float angleRateDamping;
	
	float size;
	float sizePrev;
	float sizeRate;
	float sizeRateDamping;
	
	float animationSpeed;
	
	double velX;
	double velY;
	double velZ;
	float velocityDamping;
	float velocityDampingOnGround;
	
	float systemVelocityFactor;
	
	TGParticleSystem particleSystem;
	TGParticleSystemType type;
	
	int variationFrame;
	
	protected double depth;
	
	protected boolean itemAttached=false;
	
	public TGParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn, TGParticleSystem particleSystem) {
		super((ClientWorld)worldIn, xCoordIn, yCoordIn, zCoordIn);
		this.velocityX = xSpeedIn;
		this.velocityY = ySpeedIn;
		this.velocityZ = zSpeedIn;

		this.particleSystem = particleSystem;
		this.type = particleSystem.type;
		this.init();
		}
		
		private void init() {
			this.lifetime = MathUtil.randomInt(random, type.lifetimeMin, type.lifetimeMax);
			this.maxAge = lifetime;
			this.size = MathUtil.randomFloat(random, type.sizeMin, type.sizeMax) * this.particleSystem.scale;
			this.size+= (this.particleSystem.startSize);
			this.sizeRate = MathUtil.randomFloat(random, type.sizeRateMin, type.sizeRateMax)  * this.particleSystem.scale;
			this.sizeRateDamping = MathUtil.randomFloat(random, type.sizeRateDampingMin, type.sizeRateDampingMax);
			this.animationSpeed = MathUtil.randomFloat(random, type.animationSpeedMin, type.animationSpeedMax);
			this.velocityDamping = MathUtil.randomFloat(random, type.velocityDampingMin, type.velocityDampingMax);
			this.systemVelocityFactor = MathUtil.randomFloat(random, type.systemVelocityFactorMin, type.systemVelocityFactorMax);
		    this.velocityDampingOnGround = MathUtil.randomFloat(random, type.velocityDampingOnGroundMin, type.velocityDampingOnGroundMax);
			
		    this.angle = MathUtil.randomFloat(random, type.angleMin, type.angleMax);
		    this.angleRate = MathUtil.randomFloat(random, type.angleRateMin, type.angleRateMax);
		    this.angleRateDamping = MathUtil.randomFloat(random, type.angleRateDampingMin, type.angleRateDampingMax);
		    
		    //System.out.printf("###INIT:Motion1=(%.2f / %.2f / %.2f)\n",this.motionX, this.motionY, this.motionZ);
		    
			this.velocityX+=(systemVelocityFactor*particleSystem.motionX());
			this.velocityY+=(systemVelocityFactor*particleSystem.motionY());
			this.velocityZ+=(systemVelocityFactor*particleSystem.motionZ());
			
			//System.out.printf("###INIT:Motion=(%.2f / %.2f / %.2f)\n",this.motionX, this.motionY, this.motionZ);
			//System.out.println("###INIT:VelType="+this.type.velocityType.toString());
			//System.out.printf("###INIT:Type.VelocityData=[%.2f, %.2f, %.2f]\n",this.type.velocityDataMin[0], this.type.velocityDataMin[1], this.type.velocityDataMin[2]);
			
			this.velX = this.velocityX;
			this.velY = this.velocityY;
			this.velZ = this.velocityZ;
			
			this.variationFrame = random.nextInt(type.frames);
			
		}
	

    public void onUpdate()
    {

		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;

		this.sizePrev = this.size;

		lifetime--;
		if (this.age++ >= this.maxAge) {
			this.markDead();
			return;
		}
		
		/*---
		 * Move with System
		 */
		if (this.type.particlesStickToSystem) {
			
			if (this.particleSystem.entity != null) {
				
				if (!this.particleSystem.entity.isAlive()) {
					this.markDead();
					return;
				}
				
				if (this.type.particlesMoveWithSystem && this.particleSystem.attachToHead && this.particleSystem.entity instanceof LivingEntity) {
					LivingEntity ent = (LivingEntity)this.particleSystem.entity;
					
					double p = ent.pitch*MathUtil.D2R;
					double y = ent.headYaw*MathUtil.D2R;
					
					double prevP = ent.prevPitch * MathUtil.D2R;
					double prevY =ent.prevHeadYaw * MathUtil.D2R;
					
					Vec3d offsetBase = this.particleSystem.entityOffset.add(this.particleSystem.type.offset);
					
					//ViewBobbing
					/*if (this.particleSystem.entity == Minecraft.getMinecraft().player
							&& Minecraft.getMinecraft().gameSettings.thirdPersonView == 0
							&& Minecraft.getMinecraft().gameSettings.viewBobbing) {
						Vec3d vec = setupViewBobbing(1.0f).scale(2.0);
						offsetBase = offsetBase.add(vec);
					}
					*/
					
					Vec3d offset = offsetBase.rotateX((float)-p);
					offset = offset.rotateY((float)-y);
					
					Vec3d offsetP = offsetBase.rotateX((float)-prevP);
					offsetP = offsetP.rotateY((float)-prevY);
										
					this.prevPosX = this.particleSystem.entity.prevX + offsetP.x;
					this.prevPosY = this.particleSystem.entity.prevY + ent.getEyeHeight(ent.getPose()) + offsetP.y;
					this.prevPosZ = this.particleSystem.entity.prevZ + offsetP.z;
					this.x = this.particleSystem.entity.getX() + offset.x;
					this.y = this.particleSystem.entity.getY() + ent.getEyeHeight(ent.getPose()) + offset.y;
					this.z = this.particleSystem.entity.getZ() + offset.z;
				}else {
				
					this.prevPosX = this.particleSystem.entity.prevX;
					this.prevPosY = this.particleSystem.entity.prevY;
					this.prevPosZ = this.particleSystem.entity.prevZ;
					this.x = this.particleSystem.entity.getX();
					this.y = this.particleSystem.entity.getY();
					this.z = this.particleSystem.entity.getZ();
				}
			}else {
				
				if (!this.particleSystem.isAlive()) {
					this.markDead();
					return;
				}
				
				this.x = this.particleSystem.posX();
				this.y = this.particleSystem.posY();
				this.z = this.particleSystem.posZ();
			}	
			
		}else if (this.type.particlesMoveWithSystem) {		
			double dP = (this.particleSystem.rotationPitch - this.particleSystem.prevRotationPitch)*MathUtil.D2R;
			double dY = (this.particleSystem.rotationYaw - this.particleSystem.prevRotationYaw)*MathUtil.D2R;
			
			Vec3d pos = new Vec3d(this.x,  this.y, this.z);
			Vec3d sysPos = new Vec3d(this.particleSystem.posX(), this.particleSystem.posY(), this.particleSystem.posZ());
			
			Vec3d offset = sysPos.subtract(pos);
			offset = offset.rotateY((float)-dY);
			offset = offset.rotateX((float)-dP);
			
			Vec3d motion = new Vec3d (this.velocityX, this.velocityY, this.velocityZ);
			motion = motion.rotateY((float)-dY);
			motion = motion.rotateX((float)-dP);
			
			this.x = sysPos.x+offset.x;
			this.y = sysPos.y+offset.y;
			this.z = sysPos.z+offset.z;
			
			this.velocityX = motion.x;
			this.velocityY = motion.y;
			this.velocityZ = motion.z;
		
		}
		
		
		/* -------------
		 * MOTION
		 */

		this.velocityX = velX;
		this.velocityY = velY;
		this.velocityZ = velZ;
		this.velocityY -= type.gravity;

		//System.out.printf("Velocity=(%.2f / %.2f / %.2f)\n",this.velX, this.velY, this.velZ);
		//System.out.printf("Motion=(%.2f / %.2f / %.2f)\n",this.motionX, this.motionY, this.motionZ);
		this.setPos(this.x+this.velocityX, this.y+this.velocityY, this.z+this.velocityZ);
		
		
		this.velX *= velocityDamping;
		this.velY *= velocityDamping;
		this.velZ *= velocityDamping;

		if (this.onGround) {
			this.velX *= velocityDampingOnGround;
			this.velY *= velocityDampingOnGround; // ?
			this.velZ *= velocityDampingOnGround;
			if (type.removeOnGround)
				this.markDead();
		}

		/* ------------
		 * SIZE
		 */
		size = Math.max(0.0f, size+sizeRate);
		sizeRate *= sizeRateDamping;
		
		/*
		 * ANGLE
		 */
		angle = (angle + angleRate) % 360.0f;
		angleRate *= angleRateDamping;
    }
    
    
	 /**
     * Renders the particle
     */
    public void renderParticle(VertexConsumerProvider.Immediate vertexConsumerProvider, Entity cameraEntity, float partialTickTime, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ, Matrix4f mat, Camera camera)
    {
    	float progress = ((float)this.age+partialTickTime) / (float)this.maxAge;
    	
    	preRenderStep(progress);   	
    	
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
        //this.particleScale = sizePrev + (size-sizePrev)*partialTickTime; 	
        
        CameraAccessor cam = (CameraAccessor) camera;
        
        float fscale = 0.1F * this.size;

        //float fPosX = (float)(this.prevPosX + (this.x - this.prevPosX) * (double)partialTickTime - (!this.itemAttached ? TGParticleManager.interpPosX :0));
        //float fPosY = (float)(this.prevPosY + (this.y - this.prevPosY) * (double)partialTickTime - (!this.itemAttached ? TGParticleManager.interpPosY :0));
        //float fPosZ = (float)(this.prevPosZ + (this.z - this.prevPosZ) * (double)partialTickTime - (!this.itemAttached ? TGParticleManager.interpPosZ :0));
            
        Vec3d camPos = camera.getPos();
        float fPosX = (float)(MathHelper.lerp((double)partialTickTime, this.prevPosX, this.x) - camPos.getX());
        float fPosY = (float)(MathHelper.lerp((double)partialTickTime, this.prevPosY, this.y) - camPos.getY());
        float fPosZ = (float)(MathHelper.lerp((double)partialTickTime, this.prevPosZ, this.z) - camPos.getZ());
        
        
        //float cam_y = cam.getCameraY() + (cam.getCameraY()-cam.getLastCameraY()) * partialTickTime;     
        //fPosY-=cam_y;
        
        
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
		
		RenderLayer layer = TGRenderHelper.get_fx_particlelayer(type.texture);
		VertexConsumer buffer = vertexConsumerProvider.getBuffer(layer);

        double a = (angle + (partialTickTime * angleRate)) * MathUtil.D2R;
		Vec3d p1, p2, p3, p4;
		
		if (this.type.groundAligned) {
			float s = fscale;
			p1 = new Vec3d(-s,0,-s);
			p2 = new Vec3d(s,0,-s);
			p3 = new Vec3d(s,0,s);
			p4 = new Vec3d(-s,0,s);
			if (a > 0.0001f) {
				p1 = p1.rotateY((float) a);
				p2 = p2.rotateY((float) a);
				p3 = p3.rotateY((float) a);
				p4 = p4.rotateY((float) a);
			}
		}else {
	        p1 = new Vec3d((double)(- rotX * fscale - rotXY * fscale), (double)(- rotZ * fscale), (double)(- rotYZ * fscale - rotXZ * fscale));
	        p2 = new Vec3d((double)(- rotX * fscale + rotXY * fscale), (double)( + rotZ * fscale), (double)( - rotYZ * fscale + rotXZ * fscale));
	        p3 = new Vec3d((double)( rotX * fscale + rotXY * fscale), (double)( + rotZ * fscale), (double)( + rotYZ * fscale + rotXZ * fscale));
	        p4 = new Vec3d((double)( rotX * fscale - rotXY * fscale), (double)( - rotZ * fscale), (double)( + rotYZ * fscale - rotXZ * fscale));        
			        
	        if (a > 0.0001f) {
		        Vec3d axis = p1.normalize().crossProduct(p2.normalize());
				double cosa = Math.cos(a);
				double sina = Math.sin(a);
		        
		        p1 = rotAxis(p1, axis, sina, cosa);
		        p2 = rotAxis(p2, axis, sina, cosa);
		        p3 = rotAxis(p3, axis, sina, cosa);
		        p4 = rotAxis(p4, axis, sina, cosa);     
	        }	        		
		}
	
		buffer.vertex(mat, (float)p1.x + fPosX, (float)p1.y + fPosY, (float)p1.z + fPosZ).texture(ua, va).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(240, 240).next();//.normal(0.0f, 1.0f, 0.0f).endVertex();
		buffer.vertex(mat, (float)p2.x + fPosX, (float)p2.y + fPosY, (float)p2.z + fPosZ).texture(ub, vb).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(240, 240).next();//.normal(0.0f, 1.0f, 0.0f).endVertex();
		buffer.vertex(mat, (float)p3.x + fPosX, (float)p3.y + fPosY, (float)p3.z + fPosZ).texture(uc, vc).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(240, 240).next();//.normal(0.0f, 1.0f, 0.0f).endVertex();
		buffer.vertex(mat, (float)p4.x + fPosX, (float)p4.y + fPosY, (float)p4.z + fPosZ).texture(ud, vd).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(240, 240).next();//.normal(0.0f, 1.0f, 0.0f).endVertex();

		vertexConsumerProvider.draw(layer);
    }
    
	/**
     * interpolate colors and alpha values
     */
    protected void preRenderStep(float progress) {
    	
		/* ------------------------
		 * INTERPOLATE COLOR VALUES
		 */
    	
		ColorEntry c1 = null;
		ColorEntry c2 = null;
    	if (type.colorEntries.size()==0) {
    		c1 =new ColorEntry(1.0f,1.0f,1.0f,0);
    		c2 = c1;
    	}else if (type.colorEntries.size() == 1) {
    		c1 = type.colorEntries.get(0);
    		c2 = c1;
    	}else {
    		c1 = type.colorEntries.get(0);
    		for (int i = 1; i < type.colorEntries.size(); i++) {
    			c2 = type.colorEntries.get(i);
				if (progress < c2.time) {
					break;
				}else {
					c1 = c2;
				}
			}
    	}
		float p = (progress-c1.time) / (c2.time-c1.time);		
		if (c1 != c2) {
			
			//RGB to HSB
			float[] hsb1 = Color.RGBtoHSB((int)(c1.r*255), (int)(c1.g*255), (int)(c1.b*255), null);
			float[] hsb2 = Color.RGBtoHSB((int)(c2.r*255), (int)(c2.g*255), (int)(c2.b*255), null);	
			//HSB to RGB;
			Color color = new Color(Color.HSBtoRGB(hsb1[0]*(1f-p) + hsb2[0]*p, hsb1[1]*(1f-p) + hsb2[1]*p, hsb1[2]*(1f-p) + hsb2[2]*p));
			this.colorRed = (float)color.getRed() / 255.0f;
			this.colorGreen = (float)color.getGreen() / 255.0f;
			this.colorBlue = (float)color.getBlue() / 255.0f;
		}else {
			this.colorRed = (float)c1.r;
			this.colorGreen = (float)c1.g;
			this.colorBlue = (float)c1.b;
		}
				
		/*-------------------------
		 * INTERPOLATE ALPHA VALUES
		 */
		AlphaEntry a1 = null;
		AlphaEntry a2 = null;
		if (type.alphaEntries.size() == 0) {
			this.colorAlpha = 1.0f;
		}else if (type.alphaEntries.size() == 1) {
			a1 = type.alphaEntries.get(0);
			this.colorAlpha = a1.alpha;
		}else {
			a1 = type.alphaEntries.get(0);
    		for (int i = 1; i < type.alphaEntries.size(); i++) {
    			a2 = type.alphaEntries.get(i);
				if (progress < a2.time) {
					break;
				}else {
					a1 = a2;
				}
			}
    		if (a1.time != a2.time) {
    			p = (progress-a1.time) / (a2.time-a1.time);		
    			//interpolate
    			this.colorAlpha = a1.alpha*(1f-p) + a2.alpha * p;
    		}else {
    			this.colorAlpha = a1.alpha;
    		}
		}
        
    }
	
	/*protected void enableBlendMode() {
		//GlStateManager.pushAttrib();
    	if (type.renderType != RenderType.SOLID) {
    		GlStateManager.enableBlend();
    		GlStateManager.depthMask(false);
    		//GlStateManager.disableAlpha();
    		//GL11.glEnable(GL11.GL_ALPHA_TEST);
    	}
        if (type.renderType == RenderType.ALPHA) {
        	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        } else if (type.renderType == RenderType.ADDITIVE || type.renderType==RenderType.NO_Z_TEST) {
        	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        }
        
        if (type.renderType==RenderType.NO_Z_TEST){
        	GlStateManager.depthMask(false);
        	GlStateManager.disableDepth();
        }
        
        if (type.renderType != RenderType.ALPHA_SHADED) TGRenderHelper.enableFXLighting();
	}
	
	protected void disableBlendMode() {
		if (type.renderType != RenderType.ALPHA_SHADED) TGRenderHelper.disableFXLighting();
		if (type.renderType != RenderType.SOLID) {
    		GlStateManager.disableBlend();
    		GlStateManager.depthMask(true);
    		//GlStateManager.disableAlpha();
    		//GL11.glDisable(GL11.GL_ALPHA_TEST);
    	}
		 if (type.renderType == RenderType.ALPHA) {
        	GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        } else if (type.renderType == RenderType.ADDITIVE || type.renderType==RenderType.NO_Z_TEST) {
        	GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
		
        if (type.renderType==RenderType.NO_Z_TEST){
        	GlStateManager.depthMask(true);
        	GlStateManager.enableDepth();
        }
		
		//GlStateManager.popAttrib();
	}*/
    

	public double posX() {
		return x;
	}
	
	public double posY() {
		return y;
	}
	
	public double posZ() {
		return z;
	}

	
	protected Vec3d rotAxis(Vec3d p1, Vec3d axis, double sina, double cosa) {	
		  Vec3d v1 = axis.crossProduct(p1);
		  double d1 = axis.dotProduct(p1);
		  return p1.multiply(cosa).add(v1.multiply(sina)).add(axis.multiply(d1*(1.0 - cosa)));	
	}

	@Override
	public Vec3d getPos() {
		return new Vec3d(this.x, this.y, this.z);
	}

	@Override
	public boolean shouldRemove() {
		return !this.isAlive();
	}

	@Override
	public void updateTick() {
		this.onUpdate();
	}

	@Override
	public void doRender(VertexConsumerProvider.Immediate vertexConsumerProvider, Entity entityIn, float partialTickTime, float rotX, float rotZ,
			float rotYZ, float rotXY, float rotXZ, Matrix4f mat, Camera camera) {
		this.renderParticle(vertexConsumerProvider, entityIn, partialTickTime, rotX, rotZ, rotYZ, rotXY, rotXZ, mat, camera);
	}

	
	@Override
	public Box getRenderBoundingBox(float ptt, Entity viewEnt) {
		double fPosX = (this.x-viewEnt.getX());
		double fPosY = (this.y-viewEnt.getY());
		double fPosZ = (this.z-viewEnt.getZ());
	    
		double s = size*0.5;
		return new Box(fPosX-s, fPosY-s, fPosZ-s, fPosX+s, fPosY+s, fPosZ+s);
	}

	@Override
	public double getDepth() {
		return this.depth;
	}

	@Override
	public void setDepth(double depth) {
		this.depth=depth;
	}
	
	/*private Vec3d setupViewBobbing(float ptt)
    {
		Entity cameraEnt = MinecraftClient.getInstance().getCameraEntity()
        if (cameraEnt instanceof PlayerEntity)
        {
        	PlayerEntity entityplayer = (PlayerEntity)cameraEnt;
            float f1 = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
            float f2 = -(entityplayer.distanceWalkedModified + f1 * ptt);
            float f3 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * ptt;
            float f4 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * ptt;

            float F1 = 1.0f; // (float) Keybinds.X;
            float F2 = 1.0f; //(float) Keybinds.Y;
            
//            GlStateManager.translate(MathHelper.sin(f2 * (float)Math.PI) * f3 * 0.5F * F1, -Math.abs(MathHelper.cos(f2 * (float)Math.PI) * f3) * F2, 0.0F);
//            GlStateManager.rotate(MathHelper.sin(f2 * (float)Math.PI) * f3 * 3.0F, 0.0F, 0.0F, 1.0F);
//            GlStateManager.rotate(Math.abs(MathHelper.cos(f2 * (float)Math.PI - 0.2F) * f3) * 5.0F, 1.0F, 0.0F, 0.0F);
//            GlStateManager.rotate(f4, 1.0F, 0.0F, 0.0F);

            Vec3d vec = new Vec3d(MathHelper.sin(f2 * (float)Math.PI) * f3 * 0.5F * F1,  -Math.abs(MathHelper.cos(f2 * (float)Math.PI) * f3) * F2, 0.0F);
            vec = MathUtil.rotateVec3dAroundZ(vec, MathHelper.sin(f2 * (float)Math.PI) * f3 * 3.0F * (float)MathUtil.D2R);
            return vec.rotatePitch(Math.abs(MathHelper.cos(f2 * (float)Math.PI - 0.2F) * f3) * 5.0F * (float)MathUtil.D2R).rotatePitch(f4 * (float)MathUtil.D2R);
            		
            		
        }else {
        	return new Vec3d(0,0,0);
        }
    }*/

	@Override
	public void setItemAttached() {
		this.itemAttached=true;
	}

	@Override
	public void markDead() {
		super.markDead();
		this.particleSystem=null;
	}


	@Override
	public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
		//We won't do shit here, since TG particles are rendered by WorldRendererMixin
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.CUSTOM;
	}

	
}
