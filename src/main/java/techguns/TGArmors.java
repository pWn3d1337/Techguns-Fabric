package techguns;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;
import techguns.items.armors.ArmorPowerType;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.PoweredArmor;
import techguns.items.armors.TGArmorMaterial;

public class TGArmors implements ITGInitializer{
    public static TGArmorMaterial T1_COMBAT;
    public static GenericArmor T1_COMBAT_HELMET;
    public static GenericArmor T1_COMBAT_CHESTPLATE;
    public static GenericArmor T1_COMBAT_LEGGINGS;
    public static GenericArmor T1_COMBAT_BOOTS;

    public static TGArmorMaterial T1_SCOUT;
    public static GenericArmor T1_SCOUT_HELMET;
    public static GenericArmor T1_SCOUT_CHESTPLATE;
    public static GenericArmor T1_SCOUT_LEGGINGS;
    public static GenericArmor T1_SCOUT_BOOTS;

    public static TGArmorMaterial T1_MINER;
    public static GenericArmor T1_MINER_HELMET;
    public static GenericArmor T1_MINER_CHESTPLATE;
    public static GenericArmor T1_MINER_LEGGINGS;
    public static GenericArmor T1_MINER_BOOTS;

    public static TGArmorMaterial T1_STEAM;
    public static GenericArmor T1_STEAM_HELMET;
    public static GenericArmor T1_STEAM_CHESTPLATE;
    public static GenericArmor T1_STEAM_LEGGINGS;
    public static GenericArmor T1_STEAM_BOOTS;

    public static TGArmorMaterial T2_HAZMAT;
    public static GenericArmor T2_HAZMAT_HELMET;
    public static GenericArmor T2_HAZMAT_CHESTPLATE;
    public static GenericArmor T2_HAZMAT_LEGGINGS;
    public static GenericArmor T2_HAZMAT_BOOTS;

    public static TGArmorMaterial T2_COMBAT;
    public static GenericArmor T2_COMBAT_HELMET;
    public static GenericArmor T2_COMBAT_CHESTPLATE;
    public static GenericArmor T2_COMBAT_LEGGINGS;
    public static GenericArmor T2_COMBAT_BOOTS;

    public static TGArmorMaterial T2_COMMANDO;
    public static GenericArmor T2_COMMANDO_HELMET;
    public static GenericArmor T2_COMMANDO_CHESTPLATE;
    public static GenericArmor T2_COMMANDO_LEGGINGS;
    public static GenericArmor T2_COMMANDO_BOOTS;

    public static TGArmorMaterial T2_RIOT;
    public static GenericArmor T2_RIOT_HELMET;
    public static GenericArmor T2_RIOT_CHESTPLATE;
    public static GenericArmor T2_RIOT_LEGGINGS;
    public static GenericArmor T2_RIOT_BOOTS;

    public static TGArmorMaterial T2_BERET;

    public static TGArmorMaterial T3_COMBAT;
    public static GenericArmor T3_COMBAT_HELMET;
    public static GenericArmor T3_COMBAT_CHESTPLATE;
    public static GenericArmor T3_COMBAT_LEGGINGS;
    public static GenericArmor T3_COMBAT_BOOTS;

    public static TGArmorMaterial T3_EXO;
    public static GenericArmor T3_EXO_HELMET;
    public static GenericArmor T3_EXO_CHESTPLATE;
    public static GenericArmor T3_EXO_LEGGINGS;
    public static GenericArmor T3_EXO_BOOTS;

    public static TGArmorMaterial T3_POWER;
    public static GenericArmor T3_POWER_HELMET;
    public static GenericArmor T3_POWER_CHESTPLATE;
    public static GenericArmor T3_POWER_LEGGINGS;
    public static GenericArmor T3_POWER_BOOTS;

    public static TGArmorMaterial T3_MINER;
    public static GenericArmor T3_MINER_HELMET;
    public static GenericArmor T3_MINER_CHESTPLATE;
    public static GenericArmor T3_MINER_LEGGINGS;
    public static GenericArmor T3_MINER_BOOTS;

    public static TGArmorMaterial T4_PRAETOR;
    public static GenericArmor T4_PRAETOR_HELMET;
    public static GenericArmor T4_PRAETOR_CHESTPLATE;
    public static GenericArmor T4_PRAETOR_LEGGINGS;
    public static GenericArmor T4_PRAETOR_BOOTS;

    public static TGArmorMaterial T4_POWER;
    public static GenericArmor T4_POWER_HELMET;
    public static GenericArmor T4_POWER_CHESTPLATE;
    public static GenericArmor T4_POWER_LEGGINGS;
    public static GenericArmor T4_POWER_BOOTS;

    /**
     * Needs to be higher than maximum consumption per tick, so bonuses don't flicker on/off
     */
    public static final int POWER_VALUE_MINOR_SLOTS = 100;

    @Override
    public void init() {
        T1_COMBAT = new TGArmorMaterial("t1_combat", 60, 15F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0.5F, 0.1F, TGItems.HEAVY_CLOTH, Items.IRON_INGOT);
        T1_SCOUT = new TGArmorMaterial("t1_scout", 60, 13F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0F, 0F, TGItems.HEAVY_CLOTH);
        T1_MINER = new TGArmorMaterial("t1_miner", 60, 13F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0F, 0F, TGItems.HEAVY_CLOTH, Items.IRON_INGOT);
        T1_STEAM = new TGArmorMaterial("t1_steam", 200, 19F, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON,1F, 0.15F, TGItems.BRONZE_INGOT, TGItems.STEEL_INGOT).setArmorElemental(17.0f).setArmorExplosion(18.0f).setArmorPoison(10.0f).setArmorRadiation(6.0f);

        T2_HAZMAT =  new TGArmorMaterial("t2_hazmat", 80, 10F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0F, 0.0F, TGItems.RUBBER_BAR).setArmorElemental(16.0f).setArmorExplosion(10.0f).setArmorPoison(20.0f).setArmorRadiation(20.0f);
        T2_BERET =  new TGArmorMaterial("t2_beret", 60, 8F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0F, 0.0F, TGItems.HEAVY_CLOTH);

        T2_COMBAT = new TGArmorMaterial("t2_combat", 72, 18F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,1F, 0.15F, TGItems.HEAVY_CLOTH, TGItems.STEEL_INGOT);
        T2_COMMANDO = new TGArmorMaterial("t2_commando", 72, 18F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,1F, 0.15F, TGItems.HEAVY_CLOTH, TGItems.STEEL_INGOT).setArmorElemental(16.0f).setArmorExplosion(16.0f).setArmorPoison(10.0f).setArmorRadiation(5.0f);;
        T2_RIOT = new TGArmorMaterial("t2_riot", 96, 18.5F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,1.5F, 0.15F, TGItems.TREATED_LEATHER).setArmorFire(18.0f).setArmorExplosion(18.0f).setArmorEnergy(18.0f).setArmorIce(17.0f).setArmorLightning(17.0f).setArmorPoison(16.0f).setArmorRadiation(16.0f);
        T3_COMBAT = new TGArmorMaterial("t3_combat", 240, 21F, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,2F, 0.2F, TGItems.PLATE_CARBON).setArmorFire(18.0f).setArmorExplosion(18.0f).setArmorEnergy(19.0f).setArmorIce(18.0f).setArmorLightning(18.0f).setArmorPoison(10.0f).setArmorRadiation(12.0f);
        T3_EXO = new TGArmorMaterial("t3_exo", 300, 21F, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,2F, 0.2F, TGItems.PLATE_CARBON).setArmorFire(18.0f).setArmorExplosion(18.0f).setArmorEnergy(19.0f).setArmorIce(18.0f).setArmorLightning(18.0f).setArmorPoison(10.0f).setArmorRadiation(12.0f);
        T3_POWER = new TGArmorMaterial("t3_power", 360, 22F, 0, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,2.5F, 0.25F, TGItems.POWER_ARMOR_PLATING).setArmorFire(19.0f).setArmorExplosion(21.0f).setArmorEnergy(20.0f).setArmorIce(19.0f).setArmorLightning(19.0f).setArmorPoison(15.0f).setArmorRadiation(17.0f);
        T3_MINER = new TGArmorMaterial("t3_miner", 300, 20F, 0, SoundEvents.ITEM_ARMOR_EQUIP_GOLD,2F, 0.2F, TGItems.PLATE_CARBON).setArmorFire(20.0f).setArmorExplosion(20.0f).setArmorEnergy(20.0f).setArmorIce(20.0f).setArmorLightning(20.0f).setArmorPoison(20.0f).setArmorRadiation(20.0f);


        T4_PRAETOR = new TGArmorMaterial("t4_praetor", 360, 22F, 0, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,3F, 0.25F, TGItems.POWER_ARMOR_PLATING).setArmorFire(21.0f).setArmorExplosion(21.0f).setArmorEnergy(21.0f).setArmorIce(21.0f).setArmorLightning(21.0f).setArmorPoison(21.0f).setArmorRadiation(20.0f);
        T4_POWER = new TGArmorMaterial("t4_power", 460, 23F, 0, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,3.5F, 0.25F, TGItems.POWER_ARMOR_PLATING).setArmorFire(21.0f).setArmorExplosion(22.0f).setArmorEnergy(21.0f).setArmorIce(21.0f).setArmorLightning(21.0f).setArmorPoison(19.0f).setArmorRadiation(20.0f);


        T1_COMBAT_HELMET = registerArmor(T1_COMBAT, EquipmentSlot.HEAD).setSpeedBoni(0.125f,0.02f);
        T1_COMBAT_CHESTPLATE = registerArmor(T1_COMBAT, EquipmentSlot.CHEST).setSpeedBoni(0.125f,0.02f);
        T1_COMBAT_LEGGINGS = registerArmor(T1_COMBAT, EquipmentSlot.LEGS).setSpeedBoni(0.125f,0.02f);
        T1_COMBAT_BOOTS = registerArmor(T1_COMBAT, EquipmentSlot.FEET).setSpeedBoni(0.125f,0.1f).setFallProtection(0.2f, 1.0f);

        T1_SCOUT_HELMET = registerArmor2d(T1_SCOUT, EquipmentSlot.HEAD).setSpeedBoni(0.125f,0.02f);
        T1_SCOUT_CHESTPLATE = registerArmor2d(T1_SCOUT, EquipmentSlot.CHEST).setSpeedBoni(0.125f,0.02f);
        T1_SCOUT_LEGGINGS = registerArmor2d(T1_SCOUT, EquipmentSlot.LEGS).setSpeedBoni(0.125f,0.02f);
        T1_SCOUT_BOOTS = registerArmor2d(T1_SCOUT, EquipmentSlot.FEET).setSpeedBoni(0.125f,0.1f).setFallProtection(0.2f, 1.0f);

        T1_MINER_HELMET = registerArmor2d(T1_MINER, EquipmentSlot.HEAD).setSpeedBoni(0.08f,0.00f).setMiningBoni(0.05f);
        T1_MINER_CHESTPLATE = registerArmor2d(T1_MINER, EquipmentSlot.CHEST).setSpeedBoni(0.08f,0.0f).setMiningBoni(0.05f);
        T1_MINER_LEGGINGS = registerArmor2d(T1_MINER, EquipmentSlot.LEGS).setSpeedBoni(0.08f,0.0f).setMiningBoni(0.05f);
        T1_MINER_BOOTS = registerArmor2d(T1_MINER, EquipmentSlot.FEET).setSpeedBoni(0.08f,0.1f).setMiningBoni(0.05f).setFallProtection(0.2f, 1.0f);

        T1_STEAM_HELMET = registerArmor3d(T1_STEAM, EquipmentSlot.HEAD);
        T1_STEAM_CHESTPLATE = registerArmor3d(T1_STEAM, EquipmentSlot.CHEST);
        T1_STEAM_LEGGINGS = registerArmor3d(T1_STEAM, EquipmentSlot.LEGS);
        T1_STEAM_BOOTS = registerArmor3d(T1_STEAM, EquipmentSlot.FEET);

        T2_COMBAT_HELMET = registerArmor2d(T2_COMBAT, EquipmentSlot.HEAD).setSpeedBoni(0.1f,0.0f);
        T2_COMBAT_CHESTPLATE = registerArmor2d(T2_COMBAT, EquipmentSlot.CHEST).setSpeedBoni(0.1f,0.0f);
        T2_COMBAT_LEGGINGS = registerArmor2d(T2_COMBAT, EquipmentSlot.LEGS).setSpeedBoni(0.1f,0.0f);
        T2_COMBAT_BOOTS = registerArmor2d(T2_COMBAT, EquipmentSlot.FEET).setSpeedBoni(0.1f,0.1f);

        T2_RIOT_HELMET = registerArmor2d(T2_RIOT, EquipmentSlot.HEAD).setSpeedBoni(0.12f,0.f);
        T2_RIOT_CHESTPLATE = registerArmor2d(T2_RIOT, EquipmentSlot.CHEST).setSpeedBoni(0.12f, 0.f);
        T2_RIOT_LEGGINGS = registerArmor2d(T2_RIOT, EquipmentSlot.LEGS).setSpeedBoni(0.13f, 0.f);
        T2_RIOT_BOOTS = registerArmor2d(T2_RIOT, EquipmentSlot.FEET).setSpeedBoni(0.13f, 0.15f).setFallProtection(0.2f, 0.5f);

        T2_HAZMAT_HELMET = registerArmor2d(T2_HAZMAT, EquipmentSlot.HEAD);
        T2_HAZMAT_CHESTPLATE = registerArmor2d(T2_HAZMAT, EquipmentSlot.CHEST);
        T2_HAZMAT_LEGGINGS = registerArmor2d(T2_HAZMAT, EquipmentSlot.LEGS);
        T2_HAZMAT_BOOTS = registerArmor2d(T2_HAZMAT, EquipmentSlot.FEET).setFallProtection(0.2f, 0.5f);

        T2_COMMANDO_HELMET = registerArmor2d(T2_COMMANDO, EquipmentSlot.HEAD).setSpeedBoni(0.10f,0.f).setSpeedBoni(0.10f,0f).setMiningBoniWater(1.25f).setGunBonus(0.05f).setOxygenGear(1.0f);
        T2_COMMANDO_CHESTPLATE = registerArmor2d(T2_COMMANDO, EquipmentSlot.CHEST).setSpeedBoni(0.1f,0.0f).setSpeedBoni(0.10f,0f).setMiningBoniWater(1.25f).setGunBonus(0.05f);
        T2_COMMANDO_LEGGINGS = registerArmor2d(T2_COMMANDO, EquipmentSlot.LEGS).setSpeedBoni(0.1f,0.0f).setSpeedBoni(0.10f,0.f).setMiningBoniWater(1.25f).setGunBonus(0.05f);
        T2_COMMANDO_BOOTS = registerArmor2d(T2_COMMANDO, EquipmentSlot.FEET).setSpeedBoni(0.1f,0.1f).setSpeedBoni(0.10f,0.1f).setMiningBoniWater(1.25f).setGunBonus(0.05f).setFallProtection(0.2f, 1.0f);

        T3_COMBAT_HELMET = registerArmor2d(T3_COMBAT, EquipmentSlot.HEAD).setSpeedBoni(0.1f,0.0f);
        T3_COMBAT_CHESTPLATE = registerArmor2d(T3_COMBAT, EquipmentSlot.CHEST).setSpeedBoni(0.1f,0.0f);
        T3_COMBAT_LEGGINGS = registerArmor2d(T3_COMBAT, EquipmentSlot.LEGS).setSpeedBoni(0.1f,0.0f);
        T3_COMBAT_BOOTS = registerArmor2d(T3_COMBAT, EquipmentSlot.FEET).setSpeedBoni(0.1f,0.1f);

        T3_EXO_HELMET = registerArmor2d(T3_EXO, EquipmentSlot.HEAD);
        T3_EXO_CHESTPLATE = registerArmor2d(T3_EXO, EquipmentSlot.CHEST);
        T3_EXO_LEGGINGS = registerArmor2d(T3_EXO, EquipmentSlot.LEGS);
        T3_EXO_BOOTS = registerArmor2d(T3_EXO, EquipmentSlot.FEET);

        T3_POWER_HELMET = registerArmorPowered3d(T3_POWER, EquipmentSlot.HEAD, ArmorPowerType.RF, POWER_VALUE_MINOR_SLOTS)
                .setSpeedBoni(0.05f,0.03f,0.0f,0.0f)
                .setMiningBoni(0.05f,0.0f)
                .setHealthBonus(1,0)
                .setRADResistance(0.75f);

        T3_POWER_CHESTPLATE = registerArmorPowered3d(T3_POWER, EquipmentSlot.CHEST, ArmorPowerType.RF, 3600)
                .setSpeedBoni(0.05f,0.03f,0.0f,0.0f)
                .setMiningBoni(0.05f,0.0f)
                .setHealthBonus(2,0)
                .setBattery(TGItems.ENERGY_CELL).setEmptyBattery(TGItems.ENERGY_CELL_EMPTY)
                .setRADResistance(0.75f);

        T3_POWER_LEGGINGS = registerArmorPowered3d(T3_POWER, EquipmentSlot.LEGS, ArmorPowerType.RF, POWER_VALUE_MINOR_SLOTS)
                .setSpeedBoni(0.05f,0.03f,0f,0f)
                .setMiningBoni(0.05f,0f)
                .setHealthBonus(1,0)
                .setRADResistance(0.75f);

        T3_POWER_BOOTS = registerArmorPowered3d(T3_POWER, EquipmentSlot.FEET, ArmorPowerType.RF, POWER_VALUE_MINOR_SLOTS)
                .setSpeedBoni(0.05f,0.15f,0,0)
                .setMiningBoni(0.05f,0)
                .setFallProtection(0.2f, 1.0f,0,0)
                .setHealthBonus(1,0)
                .setStepAssist(1.0f,0)
                .setRADResistance(0.75f);

        T3_MINER_HELMET = registerArmorPowered2d(T3_MINER, EquipmentSlot.HEAD, ArmorPowerType.RF, POWER_VALUE_MINOR_SLOTS)
                .setSpeedBoni(0.1f,0.3f,0,0)
                .setWaterspeedBonus(1.25f, 0)
                .setMiningBoni(0.1f,0)
                .setMiningBoniWater(1.25f,0)
                .setOxygenGear(1.0f,0.0f)
                .setRADResistance(1f);

        T3_MINER_CHESTPLATE = registerArmorPowered2d(T3_MINER, EquipmentSlot.CHEST, ArmorPowerType.RF, 10000)
                .setBattery(TGItems.ENERGY_CELL).setEmptyBattery(TGItems.ENERGY_CELL_EMPTY)
                .setSpeedBoni(0.1f, 0.03f,0,0)
                .setWaterspeedBonus(1.25f, 0)
                .setMiningBoni(0.1f,0)
                .setMiningBoniWater(1.25f,0)
                .setCoolingSystem(1.0f,0)
                .setRADResistance(1f);

        T3_MINER_LEGGINGS = registerArmorPowered2d(T3_MINER, EquipmentSlot.LEGS, ArmorPowerType.RF, POWER_VALUE_MINOR_SLOTS)
                .setSpeedBoni(0.1f, 0.03f,0,0)
                .setWaterspeedBonus(1.25f, 0)
                .setMiningBoni(0.1f,0)
                .setMiningBoniWater(1.25f,0)
                .setRADResistance(1f);

        T3_MINER_BOOTS = registerArmorPowered2d(T3_MINER, EquipmentSlot.FEET, ArmorPowerType.RF, POWER_VALUE_MINOR_SLOTS)
                .setSpeedBoni(0.1f, 0.15f,0,0)
                .setWaterspeedBonus(1.25f, 0)
                .setMiningBoni(0.1f,0)
                .setMiningBoniWater(1.25f,0)
                .setStepAssist(1.0f,0)
                .setFallProtection(0.5f, 3.0f,0,0)
                .setRADResistance(1f);


        T4_PRAETOR_HELMET = registerArmorPowered2d(T4_PRAETOR, EquipmentSlot.HEAD, ArmorPowerType.RF, POWER_VALUE_MINOR_SLOTS)
                .setSpeedBoni(0.1f,0.3f,0,0)
                .setWaterspeedBonus(1.25f, 0)
                .setMiningBoni(0.1f,0)
                .setMiningBoniWater(1.25f,0)
                .setOxygenGear(1.0f,0.0f)
                .setRADResistance(1.5f);

        T4_PRAETOR_CHESTPLATE = registerArmorPowered2d(T4_PRAETOR, EquipmentSlot.CHEST, ArmorPowerType.RF, 20000)
                .setBattery(TGItems.NUCLEAR_POWERCELL).setEmptyBattery(TGItems.NUCLEAR_POWERCELL_EMPTY)
                .setSpeedBoni(0.1f, 0.03f,0,0)
                .setWaterspeedBonus(1.25f, 0)
                .setMiningBoni(0.1f,0)
                .setMiningBoniWater(1.25f,0)
                .setCoolingSystem(1.0f,0)
                .setRADResistance(1.5f);

        T4_PRAETOR_LEGGINGS = registerArmorPowered2d(T4_PRAETOR, EquipmentSlot.LEGS, ArmorPowerType.RF, POWER_VALUE_MINOR_SLOTS)
                .setSpeedBoni(0.1f, 0.03f,0,0)
                .setWaterspeedBonus(1.25f, 0)
                .setMiningBoni(0.1f,0)
                .setMiningBoniWater(1.25f,0)
                .setRADResistance(1.5f);

        T4_PRAETOR_BOOTS = registerArmorPowered2d(T4_PRAETOR, EquipmentSlot.FEET, ArmorPowerType.RF, POWER_VALUE_MINOR_SLOTS)
                .setSpeedBoni(0.1f, 0.15f,0,0)
                .setWaterspeedBonus(1.25f, 0)
                .setMiningBoni(0.1f,0)
                .setMiningBoniWater(1.25f,0)
                .setStepAssist(1.0f,0)
                .setFallProtection(0.5f, 3.0f,0,0)
                .setRADResistance(1.5f);

        T4_POWER_HELMET = registerArmor3d(T4_POWER, EquipmentSlot.HEAD);
        T4_POWER_CHESTPLATE = registerArmor3d(T4_POWER, EquipmentSlot.CHEST);
        T4_POWER_LEGGINGS = registerArmor3d(T4_POWER, EquipmentSlot.LEGS);
        T4_POWER_BOOTS = registerArmor3d(T4_POWER, EquipmentSlot.FEET);
    }

    protected static String slotTypeToString(EquipmentSlot slotType){
        switch (slotType){
            case HEAD:
                return "helmet";
            case CHEST:
                return "chestplate";
            case LEGS:
                return "leggings";
            case FEET:
                return "boots";
            default:
                throw new IllegalArgumentException("Only slot types HEAD/CHEST/LEGS/FEET supported!");
        }
    }

    protected static String getArmorIdentifier(GenericArmor armor){
        String material_name = armor.getMaterial().getName();
        if (material_name.startsWith(Techguns.MODID+"_")){
            material_name = material_name.substring(Techguns.MODID.length()+1);
        }
        return material_name+"_"+slotTypeToString(armor.getSlotType());
    }

    /**
     * Vanilla MC armor rendering
     */
    protected static GenericArmor registerArmor(TGArmorMaterial mat,  EquipmentSlot slot){
        return registerArmor(mat, slot, false, false, true);
    }

    /**
     * Armor with custom 3d model & inventory 3d
     */
    protected static GenericArmor registerArmor3d(TGArmorMaterial mat,  EquipmentSlot slot) {
        return registerArmor(mat, slot, true, true, false);
    }
    /**
     * Armor with custom 3d model & inventory 3d
     */
    protected static PoweredArmor registerArmorPowered3d(TGArmorMaterial mat,  EquipmentSlot slot, ArmorPowerType powerType, int maxpower) {
        return registerArmorPowered(mat, slot, true, true, false, powerType, maxpower);
    }

    /**
     * 2d inventory icon, custom model
     */
    protected static GenericArmor registerArmor2d(TGArmorMaterial mat,  EquipmentSlot slot) {
        return registerArmor(mat, slot, false, true, false);
    }
    /**
     * 2d inventory icon, custom model
     */
    protected static PoweredArmor registerArmorPowered2d(TGArmorMaterial mat,  EquipmentSlot slot, ArmorPowerType powerType, int maxpower) {
        return registerArmorPowered(mat, slot, false, true, false, powerType, maxpower);
    }

    protected static GenericArmor registerArmor(TGArmorMaterial mat,  EquipmentSlot slot, boolean hasInvRenderhack, boolean hasEntityModelRenderhack, boolean shouldRenderDefaultArmor){
        GenericArmor armor = new GenericArmor(mat, slot, hasInvRenderhack, hasEntityModelRenderhack, shouldRenderDefaultArmor);
        Registry.register(Registry.ITEM, new TGIdentifier(getArmorIdentifier(armor)), armor);
        return armor;
    }

    protected static PoweredArmor registerArmorPowered(TGArmorMaterial mat, EquipmentSlot slot, boolean hasInvRenderhack, boolean hasEntityModelRenderhack, boolean shouldRenderDefaultArmor, ArmorPowerType powerType, int maxpower){
        PoweredArmor armor = new PoweredArmor(mat, slot, hasInvRenderhack, hasEntityModelRenderhack, shouldRenderDefaultArmor, powerType, maxpower);
        Registry.register(Registry.ITEM, new TGIdentifier(getArmorIdentifier(armor)), armor);
        return armor;
    }

}
