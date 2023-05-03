package techguns.entities.npcs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import techguns.*;

public class ArmySoldier extends GenericHuman{
    public ArmySoldier(EntityType<? extends GenericNPC> entityType, World world) {
        super(entityType, world);
    }
    public ArmySoldier(World world){
        this(TGEntities.ARMY_SOLDIER, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new AvoidSunlightGoal(this));
        this.goalSelector.add(3, new EscapeSunlightGoal(this, 1.0));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return GenericHuman.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        Item weapon;
        switch (random.nextInt(3)){
            case 0:
                weapon = TGuns.M4;
                break;
            case 1:
                weapon = TGuns.COMBAT_SHOTGUN;
                break;
            case 2:
                weapon = TGuns.BOLTACTION;
                break;
            default:
                weapon = TGuns.M4;
        }
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(weapon));

        RegistryEntry<Biome> biomeEntry = world.getBiome(this.getBlockPos());
        Identifier camo = TGCamos.getRandomCamoFor(TGArmors.T2_COMBAT_CHESTPLATE, random);
        if (biomeEntry.value().getPrecipitation(this.getBlockPos()) == Biome.Precipitation.SNOW){
            camo = new TGIdentifier("arctic");
        } else if(biomeEntry.isIn(BiomeTags.IS_BADLANDS) || biomeEntry.isIn(BiomeTags.IS_SAVANNA)){
            camo = new TGIdentifier("desert");
        }
        //TODO desert biome check

        if (random.nextBoolean()){
            this.equipStackWithCamo(random, TGArmors.T2_COMBAT_HELMET, camo);
        } else {
            this.equipStackWithRandomCamo(random, TGArmors.T2_BERET_HELMET);
        }
        this.equipStackWithChance(random, TGArmors.T2_COMBAT_CHESTPLATE, 0.5D, camo);
        this.equipStackWithChance(random, TGArmors.T2_COMBAT_LEGGINGS, 0.5D, camo);
        this.equipStackWithChance(random, TGArmors.T2_COMBAT_BOOTS, 0.5D, camo);
    }

}
