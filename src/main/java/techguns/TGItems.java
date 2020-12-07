package techguns;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import techguns.items.GenericItem;
import techguns.items.GenericItemRenderHack;

public class TGItems implements ITGInitializer {

	public static Item STONE_BULLETS;
	public static Item PISTOL_ROUNDS;
	public static Item SHOTGUN_ROUNDS;
	public static Item RIFLE_ROUNDS;
	public static Item RIFLE_ROUNDS_STACK;
	public static Item SNIPER_ROUNDS;
	public static Item GRENADE_40MM;
	public static Item ADVANCED_ROUNDS;
	public static Item ROCKET;
	
	public static Item SMG_MAGAZINE;
	public static Item SMG_MAGAZINE_EMPTY;
	public static Item PISTOL_MAGAZINE;
	public static Item PISTOL_MAGAZINE_EMPTY;
	public static Item ASSAULTRIFLE_MAGAZINE;
	public static Item ASSAULTRIFLE_MAGAZINE_EMPTY;
	public static Item MINIGUN_DRUM;
	public static Item MINIGUN_DRUM_EMPTY;
	public static Item LMG_MAGAZINE;
	public static Item LMG_MAGAZINE_EMPTY;
	public static Item AS50_MAGAZINE;
	public static Item AS50_MAGAZINE_EMPTY;
	
	public static Item ADVANCED_MAGAZINE;
	public static Item ADVANCED_MAGAZINE_EMPTY;
	
	public static Item COMPRESSED_AIR_TANK;
	public static Item COMPRESSED_AIR_TANK_EMPTY;
	
	public static Item BIO_TANK;
	public static Item BIO_TANK_EMPTY;
	
	public static Item FUEL_TANK;
	public static Item FUEL_TANK_EMPTY;
	
	public static Item ENERGY_CELL;
	public static Item ENERGY_CELL_EMPTY;
	
	public static Item NUCLEAR_POWERCELL;
	public static Item NUCLEAR_POWERCELL_EMPTY;

	public static Item REDSTONE_BATTERY;
	public static Item REDSTONE_BATTERY_EMPTY;

	public static Item GAUSSRIFLE_SLUGS;

	public static Item NETHER_CHARGE;
	/**
	 * MATERIALS
	 */
	public static ItemStack HEAVY_CLOTH;
	public static ItemStack PROTECTIVE_FIBER;
	
	public static ItemStack BIOMASS;
	
	/**
	 * GUN PARTS
	 */
	public static ItemStack RECEIVER_IRON;
	public static ItemStack RECEIVER_STEEL;
	public static ItemStack RECEIVER_OBSIDIAN_STEEL;
	public static ItemStack RECEIVER_CARBON;
	
	public static ItemStack BARREL_STONE;
	public static ItemStack BARREL_IRON;
	//public static ItemStack BARREL_STEEL;
	public static ItemStack BARREL_OBSIDIAN_STEEL;
	public static ItemStack BARREL_CARBON;
	public static ItemStack BARREL_LASER;
	
	public static ItemStack STOCK_WOOD;
	public static ItemStack STOCK_PLASTIC;
	public static ItemStack STOCK_CARBON;
	
	/**
	 * Metals
	 */
	public static ItemStack STEAMARMOR_PLATE;
	public static ItemStack PLATE_IRON;
	public static ItemStack PLATE_COPPER;
	public static ItemStack PLATE_TIN;
	public static ItemStack PLATE_BRONZE;
	public static ItemStack PLATE_STEEL;
	public static ItemStack PLATE_OBSIDIAN_STEEL;
	public static ItemStack PLATE_LEAD;
	public static ItemStack PLATE_TITANIUM;
	public static ItemStack PLATE_CARBON;
	
	public static ItemStack PLASTIC_SHEET;
	public static ItemStack RUBBER_BAR;
	
	public static ItemStack MECHANICAL_PARTS_IRON;
	public static ItemStack MECHANICAL_PARTS_OBSIDIAN_STEEL;
	public static ItemStack MECHANICAL_PARTS_CARBON;
	
	public static ItemStack WIRE_COPPER;
	public static ItemStack WIRE_GOLD;
	public static ItemStack CARBON_FIBERS;
	
	public static ItemStack CIRCUIT_BOARD_BASIC;
	public static ItemStack CIRCUIT_BOARD_ELITE;
	public static ItemStack POWER_ARMOR_PLATING;
	
	public static ItemStack COIL;
	public static ItemStack CYBERNETIC_PARTS;
	public static ItemStack ELECTRIC_ENGINE;
	
	public static ItemStack LASER_FOCUS;
	public static ItemStack PUMP_MECHANISM;
	public static ItemStack RAD_EMITTER;
	public static ItemStack SONIC_EMITTER;
	
	public static ItemStack TGX;
	public static ItemStack TREATED_LEATHER;
	public static ItemStack ORE_TITANIUM;
	
	public static ItemStack RAW_PLASTIC;
	public static ItemStack RAW_RUBBER;
	public static ItemStack YELLOWCAKE;
	public static ItemStack ENRICHED_URANIUM;
	
	public static ItemStack INGOT_COPPER;
	public static ItemStack INGOT_TIN;
	public static ItemStack INGOT_BRONZE;
	public static ItemStack INGOT_STEEL;
	public static ItemStack INGOT_OBSIDIAN_STEEL;
	public static ItemStack INGOT_LEAD;
	public static ItemStack INGOT_TITANIUM;
	
	public static ItemStack NUGGET_COPPER;
	public static ItemStack NUGGET_LEAD;
	public static ItemStack NUGGET_STEEL;
	
	//
	public static ItemStack GAS_MASK_FILTER;
	public static ItemStack GLIDER_BACKBACK;
	public static ItemStack GLIDER_WING;
	public static ItemStack ANTI_GRAV_CORE;
	
	public static ItemStack OXYGEN_MASK;
	
	public static ItemStack MACHINE_UPGRADE_STACK;

	public static ItemStack BARREL_GAUSS;
	
	//Ammo Variants
	public static ItemStack SHOTGUN_ROUNDS_INCENDIARY;
	public static ItemStack AS50_MAGAZINE_INCENDIARY;
	public static ItemStack SNIPER_ROUNDS_INCENDIARY;
	public static ItemStack PISTOL_ROUNDS_INCENDIARY;
	public static ItemStack RIFLE_ROUNDS_INCENDIARY;
	public static ItemStack RIFLE_ROUNDS_STACK_INCENDIARY;
	public static ItemStack PISTOL_MAGAZINE_INCENDIARY;
	public static ItemStack MINIGUN_DRUM_INCENDIARY;
	public static ItemStack SMG_MAGAZINE_INCENDIARY;
	public static ItemStack ASSAULTRIFLE_MAGAZINE_INCENDIARY;
	public static ItemStack LMG_MAGAZINE_INCENDIARY;
	
	public static ItemStack ROCKET_NUKE;
	public static ItemStack TACTICAL_NUKE_WARHEAD;
	
	public static ItemStack BARREL_TITANIUM;
	public static ItemStack RECEIVER_TITANIUM;
	public static ItemStack PLASMA_GENERATOR;
	
	public static ItemStack SNIPER_ROUNDS_EXPLOSIVE;
	public static ItemStack AS50_MAGAZINE_EXPLOSIVE;
	
	public static ItemStack ROCKET_HIGH_VELOCITY;
	
	/**
	 * ADDITONAL SLOT ITEMS
	 */
/*	public static ItemGasMask GAS_MASK;
	public static ItemGlider GLIDER;
	
	public static ItemNightVisionGoggles NIGHTVISION_GOGGLES;
	public static ItemJumpPack JUMPPACK;
	public static ItemJetpack JETPACK;
	public static ItemScubaTanks SCUBA_TANKS;
	public static ItemTacticalMask TACTICAL_MASK;
	public static ItemAntiGravPack ANTI_GRAV_PACK;*/
	
	public static ItemStack WORKING_GLOVES;
	
	public static ItemStack TURRET_ARMOR_IRON;
	public static ItemStack TURRET_ARMOR_STEEL;
	public static ItemStack TURRET_ARMOR_OBSIDIAN_STEEL;
	public static ItemStack TURRET_ARMOR_CARBON;
	
	public static ItemStack QUARTZ_ROD;
	public static ItemStack RC_HEAT_RAY;
	public static ItemStack RC_UV_EMITTER;
	
	public static ItemStack MININGDRILLHEAD_OBSIDIAN;
	public static ItemStack MININGDRILLHEAD_CARBON;
	
	public static ItemStack POWERHAMMERHEAD_OBSIDIAN;
	public static ItemStack POWERHAMMERHEAD_CARBON;
	
	public static ItemStack CHAINSAWBLADES_OBSIDIAN;
	public static ItemStack CHAINSAWBLADES_CARBON;
	
	public static ItemStack INFUSION_BAG;
		
	public static final ItemGroup ITEM_GROUP_TECHGUNS = FabricItemGroupBuilder.build(
			new TGIdentifier("techguns"),
			() -> new ItemStack(TGItems.RIFLE_ROUNDS));
	
	@Override
	public void init() {
		STONE_BULLETS = addAmmoItem("stonebullets"); 
		PISTOL_ROUNDS = addAmmoItem("pistolrounds");
		SHOTGUN_ROUNDS = addAmmoItem("shotgunrounds");
		RIFLE_ROUNDS = addAmmoItem("riflerounds");
		SNIPER_ROUNDS = addAmmoItem("sniperrounds");
		GRENADE_40MM = addAmmoItem("40mmgrenade");
		ADVANCED_ROUNDS = addAmmoItem("advancedrounds");
		ROCKET = addAmmoItemRenderHack("rocket");
		RIFLE_ROUNDS_STACK = addAmmoItem("rifleroundsstack");
		
		SMG_MAGAZINE = addAmmoItem("smgmagazine");
		SMG_MAGAZINE_EMPTY = addAmmoItem("smgmagazineempty");
		PISTOL_MAGAZINE = addAmmoItem("pistolmagazine");
		PISTOL_MAGAZINE_EMPTY = addAmmoItem("pistolmagazineempty");
		ASSAULTRIFLE_MAGAZINE = addAmmoItemRenderHack("assaultriflemagazine");
		ASSAULTRIFLE_MAGAZINE_EMPTY = addAmmoItemRenderHack("assaultriflemagazineempty");
		LMG_MAGAZINE = addAmmoItemRenderHack("lmgmagazine");
		LMG_MAGAZINE_EMPTY = addAmmoItemRenderHack("lmgmagazineempty");
		MINIGUN_DRUM = addAmmoItem("minigundrum");
		MINIGUN_DRUM_EMPTY = addAmmoItem("minigundrumempty");
		AS50_MAGAZINE = addAmmoItemRenderHack("as50magazine");
		AS50_MAGAZINE_EMPTY = addAmmoItemRenderHack("as50magazineempty");
		
		ADVANCED_MAGAZINE = addAmmoItem("advancedmagazine");
		ADVANCED_MAGAZINE_EMPTY = addAmmoItem("advancedmagazineempty");
		
		COMPRESSED_AIR_TANK = addAmmoItem("compressedairtank");
		COMPRESSED_AIR_TANK_EMPTY = addAmmoItem("compressedairtankempty");
		
		BIO_TANK = addAmmoItem("biotank");
		BIO_TANK_EMPTY = addAmmoItem("biotankempty");
		
		FUEL_TANK = addAmmoItem("fueltank");
		FUEL_TANK_EMPTY = addAmmoItem("fueltankempty");
		
		ENERGY_CELL = addAmmoItem("energycell");
		ENERGY_CELL_EMPTY = addAmmoItem("energycellempty");
		
		NUCLEAR_POWERCELL = addAmmoItem("nuclearpowercell");
		NUCLEAR_POWERCELL_EMPTY = addAmmoItem("nuclearpowercelldepleted");
		
		REDSTONE_BATTERY = addAmmoItem("redstonebattery");
		REDSTONE_BATTERY_EMPTY = addAmmoItem("redstonebatteryempty");

		GAUSSRIFLE_SLUGS = addAmmoItem("gaussrifleslugs");

		NETHER_CHARGE = addAmmoItem("nethercharge");
	}

	protected static Item addAmmoItem(String name) {
		//TODO ammoslot
		Item item = new GenericItem(new Item.Settings());
		Registry.register(Registry.ITEM, new TGIdentifier(name), item);
		return item;
	}
	
	protected static Item addAmmoItemRenderHack(String name) {
		//TODO ammoslot
		Item item = new GenericItemRenderHack(new Item.Settings());
		Registry.register(Registry.ITEM, new TGIdentifier(name), item);
		return item;
	}
	
	public static ItemStack newStack(ItemStack stack, int size) {
		ItemStack ret = stack.copy();
		ret.setCount(size);
		return ret;
	}
}
