package techguns;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import techguns.api.inventory.TGSlotType;
import techguns.inventory.slots.SlotTagItem;
import techguns.items.AmmoBagItem;
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
	public static Item MAGIC_BULLET;
	public static Item MAGIC_BULLET_LIGHTNING;
	public static Item MAGIC_BULLET_FIRE;

	/**
	 * MATERIALS
	 */
	public static Item HEAVY_CLOTH;
	public static Item PROTECTIVE_FIBER;
	
	public static Item BIOMASS;
	
	/**
	 * GUN PARTS
	 */
	public static Item RECEIVER_IRON;
	public static Item RECEIVER_STEEL;
	public static Item RECEIVER_OBSIDIAN_STEEL;
	public static Item RECEIVER_CARBON;
	
	public static Item BARREL_STONE;
	public static Item BARREL_IRON;
	//public static ItemStack BARREL_STEEL;
	public static Item BARREL_OBSIDIAN_STEEL;
	public static Item BARREL_CARBON;
	public static Item BARREL_LASER;
	
	public static Item STOCK_WOOD;
	public static Item STOCK_PLASTIC;
	public static Item STOCK_CARBON;
	
	/**
	 * Metals
	 */
	public static Item STEAMARMOR_PLATE;
	/*public static Item PLATE_IRON;
	public static Item PLATE_COPPER;
	public static Item PLATE_TIN;
	public static Item PLATE_BRONZE;
	public static Item PLATE_STEEL;
	public static Item PLATE_OBSIDIAN_STEEL;
	public static Item PLATE_LEAD;
	public static Item PLATE_TITANIUM;*/
	public static Item PLATE_CARBON;
	
	public static Item PLASTIC_SHEET;
	public static Item RUBBER_BAR;
	
	public static Item MECHANICAL_PARTS_IRON;
	public static Item MECHANICAL_PARTS_OBSIDIAN_STEEL;
	public static Item MECHANICAL_PARTS_CARBON;

	public static Item WIRE_COPPER;
	public static Item WIRE_GOLD;
	public static Item CARBON_FIBERS;

	public static Item CIRCUIT_BOARD_BASIC;
	public static Item CIRCUIT_BOARD_ADVANCED;
	public static Item CIRCUIT_BOARD_ELITE;
	public static Item POWER_ARMOR_PLATING;
	public static Item POWER_ARMOR_PLATING_MK2;
	public static Item NETHER_ARMOR_PLATING;

	public static Item COIL;
	public static Item CYBERNETIC_PARTS;
	public static Item ELECTRIC_ENGINE;

	public static Item LASER_FOCUS;
	public static Item PUMP_MECHANISM;
	public static Item RAD_EMITTER;
	public static Item SONIC_EMITTER;

	public static Item TGX;
	public static Item TREATED_LEATHER;

	public static Item RAW_PLASTIC;
	public static Item RAW_RUBBER;
	public static Item YELLOWCAKE;
	public static Item ENRICHED_URANIUM;

	public static Item TIN_INGOT;
	public static Item BRONZE_INGOT;
	public static Item STEEL_INGOT;
	public static Item OBSIDIAN_STEEL_INGOT;
	public static Item LEAD_INGOT;
	public static Item TITANIUM_INGOT;
	public static Item RAW_STEEL_INGOT;
	public static Item RAW_OBSIDIAN_STEEL_INGOT;

	public static Item COPPER_NUGGET;
	//public static Item NUGGET_LEAD;
	public static Item STEEL_NUGGET;

	//
	public static Item GAS_MASK_FILTER;
	public static Item GLIDER_BACKBACK;
	public static Item GLIDER_WING;
	public static Item ANTI_GRAV_CORE;

	public static Item OXYGEN_MASK;

	//public static Item MACHINE_UPGRADE_STACK;

	public static Item BARREL_GAUSS;
	
	public static Item BARREL_TITANIUM;
	public static Item RECEIVER_TITANIUM;
	public static Item PLASMA_GENERATOR;

	//Ammo Variants
	public static Item SHOTGUN_ROUNDS_INCENDIARY;
	public static Item AS50_MAGAZINE_INCENDIARY;
	public static Item SNIPER_ROUNDS_INCENDIARY;
	public static Item PISTOL_ROUNDS_INCENDIARY;
	public static Item RIFLE_ROUNDS_INCENDIARY;
	public static Item RIFLE_ROUNDS_STACK_INCENDIARY;
	public static Item PISTOL_MAGAZINE_INCENDIARY;
	public static Item MINIGUN_DRUM_INCENDIARY;
	public static Item SMG_MAGAZINE_INCENDIARY;
	public static Item ASSAULTRIFLE_MAGAZINE_INCENDIARY;
	public static Item LMG_MAGAZINE_INCENDIARY;

	public static Item ROCKET_NUKE;
	public static Item TACTICAL_NUKE_WARHEAD;

	public static Item SNIPER_ROUNDS_EXPLOSIVE;
	public static Item AS50_MAGAZINE_EXPLOSIVE;

	public static Item ROCKET_HIGH_VELOCITY;
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
	
	public static Item TURRET_ARMOR_IRON;
	public static Item TURRET_ARMOR_STEEL;
	public static Item TURRET_ARMOR_OBSIDIAN_STEEL;
	public static Item TURRET_ARMOR_CARBON;
	
	//public static Item QUARTZ_ROD;
	//public static Item RC_HEAT_RAY;
	//public static Item RC_UV_EMITTER;
	
	public static Item MININGDRILLHEAD_OBSIDIAN;
	public static Item MININGDRILLHEAD_CARBON;

	public static Item POWERHAMMERHEAD_STEEL;
	public static Item POWERHAMMERHEAD_OBSIDIAN;
	public static Item POWERHAMMERHEAD_CARBON;

	public static Item CHAINSAWBLADES_STEEL;
	public static Item CHAINSAWBLADES_OBSIDIAN;
	public static Item CHAINSAWBLADES_CARBON;
	
	public static Item INFUSION_BAG;

	/**
	 * BLOCK ITEMS, initialized in TGBlocks
	 */
	public static Item TIN_ORE;
	public static Item DEEPSLATE_TIN_ORE;
	public static Item LEAD_ORE;
	public static Item DEEPSLATE_LEAD_ORE;
	public static Item URANIUM_ORE;
	public static Item TITANIUM_ORE;
	public static Item DEEPSLATE_URANIUM_ORE;
	public static Item DEEPSLATE_TITANIUM_ORE;

	public static Item RAW_TIN;
	public static Item RAW_LEAD;
	public static Item RAW_TITANIUM;
	public static Item RAW_URANIUM;

	public static Item BIOBLOB;

	public static Item CAMO_BENCH;
	public static Item AMMO_BENCH;

	public static Item CONCRETE_BROWN;
	public static Item CONCRETE_BROWN_LIGHT;
	public static Item CONCRETE_BROWN_LIGHT_SCAFF;
	public static Item CONCRETE_BROWN_PIPES;
	public static Item CONCRETE_GREY;
	public static Item CONCRETE_GREY_DARK;


	public static Item AMMO_BOX;
	public static Item AMMO_BOX_LARGE;

	//spawn eggs
	public static Item SPAWN_EGG_ZOMBIE_SOLDIER;
	public static Item SPAWN_EGG_ZOMBIE_MINER;
	public static Item SPAWN_EGG_ZOMBIE_FARMER;
	public static Item SPAWN_EGG_ZOMBIE_POLICEMAN;
	public static Item SPAWN_EGG_ARMY_SOLDIER;

	public static final ItemGroup ITEM_GROUP_TECHGUNS = FabricItemGroupBuilder.build(
			new TGIdentifier("techguns"),
			() -> new ItemStack(TGItems.PISTOL_ROUNDS));

	public static TagKey<Item> TAG_BULLET_CORE;
	public static TagKey<Item> TAG_BULLET_CASING;
	public static TagKey<Item> TAG_BULLET_POWDER;

	public static final Identifier TAG_BULLET_CORE_ID = new TGIdentifier("bullet_metal_core");
	public static final Identifier TAG_BULLET_CASING_ID = new TGIdentifier("bullet_metal_casing");
	public static final Identifier TAG_BULLET_POWDER_ID = new TGIdentifier("bullet_powder");

	public static final Identifier TAG_AMMO_ID = new TGIdentifier("ammo");
	public static TagKey<Item> TAG_AMMO;

	public static final Identifier TAG_AMMO_FULL_ID = new TGIdentifier("ammo_full");
	public static TagKey<Item> TAG_AMMO_FULL;

	public static final Identifier TAG_AMMO_EMPTY_ID = new TGIdentifier("ammo_empty");
	public static TagKey<Item> TAG_AMMO_EMPTY;


	@Override
	public void init() {
		STONE_BULLETS = addItem("stonebullets", TGSlotType.AMMOSLOT);
		PISTOL_ROUNDS = addItem("pistolrounds", TGSlotType.AMMOSLOT);
		SHOTGUN_ROUNDS = addItem("shotgunrounds", TGSlotType.AMMOSLOT);
		RIFLE_ROUNDS = addItem("riflerounds", TGSlotType.AMMOSLOT);
		SNIPER_ROUNDS = addItem("sniperrounds", TGSlotType.AMMOSLOT);
		GRENADE_40MM = addItem("40mmgrenade", TGSlotType.AMMOSLOT);
		ADVANCED_ROUNDS = addItem("advancedrounds", TGSlotType.AMMOSLOT);
		ROCKET = addItem("rocket", true, TGSlotType.AMMOSLOT);
		RIFLE_ROUNDS_STACK = addItem("rifleroundsstack", TGSlotType.AMMOSLOT);

		SMG_MAGAZINE = addItem("smgmagazine", TGSlotType.AMMOSLOT);
		SMG_MAGAZINE_EMPTY = addItem("smgmagazineempty", TGSlotType.AMMOSLOT);
		PISTOL_MAGAZINE = addItem("pistolmagazine", TGSlotType.AMMOSLOT);
		PISTOL_MAGAZINE_EMPTY = addItem("pistolmagazineempty", TGSlotType.AMMOSLOT);
		ASSAULTRIFLE_MAGAZINE = addItem("assaultriflemagazine", true, TGSlotType.AMMOSLOT);
		ASSAULTRIFLE_MAGAZINE_EMPTY = addItem("assaultriflemagazineempty", true, TGSlotType.AMMOSLOT);
		LMG_MAGAZINE = addItem("lmgmagazine", true, TGSlotType.AMMOSLOT);
		LMG_MAGAZINE_EMPTY = addItem("lmgmagazineempty", true, TGSlotType.AMMOSLOT);
		MINIGUN_DRUM = addItem("minigundrum", TGSlotType.AMMOSLOT);
		MINIGUN_DRUM_EMPTY = addItem("minigundrumempty", TGSlotType.AMMOSLOT);
		AS50_MAGAZINE = addItem("as50magazine", true, TGSlotType.AMMOSLOT);
		AS50_MAGAZINE_EMPTY = addItem("as50magazineempty", true, TGSlotType.AMMOSLOT);

		ADVANCED_MAGAZINE = addItem("advancedmagazine", TGSlotType.AMMOSLOT);
		ADVANCED_MAGAZINE_EMPTY = addItem("advancedmagazineempty", TGSlotType.AMMOSLOT);

		COMPRESSED_AIR_TANK = addItem("compressedairtank", TGSlotType.AMMOSLOT);
		COMPRESSED_AIR_TANK_EMPTY = addItem("compressedairtankempty", TGSlotType.AMMOSLOT);

		BIO_TANK = addItem("biotank", TGSlotType.AMMOSLOT);
		BIO_TANK_EMPTY = addItem("biotankempty", TGSlotType.AMMOSLOT);

		FUEL_TANK = addItem("fueltank", TGSlotType.AMMOSLOT);
		FUEL_TANK_EMPTY = addItem("fueltankempty", TGSlotType.AMMOSLOT);

		ENERGY_CELL = addItem("energycell", TGSlotType.AMMOSLOT);
		ENERGY_CELL_EMPTY = addItem("energycellempty", TGSlotType.AMMOSLOT);

		NUCLEAR_POWERCELL = addItem("nuclearpowercell", TGSlotType.AMMOSLOT);
		NUCLEAR_POWERCELL_EMPTY = addItem("nuclearpowercelldepleted", TGSlotType.AMMOSLOT);

		REDSTONE_BATTERY = addItem("redstone_battery", TGSlotType.AMMOSLOT);
		REDSTONE_BATTERY_EMPTY = addItem("redstone_battery_empty", TGSlotType.AMMOSLOT);

		GAUSSRIFLE_SLUGS = addItem("gaussrifleslugs", TGSlotType.AMMOSLOT);

		NETHER_CHARGE = addItem("nethercharge", TGSlotType.AMMOSLOT);

		MAGIC_BULLET = addItem("magic_bullet", TGSlotType.AMMOSLOT);
		MAGIC_BULLET_FIRE = addItem("magic_bullet_fire", TGSlotType.AMMOSLOT);
		MAGIC_BULLET_LIGHTNING = addItem("magic_bullet_lightning", TGSlotType.AMMOSLOT);

		/**
		 * GUNPARTS
		 */
		RECEIVER_IRON = addItem("ironreceiver");
		RECEIVER_STEEL = addItem("steelreceiver");
		RECEIVER_OBSIDIAN_STEEL = addItem("obsidiansteelreceiver");
		RECEIVER_CARBON = addItem("carbonreceiver");

		BARREL_STONE = addItem("stonebarrel");
		BARREL_IRON = addItem("ironbarrel");
		//BARREL_STEEL = addItem("steelbarrel");
		BARREL_OBSIDIAN_STEEL = addItem("obsidiansteelbarrel");
		BARREL_CARBON = addItem("carbonbarrel");
		BARREL_LASER = addItem("laserbarrel");

		STOCK_WOOD = addItem("woodstock");
		STOCK_PLASTIC = addItem("plasticstock");
		STOCK_CARBON = addItem("carbonstock");

		STEAMARMOR_PLATE = addItem("steamarmorplate");
		/*PLATE_IRON =  addItem("plateiron");
		PLATE_COPPER =  addItem("platecopper");
		PLATE_TIN =  addItem("platetin");
		PLATE_BRONZE =  addItem("platebronze");
		PLATE_STEEL =  addItem("platesteel");
		PLATE_OBSIDIAN_STEEL=  addItem("plateobsidiansteel");
		PLATE_LEAD =  addItem("platelead");
		PLATE_TITANIUM =  addItem("platetitanium");*/
		PLATE_CARBON =  addItem("platecarbon");

		PLASTIC_SHEET =  addItem("plasticsheet");
		RUBBER_BAR =  addItem("rubberbar");

		MECHANICAL_PARTS_IRON = addItem("mechanicalpartsiron");
		MECHANICAL_PARTS_OBSIDIAN_STEEL = addItem("mechanicalpartsobsidiansteel");
		MECHANICAL_PARTS_CARBON = addItem("mechanicalpartscarbon");

		HEAVY_CLOTH = addItem("heavycloth");
		BIOMASS = addItem("biomass");

		WIRE_COPPER = addItem("copperwire");
		WIRE_GOLD = addItem("goldwire");
		CARBON_FIBERS = addItem("carbonfibers");

		CIRCUIT_BOARD_BASIC = addItem("circuitboard");
		CIRCUIT_BOARD_ADVANCED = addItem("circuitboardadvanced");
		CIRCUIT_BOARD_ELITE = addItem("circuitboardelite");

		POWER_ARMOR_PLATING = addItem("powerplating");
		POWER_ARMOR_PLATING_MK2 = addItem("powerplating_mk2");
		NETHER_ARMOR_PLATING = addItem("netherarmorplating");

		COIL = addItem("coil");
		CYBERNETIC_PARTS = addItem("cyberneticparts");
		ELECTRIC_ENGINE = addItem("electricengine");

		LASER_FOCUS = addItem("laserfocus");
		PUMP_MECHANISM = addItem("pumpmechanism");
		RAD_EMITTER = addItem("rademitter");
		SONIC_EMITTER = addItem("sonicemitter");

		TGX = addItem("tgx");
		TREATED_LEATHER = addItem("treatedleather");

		TIN_INGOT = addItemOptional("tin_ingot",TGConfig.INSTANCE.addTinIngots);
		BRONZE_INGOT = addItemOptional("bronze_ingot",TGConfig.INSTANCE.addBronzeIngots);
		LEAD_INGOT = addItemOptional("lead_ingot",TGConfig.INSTANCE.addLeadIngots);
		RAW_STEEL_INGOT = addItemOptional("raw_steel_ingot", TGConfig.INSTANCE.addSteelIngots);
		STEEL_INGOT = addItemOptional("steel_ingot",TGConfig.INSTANCE.addSteelIngots);
		OBSIDIAN_STEEL_INGOT = addItemOptional("obsidian_steel_ingot", TGConfig.INSTANCE.addObsidianIngots);
		RAW_OBSIDIAN_STEEL_INGOT = addItemOptional("raw_obsidian_steel_ingot", TGConfig.INSTANCE.addObsidianIngots);
		TITANIUM_INGOT = addItemOptional("titanium_ingot", TGConfig.INSTANCE.addTitaniumIngots);

		COPPER_NUGGET = addItemOptional("copper_nugget",TGConfig.INSTANCE.addCopperNuggets);
		//NUGGET_LEAD = addItemOptional("nuggetlead", TGConfig.INSTANCE.addLeadNuggets);
		STEEL_NUGGET = addItemOptional("steel_nugget", TGConfig.INSTANCE.addSteelNuggets);

		RAW_TIN = addItemOptional("raw_tin", TGConfig.INSTANCE.addTinOre);
		RAW_LEAD = addItemOptional("raw_lead", TGConfig.INSTANCE.addLeadOre);
		RAW_TITANIUM = addItemOptional("raw_titanium", TGConfig.INSTANCE.addTitaniumOre);
		RAW_URANIUM = addItemOptional("raw_uranium", TGConfig.INSTANCE.addUraniumOre);

		GAS_MASK_FILTER = addItem("gasmaskfilter");
		GLIDER_BACKBACK = addItem("gliderbackpack");
		GLIDER_WING = addItem("gliderwing");
		ANTI_GRAV_CORE = addItem("antigravcore");

		OXYGEN_MASK = addItem("oxygenmask", false, TGSlotType.FACESLOT, 1, true);

		//MACHINE_UPGRADE_STACK = addItem("machinestackupgrade", false, TGSlotType.NORMAL, 7, true);

		RAW_RUBBER = addItem("rawrubber");
		RAW_PLASTIC = addItem("rawplastic");
		YELLOWCAKE = addItem("yellowcake");
		ENRICHED_URANIUM = addItem("enricheduranium");

		TURRET_ARMOR_IRON = addItem("turretarmoriron",TGSlotType.TURRETARMOR);
		TURRET_ARMOR_STEEL = addItem("turretarmorsteel",TGSlotType.TURRETARMOR);
		TURRET_ARMOR_OBSIDIAN_STEEL = addItem("turretarmorobsidiansteel",TGSlotType.TURRETARMOR);
		TURRET_ARMOR_CARBON = addItem("turretarmorcarbon",TGSlotType.TURRETARMOR);

		//QUARTZ_ROD = addItem("quartzrod");
		//RC_HEAT_RAY = addItem("rcheatray",false,TGSlotType.REACTION_CHAMBER_FOCUS,1,true);
		//RC_UV_EMITTER = addItem("rcuvemitter",false,TGSlotType.REACTION_CHAMBER_FOCUS,1,true);

		SHOTGUN_ROUNDS_INCENDIARY = addItem("shotgunrounds_incendiary", TGSlotType.AMMOSLOT);
		AS50_MAGAZINE_INCENDIARY = addItem("as50magazine_incendiary", true, TGSlotType.AMMOSLOT);
		SNIPER_ROUNDS_INCENDIARY = addItem("sniperrounds_incendiary",TGSlotType.AMMOSLOT);

		PISTOL_ROUNDS_INCENDIARY = addItem("pistolrounds_incendiary",TGSlotType.AMMOSLOT);
		RIFLE_ROUNDS_INCENDIARY = addItem("riflerounds_incendiary",TGSlotType.AMMOSLOT);
		RIFLE_ROUNDS_STACK_INCENDIARY = addItem("rifleroundsstack_incendiary",TGSlotType.AMMOSLOT);

		SMG_MAGAZINE_INCENDIARY = addItem("smgmagazine_incendiary",TGSlotType.AMMOSLOT);
		PISTOL_MAGAZINE_INCENDIARY = addItem("pistolmagazine_incendiary",TGSlotType.AMMOSLOT);
		MINIGUN_DRUM_INCENDIARY = addItem("minigundrum_incendiary", TGSlotType.AMMOSLOT);
		ASSAULTRIFLE_MAGAZINE_INCENDIARY = addItem("assaultriflemagazine_incendiary",true, TGSlotType.AMMOSLOT);
		LMG_MAGAZINE_INCENDIARY = addItem("lmgmagazine_incendiary",true, TGSlotType.AMMOSLOT);

		ROCKET_NUKE = addItem("rocket_nuke", true, TGSlotType.AMMOSLOT);
		TACTICAL_NUKE_WARHEAD = addItem("tacticalnukewarhead");

		MININGDRILLHEAD_OBSIDIAN = addItem("miningdrillhead_obsidian");
		MININGDRILLHEAD_CARBON = addItem("miningdrillhead_carbon");

		POWERHAMMERHEAD_STEEL = addItem("powerhammerhead_steel");
		POWERHAMMERHEAD_OBSIDIAN = addItem("powerhammerhead_obsidian");
		POWERHAMMERHEAD_CARBON = addItem("powerhammerhead_carbon");

		CHAINSAWBLADES_STEEL = addItem("chainsawblades_steel");
		CHAINSAWBLADES_OBSIDIAN = addItem("chainsawblades_obsidian");
		CHAINSAWBLADES_CARBON = addItem("chainsawblades_carbon");

		BARREL_GAUSS = addItem("gaussbarrel");

		BARREL_TITANIUM =  addItem("shieldedtitaniumbarrel");
		RECEIVER_TITANIUM =  addItem("titaniumreceiver");
		PLASMA_GENERATOR =  addItem("plasmagenerator");

		PROTECTIVE_FIBER = addItem("protectivefiber");

		//WORKING_GLOVES = addItem("workinggloves", false, TGSlotType.HANDSLOT, 1, true);

		/*OREDRILLHEAD_STEEL = addItem("oredrillsmall_steel", false, TGSlotType.DRILL_SMALL, 1, true);
		OREDRILLHEAD_OBSIDIANSTEEL = addItem("oredrillsmall_obsidiansteel", false, TGSlotType.DRILL_SMALL, 1, true);
		OREDRILLHEAD_CARBON = addItem("oredrillsmall_carbon", false, TGSlotType.DRILL_SMALL, 1, true);

		OREDRILLHEAD_MEDIUM_STEEL = addItem("oredrillmedium_steel", false, TGSlotType.DRILL_MEDIUM, 1, true);
		OREDRILLHEAD_MEDIUM_OBSIDIANSTEEL = addItem("oredrillmedium_obsidiansteel", false, TGSlotType.DRILL_MEDIUM, 1, true);
		OREDRILLHEAD_MEDIUM_CARBON = addItem("oredrillmedium_carbon", false, TGSlotType.DRILL_MEDIUM, 1, true);

		OREDRILLHEAD_LARGE_STEEL = addItem("oredrilllarge_steel", false, TGSlotType.DRILL_LARGE, 1, true);
		OREDRILLHEAD_LARGE_OBSIDIANSTEEL = addItem("oredrilllarge_obsidiansteel", false, TGSlotType.DRILL_LARGE, 1, true);
		OREDRILLHEAD_LARGE_CARBON = addItem("oredrilllarge_carbon", false, TGSlotType.DRILL_LARGE, 1, true);*/

		AS50_MAGAZINE_EXPLOSIVE = addItem("as50magazine_explosive", true, TGSlotType.AMMOSLOT);
		SNIPER_ROUNDS_EXPLOSIVE = addItem("sniperrounds_explosive",TGSlotType.AMMOSLOT);

		ROCKET_HIGH_VELOCITY = addItem("rocket_high_velocity",true,TGSlotType.AMMOSLOT);

		INFUSION_BAG = addItem("infusionbag");

		AMMO_BOX = Registry.register(Registry.ITEM, new TGIdentifier("ammo_box"), new AmmoBagItem(new Item.Settings(), 64*4));
		AMMO_BOX_LARGE = Registry.register(Registry.ITEM, new TGIdentifier("ammo_box_large"), new AmmoBagItem(new Item.Settings(), 64*8));

		SPAWN_EGG_ZOMBIE_SOLDIER = Registry.register(Registry.ITEM, new TGIdentifier("zombie_soldier_spawn_egg"), new SpawnEggItem(TGEntities.ZOMBIE_SOLDIER, 0x757468, 0x38B038, new Item.Settings().group(ITEM_GROUP_TECHGUNS)));
		SPAWN_EGG_ZOMBIE_MINER = Registry.register(Registry.ITEM, new TGIdentifier("zombie_miner_spawn_egg"), new SpawnEggItem(TGEntities.ZOMBIE_MINER, 0x757468, 0x38B038, new Item.Settings().group(ITEM_GROUP_TECHGUNS)));
		SPAWN_EGG_ZOMBIE_FARMER = Registry.register(Registry.ITEM, new TGIdentifier("zombie_farmer_spawn_egg"), new SpawnEggItem(TGEntities.ZOMBIE_FARMER, 0x757468, 0x38B038, new Item.Settings().group(ITEM_GROUP_TECHGUNS)));
		SPAWN_EGG_ZOMBIE_POLICEMAN = Registry.register(Registry.ITEM, new TGIdentifier("zombie_policeman_spawn_egg"), new SpawnEggItem(TGEntities.ZOMBIE_POLICEMAN, 0x303030, 0x0000FF, new Item.Settings().group(ITEM_GROUP_TECHGUNS)));
		SPAWN_EGG_ARMY_SOLDIER = Registry.register(Registry.ITEM, new TGIdentifier("army_soldier_spawn_egg"), new SpawnEggItem(TGEntities.ARMY_SOLDIER, 0x74806e, 0x191512, new Item.Settings().group(ITEM_GROUP_TECHGUNS)));

		/*	UPGRADE_PROTECTION_1 = addItem("upgrade_protection_1", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		UPGRADE_PROJECTILE_PROTECTION_1 = addItem("upgrade_projectile_protection_1", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		UPGRADE_BLAST_PROTECTION_1 = addItem("upgrade_blast_protection_1", false, TGSlotType.ARMOR_UPGRADE, 1, true);

		UPGRADE_PROTECTION_2 = addItem("upgrade_protection_2", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		UPGRADE_PROJECTILE_PROTECTION_2 = addItem("upgrade_projectile_protection_2", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		UPGRADE_BLAST_PROTECTION_2 = addItem("upgrade_blast_protection_2", false, TGSlotType.ARMOR_UPGRADE, 1, true);

		UPGRADE_PROTECTION_3 = addItem("upgrade_protection_3", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		UPGRADE_PROJECTILE_PROTECTION_3 = addItem("upgrade_projectile_protection_3", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		UPGRADE_BLAST_PROTECTION_3 = addItem("upgrade_blast_protection_3", false, TGSlotType.ARMOR_UPGRADE, 1, true);
*/

		/*TAG_BULLET_CORE = TagRegistry.item(TAG_BULLET_CORE_ID);
		TAG_BULLET_CASING = TagRegistry.item(TAG_BULLET_CASING_ID);
		TAG_BULLET_POWDER = TagRegistry.item(TAG_BULLET_POWDER_ID);

		TAG_AMMO = TagRegistry.item(TAG_AMMO_ID);
		TAG_AMMO_EMPTY = TagRegistry.item(TAG_AMMO_EMPTY_ID);
		TAG_AMMO_FULL = TagRegistry.item(TAG_AMMO_FULL_ID);*/


		TAG_BULLET_CORE = TagKey.of(Registry.ITEM_KEY, TAG_BULLET_CORE_ID);
		TAG_BULLET_CASING = TagKey.of(Registry.ITEM_KEY, TAG_BULLET_CASING_ID);
		TAG_BULLET_POWDER = TagKey.of(Registry.ITEM_KEY, TAG_BULLET_POWDER_ID);

		TAG_AMMO = TagKey.of(Registry.ITEM_KEY, TAG_AMMO_ID);
		TAG_AMMO_EMPTY = TagKey.of(Registry.ITEM_KEY, TAG_AMMO_EMPTY_ID);
		TAG_AMMO_FULL = TagKey.of(Registry.ITEM_KEY, TAG_AMMO_FULL_ID);

		SlotTagItem.initSlotBGMap();
	}

	public Item addItem(String name){
		return addItem(name, false);
	}

	protected static Item addItem(String name, boolean useRenderHack){
		return addItem(name, useRenderHack, TGSlotType.NORMAL);
	}

	protected static Item addItem(String name, TGSlotType slottype){
		return addItem(name, false, slottype);
	}

	protected static Item addItem(String name, boolean useRenderHack, TGSlotType slottype){
		return addItem(name, useRenderHack,slottype,64,true);
	}

	protected static Item addItemOptional(String name, boolean enabled) {
		return addItem(name, false, TGSlotType.NORMAL, 64, enabled);
	}

	protected static Item addItem(String name, boolean useRenderHack, TGSlotType slottype, int maxStackSize, boolean enabled){
		if (enabled){
			GenericItem item = useRenderHack ? new GenericItemRenderHack(new Item.Settings()) : new GenericItem(new Item.Settings());
			Registry.register(Registry.ITEM, new TGIdentifier(name), item);
			return item;
		} else {
			return null;
		}
	}

	public static ItemStack newStack(ItemStack stack, int size) {
		ItemStack ret = stack.copy();
		ret.setCount(size);
		return ret;
	}

}
