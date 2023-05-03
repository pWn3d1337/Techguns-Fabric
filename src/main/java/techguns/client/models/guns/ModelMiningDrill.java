package techguns.client.models.guns;

import net.minecraft.client.render.model.json.ModelTransformationMode;
import techguns.client.models.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import techguns.TGIdentifier;
import techguns.api.guns.IGenericGun;
import techguns.client.models.ModelMultipart;
import techguns.client.render.math.TGMatrixOps;
import techguns.items.guns.GenericGunMeleeCharge;

public class ModelMiningDrill extends ModelMultipart {
    protected static final Identifier[] textures = new Identifier[]{
            new TGIdentifier("textures/guns/miningdrill_obsidian.png"),
            new TGIdentifier("textures/guns/miningdrill_carbon.png")
    };

	public ModelPart Drill01;
    public ModelPart Drill02;
    public ModelPart Drill03;
    public ModelPart Drill04;
    public ModelPart Drill05;
    public ModelPart Drill06;
    public ModelPart Drill07;
    public ModelPart Drill08;
    public ModelPart Drill09;
    public ModelPart Drill010;
    public ModelPart Drill011;
    public ModelPart Shape9;
    public ModelPart Shape13;
    public ModelPart Shape15;
    public ModelPart Shape151;
    public ModelPart Shape131;
    public ModelPart Shape91;
    public ModelPart Shape31;
    public ModelPart Shape152;
    public ModelPart Shape32;
    public ModelPart Shape33;
    public ModelPart Shape34;
    public ModelPart Shape131_1;
    public ModelPart Shape132;
    public ModelPart Shape133;
    public ModelPart Shape92;
    public ModelPart Shape93;
    public ModelPart Shape134;
    public ModelPart Shape135;
    public ModelPart Shape136;
    public ModelPart Grip01;
    public ModelPart Grip02;
    public ModelPart Grip03;
    public ModelPart Grip03_1;
    public ModelPart Grip;
    public ModelPart Grip2_01;
    public ModelPart Grip2_02;
    public ModelPart Grip2_03;

    public ModelMiningDrill() {
        super(RenderLayer::getEntitySolid);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Shape33 = new ModelPart(this, 0, 27);
        this.Shape33.setPivot(3.0F, -1.5F, -1.5F);
        this.Shape33.addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 4, 0.0F);
        this.Drill011 = new ModelPart(this, 48, 0);
        this.Drill011.setPivot(16.5F, 1.5F, 0.5F);
        this.Drill011.addCuboid(0.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotation(Drill011, 0.7853981633974483F, 0.0F, 0.0F);
        this.Shape32 = new ModelPart(this, 1, 32);
        this.Shape32.setPivot(3.0F, 4.0F, -1.0F);
        this.Shape32.addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.Shape135 = new ModelPart(this, 0, 36);
        this.Shape135.setPivot(-9.0F, -2.0F, -1.5F);
        this.Shape135.addCuboid(0.0F, 0.0F, 0.0F, 2, 1, 4, 0.0F);
        this.Shape9 = new ModelPart(this, 45, 25);
        this.Shape9.setPivot(5.0F, -0.5F, -3.0F);
        this.Shape9.addCuboid(-1.0F, -4.0F, 0.0F, 1, 4, 7, 0.0F);
        this.setRotation(Shape9, 0.0F, 0.0F, -0.39269908169872414F);
        this.Shape31 = new ModelPart(this, 0, 27);
        this.Shape31.setPivot(3.0F, 3.5F, -1.5F);
        this.Shape31.addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 4, 0.0F);
        this.Drill06 = new ModelPart(this, 17, 14);
        this.Drill06.setPivot(11.0F, 1.5F, 0.5F);
        this.Drill06.addCuboid(0.0F, -3.0F, -3.0F, 2, 6, 6, 0.0F);
        this.Shape134 = new ModelPart(this, 0, 36);
        this.Shape134.setPivot(-1.5F, -1.9F, -1.5F);
        this.Shape134.addCuboid(0.0F, 0.0F, 0.0F, 5, 1, 4, 0.0F);
        this.Grip02 = new ModelPart(this, 12, 14);
        this.Grip02.setPivot(-12.0F, -2.0F, -0.5F);
        this.Grip02.addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.Drill07 = new ModelPart(this, 0, 56);
        this.Drill07.setPivot(12.0F, 1.5F, 0.5F);
        this.Drill07.addCuboid(0.0F, -2.0F, -2.0F, 2, 4, 4, 0.0F);
        this.setRotation(Drill07, 0.7853981633974483F, 0.0F, 0.0F);
        this.Shape13 = new ModelPart(this, 24, 36);
        this.Shape13.setPivot(-9.0F, 3.0F, -1.5F);
        this.Shape13.addCuboid(0.0F, 0.0F, 0.0F, 12, 1, 4, 0.0F);
        this.Shape151 = new ModelPart(this, 47, 0);
        this.Shape151.setPivot(-7.0F, -2.5F, -2.5F);
        this.Shape151.addCuboid(0.0F, 0.0F, 0.0F, 1, 2, 6, 0.0F);
        this.Shape34 = new ModelPart(this, 1, 32);
        this.Shape34.setPivot(3.0F, -2.0F, -1.0F);
        this.Shape34.addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.Shape92 = new ModelPart(this, 13, 26);
        this.Shape92.setPivot(4.0F, -0.5F, -2.5F);
        this.Shape92.addCuboid(0.0F, 0.0F, 0.0F, 2, 4, 6, 0.0F);
        this.Shape132 = new ModelPart(this, 0, 20);
        this.Shape132.setPivot(-6.0F, -2.0F, -1.5F);
        this.Shape132.addCuboid(0.0F, 0.0F, 0.0F, 4, 1, 4, 0.0F);
        this.Drill03 = new ModelPart(this, 22, 50);
        this.Drill03.setPivot(8.0F, 1.5F, 0.5F);
        this.Drill03.addCuboid(0.0F, -3.5F, -3.5F, 2, 7, 7, 0.0F);
        this.setRotation(Drill03, 0.7853981633974483F, 0.0F, 0.0F);
        this.Grip = new ModelPart(this, 47, 42);
        this.Grip.setPivot(0.5F, 1.9F, 3.5F);
        this.Grip.addCuboid(-1.0F, -1.0F, 0.0F, 1, 1, 5, 0.0F);
        this.setRotation(Grip, 0.0F, -0.0F, 0.7853981633974483F);
        this.Shape91 = new ModelPart(this, 29, 23);
        this.Shape91.setPivot(4.0F, -0.5F, -3.0F);
        this.Shape91.addCuboid(0.0F, 0.0F, 0.0F, 1, 6, 7, 0.0F);
        this.Drill02 = new ModelPart(this, 41, 48);
        this.Drill02.setPivot(6.0F, 1.5F, 0.5F);
        this.Drill02.addCuboid(0.0F, -4.0F, -4.0F, 2, 8, 8, 0.0F);
        this.setRotation(Drill02, 0.7853981633974483F, 0.0F, 0.0F);
        this.Shape15 = new ModelPart(this, 27, 9);
        this.Shape15.setPivot(-6.5F, -0.5F, -2.5F);
        this.Shape15.addCuboid(0.0F, 0.0F, 0.0F, 11, 4, 6, 0.0F);
        this.Drill08 = new ModelPart(this, 0, 56);
        this.Drill08.setPivot(13.0F, 1.5F, 0.5F);
        this.Drill08.addCuboid(0.0F, -2.0F, -2.0F, 2, 4, 4, 0.0F);
        this.Shape131_1 = new ModelPart(this, 10, 41);
        this.Shape131_1.setPivot(-9.0F, -1.0F, -2.0F);
        this.Shape131_1.addCuboid(0.0F, 0.0F, 0.0F, 13, 4, 5, 0.0F);
        this.Grip01 = new ModelPart(this, 0, 10);
        this.Grip01.setPivot(-17.0F, 2.9F, -0.5F);
        this.Grip01.addCuboid(0.0F, 0.0F, 0.0F, 9, 1, 2, 0.0F);
        this.Grip2_03 = new ModelPart(this, 0, 4);
        this.Grip2_03.setPivot(0.0F, -5.3F, -2.1F);
        this.Grip2_03.addCuboid(0.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
        this.Drill09 = new ModelPart(this, 56, 0);
        this.Drill09.setPivot(14.5F, 1.5F, 0.5F);
        this.Drill09.addCuboid(0.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.Grip03_1 = new ModelPart(this, 39, 26);
        this.Grip03_1.setPivot(-15.8F, -0.6F, -0.43F);
        this.Grip03_1.addCuboid(-4.0F, 0.0F, 0.0F, 4, 1, 2, 0.0F);
        this.setRotation(Grip03_1, 0.0F, 0.0F, -1.265363707695889F);
        this.Shape93 = new ModelPart(this, 54, 49);
        this.Shape93.setPivot(0.5F, 0.9F, 2.5F);
        this.Shape93.addCuboid(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
        this.Shape136 = new ModelPart(this, 0, 14);
        this.Shape136.setPivot(-5.5F, -2.5F, -1.0F);
        this.Shape136.addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.Shape131 = new ModelPart(this, 0, 50);
        this.Shape131.setPivot(-5.0F, 4.0F, -1.0F);
        this.Shape131.addCuboid(0.0F, 0.0F, 0.0F, 8, 1, 3, 0.0F);
        this.Grip03 = new ModelPart(this, 39, 23);
        this.Grip03.setPivot(-12.0F, -2.0F, -0.45F);
        this.Grip03.addCuboid(-4.0F, 0.0F, 0.0F, 4, 1, 2, 0.0F);
        this.setRotation(Grip03, 0.0F, 0.0F, -0.36128315516282616F);
        this.Drill04 = new ModelPart(this, 22, 50);
        this.Drill04.setPivot(9.0F, 1.5F, 0.5F);
        this.Drill04.addCuboid(0.0F, -3.5F, -3.5F, 2, 7, 7, 0.0F);
        this.Drill010 = new ModelPart(this, 56, 0);
        this.Drill010.setPivot(14.0F, 1.5F, 0.5F);
        this.Drill010.addCuboid(0.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotation(Drill010, 0.7853981633974483F, 0.0F, 0.0F);
        this.Shape152 = new ModelPart(this, 47, 0);
        this.Shape152.setPivot(-2.0F, -2.5F, -2.5F);
        this.Shape152.addCuboid(0.0F, 0.0F, 0.0F, 1, 2, 6, 0.0F);
        this.Grip2_01 = new ModelPart(this, 47, 42);
        this.Grip2_01.setPivot(0.5F, -4.5F, -2.0F);
        this.Grip2_01.addCuboid(-1.0F, -1.0F, 0.0F, 1, 1, 5, 0.0F);
        this.setRotation(Grip2_01, 0.0F, -0.0F, 0.7853981633974483F);
        this.Shape133 = new ModelPart(this, 0, 41);
        this.Shape133.setPivot(-10.0F, -1.5F, -1.5F);
        this.Shape133.addCuboid(0.0F, 0.0F, 0.0F, 1, 5, 4, 0.0F);
        this.Drill01 = new ModelPart(this, 41, 48);
        this.Drill01.setPivot(7.0F, 1.5F, 0.5F);
        this.Drill01.addCuboid(0.0F, -4.0F, -4.0F, 2, 8, 8, 0.0F);
        this.Drill05 = new ModelPart(this, 17, 14);
        this.Drill05.setPivot(10.0F, 1.5F, 0.5F);
        this.Drill05.addCuboid(0.0F, -3.0F, -3.0F, 2, 6, 6, 0.0F);
        this.setRotation(Drill05, 0.7853981633974483F, 0.0F, 0.0F);
        this.Grip2_02 = new ModelPart(this, 0, 4);
        this.Grip2_02.setPivot(0.0F, -5.3F, 2.1F);
        this.Grip2_02.addCuboid(0.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
    }

    @Override
    public RenderLayer getLayerForPart(IGenericGun gun, ItemStack stack, Identifier texture, int part) {
        GenericGunMeleeCharge tool = (GenericGunMeleeCharge) gun;
        int level = tool.getMiningHeadLevel(stack);
        if (level > -1) {
            return this.getLayer(textures[level]);
        }
        return this.getLayer(texture);
    }

	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
                       float reloadProgress, ModelTransformationMode transformType, int part, float fireProgress, float chargeProgress, int light,
                       int overlay) {
		if (part==0) {
			this.Shape93.render(matrices, vertices, light, overlay);
			this.Shape136.render(matrices, vertices, light, overlay);
			this.Shape134.render(matrices, vertices, light, overlay);
			this.Grip01.render(matrices, vertices, light, overlay);
			this.Shape13.render(matrices, vertices, light, overlay);
			this.Shape151.render(matrices, vertices, light, overlay);
			matrices.push();
			matrices.translate(this.Grip.pivotX * scale, this.Grip.pivotY * scale, this.Grip.pivotZ * scale);
			matrices.translate(this.Grip.pitch * scale, this.Grip.yaw * scale, this.Grip.roll * scale);
			matrices.scale(1.5f, 1.5f, 1.0f);
			matrices.translate(-this.Grip.pivotX * scale, -this.Grip.pivotY * scale, -this.Grip.pivotZ * scale);
			matrices.translate(-this.Grip.pitch * scale, -this.Grip.yaw * scale,
					-this.Grip.roll * scale);
			this.Grip.render(matrices, vertices, light, overlay);
			matrices.pop();
			this.Shape9.render(matrices, vertices, light, overlay);
			this.Shape92.render(matrices, vertices, light, overlay);
			this.Shape152.render(matrices, vertices, light, overlay);
			matrices.push();
			matrices.translate(this.Grip03.pivotX * scale, this.Grip03.pivotY * scale, this.Grip03.pivotZ * scale);
			matrices.translate(this.Grip03.pitch * scale, this.Grip03.yaw * scale,
					this.Grip03.roll * scale);
			matrices.scale(1.0f, 1.0f, 0.98f);
			matrices.translate(-this.Grip03.pivotX * scale, -this.Grip03.pivotY * scale, -this.Grip03.pivotZ * scale);
			matrices.translate(-this.Grip03.pitch * scale, -this.Grip03.yaw * scale,
					-this.Grip03.roll * scale);
			this.Grip03.render(matrices, vertices, light, overlay);
			matrices.pop();
			this.Shape15.render(matrices, vertices, light, overlay);
			this.Grip02.render(matrices, vertices, light, overlay);
			this.Shape131_1.render(matrices, vertices, light, overlay);
			this.Shape34.render(matrices, vertices, light, overlay);
			this.Grip2_03.render(matrices, vertices, light, overlay);
			this.Shape32.render(matrices, vertices, light, overlay);
			this.Shape135.render(matrices, vertices, light, overlay);
			this.Shape33.render(matrices, vertices, light, overlay);
			this.Shape132.render(matrices, vertices, light, overlay);
			this.Shape131.render(matrices, vertices, light, overlay);
			this.Shape91.render(matrices, vertices, light, overlay);
			matrices.push();
			matrices.translate(this.Grip03_1.pivotX * scale, this.Grip03_1.pivotY * scale, this.Grip03_1.pivotZ * scale);
			matrices.translate(this.Grip03_1.pitch * scale, this.Grip03_1.yaw * scale,
					this.Grip03_1.roll * scale);
			matrices.scale(1.0f, 1.0f, 0.96f);
			matrices.translate(-this.Grip03_1.pivotX * scale, -this.Grip03_1.pivotY * scale, -this.Grip03_1.pivotZ * scale);
			matrices.translate(-this.Grip03_1.pitch * scale, -this.Grip03_1.yaw * scale,
					-this.Grip03_1.roll * scale);
			this.Grip03_1.render(matrices, vertices, light, overlay);
			matrices.pop();
			this.Grip2_02.render(matrices, vertices, light, overlay);
			this.Shape133.render(matrices, vertices, light, overlay);
			this.Grip2_01.render(matrices, vertices, light, overlay);
			this.Shape31.render(matrices, vertices, light, overlay);
		} else if (part==1) {
			matrices.push();
			matrices.translate(0,0.1d,0.04d);
			TGMatrixOps.rotate(matrices, 360f*fireProgress, 1, 0, 0);
			matrices.translate(0,-0.1d,-0.04d);
			this.Drill01.render(matrices, vertices, light, overlay);
			this.Drill02.render(matrices, vertices, light, overlay);
			this.Drill03.render(matrices, vertices, light, overlay);
			this.Drill04.render(matrices, vertices, light, overlay);
			this.Drill05.render(matrices, vertices, light, overlay);
			this.Drill06.render(matrices, vertices, light, overlay);
			this.Drill07.render(matrices, vertices, light, overlay);
			this.Drill08.render(matrices, vertices, light, overlay);
			this.Drill09.render(matrices, vertices, light, overlay);
			this.Drill010.render(matrices, vertices, light, overlay);
			this.Drill011.render(matrices, vertices, light, overlay);
			matrices.pop();
		}
		
	}

}
