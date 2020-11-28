package techguns.client;

import java.util.List;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGEntities;
import techguns.TGIdentifier;
import techguns.TGItems;
import techguns.TGPacketsS2C;
import techguns.TGuns;
import techguns.api.client.ClientDisconnectEvent;
import techguns.api.client.ClientGameJoinEvent;
import techguns.client.audio.TGSound;
import techguns.client.models.guns.ModelAK;
import techguns.client.models.guns.ModelBiogun;
import techguns.client.models.guns.ModelGuidedMissileLauncher;
import techguns.client.models.guns.ModelHandgun;
import techguns.client.models.guns.ModelM4;
import techguns.client.models.guns.ModelRocketLauncher;
import techguns.client.models.guns.ModelScar;
import techguns.client.models.guns.ModelTFG;
import techguns.client.models.items.ModelARMagazine;
import techguns.client.models.items.ModelAS50Mag;
import techguns.client.models.items.ModelLmgMag;
import techguns.client.models.projectiles.ModelRocket;
import techguns.client.particle.TGFX;
import techguns.client.particle.TGParticleManager;
import techguns.client.particle.TGParticleSystem;
import techguns.client.render.TGRenderRegistries;
import techguns.client.render.entities.GenericProjectileRenderer;
import techguns.client.render.entities.RenderBioGunProjectile;
import techguns.client.render.entities.RenderInvisibleProjectile;
import techguns.client.render.entities.RenderRocketProjectile;
import techguns.client.render.entities.RenderStoneBulletProjectile;
import techguns.client.render.fx.ScreenEffect;
import techguns.client.render.item.GunAnimation;
import techguns.client.render.item.RenderGunBase;
import techguns.client.render.item.RenderGunBase90;
import techguns.client.render.item.RenderItemBase;
import techguns.client.render.item.RenderItemBaseRocketItem;
import techguns.client.render.item.RenderItemLMGMag;
import techguns.sounds.TGSoundCategory;
import techguns.util.EntityCondition;

public class ClientProxy implements ClientModInitializer {
	public static ClientProxy INSTANCE;
	
	public TGParticleManager particleManager = new TGParticleManager();
	
	public float player_zoom = 1.0f;
	
	//local muzzle flash jitter offsets
	public float muzzleFlashJitterX = 0; //-1.0 to 1.0
	public float muzzleFlashJitterY = 0; //-1.0 to 1.0
	public float muzzleFlashJitterAngle = 0; //-1.0 to 1.0
	public float muzzleFlashJitterScale = 0; //-1.0 to 1.0
	
	public boolean keyFirePressedMainhand;
	public boolean keyFirePressedOffhand;
	
	//TODO set this
	public double PARTIAL_TICK_TIME=0.0d;

	public long lastReloadsoundPlayed=0L; 
	
	public Keybinds keybinds;
	
	public static ClientProxy get() {
		return INSTANCE;
	}
	
	@Override
	public void onInitializeClient() {
		INSTANCE=this;
		
		TGRenderRegistries.registerItemRenderer(TGuns.M4, new RenderGunBase(new ModelM4(), new TGIdentifier("textures/guns/m4texture.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.18f, -1.29f, 0.75f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0f,-0.05f}, //First Person
					{0f,0.01f,-0.1f}, //Third Person
					{0f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.13f, -1f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f));
	
		TGRenderRegistries.registerItemRenderer(TGuns.SCAR,new RenderGunBase(new ModelScar(),2, new TGIdentifier("textures/guns/scar_texture.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0.1f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.23f, -1.48f, 0.78f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0.04f,-0.15f}, //First Person
					{0f,0.02f,-0.11f}, //Third Person
					{0.05f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0.02f,-0.09f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.14f, -1.04f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f).setScope(ScreenEffect.sniperScope).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.05f, 2.0f));
		
		
		this.register_ammo_itemrenderers();
		
		TGRenderRegistries.registerItemRenderer(TGuns.GUIDED_MISSLE_LAUNCHER,new RenderGunBase90(new ModelGuidedMissileLauncher(),1, new TGIdentifier("textures/guns/guidedmissilelauncher.png")).setBaseTranslation(-0.4f, -0.2f, RenderItemBase.SCALE*0.5f)
				.setGUIScale(0.35f).setChargeTranslationAmount(0).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.39f, -0.6f, 0.87f, 0).setTransformTranslations(new float[][]{
					{-0.13f,0.3f,0.62f}, //First Person
					{0,0.09f,0.28f}, //Third Person
					{0.0f,0.03f,0.0f}, //GUI
					{0.0f,0.0f,0}, //Ground
					{0,0,-0.04f} //frame
				}).setMuzzleFXPos3P(0.09f, -0.26f));
		
		TGRenderRegistries.registerItemRenderer(TGuns.ROCKET_LAUNCHER,new RenderGunBase90(new ModelRocketLauncher(),2, new TGIdentifier("textures/guns/rocketlauncher.png")).setBaseTranslation(-0.4f, -0.2f, -RenderItemBase.SCALE*0.5f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.39f, -0.6f, 0.87f, 0).setTransformTranslations(new float[][]{
					{-0.13f,0.3f,0.32f}, //First Person
					{0,0.02f,0.07f}, //Third Person
					{-0.06f,0.0f,0.0f}, //GUI
					{0.0f,0.0f,0}, //Ground
					{0,0,0f} //frame
				}).setMuzzleFXPos3P(0.09f, -0.26f));
		
		TGRenderRegistries.registerItemRenderer(TGuns.BIOGUN,new RenderGunBase90(new ModelBiogun(),1, new TGIdentifier("textures/guns/biogun.png")).setBaseTranslation(0.35f, -0.2f, RenderItemBase.SCALE-0.1f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleGreenFlare, 0, 0.23f, -0.51f, 0.55f,0).setTransformTranslations(new float[][]{
					{0f,0.16f,0.05f}, //First Person
					{0f,0.07f,-0.05f}, //Third Person
					{0f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.1f, -0.51f).setChargeTranslationAmount(0.05f));
		
		TGRenderRegistries.registerItemRenderer(TGuns.AK47,new RenderGunBase(new ModelAK(),1, new TGIdentifier("textures/guns/ak47texture.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0).setBaseScale(0.75f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.18f, -1.36f, 0.8f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0.06f,-0.02f}, //First Person
					{0f,0f,-0.08f}, //Third Person
					{0.06f,-0.01f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.08f, -1.02f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f));
		
	
		TGRenderRegistries.registerItemRenderer(TGuns.HANDCANNON,new RenderGunBase90(new ModelHandgun(),1, new TGIdentifier("textures/guns/handgun.png")).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE-0.1f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.16f, -0.75f, 0.9f,0).setReloadAnim(GunAnimation.breechReload, -0.15f, 55.0f).setReloadAnim3p(GunAnimation.breechReload, 0f, 55.0f).setTransformTranslations(new float[][]{
					{0,0.03f,-0.12f}, //First Person
					{0.0f,-0.05f,-0.09f}, //Third Person
					{0.0f,0.0f,0}, //GUI
					{0.0f,0.0f,0}, //Ground
					{0,0,0f} //frame
				}).setMuzzleFXPos3P(0.03f, -0.59f).setRecoilAnim(GunAnimation.genericRecoil, 0.2f, 25.0f));
		
		
		TGRenderRegistries.registerItemRenderer(TGuns.TFG, new RenderGunBase90(new ModelTFG(),1, new TGIdentifier("textures/guns/tfg.png")).setBaseTranslation(-0.46f, -0.38f, RenderItemBase.SCALE-0.125f)
				.setBaseScale(1.20f).setGUIScale(0.30f).setMuzzleFx(ScreenEffect.muzzleFlashTFG, 0.0f, 0.18f, -0.87f, 0.9f,0).setTransformTranslations(new float[][]{
					{0f,-0.03f,0.16f}, //First Person
					{0f,-0.09f,-0.26f}, //Third Person
					{0.04f,-0.04f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{-0.07f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.09f, -1.14f).setChargeTranslationAmount(0.05f).setFirstPersonScale(0.45f));
		
		
		EntityRendererRegistry.INSTANCE.register(TGEntities.GENERIC_PROJECTILE, (dispatcher, context) -> {
            return new GenericProjectileRenderer(dispatcher);
        });
		
		EntityRendererRegistry.INSTANCE.register(TGEntities.GUIDED_MISSILE, (dispatcher, context) -> {
            return new RenderRocketProjectile(dispatcher);
        });
		
		EntityRendererRegistry.INSTANCE.register(TGEntities.ROCKET_PROJECTILE, (dispatcher, context) -> {
            return new RenderRocketProjectile(dispatcher);
        });
		
		EntityRendererRegistry.INSTANCE.register(TGEntities.BIOGUN_PROJECTILE,  (dispatcher, context) -> {
            return new RenderBioGunProjectile(dispatcher);
        });
		
		EntityRendererRegistry.INSTANCE.register(TGEntities.TFG_PROJECTILE,  (dispatcher, context) -> {
			return new RenderInvisibleProjectile(dispatcher);
		});
		
		EntityRendererRegistry.INSTANCE.register(TGEntities.STONEBULLET_PROJECTILE,  (dispatcher, context) -> {
            return new RenderStoneBulletProjectile(dispatcher);
        });
		
        keybinds = new Keybinds();
        keybinds.init();
        
        TGFX.loadFXList();
        
		TGPacketsS2C.initialize();
	}

	public void register_ammo_itemrenderers() {
		float[][] m4magTranslations = {
				{0,0.25f,0}, //First Person
				{0f,0.25f,0.05f}, //Third Person
				{0.1f,0.25f,0}, //GUI
				{0,-0.1f,0}, //Ground
				{0,0,-0.05f} //frame
		};
		
		float[][] as50magTranslations = {
				{0,0f,0}, //First Person
				{0f,0f,0f}, //Third Person
				{0f,0.05f,0}, //GUI
				{0,-0.1f,0}, //Ground
				{0,0,0f} //frame
		};
		
		float[][] lmgmagTranslations = {
				{0,0.35f,0}, //First Person
				{0f,0.15f,0.0f}, //Third Person
				{0.1f,0.25f,0}, //GUI
				{0,0f,0}, //Ground
				{0,0.25f,-0.05f} //frame
		};
		
		float[][] rocketTranslations = {
				{0,0f,0f}, //First Person
				{0f,-0.1f,0.02f}, //Third Person
				{0.0f,0.0f,0}, //GUI
				{0.0f,0.0f,0}, //Ground
				{0,0,0f} //frame
		};
		
		TGRenderRegistries.registerItemRenderer(TGItems.ASSAULTRIFLE_MAGAZINE, new RenderItemBase(new ModelARMagazine(false), new TGIdentifier("textures/guns/ar_mag.png")).setBaseScale(1.25f).setGUIScale(0.85f).setBaseTranslation(0, -0.2f, 0).setTransformTranslations(m4magTranslations));
		TGRenderRegistries.registerItemRenderer(TGItems.ASSAULTRIFLE_MAGAZINE_EMPTY, new RenderItemBase(new ModelARMagazine(true), new TGIdentifier("textures/guns/ar_mag.png")).setBaseScale(1.25f).setGUIScale(0.85f).setBaseTranslation(0, -0.2f, 0).setTransformTranslations(m4magTranslations));
		
		//TGRenderRegistries.register_itemrenderer(TGItems.ASSAULTRIFLE_MAGAZINE_INCENDIARY, new RenderItemBase(new ModelARMagazine(false), new TGIdentifier("textures/guns/ar_mag_inc.png")).setBaseScale(1.25f).setGUIScale(0.85f).setBaseTranslation(0, -0.2f, 0).setTransformTranslations(m4magTranslations));
		
		
		TGRenderRegistries.registerItemRenderer(TGItems.LMG_MAGAZINE, new RenderItemLMGMag(new ModelLmgMag(false), new TGIdentifier("textures/guns/lmg_mag.png")).setBaseScale(1.25f).setGUIScale(0.75f).setBaseTranslation(0, 0f, 0.2f).setTransformTranslations(lmgmagTranslations));
		TGRenderRegistries.registerItemRenderer(TGItems.LMG_MAGAZINE_EMPTY, new RenderItemLMGMag(new ModelLmgMag(true), new TGIdentifier("textures/guns/lmg_mag.png")).setBaseScale(1.25f).setGUIScale(0.75f).setBaseTranslation(0, 0f, 0.2f).setTransformTranslations(lmgmagTranslations));	
		
		//sharedRenderer.addRenderForType("lmgmagazine_incendiary", new RenderItemLMGMag(new ModelLmgMag(false), new ResourceLocation(Techguns.MODID,"textures/guns/lmg_mag_inc.png")).setBaseScale(1.25f).setGUIScale(0.75f).setBaseTranslation(0, 0f, 0.2f).setTransformTranslations(lmgmagTranslations));
		
		
		TGRenderRegistries.registerItemRenderer(TGItems.AS50_MAGAZINE, new RenderItemBase(new ModelAS50Mag(false), new TGIdentifier("textures/guns/as50_mag.png")).setBaseScale(1.5f).setGUIScale(0.75f).setBaseTranslation(0.0325f, -0.2f, 0.33f).setTransformTranslations(as50magTranslations));
		TGRenderRegistries.registerItemRenderer(TGItems.AS50_MAGAZINE_EMPTY, new RenderItemBase(new ModelAS50Mag(true), new TGIdentifier("textures/guns/as50_mag.png")).setBaseScale(1.5f).setGUIScale(0.75f).setBaseTranslation(0.0325f, -0.2f, 0.33f).setTransformTranslations(as50magTranslations));
		
		//sharedRenderer.addRenderForType("as50magazine_incendiary", new RenderItemBase(new ModelAS50Mag(false), new ResourceLocation(Techguns.MODID,"textures/guns/as50_mag_inc.png")).setBaseScale(1.5f).setGUIScale(0.75f).setBaseTranslation(0.0325f, -0.2f, 0.33f).setTransformTranslations(as50magTranslations));
		//sharedRenderer.addRenderForType("as50magazine_explosive", new RenderItemBase(new ModelAS50Mag(false), new ResourceLocation(Techguns.MODID,"textures/guns/as50_mag_exp.png")).setBaseScale(1.5f).setGUIScale(0.75f).setBaseTranslation(0.0325f, -0.2f, 0.33f).setTransformTranslations(as50magTranslations));
		
		
		TGRenderRegistries.registerItemRenderer(TGItems.ROCKET, new RenderItemBaseRocketItem(new ModelRocket(), new TGIdentifier("textures/guns/rocket.png")).setBaseScale(1.5f).setGUIScale(0.5f).setBaseTranslation(0, 0, 0.1f).setTransformTranslations(rocketTranslations).setFirstPersonScale(0.35f));
		
		//sharedRenderer.addRenderForType("rocket_nuke", new RenderItemBaseRocketItem(new ModelRocket(), new ResourceLocation(Techguns.MODID,"textures/guns/rocket_nuke.png")).setBaseScale(1.5f).setGUIScale(0.5f).setBaseTranslation(0, 0, 0.1f).setTransformTranslations(rocketTranslations).setFirstPersonScale(0.35f));
		
		//sharedRenderer.addRenderForType("rocket_high_velocity", new RenderItemBaseRocketItem(new ModelRocket(), new ResourceLocation(Techguns.MODID,"textures/guns/rocket_hv.png")).setBaseScale(1.5f).setGUIScale(0.5f).setBaseTranslation(0, 0, 0.1f).setTransformTranslations(rocketTranslations).setFirstPersonScale(0.35f));
		
		
		ClientTickEvents.END_WORLD_TICK.register((world) -> {
			ClientProxy.get().particleManager.tickParticles();
		} );
		
		ClientDisconnectEvent.EVENT.register((MinecraftClient client) -> {
			ClientProxy.get().particleManager.clear();
		});
		
		ClientGameJoinEvent.EVENT.register((MinecraftClient clinet) -> {
			ClientProxy.get().particleManager.clear();
		});
	}
	
	public PlayerEntity getPlayerClient() {
		MinecraftClient mc = MinecraftClient.getInstance();
		return mc.player;
	}
	
	public void playSoundOnEntity(Entity ent, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, TGSoundCategory category, EntityCondition condition) {
		MinecraftClient.getInstance().getSoundManager().play(new TGSound(soundname, ent, volume, pitch, repeat, moving, gunPosition, category, condition));
	}
	
	public void handleSoundEvent(PlayerEntity ply, int entityId, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving,
			boolean gunPosition,boolean playOnOwnPlayer, TGSoundCategory soundCategory, EntityCondition condition) {
		Entity entity = null;
		if (entityId != -1) {
			entity = ply.world.getEntityById(entityId);
		}

		if(entity!=null){
			if (entity != ply || playOnOwnPlayer ) {
				MinecraftClient.getInstance().getSoundManager().play(new TGSound(soundname,entity,volume,pitch, repeat, moving, gunPosition,soundCategory));
			}
		} else {
			//System.out.println("Handle Sound entity null, NEED FIX!");
		}
	}	
	
	public void playSoundOnPosition(SoundEvent soundname, float posx, float posy, float posz, float volume, float pitch, boolean repeat, TGSoundCategory soundCategory) {
		MinecraftClient.getInstance().getSoundManager().play(new TGSound(soundname,posx,posy,posz,volume,pitch, repeat,soundCategory));
	}
	
	public void playSoundOnEntity(Entity ent, SoundEvent soundname, float volume, float pitch, boolean repeat,
			boolean moving, boolean gunPosition, boolean playForOwnPlayer, TGSoundCategory category) {
		if(playForOwnPlayer || ent != this.getPlayerClient()) {
			this.playSoundOnEntity(ent, soundname, volume, pitch, repeat, moving, gunPosition, category);
		}
	}
	
	public void playSoundOnEntity(Entity ent, SoundEvent soundname, float volume, float pitch, boolean repeat,
			boolean moving, boolean gunPosition, boolean playForOwnPlayer, TGSoundCategory category, EntityCondition condition) {
		if(playForOwnPlayer || ent != this.getPlayerClient()) {
			this.playSoundOnEntity(ent, soundname, volume, pitch, repeat, moving, gunPosition, category, condition);
		}
	}

	public void playSoundOnEntity(Entity ent, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, TGSoundCategory category) {
		MinecraftClient.getInstance().getSoundManager().play(new TGSound(soundname,ent, volume, pitch, repeat, moving, gunPosition, category));
	}
	
	public void createFX(String name, World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ){	
		List<TGParticleSystem> systems = TGFX.createFX(world, name, posX, posY, posZ, motionX, motionY, motionZ);
		if (systems!=null) {
			systems.forEach(s -> particleManager.addEffect(s));//Minecraft.getMinecraft().effectRenderer.addEffect(s));
		}
	}
	
	public void createFX(String name, World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float pitch, float yaw){	
		List<TGParticleSystem> systems = TGFX.createFX(world, name, posX, posY, posZ, motionX, motionY, motionZ);
		if (systems!=null) {
			for (TGParticleSystem s : systems) {
				s.rotationPitch = pitch;
				s.rotationYaw = yaw;
				particleManager.addEffect(s);
			}
		}
	}

	public void createFX(String name, World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float scale){	
		List<TGParticleSystem> systems = TGFX.createFX(world, name, posX, posY, posZ, motionX, motionY, motionZ);
		if (systems!=null) {
			for (TGParticleSystem s : systems) {
				s.scale = scale;
				particleManager.addEffect(s);
			}
		}
	}
	
	public void createFXOnEntity(String name, Entity ent) {
		List<TGParticleSystem> systems = TGFX.createFXOnEntity(ent, name);
		if (systems!=null) {
			systems.forEach(s -> {
				s.condition=EntityCondition.ENTITY_ALIVE;
				particleManager.addEffect(s);
			});
		}
	}

	public void createFXOnEntity(String name, Entity ent, float scale) {
		List<TGParticleSystem> systems = TGFX.createFXOnEntity(ent, name);
		if (systems!=null) {
			for (TGParticleSystem s : systems) {
				s.scale = scale;
				s.condition=EntityCondition.ENTITY_ALIVE;
				particleManager.addEffect(s);
			}
		}
	}
	
	public void createFXOnEntityWithOffset(String name, Entity ent, float offsetX, float offsetY, float offsetZ, boolean attachToHead, EntityCondition condition) {
		List<TGParticleSystem> systems = TGFX.createFXOnEntity(ent, name);
		if (systems!=null) {
			for (TGParticleSystem s : systems) {
				s.entityOffset = new Vec3d(offsetX, offsetY, offsetZ);
				s.attachToHead = attachToHead;
				s.condition = condition;
				particleManager.addEffect(s);
			}
		}
	}
	
}
