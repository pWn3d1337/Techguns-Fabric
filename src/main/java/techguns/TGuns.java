package techguns;

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;
import techguns.api.guns.GunHandType;
import techguns.entities.projectiles.BioGunProjectile;
import techguns.entities.projectiles.ChainsawProjectile;
import techguns.entities.projectiles.GenericBeamProjectile;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.GuidedMissileProjectile;
import techguns.entities.projectiles.RocketProjectile;
import techguns.entities.projectiles.TFGProjectile;
import techguns.entities.projectiles.StoneBulletProjectile;
import techguns.entities.projectiles.*;
import techguns.items.guns.*;
import techguns.items.guns.GenericGun.HoldType;
import techguns.items.guns.ammo.AmmoTypes;

public class TGuns implements ITGInitializer {

	private static final float RANGE_MELEE=3.0f;
	private static final float RANGE_CLOSE=12.0f;
	private static final float RANGE_SHORT=18.0f;
	private static final float RANGE_MEDIUM=24.0f;
	private static final float RANGE_FAR=36.0f;
	
	private static final float PENETRATION_LOW=0.5f;
	private static final float PENETRATION_MED=1f;
	private static final float PENETRATION_MED_HIGH=2f;
	
	private static final int MAX_RANGE_PISTOL=40;
	private static final int MAX_RANGE_RIFLE=60;
	private static final int MAX_RANGE_RIFLE_LONG=75;
	private static final int MAX_RANGE_SNIPER=90;
	
	public static GenericGun AK47;
	public static GenericGun M4;
	public static GenericGun SCAR;
	public static GenericGun BIOGUN;
	public static GenericGun GUIDED_MISSLE_LAUNCHER;
	public static GenericGun ROCKET_LAUNCHER;
	public static GenericGun TFG;
	public static GenericGun HANDCANNON;
	public static GenericGun AS50;
	public static GenericGun AUG;
	public static GenericGun MINIGUN;
	public static GenericGun CHAINSAW;
	public static GenericGun NUCLEAR_DEATHRAY;
	public static GenericGun PISTOL;
	public static GenericGun SAWEDOFF;
	public static GenericGun COMBAT_SHOTGUN;
	public static GenericGun THOMPSON;
	public static GenericGun LMG;
	public static GenericGun BOLTACTION;
	public static GenericGun REVOLVER;
	public static GenericGun GOLDEN_REVOLVER;
	public static GenericGun MAC10;
	public static GenericGun VECTOR;
	public static GenericGun M4_INFILTRATOR;
	public static GenericGun GRIM_REAPER;
	public static GenericGun GAUSS_RIFLE;
	public static GenericGun GRENADE_LAUNCHER;
	public static GenericGun FLAMETHROWER;
	public static GenericGun ALIENBLASTER;
	public static GenericGun MIBGUN;
	public static GenericGun NETHERBLASTER;
	public static GenericGun BLASTERRIFLE;
	public static GenericGun BLASTERSHOTGUN;
	public static GenericGun PULSERIFLE;
	public static GenericGun PDW;
	public static GenericGun POWERHAMMER;
	public static GenericGun MININGDRILL;
	public static GenericGun LASERGUN;
	public static GenericGun LASERPISTOL;
	public static GenericGun SONICSHOTGUN;
	public static GenericGun TESLAGUN;
	public static GenericGun GRAPPLING_HOOK;

	public static ProjectileSelector<GenericProjectile> ASSAULTRIFLE_MAG_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> SNIPER_MAG_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> MINIGUN_MAG_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> PISTOL_MAG_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> SHOTGUN_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> SMG_MAG_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> LMG_MAG_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> RIFLE_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> PISTOL_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> GAUSS_PROJECTILES;
	public static ProjectileSelector<GrenadeProjectile> GRENADE40MM_PROJECTILES;
	
	public static ProjectileSelector<RocketProjectile> ROCKET_PROJECTILES;
	
	public static ProjectileSelector<GenericBeamProjectile> NDR_PROJECTILES;
	public static ProjectileSelector<GenericBeamProjectile> LASERGUN_PROJECTILES;
	public static ProjectileSelector<GenericBeamProjectile> LASERPISTOL_PROJECTILES;
	public static ProjectileSelector<FlamethrowerProjectile> FLAMETHROWER_PROJECTILES;
	public static ProjectileSelector<GenericProjectileFX> ALIENBLASTER_PROJECTILES;
	public static ProjectileSelector<GenericProjectileFX> DEATOMIZER_PROJECTILES;
	public static ProjectileSelector<GenericProjectileFX> NETHERBLASTER_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> BLASTER_ENERGYCELL_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> ADVANCED_MAG_PROJECTILES;
	public static ProjectileSelector<SonicShotgunProjectile> SONIC_SHOTGUN_PROJECTILES;

	public static ChargedProjectileSelector<GuidedMissileProjectile> GUIDED_MISSILE_PROJECTILES;
	public static ChargedProjectileSelector<BioGunProjectile> BIOGUN_PROJECTILES;
	public static ChargedProjectileSelector<TFGProjectile> TFG_PROJECTILES;
	
	public static ChargedProjectileSelector<ChainsawProjectile> CHAINSAW_PROJECTILES;
	public static ChargedProjectileSelector<ChainsawProjectile> POWERHAMMER_PROJECTILES;
	
	public static ChargedProjectileSelector<GrapplingHookProjectile> GRAPPLING_HOOK_PROJECTILES;
	public static ProjectileSelector<TeslaProjectile> TESLAGUN_PROJECTILES;

	@SuppressWarnings({ "rawtypes", "unchecked" }) //java does not like generics in arrays so there are these warnings
	@Override
	public void init() {
		IProjectileFactory<GenericProjectile> GENERIC_PROJECTILE = new GenericProjectile.Factory();
		IProjectileFactory<GenericProjectile> GENERIC_PROJECTILE_INCENDIARY = new GenericProjectile.Factory(GenericProjectile.GenericProjectileType.INCENDIARY);
		IProjectileFactory[] GENERIC_BULLET = {GENERIC_PROJECTILE, GENERIC_PROJECTILE_INCENDIARY};
		
		ASSAULTRIFLE_MAG_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.ASSAULT_RIFLE_MAGAZINE, GENERIC_BULLET);
		GUIDED_MISSILE_PROJECTILES = new ChargedProjectileSelector(AmmoTypes.ROCKETS_NO_NUKES, new GuidedMissileProjectile.Factory(), new GuidedMissileProjectile.Factory()); //new GuidedMissileProjectileHV.Factory());//TODO HV missiles
		
		ROCKET_PROJECTILES = new ProjectileSelector(AmmoTypes.ROCKETS, new RocketProjectile.Factory(), new RocketProjectile.Factory(), new RocketProjectile.Factory());//, new RocketProjectileNuke.Factory(), new RocketProjectileHV.Factory()); //TODO ammo types
		
		BIOGUN_PROJECTILES = new ChargedProjectileSelector<BioGunProjectile>(AmmoTypes.BIO_TANK, new BioGunProjectile.Factory());
		
		TFG_PROJECTILES = new ChargedProjectileSelector<TFGProjectile>(AmmoTypes.NUCLEAR_POWER_CELL, new TFGProjectile.Factory());
		SNIPER_MAG_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.AS50_MAGAZINE, new IProjectileFactory[]{GENERIC_PROJECTILE, GENERIC_PROJECTILE_INCENDIARY, new GenericProjectile.Factory(GenericProjectile.GenericProjectileType.EXPLOSIVE)});
		
		PISTOL_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.PISTOL_ROUNDS, GENERIC_BULLET);
		PISTOL_MAG_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.PISTOL_MAGAZINE, GENERIC_BULLET);
		
		SMG_MAG_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.SMG_MAGAZINE, GENERIC_BULLET);

		LMG_MAG_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.LMG_MAGAZINE, GENERIC_BULLET);
	
		RIFLE_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.RIFLE_ROUNDS, GENERIC_BULLET);
		
		
		MINIGUN_MAG_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.MINIGUN_AMMO_DRUM, GENERIC_BULLET);
		
		CHAINSAW_PROJECTILES = new ChargedProjectileSelector<ChainsawProjectile>(AmmoTypes.FUEL_TANK, new ChainsawProjectile.Factory(ChainsawProjectile.PROJECTILE_TYPE_CHAINSAW));
		
		SHOTGUN_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.SHOTGUN_ROUNDS, GENERIC_PROJECTILE, GENERIC_PROJECTILE_INCENDIARY);//TODO new GenericProjectileIncendiary.Factory(true));
		
		NDR_PROJECTILES = new ProjectileSelector<GenericBeamProjectile>(AmmoTypes.NUCLEAR_POWER_CELL, new GenericBeamProjectile.Factory(15, true, GenericBeamProjectile.BEAM_TYPE_NDR, "BeamGunImpactFX"));

		FLAMETHROWER_PROJECTILES = new ProjectileSelector<FlamethrowerProjectile>(AmmoTypes.FUEL_TANK, new FlamethrowerProjectile.Factory());

		ALIENBLASTER_PROJECTILES =  new ProjectileSelector<GenericProjectileFX>(AmmoTypes.ENERGY_CELL, new GenericProjectileFX.Factory(GenericProjectileFX.PROJECTILE_TYPE_ALIENBLASTER));
		DEATOMIZER_PROJECTILES = new ProjectileSelector<GenericProjectileFX>(AmmoTypes.ENERGY_CELL, new GenericProjectileFX.Factory(GenericProjectileFX.PROJECTILE_TYPE_DEATOMIZER));

		NETHERBLASTER_PROJECTILES = new ProjectileSelector<GenericProjectileFX>(AmmoTypes.NETHER_CHARGE, new GenericProjectileFX.Factory(GenericProjectileFX.PROJECTILE_TYPE_NETHERBLASTER));

		BLASTER_ENERGYCELL_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.ENERGY_CELL, new GenericProjectile.Factory(GenericProjectile.GenericProjectileType.BLASTER));

		ADVANCED_MAG_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.ADVANCED_MAGAZINE, new GenericProjectile.Factory(GenericProjectile.GenericProjectileType.ADVANCED));

		GAUSS_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.AMMO_GAUSS_RIFLE, new GenericProjectile.Factory(GenericProjectile.GenericProjectileType.GAUSS));

		GRENADE40MM_PROJECTILES = new ProjectileSelector<GrenadeProjectile>(AmmoTypes.GRENADES_40MM, new GrenadeProjectile.Factory(0,3));

		POWERHAMMER_PROJECTILES = new ChargedProjectileSelector<ChainsawProjectile>(AmmoTypes.COMPRESSED_AIR_TANK, new ChainsawProjectile.Factory(ChainsawProjectile.PROJECTILE_TYPE_POWERHAMMER));

		GenericBeamProjectile.Factory LASER_BEAM = new GenericBeamProjectile.Factory(1, true, GenericBeamProjectile.BEAM_TYPE_LASER, "LaserGunImpact");
		LASERGUN_PROJECTILES = new ProjectileSelector<GenericBeamProjectile>(AmmoTypes.ENERGY_CELL, LASER_BEAM);
		LASERPISTOL_PROJECTILES = new ProjectileSelector<GenericBeamProjectile>(AmmoTypes.REDSTONE_BATTERY, LASER_BEAM);

		SONIC_SHOTGUN_PROJECTILES = new ProjectileSelector<SonicShotgunProjectile>(AmmoTypes.ENERGY_CELL, new SonicShotgunProjectile.Factory());
		
		GRAPPLING_HOOK_PROJECTILES = new ChargedProjectileSelector<GrapplingHookProjectile>(AmmoTypes.COMPRESSED_AIR_TANK, new GrapplingHookProjectile.Factory());

		TESLAGUN_PROJECTILES = new ProjectileSelector<TeslaProjectile>(AmmoTypes.ENERGY_CELL, new TeslaProjectile.Factory());



		HANDCANNON = reg(new GenericGun("handcannon", new ProjectileSelector<StoneBulletProjectile>(AmmoTypes.STONE_BULLETS, new StoneBulletProjectile.Factory()), true, 12,1,30, 8.0f, TGSounds.HANDGUN_FIRE, TGSounds.HANDGUN_RELOAD,25,0.035f).setBulletSpeed(1.0f).setGravity(0.015d).setDamageDrop(10, 25, 5.0f).setAIStats(RANGE_CLOSE, 60, 0, 0).setRecoiltime(12).setCrossHair(EnumCrosshairStyle.QUAD_NO_CORNERS));
		
		SAWEDOFF = reg(new GenericGun("sawedoff",SHOTGUN_PROJECTILES, true, 4, 2, 28, 4.0f, TGSounds.SAWEDOFF_FIRE, TGSounds.SAWEDOFF_RELOAD,10, 0.01f).setAmmoCount(2).setShotgunSpread(7,0.2f,false).setDamageDrop(1, 4, 1.5f).setAIStats(RANGE_CLOSE, 60, 2, 20).setBulletSpeed(1.5f).setCrossHair(EnumCrosshairStyle.FOUR_PARTS));
	 	
		REVOLVER = reg(new GenericGun("revolver",PISTOL_PROJECTILES, true, 6, 6,45,8.0f, TGSounds.REVOLVER_FIRE, TGSounds.REVOLVER_RELOAD,MAX_RANGE_PISTOL,0.025f).setDamageDrop(12, 20, 6.0f).setBulletSpeed(2f).setRecoiltime(6).setAIStats(RANGE_SHORT, 90, 6, 20).setHandType(GunHandType.ONE_HANDED));
	 	 		
		GOLDEN_REVOLVER = reg(new GenericGun("goldenrevolver",PISTOL_PROJECTILES, true, 8, 6,45,14.0f, TGSounds.REVOLVER_GOLDEN_FIRE, TGSounds.REVOLVER_RELOAD,MAX_RANGE_PISTOL,0.015f).setDamageDrop(12, 30, 6.0f).setRecoiltime(8).setBulletSpeed(1.75f).setAIStats(RANGE_MEDIUM, 60, 6, 15).setHandType(GunHandType.ONE_HANDED));
		
		M4 = reg(new GenericGun("m4", ASSAULTRIFLE_MAG_PROJECTILES, false, 3, 30, 45, 8.0f, TGSounds.M4_FIRE, TGSounds.M4_RELOAD, MAX_RANGE_RIFLE, 0.05f).setBulletSpeed(4.0f).setZoom(0.75f, true,0.75f,false).setDamageDrop(25, 40, 6.0f).setAIStats(RANGE_MEDIUM, 30, 3, 3).setTurretPosOffset(0, 0, 0.08f).setPenetration(PENETRATION_LOW).setMuzzleFlashTime(4));
	
		M4_INFILTRATOR = reg(new GenericGun("m4_infiltrator", ASSAULTRIFLE_MAG_PROJECTILES, false, 3, 30,45,8.0f, TGSounds.M4_SILENCED_FIRE, TGSounds.M4_RELOAD, MAX_RANGE_RIFLE, 0.010f).setZoom(0.50f, true,0.5f,true).setDamageDrop(25, 35, 6.0f).setBulletSpeed(2.0f).setSilenced(true).setAIStats(RANGE_MEDIUM, 30, 3, 3).setPenetration(PENETRATION_LOW).setTurretPosOffset(0, 0, 0.08f).setNoMuzzleLight());
		
		AK47 = reg(new GenericGun("ak47", ASSAULTRIFLE_MAG_PROJECTILES, false, 3, 30,45,9.0f, TGSounds.AK_FIRE, TGSounds.AK_RELOAD, MAX_RANGE_RIFLE, 0.030f).setDamageDrop(20, 30, 5.0f).setPenetration(PENETRATION_LOW).setBulletSpeed(3.25f).setAIStats(RANGE_MEDIUM, 30, 3, 3).setMuzzleFlashTime(4).setTurretPosOffset(0, 0, 0.08f));
		  		
		AUG = reg(new GenericGun("aug", ASSAULTRIFLE_MAG_PROJECTILES, false, 3, 30,45,8.0f, TGSounds.AUG_FIRE, TGSounds.AUG_RELOAD, MAX_RANGE_RIFLE, 0.010f).setBulletSpeed(4.0f).setZoom(0.50f, true,0.5f,true).setDamageDrop(30, 45, 7.0f).setAIStats(RANGE_MEDIUM, 30, 3, 3).setPenetration(PENETRATION_LOW).setMuzzleFlashTime(4));
		 
		SCAR = reg(new GenericGun("scar", ASSAULTRIFLE_MAG_PROJECTILES, false, 4, 20,45,12.0f, TGSounds.SCAR_FIRE, TGSounds.SCAR_RELOAD, MAX_RANGE_RIFLE_LONG, 0.015f).setZoom(0.65f, true,0.5f,true).setDamageDrop(35, 60, 10.0f).setAIStats(RANGE_MEDIUM, 30, 5, 2).setPenetration(PENETRATION_MED).setBulletSpeed(4.5f).setMuzzleFlashTime(5).setTurretPosOffset(0, 0.02f, 0.09f));	
		
		PISTOL = reg(new GenericGun("pistol",PISTOL_MAG_PROJECTILES, true, 4, 18, 35, 8.0f, TGSounds.PISTOL_FIRE, TGSounds.PISTOL_RELOAD,MAX_RANGE_PISTOL, 0.025f).setBulletSpeed(2.5f).setDamageDrop(15, 22, 6.0f).setDamageDrop(18, 25, 5.0f).setAIStats(RANGE_MEDIUM, 30, 3, 10).setHandType(GunHandType.ONE_HANDED).setRecoiltime(3).setCrossHair(EnumCrosshairStyle.GUN_DYNAMIC));
		
		THOMPSON = reg(new GenericGun("thompson",SMG_MAG_PROJECTILES, false, 3, 20,40,5.0f, TGSounds.THOMPSON_FIRE, TGSounds.THOMSPON_RELOAD,MAX_RANGE_PISTOL,0.05f).setBulletSpeed(2.25f).setDamageDrop(15, 24, 3.0f).setAIStats(RANGE_SHORT, 45, 3, 3).setMuzzleFlashTime(4).setTurretPosOffset(0, 0, 0.04f));
	
		LMG = reg(new GenericGun("lmg",LMG_MAG_PROJECTILES,false, 2, 100,100,8.0f, TGSounds.LMG_FIRE, TGSounds.LMG_RELOAD, MAX_RANGE_RIFLE_LONG, 0.020f).setZoom(0.75f, true,0.75f,false).setDamageDrop(40, 60, 6.0f).setPenetration(PENETRATION_LOW).setBulletSpeed(4.0f).setAIStats(RANGE_MEDIUM, 40, 6, 3).setMuzzleFlashTime(2).setRecoiltime(3));
		
		BOLTACTION = reg(new GenericGun("boltaction", RIFLE_PROJECTILES, true, 25, 6, 50, 16.0f, TGSounds.BOLT_ACTION_FIRE, TGSounds.BOLT_ACTION_RELOAD,MAX_RANGE_SNIPER,0.05f).setZoom(0.35f, true,0.125f,true).setBulletSpeed(4.5f).setRecoiltime(15).setRechamberSound(TGSounds.BOLT_ACTION_RECHAMBER).setDamageDrop(40, 60, 10.0f).setPenetration(PENETRATION_LOW).setAIStats(RANGE_FAR, 60, 0, 0).setTurretPosOffset(0, 0, 0.14f));
			
		MAC10 = reg(new GenericGun("mac10",SMG_MAG_PROJECTILES, false, 2, 32,40,5.0f, TGSounds.MAC10_FIRE, TGSounds.M4_RELOAD,MAX_RANGE_PISTOL,0.05f).setDamageDrop(15, 24, 3.0f).setAIStats(RANGE_SHORT, 35, 3, 2).setRecoiltime(2).setMuzzleFlashTime(3).setBulletSpeed(1.75f).setHandType(GunHandType.ONE_POINT_FIVE_HANDED).setTurretPosOffset(0, 0, -0.07f));
		
		VECTOR = reg(new GenericGun("vector",SMG_MAG_PROJECTILES, false, 2, 25,40,6.0f, TGSounds.VECTOR_FIRE, TGSounds.VECTOR_RELOAD,MAX_RANGE_PISTOL,0.05f).setZoom(0.75f, true,0.35f,false).setDamageDrop(17, 25, 4.0f).setBulletSpeed(2.0f).setAIStats(RANGE_SHORT, 35, 3, 2).setRecoiltime(2).setMuzzleFlashTime(3).setPenetration(PENETRATION_LOW).setTurretPosOffset(0, -0.1f, 0.15f));
		
		COMBAT_SHOTGUN = reg(new GenericGun("combatshotgun",SHOTGUN_PROJECTILES, true, 14, 8, 50, 4.0f, TGSounds.COMBATSHOTGUN_FIRE, TGSounds.COMBATSHOTGUN_RELOAD,15, 0.01f).setAmmoCount(8).setShotgunSpread(7,0.15f,false).setRecoiltime(12).setBulletSpeed(1.5f).setRechamberSound(TGSounds.COMBATSHOTGUN_RECHAMBER).setDamageDrop(2, 5, 1.5f).setPenetration(PENETRATION_LOW).setAIStats(RANGE_CLOSE,30,0,0).setCrossHair(EnumCrosshairStyle.FOUR_PARTS));	
		
		BIOGUN = reg(new GenericGunCharge("biogun", BIOGUN_PROJECTILES, false, 6, 30,45,10.0f, TGSounds.BIOGUN_FIRE, TGSounds.BIOGUN_RELOAD, MAX_RANGE_PISTOL, 0.015f,30.0f,3).setChargeSound(TGSounds.BIOGUN_CHARGE).setChargeFX("biogunCharge",-0.12f, -0.07f, 0.27f).setBulletSpeed(0.75f).setGravity(0.01d).setPenetration(PENETRATION_LOW).setAIStats(RANGE_SHORT, 30, 0, 0).setDamageDrop(8, 15, 8.0f).setMuzzleLight(0.2f, 0.9f, 0.5f).setForwardOffset(0.40f).setCrossHair(EnumCrosshairStyle.QUAD_NO_CORNERS));
			
		AS50 = reg(new GenericGun("as50", SNIPER_MAG_PROJECTILES, true, 10, 10, 80, 32.0f, TGSounds.AS50_FIRE, TGSounds.AS50_RELOAD, MAX_RANGE_SNIPER,0.0625f).setDamageDrop(40, 60, 24.0f).setZoom(0.35f, true,0.125f,true).setBulletSpeed(4.5f).setRecoiltime(10).setPenetration(PENETRATION_MED_HIGH).setAIStats(RANGE_FAR, 30, 0, 0).setTurretPosOffset(0, 0.03f, 0.13f));
		 	
		MINIGUN = reg(new GenericGun("minigun",MINIGUN_MAG_PROJECTILES,false, 0, 200, 100, 5.0f, TGSounds.MINIGUN_FIRE, TGSounds.MINIGUN_RELOAD, MAX_RANGE_RIFLE_LONG, 0.025f).setDamageDrop(30, 50, 3.0f).setPenetration(PENETRATION_LOW).setBulletSpeed(4.5f).setAIStats(RANGE_MEDIUM, 40, 10, 1).setCheckRecoil().setMuzzleFlashTime(4).setCheckMuzzleFlash().setTurretPosOffset(0, -0.49f, -0.14f));
		
		GUIDED_MISSLE_LAUNCHER = reg(new GuidedMissileLauncher("guidedmissilelauncher", GUIDED_MISSILE_PROJECTILES, true, 10, 1 , 40, 50.0f, TGSounds.GUIDEDMISSILE_FIRE, TGSounds.ROCKET_RELOAD, 100, 0.05f, 200, 1).setFireWhileCharging(true).setChargeFireAnims(false).setLockOn(20, 80).setBulletSpeed(1.0f).setRecoiltime(10).setAIStats(RANGE_MEDIUM,80,0,0).setTurretPosOffset(0, 0.01f, -0.12f).setDamageDrop(2f, 4f, 20f).setRangeTooltipType(RangeTooltipType.RADIUS).setCrossHair(EnumCrosshairStyle.QUAD_CORNERS_DOT).setHoldType(HoldType.BOW));

		GRIM_REAPER = reg(new GuidedMissileLauncher("grimreaper", GUIDED_MISSILE_PROJECTILES, false, 6, 4 , 40, 50.0f, TGSounds.GUIDEDMISSILE_FIRE, TGSounds.ROCKET_RELOAD, 200, 0.05f,100, 1).setFireWhileCharging(true).setChargeFireAnims(false).setBulletSpeed(1.0f).setRecoiltime(10).setAmmoCount(4).setAIStats(RANGE_MEDIUM, 120, 4, 30).setLockOn(20, 80).setTurretPosOffset(0, 0.11f, -0.16f).setDamageDrop(3f, 5f, 10f).setRangeTooltipType(RangeTooltipType.RADIUS).setCrossHair(EnumCrosshairStyle.QUAD_CORNERS_DOT).setHoldType(HoldType.BOW));
		
		ROCKET_LAUNCHER = reg(new GenericGun("rocketlauncher", ROCKET_PROJECTILES, true, 10, 1 , 40, 50.0f, TGSounds.ROCKET_FIRE, TGSounds.ROCKET_RELOAD, 200, 0.05f).setGravity(0.01D).setBulletSpeed(1.0f).setRecoiltime(10).setAIStats(RANGE_MEDIUM,80,0,0).setTurretPosOffset(0, 0, -0.1f).setDamageDrop(2.0f, 4.0f, 10f).setRangeTooltipType(RangeTooltipType.RADIUS).setForwardOffset(0.35f).setCrossHair(EnumCrosshairStyle.QUAD_CORNERS_DOT).setHoldType(HoldType.BOW));
		 
		CHAINSAW = reg(new Chainsaw("chainsaw", CHAINSAW_PROJECTILES, false, 3, 300, 45, 10.0f, TGSounds.CHAINSAW_LOOP, TGSounds.POWERHAMMER_RELOAD, 2, 0.0f,1f,1, /**/12f, 2f, 0.5f, 14f).setToolLevel(FabricToolTags.AXES, 2).setRecoiltime(5).setShootWithLeftClick(false).setFiresoundStart(TGSounds.CHAINSAW_LOOP_START).setMaxLoopDelay(10).setPenetration(PENETRATION_MED).setAIStats(RANGE_MELEE, 10, 0, 0).setTurretPosOffset(0, -0.47f, -0.08f).setNoMuzzleLight().setCrossHair(EnumCrosshairStyle.VANILLA));
		
		TFG = reg(new GenericGunCharge("tfg", TFG_PROJECTILES, false, 5, 20,45,50.0f, TGSounds.TFG_FIRE, TGSounds.BIOGUN_RELOAD, 100, 0.015f, 60.0f ,10).setChargeSound(TGSounds.TFG_CHARGE).setChargeFX("TFGChargeStart",-0.14f, -0.10f, 0.42f).setBulletSpeed(2.0f).setRangeTooltipType(RangeTooltipType.RADIUS).setPenetration(PENETRATION_MED_HIGH).setAIStats(RANGE_SHORT, 30, 0, 0).setDamageDrop(8, 15, 15.0f).setMuzzleLight(0.2f, 1.0f, 0.2f).setMuzzleFlashTime(10).setRecoiltime(10).setForwardOffset(0.40f));
	
		//TODO Fix TTL/Speed
		NUCLEAR_DEATHRAY = reg(new GenericGun("nucleardeathray", NDR_PROJECTILES, false, 5, 40, 50, 6.0f, TGSounds.BEAMGUN_FIRE, TGSounds.LASERGUN_RELOAD, MAX_RANGE_SNIPER/* TODO? Beamgun.LIFETIME*/, 0.0f).setFiresoundStart(TGSounds.BEAMGUN_START).setMaxLoopDelay(10).setFireFX("BeamGunMuzzleFX", 0.16f, 0f, 0.8f).setRecoiltime(10).setCheckRecoil().setBulletSpeed(100.0f).setAIStats(RANGE_MEDIUM, 40, 5, 5).setPenetration(PENETRATION_MED).setDamageDrop(20, 40, 1.0f).setHandType(GunHandType.TWO_HANDED).setTurretPosOffset(0, 0.04f, -0.19f).setCrossHair(EnumCrosshairStyle.TRI));

		GAUSS_RIFLE = reg(new GenericGun("gaussrifle", GAUSS_PROJECTILES, true, 30, 8, 60, 40.0f, TGSounds.GAUSS_RIFLE_FIRE, TGSounds.GAUSS_RIFLE_RELOAD, MAX_RANGE_SNIPER, 0.025f).setZoom(0.35f, true,0.0f,true).setBulletSpeed(5.0f).setAIStats(RANGE_FAR, 30, 0, 0).setRechamberSound(TGSounds.GAUSS_RIFLE_RECHAMBER).setRecoiltime(8).setTurretPosOffset(0, -0.02f, 0.12f).setMuzzleLight(0f, 0.8f, 1.0f).setForwardOffset(0.45f).setPenetration(PENETRATION_MED_HIGH).setCrossHair(EnumCrosshairStyle.HORIZONTAL_TWO_PART_LARGE));

		GRENADE_LAUNCHER = reg(new GenericGun("grenadelauncher", GRENADE40MM_PROJECTILES, true, 5, 6, 100, 30.0f, TGSounds.GRENADE_LAUNCHER_FIRE, TGSounds.GRENADE_LAUNCHER_RELOAD, 160, 0.015f).setBulletSpeed(1.0f).setAIStats(RANGE_MEDIUM, 40, 3, 20).setAmmoCount(6).setDamageDrop(1.5f, 3.0f, 12f).setGravity(0.02d).setRangeTooltipType(RangeTooltipType.RADIUS).setCrossHair(EnumCrosshairStyle.QUAD_NO_CORNERS));

		FLAMETHROWER = reg(new GenericGun("flamethrower", FLAMETHROWER_PROJECTILES, false, 2, 100, 45, 5.0f, TGSounds.FLAMETHROWER_FIRE, TGSounds.FLAMETHROWER_RELOAD,16,0.05f).setBulletSpeed(0.5f).setGravity(0.01d).setFiresoundStart(TGSounds.FLAMETHROWER_START).setMaxLoopDelay(10).setDamageDrop(4, 16, 2.0f).setAIStats(RANGE_CLOSE, 20, 5, 5).setCheckRecoil().setRecoiltime(10).setCheckMuzzleFlash().setMuzzleFlashTime(10).setTurretPosOffset(0, 0, 0.1f).setForwardOffset(0.35f).setCrossHair(EnumCrosshairStyle.QUAD_NO_CORNERS));

		ALIENBLASTER = reg(new GenericGun("alienblaster", ALIENBLASTER_PROJECTILES, false, 8, 10, 35, 16.0f, TGSounds.ALIENBLASTER_FIRE, TGSounds.ALIENBLASTER_RELOAD, MAX_RANGE_PISTOL, 0.0f).setBulletSpeed(1.0f).setMuzzleFlashTime(10).setPenetration(PENETRATION_MED).setAIStats(RANGE_MEDIUM, 40, 0, 0).setHandType(GunHandType.ONE_HANDED).setTurretPosOffset(0, -0.03f, -0.04f).setMuzzleLight(0.925f, 0.415f, 1f).setCrossHair(EnumCrosshairStyle.FOUR_PARTS_SPIKED));

		MIBGUN = reg(new GenericGun("mibgun", DEATOMIZER_PROJECTILES, true, 8, 20, 45, 16.0f, TGSounds.MIBGUN_FIRE, TGSounds.MIBGUN_RELOAD, MAX_RANGE_PISTOL, 0.035f).setAIStats(RANGE_MEDIUM, 60, 0, 0).setDamageDrop(20, 30, 8.0f).setBulletSpeed(1.5f).setPenetration(PENETRATION_MED).setHandType(GunHandType.ONE_HANDED).setTurretPosOffset(0, -0.04f, 0f).setMuzzleLight(0.3333f, 0.9f, 1f).setCrossHair(EnumCrosshairStyle.HORIZONTAL_TWO_PART));

		NETHERBLASTER = reg(new GenericGun("netherblaster", NETHERBLASTER_PROJECTILES, false, 8, 10, 35, 14.0f, TGSounds.NETHERBLASTER_FIRE, TGSounds.NETHERBLASTER_RELOAD, MAX_RANGE_RIFLE, 0.0f).setBulletSpeed(1.5f).setMuzzleFlashTime(10).setPenetration(PENETRATION_LOW).setAIStats(RANGE_MEDIUM, 40, 0, 0).setDamageDrop(15, 30, 8.0f).setHandType(GunHandType.ONE_POINT_FIVE_HANDED).setTurretPosOffset(0, -0.16f, 0.12f).setMuzzleLight(0.9f, 0.8f, 0.1f).setCrossHair(EnumCrosshairStyle.FOUR_PARTS_SPIKED));

		BLASTERRIFLE = reg(new GenericGun("blasterrifle", BLASTER_ENERGYCELL_PROJECTILES, false, 5, 50, 45, 10.0f, TGSounds.BLASTER_RIFLE_FIRE, TGSounds.LASERGUN_RELOAD, MAX_RANGE_RIFLE, 0.025f).setZoom(0.5f, true,0.75f,true).setAIStats(RANGE_MEDIUM, 30, 5, 3).setDamageDrop(25, 35, 8.0f).setPenetration(PENETRATION_MED).setMuzzleLight(0.9f, 0.3f, 0.1f).setCrossHair(EnumCrosshairStyle.HORIZONTAL_TWO_PART));

		BLASTERSHOTGUN = reg(new GenericGun("blastershotgun", BLASTER_ENERGYCELL_PROJECTILES, false, 7, 40, 45, 6.0f, TGSounds.LASERGUN_FIRE, TGSounds.LASERGUN_RELOAD, 30, 0.1f).setShotgunSpread(4,0.15f,false).setBulletSpeed(1.5f).setZoom(0.75f, true,0.75f,false).setBulletSpeed(2.0f).setAIStats(RANGE_SHORT, 30, 0, 0).setMuzzleLight(0.9f, 0.3f, 0.1f).setCrossHair(EnumCrosshairStyle.FOUR_PARTS));

		PULSERIFLE = reg(new GenericGun("pulserifle",ADVANCED_MAG_PROJECTILES, false, 7, 12, 45, 10.0f,  TGSounds.PULSE_RIFLE_FIRE, TGSounds.PULSE_RIFEL_RELOAD, MAX_RANGE_RIFLE_LONG,0.024f).setDamageDrop(30, 45, 8f).setZoom(0.35f, true,0.5f,true).setBulletSpeed(3.25f).setRecoiltime(8).setPenetration(PENETRATION_MED).setShotgunSpread(2, 0.015f,true).setAIStats(RANGE_MEDIUM, 30, 0, 0).setMuzzleFlashTime(4).setTurretPosOffset(0, 0, -0.09f).setMuzzleLight(0f, 0.8f, 1.0f).setCrossHair(EnumCrosshairStyle.HORIZONTAL_TWO_PART));

		PDW = reg(new GenericGun("pdw", ADVANCED_MAG_PROJECTILES, false, 1, 40, 40, 5.0f, TGSounds.PDW_FIRE, TGSounds.PDW_RELOAD,MAX_RANGE_PISTOL,0.03f).setDamageDrop(18, 25, 3.0f).setPenetration(PENETRATION_MED).setAIStats(RANGE_SHORT, 30, 4, 2).setHandType(GunHandType.ONE_POINT_FIVE_HANDED).setMuzzleFlashTime(2).setMuzzleLight(0f, 0.8f, 1.0f).setCrossHair(EnumCrosshairStyle.HORIZONTAL_TWO_PART));

		POWERHAMMER = reg(new PowerHammer("powerhammer", POWERHAMMER_PROJECTILES, false, 4, 300, 45, 3.5f, TGSounds.POWERHAMMER_FIRE, TGSounds.POWERHAMMER_RELOAD,3,0.0f,20f,5, 6.0f,2.0f,2f, 12.0f).setToolLevel(FabricToolTags.PICKAXES,2).setToolLevel(FabricToolTags.SHOVELS,2).setChargeSound(TGSounds.POWERHAMMER_CHARGE).setBulletSpeed(1.0f).setRecoiltime(12).setShootWithLeftClick(false).setAIStats(RANGE_MELEE, 30, 0, 0).setDamageDrop(3, 3, 2.5f).setNoMuzzleLight().setCrossHair(EnumCrosshairStyle.VANILLA));

		MININGDRILL = reg(new MiningDrill("miningdrill", CHAINSAW_PROJECTILES, false, 3, 300, 45, 10.0f, TGSounds.DRILLER_LOOP, TGSounds.POWERHAMMER_RELOAD, 2, 0.0f,1f,1, 12.0f, 2.0f, 1.5f, 14.0f).setToolLevel(FabricToolTags.PICKAXES,3).setToolLevel(FabricToolTags.SHOVELS,3).setRecoiltime(5).setShootWithLeftClick(false).setFiresoundStart(TGSounds.DRILLER_SWING).setMaxLoopDelay(10).setPenetration(PENETRATION_MED).setAIStats(RANGE_MELEE, 10, 0, 0).setTurretPosOffset(0, -0.47f, -0.08f).setNoMuzzleLight().setCrossHair(EnumCrosshairStyle.VANILLA));

		LASERGUN = reg(new GenericGun("lasergun", LASERGUN_PROJECTILES, false, 5, 45, 45, 12.0f, TGSounds.LASERGUN_FIRE, TGSounds.LASERGUN_RELOAD, MAX_RANGE_SNIPER, 0.0f).setZoom(0.75f, true,0.75f,false).setBulletSpeed(100.0f).setAIStats(RANGE_MEDIUM, 30, 0, 0).setTurretPosOffset(0, 0.01f, 0.11f).setMuzzleLight(0.9f, 0.3f, 0.1f).setRangeTooltipType(RangeTooltipType.NO_DROP).setCrossHair(EnumCrosshairStyle.HORIZONTAL_TWO_PART));

		LASERPISTOL = reg(new GenericGun("laserpistol", LASERPISTOL_PROJECTILES, false, 6, 20, 40, 9.0f, TGSounds.LASER_PISTOL_FIRE, TGSounds.LASER_PISTOL_RELOAD, MAX_RANGE_SNIPER, 0.025f).setBulletSpeed(100.0f).setAIStats(RANGE_MEDIUM, 30, 0, 0).setMuzzleLight(0.9f, 0.3f, 0.1f).setRangeTooltipType(RangeTooltipType.NO_DROP).setHandType(GunHandType.ONE_HANDED).setCrossHair(EnumCrosshairStyle.HORIZONTAL_TWO_PART));

		SONICSHOTGUN = reg(new SonicShotgun("sonicshotgun",SONIC_SHOTGUN_PROJECTILES,true, 12, 8, 40, 25.0f, TGSounds.SONIC_SHOTGUN_FIRE, TGSounds.SONIC_SHOTGUN_RELOAD,20,0.0f).setBulletSpeed(0.75f).setGravity(0.0f).setDamageDrop(5, 15, 5.0f).setPenetration(PENETRATION_MED).setAIStats(RANGE_SHORT, 40, 0, 0).setCrossHair(EnumCrosshairStyle.QUAD_NO_CORNERS));

		TESLAGUN = reg(new GenericGun("teslagun", TESLAGUN_PROJECTILES, false, 8, 25, 45, 12.0f, TGSounds.TESLA_FIRE, TGSounds.TESLA_RELOAD, MAX_RANGE_RIFLE/*TODO ?Teslagun.LIFETIME*/, 0.0f).setZoom(0.75f, true,1.0f,false).setBulletSpeed(80.0f/* TODO ??Lasergun.SPEED*/).setMuzzleFlashTime(10).setAIStats(RANGE_MEDIUM, 30, 0, 0).setMuzzleLight(0f, 0.8f, 1.0f).setCrossHair(EnumCrosshairStyle.HORIZONTAL_TWO_PART_E));

		GRAPPLING_HOOK = reg(new GrapplingHook("grapplinghook", GRAPPLING_HOOK_PROJECTILES, true, 6, 100,25,1.0f, SoundEvents.ITEM_CROSSBOW_SHOOT, TGSounds.POWERHAMMER_RELOAD, MAX_RANGE_RIFLE, 0.0f,30.0f,1).setChargeSound(SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_1).setFireWhileCharging(true).setShootWithLeftClick(false).setHandType(GunHandType.ONE_HANDED).setBulletSpeed(2.5f).setGravity(0.005d).setPenetration(PENETRATION_LOW).setAIStats(RANGE_SHORT, 30, 0, 0).setDamageDrop(8, 15, 0.5f).setForwardOffset(0.40f).setCrossHair(EnumCrosshairStyle.TRI));


	}
	
	public static GenericGun reg(GenericGun gun) {
		Registry.register(Registry.ITEM, new TGIdentifier(gun.name), gun);
		return gun;
	}
	
}
