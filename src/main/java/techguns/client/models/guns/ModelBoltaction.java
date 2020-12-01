package techguns.client.models.guns;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelBoltaction extends ModelMultipart {
	// fields
	ModelPart Shape1;
	ModelPart Shape2;
	ModelPart Shape3;
	ModelPart Shape4;
	ModelPart Shape5;
	ModelPart Shape6;
	ModelPart Shape7;
	ModelPart Shape8;
	ModelPart Shape9;
	ModelPart Shape10;
	ModelPart Shape11;
	ModelPart Shape12;
	ModelPart Shape13;
	ModelPart Shape14;
	ModelPart Shape15;
	ModelPart Shape16;
	ModelPart Shape17;
	ModelPart Shape18;
	ModelPart Shape19;
	ModelPart Shape20;
	ModelPart Bolt;
	ModelPart Lever;

	public ModelBoltaction() {
        super(RenderLayer::getEntitySolid);
		textureWidth = 128;
		textureHeight = 32;

		Shape1 = new ModelPart(this, 0, 0);
		Shape1.addCuboid(0F, 0F, 1F, 38, 2, 2);
		Shape1.setPivot(-3.5F, -2.5F, -1.5F);
		Shape1.setTextureSize(128, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelPart(this, 44, 24);
		Shape2.addCuboid(0F, 0F, 0F, 9, 2, 1);
		Shape2.setPivot(-19F, 0F, -2F);
		Shape2.setTextureSize(128, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelPart(this, 20, 16);
		Shape3.addCuboid(0F, 0F, 0F, 3, 1, 3);
		Shape3.setPivot(-16F, 3F, -1F);
		Shape3.setTextureSize(128, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelPart(this, 44, 24);
		Shape4.addCuboid(0F, 0F, 0F, 9, 2, 1);
		Shape4.setPivot(-19F, 0F, 2F);
		Shape4.setTextureSize(128, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelPart(this, 32, 17);
		Shape5.addCuboid(0F, 0F, 0F, 3, 7, 3);
		Shape5.setPivot(-19F, -1F, -1F);
		Shape5.setTextureSize(128, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelPart(this, 44, 17);
		Shape6.addCuboid(0F, 0F, 0F, 8, 4, 3);
		Shape6.setPivot(-16F, -1F, -1F);
		Shape6.setTextureSize(128, 32);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape7 = new ModelPart(this, 10, 16);
		Shape7.addCuboid(0F, 0F, 0F, 2, 1, 3);
		Shape7.setPivot(-9F, 3F, -1F);
		Shape7.setTextureSize(128, 32);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Shape8 = new ModelPart(this, 0, 12);
		Shape8.addCuboid(0F, 0F, 0F, 13, 1, 3);
		Shape8.setPivot(1F, 0F, -1F);
		Shape8.setTextureSize(128, 32);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0F, 0F);
		Shape9 = new ModelPart(this, 0, 8);
		Shape9.addCuboid(0F, 0F, 0F, 24, 1, 3);
		Shape9.setPivot(-5F, -1F, -1F);
		Shape9.setTextureSize(128, 32);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);
		Shape10 = new ModelPart(this, 54, 4);
		Shape10.addCuboid(0F, 0F, 0F, 1, 2, 3);
		Shape10.setPivot(1F, -3F, -1F);
		Shape10.setTextureSize(128, 32);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 0F, 0F);
		Shape11 = new ModelPart(this, 32, 12);
		Shape11.addCuboid(0F, 0F, 0F, 7, 2, 3);
		Shape11.setPivot(-6F, 0F, -1F);
		Shape11.setTextureSize(128, 32);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0F);
		Shape12 = new ModelPart(this, 0, 16);
		Shape12.addCuboid(0F, 0F, 0F, 2, 3, 3);
		Shape12.setPivot(-8F, 0F, -1F);
		Shape12.setTextureSize(128, 32);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelPart(this, 69, 11);
		Shape13.addCuboid(0F, 0F, 0F, 3, 3, 3);
		Shape13.setPivot(-7F, -6F, -1F);
		Shape13.setTextureSize(128, 32);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, 0F);
		Shape14 = new ModelPart(this, 54, 4);
		Shape14.addCuboid(0F, 0F, 0F, 1, 2, 3);
		Shape14.setPivot(-3F, -3F, -1F);
		Shape14.setTextureSize(128, 32);
		Shape14.mirror = true;
		setRotation(Shape14, 0F, 0F, 0F);
		Shape15 = new ModelPart(this, 50, 4);
		Shape15.addCuboid(0F, 0F, 0F, 1, 1, 1);
		Shape15.setPivot(-3F, -4F, 0F);
		Shape15.setTextureSize(128, 32);
		Shape15.mirror = true;
		setRotation(Shape15, 0F, 0F, 0F);
		Shape16 = new ModelPart(this, 50, 4);
		Shape16.addCuboid(0F, 0F, 0F, 1, 1, 1);
		Shape16.setPivot(1F, -4F, 0F);
		Shape16.setTextureSize(128, 32);
		Shape16.mirror = true;
		setRotation(Shape16, 0F, 0F, 0F);
		Shape17 = new ModelPart(this, 63, 9);
		Shape17.addCuboid(0F, 0F, 0F, 1, 1, 1);
		Shape17.setPivot(-1F, -5F, -1F);
		Shape17.setTextureSize(128, 32);
		Shape17.mirror = true;
		setRotation(Shape17, 0F, 0F, 0F);
		Shape18 = new ModelPart(this, 57, 11);
		Shape18.addCuboid(0F, 0F, 0F, 3, 3, 3);
		Shape18.setPivot(3F, -6F, -1F);
		Shape18.setTextureSize(128, 32);
		Shape18.mirror = true;
		setRotation(Shape18, 0F, 0F, 0F);
		Shape19 = new ModelPart(this, 62, 4);
		Shape19.addCuboid(0F, 0F, 0F, 8, 2, 2);
		Shape19.setPivot(-5F, -5.5F, -0.5F);
		Shape19.setTextureSize(128, 32);
		Shape19.mirror = true;
		setRotation(Shape19, 0F, 0F, 0F);
		Shape20 = new ModelPart(this, 67, 8);
		Shape20.addCuboid(0F, 0F, 0F, 2, 2, 1);
		Shape20.setPivot(-1.5F, -5.5F, 1.5F);
		Shape20.setTextureSize(128, 32);
		Shape20.mirror = true;
		setRotation(Shape20, 0F, 0F, 0F);
		Bolt = new ModelPart(this, 0, 4);
		Bolt.addCuboid(0F, 0F, 0F, 7, 1, 1);
		Bolt.setPivot(-5.5F, -2F, 0F);
		Bolt.setTextureSize(128, 32);
		Bolt.mirror = true;
		setRotation(Bolt, 0F, 0F, 0F);
		Lever = new ModelPart(this, 16, 4);
		Lever.addCuboid(0F, -1F, -3F, 1, 1, 3);
		Lever.setPivot(-4.5F, -1F, 0F);
		Lever.setTextureSize(128, 32);
		Lever.mirror = true;
		setRotation(Lever, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
			float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
			int overlay) {
	
		Shape1.render(matrices, vertices, light, overlay);
		Shape2.render(matrices, vertices, light, overlay);
		Shape3.render(matrices, vertices, light, overlay);
		Shape4.render(matrices, vertices, light, overlay);
		Shape5.render(matrices, vertices, light, overlay);
		Shape6.render(matrices, vertices, light, overlay);
		Shape7.render(matrices, vertices, light, overlay);
		Shape8.render(matrices, vertices, light, overlay);
		Shape9.render(matrices, vertices, light, overlay);
		Shape10.render(matrices, vertices, light, overlay);
		Shape11.render(matrices, vertices, light, overlay);
		Shape12.render(matrices, vertices, light, overlay);
		Shape13.render(matrices, vertices, light, overlay);
		Shape14.render(matrices, vertices, light, overlay);
		Shape15.render(matrices, vertices, light, overlay);
		Shape16.render(matrices, vertices, light, overlay);
		Shape17.render(matrices, vertices, light, overlay);
		Shape18.render(matrices, vertices, light, overlay);
		Shape19.render(matrices, vertices, light, overlay);
		Shape20.render(matrices, vertices, light, overlay);
		Bolt.render(matrices, vertices, light, overlay);
		Lever.render(matrices, vertices, light, overlay);
	}

}