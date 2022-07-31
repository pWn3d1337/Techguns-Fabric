package techguns;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;
import techguns.items.armors.GenericArmor;
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

    public static TGArmorMaterial T2_COMBAT;
    public static GenericArmor T2_COMBAT_HELMET;
    public static GenericArmor T2_COMBAT_CHESTPLATE;
    public static GenericArmor T2_COMBAT_LEGGINGS;
    public static GenericArmor T2_COMBAT_BOOTS;

    public static TGArmorMaterial T2_RIOT;
    public static GenericArmor T2_RIOT_HELMET;
    public static GenericArmor T2_RIOT_CHESTPLATE;
    public static GenericArmor T2_RIOT_LEGGINGS;
    public static GenericArmor T2_RIOT_BOOTS;

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

    public static TGArmorMaterial T4_POWER;
    public static GenericArmor T4_POWER_HELMET;
    public static GenericArmor T4_POWER_CHESTPLATE;
    public static GenericArmor T4_POWER_LEGGINGS;
    public static GenericArmor T4_POWER_BOOTS;

    @Override
    public void init() {
        T1_COMBAT = new TGArmorMaterial("t1_combat", 60, 15F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0F, 0.1F, TGItems.HEAVY_CLOTH, Items.IRON_INGOT);
        T1_SCOUT = new TGArmorMaterial("t1_scout", 60, 13F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0F, 0F, TGItems.HEAVY_CLOTH);
        T1_MINER = new TGArmorMaterial("t1_miner", 60, 13F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0F, 0F, TGItems.HEAVY_CLOTH, Items.IRON_INGOT);
        T1_STEAM = new TGArmorMaterial("t1_steam", 60, 19F, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON,1F, 0.15F, TGItems.BRONZE_INGOT, TGItems.STEEL_INGOT);

        T2_COMBAT = new TGArmorMaterial("t2_combat", 60, 18F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,1F, 0.15F, TGItems.HEAVY_CLOTH, TGItems.STEEL_INGOT);
        T2_RIOT = new TGArmorMaterial("t2_riot", 70, 18.5F, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0F, 0.15F, TGItems.TREATED_LEATHER);
        T3_COMBAT = new TGArmorMaterial("t3_combat", 75, 21F, 0, SoundEvents.ITEM_ARMOR_EQUIP_GOLD,1F, 0.2F, TGItems.PLATE_CARBON);
        T3_EXO = new TGArmorMaterial("t3_exo", 75, 21F, 0, SoundEvents.ITEM_ARMOR_EQUIP_GOLD,1F, 0.2F, TGItems.PLATE_CARBON);
        T3_POWER = new TGArmorMaterial("t3_power", 80, 22F, 0, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,1F, 0.25F, TGItems.POWER_ARMOR_PLATING);
        T4_POWER = new TGArmorMaterial("t4_power", 100, 23F, 0, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,1F, 0.25F, TGItems.POWER_ARMOR_PLATING);


        T1_COMBAT_HELMET = registerArmor(T1_COMBAT, EquipmentSlot.HEAD);
        T1_COMBAT_CHESTPLATE = registerArmor(T1_COMBAT, EquipmentSlot.CHEST).setMiningBoni(2F).setMiningBoniWater(5F);
        T1_COMBAT_LEGGINGS = registerArmor(T1_COMBAT, EquipmentSlot.LEGS).setSpeedBoni(0.1F,0.5F);
        T1_COMBAT_BOOTS = registerArmor(T1_COMBAT, EquipmentSlot.FEET).setSpeedBoni(0.1F,0.5F);

        T1_SCOUT_HELMET = registerArmor(T1_SCOUT, EquipmentSlot.HEAD, false, true, false);
        T1_SCOUT_CHESTPLATE = registerArmor(T1_SCOUT, EquipmentSlot.CHEST, false, true, false).setMiningBoni(2F).setMiningBoniWater(5F);
        T1_SCOUT_LEGGINGS = registerArmor(T1_SCOUT, EquipmentSlot.LEGS, false, true, false).setSpeedBoni(0.1F,0.5F);
        T1_SCOUT_BOOTS = registerArmor(T1_SCOUT, EquipmentSlot.FEET, false, true, false).setSpeedBoni(0.1F,0.5F);

        T1_MINER_HELMET = registerArmor(T1_MINER, EquipmentSlot.HEAD, false, true, false);
        T1_MINER_CHESTPLATE = registerArmor(T1_MINER, EquipmentSlot.CHEST, false, true, false).setMiningBoni(2F).setMiningBoniWater(5F);
        T1_MINER_LEGGINGS = registerArmor(T1_MINER, EquipmentSlot.LEGS, false, true, false).setSpeedBoni(0.1F,0.5F);
        T1_MINER_BOOTS = registerArmor(T1_MINER, EquipmentSlot.FEET, false, true, false).setSpeedBoni(0.1F,0.5F);

        T1_STEAM_HELMET = registerArmor(T1_STEAM, EquipmentSlot.HEAD, true, true, false);
        T1_STEAM_CHESTPLATE = registerArmor(T1_STEAM, EquipmentSlot.CHEST, true, true, false);
        T1_STEAM_LEGGINGS = registerArmor(T1_STEAM, EquipmentSlot.LEGS, true, true, false);
        T1_STEAM_BOOTS = registerArmor(T1_STEAM, EquipmentSlot.FEET, true, true, false);

        T2_COMBAT_HELMET = registerArmor(T2_COMBAT, EquipmentSlot.HEAD, false, true, false);
        T2_COMBAT_CHESTPLATE = registerArmor(T2_COMBAT, EquipmentSlot.CHEST, false, true, false);
        T2_COMBAT_LEGGINGS = registerArmor(T2_COMBAT, EquipmentSlot.LEGS, false, true, false);
        T2_COMBAT_BOOTS = registerArmor(T2_COMBAT, EquipmentSlot.FEET, false, true, false);

        T2_RIOT_HELMET = registerArmor(T2_RIOT, EquipmentSlot.HEAD, false, true, false);
        T2_RIOT_CHESTPLATE = registerArmor(T2_RIOT, EquipmentSlot.CHEST, false, true, false);
        T2_RIOT_LEGGINGS = registerArmor(T2_RIOT, EquipmentSlot.LEGS, false, true, false);
        T2_RIOT_BOOTS = registerArmor(T2_RIOT, EquipmentSlot.FEET, false, true, false);

        T3_COMBAT_HELMET = registerArmor(T3_COMBAT, EquipmentSlot.HEAD, false, true, false);
        T3_COMBAT_CHESTPLATE = registerArmor(T3_COMBAT, EquipmentSlot.CHEST, false, true, false);
        T3_COMBAT_LEGGINGS = registerArmor(T3_COMBAT, EquipmentSlot.LEGS, false, true, false);
        T3_COMBAT_BOOTS = registerArmor(T3_COMBAT, EquipmentSlot.FEET, false, true, false);

        T3_EXO_HELMET = registerArmor(T3_EXO, EquipmentSlot.HEAD, false, true, false);
        T3_EXO_CHESTPLATE = registerArmor(T3_EXO, EquipmentSlot.CHEST, false, true, false);
        T3_EXO_LEGGINGS = registerArmor(T3_EXO, EquipmentSlot.LEGS, false, true, false);
        T3_EXO_BOOTS = registerArmor(T3_EXO, EquipmentSlot.FEET, false, true, false);

        T3_POWER_HELMET = registerArmor(T3_POWER, EquipmentSlot.HEAD, true, true, false);
        T3_POWER_CHESTPLATE = registerArmor(T3_POWER, EquipmentSlot.CHEST, true, true, false);
        T3_POWER_LEGGINGS = registerArmor(T3_POWER, EquipmentSlot.LEGS, true, true, false);
        T3_POWER_BOOTS = registerArmor(T3_POWER, EquipmentSlot.FEET, true, true, false);

        T4_POWER_HELMET = registerArmor(T4_POWER, EquipmentSlot.HEAD, true, true, false);
        T4_POWER_CHESTPLATE = registerArmor(T4_POWER, EquipmentSlot.CHEST, true, true, false);
        T4_POWER_LEGGINGS = registerArmor(T4_POWER, EquipmentSlot.LEGS, true, true, false);
        T4_POWER_BOOTS = registerArmor(T4_POWER, EquipmentSlot.FEET, true, true, false);
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

    protected static GenericArmor registerArmor(TGArmorMaterial mat,  EquipmentSlot slot){
        return registerArmor(mat, slot, false, false, true);
    }

    protected static GenericArmor registerArmor(TGArmorMaterial mat,  EquipmentSlot slot, boolean hasInvRenderhack, boolean hasEntityModelRenderhack, boolean shouldRenderDefaultArmor){
        GenericArmor armor = new GenericArmor(mat, slot, hasInvRenderhack, hasEntityModelRenderhack, shouldRenderDefaultArmor);
        Registry.register(Registry.ITEM, new TGIdentifier(getArmorIdentifier(armor)), armor);
        return armor;
    }


}
