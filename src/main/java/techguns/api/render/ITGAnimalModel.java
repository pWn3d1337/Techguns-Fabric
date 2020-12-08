package techguns.api.render;

import net.minecraft.client.model.ModelPart;


public interface ITGAnimalModel {

	public Iterable<ModelPart> _getHeadParts();

	public Iterable<ModelPart> _getBodyParts();
}
