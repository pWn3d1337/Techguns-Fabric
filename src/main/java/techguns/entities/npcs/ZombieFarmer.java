package techguns.entities.npcs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGCamos;
import techguns.TGEntities;
import techguns.TGuns;

public class ZombieFarmer extends GenericZombieEntity {
    public ZombieFarmer(EntityType<? extends GenericNPC> entityType, World world) {
        super(entityType, world);
    }

    public ZombieFarmer(World world){
        this(TGEntities.ZOMBIE_FARMER, world);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        Item weapon;
        switch (random.nextInt(4)){
            case 0:
                weapon = Items.WOODEN_HOE;
                break;
            case 1:
                weapon = Items.STONE_HOE;
                break;
            case 2:
                weapon = Items.IRON_HOE;
                break;
            default:
                weapon = TGuns.HANDCANNON;
        }
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(weapon));

        Identifier camo = TGCamos.getRandomCamoFor(TGArmors.T1_MINER_CHESTPLATE, random);

        this.equipStackWithChance(random, TGArmors.T1_MINER_CHESTPLATE, 0.5D, camo);
        this.equipStackWithChance(random, TGArmors.T1_MINER_LEGGINGS, 0.5D, camo);
        this.equipStackWithChance(random, TGArmors.T1_MINER_BOOTS, 0.5D, camo);
    }
}
