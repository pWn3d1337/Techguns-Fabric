package techguns.client.models.projectiles;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;
import techguns.client.models.ModelPart;

public class ModelRocket extends ModelMultipart
{
  //fields
    ModelPart R9;
    ModelPart R11;
    ModelPart R8;
    ModelPart R3;
    ModelPart R10;
    ModelPart R2;
    ModelPart R4;
    ModelPart R5;
    ModelPart R6;
    ModelPart R7;
    ModelPart R1;
  
  public ModelRocket()
  {
	  super(RenderLayer::getEntitySolid);
      textureWidth = 64;
      textureHeight = 32;
    
      R9 = new ModelPart(this, 0, 0);
      R9.addCuboid(0F, 0F, 0F, 6, 4, 4);
      R9.setPivot(-9.9F, -1.5F, -1.5F);
      R9.setTextureSize(128, 64);
      R9.mirror = true;
      setRotation(R9, 0F, 0F, 0F);
      R11 = new ModelPart(this, 30, 0);
      R11.addCuboid(0F, 0F, 0F, 2, 1, 6);
      R11.setPivot(-9.8F, 0F, -2.5F);
      R11.setTextureSize(128, 64);
      R11.mirror = true;
      setRotation(R11, 0F, 0F, 0F);
      R8 = new ModelPart(this, 0, 16);
      R8.addCuboid(0F, 0F, 0F, 10, 3, 3);
      R8.setPivot(-10F, -1F, -1F);
      R8.setTextureSize(128, 64);
      R8.mirror = true;
      setRotation(R8, 0F, 0F, 0F);
      R3 = new ModelPart(this, 20, 3);
      R3.addCuboid(0F, 0F, 0F, 1, 2, 2);
      R3.setPivot(10F, -0.5F, -0.5F);
      R3.setTextureSize(128, 64);
      R3.mirror = true;
      setRotation(R3, 0F, 0F, 0F);
      R10 = new ModelPart(this, 30, 7);
      R10.addCuboid(0F, 0F, 0F, 2, 6, 1);
      R10.setPivot(-9.8F, -2.5F, 0F);
      R10.setTextureSize(128, 64);
      R10.mirror = true;
      setRotation(R10, 0F, 0F, 0F);
      R2 = new ModelPart(this, 0, 16);
      R2.addCuboid(0F, 0F, 0F, 10, 3, 3);
      R2.setPivot(0F, -1F, -1F);
      R2.setTextureSize(128, 64);
      R2.mirror = true;
      setRotation(R2, 0F, 0F, 0F);
      R4 = new ModelPart(this, 12, 9);
      R4.addCuboid(0F, 0F, 0F, 4, 2, 5);
      R4.setPivot(4F, -0.5F, -2F);
      R4.setTextureSize(128, 64);
      R4.mirror = true;
      setRotation(R4, 0F, 0F, 0F);
      R5 = new ModelPart(this, 0, 8);
      R5.addCuboid(0F, 0F, 0F, 4, 5, 2);
      R5.setPivot(4F, -2F, -0.5F);
      R5.setTextureSize(128, 64);
      R5.mirror = true;
      setRotation(R5, 0F, 0F, 0F);
      R6 = new ModelPart(this, 30, 7);
      R6.addCuboid(0F, 0F, 0F, 2, 6, 1);
      R6.setPivot(4.1F, -2.5F, 0F);
      R6.setTextureSize(128, 64);
      R6.mirror = true;
      setRotation(R6, 0F, 0F, 0F);
      R7 = new ModelPart(this, 30, 0);
      R7.addCuboid(0F, 0F, 0F, 2, 1, 6);
      R7.setPivot(4.1F, 0F, -2.5F);
      R7.setTextureSize(128, 64);
      R7.mirror = true;
      setRotation(R7, 0F, 0F, 0F);
      R1 = new ModelPart(this, 0, 0);
      R1.addCuboid(0F, 0F, 0F, 6, 4, 4);
      R1.setPivot(3F, -1.5F, -1.5F);
      R1.setTextureSize(128, 64);
      R1.mirror = true;
      setRotation(R1, 0F, 0F, 0F);
  }
  

	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft, float reloadProgress,
			Mode transformType, int part, float fireProgress, float chargeProgress, int light, int overlay) {
		 	R9.render(matrices, vertices, light, overlay);
		    R11.render(matrices, vertices, light, overlay);
		    R8.render(matrices, vertices, light, overlay);
		    R3.render(matrices, vertices, light, overlay);
		    R10.render(matrices, vertices, light, overlay);
		    R2.render(matrices, vertices, light, overlay);
		    R4.render(matrices, vertices, light, overlay);
		    R5.render(matrices, vertices, light, overlay);
		    R6.render(matrices, vertices, light, overlay);
		    R7.render(matrices, vertices, light, overlay);
		    R1.render(matrices, vertices, light, overlay);
	}

}