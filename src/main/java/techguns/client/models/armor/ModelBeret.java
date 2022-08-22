package techguns.client.models.armor;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.EquipmentSlot;
import techguns.client.TGClientEntityModels;

public class ModelBeret extends BipedEntityModel {
    public ModelBeret(ModelPart root) {
        super(root);
    }

    public static ModelData getModelData(Dilation dilation, EquipmentSlot slot) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        if (slot == EquipmentSlot.HEAD) {
            var head = modelPartData.addChild(EntityModelPartNames.HEAD,
                    ModelPartBuilder.create().uv(0, 0)
                            .cuboid(-3.0F, -8.5F, -4.0F, 6, 2, 8, new Dilation(1.5F)),
                    ModelTransform.pivot(0.0F, 0.0F, 0.0F));

            head.addChild(EntityModelPartNames.HEAD+"_side",
                    ModelPartBuilder.create().uv(0, 11)
                            .cuboid(-3.0F, 0.0F, 0.0F, 3, 2, 8, new Dilation(1.4F)),
                    ModelTransform.of(-3.2F, -8.15F, -4.0F, 0.0F, 0.0F, -0.55F)); //-0.6108652381980153F));
        }

        TGClientEntityModels.addMissingParts(modelPartData);
        return modelData;
    }

}
