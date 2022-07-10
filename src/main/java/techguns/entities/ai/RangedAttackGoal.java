package techguns.entities.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.BowItem;
import net.minecraft.util.Hand;
import techguns.api.guns.IGenericGun;
import techguns.entities.npcs.GenericNPC;

import java.util.EnumSet;

public class RangedAttackGoal <T extends GenericNPC & RangedAttackMob> extends Goal {

    protected final T actor;
    protected final double speed;
    protected int attackInterval;
    protected final float squaredRange;
    protected int cooldown = -1;
    protected int targetSeeingTicker;
    protected boolean movingToLeft;
    protected boolean backward;
    protected int combatTicks = -1;

    public RangedAttackGoal(T actor, double speed, int attackInterval, float range) {
        this.actor = actor;
        this.speed = speed;
        this.attackInterval = attackInterval;
        this.squaredRange = range * range;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    public void setAttackInterval(int attackInterval) {
        this.attackInterval = attackInterval;
    }

    @Override
    public boolean canStart() {
        if (this.actor.getTarget() == null) {
            return false;
        }
        return this.isHoldingGun();
    }

    protected boolean isHoldingGun() {
        if (!this.actor.getMainHandStack().isEmpty() && this.actor.getMainHandStack().getItem() instanceof IGenericGun) {
            return true;
        } else {
            return (!this.actor.getOffHandStack().isEmpty() && this.actor.getOffHandStack().getItem() instanceof IGenericGun);
        }
    }

    @Override
    public boolean shouldContinue() {
        return (this.canStart() || !this.actor.getNavigation().isIdle()) && this.isHoldingGun();
    }

    @Override
    public void start() {
        super.start();
        this.actor.setAttacking(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.actor.setAttacking(false);
        this.targetSeeingTicker = 0;
        this.cooldown = -1;
        this.actor.clearActiveItem();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        boolean bl2;
        LivingEntity livingEntity = this.actor.getTarget();
        if (livingEntity == null) {
            return;
        }
        double d = this.actor.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
        boolean bl = this.actor.getVisibilityCache().canSee(livingEntity);
        boolean bl3 = bl2 = this.targetSeeingTicker > 0;
        if (bl != bl2) {
            this.targetSeeingTicker = 0;
        }
        this.targetSeeingTicker = bl ? ++this.targetSeeingTicker : --this.targetSeeingTicker;
        if (d > (double)this.squaredRange || this.targetSeeingTicker < 20) {
            this.actor.getNavigation().startMovingTo(livingEntity, this.speed);
            this.combatTicks = -1;
        } else {
            this.actor.getNavigation().stop();
            ++this.combatTicks;
        }
        if (this.combatTicks >= 20) {
            if ((double) this.actor.getRandom().nextFloat() < 0.3) {
                boolean bl4 = this.movingToLeft = !this.movingToLeft;
            }
            if ((double) this.actor.getRandom().nextFloat() < 0.3) {
                this.backward = !this.backward;
            }
            this.combatTicks = 0;
        }
        if (this.combatTicks > -1) {
            if (d > (double)(this.squaredRange * 0.75f)) {
                this.backward = false;
            } else if (d < (double)(this.squaredRange * 0.25f)) {
                this.backward = true;
            }
            this.actor.getMoveControl().strafeTo(this.backward ? -0.5f : 0.5f, this.movingToLeft ? 0.5f : -0.5f);
            this.actor.lookAtEntity(livingEntity, 30.0f, 30.0f);
        } else {
            this.actor.getLookControl().lookAt(livingEntity, 30.0f, 30.0f);
        }
        if (this.actor.isUsingItem()) {
            int i;
            if (!bl && this.targetSeeingTicker < -60) {
                this.actor.clearActiveItem();
            } else if (bl && (i = this.actor.getItemUseTime()) >= 20) {
                this.actor.clearActiveItem();
                this.actor.attack(livingEntity, BowItem.getPullProgress(i));
                this.cooldown = this.attackInterval;
            }
        } else if (--this.cooldown <= 0 && this.targetSeeingTicker >= -60) {
            this.actor.setCurrentHand(getHandPossiblyUsingGun(actor));
        }
    }

    public static Hand getHandPossiblyUsingGun(LivingEntity actor){
        if (!actor.getMainHandStack().isEmpty() && actor.getMainHandStack().getItem() instanceof IGenericGun){
            return Hand.MAIN_HAND;
        }
        return Hand.OFF_HAND;
    }
}
