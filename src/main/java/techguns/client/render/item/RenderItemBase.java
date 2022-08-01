package techguns.client.render.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import techguns.api.render.IItemRenderer;
import techguns.client.Keybinds;
import techguns.client.models.ModelMultipart;
import techguns.client.render.math.TGMatrixOps;


public class RenderItemBase implements IItemRenderer {

	public static final float SCALE = 0.0625f;

	protected ModelMultipart model;
	protected Identifier texture;
	
	protected float baseScale = 1.0f;

	protected float scale_thirdp = 0.35f;
	protected float scale_ground = 0.5f;
	protected float scale_ego = 0.5f;
	protected float scale_gui = 0.4f;
	protected float scale_itemframe = 0.5f;

	protected float[] translateBase = { 0f, 0f, 0f };

	protected float[][] translateType = { { 0f, 0f, 0f }, // TRANSLATE FIRST
			// PERSON
			{ 0f, 0f, 0f }, // TRANSLATE THIRD PERSON
			{ 0f, 0f, 0f }, // TRANSLATE GUI
			{ 0f, 0f, 0f }, // TRANSLATE GROUND
			{ 0f, 0f, -0.05f } // TRANLATE FIXED (frame)
	};

	protected int parts = 1;

	protected String ambientParticleFX=null;
	
	public RenderItemBase(ModelMultipart model, Identifier texture) {
		super();
		this.model = model;
		this.texture = texture;
	}

	public RenderItemBase setGUIScale(float guiscale) {
		this.scale_gui = guiscale;
		return this;
	}
	
	public RenderItemBase setFirstPersonScale(float scale) {
		this.scale_ego = scale;
		return this;
	}
	
	public RenderItemBase setGroundAndFrameScale(float scale) {
		this.scale_ground = scale;
		this.scale_itemframe=scale;
		return this;
	}

	/**
	 * Set the basic translation applied to ALL types
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public RenderItemBase setBaseTranslation(float x, float y, float z) {
		this.translateBase[0] = x;
		this.translateBase[1] = y;
		this.translateBase[2] = z;
		return this;
	}

	/**
	 * TRANSLATE FIRST PERSON x,y,z
	 * TRANSLATE THIRD PERSON x,y,z
	 * TRANSLATE GUI x,y,z
	 * TRANSLATEGROUND x,y,z
	 * TRANLATE FIXED (frame) x,y,z
	 * 
	 * Left hand gets automatically mirrored
	 * 
	 * @param translations
	 *            - must be a float[5][3]
	 * @return
	 */
	public RenderItemBase setTransformTranslations(float[][] translations) {
		this.translateType = translations;
		return this;
	}
	
	public RenderItemBase setBaseScale(float baseScale) {
		this.baseScale = baseScale;
		return this;
	}

	protected float getScaleFactorFromTransform(ModelTransformation.Mode transform) {
		switch (transform) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			return baseScale * scale_ego;

		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
			return baseScale * scale_thirdp;

		case GUI:
			return baseScale * scale_gui;

		case GROUND:
			return baseScale * scale_ground;

		case FIXED:
			return baseScale * scale_itemframe;

		default:
			return baseScale;

		}
	}

	protected void applyTranslation(MatrixStack matrices, ModelTransformation.Mode transform) {
		int index = -1;
		boolean flip = false;

		switch (transform) {
		case FIRST_PERSON_LEFT_HAND:
			flip = true; // fallthrough
		case FIRST_PERSON_RIGHT_HAND:
			index = 0;
			break;

		case THIRD_PERSON_LEFT_HAND:
			flip = true; // fallthrough
		case THIRD_PERSON_RIGHT_HAND:
			index = 1;
			break;
		case GUI:
			index = 2;
			break;
		case GROUND:
			index = 3;
			break;
		case FIXED:
			index = 4;
			break;
		default:
			break;
		}
		if (index >= 0) {
			float mirror = flip?-1.0f:1.0f;

			//DEBUG
//			double xoffset = Keybinds.OFFSET_X;
//			double yoffset = Keybinds.OFFSET_Y;
//			double zoffset = Keybinds.OFFSET_Z;
//			matrices.translate((translateType[index][0] + xoffset)*mirror, translateType[index][1] + yoffset, translateType[index][2] + zoffset);

			matrices.translate((translateType[index][0])*mirror, translateType[index][1], translateType[index][2]);
		}
	}
	
	/**
	 * center the model
	 */
	protected void applyBaseTranslation(MatrixStack matrices){
		matrices.translate(this.translateBase[0], this.translateBase[1], this.translateBase[2]);
		//GlStateManager.translate(this.translateBase[0]+Keybinds.X, this.translateBase[1]+Keybinds.Y, this.translateBase[2]+Keybinds.Z);
	}
	
	
	@Override
	public void renderItem(LivingEntity elb, ModelTransformation.Mode transform, MatrixStack matrices, ItemStack stack, boolean leftHanded, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel bakedModel) {
		
			matrices.push();

			MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
			
			this.applyTranslation(matrices, transform);

			if (ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND == transform || ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND == transform) {

			} else if (ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND == transform || ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND == transform) {

			} else if (ModelTransformation.Mode.GUI == transform) {
				TGMatrixOps.rotate(matrices, 40.0f, 0, 1f, 0);
				TGMatrixOps.rotate(matrices, 20.0f, 1f, 0, 0);

			} else if (ModelTransformation.Mode.GROUND == transform) {

			} else if (ModelTransformation.Mode.FIXED == transform) {
				TGMatrixOps.rotate(matrices, -90.0f, 0, 1.0f, 0);
			}

			this.setBaseScale(elb,matrices, transform);
			this.setBaseRotation(matrices, transform);
			this.applyBaseTranslation(matrices);
			
			for (int i = 0; i < parts; i++) {
				//model.render(elb, 0, 0, 0, 0, 0, SCALE, 0, 0, transform, i, 0, 0f);
				model.render(elb, matrices, vertexConsumers.getBuffer(model.getLayerForPart(null, stack, texture,i)), 0, 0f, transform, i, 0f, 0f, light, overlay);
			}
			//this.renderItemParticles(elb, transform, ClientProxy.get().PARTIAL_TICK_TIME); TODO
			
			matrices.pop();

	}

	protected void setBaseScale(LivingEntity entity, MatrixStack matrices, ModelTransformation.Mode transform) {
		float scale = getScaleFactorFromTransform(transform);
		
		/*if( entity!=null && entity instanceof INPCTechgunsShooter) {
			INPCTechgunsShooter shooter = (INPCTechgunsShooter) entity;
			scale *=shooter.getGunScale();
		}*/ //TODO
		
		matrices.scale(scale, scale, scale);
	}

	protected void setBaseRotation(MatrixStack matrices, ModelTransformation.Mode transform) {
		TGMatrixOps.rotate(matrices, -180.0f, 1.0f, 0, 0);
		TGMatrixOps.rotate(matrices, 180.0f, 0f, 1.0f, 0);
	}

	//TODO
/*	protected void renderItemParticles(EntityLivingBase ent, TransformType transform, float ptt) {
		
		EnumHand hand = EnumHand.MAIN_HAND;
		EnumHandSide handSide;
		if (transform == TransformType.FIRST_PERSON_LEFT_HAND || transform == TransformType.THIRD_PERSON_LEFT_HAND) {
			if(ent.getPrimaryHand() == EnumHandSide.RIGHT) {
				hand = EnumHand.OFF_HAND;
			}
			handSide = EnumHandSide.LEFT;
		} else if (transform == TransformType.FIRST_PERSON_RIGHT_HAND || transform == TransformType.THIRD_PERSON_RIGHT_HAND) {
			if(ent.getPrimaryHand() == EnumHandSide.LEFT) {
				hand = EnumHand.OFF_HAND;
			}
			handSide = EnumHandSide.RIGHT;
		} else {
			return;
		}
		
		
		List<ITGParticle> particles = null;
		if(ent instanceof EntityPlayer) {
			if(hand==EnumHand.MAIN_HAND) {
				particles = TGExtendedPlayerClient.get((EntityPlayer) ent).getEntityParticlesMH();
			} else {
				particles = TGExtendedPlayerClient.get((EntityPlayer) ent).getEntityParticlesOH();
			}
		} else if (ent instanceof INPCTechgunsShooter){
			if (hand==EnumHand.MAIN_HAND) {
				particles = TGShooterValues.get(ent).getEntityParticlesMH();
			} else {
				particles = TGShooterValues.get(ent).getEntityParticlesOH();
			}
		} 
			
		if (particles != null && !particles.isEmpty()) {
			GlStateManager.disableCull();
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();
			
			//Vec3d angles = getTransformAngles(ent, handSide, ptt);
			//System.out.println(String.format("angles: %.3f, %.3f, %.3f", angles.x, angles.y, angles.z));
			
			//Entity entityIn = Minecraft.getMinecraft().getRenderViewEntity();
			
//			float f1 = MathHelper.cos(entityIn.rotationYaw * 0.017453292F - (float)angles.y);
//	        float f2 = MathHelper.sin(entityIn.rotationYaw * 0.017453292F - (float)angles.y);
//	        float f3 = -f2 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F - (float)angles.x);
//	        float f4 = f1 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F - (float)angles.x);
//	        float f5 = MathHelper.cos(entityIn.rotationPitch * 0.017453292F - (float) angles.x);
			
			particles.forEach(p -> {
				//System.out.println("Render Particle!");
				//System.out.println("Pos:"+p.getPos());
					
				//Align to Weapon
			   p.doRender(buffer, ent, ptt, 1.0f, 1.0f, 0, 0, 0);				

		       // p.doRender(buffer, entityIn, ptt, f1, f5, f2, f3, f4);
			   //p.doRender(buffer, ent, ptt, 1.0f - (float)angles.x, 1.0f - (float)angles.z, 0, 0, 0);
			});
			GlStateManager.enableCull();
		}
		
	}*/ 

	public String getAmbientParticleFX() {
		return ambientParticleFX;
	}

	public RenderItemBase setAmbientParticleFX(String ambientParticleFX) {
		this.ambientParticleFX = ambientParticleFX;
		return this;
	}
	
	/*public static Vec3d getTransformAngles(LivingEntity entity, EnumHandSide hand, float ptt) {
		float limbSwing = entity.limbSwing - entity.limbSwingAmount * (1.0F - ptt);
		float limbSwingAmount = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * ptt;

        if (entity.isChild())
        {
            limbSwing *= 3.0F;
        }
        
        if (limbSwingAmount > 1.0F)
        {
            limbSwingAmount = 1.0F;
        }
        
        //TODO: Elytra Flying
        float f = 1.0f;
        float rAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        float lAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        float rAngleY = 0.0F;
        float lAngleY = 0.0F;
        float rAngleZ = 0.0F;
        float lAngleZ = 0.0F;
        
        if (entity.isRiding())
        {
            rAngleX += -((float)Math.PI / 5F);
            lAngleX += -((float)Math.PI / 5F);
        }
        
        ArmPose leftArmPose = ArmPose.EMPTY;
        ArmPose rightArmPose = ArmPose.ITEM;
        
        ItemStack stack = entity.getHeldItemMainhand();
	 	if(!stack.isEmpty() && stack.getItem() instanceof GenericGun && ((GenericGun) stack.getItem()).hasBowAnim()){
	 		if (entity.getPrimaryHand()==EnumHandSide.RIGHT) {
	 			rightArmPose = ArmPose.BOW_AND_ARROW;
	 		} else {
	 			leftArmPose = ArmPose.BOW_AND_ARROW;
	 		}
	 	} else { 	
		 	ItemStack stack2 =entity.getHeldItemOffhand();
		 	if(!stack2.isEmpty() && stack2.getItem() instanceof GenericGun && ((GenericGun) stack2.getItem()).hasBowAnim()){
		 		if (ShooterValues.getIsCurrentlyUsingGun(entity,true)){			 		
			 		if (entity.getPrimaryHand()==EnumHandSide.RIGHT) {
			 			leftArmPose = ArmPose.BOW_AND_ARROW;
			 		} else {
			 			rightArmPose = ArmPose.BOW_AND_ARROW;
			 		}
		 		}
		 	}
	 	}

        switch(leftArmPose) {
	        case EMPTY:
	          lAngleY = 0.0F;
	        break;
			case BLOCK:
	         	lAngleX =lAngleX * 0.5F - 0.9424779F;
	        	lAngleY = 0.5235988F;
	        break;
	    	case ITEM:
	        	lAngleX = lAngleX * 0.5F - ((float)Math.PI / 10F);
	        	lAngleY = 0.0F;
	        break;
        }
        
    	switch(rightArmPose) {
	        case EMPTY:
	          rAngleY = 0.0F;
	        break;
			case BLOCK:
	         	rAngleX = rAngleX * 0.5F - 0.9424779F;
	        	rAngleY = -0.5235988F;
	        break;
	    	case ITEM:
	        	rAngleX = rAngleX * 0.5F - ((float)Math.PI / 10F);
	        	rAngleY = 0.0F;
	        break;
    	}
    	
        float headPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * ptt;	        
        float bhrx = headPitch * 0.017453292F; //this.bipedHead.rotateAngleX
        	
        if (entity.swingProgress > 0.0f) {
        	float bbry = MathHelper.sin(MathHelper.sqrt(entity.swingProgress) * ((float)Math.PI * 2F)) * 0.2F; //= bipedBody.rotateAngleY
        	lAngleY += bbry;
        	lAngleX += bbry;
        	rAngleY += bbry;
        	float f1 = 1.0F - entity.swingProgress;
	        f1 = f1 * f1;
	        f1 = f1 * f1;
	        f1 = 1.0F - f1;
	        float f2 = MathHelper.sin(f1 * (float)Math.PI);
	        
	        float f3 = MathHelper.sin(entity.swingProgress * (float)Math.PI) * -(bhrx - 0.7F) * 0.75F;
	        
	        if (hand == EnumHandSide.LEFT) {
	        	lAngleX = (float) (lAngleX - ((double)f2 * 1.2D + (double)f3));
	        	lAngleY += bbry * 2.0F;
	        	lAngleZ += MathHelper.sin(entity.swingProgress * (float)Math.PI) * -0.4F;
	        }else {
	        	rAngleX = (float) (rAngleX - ((double)f2 * 1.2D + (double)f3));
	        	rAngleY += bbry * 2.0F;
	        	rAngleZ += MathHelper.sin(entity.swingProgress * (float)Math.PI) * -0.4F;
	        }
	    }
        
        if (entity.isSneaking()) {
        	rAngleX += 0.4F;
        	lAngleX += 0.4F;
        }
        
        float ageInTicks = (float)entity.ticksExisted + ptt;
        
        rAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        lAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        rAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        lAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        
        float f_ = MathUtil.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, ptt);
        float f1_ = MathUtil.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, ptt);

        float netHeadYaw = f1_ - f_;
        float bhry = netHeadYaw * 0.017453292F;
        
        if (rightArmPose == ArmPose.BOW_AND_ARROW) {
        	rAngleY = -0.1F + bhry;
        	lAngleY = 0.1F + bhry + 0.4F;
        	rAngleX =  -((float)Math.PI / 2F) + bhrx;
        	lAngleX = -((float)Math.PI / 2F) + bhrx;
        }else if (leftArmPose == ArmPose.BOW_AND_ARROW) {
        	rAngleY = -0.1F + bhry - 0.4F;
            lAngleY = 0.1F + bhry;
            rAngleX = -((float)Math.PI / 2F) + bhrx;
            lAngleX = -((float)Math.PI / 2F) + bhrx;
        }
        
        //Entity Rotation
        float entityYaw = (float)MathUtil.D2R * (entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * ptt);
        lAngleY += entityYaw;
        rAngleY += entityYaw;
//        
        if (hand == EnumHandSide.LEFT) {
        	return new Vec3d(lAngleX, lAngleY, lAngleZ);
        }else {
        	return new Vec3d(rAngleX, rAngleY, rAngleZ);
        }
	}*/
}
