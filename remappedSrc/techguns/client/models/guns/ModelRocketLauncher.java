package techguns.client.models.guns;

import java.util.HashMap;

import net.minecraft.client.model.ModelPart;
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
import techguns.items.guns.ammo.AmmoTypes;

public class ModelRocketLauncher extends ModelMultipart {
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
	ModelPart Shape22;
	ModelPart Shape21;

	ModelRocket rocket;
	
	protected static final HashMap<String,Identifier> textures = new HashMap<>();
	static {
		textures.put(AmmoTypes.TYPE_DEFAULT, new TGIdentifier("textures/guns/rocket.png"));
		textures.put(AmmoTypes.TYPE_NUKE, new TGIdentifier("textures/guns/rocket_nuke.png"));
		textures.put(AmmoTypes.TYPE_HV, new TGIdentifier("textures/guns/rocket_hv.png"));
	}
	
	public ModelRocketLauncher() {
		super(RenderLayer::getEntityCutout);
		textureWidth = 128;
		textureHeight = 64;

		Shape1 = new ModelPart(this, 0, 28);
		Shape1.addCuboid(0F, 0F, 0F, 6, 1, 3);
		Shape1.setPivot(20F, 3.5F, -1F);
		Shape1.setTextureSize(128, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelPart(this, 0, 55);
		Shape2.addCuboid(0F, 0F, 0F, 36, 3, 6);
		Shape2.setPivot(-16F, -1F, -2.5F);
		Shape2.setTextureSize(128, 64);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelPart(this, 0, 24);
		Shape3.addCuboid(0F, 0F, 0F, 6, 3, 1);
		Shape3.setPivot(20F, -1F, -3.5F);
		Shape3.setTextureSize(128, 64);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelPart(this, 0, 24);
		Shape4.addCuboid(0F, 0F, 0F, 6, 3, 1);
		Shape4.setPivot(20F, -1F, 3.5F);
		Shape4.setTextureSize(128, 64);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelPart(this, 0, 28);
		Shape5.addCuboid(0F, 0F, 0F, 6, 1, 3);
		Shape5.setPivot(20F, -3.5F, -1F);
		Shape5.setTextureSize(128, 64);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelPart(this, 1, 46);
		Shape6.addCuboid(0F, 0F, 0F, 36, 6, 3);
		Shape6.setPivot(-16F, -2.5F, -1F);
		Shape6.setTextureSize(128, 64);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape7 = new ModelPart(this, 99, 22);
		Shape7.addCuboid(0F, 0F, 0F, 3, 4, 1);
		Shape7.setPivot(-19F, -1.5F, 3.5F);
		Shape7.setTextureSize(128, 64);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Shape8 = new ModelPart(this, 0, 36);
		Shape8.addCuboid(0F, 0F, 0F, 36, 5, 5);
		Shape8.setPivot(-16F, -2F, -2F);
		Shape8.setTextureSize(128, 64);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0F, 0F);
		Shape9 = new ModelPart(this, 81, 20);
		Shape9.addCuboid(0F, 0F, 0F, 3, 6, 6);
		Shape9.setPivot(-19F, -2.5F, -2.5F);
		Shape9.setTextureSize(128, 64);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);
		Shape10 = new ModelPart(this, 99, 27);
		Shape10.addCuboid(0F, 0F, 0F, 3, 1, 4);
		Shape10.setPivot(-19F, -3.5F, -1.5F);
		Shape10.setTextureSize(128, 64);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 0F, 0F);
		Shape11 = new ModelPart(this, 99, 27);
		Shape11.addCuboid(0F, 0F, 0F, 3, 1, 4);
		Shape11.setPivot(-19F, 3.5F, -1.5F);
		Shape11.setTextureSize(128, 64);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0F);
		Shape12 = new ModelPart(this, 99, 22);
		Shape12.addCuboid(0F, 0F, 0F, 3, 4, 1);
		Shape12.setPivot(-19F, -1.5F, -3.5F);
		Shape12.setTextureSize(128, 64);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelPart(this, 62, 22);
		Shape13.addCuboid(0F, 0F, 0F, 6, 5, 1);
		Shape13.setPivot(6F, -2F, 3F);
		Shape13.setTextureSize(128, 64);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, 0F);
		Shape14 = new ModelPart(this, 52, 23);
		Shape14.addCuboid(-3F, 0F, 0F, 3, 7, 2);
		Shape14.setPivot(10F, 4F, -0.5F);
		Shape14.setTextureSize(128, 64);
		Shape14.mirror = true;
		setRotation(Shape14, 0F, 0F, 0.2974289F);
		Shape15 = new ModelPart(this, 62, 22);
		Shape15.addCuboid(0F, 0F, 0F, 6, 5, 1);
		Shape15.setPivot(6F, -2F, -3F);
		Shape15.setTextureSize(128, 64);
		Shape15.mirror = true;
		setRotation(Shape15, 0F, 0F, 0F);

		Shape16 = new ModelPart(this, 35, 27);
		Shape16.addCuboid(0F, 0F, 0F, 1, 0, 2);
		Shape16.setPivot(10F, -6F, -0.5F);
		Shape16.setTextureSize(128, 64);
		Shape16.mirror = true;
		setRotation(Shape16, 0F, 0F, 0F);
		Shape22 = new ModelPart(this, 37, 27);
		Shape22.addCuboid(0F, 0F, 0F, 1, 3, 0);
		Shape22.setPivot(10F, -6F, -0.5F);
		Shape22.setTextureSize(128, 64);
		Shape22.mirror = true;
		setRotation(Shape22, 0F, 0F, 0F);
		Shape21 = new ModelPart(this, 37, 27);
		Shape21.addCuboid(0F, 0F, 0F, 1, 3, 0);
		Shape21.setPivot(10F, -6F, 1.5F);
		Shape21.setTextureSize(128, 64);
		Shape21.mirror = true;
		setRotation(Shape21, 0F, 0F, 0F);

		Shape17 = new ModelPart(this, 52, 21);
		Shape17.addCuboid(0F, 0F, 0F, 2, 1, 1);
		Shape17.setPivot(9F, 4F, 0F);
		Shape17.setTextureSize(128, 64);
		Shape17.mirror = true;
		setRotation(Shape17, 0F, 0F, 0F);
		Shape18 = new ModelPart(this, 30, 20);
		Shape18.addCuboid(0F, 0F, 0F, 6, 7, 5);
		Shape18.setPivot(6F, -3F, -2F);
		Shape18.setTextureSize(128, 64);
		Shape18.mirror = true;
		setRotation(Shape18, 0F, 0F, 0F);
		Shape19 = new ModelPart(this, 62, 28);
		Shape19.addCuboid(0F, 0F, 0F, 3, 1, 3);
		Shape19.setPivot(7F, -3.5F, -1F);
		Shape19.setTextureSize(128, 64);
		Shape19.mirror = true;
		setRotation(Shape19, 0F, 0F, 0F);
		Shape20 = new ModelPart(this, 104, 0);
		Shape20.addCuboid(0F, 0F, 0F, 6, 6, 6);
		Shape20.setPivot(20F, -2.5F, -2.5F);
		Shape20.setTextureSize(128, 64);
		Shape20.mirror = true;
		setRotation(Shape20, 0F, 0F, 0F);
		
		rocket = new ModelRocket();
	}
	
	@Override
	public RenderLayer getLayerForPart(IGenericGun gun, ItemStack stack, Identifier texture, int part) {
		if (part==1) {
			//Override Missile Texture depending on Ammo type
			String variant = gun.getCurrentAmmoVariantKey(stack);
			Identifier tex = textures.get(variant);
			return this.getLayer(tex);
		} else {
			return this.getLayer(texture);
		}
	}

	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
			float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
			int overlay) {

		if(part==0) {
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
			Shape21.render(matrices, vertices, light, overlay);
			Shape22.render(matrices, vertices, light, overlay);
			Shape17.render(matrices, vertices, light, overlay);
			Shape18.render(matrices, vertices, light, overlay);
			Shape19.render(matrices, vertices, light, overlay);
			Shape20.render(matrices, vertices, light, overlay);
		} else if(part==1) {
			if ((reloadProgress == 0 && ammoLeft > 0) || (reloadProgress > 0.5f)) {
				this.rocket.render(entityIn, matrices, vertices, ammoLeft, reloadProgress, transformType, part,
						fireProgress, chargeProgress, light, overlay);
			}
		}
	}
	
	protected static class ModelRocket extends ModelMultipart {
		
		ModelPart R1;
		ModelPart R7;
		ModelPart R4;
		ModelPart R2;
		ModelPart R5;
		ModelPart R3;
		ModelPart R6;
		
		public ModelRocket() {
			super(RenderLayer::getEntitySolid);
			textureWidth = 64;
			textureHeight = 32;
			
			R1 = new ModelPart(this, 0, 0);
			R1.addCuboid(0F, 0F, 0F, 6, 4, 4);
			R1.setPivot(21F, -1.5F, -1.5F);
			R1.setTextureSize(128, 64);
			R1.mirror = true;
			setRotation(R1, 0F, 0F, 0F);
			R7 = new ModelPart(this, 30, 0);
			R7.addCuboid(0F, 0F, 0F, 2, 1, 6);
			R7.setPivot(22.1F, 0F, -2.5F);
			R7.setTextureSize(128, 64);
			R7.mirror = true;
			setRotation(R7, 0F, 0F, 0F);
			R4 = new ModelPart(this, 12, 9);
			R4.addCuboid(0F, 0F, 0F, 4, 2, 5);
			R4.setPivot(22F, -0.5F, -2F);
			R4.setTextureSize(128, 64);
			R4.mirror = true;
			setRotation(R4, 0F, 0F, 0F);
			R2 = new ModelPart(this, 0, 16);
			R2.addCuboid(0F, 0F, 0F, 10, 3, 3);
			R2.setPivot(18F, -1F, -1F);
			R2.setTextureSize(128, 64);
			R2.mirror = true;
			setRotation(R2, 0F, 0F, 0F);
			R5 = new ModelPart(this, 0, 8);
			R5.addCuboid(0F, 0F, 0F, 4, 5, 2);
			R5.setPivot(22F, -2F, -0.5F);
			R5.setTextureSize(128, 64);
			R5.mirror = true;
			setRotation(R5, 0F, 0F, 0F);
			R3 = new ModelPart(this, 20, 3);
			R3.addCuboid(0F, 0F, 0F, 1, 2, 2);
			R3.setPivot(28F, -0.5F, -0.5F);
			R3.setTextureSize(128, 64);
			R3.mirror = true;
			setRotation(R3, 0F, 0F, 0F);
			R6 = new ModelPart(this, 30, 7);
			R6.addCuboid(0F, 0F, 0F, 2, 6, 1);
			R6.setPivot(22.1F, -2.5F, 0F);
			R6.setTextureSize(128, 64);
			R6.mirror = true;
			setRotation(R6, 0F, 0F, 0F);
		}

		@Override
		public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
				float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
				int overlay) {
			R1.render(matrices, vertices, light, overlay);
			R7.render(matrices, vertices, light, overlay);
			R4.render(matrices, vertices, light, overlay);
			R2.render(matrices, vertices, light, overlay);
			R5.render(matrices, vertices, light, overlay);
			R3.render(matrices, vertices, light, overlay);
			R6.render(matrices, vertices, light, overlay);
		}
		
	}

}
