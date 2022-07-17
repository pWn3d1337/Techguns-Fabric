package techguns.client.models.entities;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.items.guns.GenericGun;

import java.util.function.Function;

public class GenericNPCModel<T extends LivingEntity & INPCTechgunsShooter> extends BipedEntityModel<T> {
    public GenericNPCModel(ModelPart root) {
        super(root);
    }

    public GenericNPCModel(ModelPart root, Function renderLayerFactory) {
        super(root, renderLayerFactory);
    }

    @Override
    public void animateModel(T livingEntity, float f, float g, float h) {
        super.animateModel(livingEntity, f, g, h);
        if (livingEntity.hasWeaponArmPose()){
            Hand rightHand = livingEntity.preferredHand != null ? livingEntity.preferredHand : Hand.MAIN_HAND;
            Hand leftHand = rightHand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;

            ItemStack rightHandStack = livingEntity.getStackInHand(rightHand);
            ItemStack leftHandStack = livingEntity.getStackInHand(leftHand);

            GenericGun rightGun = !rightHandStack.isEmpty() && rightHandStack.getItem() instanceof GenericGun ? (GenericGun) rightHandStack.getItem() : null;
            GenericGun leftGun = !leftHandStack.isEmpty() && leftHandStack.getItem() instanceof GenericGun ? (GenericGun) leftHandStack.getItem() : null;

            boolean akimbo = rightGun != null && leftGun !=null;

            if(rightGun != null) {
                this.rightArmPose = rightGun.getArmPose(akimbo);
            } else if (leftGun != null){
                this.leftArmPose = leftGun.getArmPose(akimbo);
            }
        }
    }
}
