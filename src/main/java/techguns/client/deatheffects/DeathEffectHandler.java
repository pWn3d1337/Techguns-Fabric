package techguns.client.deatheffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import techguns.TGIdentifier;
import techguns.TGSounds;
import techguns.api.entity.ITGLivingEntity;
import techguns.client.ClientProxy;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.sounds.TGSoundCategory;

public class DeathEffectHandler {
	
	
	public static final Identifier BIO_DEATH_TEXTURE = new TGIdentifier("textures/fx/bio.png");
	public static final Identifier LASER_DEATH_TEXTURE = new TGIdentifier("textures/fx/laserdeath.png");

	public static void setEntityDeathType(LivingEntity entity, DeathType deathtype) {
		ITGLivingEntity tg_entity = (ITGLivingEntity) entity;
		tg_entity.setDeathType(deathtype);
	}

	public static DeathType getEntityDeathType(LivingEntity entity) {
		ITGLivingEntity tg_entity = (ITGLivingEntity) entity;
		return tg_entity.getDeathType();
	}

	public static void createDeathEffect(LivingEntity entity, DeathType deathtype, float motionX, float motionY,
			float motionZ) {

		double x = entity.getX();
		double y = entity.getY() + (entity.getHeight() / 2.0f);
		double z = entity.getZ();

		if (deathtype == DeathType.GORE) {
/*
			GoreData data = DeathEffect.getGoreData(entity.getClass());
			Render render = Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(entity.getClass());

			try {
				if (data.model == null && render != null) {
					ModelBase mainModel = (ModelBase) DeathEffectEntityRenderer.RLB_mainModel
							.get((RenderLivingBase) render);
					if (mainModel instanceof ModelBiped) {
						data.model = new ModelGibsBiped(((ModelBiped) mainModel).getClass().newInstance());
					} else if (mainModel instanceof ModelQuadruped) {
						data.model = new ModelGibsQuadruped(((ModelQuadruped) mainModel).getClass().newInstance());
					} else if (mainModel instanceof ModelVillager) {
						data.model = new ModelGibsVillager(((ModelVillager) mainModel).getClass().newInstance());
					} else {
						data.model = genericGore.model; // new ModelGibsGeneric(mainModel.getClass().newInstance());
						data.texture = genericGore.texture;
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
				e.printStackTrace();
			}

			ClientProxy.get().playSoundOnPosition(data.sound, (float) x, (float) y, (float) z, 1.0f, 1.0f, false,
					TGSoundCategory.DEATHEFFECT);

			// Spawn MainFX
			TGParticleSystem sys = new TGParticleSystem(entity.world, data.type_main, x, entity.posY, z, entity.motionX,
					entity.motionY, entity.motionZ);
			ClientProxy.get().particleManager.addEffect(sys);

			int count;
			if (data.numGibs >= 0) {
				count = data.numGibs;
			} else {
				if (data.model == null) {
					return;
				}
				count = data.model.getNumGibs();
			}

			for (int i = 0; i < count; i++) {
				double vx = (0.5 - entity.world.rand.nextDouble()) * 0.35;
				double vy;
				if (entity.onGround) {
					vy = (entity.world.rand.nextDouble()) * 0.35;
				} else {
					vy = (0.5 - entity.world.rand.nextDouble()) * 0.35;
				}
				double vz = (0.5 - entity.world.rand.nextDouble()) * 0.35;

				FlyingGibs ent = new FlyingGibs(entity.world, entity, data, x, y, z, motionX * 0.35 + vx,
						motionY * 0.35 + vy, motionZ * 0.35 + vz, (entity.width + entity.height) / 2.0f, i);

				entity.world.spawnEntity(ent);
			}
			*/
		} else if (deathtype == DeathType.BIO) {
			ClientProxy.get().createFX("biodeath", entity.world, x, y, z, (double) motionX, (double) motionY,
					(double) motionZ);
			ClientProxy.get().playSoundOnPosition(TGSounds.DEATH_BIO, (float) x, (float) y, (float) z, 1.0f, 1.0f,
					false, TGSoundCategory.DEATHEFFECT);
		} else if (deathtype == DeathType.LASER) {
			ClientProxy.get().createFX("laserdeathFire", entity.world, x, y, z, (double) motionX, 0, (double) motionZ);
			ClientProxy.get().createFX("laserdeathAsh", entity.world, x, y, z, (double) motionX, 0, (double) motionZ);
			ClientProxy.get().playSoundOnPosition(TGSounds.DEATH_LASER, (float) x, (float) y, (float) z, 1.0f, 1.0f,
					false, TGSoundCategory.DEATHEFFECT);
		}
	}

	public static Identifier getTexture(LivingEntity entity) {
		DeathType dt = getEntityDeathType(entity);
		switch (dt) {
		case BIO:
			return BIO_DEATH_TEXTURE;
		case LASER:
			return LASER_DEATH_TEXTURE;
		case GORE:
		case DISMEMBER:
		case DEFAULT:
		default:
			return null;
		}
	}
//	
//	
//	public static class GoreData {
//		public ModelGibs model = null;
//		public ResourceLocation texture = null;
//		int numGibs = -1;
//		public float particleScale = 1.0f;
//		public float modelScale = 1.0f;
//		 	
//		int bloodColorR;
//		int bloodColorG;
//		int bloodColorB;
//		
//		//public boolean showBlood = true;
//		String fx_main = "GoreFX_Blood";
//		String fx_trail ="GoreTrailFX_Blood";
//		public SoundEvent sound = TGSounds.DEATH_GORE;
//		
//		public TGParticleSystemType type_main;
//		public TGParticleSystemType type_trail;
//
//		public float minPartScale = 1.0f;
//		public float maxPartScale = 1.0f;
//
//		public GoreData() {}
//		
//		public GoreData(ModelGibs model, int bloodColorR, int bloodColorG, int bloodColorB) {
//			this.model = model;
//	//		this.modelScale = modelScale;
//			this.bloodColorR = bloodColorR;
//			this.bloodColorG = bloodColorG;
//			this.bloodColorB = bloodColorB;
//		}
//		
//		public GoreData setNumGibs(int gibs) {
//			this.numGibs = gibs;
//			return this;
//		}
//		
//		public GoreData setTexture(ResourceLocation texture) {
//			this.texture = texture;
//			return this;
//		}
//		
//		public GoreData setFXscale(float scale) {
//			this.particleScale = scale;
//			return this;
//		}
//		
//		public GoreData setFX(String fx_main, String fx_trail) {
//			this.fx_main = fx_main;
//			this.fx_trail = fx_trail;
//			return this;
//		}
//		
//		public GoreData setSound(SoundEvent sound) {
//			this.sound = sound;
//			return this;
//		}
//		
//		public void init() {
//			type_main = new TGParticleSystemType();
//
//			if (TGFX.FXList.containsKey(fx_main.toLowerCase())) {
//				TGFXType fxtype_main = TGFX.FXList.get(fx_main.toLowerCase());
//				if (fxtype_main instanceof TGParticleSystemType) {
//					this.type_main = getExtendedType((TGParticleSystemType) fxtype_main);
//				}else {
//					this.type_main = null;
//				}
//			}else {
//				this.type_main = null;
//			}
//			
//			type_trail = new TGParticleSystemType();
//			
//			if (TGFX.FXList.containsKey(fx_trail.toLowerCase())) {
//				TGFXType fxtype_trail = TGFX.FXList.get(fx_trail.toLowerCase());
//				if (fxtype_trail instanceof TGParticleSystemType) {
//					this.type_trail = getExtendedType((TGParticleSystemType) fxtype_trail);
//				}else {
//					this.type_trail = null;
//				}
//			}else {
//				this.type_trail = null;
//			}
//		}
//
//		/**
//		 * Add a random scale to individual gibs.
//		 * @param f
//		 * @param g
//		 */
//		public void setRandomScale(float min, float max) {
//			minPartScale = min;
//			maxPartScale = max;
//		}
//		
//		
//		private TGParticleSystemType getExtendedType(TGParticleSystemType supertype) {
//			TGParticleSystemType type = new TGParticleSystemType();
//			type.extend(supertype);
//			if (type.colorEntries.size() >= 1) {
//				type.colorEntries.get(0).r = (float)this.bloodColorR /255.0f;
//				type.colorEntries.get(0).g = (float)this.bloodColorG /255.0f;
//				type.colorEntries.get(0).b = (float)this.bloodColorB /255.0f;
//			}
//			type.sizeMin *= particleScale;
//			type.sizeMax *= particleScale;
//			type.sizeRateMin *= particleScale;
//			type.sizeRateMax *= particleScale;
//			type.startSizeRateDampingMin *= particleScale;
//			type.startSizeRateMin *= particleScale;
//			type.startSizeRateMax *= particleScale;
//			for (int i = 0; i < type.volumeData.length; i++) {
//				type.volumeData[i]*=particleScale;
//			}
//			return type;
//		}
//	}
}
