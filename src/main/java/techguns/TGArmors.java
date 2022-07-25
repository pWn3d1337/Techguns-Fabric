package techguns;

import net.minecraft.entity.EquipmentSlot;
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

    public static TGArmorMaterial T1_STEAM;
    public static GenericArmor T1_STEAM_HELMET;
    public static GenericArmor T1_STEAM_CHESTPLATE;
    public static GenericArmor T1_STEAM_LEGGINGS;
    public static GenericArmor T1_STEAM_BOOTS;

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
        T1_COMBAT = new TGArmorMaterial("t1_combat", 60, new int[]{5,5,5,5}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0F, 0F, TGItems.HEAVY_CLOTH, TGItems.STEEL_INGOT);
        T1_STEAM = new TGArmorMaterial("t1_steam", 60, new int[]{5,5,5,5}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0F, 0F, TGItems.BRONZE_INGOT, TGItems.STEEL_INGOT);
        T3_POWER = new TGArmorMaterial("t3_power", 80, new int[]{6,6,6,6}, 0, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,1F, 0.25F, TGItems.POWER_ARMOR_PLATING);
        T4_POWER = new TGArmorMaterial("t4_power", 100, new int[]{6,6,6,6}, 0, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,1F, 0.25F, TGItems.POWER_ARMOR_PLATING);

        T1_COMBAT_HELMET = registerArmor(T1_COMBAT, EquipmentSlot.HEAD);
        T1_COMBAT_CHESTPLATE = registerArmor(T1_COMBAT, EquipmentSlot.CHEST);
        T1_COMBAT_LEGGINGS = registerArmor(T1_COMBAT, EquipmentSlot.LEGS);
        T1_COMBAT_BOOTS = registerArmor(T1_COMBAT, EquipmentSlot.FEET);

        T1_STEAM_HELMET = registerArmor(T1_STEAM, EquipmentSlot.HEAD, true, true, false);
        T1_STEAM_CHESTPLATE = registerArmor(T1_STEAM, EquipmentSlot.CHEST, true, true, false);
        T1_STEAM_LEGGINGS = registerArmor(T1_STEAM, EquipmentSlot.LEGS, true, true, false);
        T1_STEAM_BOOTS = registerArmor(T1_STEAM, EquipmentSlot.FEET, true, true, false);

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
