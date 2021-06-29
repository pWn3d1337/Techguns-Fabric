package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPart.Cuboid;
import techguns.api.render.ITGModelPart;

@Mixin(ModelPart.class)
public abstract class ModelPartMixin implements ITGModelPart{

    //TODO 1.17 remove?
	/*@Shadow
	private ObjectList<ModelPart.Cuboid> cuboids;
	
	@Shadow
	private ObjectList<ModelPart> children;

	@Override
	public ObjectList<Cuboid> getCuboids() {
		return cuboids;
	}

	@Override
	public ObjectList<ModelPart> getChildren() {
		return children;
	}*/

}
