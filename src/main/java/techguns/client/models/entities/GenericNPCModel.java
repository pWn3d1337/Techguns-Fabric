package techguns.client.models.entities;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

import java.util.function.Function;

public class GenericNPCModel<T extends LivingEntity> extends BipedEntityModel<T> {
    public GenericNPCModel(ModelPart root) {
        super(root);
    }

    public GenericNPCModel(ModelPart root, Function renderLayerFactory) {
        super(root, renderLayerFactory);
    }
}
