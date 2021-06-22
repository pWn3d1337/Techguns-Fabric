package techguns.client.models.guns;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import techguns.client.models.ModelPart;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import techguns.TGIdentifier;
import techguns.api.guns.IGenericGun;
import techguns.client.models.ModelMultipart;
import techguns.items.guns.GenericGunMeleeCharge;

public class ModelChainsaw extends ModelMultipart {
    protected static final Identifier[] textures = new Identifier[]{
            new TGIdentifier("textures/guns/chainsaw.png"),
            new TGIdentifier("textures/guns/chainsaw_blades_obsidian.png"),
            new TGIdentifier("textures/guns/chainsaw_blades_carbon.png"),
    };

    public ModelPart shape87;
    public ModelPart shape87_1;
    public ModelPart shape87_2;
    public ModelPart shape87_3;
    public ModelPart shape87_4;
    public ModelPart shape87_5;
    public ModelPart shape87_6;
    public ModelPart shape87_7;
    public ModelPart shape87_8;
    public ModelPart shape87_9;
    public ModelPart shape87_10;
    public ModelPart shape87_11;
    public ModelPart shape87_12;
    public ModelPart shape87_13;
    public ModelPart shape87_14;
    public ModelPart shape87_15;
    public ModelPart shape87_16;
    public ModelPart shape87_17;
    public ModelPart shape87_18;
    public ModelPart shape87_19;
    public ModelPart shape87_20;
    public ModelPart shape87_21;
    public ModelPart shape87_22;
    public ModelPart shape87_23;
    public ModelPart shape87_24;
    public ModelPart shape87_25;
    public ModelPart shape87_26;
    public ModelPart shape87_27;
    public ModelPart shape87_28;
    public ModelPart shape87_29;
    public ModelPart shape87_30;
    public ModelPart shape87_31;
    public ModelPart blade1;
    public ModelPart blade2;
    public ModelPart shape87_33;
    public ModelPart shape87_34;
    public ModelPart Shape26;
    public ModelPart Shape26_1;
    public ModelPart Shape26_2;
    public ModelPart Shape26_3;
    public ModelPart Shape26_4;
    public ModelPart Shape26_5;
    public ModelPart Shape26_6;
    public ModelPart Shape30;
    public ModelPart Shape26_7;
    public ModelPart Shape26_8;
    public ModelPart Shape26_9;

    public ModelChainsaw() {
        super(RenderLayer::getEntityCutout);
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.shape87_17 = new ModelPart(this, 88, 39);
        this.shape87_17.setPivot(4.5F, 4.5F, -3.0F);
        this.shape87_17.addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 11, 0.0F);
        this.Shape26_1 = new ModelPart(this, 98, 16);
        this.Shape26_1.setPivot(-9.0F, -3.0F, 0.0F);
        this.Shape26_1.addCuboid(0.0F, 0.0F, 0.0F, 1, 5, 2, 0.0F);
        this.Shape26_9 = new ModelPart(this, 88, 1);
        this.Shape26_9.setPivot(-19.0F, 3.0F, 0.0F);
        this.Shape26_9.addCuboid(0.0F, 0.0F, 0.0F, 9, 2, 2, 0.0F);
        this.shape87_10 = new ModelPart(this, 61, 14);
        this.shape87_10.setPivot(-7.5F, 0.5F, 5.0F);
        this.shape87_10.addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 1, 0.0F);
        this.shape87_23 = new ModelPart(this, 120, 33);
        this.shape87_23.setPivot(-6.5F, -4.0F, -5.0F);
        this.shape87_23.addCuboid(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.shape87_3 = new ModelPart(this, 48, 34);
        this.shape87_3.setPivot(7.0F, -7.0F, -2.0F);
        this.shape87_3.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 6, 0.0F);
        this.shape87_9 = new ModelPart(this, 33, 10);
        this.shape87_9.setPivot(-7.0F, -3.0F, 5.0F);
        this.shape87_9.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
        this.Shape26 = new ModelPart(this, 88, 19);
        this.Shape26.setPivot(-11.0F, -5.0F, 0.0F);
        this.Shape26.addCuboid(0.0F, 0.0F, 0.0F, 3, 2, 2, 0.0F);
        this.shape87_2 = new ModelPart(this, 66, 31);
        this.shape87_2.setPivot(10.0F, -3.0F, -2.0F);
        this.shape87_2.addCuboid(0.0F, 0.0F, 0.0F, 1, 7, 7, 0.0F);
        this.shape87_33 = new ModelPart(this, 0, 1);
        this.shape87_33.setPivot(45.0F, -2.0F, -3.5F);
        this.shape87_33.addCuboid(0.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
        this.shape87_25 = new ModelPart(this, 92, 41);
        this.shape87_25.setPivot(12.5F, -9.0F, -3.0F);
        this.shape87_25.addCuboid(-1.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
        this.setRotation(shape87_25, 0.0F, 0.0F, 0.7285004297824331F);
        this.blade1 = new ModelPart(this, 0, 56);
        this.blade1.setPivot(12.0F, -3.5F, -3.0F);
        this.blade1.addCuboid(0.0F, 0.0F, 0.0F, 36, 8, 0, 0.0F);
        this.blade2 = new ModelPart(this, 0, 48);
        this.blade2.setPivot(12.0F, -3.5F, -3.0F);
        this.blade2.addCuboid(0.0F, 0.0F, 0.0F, 36, 8, 0, 0.0F);
        this.Shape26_4 = new ModelPart(this, 88, 12);
        this.Shape26_4.setPivot(-21.0F, 2.0F, 0.0F);
        this.Shape26_4.addCuboid(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.shape87_27 = new ModelPart(this, 86, 42);
        this.shape87_27.setPivot(13.5F, -14.0F, 4.0F);
        this.shape87_27.addCuboid(-2.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
        this.Shape26_7 = new ModelPart(this, 94, 7);
        this.Shape26_7.setPivot(-21.0F, -3.0F, 0.0F);
        this.Shape26_7.addCuboid(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.shape87_26 = new ModelPart(this, 92, 41);
        this.shape87_26.setPivot(12.5F, -9.0F, 4.0F);
        this.shape87_26.addCuboid(-1.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
        this.setRotation(shape87_26, 0.0F, 0.0F, 0.7285004297824331F);
        this.shape87_20 = new ModelPart(this, 100, 60);
        this.shape87_20.setPivot(1.5F, -13.0F, -5.0F);
        this.shape87_20.addCuboid(-12.0F, 0.0F, 0.0F, 12, 2, 2, 0.0F);
        this.setRotation(shape87_20, 0.0F, 0.0F, -0.8477064176936457F);
        this.Shape26_6 = new ModelPart(this, 96, 13);
        this.Shape26_6.setPivot(-22.0F, 2.0F, 0.0F);
        this.Shape26_6.addCuboid(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.shape87 = new ModelPart(this, 86, 37);
        this.shape87.setPivot(5.0F, 3.0F, 8.0F);
        this.shape87.addCuboid(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.shape87_12 = new ModelPart(this, 48, 14);
        this.shape87_12.setPivot(5.0F, -8.0F, -2.0F);
        this.shape87_12.addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 6, 0.0F);
        this.shape87_11 = new ModelPart(this, 1, 24);
        this.shape87_11.setPivot(10.0F, -3.5F, -4.0F);
        this.shape87_11.addCuboid(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.shape87_30 = new ModelPart(this, 84, 42);
        this.shape87_30.setPivot(13.5F, -12.0F, -2.0F);
        this.shape87_30.addCuboid(-2.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.shape87_21 = new ModelPart(this, 110, 51);
        this.shape87_21.setPivot(5.0F, -13.0F, 0.0F);
        this.shape87_21.addCuboid(0.0F, 0.0F, 0.0F, 2, 2, 7, 0.0F);
        this.shape87_13 = new ModelPart(this, 37, 23);
        this.shape87_13.setPivot(-3.0F, -7.0F, -3.0F);
        this.shape87_13.addCuboid(0.0F, 0.0F, 0.0F, 10, 3, 8, 0.0F);
        this.Shape26_3 = new ModelPart(this, 88, 5);
        this.Shape26_3.setPivot(-22.0F, -2.0F, 0.0F);
        this.Shape26_3.addCuboid(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.shape87_1 = new ModelPart(this, 86, 33);
        this.shape87_1.setPivot(5.0F, 3.5F, 7.0F);
        this.shape87_1.addCuboid(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.shape87_4 = new ModelPart(this, 0, 3);
        this.shape87_4.setPivot(-8.0F, -7.0F, -3.0F);
        this.shape87_4.addCuboid(0.0F, 0.0F, 0.0F, 5, 12, 8, 0.0F);
        this.shape87_15 = new ModelPart(this, 84, 42);
        this.shape87_15.setPivot(13.5F, -10.0F, -2.0F);
        this.shape87_15.addCuboid(-2.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.shape87_29 = new ModelPart(this, 84, 42);
        this.shape87_29.setPivot(13.5F, -14.0F, -2.0F);
        this.shape87_29.addCuboid(-2.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.Shape30 = new ModelPart(this, 88, 23);
        this.Shape30.setPivot(-19.0F, -1.0F, 0.0F);
        this.Shape30.addCuboid(0.0F, -2.0F, 0.0F, 9, 2, 2, 0.0F);
        this.setRotation(Shape30, 0.0F, 0.0F, -0.222529479629277F);
        this.shape87_18 = new ModelPart(this, 112, 29);
        this.shape87_18.setPivot(5.0F, -7.0F, 11.0F);
        this.shape87_18.addCuboid(0.0F, -7.0F, -2.0F, 2, 7, 2, 0.0F);
        this.setRotation(shape87_18, 0.6058037833672317F, 0.0F, 0.0F);
        this.shape87_16 = new ModelPart(this, 112, 42);
        this.shape87_16.setPivot(7.0F, -13.0F, 0.0F);
        this.shape87_16.addCuboid(-2.0F, 0.0F, -6.0F, 2, 2, 6, 0.0F);
        this.setRotation(shape87_16, 0.0F, 0.6127851003752092F, 0.0F);
        this.shape87_28 = new ModelPart(this, 86, 42);
        this.shape87_28.setPivot(13.5F, -14.0F, -3.0F);
        this.shape87_28.addCuboid(-2.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
        this.shape87_8 = new ModelPart(this, 26, 15);
        this.shape87_8.setPivot(-3.0F, -9.0F, -2.0F);
        this.shape87_8.addCuboid(0.0F, 0.0F, 0.0F, 8, 2, 6, 0.0F);
        this.Shape26_5 = new ModelPart(this, 88, 16);
        this.Shape26_5.setPivot(-20.0F, 4.0F, 0.0F);
        this.Shape26_5.addCuboid(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.shape87_5 = new ModelPart(this, 65, 13);
        this.shape87_5.setPivot(-4.0F, -8.0F, -2.0F);
        this.shape87_5.addCuboid(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.shape87_6 = new ModelPart(this, 0, 23);
        this.shape87_6.setPivot(-3.0F, -4.0F, -5.0F);
        this.shape87_6.addCuboid(0.0F, 0.0F, 0.0F, 13, 9, 11, 0.0F);
        this.shape87_22 = new ModelPart(this, 120, 38);
        this.shape87_22.setPivot(1.5F, -13.0F, -5.0F);
        this.shape87_22.addCuboid(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.Shape26_2 = new ModelPart(this, 103, 7);
        this.Shape26_2.setPivot(-22.0F, -1.0F, 0.0F);
        this.Shape26_2.addCuboid(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.shape87_31 = new ModelPart(this, 4, 3);
        this.shape87_31.setPivot(46.0F, -1.0F, -3.5F);
        this.shape87_31.addCuboid(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.shape87_19 = new ModelPart(this, 105, 36);
        this.shape87_19.setPivot(5.0F, -7.0F, 9.0F);
        this.shape87_19.addCuboid(0.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.Shape26_8 = new ModelPart(this, 109, 13);
        this.Shape26_8.setPivot(-10.0F, 2.0F, 0.0F);
        this.Shape26_8.addCuboid(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.shape87_7 = new ModelPart(this, 65, 25);
        this.shape87_7.setPivot(1.5F, 0.5F, 6.0F);
        this.shape87_7.addCuboid(-2.5F, -2.5F, 0.0F, 5, 5, 1, 0.0F);
        this.setRotation(shape87_7, 0.0F, 0.0F, 0.7853981633974483F);
        this.shape87_14 = new ModelPart(this, 86, 35);
        this.shape87_14.setPivot(5.0F, 3.0F, 10.0F);
        this.shape87_14.addCuboid(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.shape87_24 = new ModelPart(this, 121, 29);
        this.shape87_24.setPivot(-7.0F, -2.5F, -4.0F);
        this.shape87_24.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
        this.shape87_34 = new ModelPart(this, 18, 0);
        this.shape87_34.setPivot(12.0F, -2.5F, -3.5F);
        this.shape87_34.addCuboid(0.0F, 0.0F, 0.0F, 33, 6, 1, 0.0F);
    }

    @Override
    public RenderLayer getLayerForPart(IGenericGun gun, ItemStack stack, Identifier texture, int part) {
        if(part==1) {
            GenericGunMeleeCharge tool = (GenericGunMeleeCharge) gun;
            int level = tool.getMiningHeadLevel(stack);
            if (level > -1) {
                return this.getLayer(textures[level]);
            }
        }
        return this.getLayer(texture);
    }

    @Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
		float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
	int overlay) {
    	if(part==0) {
	        this.shape87_17.render(matrices, vertices, light, overlay);
	        this.Shape26_1.render(matrices, vertices, light, overlay);
	        this.Shape26_9.render(matrices, vertices, light, overlay);
	        this.shape87_10.render(matrices, vertices, light, overlay);
	        this.shape87_23.render(matrices, vertices, light, overlay);
	        this.shape87_3.render(matrices, vertices, light, overlay);
	        this.shape87_9.render(matrices, vertices, light, overlay);
	        this.Shape26.render(matrices, vertices, light, overlay);
	        this.shape87_2.render(matrices, vertices, light, overlay);
	        this.shape87_33.render(matrices, vertices, light, overlay);
	        this.shape87_25.render(matrices, vertices, light, overlay);
	        this.Shape26_4.render(matrices, vertices, light, overlay);
	        this.shape87_27.render(matrices, vertices, light, overlay);
	        this.Shape26_7.render(matrices, vertices, light, overlay);
	        this.shape87_26.render(matrices, vertices, light, overlay);
	        this.shape87_20.render(matrices, vertices, light, overlay);
	        this.Shape26_6.render(matrices, vertices, light, overlay);
	        this.shape87.render(matrices, vertices, light, overlay);
	        this.shape87_12.render(matrices, vertices, light, overlay);
	        this.shape87_11.render(matrices, vertices, light, overlay);
	        this.shape87_30.render(matrices, vertices, light, overlay);
	        this.shape87_21.render(matrices, vertices, light, overlay);
	        this.shape87_13.render(matrices, vertices, light, overlay);
	        this.Shape26_3.render(matrices, vertices, light, overlay);
	        this.shape87_1.render(matrices, vertices, light, overlay);
	        this.shape87_4.render(matrices, vertices, light, overlay);
	        this.shape87_15.render(matrices, vertices, light, overlay);
	        this.shape87_29.render(matrices, vertices, light, overlay);
	        this.Shape30.render(matrices, vertices, light, overlay);
	        this.shape87_18.render(matrices, vertices, light, overlay);
	        this.shape87_16.render(matrices, vertices, light, overlay);
	        this.shape87_28.render(matrices, vertices, light, overlay);
	        this.shape87_8.render(matrices, vertices, light, overlay);
	        this.Shape26_5.render(matrices, vertices, light, overlay);
	        this.shape87_5.render(matrices, vertices, light, overlay);
	        this.shape87_6.render(matrices, vertices, light, overlay);
	        this.shape87_22.render(matrices, vertices, light, overlay);
	        this.Shape26_2.render(matrices, vertices, light, overlay);
	        this.shape87_31.render(matrices, vertices, light, overlay);
	        this.shape87_19.render(matrices, vertices, light, overlay);
	        this.Shape26_8.render(matrices, vertices, light, overlay);
	        this.shape87_7.render(matrices, vertices, light, overlay);
	        this.shape87_14.render(matrices, vertices, light, overlay);
	        this.shape87_24.render(matrices, vertices, light, overlay);
	        this.shape87_34.render(matrices, vertices, light, overlay);
    	} else if(part==1) {
	        int i = Math.round(fireProgress*40f)% 2;
	        if (i==0) {
	        	this.blade1.render(matrices, vertices, light, overlay);
	        } else {
	        	this.blade2.render(matrices, vertices, light, overlay);	
	        }
    	}
    }
}
