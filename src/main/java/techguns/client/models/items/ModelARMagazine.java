package techguns.client.models.items;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;
import techguns.client.models.ModelPart;

public class ModelARMagazine extends ModelMultipart {

	// fields
	ModelPart Magazine02;
	ModelPart Magazine01;
	ModelPart Magazine03;
	ModelPart Magazine04;

	protected boolean empty=false;
	
	public ModelARMagazine(boolean empty) {
		super(RenderLayer::getEntitySolid);
		
		textureWidth = 32;
		textureHeight = 32;

		this.empty=empty;
		
		Magazine02 = new ModelPart(this, 0, 0);
		Magazine02.addCuboid(0F, 0F, 0F, 2, 4, 6);
		Magazine02.setPivot(-1.5F, 9F, -4.4F);
		Magazine02.mirror = true;
		setRotation(Magazine02, -0.1570796F, 0F, 0F);
		Magazine01 = new ModelPart(this, 0, 11);
		Magazine01.addCuboid(0F, 0F, 0F, 2, 5, 6);
		Magazine01.setPivot(-1.5F, 5F, -4.5F);
		Magazine01.mirror = true;
		setRotation(Magazine01, 0F, 0F, 0F);
		Magazine03 = new ModelPart(this, 0, 11);
		Magazine03.addCuboid(0F, 0F, 0F, 2, 2, 6);
		Magazine03.setPivot(-1.5F, 3F, -4.5F);
		Magazine03.mirror = true;
		setRotation(Magazine03, 0F, 0F, 0F);
		
		if (!empty){
			Magazine04 = new ModelPart(this, 0, 22);
			Magazine04.addCuboid(0F, 0F, 0F, 1, 1, 5);
			Magazine04.setPivot(-1.25F, 3F, -4F);
			Magazine04.mirror = true;
			setRotation(Magazine04, 0F, 0F, -0.7853982F);
		}
	}
	
	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
			float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
			int overlay) {
		Magazine02.render(matrices, vertices, light, overlay);
		Magazine01.render(matrices, vertices, light, overlay);
		Magazine03.render(matrices, vertices, light, overlay);
		if (!empty){
			Magazine04.render(matrices, vertices, light, overlay);
		}
	}

}
