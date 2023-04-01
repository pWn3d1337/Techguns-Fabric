package techguns.items.armors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import techguns.TGCamos;
import techguns.TGEntityAttributes;
import techguns.TGIdentifier;
import techguns.TGItems;
import techguns.api.ICamoChangeable;
import techguns.api.damagesystem.DamageType;
import techguns.api.render.ITGArmorSpecialRenderer;
import techguns.client.item.TGArmorTooltipData;
import techguns.client.render.ITGItemRenderer;

import java.util.*;

public class GenericArmor extends ArmorItem implements FabricItem, ITGItemRenderer, ITGArmorSpecialRenderer, ICamoChangeable {
    //Copied from ArmorItem
    protected static final UUID[] ARMOR_MODIFIER_UUIDS = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};

    protected boolean hasInvRenderhack = false;
    protected boolean hasEntityModelRenderhack = false;
    protected boolean shouldRenderDefaultArmor = true;

    //item boni
    protected float speedBonus =0.0f;
    protected float jumpBonus =0.0f;
    protected float fallDMG =0.0f;
    protected float fallFreeHeight =0.0f;
    protected float miningSpeedBonus =0.0f;
    protected float waterMiningBonus =0.0f;

    protected float gunAccuracy =0.0f;
    protected float extraHearts=0.0f;
    protected float nightvision=0.0f;

    protected float stepassist =0.0f;

    protected float oxygengear =0.0f;
    protected float waterelectrolyzer =0.0f;
    protected float coolingsystem=0.0f;

    protected float waterSpeedBonus =0.0f;

    protected float radResistance =0.0f;

    protected boolean hideFaceslot=false;
    protected boolean hideBackslot=false;
    protected boolean hideGloveslot=false;

    public GenericArmor(TGArmorMaterial material, EquipmentSlot slot) {
        super(material, slot, new Item.Settings());
    }

    public GenericArmor(TGArmorMaterial material, EquipmentSlot slot, boolean hasInvRenderhack, boolean hasEntityModelRenderhack, boolean shouldRenderDefaultArmor) {
        super(material, slot, new Item.Settings());
        this.hasInvRenderhack = hasInvRenderhack;
        this.hasEntityModelRenderhack = hasEntityModelRenderhack;
        this.shouldRenderDefaultArmor = shouldRenderDefaultArmor;
    }

    @Override
    public boolean hasCustomRenderer() {
        return this.hasEntityModelRenderhack;
    }

    @Override
    public boolean shouldRenderDefaultArmor() {
        return this.shouldRenderDefaultArmor;
    }

    @Override
    public boolean shouldUseRenderHack(ItemStack stack) {
        return this.hasInvRenderhack;
    }

    @Override
    public boolean isModelBase(ItemStack stack) {
        return this.hasInvRenderhack;
    }

    @Override
    public TGArmorMaterial getMaterial() {
        return (TGArmorMaterial) this.type;
    }

    @Override
    public int getCamoCount() {
        return TGCamos.getCamoCount(this);
    }

    public GenericArmor setSpeedBoni(float speed,float jump){
        this.speedBonus =speed;
        this.jumpBonus =jump;
        return this;
    }

    public GenericArmor setFallProtection(float multiplier, float freeheight){
        this.fallDMG = multiplier;
        this.fallFreeHeight = freeheight;
        return this;
    }

    public GenericArmor setMiningBoni(float bonus){
        this.miningSpeedBonus =bonus;
        return this;
    }

    public GenericArmor setMiningBoniWater(float bonus){
        this.waterMiningBonus =bonus;
        return this;
    }

    public GenericArmor setGunBonus(float acc){
        this.gunAccuracy =acc;
        return this;
    }

    public GenericArmor setHealthBonus(int bonusHearts){
        this.extraHearts=(float)bonusHearts;
        return this;
    }

    public GenericArmor setRADResistance(float radresistance){
        this.radResistance =radresistance;
        return this;
    }

    public GenericArmor setStepAssist(float stepassist){
        this.stepassist=stepassist;
        return this;
    }

    public GenericArmor setOxygenGear(float value){
        this.oxygengear =value;
        return this;
    }

    public GenericArmor setCoolingSystem(float value){
        this.coolingsystem = value;
        return this;
    }

    public GenericArmor setWaterspeedBonus(float value){
        this.waterSpeedBonus = value;
        return this;
    }

    protected static String getAttributeName(String base, EquipmentSlot slot){
        return new TGIdentifier(base).toString();//+"_"+slot.getName()).toString();
    }

    protected static final Map<DamageType, Pair<EntityAttribute, String>> DAMAGE_TYPE_ATTRIBUTE_MAPPING = Map.ofEntries(
            Map.entry(DamageType.PHYSICAL, new Pair<>(EntityAttributes.GENERIC_ARMOR, "Armor Modifier")),
            Map.entry(DamageType.PROJECTILE, new Pair<>(TGEntityAttributes.ARMOR_PROJECTILE, "Armor Modifier Projectile")),
            Map.entry(DamageType.FIRE, new Pair<>(TGEntityAttributes.ARMOR_FIRE, "Armor Modifier Fire")),
            Map.entry(DamageType.EXPLOSION, new Pair<>(TGEntityAttributes.ARMOR_EXPLOSION, "Armor Modifier Explosion")),
            Map.entry(DamageType.ENERGY, new Pair<>(TGEntityAttributes.ARMOR_ENERGY, "Armor Modifier Energy")),
            Map.entry(DamageType.POISON, new Pair<>(TGEntityAttributes.ARMOR_POISON, "Armor Modifier Poison")),
            Map.entry(DamageType.ICE, new Pair<>(TGEntityAttributes.ARMOR_ICE, "Armor Modifier Ice")),
            Map.entry(DamageType.LIGHTNING, new Pair<>(TGEntityAttributes.ARMOR_LIGHTNING , "Armor Modifier Lightning")),
            Map.entry(DamageType.RADIATION, new Pair<>(TGEntityAttributes.ARMOR_RADIATION, "Armor Modifier Radiation")),
            Map.entry(DamageType.DARK, new Pair<>(TGEntityAttributes.ARMOR_DARK, "Armor Modifier Dark"))
    );


    protected void addArmorAttributes(ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> attribues, EquipmentSlot slot){
        for (var type: DamageType.values()){

            double armorvalue = this.getMaterial().getArmorValue(slot, type);
            if(armorvalue > 0.0){
                var entry = DAMAGE_TYPE_ATTRIBUTE_MAPPING.get(type);
                EntityAttribute attr = entry.getLeft();
                String name = entry.getRight();

                attribues.put(attr, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], name, armorvalue, EntityAttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        var attributes = ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder();

        if (slot == this.slot) {
            this.addArmorAttributes(attributes, slot);

            if(this.getMaterial().getToughness() > 0.0F){
                attributes.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], "Armor toughness", this.getMaterial().getToughness(), EntityAttributeModifier.Operation.ADDITION));
            }
            if(this.getMaterial().getKnockbackResistance() > 0.0F){
                attributes.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], "Armor knockback resistance", this.getMaterial().getKnockbackResistance(), EntityAttributeModifier.Operation.ADDITION));
            }

           /* if (this.speedBonus != 0.0F) {
                attributes.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], getAttributeName("armor_movespeed", slot), this.speedBonus, EntityAttributeModifier.Operation.ADDITION));
            }
            if (this.jumpBonus != 0.0F) {
                attributes.put(TGEntityAttributes.ARMOR_JUMPBOOST, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], getAttributeName("armor_jumpboost", slot), this.jumpBonus, EntityAttributeModifier.Operation.ADDITION));
            }
            if (this.miningSpeedBonus != 0.0F) {
                attributes.put(TGEntityAttributes.ARMOR_MININGSPEED, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], getAttributeName("armor_miningspeed", slot), this.miningSpeedBonus, EntityAttributeModifier.Operation.ADDITION));
            }
            if (this.waterMiningBonus != 0.0F) {
                attributes.put(TGEntityAttributes.ARMOR_WATERMININGSPEED, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], getAttributeName("armor_waterminingspeed", slot), this.waterMiningBonus, EntityAttributeModifier.Operation.ADDITION));
            }
            if(this.fallDMG != 0.0F){
                attributes.put(TGEntityAttributes.ARMOR_FALLDAMAGEREDUCTION, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], getAttributeName("armor_falldamagereduction", slot), this.fallDMG, EntityAttributeModifier.Operation.ADDITION));
            }
            if(this.fallFreeHeight != 0.0F){
                attributes.put(TGEntityAttributes.ARMOR_FALLFREEHEIGHT, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], getAttributeName("armor_fallfreeheight", slot), this.fallFreeHeight, EntityAttributeModifier.Operation.ADDITION));
            }*/
            handle_attribute(attributes, slot, EntityAttributes.GENERIC_MOVEMENT_SPEED, this.speedBonus);
            handle_attribute(attributes, slot, TGEntityAttributes.ARMOR_JUMPBOOST, this.jumpBonus);
            handle_attribute(attributes, slot, TGEntityAttributes.ARMOR_MININGSPEED, this.miningSpeedBonus);
            handle_attribute(attributes, slot, TGEntityAttributes.ARMOR_WATERMININGSPEED, this.waterMiningBonus);
            handle_attribute(attributes, slot, TGEntityAttributes.ARMOR_FALLDAMAGEREDUCTION, this.fallDMG);
            handle_attribute(attributes, slot, TGEntityAttributes.ARMOR_FALLFREEHEIGHT, this.fallFreeHeight);
            handle_attribute(attributes, slot, TGEntityAttributes.ARMOR_GUNACCURACY, this.gunAccuracy);
            handle_attribute(attributes, slot, EntityAttributes.GENERIC_MAX_HEALTH, this.extraHearts);
            handle_attribute(attributes, slot, TGEntityAttributes.ARMOR_NIGHTVISION, this.nightvision);
            handle_attribute(attributes, slot, TGEntityAttributes.ARMOR_STEPASSIST, this.stepassist);
            handle_attribute(attributes, slot, TGEntityAttributes.ARMOR_OXYGENGEAR, this.oxygengear);
            handle_attribute(attributes, slot, TGEntityAttributes.ARMOR_WATERELECTROLYZER, this.waterelectrolyzer);
            handle_attribute(attributes, slot, TGEntityAttributes.ARMOR_COOLINGSYSTEM, this.coolingsystem);

        }
        return attributes.build();
    }

    private void handle_attribute(ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder, EquipmentSlot slot, EntityAttribute attribute, double attributeValue){
        if(attributeValue != 0D){
            builder.put(attribute, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], getAttributeName(attribute.getTranslationKey(), slot), attributeValue, EntityAttributeModifier.Operation.ADDITION));
        }
    }

    private static final EntityAttribute[] HIDE_TOOLTIP_ENIITY_MODIFIERS = {
            TGEntityAttributes.ARMOR_PROJECTILE, TGEntityAttributes.ARMOR_DARK, TGEntityAttributes.ARMOR_ICE, TGEntityAttributes.ARMOR_FIRE, TGEntityAttributes.ARMOR_POISON,
            TGEntityAttributes.ARMOR_ENERGY, TGEntityAttributes.ARMOR_EXPLOSION, TGEntityAttributes.ARMOR_LIGHTNING, TGEntityAttributes.ARMOR_RADIATION, EntityAttributes.GENERIC_ARMOR
    };

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> list, TooltipContext context) {
        super.appendTooltip(stack, world, list, context);

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            Multimap<EntityAttribute, EntityAttributeModifier> multimap = this.getAttributeModifiers(stack, equipmentSlot);
            if (multimap.isEmpty()) continue;
            list.add(ScreenTexts.EMPTY);
            list.add(Text.translatable("item.modifiers." + equipmentSlot.getName()).formatted(Formatting.GRAY));
            for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : multimap.entries()) {
                //Don't add armor modifiers to tooltip
                if (Arrays.stream(HIDE_TOOLTIP_ENIITY_MODIFIERS).anyMatch(entityAttribute -> entityAttribute == entry.getKey())) {
                    continue;
                }

                EntityAttributeModifier entityAttributeModifier = entry.getValue();
                double d = entityAttributeModifier.getValue();
                boolean bl = false;

                double e = entityAttributeModifier.getOperation() == EntityAttributeModifier.Operation.MULTIPLY_BASE || entityAttributeModifier.getOperation() == EntityAttributeModifier.Operation.MULTIPLY_TOTAL ? d * 100.0 : (entry.getKey().equals(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) ? d * 10.0 : d);
                if (bl) {
                    list.add(Text.literal(" ").append(Text.translatable("attribute.modifier.equals." + entityAttributeModifier.getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(e), Text.translatable(entry.getKey().getTranslationKey()))).formatted(Formatting.DARK_GREEN));
                    continue;
                }
                if (d > 0.0) {
                    list.add(Text.translatable("attribute.modifier.plus." + entityAttributeModifier.getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(e), Text.translatable(entry.getKey().getTranslationKey())).formatted(Formatting.BLUE));
                    continue;
                }
                if (!(d < 0.0)) continue;
                list.add(Text.translatable("attribute.modifier.take." + entityAttributeModifier.getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(e *= -1.0), Text.translatable(entry.getKey().getTranslationKey())).formatted(Formatting.RED));
            }
        }
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        //We don't want to display a huge list of modifiers
        stack.addHideFlag(ItemStack.TooltipSection.MODIFIERS);
        return Optional.of(new TGArmorTooltipData(this, stack));
    }


}
