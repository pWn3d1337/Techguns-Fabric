package techguns.client.models.guns;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelM4 extends ModelMultipart {

	// fields
	ModelPart Grip1;
	ModelPart Receiver02;
	ModelPart Trigger01;
	ModelPart Trigger02;
	ModelPart Magazine02;
	ModelPart Grip02;
	ModelPart Stock01;
	ModelPart Bolt03;
	ModelPart BarrelGuard;
	ModelPart BarrelRails01;
	ModelPart Eotech01;
	ModelPart BarrelRails02;
	ModelPart IronSight01;
	ModelPart Barrel;
	ModelPart Stock02;
	ModelPart BarrelRails03;
	ModelPart IronSight02;
	ModelPart IronSight03;
	ModelPart Receiver03;
	ModelPart Barrel02;
	ModelPart Magazine01;
	ModelPart Stock03;
	ModelPart Stock04;
	ModelPart Stock05;
	ModelPart Stock06;
	ModelPart Stock07;
	ModelPart Stock08;
	ModelPart Stock09;
	ModelPart Stock10;
	ModelPart TopRails;
	ModelPart IronSightTop;
	ModelPart Eotech02;
	ModelPart Eotech03;
	ModelPart Eotech04;
	ModelPart Grip01;
	ModelPart BarrelRails04;
	ModelPart MagSocket;
	ModelPart Receiver01;
	ModelPart Receiver04;
	ModelPart Bolt01;
	ModelPart Bolt02;
	ModelPart RedDot;

	public ModelM4() {
		super(RenderLayer::getEntitySolid);
		
		textureWidth = 128;
		textureHeight = 64;

		Grip1 = new ModelPart(this, 68, 0);
		Grip1.addCuboid(0F, 0F, 0F, 2.98F, 9, 4);
		Grip1.setPivot(-2.01F, 2F, 6F);
		Grip1.setTextureSize(128, 64);
		Grip1.mirror = true;
		setRotation(Grip1, 0.4461433F, 0F, 0F);
		Receiver02 = new ModelPart(this, 106, 19);
		Receiver02.addCuboid(0F, 0F, 0F, 3, 1, 2);
		Receiver02.setPivot(-2F, -1F, 10F);
		Receiver02.setTextureSize(128, 64);
		Receiver02.mirror = true;
		setRotation(Receiver02, 0F, 0F, 0F);
		Trigger01 = new ModelPart(this, 78, 12);
		Trigger01.addCuboid(0F, 0F, 0F, 2, 1, 5);
		Trigger01.setPivot(-1.5F, 4F, 3F);
		Trigger01.setTextureSize(128, 64);
		Trigger01.mirror = true;
		setRotation(Trigger01, 0F, 0F, 0F);
		Trigger02 = new ModelPart(this, 87, 1);
		Trigger02.addCuboid(0F, 0F, 0F, 1, 2, 1);
		Trigger02.setPivot(-1F, 1.5F, 5F);
		Trigger02.setTextureSize(128, 64);
		Trigger02.mirror = true;
		setRotation(Trigger02, -0.2617994F, 0F, 0F);
		Magazine02 = new ModelPart(this, 95, 19);
		Magazine02.addCuboid(0F, 0F, 0F, 2, 4, 6);
		Magazine02.setPivot(-1.5F, 9F, -4.4F);
		Magazine02.setTextureSize(128, 64);
		Magazine02.mirror = true;
		setRotation(Magazine02, -0.1570796F, 0F, 0F);
		Grip02 = new ModelPart(this, 56, 6);
		Grip02.addCuboid(0F, 0F, 0F, 2, 8, 2);
		Grip02.setPivot(-2F, 0.5F, -13.5F);
		Grip02.setTextureSize(128, 64);
		Grip02.mirror = true;
		setRotation(Grip02, 0F, 0.7853982F, 0F);
		Stock01 = new ModelPart(this, 65, 46);
		Stock01.addCuboid(0F, 0F, 0F, 3, 3, 15);
		Stock01.setPivot(-2F, -4F, 13F);
		Stock01.setTextureSize(128, 64);
		Stock01.mirror = true;
		setRotation(Stock01, 0F, 0F, 0F);
		Bolt03 = new ModelPart(this, 0, 0);
		Bolt03.addCuboid(0F, 0F, 0F, 4, 1, 1);
		Bolt03.setPivot(-3.5F, -3.8F, 11F);
		Bolt03.setTextureSize(128, 64);
		Bolt03.mirror = true;
		setRotation(Bolt03, 0F, 1.301251F, 0F);
		BarrelGuard = new ModelPart(this, 32, 13);
		BarrelGuard.addCuboid(0F, 0F, 0F, 4, 4, 15);
		BarrelGuard.setPivot(-2.5F, -5F, -23F);
		BarrelGuard.setTextureSize(128, 64);
		BarrelGuard.mirror = true;
		setRotation(BarrelGuard, 0F, 0F, 0F);
		BarrelRails01 = new ModelPart(this, 36, 33);
		BarrelRails01.addCuboid(0F, 0F, 0F, 1, 2, 13);
		BarrelRails01.setPivot(1.5F, -4F, -22F);
		BarrelRails01.setTextureSize(128, 64);
		BarrelRails01.mirror = true;
		setRotation(BarrelRails01, 0F, 0F, 0F);
		Eotech01 = new ModelPart(this, 0, 3);
		Eotech01.addCuboid(0F, 0F, 0F, 1, 2.5F, 4);
		Eotech01.setPivot(0.5F, -11F, -1F);
		Eotech01.setTextureSize(128, 64);
		Eotech01.mirror = true;
		setRotation(Eotech01, 0F, 0F, 0F);
		BarrelRails02 = new ModelPart(this, 36, 33);
		BarrelRails02.addCuboid(0F, 0F, 0F, 1, 2, 13);
		BarrelRails02.setPivot(-3.5F, -4F, -22F);
		BarrelRails02.setTextureSize(128, 64);
		BarrelRails02.mirror = true;
		setRotation(BarrelRails02, 0F, 0F, 0F);
		IronSight01 = new ModelPart(this, 0, 47);
		IronSight01.addCuboid(0F, 0F, 0F, 0.98F, 4, 1);
		IronSight01.setPivot(-1.01F, -8F, -25F);
		IronSight01.setTextureSize(128, 64);
		IronSight01.mirror = true;
		setRotation(IronSight01, 0.4363323F, 0F, 0F);
		Barrel = new ModelPart(this, 0, 26);
		Barrel.addCuboid(0F, 0F, 0F, 2, 2, 18);
		Barrel.setPivot(-0.5F, -4.5F, -41F);
		Barrel.setTextureSize(128, 64);
		Barrel.mirror = true;
		setRotation(Barrel, 0F, 0F, 0.7853982F);
		Stock02 = new ModelPart(this, 21, 47);
		Stock02.addCuboid(0F, 0F, 0F, 2, 2, 1);
		Stock02.setPivot(-0.5F, -4F, 12F);
		Stock02.setTextureSize(128, 64);
		Stock02.mirror = true;
		setRotation(Stock02, 0F, 0F, 0.7853982F);
		BarrelRails03 = new ModelPart(this, 34, 48);
		BarrelRails03.addCuboid(0F, 0F, 0F, 2, 1, 13);
		BarrelRails03.setPivot(-1.5F, -6F, -22F);
		BarrelRails03.setTextureSize(128, 64);
		BarrelRails03.mirror = true;
		setRotation(BarrelRails03, 0F, 0F, 0F);
		IronSight02 = new ModelPart(this, 5, 47);
		IronSight02.addCuboid(0F, 0F, 0F, 1, 2, 2);
		IronSight02.setPivot(-1F, -9F, -26F);
		IronSight02.setTextureSize(128, 64);
		IronSight02.mirror = true;
		setRotation(IronSight02, 0F, 0F, 0F);
		IronSight03 = new ModelPart(this, 0, 47);
		IronSight03.addCuboid(0F, 0F, 0F, 1, 3, 1);
		IronSight03.setPivot(-1F, -7F, -26F);
		IronSight03.setTextureSize(128, 64);
		IronSight03.mirror = true;
		setRotation(IronSight03, 0F, 0F, 0F);
		Receiver03 = new ModelPart(this, 92, 0);
		Receiver03.addCuboid(0F, 0F, 0F, 3, 3, 15);
		Receiver03.setPivot(-2F, -1F, -5F);
		Receiver03.setTextureSize(128, 64);
		Receiver03.mirror = true;
		setRotation(Receiver03, 0F, 0F, 0F);
		Barrel02 = new ModelPart(this, 0, 37);
		Barrel02.addCuboid(0F, 0F, 0F, 4, 4, 2);
		Barrel02.setPivot(-0.5F, -6F, -8F);
		Barrel02.setTextureSize(128, 64);
		Barrel02.mirror = true;
		setRotation(Barrel02, 0F, 0F, 0.7853982F);
		Magazine01 = new ModelPart(this, 112, 19);
		Magazine01.addCuboid(0F, 0F, 0F, 2, 5, 6);
		Magazine01.setPivot(-1.5F, 5F, -4.5F);
		Magazine01.setTextureSize(128, 64);
		Magazine01.mirror = true;
		setRotation(Magazine01, 0F, 0F, 0F);
		Stock03 = new ModelPart(this, 87, 48);
		Stock03.addCuboid(0F, 0F, 0F, 1, 4, 8);
		Stock03.setPivot(-1F, -2F, 16F);
		Stock03.setTextureSize(128, 64);
		Stock03.mirror = true;
		setRotation(Stock03, 0F, 0F, 0F);
		Stock04 = new ModelPart(this, 72, 35);
		Stock04.addCuboid(0F, 0F, 0F, 2, 8, 2);
		Stock04.setPivot(-1.5F, -1F, 26F);
		Stock04.setTextureSize(128, 64);
		Stock04.mirror = true;
		setRotation(Stock04, 0F, 0F, 0F);
		Stock05 = new ModelPart(this, 98, 47);
		Stock05.addCuboid(0F, 0F, 0F, 1, 5, 3);
		Stock05.setPivot(-1F, -2F, 24F);
		Stock05.setTextureSize(128, 64);
		Stock05.mirror = true;
		setRotation(Stock05, 0F, 0F, 0F);
		Stock06 = new ModelPart(this, 65, 46);
		Stock06.addCuboid(0F, 0F, 0F, 2, 3, 1);
		Stock06.setPivot(-1.5F, -1F, 15F);
		Stock06.setTextureSize(128, 64);
		Stock06.mirror = true;
		setRotation(Stock06, 0F, 0F, 0F);
		Stock07 = new ModelPart(this, 72, 46);
		Stock07.addCuboid(0F, 0F, 0F, 2, 2, 2);
		Stock07.setPivot(-1.5F, 2F, 19F);
		Stock07.setTextureSize(128, 64);
		Stock07.mirror = true;
		setRotation(Stock07, 0F, 0F, 0F);
		Stock08 = new ModelPart(this, 81, 34);
		Stock08.addCuboid(0F, 0F, 0F, 1.98F, 9, 2);
		Stock08.setPivot(-1.51F, -1F, 21F);
		Stock08.setTextureSize(128, 64);
		Stock08.mirror = true;
		setRotation(Stock08, 0.5915014F, 0F, 0F);
		Stock09 = new ModelPart(this, 72, 51);
		Stock09.addCuboid(0F, 0F, 0F, 1.98F, 4, 2);
		Stock09.setPivot(-1.51F, 2F, 16F);
		Stock09.setTextureSize(128, 64);
		Stock09.mirror = true;
		setRotation(Stock09, 1.047198F, 0F, 0F);
		Stock10 = new ModelPart(this, 64, 51);
		Stock10.addCuboid(0F, 0F, 0F, 2, 2, 2);
		Stock10.setPivot(-1.5F, 0F, 16F);
		Stock10.setTextureSize(128, 64);
		Stock10.mirror = true;
		setRotation(Stock10, 0F, 0F, 0F);
		TopRails = new ModelPart(this, 2, 48);
		TopRails.addCuboid(0F, 0F, 0F, 2, 1, 15);
		TopRails.setPivot(-1.5F, -6F, -5.5F);
		TopRails.setTextureSize(128, 64);
		TopRails.mirror = true;
		setRotation(TopRails, 0F, 0F, 0F);
		IronSightTop = new ModelPart(this, 0, 53);
		IronSightTop.addCuboid(0F, 0F, 0F, 3, 2, 4);
		IronSightTop.setPivot(-2F, -7F, 5F);
		IronSightTop.setTextureSize(128, 64);
		IronSightTop.mirror = true;
		setRotation(IronSightTop, 0F, 0F, 0F);
		Eotech02 = new ModelPart(this, 56, 15);
		Eotech02.addCuboid(0F, 0F, 0F, 4, 3.5F, 9);
		Eotech02.setPivot(-2.5F, -8.5F, -5F);
		Eotech02.setTextureSize(128, 64);
		Eotech02.mirror = true;
		setRotation(Eotech02, 0F, 0F, 0F);
		Eotech03 = new ModelPart(this, 0, 10);
		Eotech03.addCuboid(0F, 0F, 0F, 4, 1, 4);
		Eotech03.setPivot(-2.5F, -12F, -1F);
		Eotech03.setTextureSize(128, 64);
		Eotech03.mirror = true;
		setRotation(Eotech03, 0F, 0F, 0F);
		Eotech04 = new ModelPart(this, 0, 3);
		Eotech04.addCuboid(0F, 0F, 0F, 1, 2.5F, 4);
		Eotech04.setPivot(-2.5F, -11F, -1F);
		Eotech04.setTextureSize(128, 64);
		Eotech04.mirror = true;
		setRotation(Eotech04, 0F, 0F, 0F);
		Grip01 = new ModelPart(this, 53, 0);
		Grip01.addCuboid(0F, 0F, 0F, 3, 2, 4);
		Grip01.setPivot(-2F, -1.5F, -15.5F);
		Grip01.setTextureSize(128, 64);
		Grip01.mirror = true;
		setRotation(Grip01, 0F, 0F, 0F);
		BarrelRails04 = new ModelPart(this, 34, 48);
		BarrelRails04.addCuboid(0F, 0F, 0F, 2, 1, 13);
		BarrelRails04.setPivot(-1.5F, -1F, -22F);
		BarrelRails04.setTextureSize(128, 64);
		BarrelRails04.mirror = true;
		setRotation(BarrelRails04, 0F, 0F, 0F);
		MagSocket = new ModelPart(this, 84, 0);
		MagSocket.addCuboid(0F, 0F, 0F, 3, 3, 8);
		MagSocket.setPivot(-2F, 2F, -5F);
		MagSocket.setTextureSize(128, 64);
		MagSocket.mirror = true;
		setRotation(MagSocket, 0F, 0F, 0F);
		Receiver01 = new ModelPart(this, 2, 5);
		Receiver01.addCuboid(0F, 0F, 0F, 4, 4, 16);
		Receiver01.setPivot(-2.5F, -5F, -6F);
		Receiver01.setTextureSize(128, 64);
		Receiver01.mirror = true;
		setRotation(Receiver01, 0F, 0F, 0F);
		Receiver04 = new ModelPart(this, 0, 15);
		Receiver04.addCuboid(0F, 0F, 0F, 4, 3, 2);
		Receiver04.setPivot(-2.5F, -4F, 10F);
		Receiver04.setTextureSize(128, 64);
		Receiver04.mirror = true;
		setRotation(Receiver04, 0F, 0F, 0F);
		Bolt01 = new ModelPart(this, 11, 0);
		Bolt01.addCuboid(0F, 0F, 0F, 2, 1, 1);
		Bolt01.setPivot(-1.5F, -5F, 10F);
		Bolt01.setTextureSize(128, 64);
		Bolt01.mirror = true;
		setRotation(Bolt01, 0F, 0F, 0F);
		Bolt02 = new ModelPart(this, 0, 0);
		Bolt02.addCuboid(0F, 0F, 0F, 4, 1, 1);
		Bolt02.setPivot(-2.5F, -5F, 11F);
		Bolt02.setTextureSize(128, 64);
		Bolt02.mirror = true;
		setRotation(Bolt02, 0F, 0F, 0F);
		RedDot = new ModelPart(this, 1, 11);
		RedDot.addCuboid(0F, 0F, 0F, 0.1F, 0.1F, 0);
		RedDot.setPivot(-1F+0.45F, -10.5F+0.45F, 1F);
		RedDot.setTextureSize(128, 64);
		RedDot.mirror = true;
		setRotation(RedDot, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
			float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
			int overlay) {
		// setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		Grip1.render(matrices, vertices, light, overlay);
		Receiver02.render(matrices, vertices, light, overlay);
		Trigger01.render(matrices, vertices, light, overlay);
		Trigger02.render(matrices, vertices, light, overlay);
		Magazine02.render(matrices, vertices, light, overlay);
		Grip02.render(matrices, vertices, light, overlay);
		Stock01.render(matrices, vertices, light, overlay);
		Bolt03.render(matrices, vertices, light, overlay);
		BarrelGuard.render(matrices, vertices, light, overlay);
		BarrelRails01.render(matrices, vertices, light, overlay);
		Eotech01.render(matrices, vertices, light, overlay);
		BarrelRails02.render(matrices, vertices, light, overlay);
		IronSight01.render(matrices, vertices, light, overlay);
		Barrel.render(matrices, vertices, light, overlay);
		Stock02.render(matrices, vertices, light, overlay);
		BarrelRails03.render(matrices, vertices, light, overlay);
		IronSight02.render(matrices, vertices, light, overlay);
		IronSight03.render(matrices, vertices, light, overlay);
		Receiver03.render(matrices, vertices, light, overlay);
		Barrel02.render(matrices, vertices, light, overlay);
		Magazine01.render(matrices, vertices, light, overlay);
		Stock03.render(matrices, vertices, light, overlay);
		Stock04.render(matrices, vertices, light, overlay);
		Stock05.render(matrices, vertices, light, overlay);
		Stock06.render(matrices, vertices, light, overlay);
		Stock07.render(matrices, vertices, light, overlay);
		Stock08.render(matrices, vertices, light, overlay);
		Stock09.render(matrices, vertices, light, overlay);
		Stock10.render(matrices, vertices, light, overlay);
		TopRails.render(matrices, vertices, light, overlay);
		IronSightTop.render(matrices, vertices, light, overlay);
		Eotech02.render(matrices, vertices, light, overlay);
		Eotech03.render(matrices, vertices, light, overlay);
		Eotech04.render(matrices, vertices, light, overlay);
		Grip01.render(matrices, vertices, light, overlay);
		BarrelRails04.render(matrices, vertices, light, overlay);
		MagSocket.render(matrices, vertices, light, overlay);
		Receiver01.render(matrices, vertices, light, overlay);
		Receiver04.render(matrices, vertices, light, overlay);
		Bolt01.render(matrices, vertices, light, overlay);
		Bolt02.render(matrices, vertices, light, overlay);
		RedDot.render(matrices, vertices, bright_light, overlay);
	}

}
