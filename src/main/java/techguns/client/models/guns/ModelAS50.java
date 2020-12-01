package techguns.client.models.guns;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelAS50 extends ModelMultipart{
	 //fields
   ModelPart Grip1;
   ModelPart Trigger01;
   ModelPart Trigger02;
   ModelPart MuzzleFront2;
   ModelPart Magazine01;
   ModelPart Trigger03;
   ModelPart MagHolder;
   ModelPart CheekRest;
   ModelPart LowerTrigger;
   ModelPart Magazine02;
   ModelPart Magazine03;
   ModelPart MidRailPart1;
   ModelPart UpperBarrel;
   ModelPart BarrelBigPart;
   ModelPart Barrel;
   ModelPart ReceiverTop;
   ModelPart BarrelBigPartTop;
   ModelPart ScopeMount4;
   ModelPart BarrelFrontPartBottom;
   ModelPart MidRailPart2;
   ModelPart MidRailPart3;
   ModelPart MidRailPart4;
   ModelPart MidRailPartTop;
   ModelPart MidRailPartFront;
   ModelPart BarrelMidPartTop;
   ModelPart BarrelMidPart01;
   ModelPart BarrelMidPartBottom;
   ModelPart ScopeFront;
   ModelPart BarrelFrontPart01;
   ModelPart MuzzleFront3;
   ModelPart MuzzleFront;
   ModelPart Muzzle05;
   ModelPart UpperTrigger;
   ModelPart StockBack02;
   ModelPart StockFlip;
   ModelPart StockBack01;
   ModelPart StockLower;
   ModelPart TopRails;
   ModelPart ScopeAdjust;
   ModelPart ScopeMount2;
   ModelPart BarrelBigPartFront;
   ModelPart Scope01;
   ModelPart Scope02;
   ModelPart Scope03;
   ModelPart Receiver;
   ModelPart ScopeMount;
   ModelPart ScopeAdjust2;
   ModelPart ScopeMiddle;
   ModelPart Muzzle01;
   ModelPart Muzzle04;
 
	public ModelAS50() {
		super(RenderLayer::getEntitySolid);
		textureWidth = 256;
		textureHeight = 128;

		Grip1 = new ModelPart(this, 73, 37);
		Grip1.addCuboid(0F, 0F, 0F, 3, 9, 4);
		Grip1.setPivot(-2F, 2F, 6F);
		Grip1.setTextureSize(256, 128);
		Grip1.mirror = true;
		setRotation(Grip1, 0.4461433F, 0F, 0F);
		Trigger01 = new ModelPart(this, 96, 32);
		Trigger01.addCuboid(0F, 0F, 0F, 2, 1, 6);
		Trigger01.setPivot(-1.5F, 4F, 2F);
		Trigger01.setTextureSize(256, 128);
		Trigger01.mirror = true;
		setRotation(Trigger01, 0F, 0F, 0F);
		Trigger02 = new ModelPart(this, 96, 34);
		Trigger02.addCuboid(0F, 0F, 0F, 2, 2, 1);
		Trigger02.setPivot(-1.5F, 2F, 2F);
		Trigger02.setTextureSize(256, 128);
		Trigger02.mirror = true;
		setRotation(Trigger02, 0F, 0F, 0F);
		MuzzleFront2 = new ModelPart(this, 0, 43);
		MuzzleFront2.addCuboid(0F, 0F, 0F, 1, 1, 1);
		MuzzleFront2.setPivot(-0.5F, -4.88F, -66F);
		MuzzleFront2.setTextureSize(256, 128);
		MuzzleFront2.mirror = true;
		setRotation(MuzzleFront2, 0F, 0F, 0.7853982F);
		Magazine01 = new ModelPart(this, 52, 101);
		Magazine01.addCuboid(0F, 0F, 0F, 2, 2, 1);
		Magazine01.setPivot(-1.5F, 7F, -0.5F);
		Magazine01.setTextureSize(256, 128);
		Magazine01.mirror = true;
		setRotation(Magazine01, 0F, 0F, 0F);
		Trigger03 = new ModelPart(this, 106, 34);
		Trigger03.addCuboid(0F, 0F, 0F, 1, 2, 1);
		Trigger03.setPivot(-1F, 1.5F, 5F);
		Trigger03.setTextureSize(256, 128);
		Trigger03.mirror = true;
		setRotation(Trigger03, -0.2617994F, 0F, 0F);
		MagHolder = new ModelPart(this, 0, 99);
		MagHolder.addCuboid(0F, 0F, 0F, 3, 4, 13);
		MagHolder.setPivot(-2F, -4F, -12F);
		MagHolder.setTextureSize(256, 128);
		MagHolder.mirror = true;
		setRotation(MagHolder, -0.0872665F, 0F, 0F);
		CheekRest = new ModelPart(this, 68, 3);
		CheekRest.addCuboid(0F, 0F, 0F, 4, 4, 16);
		CheekRest.setPivot(-0.5F, -10F, 17F);
		CheekRest.setTextureSize(256, 128);
		CheekRest.mirror = true;
		setRotation(CheekRest, 0F, 0F, 0.7853982F);
		LowerTrigger = new ModelPart(this, 96, 40);
		LowerTrigger.addCuboid(0F, 0F, 0F, 3, 1, 13);
		LowerTrigger.setPivot(-2F, 1F, 2F);
		LowerTrigger.setTextureSize(256, 128);
		LowerTrigger.mirror = true;
		setRotation(LowerTrigger, 0F, 0F, 0F);
		Magazine02 = new ModelPart(this, 34, 99);
		Magazine02.addCuboid(0F, 0F, 0F, 2, 8, 12);
		Magazine02.setPivot(-1.5F, 0F, -11.5F);
		Magazine02.setTextureSize(256, 128);
		Magazine02.mirror = true;
		setRotation(Magazine02, 0F, 0F, 0F);
		Magazine03 = new ModelPart(this, 63, 100);
		Magazine03.addCuboid(0F, 0F, 0F, 2, 2, 11);
		Magazine03.setPivot(-1.5F, 6F, -11F);
		Magazine03.setTextureSize(256, 128);
		Magazine03.mirror = true;
		setRotation(Magazine03, -0.0916298F, 0F, 0F);
		MidRailPart1 = new ModelPart(this, 0, 71);
		MidRailPart1.addCuboid(0F, 0F, 0F, 2, 1, 1);
		MidRailPart1.setPivot(-1.5F, -10F, -17F);
		MidRailPart1.setTextureSize(256, 128);
		MidRailPart1.mirror = true;
		setRotation(MidRailPart1, 0F, 0F, 0F);
		UpperBarrel = new ModelPart(this, 69, 72);
		UpperBarrel.addCuboid(0F, 0F, 0F, 1, 1, 19);
		UpperBarrel.setPivot(-0.5F, -9F, -48F);
		UpperBarrel.setTextureSize(256, 128);
		UpperBarrel.mirror = true;
		setRotation(UpperBarrel, 0F, 0F, 0.7853982F);
		BarrelBigPart = new ModelPart(this, 0, 65);
		BarrelBigPart.addCuboid(0F, 0F, 0F, 4, 4, 6);
		BarrelBigPart.setPivot(-0.5F, -8.5F, -21F);
		BarrelBigPart.setTextureSize(256, 128);
		BarrelBigPart.mirror = true;
		setRotation(BarrelBigPart, 0F, 0F, 0.7853982F);
		Barrel = new ModelPart(this, 0, 24);
		Barrel.addCuboid(0F, 0F, 0F, 2, 2, 38);
		Barrel.setPivot(-0.5F, -7F, -59F);
		Barrel.setTextureSize(256, 128);
		Barrel.mirror = true;
		setRotation(Barrel, 0F, 0F, 0.7853982F);
		ReceiverTop = new ModelPart(this, 0, 65);
		ReceiverTop.addCuboid(0F, 0F, 0F, 3, 1, 32);
		ReceiverTop.setPivot(-2F, -9F, -15F);
		ReceiverTop.setTextureSize(256, 128);
		ReceiverTop.mirror = true;
		setRotation(ReceiverTop, 0F, 0F, 0F);
		BarrelBigPartTop = new ModelPart(this, 0, 20);
		BarrelBigPartTop.addCuboid(0F, 0F, 0F, 2, 2, 14);
		BarrelBigPartTop.setPivot(-1.5F, -9F, -29F);
		BarrelBigPartTop.setTextureSize(256, 128);
		BarrelBigPartTop.mirror = true;
		setRotation(BarrelBigPartTop, 0F, 0F, 0F);
		ScopeMount4 = new ModelPart(this, 130, 0);
		ScopeMount4.addCuboid(0F, 0F, 0F, 3, 3, 1);
		ScopeMount4.setPivot(-2F, -15F, 3F);
		ScopeMount4.setTextureSize(256, 128);
		ScopeMount4.mirror = true;
		setRotation(ScopeMount4, 0F, 0F, 0F);
		BarrelFrontPartBottom = new ModelPart(this, 0, 37);
		BarrelFrontPartBottom.addCuboid(0F, 0F, 0F, 2, 1, 2);
		BarrelFrontPartBottom.setPivot(-1.5F, -4.5F, -61.5F);
		BarrelFrontPartBottom.setTextureSize(256, 128);
		BarrelFrontPartBottom.mirror = true;
		setRotation(BarrelFrontPartBottom, 0F, 0F, 0F);
		MidRailPart2 = new ModelPart(this, 0, 71);
		MidRailPart2.addCuboid(0F, 0F, 0F, 2, 1, 1);
		MidRailPart2.setPivot(-1.5F, -10F, -26F);
		MidRailPart2.setTextureSize(256, 128);
		MidRailPart2.mirror = true;
		setRotation(MidRailPart2, 0F, 0F, 0F);
		MidRailPart3 = new ModelPart(this, 0, 71);
		MidRailPart3.addCuboid(0F, 0F, 0F, 2, 1, 1);
		MidRailPart3.setPivot(-1.5F, -10F, -23F);
		MidRailPart3.setTextureSize(256, 128);
		MidRailPart3.mirror = true;
		setRotation(MidRailPart3, 0F, 0F, 0F);
		MidRailPart4 = new ModelPart(this, 0, 71);
		MidRailPart4.addCuboid(0F, 0F, 0F, 2, 1, 1);
		MidRailPart4.setPivot(-1.5F, -10F, -20F);
		MidRailPart4.setTextureSize(256, 128);
		MidRailPart4.mirror = true;
		setRotation(MidRailPart4, 0F, 0F, 0F);
		MidRailPartTop = new ModelPart(this, 113, 24);
		MidRailPartTop.addCuboid(0F, 0F, 0F, 2, 1, 14);
		MidRailPartTop.setPivot(-1.5F, -11F, -28F);
		MidRailPartTop.setTextureSize(256, 128);
		MidRailPartTop.mirror = true;
		setRotation(MidRailPartTop, 0F, 0F, 0F);
		MidRailPartFront = new ModelPart(this, 0, 71);
		MidRailPartFront.addCuboid(0F, 0F, 0F, 2, 3, 1);
		MidRailPartFront.setPivot(-1.5F, -11F, -28F);
		MidRailPartFront.setTextureSize(256, 128);
		MidRailPartFront.mirror = true;
		setRotation(MidRailPartFront, -0.3228859F, 0F, 0F);
		BarrelMidPartTop = new ModelPart(this, 0, 56);
		BarrelMidPartTop.addCuboid(0F, 0F, 0F, 2, 3, 2);
		BarrelMidPartTop.setPivot(-1.5F, -9F, -48F);
		BarrelMidPartTop.setTextureSize(256, 128);
		BarrelMidPartTop.mirror = true;
		setRotation(BarrelMidPartTop, -0.6707641F, 0F, 0F);
		BarrelMidPart01 = new ModelPart(this, 0, 55);
		BarrelMidPart01.addCuboid(0F, 0F, 0F, 3, 3, 3);
		BarrelMidPart01.setPivot(-0.5F, -7.7F, -50F);
		BarrelMidPart01.setTextureSize(256, 128);
		BarrelMidPart01.mirror = true;
		setRotation(BarrelMidPart01, 0F, 0F, 0.7853982F);
		BarrelMidPartBottom = new ModelPart(this, 0, 37);
		BarrelMidPartBottom.addCuboid(0F, 0F, 0F, 2, 1, 2);
		BarrelMidPartBottom.setPivot(-1.5F, -4.5F, -49.5F);
		BarrelMidPartBottom.setTextureSize(256, 128);
		BarrelMidPartBottom.mirror = true;
		setRotation(BarrelMidPartBottom, 0F, 0F, 0F);
		ScopeFront = new ModelPart(this, 129, 12);
		ScopeFront.addCuboid(0F, 0F, 0F, 5, 5, 5);
		ScopeFront.setPivot(-0.5F, -19F, -18F);
		ScopeFront.setTextureSize(256, 128);
		ScopeFront.mirror = true;
		setRotation(ScopeFront, 0F, 0F, 0.7853982F);
		BarrelFrontPart01 = new ModelPart(this, 0, 55);
		BarrelFrontPart01.addCuboid(0F, 0F, 0F, 3, 3, 3);
		BarrelFrontPart01.setPivot(-0.5F, -7.7F, -62F);
		BarrelFrontPart01.setTextureSize(256, 128);
		BarrelFrontPart01.mirror = true;
		setRotation(BarrelFrontPart01, 0F, 0F, 0.7853982F);
		MuzzleFront3 = new ModelPart(this, 5, 41);
		MuzzleFront3.addCuboid(0F, 0F, 0F, 3, 3, 1);
		MuzzleFront3.setPivot(-0.5F, -7.7F, -67F);
		MuzzleFront3.setTextureSize(256, 128);
		MuzzleFront3.mirror = true;
		setRotation(MuzzleFront3, 0F, 0F, 0.7853982F);
		MuzzleFront = new ModelPart(this, 0, 43);
		MuzzleFront.addCuboid(0F, 0F, 0F, 1, 1, 1);
		MuzzleFront.setPivot(-0.5F, -7.7F, -66F);
		MuzzleFront.setTextureSize(256, 128);
		MuzzleFront.mirror = true;
		setRotation(MuzzleFront, 0F, 0F, 0.7853982F);
		Muzzle05 = new ModelPart(this, 12, 46);
		Muzzle05.addCuboid(0F, 0F, 0F, 4, 4, 1);
		Muzzle05.setPivot(-0.5F, -8.5F, -64F);
		Muzzle05.setTextureSize(256, 128);
		Muzzle05.mirror = true;
		setRotation(Muzzle05, 0F, 0F, 0.7853982F);
		UpperTrigger = new ModelPart(this, 45, 34);
		UpperTrigger.addCuboid(0F, 0F, 0F, 4, 3, 17);
		UpperTrigger.setPivot(-2.5F, -2F, 0F);
		UpperTrigger.setTextureSize(256, 128);
		UpperTrigger.mirror = true;
		setRotation(UpperTrigger, 0F, 0F, 0F);
		StockBack02 = new ModelPart(this, 93, 0);
		StockBack02.addCuboid(0F, 0F, 0F, 5, 10, 1);
		StockBack02.setPivot(-3F, -7F, 32F);
		StockBack02.setTextureSize(256, 128);
		StockBack02.mirror = true;
		setRotation(StockBack02, 0F, 0F, 0F);
		StockFlip = new ModelPart(this, 58, 0);
		StockFlip.addCuboid(0F, 0F, 0F, 3, 2, 10);
		StockFlip.setPivot(-2F, 1F, 21F);
		StockFlip.setTextureSize(256, 128);
		StockFlip.mirror = true;
		setRotation(StockFlip, 0F, 0F, 0F);
		StockBack01 = new ModelPart(this, 117, 0);
		StockBack01.addCuboid(0F, 0F, 0F, 4, 10, 1);
		StockBack01.setPivot(-2.5F, -7F, 31F);
		StockBack01.setTextureSize(256, 128);
		StockBack01.mirror = true;
		setRotation(StockBack01, 0F, 0F, 0F);
		StockLower = new ModelPart(this, 92, 0);
		StockLower.addCuboid(0F, 0F, 0F, 4, 5, 14);
		StockLower.setPivot(-2.5F, -4F, 17F);
		StockLower.setTextureSize(256, 128);
		StockLower.mirror = true;
		setRotation(StockLower, 0F, 0F, 0F);
		TopRails = new ModelPart(this, 39, 68);
		TopRails.addCuboid(0F, 0F, 0F, 2, 2, 25);
		TopRails.setPivot(-1.5F, -11F, -14F);
		TopRails.setTextureSize(256, 128);
		TopRails.mirror = true;
		setRotation(TopRails, 0F, 0F, 0F);
		ScopeAdjust = new ModelPart(this, 40, 9);
		ScopeAdjust.addCuboid(0F, 0F, 0F, 2, 3, 3);
		ScopeAdjust.setPivot(-4.3F, -15.4F, -3.1F);
		ScopeAdjust.setTextureSize(256, 128);
		ScopeAdjust.mirror = true;
		setRotation(ScopeAdjust, 0.7853982F, 0F, 0F);
		ScopeMount2 = new ModelPart(this, 130, 0);
		ScopeMount2.addCuboid(0F, 0F, 0F, 3, 3, 1);
		ScopeMount2.setPivot(-2F, -15F, -6F);
		ScopeMount2.setTextureSize(256, 128);
		ScopeMount2.mirror = true;
		setRotation(ScopeMount2, 0F, 0F, 0F);
		BarrelBigPartFront = new ModelPart(this, 0, 76);
		BarrelBigPartFront.addCuboid(0F, 0F, 0F, 4, 4, 8);
		BarrelBigPartFront.setPivot(-0.5F, -8.5F, -29F);
		BarrelBigPartFront.setTextureSize(256, 128);
		BarrelBigPartFront.mirror = true;
		setRotation(BarrelBigPartFront, 0F, 0F, 0.7853982F);
		Scope01 = new ModelPart(this, 133, 23);
		Scope01.addCuboid(0F, 0F, 0F, 4, 4, 7);
		Scope01.setPivot(-0.5F, -18.25F, 6F);
		Scope01.setTextureSize(256, 128);
		Scope01.mirror = true;
		setRotation(Scope01, 0F, 0F, 0.7853982F);
		Scope02 = new ModelPart(this, 150, 0);
		Scope02.addCuboid(0F, 0F, 0F, 3, 3, 14);
		Scope02.setPivot(-0.5F, -17.5F, -8F);
		Scope02.setTextureSize(256, 128);
		Scope02.mirror = true;
		setRotation(Scope02, 0F, 0F, 0.7853982F);
		Scope03 = new ModelPart(this, 21, 0);
		Scope03.addCuboid(0F, 0F, 0F, 4, 4, 5);
		Scope03.setPivot(-0.5F, -18.25F, -13F);
		Scope03.setTextureSize(256, 128);
		Scope03.mirror = true;
		setRotation(Scope03, 0F, 0F, 0.7853982F);
		Receiver = new ModelPart(this, 56, 23);
		Receiver.addCuboid(0F, 0F, 0F, 4, 6, 32);
		Receiver.setPivot(-2.5F, -8F, -15F);
		Receiver.setTextureSize(256, 128);
		Receiver.mirror = true;
		setRotation(Receiver, 0F, 0F, 0F);
		ScopeMount = new ModelPart(this, 129, 0);
		ScopeMount.addCuboid(0F, 0F, 0F, 3, 1, 10);
		ScopeMount.setPivot(-2F, -12F, -6F);
		ScopeMount.setTextureSize(256, 128);
		ScopeMount.mirror = true;
		setRotation(ScopeMount, 0F, 0F, 0F);
		ScopeAdjust2 = new ModelPart(this, 51, 13);
		ScopeAdjust2.addCuboid(0F, 0F, 0F, 3, 2, 3);
		ScopeAdjust2.setPivot(-2.62F, -19.5F, -1F);
		ScopeAdjust2.setTextureSize(256, 128);
		ScopeAdjust2.mirror = true;
		setRotation(ScopeAdjust2, 0F, 0.7853982F, 0F);
		ScopeMiddle = new ModelPart(this, 40, 0);
		ScopeMiddle.addCuboid(0F, 0F, 0F, 4, 4, 4);
		ScopeMiddle.setPivot(-2.3F, -17.5F, -3F);
		ScopeMiddle.setTextureSize(256, 128);
		ScopeMiddle.mirror = true;
		setRotation(ScopeMiddle, 0F, 0F, 0F);
		Muzzle01 = new ModelPart(this, 0, 46);
		Muzzle01.addCuboid(0F, 0F, 0F, 4, 4, 1);
		Muzzle01.setPivot(-0.5F, -8.5F, -65F);
		Muzzle01.setTextureSize(256, 128);
		Muzzle01.mirror = true;
		setRotation(Muzzle01, 0F, 0F, 0.7853982F);
		Muzzle04 = new ModelPart(this, 0, 46);
		Muzzle04.addCuboid(0F, 0F, 0F, 4, 4, 1);
		Muzzle04.setPivot(-0.5F, -8.5F, -63F);
		Muzzle04.setTextureSize(256, 128);
		Muzzle04.mirror = true;
		setRotation(Muzzle04, 0F, 0F, 0.7853982F);
	}
 
	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
			float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
			int overlay) {
		Grip1.render(matrices, vertices, light, overlay);
		Trigger01.render(matrices, vertices, light, overlay);
		Trigger02.render(matrices, vertices, light, overlay);
		MuzzleFront2.render(matrices, vertices, light, overlay);
		Magazine01.render(matrices, vertices, light, overlay);
		Trigger03.render(matrices, vertices, light, overlay);
		MagHolder.render(matrices, vertices, light, overlay);
		CheekRest.render(matrices, vertices, light, overlay);
		LowerTrigger.render(matrices, vertices, light, overlay);
		Magazine02.render(matrices, vertices, light, overlay);
		Magazine03.render(matrices, vertices, light, overlay);
		MidRailPart1.render(matrices, vertices, light, overlay);
		UpperBarrel.render(matrices, vertices, light, overlay);
		BarrelBigPart.render(matrices, vertices, light, overlay);
		Barrel.render(matrices, vertices, light, overlay);
		ReceiverTop.render(matrices, vertices, light, overlay);
		BarrelBigPartTop.render(matrices, vertices, light, overlay);
		ScopeMount4.render(matrices, vertices, light, overlay);
		BarrelFrontPartBottom.render(matrices, vertices, light, overlay);
		MidRailPart2.render(matrices, vertices, light, overlay);
		MidRailPart3.render(matrices, vertices, light, overlay);
		MidRailPart4.render(matrices, vertices, light, overlay);
		MidRailPartTop.render(matrices, vertices, light, overlay);
		MidRailPartFront.render(matrices, vertices, light, overlay);
		BarrelMidPartTop.render(matrices, vertices, light, overlay);
		BarrelMidPart01.render(matrices, vertices, light, overlay);
		BarrelMidPartBottom.render(matrices, vertices, light, overlay);
		ScopeFront.render(matrices, vertices, light, overlay);
		BarrelFrontPart01.render(matrices, vertices, light, overlay);
		MuzzleFront3.render(matrices, vertices, light, overlay);
		MuzzleFront.render(matrices, vertices, light, overlay);
		Muzzle05.render(matrices, vertices, light, overlay);
		UpperTrigger.render(matrices, vertices, light, overlay);
		StockBack02.render(matrices, vertices, light, overlay);
		StockFlip.render(matrices, vertices, light, overlay);
		StockBack01.render(matrices, vertices, light, overlay);
		StockLower.render(matrices, vertices, light, overlay);
		TopRails.render(matrices, vertices, light, overlay);
		ScopeAdjust.render(matrices, vertices, light, overlay);
		ScopeMount2.render(matrices, vertices, light, overlay);
		BarrelBigPartFront.render(matrices, vertices, light, overlay);
		Scope01.render(matrices, vertices, light, overlay);
		Scope02.render(matrices, vertices, light, overlay);
		Scope03.render(matrices, vertices, light, overlay);
		Receiver.render(matrices, vertices, light, overlay);
		ScopeMount.render(matrices, vertices, light, overlay);
		ScopeAdjust2.render(matrices, vertices, light, overlay);
		ScopeMiddle.render(matrices, vertices, light, overlay);
		Muzzle01.render(matrices, vertices, light, overlay);
		Muzzle04.render(matrices, vertices, light, overlay);
	}

}