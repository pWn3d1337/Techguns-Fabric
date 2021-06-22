package techguns.client.models.guns;

import techguns.client.models.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelPistol extends ModelMultipart {

	//fields
    ModelPart Top;
    ModelPart Grip3;
    ModelPart Barrel;
    ModelPart IS2;
    ModelPart Grip2;
    ModelPart IS1;
    ModelPart Trigger;
    ModelPart Grip1;
    ModelPart Slide;
  
  public ModelPistol()
  {
        super(RenderLayer::getEntitySolid);
    textureWidth = 64;
    textureHeight = 32;
    
      Top = new ModelPart(this, 0, 17);
      Top.addCuboid(0F, 0F, 0F, 3, 1, 14);
      Top.setPivot(-2F, 2F, -2F);
      Top.setTextureSize(64, 32);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 0F);
      Grip3 = new ModelPart(this, 3, 0);
      Grip3.addCuboid(0F, 0F, 0F, 2, 7, 3);
      Grip3.setPivot(-1.5F, 3F, 7F);
      Grip3.setTextureSize(64, 32);
      Grip3.mirror = true;
      setRotation(Grip3, 0.2181662F, 0F, 0F);
      Barrel = new ModelPart(this, 0, 21);
      Barrel.addCuboid(0F, 0F, 0F, 1, 1, 6);
      Barrel.setPivot(-0.5F, 0.25F, -3F);
      Barrel.setTextureSize(64, 32);
      Barrel.mirror = true;
      setRotation(Barrel, 0F, 0F, 0.7853982F);
      IS2 = new ModelPart(this, 0, 21);
      IS2.addCuboid(0F, 0F, 0F, 1, 1, 1);
      IS2.setPivot(-1F, -0.5333334F, -0.5F);
      IS2.setTextureSize(64, 32);
      IS2.mirror = true;
      setRotation(IS2, 0F, 0F, 0F);
      Grip2 = new ModelPart(this, 20, 5);
      Grip2.addCuboid(0F, 0F, 0F, 1, 1, 3);
      Grip2.setPivot(-1F, 5F, 5F);
      Grip2.setTextureSize(64, 32);
      Grip2.mirror = true;
      setRotation(Grip2, 0F, 0F, 0F);
      IS1 = new ModelPart(this, 0, 24);
      IS1.addCuboid(0F, 0F, 0F, 1, 1, 1);
      IS1.setPivot(-1F, -0.5F, 9.5F);
      IS1.setTextureSize(64, 32);
      IS1.mirror = true;
      setRotation(IS1, 0F, 0F, 0F);
      Trigger = new ModelPart(this, 25, 0);
      Trigger.addCuboid(0F, 0F, 0F, 1, 2, 1);
      Trigger.setPivot(-1F, 2.5F, 6.5F);
      Trigger.setTextureSize(64, 32);
      Trigger.mirror = true;
      setRotation(Trigger, -0.4089647F, 0F, 0F);
      Grip1 = new ModelPart(this, 20, 0);
      Grip1.addCuboid(0F, 0F, 0F, 1, 3, 1);
      Grip1.setPivot(-1F, 3F, 4F);
      Grip1.setTextureSize(64, 32);
      Grip1.mirror = true;
      setRotation(Grip1, 0F, 0F, 0F);
      Slide = new ModelPart(this, 0, 0);
      Slide.addCuboid(0F, 0F, 0F, 3, 2, 14);
      Slide.setPivot(-2F, 0F, -2F);
      Slide.setTextureSize(64, 32);
      Slide.mirror = true;
      setRotation(Slide, 0F, 0F, 0F);
  }
  
  @Override
  public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
		float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
	int overlay) {
	    if(part==0){
		    Top.render(matrices, vertices, light, overlay);
		    Grip3.render(matrices, vertices, light, overlay);
		    Barrel.render(matrices, vertices, light, overlay);
		    Grip2.render(matrices, vertices, light, overlay);
		    Trigger.render(matrices, vertices, light, overlay);
		    Grip1.render(matrices, vertices, light, overlay);
	    } else {
	    	matrices.push();
	    	float progress=fireProgress*2.0f;
	    	if (progress<0.5f){
	    		float prog = progress*2.0f;
	    		matrices.translate(0 * scale, 0 * scale, 0.15f*prog * scale);
	    	}
		    IS1.render(matrices, vertices, light, overlay);
		    IS2.render(matrices, vertices, light, overlay);
		    Slide.render(matrices, vertices, light, overlay);
		    matrices.pop();
	    }
  }

}
