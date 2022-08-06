package techguns.client.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;
import techguns.api.damagesystem.DamageType;
import techguns.items.armors.GenericArmor;

@Environment(value= EnvType.CLIENT)
public class TGArmorTooltipData implements TooltipData {

    protected double armor_phys = 0D;
    protected double armor_projectile = 0D;
    protected double armor_energy = 0D;
    protected double armor_fire = 0D;
    protected double armor_ice = 0D;
    protected double armor_lightning = 0D;
    protected double armor_poison = 0D;
    protected double armor_radiation = 0D;
    protected double armor_dark = 0D;
    protected double armor_explosion = 0D;

    public TGArmorTooltipData(GenericArmor armor, ItemStack stack) {
        this.armor_phys = armor.getMaterial().getArmorValue(armor.getSlotType(), DamageType.PHYSICAL);
        this.armor_projectile = armor.getMaterial().getArmorValue(armor.getSlotType(), DamageType.PROJECTILE);
        this.armor_energy = armor.getMaterial().getArmorValue(armor.getSlotType(), DamageType.ENERGY);
        this.armor_explosion = armor.getMaterial().getArmorValue(armor.getSlotType(), DamageType.EXPLOSION);
        this.armor_fire = armor.getMaterial().getArmorValue(armor.getSlotType(), DamageType.FIRE);
        this.armor_ice = armor.getMaterial().getArmorValue(armor.getSlotType(), DamageType.ICE);
        this.armor_lightning = armor.getMaterial().getArmorValue(armor.getSlotType(), DamageType.LIGHTNING);
        this.armor_poison= armor.getMaterial().getArmorValue(armor.getSlotType(), DamageType.POISON);
        this.armor_radiation = armor.getMaterial().getArmorValue(armor.getSlotType(), DamageType.RADIATION);
        this.armor_dark = armor.getMaterial().getArmorValue(armor.getSlotType(), DamageType.DARK);
    }
}
