package techguns.entities.npcs;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;
import techguns.api.entity.AttackTime;
import techguns.api.entity.ITGShooterValues;
import techguns.api.guns.IGenericGun;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.entities.ai.RangedAttackGoal;
import techguns.items.guns.GenericGun;

public class GenericNPC extends HostileEntity implements RangedAttackMob, INPCTechgunsShooter, ITGShooterValues {
    protected final RangedAttackGoal<GenericNPC> rangedAttackGoal = new RangedAttackGoal<>(this, 1.0, 20, 15.0f);
    protected final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2, false){

        @Override
        public void stop() {
            super.stop();
            GenericNPC.this.setAttacking(false);
        }

        @Override
        public void start() {
            super.start();
            GenericNPC.this.setAttacking(true);
        }
    };
    protected AttackTime attackTimes_mh = new AttackTime();
    protected AttackTime attackTimes_oh = new AttackTime();

    protected GenericNPC(EntityType<? extends GenericNPC> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tickRiding() {
        super.tickRiding();
        if (this.getVehicle() instanceof PathAwareEntity) {
            PathAwareEntity pathAwareEntity = (PathAwareEntity)this.getVehicle();
            this.bodyYaw = pathAwareEntity.bodyYaw;
        }
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        GenericGun gun = null;
        Hand shootingHand = Hand.MAIN_HAND;
        if (!this.getMainHandStack().isEmpty() && this.getMainHandStack().getItem() instanceof GenericGun){
            gun = (GenericGun) this.getMainHandStack().getItem();
        } else if  (!this.getOffHandStack().isEmpty() && this.getOffHandStack().getItem() instanceof GenericGun){
            gun = (GenericGun) this.getOffHandStack().getItem();
            shootingHand = Hand.OFF_HAND;
        }
        if (gun !=null){
            Difficulty difficulty = this.world.getDifficulty();
            float acc=1.0f;
            float dmg=1.0f;
            switch(difficulty){
                case EASY:
                    acc=1.3f;
                    dmg=0.6f;
                    break;
                case NORMAL:
                    acc = 1.15f;
                    dmg = 0.8f;
                    break;
                case HARD:
                    acc = 1.0f;
                    dmg = 1.0f;
                    break;
                default:
                    break;
            }
            gun.fireWeaponFromNPC(this,dmg,acc, shootingHand);
        }
    }



    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        Random random = world.getRandom();
        this.initEquipment(random, difficulty);
        this.updateEnchantments(random, difficulty);
        this.updateAttackType();

        return entityData;
    }

    protected void updateAttackType() {
        if (this.world == null || this.world.isClient) {
            return;
        }
        this.goalSelector.remove(this.meleeAttackGoal);
        this.goalSelector.remove(this.rangedAttackGoal);

        ItemStack itemStack = this.getStackInHand(RangedAttackGoal.getHandPossiblyUsingGun(this));
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof IGenericGun) {
            int i = 20;
            if (this.world.getDifficulty() != Difficulty.HARD) {
                i = 40;
            }
            this.rangedAttackGoal.setAttackInterval(i);
            this.goalSelector.add(4, this.rangedAttackGoal);
        } else {
            this.goalSelector.add(4, this.meleeAttackGoal);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.updateAttackType();
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        super.equipStack(slot, stack);
        if (!this.world.isClient) {
            this.updateAttackType();
        }
    }

    @Override
    public AttackTime getAttackTime(boolean offHand) {
        return offHand ? this.attackTimes_oh : this.attackTimes_mh;
    }
}
