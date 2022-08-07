package techguns.items.armors;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import techguns.TGEntityAttributes;
import techguns.TGItems;
import techguns.TGSounds;
import techguns.util.InventoryUtil;
import techguns.util.TextUtil;

import java.util.List;

public class PoweredArmor extends GenericArmor {
    protected ArmorPowerType powerType;
    protected ItemStack battery = ItemStack.EMPTY;
    protected ItemStack battery_empty = ItemStack.EMPTY;

    public int maxpower;

    //
    protected float speedBonusUnpowered =0.0f;
    protected float jumpBonusUnpowered =0.0f;
    protected float fallDMGUnpowered =0.0f;
    protected float fallFreeHeightUnpowered =0.0f;
    protected float miningSpeedBonusUnpowered =0.0f;
    protected float waterMiningBonusUnpowered =0.0f;

    protected float gunAccuracyUnpowered =0.0f;
    protected float extraHeartsUnpowered=0.0f;
    protected float nightvisionUnpowered=0.0f;

    protected float stepassistUnpowerd =0.0f;

    protected float oxygengearUnpowered =0.0f;
    protected float water_electrolyzerUnpowered=0.0f;
    protected float coolingsystemUnpowered=0.0f;

    protected float waterSpeedBonusUnpowered =0.0f;

    protected static final String KEY_POWER = "power";

    public PoweredArmor(TGArmorMaterial material, EquipmentSlot slot, boolean hasInvRenderhack, boolean hasEntityModelRenderhack, boolean shouldRenderDefaultArmor, ArmorPowerType powerType, int maxpower) {
        super(material, slot, hasInvRenderhack, hasEntityModelRenderhack, shouldRenderDefaultArmor);
        this.powerType = powerType;
        this.maxpower = maxpower;
    }

    public PoweredArmor setSpeedBoni(float speed, float jump, float speed_unpowered, float jump_unpowered) {
        this.speedBonus=speed;
        this.jumpBonus=jump;
        this.speedBonusUnpowered =speed_unpowered;
        this.jumpBonusUnpowered =jump_unpowered;
        return this;
    }

    public PoweredArmor setFallProtection(float multiplier, float freeheight, float multiplier_unpowered, float freeheight_unpowered) {
        this.fallDMG=multiplier;
        this.fallFreeHeight=freeheight;
        this.fallDMGUnpowered =multiplier_unpowered;
        this.fallFreeHeightUnpowered =freeheight_unpowered;
        return this;
    }

    public PoweredArmor setMiningBoni(float bonus, float bonus_unpowered) {
        this.miningSpeedBonus=bonus;
        this.miningSpeedBonusUnpowered =bonus_unpowered;
        return this;
    }

    public PoweredArmor setMiningBoniWater(float bonus, float bonus_unpowered) {
        this.waterMiningBonus=bonus;
        this.waterMiningBonusUnpowered =bonus_unpowered;
        return this;
    }

    public PoweredArmor setGunBonus(float acc,float acc_unpowered) {
        this.gunAccuracy=acc;
        this.gunAccuracyUnpowered =acc_unpowered;
        return this;
    }

    public PoweredArmor setHealthBonus(int bonusHearts, int bonusHeartsUnpowered) {
        this.extraHearts=bonusHearts;
        this.extraHeartsUnpowered=bonusHeartsUnpowered;
        return this;
    }

    public PoweredArmor setStepAssist(float stepassist, float stepassist_unpowered) {
        this.stepassist=stepassist;
        this.stepassistUnpowerd=stepassist_unpowered;
        return this;
    }

    public PoweredArmor setOxygenGear(float value, float unpowered_value){
        this.oxygengear=value;
        this.oxygengearUnpowered = unpowered_value;
        return this;
    }

    public PoweredArmor setCoolingSystem(float value,  float unpowered_value){
        this.coolingsystem = value;
        this.coolingsystemUnpowered = unpowered_value;
        return this;
    }

    public PoweredArmor setWaterspeedBonus(float value,  float unpowered_value){
        this.waterSpeedBonus = value;
        this.waterSpeedBonusUnpowered = unpowered_value;
        return this;
    }

    public PoweredArmor setBattery(ItemStack battery) {
        this.battery = battery;
        return this;
    }

    public PoweredArmor setEmptyBattery(ItemStack emptyBattery) {
        this.battery_empty = emptyBattery;
        return this;
    }

    public PoweredArmor setBattery(Item battery) {
        this.battery = new ItemStack(battery);
        return this;
    }

    public PoweredArmor setEmptyBattery(Item emptyBattery) {
        this.battery_empty = new ItemStack(emptyBattery);
        return this;
    }

    public static void consumePower(ItemStack armor, int amount) {
        NbtCompound tags = armor.getNbt();
        if(tags==null){
            tags=new NbtCompound();
            armor.setNbt(tags);
        }
        int power=tags.getInt(KEY_POWER);
        power-=amount;
        if(power<0){
            power=0;
        }
        tags.putInt(KEY_POWER, power);
    }

    public int setPowered(ItemStack armor, int max) {
        NbtCompound tags = armor.getOrCreateNbt();

        int power=tags.getInt(KEY_POWER);
        int missing = maxpower-power;

        if(missing>max){
            tags.putInt(KEY_POWER, power+max);
            return max;
        } else {
            tags.putInt(KEY_POWER, maxpower);
            return missing;
        }
    }

    protected void setFullyPowered(ItemStack armor){
        NbtCompound tags = armor.getNbt();
        if(tags==null){
            tags=new NbtCompound();
            armor.setNbt(tags);
        }
        tags.putInt(KEY_POWER, maxpower);
    }

    public static boolean hasPower(ItemStack armor){
        return getPower(armor)>0;
    }

    public boolean isFullyPowered(ItemStack stack){
        return getPower(stack)>= this.maxpower;
    }

    public static int getPower(ItemStack armor){
        NbtCompound tags = armor.getOrCreateNbt();
        return tags.getInt(KEY_POWER);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        super.onCraft(stack, world, player);
        NbtCompound tags = stack.getOrCreateNbt();
        tags.putInt(KEY_POWER, 0);
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            ItemStack armor = new ItemStack(this, 1);
            NbtCompound tags = armor.getOrCreateNbt();
            tags.putInt(KEY_POWER, this.maxpower);
            stacks.add(armor);
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

            if (getPower(stack) > 0) {

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

            } else {

                if (this.speedBonusUnpowered != 0.0F) {
                    attributes.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], getAttributeName("armor_movespeed", slot), this.speedBonusUnpowered, EntityAttributeModifier.Operation.ADDITION));
                }
                if (this.jumpBonusUnpowered != 0.0F) {
                    attributes.put(TGEntityAttributes.ARMOR_JUMPBOOST, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], getAttributeName("armor_jumpboost", slot), this.jumpBonusUnpowered, EntityAttributeModifier.Operation.ADDITION));
                }
                if (this.miningSpeedBonusUnpowered != 0.0F) {
                    attributes.put(TGEntityAttributes.ARMOR_MININGSPEED, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], getAttributeName("armor_miningspeed", slot), this.miningSpeedBonusUnpowered, EntityAttributeModifier.Operation.ADDITION));
                }
                if (this.waterMiningBonusUnpowered != 0.0F) {
                    attributes.put(TGEntityAttributes.ARMOR_WATERMININGSPEED, new EntityAttributeModifier(ARMOR_MODIFIER_UUIDS[slot.getEntitySlotId()], getAttributeName("armor_waterminingspeed", slot), this.waterMiningBonusUnpowered, EntityAttributeModifier.Operation.ADDITION));
                }
            }

        }
        return attributes.build();
    }

    public int applyPowerConsumptionOnAction(TGArmorBonus type, PlayerEntity player){
        if (type==TGArmorBonus.JUMP){
            if (this.slot==EquipmentSlot.LEGS){
                if (!player.world.isClient())
                {
                    //player.world.playSoundAtEntity(player, "techguns:effects.steamarmorJump", 0.66F, 1.0F );
                    player.world.playSound(null, player.getX(), player.getY(), player.getZ(), TGSounds.STEAM_JUMP, SoundCategory.PLAYERS, 1f, 1f);
                }
                return 5;
            }
        } else if (type ==TGArmorBonus.FALLDMG){
            if (this.slot==EquipmentSlot.FEET){
				/*if (!player.worldObj.isRemote)
		        {
    				player.worldObj.playSoundAtEntity(player, "random.door_close", 1.0F, 1.0F );
		        }*/
                return 5;
            }
        } else if (type ==TGArmorBonus.COOLING_SYSTEM){
            if (this.slot==EquipmentSlot.CHEST){
                return 1;
            }
        }

        return 0;
    }

    /**
     * Provide power from chest to other slots
     */
    protected static void powerSlots(PlayerEntity player, ArmorPowerType power, ItemStack chest, boolean consumeTick){
        for (int i = 0; i < 4; i++) {
            ItemStack armor = player.getInventory().getArmorStack(i);

            if (!armor.isEmpty() && armor.getItem() instanceof PoweredArmor) {
                if (i != 2) {
                    //Not chestplate
                    if (((PoweredArmor) armor.getItem()).powerType == power) {
                        int max = getPower(chest);
                        int dmg = ((PoweredArmor)armor.getItem()).setPowered(armor, max);
                        consumePower(chest, dmg);
                    }
                    if (consumeTick){
                        consumePower(armor,1);
                    }

                } else if(consumeTick) {
                    consumePower(armor,1);
                }
            }
        }
    }

    /**
     * Called from tick event to reduce durability
     */
    public static void calculateConsumptionTick(PlayerEntity player) {
        boolean moving = Math.abs(player.getVelocity().lengthSquared()) > 0.01;

        ArmorPowerType power = null;

        ItemStack chest = player.getInventory().getArmorStack(2);
        if (!chest.isEmpty() && chest.getItem() instanceof PoweredArmor) {
            power = ((PoweredArmor) chest.getItem()).powerType;
        }

        int modTick = (int) (player.world.getTime() % 20);
        powerSlots(player,power,chest,(modTick==0)&&moving);

        if (!chest.isEmpty() && chest.getItem() instanceof PoweredArmor) {
            PoweredArmor powerChest = (PoweredArmor) chest.getItem();
            if (!hasPower(chest)){

                //chest is empty, try reload;
                if (InventoryUtil.consumeAmmoPlayer(player,powerChest.battery)) {

                    if (powerChest.battery_empty!=null){
                        int amount = InventoryUtil.addAmmoToPlayerInventory(player, TGItems.newStack(powerChest.battery_empty, 1));
                        if(amount >0 && !player.world.isClient()){
                            player.world.spawnEntity(new ItemEntity(player.world, player.getX(), player.getY(), player.getZ(), TGItems.newStack(powerChest.battery_empty, amount)));
                        }
                    }
                    powerChest.setFullyPowered(chest);
                    powerSlots(player,power,chest,false);

                } else {
                    //can't reload
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> list, TooltipContext context) {
        NbtCompound tags = stack.getNbt();
        if(tags==null){
            tags=new NbtCompound();
            stack.setNbt(tags);
            tags.putInt(KEY_POWER, 0);
        }
        int power=tags.getInt(KEY_POWER);

        list.add(Text.of(Formatting.AQUA+TextUtil.transTG("tooltip.power") + ": " + power + "/" + maxpower));

        if (this.slot==EquipmentSlot.CHEST){
            list.add(Text.of(Formatting.AQUA+TextUtil.transTG("tooltip.powerType")+": "+this.powerType.toString()+" ("+TextUtil.trans(this.battery.getName().getString()) +")"));

        } else {
            list.add(Text.of(Formatting.AQUA+TextUtil.transTG("tooltip.powerType")+": "+this.powerType.toString()));
        }
        super.appendTooltip(stack, world, list, context);
    }


}
