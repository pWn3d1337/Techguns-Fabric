package techguns.client.deatheffects;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import techguns.TGEntities;
import techguns.TGIdentifier;
import techguns.TGSounds;
import techguns.api.entity.ITGLivingEntity;
import techguns.api.render.ITGAnimalModel;
import techguns.client.ClientProxy;
import techguns.client.models.gibs.ModelGibs;
import techguns.client.models.gibs.ModelGibsAnimal;
import techguns.client.particle.TGFX;
import techguns.client.particle.TGFXType;
import techguns.client.particle.TGParticleSystem;
import techguns.client.particle.TGParticleSystemType;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.entities.projectiles.FlyingGibs;
import techguns.sounds.TGSoundCategory;
import net.minecraft.client.world.ClientWorld;

public class DeathEffectHandler {
	
	public static HashMap<Class<? extends LivingEntity>, GoreData> goreStats = new HashMap<Class<? extends LivingEntity>, GoreData>();
	
	public static final Identifier GORE_TEXTURE = new TGIdentifier("textures/entity/gore.png");
	private static GoreData genericGore;
	private static ArrayList<ModelPart> genericGibs;
	static{

		//addGore(PlayerEntity.class, (new GoreData(modelAnimal, 110,21,41)));
		
		genericGore = (new GoreData(160,21,31)).setTexture(GORE_TEXTURE);
		genericGore.setRandomScale(0.5f, 0.8f);
		
		ZombieEntityModel<ZombieEntity> model = new ZombieEntityModel<>(0, false);
		genericGibs = getModelParts(model);
	}
	
	
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

		System.out.println("CreateDeathEffect for type: " + deathtype);
		if (deathtype == DeathType.GORE) {
			
			GoreData data = getGoreData(entity.getClass());
			ArrayList<ModelPart> gibs = null; 
			Identifier gibsTexture = null;
			
			EntityRenderer<? super LivingEntity> renderer =  MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity);
			
			if (data.modelParts == null && renderer != null) {
				if (renderer instanceof LivingEntityRenderer) {
					EntityModel<?> model = ((LivingEntityRenderer<?, ?>) renderer).getModel();
					gibs = getModelParts(model);

					if (gibs != null) {
						gibsTexture = renderer.getTexture(entity);
					} else {
						gibs = genericGibs;
						gibsTexture = GORE_TEXTURE;
					}
				}
			}else {
				gibs = data.modelParts;
				gibsTexture = data.texture;
			}
				
			ClientProxy.get().playSoundOnPosition(data.sound, (float) x, (float) y, (float) z, 1.0f, 1.0f, false,
					TGSoundCategory.DEATHEFFECT);

			// Spawn MainFX
			Vec3d vel = entity.getVelocity();
			//TGParticleSystem sys = new TGParticleSystem(entity.world, data.type_main, x, entity.getY(), z, vel.x, vel.y, vel.z);
			//ClientProxy.get().particleManager.addEffect(sys);

			int count = gibs.size();

			TGParticleSystem gibsSys = new TGParticleSystem(entity.world, data.type_gibs, x, entity.getY(), z, vel.x, vel.y, vel.z);
			gibsSys.type.is3d = true;
			gibsSys.models = gibs;
			gibsSys.modelsTexture = gibsTexture;
			gibsSys.type.particleCountMin = count; //this should be safe to set, since the number of parts shouldn't vary
			gibsSys.type.particleCountMax = count;
			gibsSys.attachedFXType = data.type_trail;
			ClientProxy.get().particleManager.addEffect(gibsSys);
			/*
			for (int i = 0; i < count; i++) {
				double vx = (0.5 - entity.world.random.nextDouble()) * 0.35;
				double vy;
				if (entity.isOnGround()) {
					vy = (entity.world.random.nextDouble()) * 0.35;
				} else {
					vy = (0.5 - entity.world.random.nextDouble()) * 0.35;
				}
				double vz = (0.5 - entity.world.random.nextDouble()) * 0.35;

				FlyingGibs ent = new FlyingGibs(TGEntities.FLYING_GIBS, entity.world, entity, data, x, y, z, motionX * 0.35 + vx,
						motionY * 0.35 + vy, motionZ * 0.35 + vz, (entity.getWidth() + entity.getHeight()) / 2.0f, gibs.get(i), gibsTexture);

				//TODO
				//entity.world.spawnEntity(ent);
				((ClientWorld) MinecraftClient.getInstance().player.world).addEntity(ent.getEntityId(), entity);
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
	
	public static ArrayList<ModelPart> getModelParts(EntityModel<?> model) {
		ArrayList<ModelPart> parts = new ArrayList<ModelPart>();		
		if (model instanceof CompositeEntityModel) {
			for (ModelPart part : ((CompositeEntityModel<?>)model).getParts()) {
				parts.add(part);
			}			
		} else if (model instanceof AnimalModel) {
			for (ModelPart part : ((ITGAnimalModel)model)._getBodyParts()) {
				parts.add(part);
			}
			for (ModelPart part : ((ITGAnimalModel)model)._getHeadParts()) {
				parts.add(part);
			}
		} else {
			return null;
		}
		
		return parts;
	}
	
	public static GoreData getGoreData(Class<? extends LivingEntity> entityClass) {
		GoreData data = goreStats.get(entityClass);
		if (data != null) {
			return data;
		}else {
			data = new GoreData();
			data.bloodColorR = genericGore.bloodColorR;
			data.bloodColorG = genericGore.bloodColorG;
			data.bloodColorB = genericGore.bloodColorB;
			data.type_main = genericGore.type_main;
			data.type_trail = genericGore.type_trail;
			data.type_gibs = genericGore.type_gibs;
			data.sound = genericGore.sound;
			data.numGibs = -1; //TODO
			data.init();
			goreStats.put(entityClass, data);
			return data;
		}
	}
	
	public static void reloadAllFX() {
		for (GoreData data : goreStats.values()) {
			data.init();
		}
	}
	
	public static class GoreData {
		//public ModelGibs model = null;
		public ArrayList<ModelPart> modelParts;
		public Identifier texture = null;
		int numGibs = -1;
		public float particleScale = 1.0f;
		public float modelScale = 1.0f;
		 	
		int bloodColorR;
		int bloodColorG;
		int bloodColorB;
		
		//public boolean showBlood = true;
		String fx_main = "GoreFX_Blood";
		String fx_trail ="GoreTrailFX_Blood";
		String fx_gibs ="Gore3dGibsFX_Generic";
		public SoundEvent sound = TGSounds.DEATH_GORE;
		
		public TGParticleSystemType type_main;
		public TGParticleSystemType type_trail;
		public TGParticleSystemType type_gibs;

		public float minPartScale = 1.0f;
		public float maxPartScale = 1.0f;

		public GoreData() {}
		
		public GoreData(int bloodColorR, int bloodColorG, int bloodColorB) {
			this.bloodColorR = bloodColorR;
			this.bloodColorG = bloodColorG;
			this.bloodColorB = bloodColorB;
		}
		
		public GoreData setModelParts(ArrayList<ModelPart> modelParts) {
			this.modelParts = modelParts;
			return this;
		}
		
		public GoreData setNumGibs(int gibs) {
			this.numGibs = gibs;
			return this;
		}
		
		public GoreData setTexture(Identifier texture) {
			this.texture = texture;
			return this;
		}
		
		public GoreData setFXscale(float scale) {
			this.particleScale = scale;
			return this;
		}
		
		public GoreData setFX(String fx_main, String fx_trail) {
			this.fx_main = fx_main;
			this.fx_trail = fx_trail;
			return this;
		}
		
		public GoreData setSound(SoundEvent sound) {
			this.sound = sound;
			return this;
		}
		
		public void init() {
			type_main = new TGParticleSystemType();

			if (TGFX.FXList.containsKey(fx_main.toLowerCase())) {
				TGFXType fxtype_main = TGFX.FXList.get(fx_main.toLowerCase());
				if (fxtype_main instanceof TGParticleSystemType) {
					this.type_main = getExtendedType((TGParticleSystemType) fxtype_main);
				}else {
					this.type_main = null;
				}
			}else {
				this.type_main = null;
			}
			
			type_trail = new TGParticleSystemType();
			
			if (TGFX.FXList.containsKey(fx_trail.toLowerCase())) {
				TGFXType fxtype_trail = TGFX.FXList.get(fx_trail.toLowerCase());
				if (fxtype_trail instanceof TGParticleSystemType) {
					this.type_trail = getExtendedType((TGParticleSystemType) fxtype_trail);
				}else {
					this.type_trail = null;
				}
			}else {
				this.type_trail = null;
			}
			
			type_gibs = new TGParticleSystemType();
			
			if (TGFX.FXList.containsKey(fx_gibs.toLowerCase())) {
				TGFXType fxtype_gibs = TGFX.FXList.get(fx_gibs.toLowerCase());
				if (fxtype_gibs instanceof TGParticleSystemType) {
					this.type_gibs = getExtendedType((TGParticleSystemType) fxtype_gibs);
				}else {
					this.type_gibs = null;
				}
			}else {
				this.type_gibs = null;
			}
		}

		/**
		 * Add a random scale to individual gibs.
		 * @param f
		 * @param g
		 */
		public void setRandomScale(float min, float max) {
			minPartScale = min;
			maxPartScale = max;
		}
		
		
		private TGParticleSystemType getExtendedType(TGParticleSystemType supertype) {
			TGParticleSystemType type = new TGParticleSystemType();
			type.extend(supertype);
			if (type.colorEntries.size() >= 1) {
				type.colorEntries.get(0).r = (float)this.bloodColorR /255.0f;
				type.colorEntries.get(0).g = (float)this.bloodColorG /255.0f;
				type.colorEntries.get(0).b = (float)this.bloodColorB /255.0f;
			}
			type.sizeMin *= particleScale;
			type.sizeMax *= particleScale;
			type.sizeRateMin *= particleScale;
			type.sizeRateMax *= particleScale;
			type.startSizeRateDampingMin *= particleScale;
			type.startSizeRateMin *= particleScale;
			type.startSizeRateMax *= particleScale;
			for (int i = 0; i < type.volumeData.length; i++) {
				type.volumeData[i]*=particleScale;
			}
			return type;
		}
	}
}
