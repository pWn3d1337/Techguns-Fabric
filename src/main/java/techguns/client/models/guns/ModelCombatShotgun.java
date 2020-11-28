package techguns.client.models.guns;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelCombatShotgun extends ModelMultipart {
	// fields
	ModelPart Shape1;
	ModelPart Shape2;
	ModelPart Shape3;
	ModelPart Pump;
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

	public ModelCombatShotgun() {
        super(RenderLayer::getEntitySolid);
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelPart(this, 50, 4);
		Shape1.addCuboid(0F, 0F, 0F, 2, 1, 1);
		Shape1.setPivot(-7F, 2.5F, 0F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelPart(this, 54, 14);
		Shape2.addCuboid(0F, 0F, 0F, 3, 7, 2);
		Shape2.setPivot(-9F, 1.5F, -0.5F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0.3346075F);
		Shape2.mirror = false;
		Shape3 = new ModelPart(this, 0, 4);
		Shape3.addCuboid(0F, 0F, 0F, 24, 1, 1);
		Shape3.setPivot(8F, -1.5F, 1F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Pump = new ModelPart(this, 0, 23);
		Pump.addCuboid(0F, 0F, 0F, 12, 5, 4);
		Pump.setPivot(7F, -1F, -1.5F);
		Pump.setTextureSize(64, 32);
		Pump.mirror = true;
		setRotation(Pump, 0F, 0F, 0F);
		Shape5 = new ModelPart(this, 0, 4);
		Shape5.addCuboid(0F, 0F, 0F, 24, 1, 1);
		Shape5.setPivot(8F, -1.5F, -1F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelPart(this, 0, 6);
		Shape6.addCuboid(0F, 0F, 0F, 22, 2, 2);
		Shape6.setPivot(8F, 1F, -0.5F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape6.mirror = false;
		Shape7 = new ModelPart(this, 32, 14);
		Shape7.addCuboid(0F, 0F, 0F, 8, 4, 3);
		Shape7.setPivot(-10F, -1.5F, -1F);
		Shape7.setTextureSize(64, 32);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Shape8 = new ModelPart(this, 32, 21);
		Shape8.addCuboid(0F, 0F, 0F, 10, 1, 2);
		Shape8.setPivot(-2F, -3F, -0.5F);
		Shape8.setTextureSize(64, 32);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0F, 0F);
		Shape8.mirror = false;
		Shape9 = new ModelPart(this, 0, 0);
		Shape9.addCuboid(0F, 0F, 0F, 24, 3, 1);
		Shape9.setPivot(8F, -2.5F, 0F);
		Shape9.setTextureSize(64, 32);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);
		Shape10 = new ModelPart(this, 32, 24);
		Shape10.addCuboid(0F, 0F, 0F, 10, 5, 3);
		Shape10.setPivot(-2F, -2F, -1F);
		Shape10.setTextureSize(64, 32);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 0F, 0F);
		Shape11 = new ModelPart(this, 50, 0);
		Shape11.addCuboid(0F, 0F, 0F, 2, 1, 1);
		Shape11.setPivot(27F, 0.5F, 0F);
		Shape11.setTextureSize(64, 32);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0F);
		Shape12 = new ModelPart(this, 46, 9);
		Shape12.addCuboid(0F, 0F, 0F, 7, 1, 2);
		Shape12.setPivot(-9F, -2.5F, -0.5F);
		Shape12.setTextureSize(64, 32);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelPart(this, 50, 2);
		Shape13.addCuboid(0F, 0F, 0F, 2, 1, 1);
		Shape13.setPivot(-7.5F, -3.5F, 0F);
		Shape13.setTextureSize(64, 32);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, 0F);
		Shape14 = new ModelPart(this, 50, 0);
		Shape14.addCuboid(0F, 0F, 0F, 2, 1, 1);
		Shape14.setPivot(29F, -3.5F, 0F);
		Shape14.setTextureSize(64, 32);
		Shape14.mirror = true;
		setRotation(Shape14, 0F, 0F, 0F);
		Shape15 = new ModelPart(this, 18, 10);
		Shape15.addCuboid(0F, 0F, 0F, 2, 5, 3);
		Shape15.setPivot(-21F, -0.5F, -1F);
		Shape15.setTextureSize(64, 32);
		Shape15.mirror = true;
		setRotation(Shape15, 0F, 0F, 0F);
		Shape16 = new ModelPart(this, 0, 19);
		Shape16.addCuboid(0F, 0F, 0F, 12, 2, 2);
		Shape16.setPivot(-21F, 2.5F, -0.5F);
		Shape16.setTextureSize(64, 32);
		Shape16.mirror = true;
		setRotation(Shape16, 0F, 0F, -0.2617994F);
		Shape17 = new ModelPart(this, 10, 10);
		Shape17.addCuboid(0F, 0F, 0F, 2, 3, 2);
		Shape17.setPivot(-23F, 5F, -0.5F);
		Shape17.setTextureSize(64, 32);
		Shape17.mirror = true;
		setRotation(Shape17, 0F, 0F, 0F);
		Shape17.mirror = false;
		Shape18 = new ModelPart(this, 0, 10);
		Shape18.addCuboid(0F, 0F, 0F, 2, 5, 3);
		Shape18.setPivot(-23F, 0F, -1F);
		Shape18.setTextureSize(64, 32);
		Shape18.mirror = true;
		setRotation(Shape18, 0F, 0F, 0F);
		Shape18.mirror = false;
		Shape19 = new ModelPart(this, 10, 15);
		Shape19.addCuboid(0F, 0F, 0F, 1, 2, 2);
		Shape19.setPivot(-21F, 4.5F, -0.5F);
		Shape19.setTextureSize(64, 32);
		Shape19.mirror = true;
		setRotation(Shape19, 0F, 0F, 0F);
		Shape20 = new ModelPart(this, 29, 12);
		Shape20.addCuboid(0F, 0F, 0F, 10, 1, 1);
		Shape20.setPivot(-19.5F, 0F, 0F);
		Shape20.setTextureSize(64, 32);
		Shape20.mirror = true;
		setRotation(Shape20, 0F, 0F, -0.1396263F);
	}

	
	
	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
		float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
	int overlay) {

		if (part==0){
			Shape1.render(matrices, vertices, light, overlay);
			Shape2.render(matrices, vertices, light, overlay);
			Shape3.render(matrices, vertices, light, overlay);
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
		} else {
			float pumpprogress=0f;
			if(fireProgress>=0.65 && fireProgress<=0.95f) {
				pumpprogress = (fireProgress-0.65f)*3.333333f;
			} else if (reloadProgress>=0.9f) {
				pumpprogress = (reloadProgress-0.9f)*10f;
			}
			if(pumpprogress>0.5f) {
				pumpprogress=1f-pumpprogress;
			}
			matrices.push();
			matrices.translate(-0.8f*pumpprogress,0,0);
			Pump.render(matrices, vertices, light, overlay);
			matrices.pop();
		}
	}
}
