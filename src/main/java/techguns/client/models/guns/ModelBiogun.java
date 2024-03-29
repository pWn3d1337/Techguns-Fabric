package techguns.client.models.guns;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;
import techguns.client.models.ModelPart;
import techguns.client.render.TGRenderHelper;

public class ModelBiogun extends ModelMultipart {
	// fields
	ModelPart Shape1;
	ModelPart Glow2;
	ModelPart Glow3;
	ModelPart Shape2;
	ModelPart Shape3;
	ModelPart Shape4;
	ModelPart Shape5;
	ModelPart Shape6;
	ModelPart Shape7;
	ModelPart Shape36;
	ModelPart Shape8;
	ModelPart Shape9;
	ModelPart Shape10;
	ModelPart Shape11;
	ModelPart Glow1;
	ModelPart Shape12;
	ModelPart Shape13;
	ModelPart Shape14;
	ModelPart Shape15;
	ModelPart Glow5;
	ModelPart Shape16;
	ModelPart Shape17;
	ModelPart Shape18;
	ModelPart Shape19;
	ModelPart Shape20;
	ModelPart Shape21;
	ModelPart Shape22;
	ModelPart Shape23;
	ModelPart Shape24;
	ModelPart Shape25;
	ModelPart Glow4;
	ModelPart Shape26;
	ModelPart Shape27;
	ModelPart Shape28;
	ModelPart Shape29;
	ModelPart Shape30;
	ModelPart Shape31;
	ModelPart Shape32;
	ModelPart Shape33;
	ModelPart Shape34;
	ModelPart Shape35;
	
	public ModelBiogun() {
        super(RenderLayer::getEntitySolid);
		textureWidth = 64;
		textureHeight = 64;

		Shape1 = new ModelPart(this, 0, 25);
		Shape1.addCuboid(0F, 0F, 0F, 4, 1, 6);
		Shape1.setPivot(-4F, 3.5F, -2.5F);
		Shape1.setTextureSize(64, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Glow2 = new ModelPart(this, 0, 9);
		Glow2.addCuboid(0F, 0F, 0F, 8, 4, 5);
		Glow2.setPivot(0F, 0F, -2F);
		Glow2.setTextureSize(64, 64);
		Glow2.mirror = true;
		setRotation(Glow2, 0F, 0F, 0F);
		Glow3 = new ModelPart(this, 44, 0);
		Glow3.addCuboid(0F, 0F, 0F, 1, 4, 5);
		Glow3.setPivot(9F, 0F, -2F);
		Glow3.setTextureSize(64, 64);
		Glow3.mirror = true;
		setRotation(Glow3, 0F, 0F, 0F);
		Shape2 = new ModelPart(this, 40, 10);
		Shape2.addCuboid(0F, 0F, 0F, 5, 3, 7);
		Shape2.setPivot(10F, 0.5F, -3F);
		Shape2.setTextureSize(64, 64);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelPart(this, 0, 18);
		Shape3.addCuboid(0F, 0F, 0F, 8, 1, 1);
		Shape3.setPivot(0F, -0.5F, -1.5F);
		Shape3.setTextureSize(64, 64);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelPart(this, 42, 20);
		Shape4.addCuboid(0F, 0F, 0F, 5, 1, 6);
		Shape4.setPivot(10F, 3.5F, -2.5F);
		Shape4.setTextureSize(64, 64);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelPart(this, 0, 18);
		Shape5.addCuboid(0F, 0F, 0F, 8, 1, 1);
		Shape5.setPivot(0F, 1.5F, -3F);
		Shape5.setTextureSize(64, 64);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelPart(this, 0, 18);
		Shape6.addCuboid(0F, 0F, 0F, 8, 1, 1);
		Shape6.setPivot(0F, 3.5F, -1.5F);
		Shape6.setTextureSize(64, 64);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape7 = new ModelPart(this, 0, 18);
		Shape7.addCuboid(0F, 0F, 0F, 8, 1, 1);
		Shape7.setPivot(0F, 1.5F, 3F);
		Shape7.setTextureSize(64, 64);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Shape36 = new ModelPart(this, 0, 18);
		Shape36.addCuboid(0F, 0F, 0F, 8, 1, 1);
		Shape36.setPivot(0F, -0.5F, 1.5F);
		Shape36.setTextureSize(64, 64);
		Shape36.mirror = true;
		setRotation(Shape36, 0F, 0F, 0F);
		Shape8 = new ModelPart(this, 0, 18);
		Shape8.addCuboid(0F, 0F, 0F, 8, 1, 1);
		Shape8.setPivot(0F, 3.5F, 1.5F);
		Shape8.setTextureSize(64, 64);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0F, 0F);
		Shape9 = new ModelPart(this, 0, 48);
		Shape9.addCuboid(0F, 0F, 0F, 1, 2, 1);
		Shape9.setPivot(-3F, -2.5F, 1.5F);
		Shape9.setTextureSize(64, 64);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);
		Shape10 = new ModelPart(this, 0, 25);
		Shape10.addCuboid(0F, 0F, 0F, 4, 1, 6);
		Shape10.setPivot(-4F, -0.5F, -2.5F);
		Shape10.setTextureSize(64, 64);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 0F, 0F);
		Shape11 = new ModelPart(this, 42, 20);
		Shape11.addCuboid(0F, 0F, 0F, 5, 1, 6);
		Shape11.setPivot(10F, -0.5F, -2.5F);
		Shape11.setTextureSize(64, 64);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0F);
		Glow1 = new ModelPart(this, 0, 0);
		Glow1.addCuboid(0F, 0F, 0F, 8, 3, 6);
		Glow1.setPivot(0F, 0.5F, -2.5F);
		Glow1.setTextureSize(64, 64);
		Glow1.mirror = true;
		setRotation(Glow1, 0F, 0F, 0F);
		Shape12 = new ModelPart(this, 28, 0);
		Shape12.addCuboid(0F, 0F, 0F, 1, 3, 7);
		Shape12.setPivot(8F, 0.5F, -3F);
		Shape12.setTextureSize(64, 64);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelPart(this, 26, 10);
		Shape13.addCuboid(0F, 0F, 0F, 1, 1, 6);
		Shape13.setPivot(8F, -0.5F, -2.5F);
		Shape13.setTextureSize(64, 64);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, 0F);
		Shape14 = new ModelPart(this, 26, 10);
		Shape14.addCuboid(0F, 0F, 0F, 1, 1, 6);
		Shape14.setPivot(8F, 3.5F, -2.5F);
		Shape14.setTextureSize(64, 64);
		Shape14.mirror = true;
		setRotation(Shape14, 0F, 0F, 0F);
		Shape15 = new ModelPart(this, 20, 22);
		Shape15.addCuboid(0F, 0F, 0F, 4, 3, 7);
		Shape15.setPivot(-4F, 0.5F, -3F);
		Shape15.setTextureSize(64, 64);
		Shape15.mirror = true;
		setRotation(Shape15, 0F, 0F, 0F);
		Glow5 = new ModelPart(this, 32, 52);
		Glow5.addCuboid(-1.5F, 1F, -2.5F, 3, 6, 5);
		Glow5.setPivot(-2.2F, 8F, 0.5F);
		Glow5.setTextureSize(64, 64);
		Glow5.mirror = true;
		setRotation(Glow5, 0F, 0F, -0.5235988F);
		Shape16 = new ModelPart(this, 0, 37);
		Shape16.addCuboid(0F, 0F, 0F, 6, 4, 5);
		Shape16.setPivot(-10F, 0.5F, -2F);
		Shape16.setTextureSize(64, 64);
		Shape16.mirror = true;
		setRotation(Shape16, 0F, 0F, 0F);
		Shape17 = new ModelPart(this, 0, 32);
		Shape17.addCuboid(0F, 0F, 0F, 8, 1, 4);
		Shape17.setPivot(-12F, -0.5F, -1.5F);
		Shape17.setTextureSize(64, 64);
		Shape17.mirror = true;
		setRotation(Shape17, 0F, 0F, 0F);
		Shape18 = new ModelPart(this, 0, 46);
		Shape18.addCuboid(0F, 0F, 0F, 8, 1, 1);
		Shape18.setPivot(-11F, -2.5F, -1.5F);
		Shape18.setTextureSize(64, 64);
		Shape18.mirror = true;
		setRotation(Shape18, 0F, 0F, 0F);
		Shape19 = new ModelPart(this, 0, 46);
		Shape19.addCuboid(0F, 0F, 0F, 8, 1, 1);
		Shape19.setPivot(-11F, -2.5F, 1.5F);
		Shape19.setTextureSize(64, 64);
		Shape19.mirror = true;
		setRotation(Shape19, 0F, 0F, 0F);
		Shape20 = new ModelPart(this, 0, 48);
		Shape20.addCuboid(0F, 0F, 0F, 1, 2, 1);
		Shape20.setPivot(-12F, -2.5F, 1.5F);
		Shape20.setTextureSize(64, 64);
		Shape20.mirror = true;
		setRotation(Shape20, 0F, 0F, 0F);
		Shape21 = new ModelPart(this, 0, 48);
		Shape21.addCuboid(0F, 0F, 0F, 1, 2, 1);
		Shape21.setPivot(-12F, -2.5F, -1.5F);
		Shape21.setTextureSize(64, 64);
		Shape21.mirror = true;
		setRotation(Shape21, 0F, 0F, 0F);
		Shape22 = new ModelPart(this, 0, 48);
		Shape22.addCuboid(0F, 0F, 0F, 1, 2, 1);
		Shape22.setPivot(-3F, -2.5F, -1.5F);
		Shape22.setTextureSize(64, 64);
		Shape22.mirror = true;
		setRotation(Shape22, 0F, 0F, 0F);
		Shape23 = new ModelPart(this, 24, 46);
		Shape23.addCuboid(0F, 0F, 0F, 3, 1, 3);
		Shape23.setPivot(-7F, 7.5F, -1F);
		Shape23.setTextureSize(64, 64);
		Shape23.mirror = true;
		setRotation(Shape23, 0F, 0F, 0F);
		Shape24 = new ModelPart(this, 0, 57);
		Shape24.addCuboid(-5F, 0F, 0F, 5, 2, 5);
		Shape24.setPivot(-0.9F, 5.2F, -2F);
		Shape24.setTextureSize(64, 64);
		Shape24.mirror = true;
		setRotation(Shape24, 0F, 0F, -0.5235988F);
		Shape25 = new ModelPart(this, 48, 52);
		Shape25.addCuboid(-2F, 0F, -2F, 4, 8, 4);
		Shape25.setPivot(-2.2F, 8F, 0.5F);
		Shape25.setTextureSize(64, 64);
		Shape25.mirror = true;
		setRotation(Shape25, 0F, 0F, -0.5235988F);
		Glow4 = new ModelPart(this, 48, 42);
		Glow4.addCuboid(-2.5F, 1F, -1.5F, 5, 6, 3);
		Glow4.setPivot(-2.2F, 8F, 0.5F);
		Glow4.setTextureSize(64, 64);
		Glow4.mirror = true;
		setRotation(Glow4, 0F, 0F, -0.5235988F);
		Shape26 = new ModelPart(this, 22, 40);
		Shape26.addCuboid(0F, 0F, 0F, 4, 3, 3);
		Shape26.setPivot(-11F, 4.5F, -1F);
		Shape26.setTextureSize(64, 64);
		Shape26.mirror = true;
		setRotation(Shape26, 0F, 0F, 0F);
		Shape27 = new ModelPart(this, 36, 39);
		Shape27.addCuboid(0F, 0F, 0F, 3, 7, 2);
		Shape27.setPivot(-11F, 6F, -0.5F);
		Shape27.setTextureSize(64, 64);
		Shape27.mirror = true;
		setRotation(Shape27, 0F, 0F, 0.3490659F);
		Shape28 = new ModelPart(this, 26, 52);
		Shape28.addCuboid(0F, 0F, 0F, 1, 7, 2);
		Shape28.setPivot(-23F, 0F, -0.5F);
		Shape28.setTextureSize(64, 64);
		Shape28.mirror = true;
		setRotation(Shape28, 0F, 0F, 0F);
		Shape29 = new ModelPart(this, 20, 57);
		Shape29.addCuboid(0F, 0F, 0F, 1, 4, 1);
		Shape29.setPivot(-18F, 2.5F, 0F);
		Shape29.setTextureSize(64, 64);
		Shape29.mirror = true;
		setRotation(Shape29, 0F, 0F, 0F);
		Shape30 = new ModelPart(this, 4, 48);
		Shape30.addCuboid(0F, 0F, 0F, 9, 1, 1);
		Shape30.setPivot(-22F, 0.5F, 0F);
		Shape30.setTextureSize(64, 64);
		Shape30.mirror = true;
		setRotation(Shape30, 0F, 0F, 0F);
		Shape31 = new ModelPart(this, 24, 32);
		Shape31.addCuboid(0F, 0F, 0F, 2, 4, 4);
		Shape31.setPivot(-12F, 0.5F, -1.5F);
		Shape31.setTextureSize(64, 64);
		Shape31.mirror = true;
		setRotation(Shape31, 0F, 0F, 0F);
		Shape32 = new ModelPart(this, 18, 50);
		Shape32.addCuboid(0F, 0F, 0F, 1, 4, 3);
		Shape32.setPivot(-13F, 0F, -1F);
		Shape32.setTextureSize(64, 64);
		Shape32.mirror = true;
		setRotation(Shape32, 0F, 0F, 0F);
		Shape33 = new ModelPart(this, 38, 50);
		Shape33.addCuboid(0F, 0F, 0F, 4, 1, 1);
		Shape33.setPivot(-22F, 5.5F, 0F);
		Shape33.setTextureSize(64, 64);
		Shape33.mirror = true;
		setRotation(Shape33, 0F, 0F, 0F);
		Shape34 = new ModelPart(this, 28, 50);
		Shape34.addCuboid(0F, 0F, 0F, 4, 1, 1);
		Shape34.setPivot(-17F, 2.5F, 0F);
		Shape34.setTextureSize(64, 64);
		Shape34.mirror = true;
		setRotation(Shape34, 0F, 0F, 0F);
		Shape35 = new ModelPart(this, 36, 32);
		Shape35.addCuboid(0F, 0F, 0F, 6, 3, 4);
		Shape35.setPivot(-7F, 4.5F, -1.5F);
		Shape35.setTextureSize(64, 64);
		Shape35.mirror = true;
		setRotation(Shape35, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
			float reloadProgress, ModelTransformationMode transformType, int part, float fireProgress, float chargeProgress, int light,
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
		Shape21.render(matrices, vertices, light, overlay);
		Shape22.render(matrices, vertices, light, overlay);
		Shape23.render(matrices, vertices, light, overlay);
		Shape24.render(matrices, vertices, light, overlay);
		Shape25.render(matrices, vertices, light, overlay);
		Shape26.render(matrices, vertices, light, overlay);
		Shape27.render(matrices, vertices, light, overlay);
		Shape28.render(matrices, vertices, light, overlay);
		Shape29.render(matrices, vertices, light, overlay);
		Shape30.render(matrices, vertices, light, overlay);
		Shape31.render(matrices, vertices, light, overlay);
		Shape32.render(matrices, vertices, light, overlay);
		Shape33.render(matrices, vertices, light, overlay);
		Shape34.render(matrices, vertices, light, overlay);
		Shape35.render(matrices, vertices, light, overlay);
		Shape36.render(matrices, vertices, light, overlay);

		TGRenderHelper.enableFXLighting();
		Glow1.render(matrices, vertices, bright_light, overlay);
		Glow2.render(matrices, vertices, bright_light, overlay);
		Glow3.render(matrices, vertices, bright_light, overlay);
		Glow4.render(matrices, vertices, bright_light, overlay);
		Glow5.render(matrices, vertices, bright_light, overlay);
		TGRenderHelper.disableFXLighting();
	}



}

