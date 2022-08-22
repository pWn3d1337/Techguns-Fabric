package techguns.entities.npcs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGEntities;
import techguns.TGIdentifier;
import techguns.TGuns;

public class ZombiePoliceman extends GenericZombieEntity {
    public ZombiePoliceman(EntityType<? extends GenericNPC> entityType, World world) {
        super(entityType, world);
    }

    public ZombiePoliceman(World world) {
        this(TGEntities.ZOMBIE_POLICEMAN, world);
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return GenericZombieEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        Item weapon;
        switch (random.nextInt(2)) {
            case 0:
                weapon = TGuns.REVOLVER;
                break;
            case 1:
                weapon = TGuns.PISTOL;
                break;
            default:
                weapon = TGuns.REVOLVER;
        }
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(weapon));

        Identifier camo = new TGIdentifier("security");
        this.equipStackWithChance(random, TGArmors.T2_COMBAT_HELMET, 0.5D, camo);
        this.equipStackWithChance(random, TGArmors.T2_COMBAT_CHESTPLATE, 0.5D, camo);
        this.equipStackWithChance(random, TGArmors.T2_COMBAT_LEGGINGS, 0.5D, camo);
        this.equipStackWithChance(random, TGArmors.T2_COMBAT_BOOTS, 0.5D, camo);
    }
}