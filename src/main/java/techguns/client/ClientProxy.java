package techguns.client;

import java.util.List;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
import techguns.api.entity.ITGLivingEntity;
import techguns.client.audio.TGSound;
import techguns.client.modelloader.TGObjLoader;
import techguns.client.modelloader.TGObjModel;
import techguns.client.models.ModelBaseBaked;
import techguns.client.models.guns.*;
import techguns.client.models.items.ModelARMagazine;
import techguns.client.models.items.ModelAS50Mag;
import techguns.client.models.items.ModelLmgMag;
import techguns.client.models.projectiles.ModelRocket;
import techguns.client.particle.TGFX;
import techguns.client.particle.TGParticleManager;
import techguns.client.particle.TGParticleSystem;
import techguns.client.render.TGRenderRegistries;
import techguns.client.render.entities.*;
import techguns.client.render.fx.ScreenEffect;
import techguns.client.render.item.*;
import techguns.deatheffects.EntityDeathUtils.DeathType;
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

		ModelLoadingRegistry.INSTANCE.registerResourceProvider(TGObjLoader.INSTANCE);
		ModelLoadingRegistry.INSTANCE.registerVariantProvider(r -> TGObjLoader.INSTANCE::loadModelResource);

//		TGObjLoader.INSTANCE.registerManualModel(new TGIdentifier("item/gaussrifle.obj"));
		TGObjLoader.INSTANCE.registerManualModel(new TGIdentifier("item/grenadelauncher.obj"), true);
		TGObjLoader.INSTANCE.registerManualModel(new TGIdentifier("item/grenadelauncher_1.obj"), true);
		TGObjLoader.INSTANCE.registerManualModel(new TGIdentifier("item/grenade40mm.obj"));

		TGRenderRegistries.registerItemRenderer(TGuns.M4, new RenderGunBase(new ModelM4(), new TGIdentifier("textures/guns/m4texture.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.18f, -1.29f, 0.75f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0f,-0.05f}, //First Person
					{0f,0.01f,-0.1f}, //Third Person
					{0f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.13f, -1f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f));
	
		TGRenderRegistries.registerItemRenderer(TGuns.M4_INFILTRATOR,new RenderGunBase(new ModelM4Infiltrator(),1, new TGIdentifier("textures/guns/m4_uq_texture.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0)
				.setGUIScale(0.35f)/*.setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.18f, -1.5f, 0.5f,0)*/.setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0f,-0.05f}, //First Person
					{0f,0.01f,-0.1f}, //Third Person
					{0f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.14f, -1.15f).setScope(ScreenEffect.sniperScope).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.05f, 1.0f));
		
		
		TGRenderRegistries.registerItemRenderer(TGuns.SCAR,new RenderGunBase(new ModelScar(),2, new TGIdentifier("textures/guns/scar_texture.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0.1f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.23f, -1.48f, 0.78f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0.04f,-0.15f}, //First Person
					{0f,0.02f,-0.11f}, //Third Person
					{0.05f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0.02f,-0.09f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.14f, -1.04f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f).setScope(ScreenEffect.sniperScope).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.05f, 2.0f));
		
		
		this.register_ammo_itemrenderers();
		
		TGRenderRegistries.registerItemRenderer(TGuns.GUIDED_MISSLE_LAUNCHER,new RenderGunBase90(new ModelGuidedMissileLauncher(),2, new TGIdentifier("textures/guns/guidedmissilelauncher.png")).setBaseTranslation(-0.4f, -0.2f, RenderItemBase.SCALE*0.5f)
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
		
		
		TGRenderRegistries.registerItemRenderer(TGuns.AS50,new RenderGunBase(new ModelAS50(),1, new TGIdentifier("textures/guns/as50texture.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0)
				.setBaseScale(0.85f).setGUIScale(0.30f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.29f, -1.82f, 1.15f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.25f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0.06f,-0.1f}, //First Person
					{0f,0.0f,-0.05f}, //Third Person
					{0.13f,-0.09f,-0.05f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,-0.2f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.17f, -1.29f).setScope(ScreenEffect.sniperScope).setRecoilAnim(GunAnimation.genericRecoil, 0.25f, 5.0f).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.2f, 2.0f));
		
		TGRenderRegistries.registerItemRenderer(TGuns.AUG,new RenderGunBase(new ModelAUG(),2,new TGIdentifier("textures/guns/augtexture.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0.1f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.19f, -1.45f, 0.75f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
					{0f,0.01f,-0.15f}, //First Person
					{0f,0.0f,-0.01f}, //Third Person
					{0f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0.02f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.12f, -0.87f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f).setScope(ScreenEffect.sniperScope).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.075f, 1.0f));
	
		TGRenderRegistries.registerItemRenderer(TGuns.MINIGUN,new RenderGunBase90(new ModelMinigun(),2,new TGIdentifier("textures/guns/minigun.png")).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE*0.5f).setBaseScale(1.2f)
				.setGUIScale(0.3f).setMuzzleFx(ScreenEffect.muzzleFlash_minigun, -0.04f, 0.05f, -1.25f, 0.8f,0.04f).setTransformTranslations(new float[][]{
					{0f,-0.16f,0.1f}, //First Person
					{0f,-0.53f,0.2f}, //Third Person
					{0.12f,-0.02f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(-0.38f, -0.78f).setRecoilAnim(GunAnimation.genericRecoil, 0.05f, 3.0f));
		
		TGRenderRegistries.registerItemRenderer(TGuns.CHAINSAW,new RenderGunBase90(new ModelChainsaw(),2, new TGIdentifier("textures/guns/chainsaw.png")).setBaseTranslation(-0.4f, -0.2f, RenderItemBase.SCALE-0.09f)
				.setBaseScale(0.95f).setGUIScale(0.45f).setTransformTranslations(new float[][]{
					{0f,-0.08f,0.15f}, //First Person
					{0f,-0.5f,0.04f}, //Third Person
					{0.03f,0.01f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{-0.07f,-0.03f,-0.11f} //frame
				}));	
		
		TGRenderRegistries.registerItemRenderer(TGuns.PISTOL,new RenderGunBase(new ModelPistol(),2, new TGIdentifier("textures/guns/pistol3.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.3f, -0.4f)
				.setBaseScale(1.2f).setGUIScale(0.9f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0.03f, 0.2f, -0.5f, 0.55f,-0.03f).setTransformTranslations(new float[][]{
					{0,0.09f,-0.02f}, //First Person
					{0.0f,-0.03f,0.0f}, //Third Person
					{0.02f,-0.08f,0}, //GUI
					{0.02f,-0.08f,0}, //Ground
					{0,0,0f} //frame
				}).setRecoilAnim(GunAnimation.genericRecoil, 0.025f, 12.0f).setMuzzleFlashJitter(0.01f, 0.01f, 5.0f, 0.05f).setMuzzleFXPos3P(0.07f, -0.26f));
		
		TGRenderRegistries.registerItemRenderer(TGuns.NUCLEAR_DEATHRAY,new RenderGunBase90(new ModelNDR(), 2, new TGIdentifier("textures/guns/ndr.png")).setBaseTranslation(1f, -0.2f, RenderItemBase.SCALE*1.5f-0.09f)
				.setBaseScale(1.2f).setGUIScale(0.40f).setMuzzleFx(ScreenEffect.muzzleFlashNukeBeam, 0, 0.19f, -0.91f, 0.65f,0).setTransformTranslations(new float[][]{
					{0f,0.02f,0.09f}, //First Person
					{-0.01f,0.04f,0.3f}, //Third Person
					{0.11f,-0.08f,0f}, //GUI
					{0f,0f,0.15f}, //Ground
					{-0.23f,-0.08f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.11f, -0.83f).setRecoilAnim(GunAnimation.swayRecoil, 0.025f, 0.75f));
		TGRenderRegistries.registerItemRenderer(TGuns.COMBAT_SHOTGUN,new RenderGunBase90(new ModelCombatShotgun(),2, new TGIdentifier("textures/guns/combatshotgun.png")).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE-0.1f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.21f, -0.91f, 0.75f,0).setTransformTranslations(new float[][]{
					{0f,0.03f,0f}, //First Person
					{0f,-0.01f,-0.10f}, //Third Person
					{0.05f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.12f, -0.81f).setRecoilAnim(GunAnimation.genericRecoil, 0.3f, 15.0f));
		
		
		
		TGRenderRegistries.registerItemRenderer(TGuns.SAWEDOFF,new RenderGunBase90(new ModelSawedOff(),1, new TGIdentifier("textures/guns/sawedoff.png")).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE-0.1f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.16f, -0.75f, 1.05f,0).setReloadAnim(GunAnimation.breechReload, -0.15f, 55.0f).setReloadAnim3p(GunAnimation.breechReload, 0f, 55.0f).setTransformTranslations(new float[][]{
					{0f,0f,0f}, //First Person
					{0f,-0.04f,0f}, //Third Person
					{0f,0f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.06f, -0.49f).setRecoilAnim(GunAnimation.genericRecoil, 0.2f, 20.0f));
		
		TGRenderRegistries.registerItemRenderer(TGuns.LMG,new RenderGunBase(new ModelLMG(),1, new TGIdentifier("textures/guns/mg2_texture.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.21f, -1.5f, 0.78f,0).setMuzzleFXPos3P(0.13f, -0.98f).setRecoilAnim(GunAnimation.genericRecoil, 0.01f, 2.0f).setTransformTranslations(new float[][]{
					{0f,0.02f,-0.09f}, //First Person
					{0f,0f,-0.06f}, //Third Person
					{0.05f,-0.03f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.14f, -1.02f).setMuzzleFlashJitter(0.03f, 0.03f, 5.0f, 0.1f));

		TGRenderRegistries.registerItemRenderer(TGuns.THOMPSON,new RenderGunBase90(new ModelThompson(),1, new TGIdentifier("textures/guns/thompson.png")).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE*0.5f-0.1f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.14f, -0.75f, 0.55f,0).setMuzzleFXPos3P(0.1f, -0.59f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f));
	
		TGRenderRegistries.registerItemRenderer(TGuns.BOLTACTION,new RenderGunBase90(new ModelBoltaction(),1, new TGIdentifier("textures/guns/boltactionrifle.png")).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE-0.1f).setBaseScale(1.35f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.21f, -1.48f, 0.9f,0).setScope(ScreenEffect.sniperScope).setTransformTranslations(new float[][]{
					{0f,-0.02f,-0.09f}, //First Person
					{0f,-0.04f,-0.11f}, //Third Person
					{0.1f,-0.08f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,-0.025f} //frame
				}).setMuzzleFXPos3P(0.12f, -1.13f).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.20f, 2.0f));
	
		TGRenderRegistries.registerItemRenderer(TGuns.REVOLVER,new RenderGunBase90(new ModelRevolver(),1, new TGIdentifier("textures/guns/revolver.png")).setBaseTranslation(-0.35f, -0.2f, RenderItemBase.SCALE*0.5f-0.1f)
				.setBaseScale(0.75f).setGUIScale(0.75f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.25f, -0.41f, 0.5f,0).setTransformTranslations(new float[][]{
					{0f,0.14f,0.01f}, //First Person
					{0f,0.0f,0.0f}, //Third Person
					{0.05f,0.01f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,0f} //frame
				}).setMuzzleFXPos3P(0.09f, -0.30f).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 10.0f));
	
		TGRenderRegistries.registerItemRenderer(TGuns.GOLDEN_REVOLVER,new RenderGunBase90(new ModelGoldenRevolver(),1, new TGIdentifier("textures/guns/goldenrevolver.png")).setBaseTranslation(-0.35f, -0.2f, RenderItemBase.SCALE*0.5f-0.1f)
				.setBaseScale(0.75f).setGUIScale(0.75f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.25f, -0.41f, 0.5f,0).setTransformTranslations(new float[][]{
					{0f,0.14f,0.01f}, //First Person
					{0f,0.0f,0.0f}, //Third Person
					{0.05f,0.01f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0f,0f,0f} //frame
				}).setMuzzleFXPos3P(0.09f, -0.30f).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 15.0f));
	
		TGRenderRegistries.registerItemRenderer(TGuns.MAC10,new RenderGunBase(new ModelMac10(),1, new TGIdentifier("textures/guns/mac10texture.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.45f, -0.3f)
				.setBaseScale(1.2f).setGUIScale(0.55f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.23f, -0.46f, 0.5f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.06f, 3.0f).setTransformTranslations(new float[][]{
					{0f,0f,-0.05f}, //First Person
					{0f,-0.10f,0.01f}, //Third Person
					{-0.02f,-0.02f,0f}, //GUI
					{0f,0.03f,0f}, //Ground
					{0f,-0.05f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.07f, -0.26f).setMuzzleFlashJitter(0.02f, 0.05f, 5.0f, 0.1f));
		
		TGRenderRegistries.registerItemRenderer(TGuns.VECTOR,new RenderGunBase(new ModelVector(),1, new TGIdentifier("textures/guns/vector_texture.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.57f, -0.2f)
				.setBaseScale(1.1f).setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_rifle, 0, 0.10f, -0.72f, 0.60f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.05f, 2.0f).setTransformTranslations(new float[][]{
					{0f,-0.17f,0.0f}, //First Person
					{0f,-0.2f,-0.04f}, //Third Person
					{-0.08f,-0.09f,0f}, //GUI
					{0f,0f,0f}, //Ground
					{0.05f,-0.17f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.0f, -0.53f).setMuzzleFlashJitter(0.02f, 0.02f, 5.0f, 0.1f));
	
		TGRenderRegistries.registerItemRenderer(TGuns.GRIM_REAPER,new RenderGunBase90(new ModelGrimReaper(),1, new TGIdentifier("textures/guns/grimreaper.png")).setBaseTranslation(0.3f, -0.2f, RenderItemBase.SCALE*0.5f)
				.setGUIScale(0.3f).setChargeTranslationAmount(0.025f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.39f, -0.61f, 0.75f,0).setTransformTranslations(new float[][]{
					{0f,0.25f,0.18f}, //First Person
					{0,0.13f,0.1f}, //Third Person
					{-0.02f,0.01f,0.0f}, //GUI
					{0.0f,0.1f,0}, //Ground
					{0,0,0f} //frame
				}).setMuzzleFXPos3P(0.24f, -0.56f).setBaseScale(1.25f).setFirstPersonScale(0.4f).setGroundAndFrameScale(0.35f));

		/*TGRenderRegistries.registerItemRenderer(TGuns.GAUSS_RIFLE,new RenderGunBaseObj(
				new ModelGaussrifle(),1, new TGIdentifier("textures/guns/gaussrifle.png"), -90f)
				.setBaseTranslation(0.6f, 0f, RenderItemBase.SCALE*0.5f-0.09f)
				.setBaseScale(0.9f).setGUIScale(0.25f).setMuzzleFx(ScreenEffect.muzzleFlashSonic, 0, 0.21f, -1.56f, 1.0f,0).setTransformTranslations(new float[][]{
						{0f,0.12f,-0.1f}, //First Person
						{0f,0.05f,-0.17f}, //Third Person
						{0.f,0.06f,0.06f}, //GUI
						{0f,0f,0.f}, //Ground
						{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.09f, -1.26f).setRecoilAnim(GunAnimation.genericRecoil, 0.25f, 7.5f).setScope(ScreenEffect.techScope,2.125f).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.15f, 1.0f));
*/
		TGRenderRegistries.registerItemRenderer(TGuns.GRENADE_LAUNCHER,new RenderGunBaseObj(new ModelGrenadeLauncher(),2,
				new TGIdentifier("textures/guns/grenadelauncher.png"),90.0f)
				.setBaseTranslation(0f, 0f, 0f)
				.setBaseScale(0.125f).setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_gun, 0, 0.22f, -0.63f, 0.5f,0).setTransformTranslations(new float[][]{
						{0f,0.19f,-0.09f}, //First Person
						{0f,0.06f,-0.20f}, //Third Person
						{-0.05f,0.08f,0f}, //GUI
						{0f,0.05f,-0.09f}, //Ground
						{0.11f,0.01f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.07f, -0.61f));

		TGRenderRegistries.registerItemRenderer(TGuns.FLAMETHROWER,new RenderFlamethrower(new ModelFlamethrower(),1, new TGIdentifier("textures/guns/flamethrower.png")).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE-0.1f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.FlamethrowerMuzzleFlash, 0, 0.16f, -0.9f, 0.45f,0).setTransformTranslations(new float[][]{
						{0f,0f,0f}, //First Person
						{0f,0f,-0.08f}, //Third Person
						{0.05f,0f,0f}, //GUI
						{0f,0f,0f}, //Ground
						{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.1f, -0.63f).setRecoilAnim(GunAnimation.swayRecoil, 0.025f, 2.5f));

		TGRenderRegistries.registerItemRenderer(TGuns.MIBGUN,new RenderGunBase(new ModelMibGun(),1, new TGIdentifier("textures/guns/mibgun.png")).setBaseTranslation(/*RenderItemBase.SCALE*0.5f*/0f, -0.56f, -0.02f)
				.setBaseScale(1.2f).setGUIScale(0.75f).setMuzzleFx(ScreenEffect.muzzleFlashMibGun, 0f, 0.26f, -0.42f, 0.55f,0f).setTransformTranslations(new float[][]{
						{0,0.10f,-0.02f}, //First Person
						{0.0f,-0.04f,-0.01f}, //Third Person
						{0.02f,-0.08f,0}, //GUI
						{0.02f,-0.08f,0}, //Ground
						{-0.04f,-0.09f,0f} //frame
				}).setRecoilAnim(GunAnimation.genericRecoil, 0.025f, 10.0f).setMuzzleFXPos3P(0.06f, -0.31f).setReloadAnim(GunAnimation.breechReload, -0.15f, 55.0f).setReloadAnim3p(GunAnimation.breechReload, 0f, 55.0f));

		TGRenderRegistries.registerItemRenderer(TGuns.ALIENBLASTER,new RenderGunBase(new ModelAlienBlaster(),1, new TGIdentifier("textures/guns/alien_blaster.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.3f, -0.2f)
				.setBaseScale(0.75f).setGUIScale(0.75f).setMuzzleFx(ScreenEffect.muzzleFlashAlienBlaster, 0f, 0.28f, -0.52f, 0.55f,0f).setTransformTranslations(new float[][]{
						{0,0.06f,-0.02f}, //First Person
						{0.0f,-0.07f,-0.01f}, //Third Person
						{0.02f,-0.08f,0}, //GUI
						{0.02f,-0.08f,0}, //Ground
						{-0.04f,-0.09f,0f} //frame
				}).setRecoilAnim(GunAnimation.genericRecoil, 0.025f, 10.0f).setMuzzleFXPos3P(0.07f, -0.36f).setReloadAnim(GunAnimation.breechReload, -0.15f, 55.0f).setReloadAnim3p(GunAnimation.breechReload, 0f, 55.0f));

		TGRenderRegistries.registerItemRenderer(TGuns.NETHERBLASTER,new RenderGunBase(new ModelNetherBlaster(),1, new TGIdentifier("textures/guns/cyberdemonblaster.png")).setBaseTranslation(0f, -0.2f, RenderItemBase.SCALE-0.09f)
				.setGUIScale(0.60f).setMuzzleFx(ScreenEffect.muzzleFlashFireball_alpha, 0, 0.29f, -0.33f, 0.5f,0).setTransformTranslations(new float[][]{
						{0f,0.15f,0.04f}, //First Person
						{0f,-0.16f,-0.24f}, //Third Person
						{-0.10f,0.01f,0f}, //GUI
						{0f,0f,-0.11f}, //Ground
						{0.16f,-0.07f,-0.16f} //frame
				}).setMuzzleFXPos3P(-0.06f, -0.45f));

		TGRenderRegistries.registerItemRenderer(TGuns.BLASTERRIFLE,new RenderGunBase(new ModelBlasterRifle(),1, new TGIdentifier("textures/guns/blasterrifle.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0)
				.setBaseScale(0.9f).setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlashLaser, 0, 0.24f, -0.93f, 0.75f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
						{0f,0.11f,-0.20f}, //First Person
						{0f,0.0f,-0.04f}, //Third Person
						{0f,0f,0.03f}, //GUI
						{0f,0f,0f}, //Ground
						{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.09f, -0.57f).setScope(ScreenEffect.sniperScope).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.05f, 1.0f));

		TGRenderRegistries.registerItemRenderer(TGuns.BLASTERSHOTGUN,new RenderGunBase(new ModelLasergun2(),1, new TGIdentifier("textures/guns/lasergun.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0.1f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlashLaser, 0, 0.22f, -1.09f, 0.75f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
						{0f,0.04f,-0.05f}, //First Person
						{0f,0.01f,-0.1f}, //Third Person
						{0f,0f,0f}, //GUI
						{0f,0f,0f}, //Ground
						{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.14f, -0.82f));

		TGRenderRegistries.registerItemRenderer(TGuns.BLASTERSHOTGUN,new RenderGunBase(new ModelLasergun2(),1, new TGIdentifier("textures/guns/lasergunnew.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.1f, 0.1f)
				.setGUIScale(0.35f).setMuzzleFx(ScreenEffect.muzzleFlashLaser, 0, 0.22f, -1.09f, 0.75f,0).setRecoilAnim(GunAnimation.genericRecoil, 0.1f, 4.0f).setTransformTranslations(new float[][]{
						{0f,0.04f,-0.05f}, //First Person
						{0f,0.01f,-0.1f}, //Third Person
						{0f,0f,0f}, //GUI
						{0f,0f,0f}, //Ground
						{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.14f, -0.82f));

		TGRenderRegistries.registerItemRenderer(TGuns.GAUSS_RIFLE,new RenderGunBase90(new ModelGaussRifle(), 1, new TGIdentifier("textures/guns/gaussrifle.png"))
				.setBaseTranslation(-0.6f, 0f, RenderItemBase.SCALE*0.5f-0.09f)
				.setBaseScale(0.9f).setGUIScale(0.25f).setMuzzleFx(ScreenEffect.muzzleFlashSonic, 0, 0.21f, -1.56f, 1.0f,0).setTransformTranslations(new float[][]{
						{0f,0.12f,-0.1f}, //First Person
						{0f,0.05f,-0.17f}, //Third Person
						{0.f,0.06f,0.06f}, //GUI
						{0f,0f,0.f}, //Ground
						{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.09f, -1.26f).setRecoilAnim(GunAnimation.genericRecoil, 0.25f, 7.5f).setScope(ScreenEffect.techScope,2.125f).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.15f, 1.0f));

		TGRenderRegistries.registerItemRenderer(TGuns.PDW,new RenderGunBase90(new ModelPDW(),1, new TGIdentifier("textures/guns/pdw.png")).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE*1.5f-0.1f)
				.setGUIScale(0.55f).setMuzzleFx(ScreenEffect.muzzleFlash_blue, 0, 0.13f, -0.58f, 0.55f ,0).setTransformTranslations(new float[][]{
						{0.0f, 0.09f, -0.04f}, //First Person
						{0f, 0.06f, -0.02f}, //Third Person
						{0f, 0f, 0f}, //GUI
						{0f, 0f, 0f}, //Ground
						{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.11f, -0.41f).setMuzzleFlashJitter(0.01f, 0.01f, 5.0f, 0.1f).setRecoilAnim(GunAnimation.genericRecoil,  0.06f, 4.0f));

		TGRenderRegistries.registerItemRenderer(TGuns.PULSERIFLE,new RenderGunBase90(new ModelPulseRifle(),1, new TGIdentifier("textures/guns/pulserifle.png")).setBaseTranslation(0, -0.2f, RenderItemBase.SCALE*1.5f - 0.09f)
				.setGUIScale(0.45f).setMuzzleFx(ScreenEffect.muzzleFlash_blue, 0, 0.22f, -0.76f, 0.6f,0).setTransformTranslations(new float[][]{
						{0f,0.16f,0.01f}, //First Person
						{0f,0.05f,0.08f}, //Third Person
						{0.05f,0f,0f}, //GUI
						{0f,0f,0f}, //Ground
						{0f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.1f, -0.50f).setScope(ScreenEffect.techScope,2.125f).setScopeRecoilAnim(GunAnimation.scopeRecoil, 0.10f, 1.5f).setRecoilAnim(GunAnimation.pulseRifleRecoil, 0.25f, 10.0f));

		TGRenderRegistries.registerItemRenderer(TGuns.POWERHAMMER,new RenderGunBase90(new ModelPowerHammer(),2, new TGIdentifier("textures/guns/powerhammer.png")).setBaseTranslation(0.15f, -0.2f, RenderItemBase.SCALE-0.09f)
				.setBaseScale(1.25f).setGUIScale(0.45f).setMuzzleFx(null, 0, 0.26f, -0.67f, 0.5f,0).setTransformTranslations(new float[][]{
						{0f,0.18f,0.09f}, //First Person
						{0f,0.04f,0.04f}, //Third Person
						{0.03f,0.01f,0f}, //GUI
						{0f,0f,0f}, //Ground
						{-0.07f,-0.03f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.12f, -0.65f).setChargeTranslationAmount(0.125f));

		TGRenderRegistries.registerItemRenderer(TGuns.MININGDRILL,new RenderGunBase90(new ModelMiningDrill(),2, new TGIdentifier("textures/guns/miningdrill_obsidian.png")).setBaseTranslation(0, -0.2f, -RenderItemBase.SCALE*0.5f).setBaseScale(2.0f)
				.setGUIScale(0.35f).setTransformTranslations(new float[][]{
						{0f,-0.03f,0.0f}, //First Person
						{0f,-0.57f,0.08f}, //Third Person
						{0.01f,-0.01f,0f}, //GUI
						{0f,0f,0f}, //Ground
						{0f,-0.08f,-0.05f} //frame
				}).setRecoilAnim(GunAnimation.genericRecoil, 0.05f, 1.0f));

		TGRenderRegistries.registerItemRenderer(TGuns.LASERPISTOL,new RenderGunBase(new ModelLaserPistol(),1, new TGIdentifier("textures/guns/laser_pistol.png")).setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.3f, -0.4f)
				.setBaseScale(1.2f).setGUIScale(0.7f).setMuzzleFx(ScreenEffect.muzzleFlashLaser, 0.03f, 0.2f, -0.5f, 0.55f,-0.03f).setTransformTranslations(new float[][]{
						{0,0.09f,-0.02f}, //First Person
						{0.0f,-0.03f,0.0f}, //Third Person
						{0.02f,-0.08f,0}, //GUI
						{0.02f,-0.08f,0}, //Ground
						{0,0,0f} //frame
				}).setRecoilAnim(GunAnimation.genericRecoil, 0.0125f, 6.0f).setMuzzleFXPos3P(0.07f, -0.26f));

		TGRenderRegistries.registerItemRenderer(TGuns.LASERGUN,new RenderGunBase90(new ModelLasergun(),1, new TGIdentifier("textures/guns/lasergun.png")).setBaseTranslation(0.25f, -0.2f, RenderItemBase.SCALE*0.5f-0.10f)
				.setBaseScale(1.1f).setGUIScale(0.40f).setMuzzleFx(ScreenEffect.muzzleFlashLaser, 0, 0.30f, -1.06f, 0.5f,0).setTransformTranslations(new float[][]{
						{0f,0.15f,0.04f}, //First Person
						{0f,0.02f,0.01f}, //Third Person
						{0.13f,0.01f,0f}, //GUI
						{0f,0f,0.15f}, //Ground
						{-0.18f,0f,-0.05f} //frame
				}).setMuzzleFXPos3P(0.11f, -0.83f).setRecoilAnim(GunAnimation.genericRecoil, 0.2f, 5.0f));


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
		
		EntityRendererRegistry.INSTANCE.register(TGEntities.CHAINSAW_PROJECTILE,  (dispatcher, context) -> {
			return new RenderInvisibleProjectile(dispatcher);
		});

		EntityRendererRegistry.INSTANCE.register(TGEntities.GENERIC_BEAM_PROJECTILE,  (dispatcher, context) -> {
			return new RenderGenericBeamProjectile(dispatcher);
		});

		EntityRendererRegistry.INSTANCE.register(TGEntities.GRENADE_PROJECTILE, (dispatcher, context) -> {
			return new RenderGrenadeProjectile(dispatcher);
		});

		EntityRendererRegistry.INSTANCE.register(TGEntities.FLAMETHROWER_PROJECTILE, (dispatcher, context) -> {
			return new RenderInvisibleProjectile(dispatcher);
		});

		EntityRendererRegistry.INSTANCE.register(TGEntities.GENERIC_FX_PROJECTILE, (dispatcher, context) -> {
			return new RenderInvisibleProjectile(dispatcher);
		});
		
		EntityRendererRegistry.INSTANCE.register(TGEntities.FLYING_GIBS, (dispatcher, context) -> {
			return new RenderFlyingGibs(dispatcher);
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
	public void createFX(String name, World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float pitch, float yaw, float scale){
		List<TGParticleSystem> systems = TGFX.createFX(world, name, posX, posY, posZ, motionX, motionY, motionZ);
		if (systems!=null) {
			for (TGParticleSystem s : systems) {
				s.rotationPitch = pitch;
				s.rotationYaw = yaw;
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
