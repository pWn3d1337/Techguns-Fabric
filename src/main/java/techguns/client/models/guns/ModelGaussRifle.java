package techguns.client.models.guns;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelGaussRifle extends ModelMultipart {
    public ModelPart Scope4;
    public ModelPart Scope1;
    public ModelPart Scope2;
    public ModelPart Scope3;
    public ModelPart Scope5;
    public ModelPart Scope6;
    public ModelPart Stock1;
    public ModelPart Stock2;
    public ModelPart Stock3;
    public ModelPart Grip1;
    public ModelPart Grip2;
    public ModelPart Grip3;
    public ModelPart Grip4;
    public ModelPart Grip5;
    public ModelPart Grip6;
    public ModelPart Grip7;
    public ModelPart Grip8;
    public ModelPart Grip9;
    public ModelPart Stock4;
    public ModelPart Stock5;
    public ModelPart Stock6;
    public ModelPart Stock7;
    public ModelPart Stock8;
    public ModelPart Stock9;
    public ModelPart Stock10;
    public ModelPart Mag1;
    public ModelPart Stock11;
    public ModelPart Stock12;
    public ModelPart Stock13;
    public ModelPart Stock14;
    public ModelPart Stock15;
    public ModelPart Stock16;
    public ModelPart Grip10;
    public ModelPart Grip11;
    public ModelPart Stock17;
    public ModelPart Bipod1;
    public ModelPart Bipod2;
    public ModelPart Bipod3;
    public ModelPart Bottom4;
    public ModelPart Bottom1;
    public ModelPart Bottom3;
    public ModelPart Bottom2;
    public ModelPart Center1;
    public ModelPart Center2;
    public ModelPart Barrel1;
    public ModelPart Barrel2;
    public ModelPart Muzzle1;
    public ModelPart Barrel3;
    public ModelPart Muzzle2;
    public ModelPart Barrel4;
    public ModelPart Barrel5;
    public ModelPart Muzzle3;
    public ModelPart Muzzle4;
    public ModelPart Muzzle5;
    public ModelPart Center3;

    public ModelGaussRifle() {
        super(RenderLayer::getEntitySolid);
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.Stock12 = new ModelPart(this, 0, 112);
        this.Stock12.setPivot(1.0F, 0.5F, -0.5F);
        this.Stock12.addCuboid(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
        this.Bipod2 = new ModelPart(this, 0, 85);
        this.Bipod2.setPivot(26.0F, 5.5F, 2.0F);
        this.Bipod2.addCuboid(0.0F, 0.0F, 0.0F, 14, 1, 1, 0.0F);
        this.Bipod3 = new ModelPart(this, 0, 85);
        this.Bipod3.setPivot(26.0F, 5.5F, -1.0F);
        this.Bipod3.addCuboid(0.0F, 0.0F, 0.0F, 14, 1, 1, 0.0F);
        this.Grip6 = new ModelPart(this, 120, 87);
        this.Grip6.setPivot(-13.0F, 4.0F, 0.0F);
        this.Grip6.addCuboid(-2.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.setRotation(Grip6, 0.0F, 0.0F, -0.7853981633974483F);
        this.Grip3 = new ModelPart(this, 93, 87);
        this.Grip3.setPivot(-12.0F, 5.0F, 0.0F);
        this.Grip3.addCuboid(0.0F, 0.0F, 0.0F, 4, 3, 2, 0.0F);
        this.Stock4 = new ModelPart(this, 10, 115);
        this.Stock4.setPivot(-17.0F, 1.0F, -0.5F);
        this.Stock4.addCuboid(0.0F, 0.0F, 0.0F, 3, 5, 3, 0.0F);
        this.Grip10 = new ModelPart(this, 102, 100);
        this.Grip10.setPivot(-6.0F, 3.0F, 0.0F);
        this.Grip10.addCuboid(0.0F, 0.0F, 0.0F, 3, 7, 2, 0.0F);
        this.setRotation(Grip10, 0.0F, 0.0F, 0.6108652381980153F);
        this.Stock8 = new ModelPart(this, 36, 105);
        this.Stock8.setPivot(-33.5F, -5.6F, -0.5F);
        this.Stock8.addCuboid(0.0F, 0.0F, 0.0F, 2, 11, 3, 0.0F);
        this.setRotation(Stock8, 0.0F, 0.0F, -0.18203784098300857F);
        this.Stock6 = new ModelPart(this, 22, 119);
        this.Stock6.setPivot(-32.0F, -4.0F, -0.5F);
        this.Stock6.addCuboid(0.0F, 0.0F, 0.0F, 17, 2, 3, 0.0F);
        this.Grip11 = new ModelPart(this, 105, 89);
        this.Grip11.setPivot(-8.0F, 1.5F, 0.0F);
        this.Grip11.addCuboid(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.setRotation(Grip11, 0.0F, 0.0F, -0.7853981633974483F);
        this.Stock17 = new ModelPart(this, 58, 99);
        this.Stock17.setPivot(-2.0F, -3.0F, -0.5F);
        this.Stock17.addCuboid(0.0F, 0.0F, 0.0F, 2, 1, 3, 0.0F);
        this.Grip1 = new ModelPart(this, 105, 94);
        this.Grip1.setPivot(-13.0F, 8.0F, 0.0F);
        this.Grip1.addCuboid(0.0F, -2.0F, 0.0F, 5, 2, 2, 0.0F);
        this.setRotation(Grip1, 0.0F, 0.0F, 0.27314402793711257F);
        this.Scope4 = new ModelPart(this, 106, 48);
        this.Scope4.setPivot(-0.5F, -8.0F, -0.5F);
        this.Scope4.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 3, 0.0F);
        this.Grip9 = new ModelPart(this, 111, 89);
        this.Grip9.setPivot(-12.0F, 1.5F, 0.0F);
        this.Grip9.addCuboid(-1.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.setRotation(Grip9, 0.0F, 0.0F, 0.7853981633974483F);
        this.Mag1 = new ModelPart(this, 67, 116);
        this.Mag1.setPivot(-26.0F, 1.0F, -1.5F);
        this.Mag1.addCuboid(0.0F, 0.0F, 0.0F, 7, 7, 5, 0.0F);
        this.Stock15 = new ModelPart(this, 50, 99);
        this.Stock15.setPivot(2.0F, 1.0F, -1.0F);
        this.Stock15.addCuboid(0.0F, 0.0F, 0.0F, 2, 4, 4, 0.0F);
        this.Stock3 = new ModelPart(this, 0, 118);
        this.Stock3.setPivot(-6.0F, 2.0F, 0.0F);
        this.Stock3.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 2, 0.0F);
        this.Muzzle3 = new ModelPart(this, 86, 66);
        this.Muzzle3.setPivot(62.0F, -1.3F, -1.0F);
        this.Muzzle3.addCuboid(0.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
        this.Muzzle1 = new ModelPart(this, 87, 57);
        this.Muzzle1.setPivot(62.0F, -1.3F, 2.0F);
        this.Muzzle1.addCuboid(0.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
        this.Grip8 = new ModelPart(this, 122, 97);
        this.Grip8.setPivot(-14.0F, 1.0F, 0.0F);
        this.Grip8.addCuboid(0.0F, 0.0F, 0.0F, 1, 5, 2, 0.0F);
        this.Grip2 = new ModelPart(this, 99, 94);
        this.Grip2.setPivot(-13.0F, 8.0F, 0.0F);
        this.Grip2.addCuboid(0.0F, -3.0F, 0.0F, 1, 3, 2, 0.0F);
        this.setRotation(Grip2, 0.0F, 0.0F, -0.22759093446006054F);
        this.Stock9 = new ModelPart(this, 0, 92);
        this.Stock9.setPivot(-30.0F, -2.0F, -1.0F);
        this.Stock9.addCuboid(0.0F, 0.0F, 0.0F, 34, 3, 4, 0.0F);
        this.Stock14 = new ModelPart(this, 34, 99);
        this.Stock14.setPivot(0.0F, -4.0F, -1.0F);
        this.Stock14.addCuboid(0.0F, 0.0F, 0.0F, 4, 2, 4, 0.0F);
        this.Scope3 = new ModelPart(this, 106, 66);
        this.Scope3.setPivot(2.5F, -7.5F, -0.5F);
        this.Scope3.addCuboid(0.0F, 0.0F, 0.0F, 8, 2, 3, 0.0F);
        this.Bottom2 = new ModelPart(this, 30, 84);
        this.Bottom2.setPivot(21.0F, 5.0F, 0.0F);
        this.Bottom2.addCuboid(0.0F, 0.0F, 0.0F, 14, 1, 2, 0.0F);
        this.Grip7 = new ModelPart(this, 122, 92);
        this.Grip7.setPivot(-7.0F, 1.5F, 0.0F);
        this.Grip7.addCuboid(0.0F, 0.0F, 0.0F, 1, 3, 2, 0.0F);
        this.Stock10 = new ModelPart(this, 11, 108);
        this.Stock10.setPivot(-30.0F, -5.0F, -1.0F);
        this.Stock10.addCuboid(0.0F, 0.0F, 0.0F, 2, 3, 4, 0.0F);
        this.Stock13 = new ModelPart(this, 20, 108);
        this.Stock13.setPivot(-15.0F, -3.0F, -0.5F);
        this.Stock13.addCuboid(0.0F, 0.0F, 0.0F, 2, 1, 3, 0.0F);
        this.Barrel4 = new ModelPart(this, 0, 64);
        this.Barrel4.setPivot(22.0F, -3.0F, 0.0F);
        this.Barrel4.addCuboid(0.0F, 0.0F, 0.0F, 42, 1, 2, 0.0F);
        this.Scope2 = new ModelPart(this, 106, 58);
        this.Scope2.setPivot(3.0F, -8.0F, -1.0F);
        this.Scope2.addCuboid(0.0F, 0.0F, 0.0F, 7, 3, 4, 0.0F);
        this.Bottom4 = new ModelPart(this, 21, 33);
        this.Bottom4.setPivot(4.0F, 2.0F, -1.0F);
        this.Bottom4.addCuboid(0.0F, 0.0F, 0.0F, 32, 3, 4, 0.0F);
        this.Bipod1 = new ModelPart(this, 31, 87);
        this.Bipod1.setPivot(25.0F, 5.0F, -0.5F);
        this.Bipod1.addCuboid(0.0F, 0.0F, 0.0F, 3, 2, 3, 0.0F);
        this.Grip4 = new ModelPart(this, 110, 107);
        this.Grip4.setPivot(-13.0F, 0.5F, 0.0F);
        this.Grip4.addCuboid(0.0F, 0.0F, 0.0F, 7, 1, 2, 0.0F);
        this.Scope5 = new ModelPart(this, 114, 72);
        this.Scope5.setPivot(10.5F, -8.0F, -0.5F);
        this.Scope5.addCuboid(0.0F, 0.0F, 0.0F, 4, 3, 3, 0.0F);
        this.Muzzle2 = new ModelPart(this, 87, 61);
        this.Muzzle2.setPivot(62.0F, -2.0F, 0.0F);
        this.Muzzle2.addCuboid(0.0F, 0.0F, 0.0F, 1, 3, 2, 0.0F);
        this.Stock1 = new ModelPart(this, 56, 113);
        this.Stock1.setPivot(2.0F, 3.5F, -0.5F);
        this.Stock1.addCuboid(-1.0F, 0.0F, 0.0F, 1, 2, 3, 0.0F);
        this.setRotation(Stock1, 0.0F, 0.0F, 0.7853981633974483F);
        this.Scope6 = new ModelPart(this, 108, 79);
        this.Scope6.setPivot(5.0F, -7.0F, 3.0F);
        this.Scope6.addCuboid(0.0F, -0.5F, -0.5F, 9, 1, 1, 0.0F);
        this.setRotation(Scope6, 0.7853981633974483F, 0.0F, 0.0F);
        this.Center3 = new ModelPart(this, 30, 54);
        this.Center3.setPivot(12.0F, -4.0F, -0.5F);
        this.Center3.addCuboid(0.0F, 0.0F, 0.0F, 10, 1, 3, 0.0F);
        this.Barrel1 = new ModelPart(this, 26, 40);
        this.Barrel1.setPivot(22.0F, -0.5F, -1.5F);
        this.Barrel1.addCuboid(0.0F, 0.0F, 0.0F, 40, 3, 5, 0.0F);
        this.Muzzle4 = new ModelPart(this, 93, 58);
        this.Muzzle4.setPivot(62.0F, 0.7F, 0.0F);
        this.Muzzle4.addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.Bottom3 = new ModelPart(this, 9, 87);
        this.Bottom3.setPivot(13.0F, 4.5F, -0.5F);
        this.Bottom3.addCuboid(0.0F, 0.0F, 0.0F, 8, 2, 3, 0.0F);
        this.Stock7 = new ModelPart(this, 22, 114);
        this.Stock7.setPivot(-30.0F, 5.0F, -0.5F);
        this.Stock7.addCuboid(0.0F, 0.0F, 0.0F, 4, 2, 3, 0.0F);
        this.Barrel5 = new ModelPart(this, 0, 68);
        this.Barrel5.setPivot(22.0F, -1.3F, -1.0F);
        this.Barrel5.addCuboid(0.0F, 0.0F, 0.0F, 40, 1, 4, 0.0F);
        this.Center2 = new ModelPart(this, 0, 48);
        this.Center2.setPivot(12.0F, -3.0F, -1.5F);
        this.Center2.addCuboid(0.0F, 0.0F, 0.0F, 10, 5, 5, 0.0F);
        this.Stock2 = new ModelPart(this, 0, 123);
        this.Stock2.setPivot(-6.0F, 0.5F, -0.5F);
        this.Stock2.addCuboid(0.0F, 0.0F, 0.0F, 7, 2, 3, 0.0F);
        this.Stock5 = new ModelPart(this, 20, 124);
        this.Stock5.setPivot(-13.0F, -2.5F, -0.5F);
        this.Stock5.addCuboid(0.0F, 0.0F, 0.0F, 11, 1, 3, 0.0F);
        this.Muzzle5 = new ModelPart(this, 93, 61);
        this.Muzzle5.setPivot(64.0F, -3.0F, 0.0F);
        this.Muzzle5.addCuboid(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.Bottom1 = new ModelPart(this, 62, 84);
        this.Bottom1.setPivot(7.0F, 5.0F, 0.0F);
        this.Bottom1.addCuboid(0.0F, 0.0F, 0.0F, 6, 1, 2, 0.0F);
        this.Center1 = new ModelPart(this, 0, 36);
        this.Center1.setPivot(4.0F, -4.0F, -1.5F);
        this.Center1.addCuboid(0.0F, 0.0F, 0.0F, 8, 7, 5, 0.0F);
        this.Scope1 = new ModelPart(this, 106, 54);
        this.Scope1.setPivot(3.5F, -5.0F, -0.5F);
        this.Scope1.addCuboid(0.0F, 0.0F, 0.0F, 6, 1, 3, 0.0F);
        this.Stock11 = new ModelPart(this, 0, 99);
        this.Stock11.setPivot(-30.0F, 1.0F, -1.0F);
        this.Stock11.addCuboid(0.0F, 0.0F, 0.0F, 13, 4, 4, 0.0F);
        this.Barrel2 = new ModelPart(this, 0, 58);
        this.Barrel2.setPivot(22.0F, -3.0F, 2.0F);
        this.Barrel2.addCuboid(0.0F, 0.0F, -1.0F, 42, 2, 1, 0.0F);
        this.setRotation(Barrel2, 0.5235987755982988F, -0.0F, 0.0F);
        this.Stock16 = new ModelPart(this, 0, 108);
        this.Stock16.setPivot(-3.0F, 4.5F, 0.0F);
        this.Stock16.addCuboid(0.0F, 0.0F, 0.0F, 4, 0, 2, 0.0F);
        this.Barrel3 = new ModelPart(this, 0, 61);
        this.Barrel3.setPivot(22.0F, -3.0F, 0.0F);
        this.Barrel3.addCuboid(0.0F, 0.0F, 0.0F, 42, 2, 1, 0.0F);
        this.setRotation(Barrel3, -0.5235987755982988F, -0.0F, 0.0F);
        this.Grip5 = new ModelPart(this, 116, 98);
        this.Grip5.setPivot(-6.0F, 3.0F, 0.0F);
        this.Grip5.addCuboid(0.0F, 0.0F, 0.0F, 1, 4, 2, 0.0F);
        this.setRotation(Grip5, 0.0F, 0.0F, 0.7853981633974483F);
    }

    @Override
    public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft, float reloadProgress,
                       ModelTransformation.Mode transformType, int part, float fireProgress, float chargeProgress,
                       int light, int overlay) {

        this.Stock12.render(matrices, vertices, light, overlay);
        this.Bipod2.render(matrices, vertices, light, overlay);
        this.Bipod3.render(matrices, vertices, light, overlay);
        this.Grip6.render(matrices, vertices, light, overlay);
        this.Grip3.render(matrices, vertices, light, overlay);
        this.Stock4.render(matrices, vertices, light, overlay);
        this.Grip10.render(matrices, vertices, light, overlay);
        this.Stock8.render(matrices, vertices, light, overlay);
        this.Stock6.render(matrices, vertices, light, overlay);
        this.Grip11.render(matrices, vertices, light, overlay);
        this.Stock17.render(matrices, vertices, light, overlay);
        this.Grip1.render(matrices, vertices, light, overlay);
        this.Scope4.render(matrices, vertices, light, overlay);
        this.Grip9.render(matrices, vertices, light, overlay);
        this.Mag1.render(matrices, vertices, light, overlay);
        this.Stock15.render(matrices, vertices, light, overlay);
        this.Stock3.render(matrices, vertices, light, overlay);
        this.Muzzle3.render(matrices, vertices, light, overlay);
        this.Muzzle1.render(matrices, vertices, light, overlay);
        this.Grip8.render(matrices, vertices, light, overlay);
        this.Grip2.render(matrices, vertices, light, overlay);
        this.Stock9.render(matrices, vertices, light, overlay);
        this.Stock14.render(matrices, vertices, light, overlay);
        this.Scope3.render(matrices, vertices, light, overlay);
        this.Bottom2.render(matrices, vertices, light, overlay);
        this.Grip7.render(matrices, vertices, light, overlay);
        this.Stock10.render(matrices, vertices, light, overlay);
        this.Stock13.render(matrices, vertices, light, overlay);
        this.Barrel4.render(matrices, vertices, light, overlay);
        this.Scope2.render(matrices, vertices, light, overlay);
        this.Bottom4.render(matrices, vertices, light, overlay);
        this.Bipod1.render(matrices, vertices, light, overlay);
        this.Grip4.render(matrices, vertices, light, overlay);
        this.Scope5.render(matrices, vertices, light, overlay);
        this.Muzzle2.render(matrices, vertices, light, overlay);
        this.Stock1.render(matrices, vertices, light, overlay);
        this.Scope6.render(matrices, vertices, light, overlay);
        this.Center3.render(matrices, vertices, light, overlay);
        this.Barrel1.render(matrices, vertices, light, overlay);
        this.Muzzle4.render(matrices, vertices, light, overlay);
        this.Bottom3.render(matrices, vertices, light, overlay);
        this.Stock7.render(matrices, vertices, light, overlay);
        this.Barrel5.render(matrices, vertices, light, overlay);
        this.Center2.render(matrices, vertices, light, overlay);
        this.Stock2.render(matrices, vertices, light, overlay);
        this.Stock5.render(matrices, vertices, light, overlay);
        this.Muzzle5.render(matrices, vertices, light, overlay);
        this.Bottom1.render(matrices, vertices, light, overlay);
        this.Center1.render(matrices, vertices, light, overlay);
        this.Scope1.render(matrices, vertices, light, overlay);
        this.Stock11.render(matrices, vertices, light, overlay);
        this.Barrel2.render(matrices, vertices, light, overlay);
        this.Stock16.render(matrices, vertices, light, overlay);
        this.Barrel3.render(matrices, vertices, light, overlay);
        this.Grip5.render(matrices, vertices, light, overlay);
    }
}