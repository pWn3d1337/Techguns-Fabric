package techguns.client.models.guns;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelLMG extends ModelMultipart {

	 //fields
   ModelPart Grip1;
   ModelPart Trigger01;
   ModelPart Trigger02;
   ModelPart Grip02;
   ModelPart IronSight01;
   ModelPart Bullet01;
   ModelPart IronSight02;
   ModelPart Barrelpart;
   ModelPart Mag03;
   ModelPart Grip01;
   ModelPart BarrelRails04;
   ModelPart Stock01;
   ModelPart Receiver07;
   ModelPart Stock02;
   ModelPart Stock04;
   ModelPart Stock05;
   ModelPart Stock03;
   ModelPart Trigger03;
   ModelPart Receiver06;
   ModelPart Mag01;
   ModelPart Mag02;
   ModelPart Barrel03;
   ModelPart Bullet06;
   ModelPart Bullet05;
   ModelPart Bullet04;
   ModelPart Bullet03;
   ModelPart Bullet02;
   ModelPart Receiver01;
   ModelPart Receiver02;
   ModelPart Barrel;
   ModelPart Receiver03;
   ModelPart Receiver04;
   ModelPart RedDot;
   ModelPart TopRails;
   ModelPart Holo01;
   ModelPart Holo02;
   ModelPart Holo06;
   ModelPart Holo03;
   ModelPart Holo04;
   ModelPart Holo05;
   ModelPart Receiver08;
   ModelPart Stock06;
   ModelPart Stock07;
   ModelPart IronSight03;
   ModelPart Barrel02;
   ModelPart Receiver05;
   ModelPart Bullet00;
   ModelPart Bullet07;
   
	public ModelLMG() {
		super(RenderLayer::getEntitySolid);
		textureWidth = 128;
		textureHeight = 128;

		Grip1 = new ModelPart(this, 68, 0);
		Grip1.addCuboid(0F, 0F, 0F, 3, 9, 4);
		Grip1.setPivot(-2F, 2F, 6F);
		Grip1.setTextureSize(128, 128);
		Grip1.mirror = true;
		setRotation(Grip1, 0.4461433F, 0F, 0F);
		Trigger01 = new ModelPart(this, 83, 21);
		Trigger01.addCuboid(0F, 0F, 0F, 2, 3, 1);
		Trigger01.setPivot(-1.5F, 2F, 2F);
		Trigger01.setTextureSize(128, 128);
		Trigger01.mirror = true;
		setRotation(Trigger01, 0F, 0F, 0F);
		Trigger02 = new ModelPart(this, 83, 17);
		Trigger02.addCuboid(0F, 0F, 0F, 1, 2, 1);
		Trigger02.setPivot(-1F, 1.5F, 5F);
		Trigger02.setTextureSize(128, 128);
		Trigger02.mirror = true;
		setRotation(Trigger02, -0.2617994F, 0F, 0F);
		Grip02 = new ModelPart(this, 56, 6);
		Grip02.addCuboid(0F, 0F, 0F, 2, 8, 2);
		Grip02.setPivot(-2F, 3.5F, -14.5F);
		Grip02.setTextureSize(128, 128);
		Grip02.mirror = true;
		setRotation(Grip02, 0F, 0.7853982F, 0F);
		IronSight01 = new ModelPart(this, 5, 39);
		IronSight01.addCuboid(0F, 0F, 0F, 1, 4, 1);
		IronSight01.setPivot(-1F, -8F, -31F);
		IronSight01.setTextureSize(128, 128);
		IronSight01.mirror = true;
		setRotation(IronSight01, 0.4363323F, 0F, 0F);
		Bullet01 = new ModelPart(this, 0, 26);
		Bullet01.addCuboid(0F, 0F, 0F, 1, 1, 6);
		Bullet01.setPivot(2.5F, -4F, -5.5F);
		Bullet01.setTextureSize(128, 128);
		Bullet01.mirror = true;
		setRotation(Bullet01, 0F, 0F, 0.7853982F);
		Bullet00 = new ModelPart(this, 0, 26);
		Bullet00.addCuboid(0F, 0F, 0F, 1, 1, 6);
		Bullet00.setPivot(1.5F, -4F, -5.5F);
		Bullet00.setTextureSize(128, 128);
		Bullet00.mirror = true;
		setRotation(Bullet00, 0F, 0F, 0.7853982F);
		IronSight02 = new ModelPart(this, 0, 34);
		IronSight02.addCuboid(0F, 0F, 0F, 1, 2, 2);
		IronSight02.setPivot(-1F, -9F, -32F);
		IronSight02.setTextureSize(128, 128);
		IronSight02.mirror = true;
		setRotation(IronSight02, 0F, 0F, 0F);
		Barrelpart = new ModelPart(this, 0, 39);
		Barrelpart.addCuboid(0F, 0F, 0F, 1, 2, 4);
		Barrelpart.setPivot(-1F, -4F, -32F);
		Barrelpart.setTextureSize(128, 128);
		Barrelpart.mirror = true;
		setRotation(Barrelpart, 0F, 0F, 0F);
		Mag03 = new ModelPart(this, 39, 66);
		Mag03.addCuboid(0F, 0F, 0F, 3, 6, 7);
		Mag03.setPivot(-3.5F, 1F, -6F);
		Mag03.setTextureSize(128, 128);
		Mag03.mirror = true;
		setRotation(Mag03, 0F, 0F, 0.7888888F);
		Grip01 = new ModelPart(this, 53, 0);
		Grip01.addCuboid(0F, 0F, 0F, 3, 2, 4);
		Grip01.setPivot(-2F, 1.5F, -16.5F);
		Grip01.setTextureSize(128, 128);
		Grip01.mirror = true;
		setRotation(Grip01, 0F, 0F, 0F);
		BarrelRails04 = new ModelPart(this, 42, 33);
		BarrelRails04.addCuboid(0F, 0F, 0F, 2, 1, 12);
		BarrelRails04.setPivot(-1.5F, 1F, -20F);
		BarrelRails04.setTextureSize(128, 128);
		BarrelRails04.mirror = true;
		setRotation(BarrelRails04, 0F, 0F, 0F);
		Stock01 = new ModelPart(this, 106, 25);
		Stock01.addCuboid(0F, 0F, 0F, 3, 3, 8);
		Stock01.setPivot(-2F, -2F, 11F);
		Stock01.setTextureSize(128, 128);
		Stock01.mirror = true;
		setRotation(Stock01, 0.1783541F, 0F, 0F);
		Receiver07 = new ModelPart(this, 92, 21);
		Receiver07.addCuboid(0F, 0F, 0F, 3, 2, 2);
		Receiver07.setPivot(-2F, -1F, 10F);
		Receiver07.setTextureSize(128, 128);
		Receiver07.mirror = true;
		setRotation(Receiver07, 0F, 0F, 0F);
		Stock02 = new ModelPart(this, 110, 48);
		Stock02.addCuboid(0F, 0F, 0F, 3, 1, 6);
		Stock02.setPivot(-2F, -6F, 23F);
		Stock02.setTextureSize(128, 128);
		Stock02.mirror = true;
		setRotation(Stock02, 0F, 0F, 0F);
		Stock04 = new ModelPart(this, 79, 64);
		Stock04.addCuboid(0F, 0F, 0F, 3, 7, 8);
		Stock04.setPivot(-2F, -4F, 21F);
		Stock04.setTextureSize(128, 128);
		Stock04.mirror = true;
		setRotation(Stock04, 0F, 0F, 0F);
		Stock05 = new ModelPart(this, 119, 68);
		Stock05.addCuboid(0F, 0F, 0F, 3, 1, 1);
		Stock05.setPivot(-2F, 2F, 20F);
		Stock05.setTextureSize(128, 128);
		Stock05.mirror = true;
		setRotation(Stock05, 0F, 0F, 0F);
		Stock03 = new ModelPart(this, 108, 39);
		Stock03.addCuboid(0F, 0F, 0F, 3, 1, 7);
		Stock03.setPivot(-2F, -5F, 22F);
		Stock03.setTextureSize(128, 128);
		Stock03.mirror = true;
		setRotation(Stock03, 0F, 0F, 0F);
		Trigger03 = new ModelPart(this, 83, 26);
		Trigger03.addCuboid(0F, 0F, 0F, 2, 1, 5);
		Trigger03.setPivot(-1.5F, 4F, 3F);
		Trigger03.setTextureSize(128, 128);
		Trigger03.mirror = true;
		setRotation(Trigger03, 0F, 0F, 0F);
		Receiver06 = new ModelPart(this, 106, 13);
		Receiver06.addCuboid(0F, 0F, 0F, 3, 3, 8);
		Receiver06.setPivot(-2F, -1F, 2F);
		Receiver06.setTextureSize(128, 128);
		Receiver06.mirror = true;
		setRotation(Receiver06, 0F, 0F, 0F);
		Mag01 = new ModelPart(this, 0, 71);
		Mag01.addCuboid(0F, 0F, 0F, 13, 6, 7);
		Mag01.setPivot(-7.5F, 5F, -6F);
		Mag01.setTextureSize(128, 128);
		Mag01.mirror = true;
		setRotation(Mag01, 0F, 0F, 0F);
		Mag02 = new ModelPart(this, 0, 59);
		Mag02.addCuboid(0F, 0F, 0F, 9, 4, 7);
		Mag02.setPivot(-3.5F, 1F, -6F);
		Mag02.setTextureSize(128, 128);
		Mag02.mirror = true;
		setRotation(Mag02, 0F, 0F, 0F);
		Barrel03 = new ModelPart(this, 22, 25);
		Barrel03.addCuboid(0F, 0F, 0F, 3, 3, 2);
		Barrel03.setPivot(-0.5F, -3.2F, -28F);
		Barrel03.setTextureSize(128, 128);
		Barrel03.mirror = true;
		setRotation(Barrel03, 0F, 0F, 0.7853982F);
		Bullet07 = new ModelPart(this, 0, 26);
		Bullet07.addCuboid(0F, 0F, 0F, 1, 1, 6);
		Bullet07.setPivot(4.5F, 1.5F, -5.5F);
		Bullet07.setTextureSize(128, 128);
		Bullet07.mirror = true;
		setRotation(Bullet07, 0F, 0F, 0.7853982F);
		Bullet06 = new ModelPart(this, 0, 26);
		Bullet06.addCuboid(0F, 0F, 0F, 1, 1, 6);
		Bullet06.setPivot(4.5F, 0.5F, -5.5F);
		Bullet06.setTextureSize(128, 128);
		Bullet06.mirror = true;
		setRotation(Bullet06, 0F, 0F, 0.7853982F);
		Bullet05 = new ModelPart(this, 0, 26);
		Bullet05.addCuboid(0F, 0F, 0F, 1, 1, 6);
		Bullet05.setPivot(4F, -0.5F, -5.5F);
		Bullet05.setTextureSize(128, 128);
		Bullet05.mirror = true;
		setRotation(Bullet05, 0F, 0F, 0.7853982F);
		Bullet04 = new ModelPart(this, 0, 26);
		Bullet04.addCuboid(0F, 0F, 0F, 1, 1, 6);
		Bullet04.setPivot(4F, -1.5F, -5.5F);
		Bullet04.setTextureSize(128, 128);
		Bullet04.mirror = true;
		setRotation(Bullet04, 0F, 0F, 0.7853982F);
		Bullet03 = new ModelPart(this, 0, 26);
		Bullet03.addCuboid(0F, 0F, 0F, 1, 1, 6);
		Bullet03.setPivot(4F, -2.5F, -5.5F);
		Bullet03.setTextureSize(128, 128);
		Bullet03.mirror = true;
		setRotation(Bullet03, 0F, 0F, 0.7853982F);
		Bullet02 = new ModelPart(this, 0, 26);
		Bullet02.addCuboid(0F, 0F, 0F, 1, 1, 6);
		Bullet02.setPivot(3.5F, -3.5F, -5.5F);
		Bullet02.setTextureSize(128, 128);
		Bullet02.mirror = true;
		setRotation(Bullet02, 0F, 0F, 0.7853982F);
		Receiver01 = new ModelPart(this, 54, 13);
		Receiver01.addCuboid(0F, 0F, 0F, 4, 1, 20);
		Receiver01.setPivot(-2.5F, -7F, -8F);
		Receiver01.setTextureSize(128, 128);
		Receiver01.mirror = true;
		setRotation(Receiver01, 0F, 0F, 0F);
		Receiver02 = new ModelPart(this, 54, 34);
		Receiver02.addCuboid(0F, 0F, 0F, 5, 6, 18);
		Receiver02.setPivot(-3F, -5F, -26F);
		Receiver02.setTextureSize(128, 128);
		Receiver02.mirror = true;
		setRotation(Receiver02, 0F, 0F, 0F);
		Barrel = new ModelPart(this, 0, 26);
		Barrel.addCuboid(0F, 0F, 0F, 2, 2, 18);
		Barrel.setPivot(-0.5F, -5.5F, -44F);
		Barrel.setTextureSize(128, 128);
		Barrel.mirror = true;
		setRotation(Barrel, 0F, 0F, 0.7853982F);
		Receiver03 = new ModelPart(this, 66, 81);
		Receiver03.addCuboid(0F, 0F, 0F, 5, 5, 21);
		Receiver03.setPivot(-3F, -6F, -8F);
		Receiver03.setTextureSize(128, 128);
		Receiver03.mirror = true;
		setRotation(Receiver03, 0F, 0F, 0F);
		Receiver04 = new ModelPart(this, 31, 47);
		Receiver04.addCuboid(0F, 0F, 0F, 3, 1, 16);
		Receiver04.setPivot(-2F, -7F, -24F);
		Receiver04.setTextureSize(128, 128);
		Receiver04.mirror = true;
		setRotation(Receiver04, 0F, 0F, 0F);
		RedDot = new ModelPart(this, 105, 10);
		RedDot.addCuboid(0F, 0F, 0F, 1, 1, 0);
		RedDot.setPivot(-1F, -10.5F, 5.5F);
		RedDot.setTextureSize(128, 128);
		RedDot.mirror = true;
		setRotation(RedDot, 0F, 0F, 0F);
		TopRails = new ModelPart(this, 0, 0);
		TopRails.addCuboid(0F, 0F, 0F, 2, 1, 17);
		TopRails.setPivot(-1.5F, -8F, -5.5F);
		TopRails.setTextureSize(128, 128);
		TopRails.mirror = true;
		setRotation(TopRails, 0F, 0F, 0F);
		Holo01 = new ModelPart(this, 2, 48);
		Holo01.addCuboid(0F, 0F, 0F, 3, 2, 8);
		Holo01.setPivot(-2F, -9F, -0.5F);
		Holo01.setTextureSize(128, 128);
		Holo01.mirror = true;
		setRotation(Holo01, 0F, 0F, 0F);
		Holo02 = new ModelPart(this, 116, 0);
		Holo02.addCuboid(0F, 0F, 0F, 1, 2, 5);
		Holo02.setPivot(-2.5F, -9F, 1.5F);
		Holo02.setTextureSize(128, 128);
		Holo02.mirror = true;
		setRotation(Holo02, 0F, 0F, 0F);
		Holo06 = new ModelPart(this, 116, 0);
		Holo06.addCuboid(0F, 0F, 0F, 1, 2, 5);
		Holo06.setPivot(0.5F, -9F, 1.5F);
		Holo06.setTextureSize(128, 128);
		Holo06.mirror = true;
		setRotation(Holo06, 0F, 0F, 0F);
		Holo03 = new ModelPart(this, 105, 0);
		Holo03.addCuboid(0F, 0F, 0F, 1, 3, 4);
		Holo03.setPivot(0.5F, -12F, 2.5F);
		Holo03.setTextureSize(128, 128);
		Holo03.mirror = true;
		setRotation(Holo03, 0F, 0F, 0F);
		Holo04 = new ModelPart(this, 105, 0);
		Holo04.addCuboid(0F, 0F, 0F, 1, 3, 4);
		Holo04.setPivot(-2.5F, -12F, 2.5F);
		Holo04.setTextureSize(128, 128);
		Holo04.mirror = true;
		setRotation(Holo04, 0F, 0F, 0F);
		Holo05 = new ModelPart(this, 104, 8);
		Holo05.addCuboid(0F, 0F, 0F, 2, 1, 4);
		Holo05.setPivot(-1.5F, -12F, 2.5F);
		Holo05.setTextureSize(128, 128);
		Holo05.mirror = true;
		setRotation(Holo05, 0F, 0F, 0F);
		Receiver08 = new ModelPart(this, 98, 81);
		Receiver08.addCuboid(0F, 0F, 0F, 5, 3, 10);
		Receiver08.setPivot(-3F, -1F, -8F);
		Receiver08.setTextureSize(128, 128);
		Receiver08.mirror = true;
		setRotation(Receiver08, 0F, 0F, 0F);
		Stock06 = new ModelPart(this, 102, 65);
		Stock06.addCuboid(-3F, 0F, 0F, 3, 5, 10);
		Stock06.setPivot(2F, -6F, 13F);
		Stock06.setTextureSize(128, 128);
		Stock06.mirror = true;
		setRotation(Stock06, -0.2050762F, -0.1047198F, 0F);
		Stock07 = new ModelPart(this, 102, 65);
		Stock07.addCuboid(0F, 0F, 0F, 3, 5, 10);
		Stock07.setPivot(-3F, -6F, 13F);
		Stock07.setTextureSize(128, 128);
		Stock07.mirror = true;
		setRotation(Stock07, -0.2050762F, 0.1047198F, 0F);
		IronSight03 = new ModelPart(this, 0, 39);
		IronSight03.addCuboid(0F, 0F, 0F, 1, 3, 1);
		IronSight03.setPivot(-1F, -7F, -32F);
		IronSight03.setTextureSize(128, 128);
		IronSight03.mirror = true;
		setRotation(IronSight03, 0F, 0F, 0F);
		Barrel02 = new ModelPart(this, 22, 31);
		Barrel02.addCuboid(0F, 0F, 0F, 2, 2, 8);
		Barrel02.setPivot(-0.5F, -2.5F, -36F);
		Barrel02.setTextureSize(128, 128);
		Barrel02.mirror = true;
		setRotation(Barrel02, 0F, 0F, 0.7853982F);
		Receiver05 = new ModelPart(this, 28, 12);
		Receiver05.addCuboid(0F, 0F, 0F, 4, 1, 18);
		Receiver05.setPivot(-2.5F, -6F, -26F);
		Receiver05.setTextureSize(128, 128);
		Receiver05.mirror = true;
		setRotation(Receiver05, 0F, 0F, 0F);
	}
 
	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
			float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
			int overlay) {
	
   Grip1.render(matrices, vertices, light, overlay);
   Trigger01.render(matrices, vertices, light, overlay);
   Trigger02.render(matrices, vertices, light, overlay);
   Grip02.render(matrices, vertices, light, overlay);
   IronSight01.render(matrices, vertices, light, overlay);
   IronSight02.render(matrices, vertices, light, overlay);
   Barrelpart.render(matrices, vertices, light, overlay);
   Mag03.render(matrices, vertices, light, overlay);
   Grip01.render(matrices, vertices, light, overlay);
   BarrelRails04.render(matrices, vertices, light, overlay);
   Stock01.render(matrices, vertices, light, overlay);
   Receiver07.render(matrices, vertices, light, overlay);
   Stock02.render(matrices, vertices, light, overlay);
   Stock04.render(matrices, vertices, light, overlay);
   Stock05.render(matrices, vertices, light, overlay);
   Stock03.render(matrices, vertices, light, overlay);
   Trigger03.render(matrices, vertices, light, overlay);
   Receiver06.render(matrices, vertices, light, overlay);
   Mag01.render(matrices, vertices, light, overlay);
   Mag02.render(matrices, vertices, light, overlay);
   Barrel03.render(matrices, vertices, light, overlay);
   Receiver01.render(matrices, vertices, light, overlay);
   Receiver02.render(matrices, vertices, light, overlay);
   Barrel.render(matrices, vertices, light, overlay);
   Receiver03.render(matrices, vertices, light, overlay);
   Receiver04.render(matrices, vertices, light, overlay);
   RedDot.render(matrices, vertices, light, overlay);
   TopRails.render(matrices, vertices, light, overlay);
   Holo01.render(matrices, vertices, light, overlay);
   Holo02.render(matrices, vertices, light, overlay);
   Holo06.render(matrices, vertices, light, overlay);
   Holo03.render(matrices, vertices, light, overlay);
   Holo04.render(matrices, vertices, light, overlay);
   Holo05.render(matrices, vertices, light, overlay);
   Receiver08.render(matrices, vertices, light, overlay);
   Stock06.render(matrices, vertices, light, overlay);
   Stock07.render(matrices, vertices, light, overlay);
   IronSight03.render(matrices, vertices, light, overlay);
   Barrel02.render(matrices, vertices, light, overlay);
   Receiver05.render(matrices, vertices, light, overlay);
       
   if (reloadProgress==0f || reloadProgress > 0.5f) {
	   if (ammoLeft > 7 || ammoLeft==7 && fireProgress>0) {
	    	animateBullet(matrices, Bullet07, transformType, Bullet06, fireProgress, vertices, light, overlay);
	    	//Bullet06.render(matrices, vertices, light, overlay);
	    }
	    if (ammoLeft > 6 || ammoLeft==6 && fireProgress>0) {
	    	animateBullet(matrices, Bullet06, transformType, Bullet05, fireProgress, vertices, light, overlay);
	    	//Bullet06.render(matrices, vertices, light, overlay);
	    }
	    if (ammoLeft > 5 || ammoLeft==5 && fireProgress>0) {
	    	//Bullet05.render(matrices, vertices, light, overlay);
	    	animateBullet(matrices, Bullet05, transformType, Bullet04, fireProgress, vertices, light, overlay);
	    }
	    if (ammoLeft > 4 || ammoLeft==4 && fireProgress>0) {
	    	animateBullet(matrices, Bullet04, transformType, Bullet03, fireProgress, vertices, light, overlay);
	    	//Bullet04.render(matrices, vertices, light, overlay);
	    }
	    if (ammoLeft > 3 || ammoLeft==3 && fireProgress>0) {
	    	animateBullet(matrices, Bullet03, transformType, Bullet02, fireProgress, vertices, light, overlay);
	    	//Bullet03.render(matrices, vertices, light, overlay);
	    }
	    if (ammoLeft > 2 || ammoLeft==2 && fireProgress>0) {
	    	animateBullet(matrices, Bullet02, transformType, Bullet01, fireProgress, vertices, light, overlay);
	    	//Bullet02.render(matrices, vertices, light, overlay);
	    }
	    if (ammoLeft > 1 || ammoLeft==1 && fireProgress>0) {
	    	animateBullet(matrices, Bullet01, transformType, Bullet00, fireProgress, vertices, light, overlay);
	    	//Bullet01.render(matrices, vertices, light, overlay);
	    }
   }
 }
	
	protected void animateBullet(MatrixStack matrices, ModelPart bullet, Mode transform, ModelPart targetPos, float recoilProgress, VertexConsumer vertices, int light,
			int overlay) {
		if ( transform == Mode.FIRST_PERSON_RIGHT_HAND || transform == Mode.FIRST_PERSON_LEFT_HAND) {
			float x,y,z;
			recoilProgress = recoilProgress*4F;
			if (recoilProgress>1.0f) {
				recoilProgress=1.0f;
			}
			
			x = (targetPos.pitch-bullet.pitch)*recoilProgress;
			y = (targetPos.yaw-bullet.yaw)*recoilProgress;
			z = (targetPos.roll-bullet.roll)*recoilProgress;
			
			matrices.push();
			matrices.translate(x*scale * scale, y*scale * scale, z*scale * scale);
			bullet.render(matrices, vertices, light, overlay);
			matrices.pop();
		} else {
			bullet.render(matrices, vertices, light, overlay);
		}
	}

	
}