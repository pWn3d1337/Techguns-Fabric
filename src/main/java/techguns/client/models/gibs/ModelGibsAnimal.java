package techguns.client.models.gibs;

import java.util.ArrayList;
import java.util.function.Function;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import techguns.api.render.ITGAnimalModel;

public class ModelGibsAnimal extends ModelGibs{
	
	public AnimalModel<?> model;
	public ArrayList<ModelPart> parts;

	public ModelGibsAnimal(AnimalModel<?> model) {
		super(RenderLayer::getEntityCutoutNoCull);
		this.model = model;
		this.parts = new ArrayList<>();
		for (ModelPart part : ((ITGAnimalModel)model)._getBodyParts()) {
			part.setPivot(0, 0, 0);
			parts.add(part);
		}
		for (ModelPart part : ((ITGAnimalModel)model)._getHeadParts()) {
			part.setPivot(0, 0, 0);
			parts.add(part);
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int part) {
		if (part < 0 || part >= this.getNumGibs()) return;
		
		ModelPart modelPart = this.parts.get(part);
		modelPart.render(matrices, vertices, light, OverlayTexture.DEFAULT_UV);
	}

	@Override
	public int getNumGibs() {
		return parts.size();
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
			float blue, float alpha) {
		//No
	}

}
