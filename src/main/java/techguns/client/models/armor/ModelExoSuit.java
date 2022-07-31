package techguns.client.models.armor;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.EquipmentSlot;

public class ModelExoSuit extends BipedEntityModel {
    public ModelExoSuit(ModelPart root) {
        super(root);
    }

    public static ModelData getModelData(Dilation dilation, EquipmentSlot slot) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData Head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData Body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.5F))
                .uv(38, 40).cuboid(-1.5F, 3.0F, 1.5F, 3.0F, 9.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 60).cuboid(-5.0F, 1.0F, 1.5F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData RightArm = modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F))
                .uv(0, 32).cuboid(-4.5F, -3.0F, -3.0F, 6.0F, 5.0F, 6.0F, new Dilation(0.1F))
                .uv(48, 32).cuboid(-4.5F, 2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 32).cuboid(-4.0F, 5.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.1F))
                .uv(27, 41).cuboid(-5.0F, 4.5F, -1.5F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        ModelPartData LeftArm = modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)).mirrored(false)
                .uv(0, 32).mirrored().cuboid(-1.5F, -3.0F, -3.0F, 6.0F, 5.0F, 6.0F, new Dilation(0.1F)).mirrored(false)
                .uv(24, 32).mirrored().cuboid(-2.0F, 5.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.1F)).mirrored(false)
                .uv(48, 32).mirrored().cuboid(2.5F, 2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(27, 41).cuboid(3.0F, 4.5F, -1.5F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

        ModelPartData RightLeg = modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F))
                .uv(40, 56).cuboid(-3.1F, 10.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F))
                .uv(56, 51).cuboid(-1.6F, 9.5F, -3.5F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        ModelPartData LeftLeg = modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)).mirrored(false)
                .uv(40, 56).mirrored().cuboid(-2.9F, 10.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
                .uv(56, 51).mirrored().cuboid(-1.4F, 9.5F, -3.5F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

        TGArmorModelRegistry.addMissingParts(modelPartData);
        return modelData;
    }
}
