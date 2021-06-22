package techguns.client.models.guns;

import techguns.client.models.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import techguns.TGIdentifier;
import techguns.api.guns.IGenericGun;
import techguns.client.models.ModelMultipart;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.items.guns.ammo.AmmoTypes;

import java.util.HashMap;

public class ModelPowerHammer extends ModelMultipart {
	protected static final Identifier[] textures = new Identifier[]{
			new TGIdentifier("textures/guns/powerhammer.png"),
			new TGIdentifier("textures/guns/powerhammer_obsidian.png"),
			new TGIdentifier("textures/guns/powerhammer_carbon.png"),
	};

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
	ModelPart H4;
	ModelPart Shape13;
	ModelPart Shape14;
	ModelPart H7;
	ModelPart H9;
	ModelPart H8;
	ModelPart H5;
	ModelPart H6;
	ModelPart Shape20;
	ModelPart H3;
	ModelPart H1;
	ModelPart H2;
	ModelPart Shape24;
	ModelPart Shape25;
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
	ModelPart Shape36;
	ModelPart Shape37;
	ModelPart Shape38;
	ModelPart Shape39;
	ModelPart Shape40;

	public ModelPowerHammer() {
        super(RenderLayer::getEntitySolid);
		textureWidth = 64;
		textureHeight = 64;

		Shape1 = new ModelPart(this, 52, 19);
		Shape1.addCuboid(-3F, 0F, 0F, 3, 0, 2);
		Shape1.setPivot(-0.5F, 7F, -0.5F);
		Shape1.setTextureSize(64, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelPart(this, 45, 25);
		Shape2.addCuboid(-1F, -4F, 0F, 1, 4, 7);
		Shape2.setPivot(5F, -0.5F, -3F);
		Shape2.setTextureSize(64, 64);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, -0.7853982F);
		Shape3 = new ModelPart(this, 24, 36);
		Shape3.addCuboid(0F, 0F, 0F, 12, 1, 4);
		Shape3.setPivot(-9F, 3F, -1.5F);
		Shape3.setTextureSize(64, 64);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelPart(this, 27, 9);
		Shape4.addCuboid(0F, 0F, 0F, 11, 4, 6);
		Shape4.setPivot(-7F, -0.5F, -2.5F);
		Shape4.setTextureSize(64, 64);
		setRotation(Shape4, 0F, 0F, 0F);
		Shape4.mirror = false;
		Shape5 = new ModelPart(this, 47, 0);
		Shape5.addCuboid(0F, 0F, 0F, 1, 2, 6);
		Shape5.setPivot(-7F, -2.5F, -2.5F);
		Shape5.setTextureSize(64, 64);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelPart(this, 0, 50);
		Shape6.addCuboid(0F, 0F, 0F, 8, 1, 3);
		Shape6.setPivot(-5F, 4F, -1F);
		Shape6.setTextureSize(64, 64);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape7 = new ModelPart(this, 29, 23);
		Shape7.addCuboid(0F, 0F, 0F, 1, 6, 7);
		Shape7.setPivot(4F, -0.5F, -3F);
		Shape7.setTextureSize(64, 64);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Shape8 = new ModelPart(this, 0, 27);
		Shape8.addCuboid(0F, 0F, 0F, 3, 1, 4);
		Shape8.setPivot(3F, 3.5F, -1.5F);
		Shape8.setTextureSize(64, 64);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0F, 0F);
		Shape9 = new ModelPart(this, 47, 0);
		Shape9.addCuboid(0F, 0F, 0F, 1, 2, 6);
		Shape9.setPivot(-2F, -2.5F, -2.5F);
		Shape9.setTextureSize(64, 64);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);
		Shape10 = new ModelPart(this, 0, 57);
		Shape10.addCuboid(0F, 0F, 0F, 3, 1, 1);
		Shape10.setPivot(-13F, -1F, 1F);
		Shape10.setTextureSize(64, 64);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 0F, 0F);
		Shape11 = new ModelPart(this, 1, 32);
		Shape11.addCuboid(0F, 0F, 0F, 3, 1, 3);
		Shape11.setPivot(2.99F, 4F, -1F);
		Shape11.setTextureSize(64, 64);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0F);
		H4 = new ModelPart(this, 16, 0);
		H4.addCuboid(0F, 0F, 0F, 1, 1, 4);
		H4.setPivot(13F, 4F, -1.5F);
		H4.setTextureSize(64, 64);
		H4.mirror = true;
		setRotation(H4, 0F, 0F, 0F);
		Shape13 = new ModelPart(this, 0, 27);
		Shape13.addCuboid(0F, 0F, 0F, 3, 1, 4);
		Shape13.setPivot(3F, -1.5F, -1.5F);
		Shape13.setTextureSize(64, 64);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, 0F);
		Shape14 = new ModelPart(this, 1, 32);
		Shape14.addCuboid(0F, 0F, 0F, 3, 1, 3);
		Shape14.setPivot(2.99F, -2F, -1F);
		Shape14.setTextureSize(64, 64);
		Shape14.mirror = true;
		setRotation(Shape14, 0F, 0F, 0F);
		H7 = new ModelPart(this, 26, 0);
		H7.addCuboid(0F, -1.5F, -1.5F, 7, 3, 3);
		H7.setPivot(6F, 1.5F, 0.5F);
		H7.setTextureSize(64, 64);
		H7.mirror = true;
		setRotation(H7, 0.7853982F, 0F, 0F);
		H9 = new ModelPart(this, 30, 6);
		H9.addCuboid(0F, -0.5F, -0.5F, 7, 1, 1);
		H9.setPivot(6F, 3.7F, -0.5F);
		H9.setTextureSize(64, 64);
		H9.mirror = true;
		setRotation(H9, 0.7853982F, 0F, 0F);
		H8 = new ModelPart(this, 30, 6);
		H8.addCuboid(0F, -0.5F, -0.5F, 7, 1, 1);
		H8.setPivot(6F, 3.7F, 1.5F);
		H8.setTextureSize(64, 64);
		H8.mirror = true;
		setRotation(H8, 0.7853982F, 0F, 0F);
		H5 = new ModelPart(this, 30, 6);
		H5.addCuboid(0F, -0.5F, -0.5F, 7, 1, 1);
		H5.setPivot(6F, -0.7F, -0.5F);
		H5.setTextureSize(64, 64);
		H5.mirror = true;
		setRotation(H5, 0.7853982F, 0F, 0F);
		H6 = new ModelPart(this, 30, 6);
		H6.addCuboid(0F, -0.5F, -0.5F, 7, 1, 1);
		H6.setPivot(6F, -0.7F, 1.5F);
		H6.setTextureSize(64, 64);
		H6.mirror = true;
		setRotation(H6, 0.7853982F, 0F, 0F);
		Shape20 = new ModelPart(this, 46, 41);
		Shape20.addCuboid(-1F, -1F, 0F, 2, 2, 6);
		Shape20.setPivot(1.5F, 1.5F, 3.5F);
		Shape20.setTextureSize(64, 64);
		Shape20.mirror = true;
		setRotation(Shape20, 0F, 0F, 0.7853982F);
		H3 = new ModelPart(this, 0, 0);
		H3.addCuboid(0F, 0F, 0F, 1, 6, 4);
		H3.setPivot(14F, -1.5F, -1.5F);
		H3.setTextureSize(64, 64);
		H3.mirror = true;
		setRotation(H3, 0F, 0F, 0F);
		H1 = new ModelPart(this, 16, 0);
		H1.addCuboid(0F, 0F, 0F, 1, 1, 4);
		H1.setPivot(13F, -2F, -1.5F);
		H1.setTextureSize(64, 64);
		H1.mirror = true;
		setRotation(H1, 0F, 0F, 0F);
		H2 = new ModelPart(this, 10, 0);
		H2.addCuboid(0F, 0F, 0F, 1, 5, 5);
		H2.setPivot(13F, -1F, -2F);
		H2.setTextureSize(64, 64);
		H2.mirror = true;
		setRotation(H2, 0F, 0F, 0F);
		Shape24 = new ModelPart(this, 10, 41);
		Shape24.addCuboid(0F, 0F, 0F, 13, 4, 5);
		Shape24.setPivot(-9F, -1F, -2F);
		Shape24.setTextureSize(64, 64);
		Shape24.mirror = true;
		setRotation(Shape24, 0F, 0F, 0F);
		Shape25 = new ModelPart(this, 0, 20);
		Shape25.addCuboid(0F, 0F, 0F, 4, 2, 4);
		Shape25.setPivot(-6F, -3F, -1.5F);
		Shape25.setTextureSize(64, 64);
		Shape25.mirror = true;
		setRotation(Shape25, 0F, 0F, 0F);
		Shape26 = new ModelPart(this, 0, 41);
		Shape26.addCuboid(0F, 0F, 0F, 1, 5, 4);
		Shape26.setPivot(-10F, -1.5F, -1.5F);
		Shape26.setTextureSize(64, 64);
		Shape26.mirror = true;
		setRotation(Shape26, 0F, 0F, 0F);
		Shape27 = new ModelPart(this, 0, 60);
		Shape27.addCuboid(0F, 0F, 0F, 3, 2, 2);
		Shape27.setPivot(-13F, 1F, -0.5F);
		Shape27.setTextureSize(64, 64);
		Shape27.mirror = true;
		setRotation(Shape27, 0F, 0F, 0F);
		Shape28 = new ModelPart(this, 8, 57);
		Shape28.addCuboid(0F, 0F, 0F, 3, 1, 1);
		Shape28.setPivot(-13F, -1F, -1F);
		Shape28.setTextureSize(64, 64);
		Shape28.mirror = true;
		setRotation(Shape28, 0F, 0F, 0F);
		Shape29 = new ModelPart(this, 13, 26);
		Shape29.addCuboid(0F, 0F, 0F, 2, 4, 6);
		Shape29.setPivot(4F, -0.5F, -2.5F);
		Shape29.setTextureSize(64, 64);
		Shape29.mirror = true;
		setRotation(Shape29, 0F, 0F, 0F);
		Shape30 = new ModelPart(this, 54, 49);
		Shape30.addCuboid(-1.5F, -1.5F, 0F, 3, 3, 2);
		Shape30.setPivot(1.5F, 1.5F, 2.5F);
		Shape30.setTextureSize(64, 64);
		Shape30.mirror = true;
		setRotation(Shape30, 0F, 0F, 0F);
		Shape31 = new ModelPart(this, 54, 55);
		Shape31.addCuboid(-3F, 0F, 0F, 3, 6, 2);
		Shape31.setPivot(-2F, 5F, -0.5F);
		Shape31.setTextureSize(64, 64);
		Shape31.mirror = true;
		setRotation(Shape31, 0F, 0F, 0.2617994F);
		Shape32 = new ModelPart(this, 52, 19);
		Shape32.addCuboid(0F, 0F, 0F, 0, 2, 1);
		Shape32.setPivot(-1.5F, 4.5F, 0F);
		Shape32.setTextureSize(64, 64);
		Shape32.mirror = true;
		setRotation(Shape32, 0F, 0F, -0.2094395F);
		Shape33 = new ModelPart(this, 52, 19);
		Shape33.addCuboid(0F, -3F, 0F, 0, 3, 2);
		Shape33.setPivot(-0.5F, 7F, -0.5F);
		Shape33.setTextureSize(64, 64);
		Shape33.mirror = true;
		setRotation(Shape33, 0F, 0F, 0.2617994F);
		Shape34 = new ModelPart(this, 0, 36);
		Shape34.addCuboid(0F, 0F, 0F, 5, 1, 4);
		Shape34.setPivot(-1.5F, -2F, -1.5F);
		Shape34.setTextureSize(64, 64);
		Shape34.mirror = true;
		setRotation(Shape34, 0F, 0F, 0F);
		Shape35 = new ModelPart(this, 0, 36);
		Shape35.addCuboid(0F, 0F, 0F, 2, 1, 4);
		Shape35.setPivot(-9F, -2F, -1.5F);
		Shape35.setTextureSize(64, 64);
		Shape35.mirror = true;
		setRotation(Shape35, 0F, 0F, 0F);
		Shape36 = new ModelPart(this, 0, 14);
		Shape36.addCuboid(0F, 0F, 0F, 3, 1, 3);
		Shape36.setPivot(-5.5F, -4F, -1F);
		Shape36.setTextureSize(64, 64);
		Shape36.mirror = true;
		setRotation(Shape36, 0F, 0F, 0F);
		Shape37 = new ModelPart(this, 42, 58);
		Shape37.addCuboid(0F, 0F, 0F, 2, 2, 4);
		Shape37.setPivot(-16F, 3.5F, -1.5F);
		Shape37.setTextureSize(64, 64);
		Shape37.mirror = true;
		setRotation(Shape37, 0F, 0F, 0F);
		Shape38 = new ModelPart(this, 16, 55);
		Shape38.addCuboid(0F, 0F, 0F, 1, 5, 3);
		Shape38.setPivot(-15.5F, -1.5F, -1F);
		Shape38.setTextureSize(64, 64);
		Shape38.mirror = true;
		setRotation(Shape38, 0F, 0F, 0F);
		Shape39 = new ModelPart(this, 42, 53);
		Shape39.addCuboid(0F, 0F, 0F, 2, 1, 4);
		Shape39.setPivot(-16F, -2.5F, -1.5F);
		Shape39.setTextureSize(64, 64);
		Shape39.mirror = true;
		setRotation(Shape39, 0F, 0F, 0F);
		Shape40 = new ModelPart(this, 28, 54);
		Shape40.addCuboid(0F, 0F, 0F, 2, 5, 5);
		Shape40.setPivot(-15F, -1.5F, -2F);
		Shape40.setTextureSize(64, 64);
		Shape40.mirror = true;
		setRotation(Shape40, 0F, 0F, 0F);
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
		float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
	int overlay) {

		if (part == 0) {
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
			Shape13.render(matrices, vertices, light, overlay);
			Shape14.render(matrices, vertices, light, overlay);
			Shape20.render(matrices, vertices, light, overlay);
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
			Shape37.render(matrices, vertices, light, overlay);
			Shape38.render(matrices, vertices, light, overlay);
			Shape39.render(matrices, vertices, light, overlay);
			Shape40.render(matrices, vertices, light, overlay);
		} else {
			float headProgress =0.0f;
			if (fireProgress>0.0f) {
				headProgress = fireProgress;
			}
			if(headProgress>0.5f) {
				headProgress=1f-headProgress;
			}
			if (transformType==Mode.GUI|| transformType==Mode.FIXED || transformType==Mode.GROUND) {
				headProgress=0.25f;
			}
			
			matrices.push();
			matrices.translate(-0.4f+0.8f*headProgress,0,0);
			H7.render(matrices, vertices, light, overlay);
			H9.render(matrices, vertices, light, overlay);
			H8.render(matrices, vertices, light, overlay);
			H5.render(matrices, vertices, light, overlay);
			H6.render(matrices, vertices, light, overlay);
			H4.render(matrices, vertices, light, overlay);
			H3.render(matrices, vertices, light, overlay);
			H1.render(matrices, vertices, light, overlay);
			H2.render(matrices, vertices, light, overlay);
			matrices.pop();
		}
	}
}
