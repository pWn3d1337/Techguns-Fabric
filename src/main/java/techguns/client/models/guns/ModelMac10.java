package techguns.client.models.guns;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import techguns.client.models.ModelPart;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelMac10 extends ModelMultipart {

	public ModelPart Top;
	public ModelPart Grip3;
	public ModelPart Barrel;
	public ModelPart IS2;
	public ModelPart Grip2;
	public ModelPart IS1;
	public ModelPart Trigger;
	public ModelPart Grip5;
	public ModelPart Grip1;
	public ModelPart Grip4;
	public ModelPart Magazine;
	public ModelPart Stock1;
	public ModelPart Stock2;
	public ModelPart Stock3;
	public ModelPart Stock4;
	public ModelPart Stock3_2;
	public ModelPart Stock4_2;
	public ModelPart Stock5;
	public ModelPart Stock4_3;
	public ModelPart Stock3_3;
	public ModelPart IS3;
	public ModelPart IS3_1;
	public ModelPart Stock6;

	public ModelMac10() {
        super(RenderLayer::getEntitySolid);
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.Grip4 = new ModelPart(this, 11, 18);
		this.Grip4.setPivot(-1.5F, 2.5F, 10.0F);
		this.Grip4.addCuboid(0.0F, 0.0F, 0.0F, 2, 3, 1, -0.1F);
		this.setRotation(Grip4, -0.2181661564992912F, -0.0F, 0.0F);
		this.Stock2 = new ModelPart(this, 25, 25);
		this.Stock2.setPivot(-1.5F, 2.0F, 14.0F);
		this.Stock2.addCuboid(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.IS3 = new ModelPart(this, 10, 22);
		this.IS3.setPivot(0.5F, -2.0F, -2.3F);
		this.IS3.addCuboid(0.0F, 0.0F, 0.0F, 0, 1, 1, 0.0F);
		this.IS2 = new ModelPart(this, 0, 21);
		this.IS2.setPivot(-1.0F, -1.7F, -2.8F);
		this.IS2.addCuboid(0.0F, 0.0F, 0.0F, 1, 1, 2, -0.3F);
		this.Stock4_2 = new ModelPart(this, 26, 15);
		this.Stock4_2.setPivot(0.4F, -1.4F, 11.7F);
		this.Stock4_2.addCuboid(-0.5F, -0.5F, 0.0F, 1, 1, 4, -0.2F);
		this.setRotation(Stock4_2, 0.08726646259971647F, -0.054105206811824215F, 0.7853981633974483F);
		this.Stock3_2 = new ModelPart(this, 25, 12);
		this.Stock3_2.setPivot(0.5F, -2.0F, 15.5F);
		this.Stock3_2.addCuboid(-0.5F, 0.0F, -0.5F, 1, 5, 1, -0.2F);
		this.setRotation(Stock3_2, 0.0F, 0.7853981633974483F, 0.0F);
		this.Top = new ModelPart(this, 1, 11);
		this.Top.setPivot(-2.0F, -1.0F, -3.0F);
		this.Top.addCuboid(0.0F, 0.0F, 0.0F, 3, 4, 17, 0.0F);
		this.Stock3 = new ModelPart(this, 25, 12);
		this.Stock3.setPivot(-1.5F, -2.0F, 15.5F);
		this.Stock3.addCuboid(-0.5F, 0.0F, -0.5F, 1, 5, 1, -0.2F);
		this.setRotation(Stock3, 0.0F, 0.7853981633974483F, 0.0F);
		this.Stock1 = new ModelPart(this, 25, 21);
		this.Stock1.setPivot(-2.0F, 2.0F, 15.0F);
		this.Stock1.addCuboid(0.0F, 0.0F, 0.0F, 3, 2, 1, 0.0F);
		this.Stock6 = new ModelPart(this, 20, 0);
		this.Stock6.setPivot(-0.5F, -0.4F, 13.38F);
		this.Stock6.addCuboid(-1.5F, -1.5F, 0.0F, 3, 3, 1, -0.45F);
		this.setRotation(Stock6, 0.0F, 0.0F, 0.7853981633974483F);
		this.Stock5 = new ModelPart(this, 24, 5);
		this.Stock5.setPivot(-1.0F, -1.4F, 10.4F);
		this.Stock5.addCuboid(-0.5F, -0.5F, -0.5F, 2, 1, 1, -0.2F);
		this.setRotation(Stock5, 0.7853981633974483F, 0.0F, 0.0F);
		this.Grip5 = new ModelPart(this, 11, 11);
		this.Grip5.setPivot(-1.5F, 5.2F, 9.3F);
		this.Grip5.addCuboid(0.0F, 0.0F, 0.0F, 2, 5, 1, -0.1F);
		this.setRotation(Grip5, 0.1291543646475804F, -0.0F, 0.0F);
		this.Magazine = new ModelPart(this, 0, 10);
		this.Magazine.setPivot(-1.5F, 9.7F, 7.0F);
		this.Magazine.addCuboid(0.0F, 0.0F, 0.0F, 2, 7, 3, -0.3F);
		this.Stock3_3 = new ModelPart(this, 25, 8);
		this.Stock3_3.setPivot(-1.4F, -1.4F, 10.2F);
		this.Stock3_3.addCuboid(-0.5F, -0.5F, 0.0F, 1, 1, 2, -0.2F);
		this.setRotation(Stock3_3, 0.0F, 0.0F, 0.7853981633974483F);
		this.Grip1 = new ModelPart(this, 11, 0);
		this.Grip1.setPivot(-1.0F, 3.0F, 3.0F);
		this.Grip1.addCuboid(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.Stock4 = new ModelPart(this, 26, 15);
		this.Stock4.setPivot(-1.4F, -1.4F, 11.7F);
		this.Stock4.addCuboid(-0.5F, -0.5F, 0.0F, 1, 1, 4, -0.2F);
		this.setRotation(Stock4, 0.054105206811824215F, -0.08726646259971647F, 0.7853981633974483F);
		this.Barrel = new ModelPart(this, 1, 20);
		this.Barrel.setPivot(-0.5F, 0.25F, -6.0F);
		this.Barrel.addCuboid(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
		this.setRotation(Barrel, 0.0F, 0.0F, 0.7853981633974483F);
		this.Trigger = new ModelPart(this, 11, 7);
		this.Trigger.setPivot(-1.0F, 2.5F, 6.5F);
		this.Trigger.addCuboid(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.setRotation(Trigger, -0.40893064374227134F, -0.0F, 0.0F);
		this.Grip3 = new ModelPart(this, 0, 0);
		this.Grip3.setPivot(-1.5F, 3.0F, 7.0F);
		this.Grip3.addCuboid(0.0F, 0.0F, 0.0F, 2, 7, 3, 0.0F);
		this.Stock4_3 = new ModelPart(this, 25, 8);
		this.Stock4_3.setPivot(0.4F, -1.4F, 10.2F);
		this.Stock4_3.addCuboid(-0.5F, -0.5F, 0.0F, 1, 1, 2, -0.2F);
		this.setRotation(Stock4_3, 0.0F, 0.0F, 0.7853981633974483F);
		this.IS1 = new ModelPart(this, 12, 23);
		this.IS1.setPivot(-0.5F, -1.7F, 3.4F);
		this.IS1.addCuboid(-0.5F, 0.0F, -0.5F, 1, 1, 1, 0.0F);
		this.setRotation(IS1, 0.0F, 0.7853981633974483F, 0.0F);
		this.IS3_1 = new ModelPart(this, 10, 22);
		this.IS3_1.setPivot(-1.5F, -2.0F, -2.3F);
		this.IS3_1.addCuboid(0.0F, 0.0F, 0.0F, 0, 1, 1, 0.0F);
		this.Grip2 = new ModelPart(this, 13, 2);
		this.Grip2.setPivot(-1.0F, 5.0F, 4.0F);
		this.Grip2.addCuboid(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
	}

	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
					   float reloadProgress, ModelTransformationMode transformType, int part, float fireProgress, float chargeProgress, int light,
					   int overlay) {

		this.Grip3.render(matrices, vertices, light, overlay);
		this.Grip4.render(matrices, vertices, light, overlay);
		this.Stock2.render(matrices, vertices, light, overlay);
		this.Top.render(matrices, vertices, light, overlay);
		this.Stock1.render(matrices, vertices, light, overlay);
		this.IS3_1.render(matrices, vertices, light, overlay);
		this.Grip2.render(matrices, vertices, light, overlay);
		this.Trigger.render(matrices, vertices, light, overlay);
		this.Grip5.render(matrices, vertices, light, overlay);
		this.Stock4_2.render(matrices, vertices, light, overlay);
		this.Stock3_2.render(matrices, vertices, light, overlay);
		this.Stock4.render(matrices, vertices, light, overlay);
		this.Stock4_3.render(matrices, vertices, light, overlay);
		this.Stock3_3.render(matrices, vertices, light, overlay);
		this.IS1.render(matrices, vertices, light, overlay);
		this.IS3.render(matrices, vertices, light, overlay);
		this.Stock5.render(matrices, vertices, light, overlay);
		this.Stock6.render(matrices, vertices, light, overlay);
		this.Barrel.render(matrices, vertices, light, overlay);
		this.Grip1.render(matrices, vertices, light, overlay);
		this.IS2.render(matrices, vertices, light, overlay);
		this.Magazine.render(matrices, vertices, light, overlay);
		this.Stock3.render(matrices, vertices, light, overlay);
	}
}