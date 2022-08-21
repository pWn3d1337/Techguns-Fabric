package techguns.entities.npcs;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGEntities;
import techguns.TGuns;

public class ZombieSoldier extends GenericZombieEntity {
    public ZombieSoldier(EntityType<ZombieSoldier> entityType, World world) {
        super(entityType, world);
    }

    public ZombieSoldier(World world){
        this(TGEntities.ZOMBIE_SOLDIER, world);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        Item weapon;
        switch (random.nextInt(4)){
            case 0:
                weapon = TGuns.THOMPSON;
                break;
            case 1:
                weapon = TGuns.REVOLVER;
                break;
            case 2:
                weapon = TGuns.HANDCANNON;
                break;
            default:
                weapon = Items.IRON_SWORD;
        }
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(weapon));

        this.equipStack(EquipmentSlot.CHEST, new ItemStack(TGArmors.T1_COMBAT_CHESTPLATE));
        this.equipStack(EquipmentSlot.HEAD, new ItemStack(TGArmors.T1_COMBAT_HELMET));
        this.equipStack(EquipmentSlot.LEGS, new ItemStack(TGArmors.T1_COMBAT_LEGGINGS));
        this.equipStack(EquipmentSlot.FEET, new ItemStack(TGArmors.T1_COMBAT_BOOTS));
    }
}
