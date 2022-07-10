package techguns.entities.npcs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import techguns.api.guns.IGenericGun;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.entities.ai.RangedAttackGoal;
import techguns.items.guns.GenericGun;

public class GenericNPC extends HostileEntity implements RangedAttackMob, INPCTechgunsShooter {
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

    protected GenericNPC(EntityType<? extends GenericNPC> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        GenericGun gun = null;
        Hand shootingHand = Hand.MAIN_HAND;
        if (this.getMainHandStack().isEmpty() && this.getMainHandStack().getItem() instanceof GenericGun){
            gun = (GenericGun) this.getMainHandStack().getItem();
        } else if  (this.getOffHandStack().isEmpty() && this.getOffHandStack().getItem() instanceof GenericGun){
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
}
