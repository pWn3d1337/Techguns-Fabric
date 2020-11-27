package techguns;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import techguns.entities.projectiles.BioGunProjectile;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.GuidedMissileProjectile;
import techguns.entities.projectiles.RocketProjectile;
import techguns.entities.projectiles.TFGProjectile;

public class TGEntities implements ITGInitializer {

	public static EntityType<GenericProjectile> GENERIC_PROJECTILE;
	public static EntityType<GuidedMissileProjectile> GUIDED_MISSILE;
	public static EntityType<RocketProjectile> ROCKET_PROJECTILE;
	public static EntityType<BioGunProjectile> BIOGUN_PROJECTILE;
	public static EntityType<TFGProjectile> TFG_PROJECTILE;
	public static final int bulletTrackRange = 128;
	
	@Override
	public void init() {
		GENERIC_PROJECTILE = Registry.register(
	            Registry.ENTITY_TYPE,
	            new TGIdentifier("generic_projectile"),
	            FabricEntityTypeBuilder.<GenericProjectile>create(SpawnGroup.MISC, GenericProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());
		
		GUIDED_MISSILE = Registry.register(
	            Registry.ENTITY_TYPE,
	            new TGIdentifier("guided_missile"),
	            FabricEntityTypeBuilder.<GuidedMissileProjectile>create(SpawnGroup.MISC, GuidedMissileProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());
		
		ROCKET_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
	            new TGIdentifier("rocket_projectile"),
	            FabricEntityTypeBuilder.<RocketProjectile>create(SpawnGroup.MISC, RocketProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

		BIOGUN_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
				new TGIdentifier("biogun_projectile"),
				FabricEntityTypeBuilder.<BioGunProjectile>create(SpawnGroup.MISC, BioGunProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());
		
		TFG_PROJECTILE = Registry.register(
				Registry.ENTITY_TYPE,
	            new TGIdentifier("tfg_projectile"),
	            FabricEntityTypeBuilder.<TFGProjectile>create(SpawnGroup.MISC,TFGProjectile::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

	}

}
