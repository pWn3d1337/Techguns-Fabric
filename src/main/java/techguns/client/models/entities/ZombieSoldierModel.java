package techguns.client.models.entities;

import net.minecraft.client.model.ModelPart;
import techguns.entities.npcs.ZombieSoldier;

import java.util.function.Function;

public class ZombieSoldierModel extends GenericNPCModel<ZombieSoldier> {
    public ZombieSoldierModel(ModelPart root) {
        super(root);
    }

    public ZombieSoldierModel(ModelPart root, Function renderLayerFactory) {
        super(root, renderLayerFactory);
    }
}
