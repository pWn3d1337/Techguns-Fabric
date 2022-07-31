package techguns.items.armors;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;

import java.util.ArrayList;
import java.util.Arrays;

public class TGArmorMaterial implements ArmorMaterial {
    static {
        BASE_DURABILITY = new int[]{13, 15, 16, 11};
    }
    /**
     * feet, legs, chest, helmet
     */
    protected static final double[] slotFactors = {0.2D, 0.25D, 0.3D, 0.25D};

    protected static final int[] BASE_DURABILITY;
    protected final String name;
    protected final int durabilityMultiplier;
    //protected final int[] protectionAmounts;
    protected final int enchantability;
    protected final SoundEvent equipSound;
    protected final float toughness;
    protected final float knockbackResistance;
    protected final ArrayList<Item> repairItems = new ArrayList<>();

    protected float armorPhys=0;
    protected float armorProjectile=0;
    protected float armorExplosion=0;
    protected float armorEnergy=0;
    protected float armorFire=0;
    protected float armorLightning=0;
    protected float armorIce=0;
    protected float armorDark=0;
    protected float armorPoison=0;
    protected float armorRadiation=0;

    public TGArmorMaterial(String name, int durabilityMultiplier, float armor, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Item... repairItems) {
        this.name = Techguns.MODID + "_" + name; //Armor textures go into minecraft resource folder, so prefix with modid
        this.durabilityMultiplier = durabilityMultiplier;
        //this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairItems.addAll(Arrays.asList(repairItems));

        float f=0.75f;
        this.armorPhys=armor;
        this.armorProjectile=armor;
        this.armorFire=armor*f;
        this.armorExplosion=armor*f;
        this.armorEnergy=armor*f;
        this.armorIce=armor*f;
        this.armorLightning=armor*f;
        this.armorDark=armor*f;
        this.armorPoison=armor*f;
        this.armorRadiation=0.0f;
    }

    public double getArmorValue(EquipmentSlot slot, DamageType type){
        double val = 0.0D;
        switch (type) {
            case PROJECTILE:
                val = this.armorProjectile;
                break;
            case FIRE:
                val = this.armorFire;
                break;
            case EXPLOSION:
                val = this.armorExplosion;
                break;
            case ENERGY:
                val = this.armorEnergy;
                break;
            case POISON:
                val = this.armorPoison;
                break;
            case UNRESISTABLE:
                break;
            case ICE:
                val = this.armorIce;
                break;
            case LIGHTNING:
                val = this.armorLightning;
                break;
            case RADIATION:
                val = this.armorRadiation;
                break;
            case DARK:
                val = this.armorDark;
                break;
            case PHYSICAL:
            default:
                val = this.armorPhys;
        }
        return val * slotFactors[slot.getEntitySlotId()];
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(this.repairItems.toArray(new Item[0]));
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
