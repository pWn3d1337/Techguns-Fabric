package techguns.client.models.items;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;
import techguns.client.models.ModelPart;

public class ModelAS50Mag extends ModelMultipart {

	// fields
	ModelPart Magazine01;
	ModelPart Magazine03;
	ModelPart Bullet2;
	ModelPart Magazine02;
	ModelPart Bullet;
	boolean empty;

	public ModelAS50Mag(boolean empty) {
		super(RenderLayer::getEntitySolid);
		textureWidth = 64;
		textureHeight = 32;

		this.empty = empty;

		Magazine01 = new ModelPart(this, 0, 19);
		Magazine01.addCuboid(0F, 0F, 0F, 2, 2, 1);
		Magazine01.setPivot(-1.5F, 7F, -0.5F);
		Magazine01.mirror = true;
		setRotation(Magazine01, 0F, 0F, 0F);
		Magazine03 = new ModelPart(this, 17, 10);
		Magazine03.addCuboid(0F, 0F, 0F, 2, 2, 11);
		Magazine03.setPivot(-1.5F, 6F, -11F);
		Magazine03.mirror = true;
		setRotation(Magazine03, -0.0916298F, 0F, 0F);
		if (!empty) {
			Bullet2 = new ModelPart(this, 0, 0);
			Bullet2.addCuboid(0F, 0F, 0F, 1, 1, 5);
			Bullet2.setPivot(-0.5F, -1.1F, -11F);
			Bullet2.mirror = true;
			setRotation(Bullet2, 0F, 0F, 0.7853982F);
		}
		Magazine02 = new ModelPart(this, 0, 12);
		Magazine02.addCuboid(0F, 0F, 0F, 2, 8, 12);
		Magazine02.setPivot(-1.5F, 0F, -11.5F);
		Magazine02.mirror = true;
		setRotation(Magazine02, 0F, 0F, 0F);
		if (!empty) {
			Bullet = new ModelPart(this, 13, 0);
			Bullet.addCuboid(0F, 0F, 0F, 2, 2, 6);
			Bullet.setPivot(-0.5F, -1.8F, -6F);
			Bullet.mirror = true;
			setRotation(Bullet, 0F, 0F, 0.7853982F);
		}
	}


	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
			float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
			int overlay) {

		Magazine01.render(matrices, vertices, light, overlay);
		Magazine03.render(matrices, vertices, light, overlay);
		Magazine02.render(matrices, vertices, light, overlay);
		if (!empty) {
			Bullet2.render(matrices, vertices, light, overlay);
			Bullet.render(matrices, vertices, light, overlay);
		}
	}

}
