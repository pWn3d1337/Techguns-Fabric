package techguns.api.render;

import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.model.ModelPart;

public interface ITGModelPart {
	
	public ObjectList<ModelPart.Cuboid> getCuboids();
	
	public ObjectList<ModelPart> getChildren();
}
