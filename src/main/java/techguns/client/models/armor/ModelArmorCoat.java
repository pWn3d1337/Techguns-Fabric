package techguns.client.models.armor;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.EquipmentSlot;

import java.util.function.Function;

public class ModelArmorCoat extends BipedEntityModel {

    public ModelArmorCoat(ModelPart root) {
        super(root);
    }

    public ModelArmorCoat(ModelPart root, Function renderLayerFactory) {
        super(root, renderLayerFactory);
    }

    public static ModelData getModelData(Dilation dilation, EquipmentSlot slot) {
        return getModelData(dilation, 0F, 0F, 0F,0F, 0F, 0F, slot);
    }

    public static ModelData getModelData(Dilation dilation, float offsetX, float offsetX_left, float offsetX_right, float offsetY, float offsetY2, float offsetZ, EquipmentSlot slot) {
        ModelData modelData = BipedEntityModel.getModelData(dilation, offsetY); //new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        if (slot == EquipmentSlot.CHEST) {

            var body = modelPartData.getChild(EntityModelPartNames.BODY);
            ModelPartLegacy coatLeft = new ModelPartLegacy(50, 47);
            coatLeft.mirror = true;
            coatLeft.setPivot(7.0F, -0.1F, -0.5F);
            coatLeft.addCuboid(-4.0F, 0.0F, -2.0F, 2, 12, 5, dilation);
            coatLeft.addTo(body, EntityModelPartNames.BODY + "_coatLeft");

            ModelPartLegacy coatback = new ModelPartLegacy(44, 35);
            coatback.setPivot(-1.0F, -0.1F, 4.5F);
            coatback.addCuboid(-4.0F, 0.0F, -2.0F, 10, 12, 0, dilation);
            coatback.addTo(body, EntityModelPartNames.BODY + "_coatback");

            ModelPartLegacy coatRight = new ModelPartLegacy(50, 47);
            coatRight.setPivot(-1.0F, -0.1F, -0.5F);
            coatRight.addCuboid(-4.0F, 0.0F, -2.0F, 2, 12, 5, dilation);
            coatRight.addTo(body, EntityModelPartNames.BODY + "_coatRight");

            var left_arm = modelPartData.getChild(EntityModelPartNames.LEFT_ARM);
            ModelPartLegacy leftShoulder = new ModelPartLegacy(0, 34);
            leftShoulder.mirror = true;
            leftShoulder.setPivot(1.0F, -0.5F, -1.0F);
            leftShoulder.addCuboid(-2.0F, -2.5F, -2.0F, 5, 3, 6, dilation);
            leftShoulder.setRotation(0.0F, 0.0F, 0.22689280275926282F);
            leftShoulder.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_leftShoulder");

            var right_arm = modelPartData.getChild(EntityModelPartNames.RIGHT_ARM);
            ModelPartLegacy rightShoulder = new ModelPartLegacy(0, 34);
            rightShoulder.setPivot(-1.0F, -0.5F, -1.0F);
            rightShoulder.addCuboid(-3.0F, -2.5F, -2.0F, 5, 3, 6, dilation);
            rightShoulder.setRotation(0.0F, 0.0F, -0.22689280275926282F);
            rightShoulder.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_rightShoulder");

        } else if (slot == EquipmentSlot.LEGS) {
            var left_leg = modelPartData.getChild(EntityModelPartNames.LEFT_LEG);
            ModelPartLegacy coatLeftLeg = new ModelPartLegacy(0, 48);
            coatLeftLeg.mirror = true;
            coatLeftLeg.setPivot(0.1F, -0.1F, -0.5F);
            coatLeftLeg.addCuboid(-2.0F, 0.0F, -2.0F, 5, 10, 5, dilation);
            coatLeftLeg.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_coatLeftLeg");

            var right_leg = modelPartData.getChild(EntityModelPartNames.RIGHT_LEG);
            ModelPartLegacy coatRightLeg = new ModelPartLegacy(0, 48);
            coatRightLeg.setPivot(-1.1F, -0.1F, -0.5F);
            coatRightLeg.addCuboid(-2.0F, 0.0F, -2.0F, 5, 10, 5, dilation);
            coatRightLeg.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_coatRightLeg");
        }

        return modelData;
    }

}
