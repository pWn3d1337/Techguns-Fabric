package techguns.client.render.item;

import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import techguns.api.ICamoChangeable;
import techguns.api.entity.AttackTime;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.api.entity.ITGShooterValues;
import techguns.api.guns.GunManager;
import techguns.api.guns.IGenericGun;
import techguns.client.ClientProxy;
import techguns.client.Keybinds;
import techguns.client.models.ModelMultipart;
import techguns.client.render.fx.GunEffect;
import techguns.client.render.fx.IScreenEffect;
import techguns.client.render.math.TGMatrixOps;
import techguns.items.guns.GenericGunCharge;
import techguns.items.guns.GrapplingHook;
import techguns.util.MathUtil;

public class RenderGunBase extends RenderItemBase {

//	protected IScreenEffect muzzleFX= null; //ScreenEffect.muzzleFlash2;

	protected GunEffect muzzleEffect = null;
	protected GunEffect chargeEffect = null;

	protected IScreenEffect scope=null;
	
//	protected float muzzleFX_x_l=0f;
//	protected float muzzleFX_x_r=0f;
//	protected float muzzleFX_y=0f;
//	protected float muzzleFX_z=0f;
//	protected float muzzleFX_scale=1.0f;
//
//	protected float muzzleFX_3p_y=0f;
//	protected float muzzleFX_3p_z=0f;
//	protected float muzzleFX_3p_scale=0.5f;
//
//	protected float mf_jitterX = 0f;
//	protected float mf_jitterY = 0f;
//	protected float mf_jitterAngle = 0f;
//	protected float mf_jitterScale = 0f;
	
	protected GunAnimation recoilAnim = GunAnimation.genericRecoil;
	protected float[] recoilParams = new float[] {0.15f, 5.0f};
	protected GunAnimation reloadAnim = GunAnimation.genericReload;
	protected float[] reloadParams = new float[] {1.0f, 40.0f};
	
	protected GunAnimation scopeRecoilAnim = null; //GunAnimation.scopeRecoil;
	protected float[] scopeRecoilParams = null; //new float[] {0.25f, 1.0f};	
	
	protected GunAnimation recoilAnim3p = GunAnimation.genericRecoil;
	protected float[] recoilParams3p = new float[] {0f, 5.0f};
	protected GunAnimation reloadAnim3p = GunAnimation.genericReload;
	protected float[] reloadParams3p = new float[] {0f, 40.0f};

	protected float[] adsOffsets = new float[]{0f,0f,0f};

	protected float scopescale=3.0f;
	
	protected float chargeTranslation=0.25f;
	
	Identifier texture;

	
	/*public RenderGunBase(ModelMultipart model, int parts) {
		super(model, null);
		this.parts = parts;
		this.scale_ground=this.scale_thirdp;
	}*/
	
	public RenderGunBase(ModelMultipart model, Identifier texture) {
		super(model, null);
		this.parts = 1;
		this.texture=texture;
		this.scale_ground=this.scale_thirdp;
	}
	
	//TODO Multiple Textures
	public RenderGunBase(ModelMultipart model, int parts, Identifier texture) {
		super(model, null);
		this.parts = parts;
		this.texture=texture;
		this.scale_ground=this.scale_thirdp;
	}

	/**
	 * Set the basic translation applied to ALL types
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	@Override
	public RenderGunBase setBaseTranslation(float x, float y, float z) {
		return (RenderGunBase) super.setBaseTranslation(x, y, z);
	}

	@Override
	public RenderGunBase setGUIScale(float guiscale){
		return (RenderGunBase) super.setGUIScale(guiscale);
	}
	
	public RenderGunBase setReloadAnim(GunAnimation anim, float... params) {
		this.reloadAnim = anim;
		this.reloadParams = params;
		return this;
	}
	
	public RenderGunBase setRecoilAnim(GunAnimation anim, float... params) {
		this.recoilAnim = anim;
		this.recoilParams = params;
		return this;
	}
	
	public RenderGunBase setScopeRecoilAnim(GunAnimation anim, float... params) {
		this.scopeRecoilAnim = anim;
		this.scopeRecoilParams = params;
		return this;
	}
	
	public RenderGunBase setReloadAnim3p(GunAnimation anim, float... params) {
		this.reloadAnim3p = anim;
		this.reloadParams3p = params;
		return this;
	}
	
	public RenderGunBase setRecoilAnim3p(GunAnimation anim, float... params) {
		this.recoilAnim3p = anim;
		this.recoilParams3p = params;
		return this;
	}
	
	public RenderGunBase setChargeTranslationAmount(float value) {
		this.chargeTranslation=value;
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
	public RenderGunBase setTransformTranslations(float[][] translations) {
		return (RenderGunBase) super.setTransformTranslations(translations);
	}

	public RenderGunBase setAdsOffsets(float offsetX, float offsetY, float offsetZ){
		this.adsOffsets = new float[]{offsetX, offsetY, offsetZ};
		return this;
	}

	public RenderGunBase setMuzzleFx(IScreenEffect muzzleFX, float x, float y, float z, float scale, float x_l){
		this.muzzleEffect = new GunEffect(muzzleFX, x, x_l, y, z, scale);
		return this;
	}
	public RenderGunBase setMuzzleFXPos3P(float y, float z) {
		if (this.muzzleEffect != null) {
			this.muzzleEffect.offsetY_3p = y;
			this.muzzleEffect.offsetZ_3p = z;
		}
		return this;
	}

	public RenderGunBase setChargeFx(IScreenEffect muzzleFX, float x, float y, float z, float scale, float x_l){
		this.chargeEffect = new GunEffect(muzzleFX, x, x_l, y, z, scale);
		return this;
	}
	public RenderGunBase setChargeFXPos3P(float y, float z) {
		if (this.chargeEffect != null) {
			this.chargeEffect.offsetY_3p = y;
			this.chargeEffect.offsetZ_3p = z;
		}
		return this;
	}

	public RenderGunBase setChargeFXPos3P(float y, float z, float scale) {
		if (this.chargeEffect != null) {
			this.chargeEffect.offsetY_3p = y;
			this.chargeEffect.offsetZ_3p = z;
			this.chargeEffect.effectScale_3p = scale;
		}
		return this;
	}
	
	public RenderGunBase setScope(IScreenEffect scope) {
		this.scope=scope;
		return this;
	}
	
	public RenderGunBase setScope(IScreenEffect scope, float scale) {
		this.scope=scope;
		this.scopescale=scale;
		return this;
	}
	
	public boolean hasScopeTexture() {
		return this.scope!=null;
	}
	
	protected static ITGShooterValues getShooterValues(LivingEntity ent) {
		ITGShooterValues values = null;
		if (ent != null) {
			if (ent instanceof PlayerEntity) {
				values = ((ITGExtendedPlayer)ent);
			} else if (ent instanceof ITGShooterValues) {
				values = (ITGShooterValues)ent;
			}
		}
		return values;
	}

	
	
	@Override
	public void renderItem(LivingEntity entityIn, Mode transform, MatrixStack matrices, ItemStack stack, boolean leftHand,
			VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel bakedModel) {

		
		IGenericGun gun = ((IGenericGun) stack.getItem());
		ITGShooterValues values = getShooterValues(entityIn);
		
		//System.out.println("Render:"+stack+" for "+entityIn);
		boolean akimbo = false;
		boolean sneaking = false;
		boolean isOffhand = false;
		if (entityIn!=null){
			sneaking = entityIn.isSneaking();
			isOffhand = ((!leftHand) && entityIn.getMainArm() == Arm.LEFT) || ((leftHand) && entityIn.getMainArm() == Arm.RIGHT);
			
			akimbo = GunManager.isAkimbo(entityIn, isOffhand?Hand.OFF_HAND:Hand.MAIN_HAND, stack);
		}
		matrices.push();

		float fireProgress = 0.0f;
		float reloadProgress = 0.0f;
		float muzzleFlashProgress = 0.0f;
		float chargeProgress = 0.0f;
		float zoomProgress = 1.0f;

		byte attackType=0;
		
		boolean renderScope = false;
		
		if (values != null && (Mode.FIRST_PERSON_LEFT_HAND == transform || Mode.FIRST_PERSON_RIGHT_HAND == transform
				|| Mode.THIRD_PERSON_LEFT_HAND == transform || Mode.THIRD_PERSON_RIGHT_HAND == transform)) {
			AttackTime attack = values.getAttackTime(isOffhand);
			attackType = attack.getAttackType();
			
			if (gun.canCharge() && !isOffhand && !entityIn.getActiveItem().isEmpty()) {
			
				int dur = entityIn.getItemUseTime();

				chargeProgress = dur / ((GenericGunCharge)stack.getItem()).fullChargeTime;

				System.out.println("chargeProgess = "+chargeProgress);

				if (chargeProgress < 0.0f) {
					chargeProgress = 0.0f;
				} else if (chargeProgress > 1.0f) {
					chargeProgress = 1.0f;
				}
								
			} else if (attack.isReloading()) {
				long diff = attack.getReloadTime() - System.currentTimeMillis();

				if (diff <= 0) {
					attack.setReloadTime(0);
					attack.setReloadTimeTotal(0);
					attack.setAttackType((byte) 0);
				} else {
					reloadProgress = 1.0f - ((float) diff / (float) attack.getReloadTimeTotal());
				}
			} else if (attack.isRecoiling()) {
								
				//System.out.println(stack+": LeftHand:"+leftHand+ "  Offand:"+isOffhand);
				
				long diff = attack.getRecoilTime() - System.currentTimeMillis();

				if (diff <= 0) {
					attack.setRecoilTime(0);
					attack.setRecoilTimeTotal(0);
					attack.setAttackType((byte) 0);
					attack.setRecoilChargeProgress(0f);
				} else {
					fireProgress = 1.0f - ((float) diff / (float) attack.getRecoilTimeTotal());
					
					if (gun.canCharge()) {
						chargeProgress = (1.0f- fireProgress) * attack.getRecoilChargeProgress();						
					}

				}
			}
			
			if (Mode.FIRST_PERSON_LEFT_HAND == transform || Mode.FIRST_PERSON_RIGHT_HAND == transform  || Mode.THIRD_PERSON_LEFT_HAND == transform || Mode.THIRD_PERSON_RIGHT_HAND == transform){
				//Calculate muzzleFlash progress
								
				if(attack.getMuzzleFlashTime()>0) {
					long diff = attack.getMuzzleFlashTime() - System.currentTimeMillis();
					if (diff <= 0 || diff > attack.getMuzzleFlashTimeTotal()) {
						attack.setMuzzleFlashTime(0L);
						attack.setMuzzleFlashTimeTotal(0);
					}else{			
						muzzleFlashProgress = 1.0f-((float)diff / (float)attack.getMuzzleFlashTimeTotal());
					}		
				}
				
			} 
			
		}
		
		this.applyTranslation(matrices, transform);

		if (Mode.FIRST_PERSON_LEFT_HAND == transform || Mode.FIRST_PERSON_RIGHT_HAND == transform) {

			//calculate zoomprogress
			ClientProxy cp = ClientProxy.get();
			float zoomtime = cp.getZoomTicks()+MinecraftClient.getInstance().getTickDelta();

			if (cp.isZooming()) {
				zoomProgress = MathUtil.clamp(zoomtime, 0F, 4F) / 4F;
			} else {
				zoomProgress = 1F - MathUtil.clamp(zoomtime, 0F, 2.5F) / 2.5F;
			}

			boolean doADSrecoil = false;
			if (!isOffhand && gun.isZooming()) {

				if (this.scope != null) {
					renderScope = true;
				} else {

					if (!isOffhand && gun.getZoomMult() > 0f && gun.isZooming()) {
						doADSrecoil = true;
					}
				}
			}
			if (zoomProgress>0F) {
				this.transformADS(matrices, zoomProgress);
			}

			this.transformFirstPerson(matrices, fireProgress, reloadProgress, chargeProgress, Mode.FIRST_PERSON_LEFT_HAND == transform, sneaking && isOffhand, doADSrecoil);


		} else if (Mode.THIRD_PERSON_LEFT_HAND == transform || Mode.THIRD_PERSON_RIGHT_HAND == transform) {
			this.transformThirdPerson(matrices, entityIn, fireProgress, reloadProgress, Mode.THIRD_PERSON_LEFT_HAND == transform, gun.getArmPose(akimbo));

		} else if (Mode.GUI == transform) {
			this.transformGUI(matrices);

		} else if (Mode.GROUND == transform) {
			this.transformGround(matrices);

		} else if (Mode.FIXED == transform) {
			this.transformFixed(matrices);
		}

		
		if (!renderScope) {		
			this.setBaseScale(entityIn,matrices, transform);
			this.setBaseRotation(matrices, transform);
			this.applyBaseTranslation(matrices);
			//RenderSystem.color4f(1f, 1f, 1f, 1f);

			for (int i = 0; i < parts; i++) {
				
				Identifier tex = texture;
				if(gun instanceof ICamoChangeable) {
					ICamoChangeable camogun = (ICamoChangeable)gun;
					List<Identifier>textures = camogun.getCurrentCamoTextures(stack);
					if (textures != null) {
						tex = textures.get(MathUtil.clamp(i,0,textures.size()-1));
					}
				}

				//this.bindTextureForPart(gun, i, stack);
				//this.setGLColorForPart(gun, i, stack);
				//model.render(entityIn, 0, 0, 0, 0, 0, SCALE, gun.getAmmoLeft(stack), reloadProgress, transform, i, fireProgress, chargeProgress);
				model.render(entityIn, matrices, vertexConsumers.getBuffer(model.getLayerForPart(gun, stack, tex,i)), gun.getAmmoLeft(stack), reloadProgress, transform, i, fireProgress, chargeProgress, light, overlay);
				//RenderSystem.color4f(1f, 1f, 1f, 1f);
			}
			matrices.pop();
			
			this.applyAnimForParticles(matrices, entityIn, reloadProgress, transform, sneaking, isOffhand, gun.getArmPose(akimbo));
			//TODO add item particles
			//this.renderItemParticles(matrices, entityIn, transform, ClientProxy.get().PARTIAL_TICK_TIME);
			matrices.pop();

			String ammoVariant = gun.getCurrentAmmoVariantKey(stack);
			
			//Draw muzzle FX
			//System.out.println("muzzleFlashProgress = "+muzzleFlashProgress);

			if (muzzleFlashProgress>0){
				if (Mode.FIRST_PERSON_LEFT_HAND== transform || Mode.FIRST_PERSON_RIGHT_HAND == transform ) {

					if (!isOffhand && gun.getZoomMult() > 0f && gun.isZooming()  && this.scope==null) {
						this.transformADS(matrices, zoomProgress);
					}

					this.drawMuzzleFx(matrices, vertexConsumers, muzzleFlashProgress, attackType, leftHand, ammoVariant);
				} else {
					matrices.push();
					this.transformThirdPersonArmPose(matrices, entityIn, reloadProgress, gun.getArmPose(akimbo), leftHand);
					this.drawMuzzleFx3P(matrices, vertexConsumers, muzzleFlashProgress, attackType, leftHand, ammoVariant);
					matrices.pop();
				}
			}else {
				if (reloadProgress<=0){
					if (Mode.FIRST_PERSON_LEFT_HAND== transform || Mode.FIRST_PERSON_RIGHT_HAND == transform ) {
						this.drawIdleFx(entityIn, matrices, vertexConsumers, leftHand, ammoVariant);
					} else {
						matrices.push();
						this.transformThirdPersonArmPose(matrices, entityIn, reloadProgress, gun.getArmPose(akimbo), leftHand);
						this.drawIdleFx3P(entityIn, matrices, vertexConsumers, leftHand, ammoVariant);
						matrices.pop();
					}
				}if (chargeProgress > 0.0f && this.chargeEffect != null) {
					//System.out.println("chargeProgess2 = "+chargeProgress);
					if (Mode.FIRST_PERSON_LEFT_HAND== transform || Mode.FIRST_PERSON_RIGHT_HAND == transform ) {
						this.drawChargeFx(matrices, vertexConsumers, chargeProgress, attackType, leftHand, ammoVariant);
					} else {
						matrices.push();
						this.transformThirdPersonArmPose(matrices, entityIn, reloadProgress, gun.getArmPose(akimbo), leftHand);
						this.drawChargeFx3P(matrices, vertexConsumers, chargeProgress, attackType, leftHand, ammoVariant);
						matrices.pop();
					}
				}
			}
			
		} else {
			matrices.pop();
			if (this.scopeRecoilAnim != null && fireProgress > 0f) {
				this.scopeRecoilAnim.play(matrices, fireProgress, Mode.FIRST_PERSON_LEFT_HAND == transform, this.scopeRecoilParams);
			}

			this.renderScope(matrices,vertexConsumers, fireProgress, leftHand);
		}

	}

	protected void drawIdleFx(LivingEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, boolean leftHand, String ammoVariant) {}
	protected void drawIdleFx3P(LivingEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, boolean leftHand, String ammoVariant) {}

	protected void setGLColorForPart(IGenericGun gun, int part, ItemStack stack) {
		
	};
	
	public void applyAnimForParticles(MatrixStack matrices, LivingEntity entity, float reloadProgress, Mode transform, boolean sneaking, boolean isOffhand, ArmPose armPose) {
		matrices.push();
		if(reloadProgress==0) return;
		if (transform==Mode.FIRST_PERSON_LEFT_HAND || transform==Mode.FIRST_PERSON_RIGHT_HAND) {
			this.transformFirstPerson(matrices, 0f, reloadProgress, 0f, Mode.FIRST_PERSON_LEFT_HAND == transform, sneaking&&isOffhand, false);
		} else if (transform==Mode.THIRD_PERSON_LEFT_HAND || transform==Mode.THIRD_PERSON_RIGHT_HAND) {
			this.transformThirdPerson(matrices, entity, 0f, reloadProgress, Mode.THIRD_PERSON_LEFT_HAND == transform, armPose);
		}
		
	}
	
	protected void transformFirstPerson(MatrixStack matrices, float fireProgress, float reloadProgress, float chargeProgress, boolean left, boolean shoudLowerWeapon, boolean ads) {
		if (chargeProgress>0) {
		
			matrices.translate(0, 0, this.chargeTranslation*chargeProgress);
			
		}
		if (fireProgress >0){

			if(ads && this.scopeRecoilAnim !=null){
				this.scopeRecoilAnim.play(matrices, fireProgress, left, this.scopeRecoilParams);
			} else {
				this.recoilAnim.play(matrices, fireProgress, left, this.recoilParams);
			}
			
		} else if (reloadProgress>0){
			
			this.reloadAnim.play(matrices, reloadProgress, left, this.reloadParams);
			
		} else if (shoudLowerWeapon){
			TGMatrixOps.rotate(matrices, -35f, 1, 0, 0);
		}
	}

	protected void transformThirdPersonArmPose(MatrixStack matrices, LivingEntity entity, float reloadProgress, ArmPose armPose, boolean left) {
		if (reloadProgress<=0 && ArmPose.CROSSBOW_HOLD == armPose) {	
			TGMatrixOps.rotate(matrices, left?15.0f:-15.0f, 0f, 1f, 0f);
		}
	}
	 
	protected void transformThirdPerson(MatrixStack matrices, LivingEntity ent, float fireProgress, float reloadProgress, boolean left, ArmPose armPose) {
		//TODO NPC shooter
		/*if( ent!=null && ent instanceof INPCTechgunsShooter) {
			INPCTechgunsShooter shooter = (INPCTechgunsShooter) ent;
			if(!shooter.hasWeaponArmPose()) {
				GlStateManager.rotate(90.0f, 1, 0, 0);
				//GlStateManager.translate(Keybinds.X, Keybinds.Y, Keybinds.Z);
			}
			
		}*/
		transformThirdPersonArmPose(matrices, ent, reloadProgress, armPose, left);
		
		if (fireProgress >0){
			
			this.recoilAnim3p.play(matrices, fireProgress, left, this.recoilParams3p);
			
		} else if (reloadProgress>0){	
			
			this.reloadAnim3p.play(matrices, reloadProgress, left, this.reloadParams3p);
		}
	}

	protected void transformGUI(MatrixStack matrices) {
		// GlStateManager.rotate(-22.5f, 1f, 2f, 0f);
		TGMatrixOps.rotate(matrices, 40.0f, 0, 1f, 0);
		TGMatrixOps.rotate(matrices, 20.0f, 1f, 0, 0);
	}

	protected void transformGround(MatrixStack matrices) {
		// GlStateManager.rotate(Keybinds.Y, 0, 1, 0);
	}

	protected void transformFixed(MatrixStack matrices) {
		TGMatrixOps.rotate(matrices, -90.0f, 0, 1.0f, 0);
	}
	
	protected void drawMuzzleFx(MatrixStack matrices, VertexConsumerProvider verticesProvider, float progress, byte attackType, boolean leftHand, String ammoVariant){
		if (this.muzzleEffect!=null && this.muzzleEffect.screenEffect!=null){
			float x= leftHand?this.muzzleEffect.offsetX_l:this.muzzleEffect.offsetX_r;
			//float leftOffset = leftHand?0.05f:0f;
			
			//Muzzle flash jitter
			ClientProxy cp = ClientProxy.get();
			float scale = this.muzzleEffect.effectScale;
			float offsetX = x;
			float offsetY = this.muzzleEffect.offsetY;
			float offsetZ = this.muzzleEffect.offsetZ;

			//DEBUG:
//			offsetX += (float) Keybinds.OFFSET_X;
//			offsetY += (float) Keybinds.OFFSET_Y;
//			offsetZ += (float) Keybinds.OFFSET_Z;

			if (this.muzzleEffect.jitterScale > 0.0f) scale += muzzleEffect.jitterScale*cp.muzzleFlashJitterScale;
			if (this.muzzleEffect.jitterX > 0.0f) offsetX += muzzleEffect.jitterX*cp.muzzleFlashJitterX;
			if (this.muzzleEffect.jitterY > 0.0f) offsetY += muzzleEffect.jitterY*cp.muzzleFlashJitterY;
			//if (this.jitterAngle > 0.0f) angle += jitterAngle*cp.muzzleFlashJitterAngle*f;

			//this.muzzleFX.doRender(progress, (x+Keybinds.X)/*+leftOffset*/, this.muzzleFX_y+Keybinds.Y, this.muzzleFX_z+Keybinds.Z, this.muzzleFX_scale, false);
			
			this.muzzleEffect.screenEffect.doRender(matrices, verticesProvider, progress, offsetX, offsetY, offsetZ, scale, false, ammoVariant);
			
		}
	}

	protected void drawMuzzleFx3P(MatrixStack matrices, VertexConsumerProvider verticesProvider, float progress, byte attackType, boolean leftHand, String ammoVariant) {
		if (this.muzzleEffect!=null && this.muzzleEffect.screenEffect!=null) {
			//float x= leftHand?this.muzzleFX_x_l:this.muzzleFX_x_r;
			//this.muzzleFX.doRender(progress, 0, this.muzzleFX_3p_y+Keybinds.Y, this.muzzleFX_3p_z+Keybinds.Z, this.muzzleFX_scale*this.muzzleFX_3p_scale, true);
			this.muzzleEffect.screenEffect.doRender(matrices, verticesProvider, progress, 0, this.muzzleEffect.offsetY_3p, this.muzzleEffect.offsetZ_3p, this.muzzleEffect.effectScale*this.muzzleEffect.effectScale_3p, true, ammoVariant);
		}
	}


	protected void drawChargeFx(MatrixStack matrices, VertexConsumerProvider verticesProvider, float progress, byte attackType, boolean leftHand, String ammoVariant){
		if (this.chargeEffect!=null && this.chargeEffect.screenEffect!=null){
			float x= leftHand?this.chargeEffect.offsetX_l:this.chargeEffect.offsetX_r;
			//float leftOffset = leftHand?0.05f:0f;

			//Muzzle flash jitter
			ClientProxy cp = ClientProxy.get();
			float scale = this.chargeEffect.effectScale;
			float offsetX = x;
			float offsetY = this.chargeEffect.offsetY;
			float offsetZ = this.chargeEffect.offsetZ;

			//DEBUG:
//			offsetX += (float) Keybinds.OFFSET_X;
//			offsetY += (float) Keybinds.OFFSET_Y;
//			offsetZ += (float) Keybinds.OFFSET_Z;

			if (this.chargeEffect.jitterScale > 0.0f) scale += chargeEffect.jitterScale*cp.muzzleFlashJitterScale;
			if (this.chargeEffect.jitterX > 0.0f) offsetX += chargeEffect.jitterX*cp.muzzleFlashJitterX;
			if (this.chargeEffect.jitterY > 0.0f) offsetY += chargeEffect.jitterY*cp.muzzleFlashJitterY;
			//if (this.jitterAngle > 0.0f) angle += jitterAngle*cp.muzzleFlashJitterAngle*f;

			//this.muzzleFX.doRender(progress, (x+Keybinds.X)/*+leftOffset*/, this.muzzleFX_y+Keybinds.Y, this.muzzleFX_z+Keybinds.Z, this.muzzleFX_scale, false);

			this.chargeEffect.screenEffect.doRender(matrices, verticesProvider, progress, offsetX, offsetY, offsetZ, scale, false, ammoVariant);

		}
	}

	protected void drawChargeFx3P(MatrixStack matrices, VertexConsumerProvider verticesProvider, float progress, byte attackType, boolean leftHand, String ammoVariant) {
		if (this.chargeEffect!=null && this.chargeEffect.screenEffect!=null) {
			//float x= leftHand?this.muzzleFX_x_l:this.muzzleFX_x_r;
			//this.muzzleFX.doRender(progress, 0, this.muzzleFX_3p_y+Keybinds.Y, this.muzzleFX_3p_z+Keybinds.Z, this.muzzleFX_scale*this.muzzleFX_3p_scale, true);
			this.chargeEffect.screenEffect.doRender(matrices, verticesProvider, progress, 0, this.chargeEffect.offsetY_3p, this.chargeEffect.offsetZ_3p, this.chargeEffect.effectScale*this.chargeEffect.effectScale_3p, true, ammoVariant);
		}
	}

	
	protected void renderScope(MatrixStack matrices, VertexConsumerProvider verticesProvider, float fireProgress, boolean leftHand) {
		if (this.scope!=null) {
			float x= leftHand?0.56f:-0.56f;
			this.scope.doRender(matrices, verticesProvider, fireProgress, x, 0.52f, 0f, scopescale, false, null);
		}
	}
	
	@Override
	public RenderGunBase setBaseScale(float baseScale) {
		return (RenderGunBase) super.setBaseScale(baseScale);
	}
	
	protected void transformADS(MatrixStack matrices, float zoomProgress) {
		matrices.translate((-0.5588f + this.adsOffsets[0]) * zoomProgress, (0.15f+this.adsOffsets[1])*zoomProgress, (0.31f+this.adsOffsets[2])*zoomProgress);
	}
	
	public RenderGunBase setMuzzleFlashJitter(float jX, float jY, float jAngle, float jScale) {
		if (this.muzzleEffect!=null && this.muzzleEffect.screenEffect!=null) {
			this.muzzleEffect.jitterX = jX;
			this.muzzleEffect.jitterY = jY;
			this.muzzleEffect.jitterScale = jScale;
			this.muzzleEffect.jitterAngle = jAngle;
		}
		return this;
	}
	
	public void bindTextureForPart(IGenericGun gun, int part, ItemStack stack) {
		//TODO Texture multipart
		/*if(part==0) {
			if(gun.hasCustomTexture) {
				Minecraft.getMinecraft().getTextureManager().bindTexture(gun.getCurrentTexture(stack));
			} else {
				Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			}
		}*/
		MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
	}
	
	public RenderGunBase setAmbientParticleFX(String ambientParticleFX) {
		this.ambientParticleFX = ambientParticleFX;
		return this;
	}
	
}
