package techguns;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import techguns.blocks.entity.CamoBenchBlockEntity;
import techguns.entities.projectiles.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class TGEntities implements ITGInitializer {

	public static EntityType<GenericProjectile> GENERIC_PROJECTILE;
	public static EntityType<GuidedMissileProjectile> GUIDED_MISSILE;
	public static EntityType<RocketProjectile> ROCKET_PROJECTILE;
	public static EntityType<BioGunProjectile> BIOGUN_PROJECTILE;
	public static EntityType<StoneBulletProjectile> STONEBULLET_PROJECTILE;
	public static EntityType<ChainsawProjectile> CHAINSAW_PROJECTILE;
	public static EntityType<TFGProjectile> TFG_PROJECTILE;
	public static EntityType<GenericBeamProjectile> GENERIC_BEAM_PROJECTILE;
	public static EntityType<GrenadeProjectile> GRENADE_PROJECTILE;
	public static EntityType<FlamethrowerProjectile> FLAMETHROWER_PROJECTILE;
	public static EntityType<GenericProjectileFX> GENERIC_FX_PROJECTILE;
	public static EntityType<SonicShotgunProjectile> SONIC_SHOTGUN_PROJECTILE;
	public static EntityType<GrapplingHookProjectile> GRAPPLING_HOOK_PROJECTILE;
	public static EntityType<TeslaProjectile> TESLA_PROJECTILE;
	//Client Only:
	//public static EntityType<FlyingGibs> FLYING_GIBS;
	
	public static final int bulletTrackRange = 128;
	public static final int gibsTrackRange = 64;

	public static Map<EntityType, EntityPacketFactory> ENTITY_SPAWN_PACKET_MAP = new HashMap<>();

	public interface EntityPacketFactory<T extends Entity> {
		T create(EntityType<T> type, World world, LivingEntity shooter);
	}

	@Override
	public void init() {
		GENERIC_PROJECTILE = Registry.register(
	            Registry.ENTITY_TYPE,
	            new TGIdentifier("generic_projectile"),
	            FabricEntityTypeBuilder.<GenericProjectile>create(SpawnGroup.MISC, GenericProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

		ENTITY_SPAWN_PACKET_MAP.put(GENERIC_PROJECTILE, GenericProjectile::new);

		GUIDED_MISSILE = Registry.register(
	            Registry.ENTITY_TYPE,
	            new TGIdentifier("guided_missile"),
	            FabricEntityTypeBuilder.<GuidedMissileProjectile>create(SpawnGroup.MISC, GuidedMissileProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

		ENTITY_SPAWN_PACKET_MAP.put(GUIDED_MISSILE, GuidedMissileProjectile::new);

		ROCKET_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
	            new TGIdentifier("rocket_projectile"),
	            FabricEntityTypeBuilder.<RocketProjectile>create(SpawnGroup.MISC, RocketProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

		ENTITY_SPAWN_PACKET_MAP.put(ROCKET_PROJECTILE, RocketProjectile::new);

		BIOGUN_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
				new TGIdentifier("biogun_projectile"),
				FabricEntityTypeBuilder.<BioGunProjectile>create(SpawnGroup.MISC, BioGunProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

		ENTITY_SPAWN_PACKET_MAP.put(BIOGUN_PROJECTILE, BioGunProjectile::new);

		TFG_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
	            new TGIdentifier("tfg_projectile"),
	            FabricEntityTypeBuilder.<TFGProjectile>create(SpawnGroup.MISC,TFGProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

		ENTITY_SPAWN_PACKET_MAP.put(TFG_PROJECTILE, TFGProjectile::new);

		STONEBULLET_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
	            new TGIdentifier("stonebullet_projectile"),
	            FabricEntityTypeBuilder.<StoneBulletProjectile>create(SpawnGroup.MISC, StoneBulletProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

		ENTITY_SPAWN_PACKET_MAP.put(STONEBULLET_PROJECTILE, StoneBulletProjectile::new);

		CHAINSAW_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
	            new TGIdentifier("chainsaw_projectile"),
	            FabricEntityTypeBuilder.<ChainsawProjectile>create(SpawnGroup.MISC, ChainsawProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

		ENTITY_SPAWN_PACKET_MAP.put(CHAINSAW_PROJECTILE, ChainsawProjectile::new);

		GENERIC_BEAM_PROJECTILE = Registry.register(
	            Registry.ENTITY_TYPE,
	            new TGIdentifier("generic_beam_projectile"),
	            FabricEntityTypeBuilder.<GenericBeamProjectile>create(SpawnGroup.MISC, GenericBeamProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(bulletTrackRange).trackedUpdateRate(1).build());

		ENTITY_SPAWN_PACKET_MAP.put(GENERIC_BEAM_PROJECTILE, GenericBeamProjectile::new);

		GRENADE_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
				new TGIdentifier("grenade_projectile"),
				FabricEntityTypeBuilder.<GrenadeProjectile>create(SpawnGroup.MISC, GrenadeProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(bulletTrackRange).trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

		ENTITY_SPAWN_PACKET_MAP.put(GRENADE_PROJECTILE, GrenadeProjectile::new);

		FLAMETHROWER_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
				new TGIdentifier("flamethrower_projectile"),
				FabricEntityTypeBuilder.<FlamethrowerProjectile>create(SpawnGroup.MISC, FlamethrowerProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(bulletTrackRange).build());

		ENTITY_SPAWN_PACKET_MAP.put(FLAMETHROWER_PROJECTILE, FlamethrowerProjectile::new);

		GENERIC_FX_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
				new TGIdentifier("generic_fx_projectile"),
				FabricEntityTypeBuilder.<GenericProjectileFX>create(SpawnGroup.MISC, GenericProjectileFX::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(bulletTrackRange).build());

		ENTITY_SPAWN_PACKET_MAP.put(GENERIC_FX_PROJECTILE, GenericProjectileFX::new);

//		FLYING_GIBS = Registry.register(
//				Registry.ENTITY_TYPE,
//				new TGIdentifier("flying_gibs"),
//				FabricEntityTypeBuilder.<FlyingGibs>create(SpawnGroup.MISC, FlyingGibs::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(gibsTrackRange).build());

		SONIC_SHOTGUN_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
				new TGIdentifier("sonic_shotgun_projectile"),
				FabricEntityTypeBuilder.<SonicShotgunProjectile>create(SpawnGroup.MISC, SonicShotgunProjectile::new).dimensions(EntityDimensions.fixed(0.35f, 0.35f)).trackRangeBlocks(bulletTrackRange).build());

		ENTITY_SPAWN_PACKET_MAP.put(SONIC_SHOTGUN_PROJECTILE, SonicShotgunProjectile::new);

		GRAPPLING_HOOK_PROJECTILE = Registry.register(
	            Registry.ENTITY_TYPE,
	            new TGIdentifier("grappling_hook_projectile"),
	            FabricEntityTypeBuilder.<GrapplingHookProjectile>create(SpawnGroup.MISC, GrapplingHookProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

		ENTITY_SPAWN_PACKET_MAP.put(GRAPPLING_HOOK_PROJECTILE, GrapplingHookProjectile::new);

		TESLA_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
				new TGIdentifier("tesla_projectile"),
				FabricEntityTypeBuilder.<TeslaProjectile>create(SpawnGroup.MISC, TeslaProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(bulletTrackRange).build());

		ENTITY_SPAWN_PACKET_MAP.put(TESLA_PROJECTILE, TeslaProjectile::new);
	}
}
