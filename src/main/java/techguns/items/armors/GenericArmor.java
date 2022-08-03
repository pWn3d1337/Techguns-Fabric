package techguns.items.armors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import techguns.TGCamos;
import techguns.TGEntityAttributes;
import techguns.TGIdentifier;
import techguns.TGItems;
import techguns.api.ICamoChangeable;
import techguns.api.damagesystem.DamageType;
import techguns.api.render.ITGArmorSpecialRenderer;
import techguns.client.render.ITGItemRenderer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GenericArmor extends ArmorItem implements FabricItem, ITGItemRenderer, ITGArmorSpecialRenderer, ICamoChangeable {
    //Copied from ArmorItem
    private static final UUID[] ARMOR_MODIFIER_UUIDS = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};

    /*public static final UUID[] UUIDS_MOVESPEED = {
            UUID.fromString("6a9a37a6-0e70-11ed-861d-0242ac120002"),
            UUID.fromString("cf6b9ac0-0e71-11ed-861d-0242ac120002"),
            UUID.fromString("d333173c-0e71-11ed-861d-0242ac120002"),
            UUID.fromString("d6df7b28-0e71-11ed-861d-0242ac120002")};

    public static final UUID[] UUIDS_JUMPBOOST = {
            UUID.fromString("6f041dc8-0f12-11ed-861d-0242ac120002"),
            UUID.fromString("6f041fb2-0f12-11ed-861d-0242ac120002"),
            UUID.fromString("6f0420ac-0f12-11ed-861d-0242ac120002"),
            UUID.fromString("6f042192-0f12-11ed-861d-0242ac120002")};

    public static final UUID[] UUIDS_MININGSPEED = {
            UUID.fromString("80932e26-0f1c-11ed-861d-0242ac120002"),
            UUID.fromString("80933358-0f1c-11ed-861d-0242ac120002"),
            UUID.fromString("80933498-0f1c-11ed-861d-0242ac120002"),
            UUID.fromString("809335c4-0f1c-11ed-861d-0242ac120002")};

    public static final UUID[] UUIDS_WATERMININGSPEED = {
            UUID.fromString("f61b2400-0f1c-11ed-861d-0242ac120002"),
            UUID.fromString("f61b2694-0f1c-11ed-861d-0242ac120002"),
            UUID.fromString("f61b27ca-0f1c-11ed-861d-0242ac120002"),
            UUID.fromString("f61b28d8-0f1c-11ed-861d-0242ac120002")};*/


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
        super(material, slot, new Item.Settings().group(TGItems.ITEM_GROUP_TECHGUNS));
    }

    public GenericArmor(TGArmorMaterial material, EquipmentSlot slot, boolean hasInvRenderhack, boolean hasEntityModelRenderhack, boolean shouldRenderDefaultArmor) {
        super(material, slot, new Item.Settings().group(TGItems.ITEM_GROUP_TECHGUNS));
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
            /*if(this.getMaterial().armorPhys > 0.0F){
                attributes.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], "Armor modifier", this.getMaterial().getArmorValue(slot, DamageType.PHYSICAL), EntityAttributeModifier.Operation.ADDITION));
            }*/
            this.addArmorAttributes(attributes, slot);

            if(this.getMaterial().getToughness() > 0.0F){
                attributes.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], "Armor toughness", this.getMaterial().getToughness(), EntityAttributeModifier.Operation.ADDITION));
            }
            if(this.getMaterial().getKnockbackResistance() > 0.0F){
                attributes.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], "Armor knockback resistance", this.getMaterial().getKnockbackResistance(), EntityAttributeModifier.Operation.ADDITION));
            }

            if (this.speedBonus != 0.0F) {
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

        }
        return attributes.build();
    }

    @Override
    public int getProtection() {
        return super.getProtection();
    }
}
