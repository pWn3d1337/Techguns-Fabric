package techguns.items.armors;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import techguns.Techguns;

import java.util.ArrayList;
import java.util.Arrays;

public class TGArmorMaterial implements ArmorMaterial {
    static {
        BASE_DURABILITY = new int[]{13, 15, 16, 11};
    }
    protected static final int[] BASE_DURABILITY;
    protected final String name;
    protected final int durabilityMultiplier;
    protected final int[] protectionAmounts;
    protected final int enchantability;
    protected final SoundEvent equipSound;
    protected final float toughness;
    protected final float knockbackResistance;
    protected final ArrayList<Item> repairItems = new ArrayList<>();

    public TGArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Item... repairItems) {
        this.name = Techguns.MODID + "_" + name; //Armor textures go into minecraft resource folder, so prefix with modid
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairItems.addAll(Arrays.asList(repairItems));
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return this.protectionAmounts[slot.getEntitySlotId()];
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
