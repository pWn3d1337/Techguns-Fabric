package techguns.client.models.items;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;
import techguns.client.models.ModelPart;

public class ModelLmgMag extends ModelMultipart {
	// fields
	ModelPart Mag03;
	ModelPart Mag01;
	ModelPart Mag02;
	ModelPart Bullet06;
	ModelPart Bullet05;
	ModelPart Bullet04;
	ModelPart Bullet03;
	ModelPart Bullet02;
	ModelPart Bullet01;

	protected boolean empty;

	public ModelLmgMag(boolean empty) {
		super(RenderLayer::getEntitySolid);
		textureWidth = 64;
		textureHeight = 32;
		this.empty = empty;

		Mag03 = new ModelPart(this, 0, 0);
		Mag03.addCuboid(0F, 0F, 0F, 3, 6, 7);
		Mag03.setPivot(-3.5F, 1F, -6F);
		Mag03.setTextureSize(128, 128);
		Mag03.mirror = true;
		setRotation(Mag03, 0F, 0F, 0.7888888F);
		Mag01 = new ModelPart(this, 0, 14);
		Mag01.addCuboid(0F, 0F, 0F, 13, 6, 7);
		Mag01.setPivot(-7.5F, 5F, -6F);
		Mag01.setTextureSize(128, 128);
		Mag01.mirror = true;
		setRotation(Mag01, 0F, 0F, 0F);
		Mag02 = new ModelPart(this, 22, 2);
		Mag02.addCuboid(0F, 0F, 0F, 9, 4, 7);
		Mag02.setPivot(-3.5F, 1F, -6F);
		Mag02.setTextureSize(128, 128);
		Mag02.mirror = true;
		setRotation(Mag02, 0F, 0F, 0F);
		if (!empty) {
			Bullet06 = new ModelPart(this, 41, 20);
			Bullet06.addCuboid(0F, 0F, 0F, 1, 1, 6);
			Bullet06.setPivot(4.5F, 0.5F, -5.5F);
			Bullet06.setTextureSize(128, 128);
			Bullet06.mirror = true;
			setRotation(Bullet06, 0F, 0F, 0.7853982F);
			Bullet05 = new ModelPart(this, 41, 20);
			Bullet05.addCuboid(0F, 0F, 0F, 1, 1, 6);
			Bullet05.setPivot(4F, -0.5F, -5.5F);
			Bullet05.setTextureSize(128, 128);
			Bullet05.mirror = true;
			setRotation(Bullet05, 0F, 0F, 0.7853982F);
			Bullet04 = new ModelPart(this, 41, 20);
			Bullet04.addCuboid(0F, 0F, 0F, 1, 1, 6);
			Bullet04.setPivot(3F, -0.5F, -5.5F);
			Bullet04.setTextureSize(128, 128);
			Bullet04.mirror = true;
			setRotation(Bullet04, 0F, 0F, 0.7853982F);
			Bullet03 = new ModelPart(this, 41, 20);
			Bullet03.addCuboid(0F, 0F, 0F, 1, 1, 6);
			Bullet03.setPivot(2F, -0.5F, -5.5F);
			Bullet03.setTextureSize(128, 128);
			Bullet03.mirror = true;
			setRotation(Bullet03, 0F, 0F, 0.7853982F);
			Bullet02 = new ModelPart(this, 41, 20);
			Bullet02.addCuboid(0F, 0F, 0F, 1, 1, 6);
			Bullet02.setPivot(1F, -0.5F, -5.5F);
			Bullet02.setTextureSize(128, 128);
			Bullet02.mirror = true;
			setRotation(Bullet02, 0F, 0F, 0.7853982F);
			Bullet01 = new ModelPart(this, 41, 20);
			Bullet01.addCuboid(0F, 0F, 0F, 1, 1, 6);
			Bullet01.setPivot(0F, -0.5F, -5.5F);
			Bullet01.setTextureSize(128, 128);
			Bullet01.mirror = true;
			setRotation(Bullet01, 0F, 0F, 0.7853982F);
		}
	}

	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
			float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
			int overlay) {

		Mag03.render(matrices, vertices, light, overlay);
		Mag01.render(matrices, vertices, light, overlay);
		Mag02.render(matrices, vertices, light, overlay);

		if (!empty) {
			Bullet06.render(matrices, vertices, light, overlay);
			Bullet05.render(matrices, vertices, light, overlay);
			Bullet04.render(matrices, vertices, light, overlay);
			Bullet03.render(matrices, vertices, light, overlay);
			Bullet02.render(matrices, vertices, light, overlay);
			Bullet01.render(matrices, vertices, light, overlay);
		}
	}

}