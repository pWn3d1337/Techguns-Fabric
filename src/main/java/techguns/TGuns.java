package techguns;

import net.minecraft.util.registry.Registry;
import techguns.entities.projectiles.BioGunProjectile;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.GuidedMissileProjectile;
import techguns.entities.projectiles.RocketProjectile;
import techguns.entities.projectiles.TFGProjectile;
import techguns.entities.projectiles.StoneBulletProjectile;
import techguns.items.guns.ChargedProjectileSelector;
import techguns.items.guns.EnumCrosshairStyle;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GenericGunCharge;
import techguns.items.guns.GuidedMissileLauncher;
import techguns.items.guns.IProjectileFactory;
import techguns.items.guns.ProjectileSelector;
import techguns.items.guns.RangeTooltipType;
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
	
	public static ProjectileSelector<GenericProjectile> ASSAULTRIFLE_MAG_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> SNIPER_MAG_PROJECTILES;
	public static ProjectileSelector<GenericProjectile> MINIGUN_MAG_PROJECTILES;
	
	public static ProjectileSelector<RocketProjectile> ROCKET_PROJECTILES;
	public static ChargedProjectileSelector<GuidedMissileProjectile> GUIDED_MISSILE_PROJECTILES;
	public static ChargedProjectileSelector<BioGunProjectile> BIOGUN_PROJECTILES;
	public static ChargedProjectileSelector<TFGProjectile> TFG_PROJECTILES;
	
	@SuppressWarnings({ "rawtypes", "unchecked" }) //java does not like generics in arrays so there are these warnings
	@Override
	public void init() {
		IProjectileFactory<GenericProjectile> GENERIC_PROJECTILE = new GenericProjectile.Factory();
		IProjectileFactory[] GENERIC_BULLET = {GENERIC_PROJECTILE, GENERIC_PROJECTILE};
		
		ASSAULTRIFLE_MAG_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.ASSAULT_RIFLE_MAGAZINE, GENERIC_BULLET);
		GUIDED_MISSILE_PROJECTILES = new ChargedProjectileSelector(AmmoTypes.ROCKETS_NO_NUKES, new GuidedMissileProjectile.Factory(), new GuidedMissileProjectile.Factory()); //new GuidedMissileProjectileHV.Factory());//TODO HV missiles
		
		ROCKET_PROJECTILES = new ProjectileSelector(AmmoTypes.ROCKETS, new RocketProjectile.Factory(), new RocketProjectile.Factory(), new RocketProjectile.Factory());//, new RocketProjectileNuke.Factory(), new RocketProjectileHV.Factory()); //TODO ammo types
		
		BIOGUN_PROJECTILES = new ChargedProjectileSelector<BioGunProjectile>(AmmoTypes.BIO_TANK, new BioGunProjectile.Factory());
		
		TFG_PROJECTILES = new ChargedProjectileSelector<TFGProjectile>(AmmoTypes.NUCLEAR_POWER_CELL, new TFGProjectile.Factory());
		SNIPER_MAG_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.AS50_MAGAZINE, new IProjectileFactory[]{GENERIC_PROJECTILE, GENERIC_PROJECTILE, GENERIC_PROJECTILE});
		
		
		MINIGUN_MAG_PROJECTILES = new ProjectileSelector<GenericProjectile>(AmmoTypes.MINIGUN_AMMO_DRUM, GENERIC_BULLET);
		
		
		HANDCANNON = reg(new GenericGun("handcannon", new ProjectileSelector<StoneBulletProjectile>(AmmoTypes.STONE_BULLETS, new StoneBulletProjectile.Factory()), true, 12,1,30, 8.0f, TGSounds.HANDGUN_FIRE, TGSounds.HANDGUN_RELOAD,25,0.035f).setBulletSpeed(1.0f).setGravity(0.015d).setDamageDrop(10, 25, 5.0f).setAIStats(RANGE_CLOSE, 60, 0, 0).setRecoiltime(12).setCrossHair(EnumCrosshairStyle.QUAD_NO_CORNERS));
		
		M4 = reg(new GenericGun("m4", ASSAULTRIFLE_MAG_PROJECTILES, false, 3, 30, 45, 8.0f, TGSounds.M4_FIRE, TGSounds.M4_RELOAD, MAX_RANGE_RIFLE, 0.05f).setBulletSpeed(4.0f).setZoom(0.75f, true,0.75f,false).setDamageDrop(25, 40, 6.0f).setAIStats(RANGE_MEDIUM, 30, 3, 3).setTurretPosOffset(0, 0, 0.08f)/*.setMuzzleParticle(1,0,0.025f,0)*/.setPenetration(PENETRATION_LOW).setMuzzleFlashTime(4));
	
		AK47 = reg(new GenericGun("ak47", ASSAULTRIFLE_MAG_PROJECTILES, false, 3, 30,45,9.0f, TGSounds.AK_FIRE, TGSounds.AK_RELOAD, MAX_RANGE_RIFLE, 0.030f).setDamageDrop(20, 30, 5.0f).setPenetration(PENETRATION_LOW).setBulletSpeed(3.25f).setAIStats(RANGE_MEDIUM, 30, 3, 3).setMuzzleFlashTime(4).setTurretPosOffset(0, 0, 0.08f));
		  		
		AUG = reg(new GenericGun("aug", ASSAULTRIFLE_MAG_PROJECTILES, false, 3, 30,45,8.0f, TGSounds.AUG_FIRE, TGSounds.AUG_RELOAD, MAX_RANGE_RIFLE, 0.010f).setBulletSpeed(4.0f).setZoom(0.50f, true,0.5f,true).setDamageDrop(30, 45, 7.0f).setAIStats(RANGE_MEDIUM, 30, 3, 3).setPenetration(PENETRATION_LOW).setMuzzleFlashTime(4));
		 
		
		SCAR = reg(new GenericGun("scar", ASSAULTRIFLE_MAG_PROJECTILES, false, 4, 20,45,12.0f, TGSounds.SCAR_FIRE, TGSounds.SCAR_RELOAD, MAX_RANGE_RIFLE_LONG, 0.015f).setZoom(0.65f, true,0.5f,true).setDamageDrop(35, 60, 10.0f).setAIStats(RANGE_MEDIUM, 30, 5, 2).setPenetration(PENETRATION_MED).setBulletSpeed(4.5f).setMuzzleFlashTime(5).setTurretPosOffset(0, 0.02f, 0.09f));	
		
		BIOGUN = reg(new GenericGunCharge("biogun", BIOGUN_PROJECTILES, false, 6, 30,45,10.0f, TGSounds.BIOGUN_FIRE, TGSounds.BIOGUN_RELOAD, MAX_RANGE_PISTOL, 0.015f,30.0f,3).setChargeSound(TGSounds.BIOGUN_CHARGE).setChargeFX("biogunCharge",-0.12f, -0.07f, 0.27f).setBulletSpeed(0.75f).setGravity(0.01d).setPenetration(PENETRATION_LOW).setAIStats(RANGE_SHORT, 30, 0, 0).setDamageDrop(8, 15, 8.0f).setMuzzleLight(0.2f, 0.9f, 0.5f).setForwardOffset(0.40f).setCrossHair(EnumCrosshairStyle.QUAD_NO_CORNERS));
			
		AS50 = reg(new GenericGun("as50", SNIPER_MAG_PROJECTILES, true, 10, 10, 80, 32.0f, TGSounds.AS50_FIRE, TGSounds.AS50_RELOAD, MAX_RANGE_SNIPER,0.0625f).setDamageDrop(40, 60, 24.0f).setZoom(0.35f, true,0.125f,true).setBulletSpeed(4.5f).setRecoiltime(10).setPenetration(PENETRATION_MED_HIGH).setAIStats(RANGE_FAR, 30, 0, 0).setTurretPosOffset(0, 0.03f, 0.13f));
		 	
		MINIGUN = reg(new GenericGun("minigun",MINIGUN_MAG_PROJECTILES,false, 0, 200, 100, 5.0f, TGSounds.MINIGUN_FIRE, TGSounds.MINIGUN_RELOAD, MAX_RANGE_RIFLE_LONG, 0.025f).setDamageDrop(30, 50, 3.0f).setPenetration(PENETRATION_LOW).setBulletSpeed(4.5f).setAIStats(RANGE_MEDIUM, 40, 10, 1).setCheckRecoil().setMuzzleFlashTime(4).setCheckMuzzleFlash().setTurretPosOffset(0, -0.49f, -0.14f));
		
		GUIDED_MISSLE_LAUNCHER = reg(new GuidedMissileLauncher("guidedmissilelauncher", GUIDED_MISSILE_PROJECTILES, true, 10, 1 , 40, 50.0f, TGSounds.GUIDEDMISSILE_FIRE, TGSounds.ROCKET_RELOAD, 100, 0.05f, 200, 1).setFireWhileCharging(true).setChargeFireAnims(false).setLockOn(20, 80).setBulletSpeed(1.0f).setRecoiltime(10).setAIStats(RANGE_MEDIUM,80,0,0).setTurretPosOffset(0, 0.01f, -0.12f).setDamageDrop(2f, 4f, 20f).setRangeTooltipType(RangeTooltipType.RADIUS).setCrossHair(EnumCrosshairStyle.QUAD_CORNERS_DOT));

		ROCKET_LAUNCHER = reg(new GenericGun("rocketlauncher", ROCKET_PROJECTILES, true, 10, 1 , 40, 50.0f, TGSounds.ROCKET_FIRE, TGSounds.ROCKET_RELOAD, 200, 0.05f).setGravity(0.01D).setBulletSpeed(1.0f).setRecoiltime(10).setAIStats(RANGE_MEDIUM,80,0,0).setTurretPosOffset(0, 0, -0.1f).setDamageDrop(2.0f, 4.0f, 10f).setRangeTooltipType(RangeTooltipType.RADIUS).setForwardOffset(0.35f).setCrossHair(EnumCrosshairStyle.QUAD_CORNERS_DOT));
		 
		TFG = reg(new GenericGunCharge("tfg", TFG_PROJECTILES, false, 5, 20,45,50.0f, TGSounds.TFG_FIRE, TGSounds.BIOGUN_RELOAD, 100, 0.015f, 60.0f ,10).setChargeSound(TGSounds.TFG_CHARGE).setChargeFX("TFGChargeStart",-0.14f, -0.10f, 0.42f).setBulletSpeed(2.0f).setRangeTooltipType(RangeTooltipType.RADIUS).setPenetration(PENETRATION_MED_HIGH).setAIStats(RANGE_SHORT, 30, 0, 0).setDamageDrop(8, 15, 15.0f).setMuzzleLight(0.2f, 1.0f, 0.2f).setMuzzleFlashTime(10).setRecoiltime(10).setForwardOffset(0.40f));
	}
	
	public static GenericGun reg(GenericGun gun) {
		Registry.register(Registry.ITEM, new TGIdentifier(gun.name), gun);
		return gun;
	}
	
}
