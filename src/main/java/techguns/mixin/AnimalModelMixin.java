package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import techguns.api.render.ITGAnimalModel;

@Mixin(AnimalModel.class)
public abstract class AnimalModelMixin<E extends Entity> extends EntityModel<E> implements ITGAnimalModel {
	
	@Override
	public Iterable<ModelPart> _getBodyParts() {
		return getBodyParts();
	}
	
	@Override
	public Iterable<ModelPart> _getHeadParts() {
		return getHeadParts();
	}
	
	@Shadow
	public abstract Iterable<ModelPart> getHeadParts();

	@Shadow
	public abstract Iterable<ModelPart> getBodyParts();
}
