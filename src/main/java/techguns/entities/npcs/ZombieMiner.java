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

public class ZombieMiner extends GenericZombieEntity{

    public ZombieMiner(EntityType<? extends GenericNPC> entityType, World world) {
        super(entityType, world);
    }
    public ZombieMiner(World world){
        this(TGEntities.ZOMBIE_MINER, world);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        Item weapon;
        switch (random.nextInt(3)){
            case 0:
                weapon = Items.STONE_PICKAXE;
                break;
            case 1:
                weapon = Items.IRON_PICKAXE;
                break;
            default:
                weapon = TGuns.HANDCANNON;
        }
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(weapon));

        Identifier camo = TGCamos.getRandomCamoFor(TGArmors.T1_MINER_CHESTPLATE, random);
        Identifier helmetcamo = TGCamos.getRandomCamoFor(TGArmors.T1_MINER_HELMET, random);

        this.equipStackWithCamo(random, TGArmors.T1_MINER_HELMET, helmetcamo);
        this.equipStackWithChance(random, TGArmors.T1_MINER_CHESTPLATE, 0.5D, camo);
        this.equipStackWithChance(random, TGArmors.T1_MINER_LEGGINGS, 0.5D, camo);
        this.equipStackWithChance(random, TGArmors.T1_MINER_BOOTS, 0.5D, camo);
    }
}
