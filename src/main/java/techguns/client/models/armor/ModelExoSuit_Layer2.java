package techguns.client.models.armor;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.EquipmentSlot;

public class ModelExoSuit_Layer2 extends BipedEntityModel {
    public ModelExoSuit_Layer2(ModelPart root) {
        super(root);
    }

    public static ModelData getModelData(Dilation dilation, EquipmentSlot slot) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData Body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.5F))
                .uv(0, 43).cuboid(-5.0F, 11.0F, -3.0F, 10.0F, 2.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 52).cuboid(-1.0F, 13.0F, -3.0F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData RightLeg = modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F))
                .uv(56, 32).cuboid(-3.6F, 0.5F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F))
                .uv(17, 52).cuboid(-3.1F, 5.0F, -3.0F, 5.0F, 2.0F, 6.0F, new Dilation(0.0F))
                .uv(27, 41).cuboid(-4.1F, 4.5F, -1.5F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        ModelPartData LeftLeg = modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)).mirrored(false)
                .uv(56, 32).mirrored().cuboid(1.6F, 0.5F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(17, 52).mirrored().cuboid(-1.9F, 5.0F, -3.0F, 5.0F, 2.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
                .uv(27, 41).mirrored().cuboid(2.1F, 4.5F, -1.5F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

        TGArmorModelRegistry.addMissingParts(modelPartData);
        return modelData;
    }
}
