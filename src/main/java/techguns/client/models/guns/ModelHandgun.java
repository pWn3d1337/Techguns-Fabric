package techguns.client.models.guns;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import techguns.client.models.ModelPart;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelHandgun extends ModelMultipart
{
  //fields
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
    //ModelPart Shape13;
    ModelPart Shape14;
    ModelPart Shape15;
  
  public ModelHandgun()
  {
        super(RenderLayer::getEntitySolid);
    textureWidth = 64;
    textureHeight = 32;
    
      Shape1 = new ModelPart(this, 4, 0);
      Shape1.addCuboid(0F, 0F, 0F, 22, 3, 1);
      Shape1.setPivot(0F, -1F, 2F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelPart(this, 4, 0);
      Shape2.addCuboid(0F, 0F, 0F, 22, 3, 1);
      Shape2.setPivot(0F, -1F, -2F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelPart(this, 0, 1);
      Shape3.addCuboid(0F, 0F, 0F, 22, 5, 3);
      Shape3.setPivot(0F, -2F, -1F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelPart(this, 0, 0);
      Shape4.addCuboid(0F, 0F, 0F, 1, 1, 1);
      Shape4.setPivot(21F, -3F, 0F);
      Shape4.setTextureSize(64, 32);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelPart(this, 32, 9);
      Shape5.addCuboid(0F, 0F, 0F, 2, 1, 3);
      Shape5.setPivot(-8F, -3F, -1F);
      Shape5.setTextureSize(64, 32);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape6 = new ModelPart(this, 0, 9);
      Shape6.addCuboid(0F, 0F, 0F, 2, 5, 5);
      Shape6.setPivot(-8F, -2F, -2F);
      Shape6.setTextureSize(64, 32);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Shape7 = new ModelPart(this, 14, 9);
      Shape7.addCuboid(0F, 0F, 0F, 4, 4, 5);
      Shape7.setPivot(-6F, -1F, -2F);
      Shape7.setTextureSize(64, 32);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0F);
      Shape8 = new ModelPart(this, 32, 9);
      Shape8.addCuboid(0F, 0F, 0F, 2, 1, 3);
      Shape8.setPivot(-2F, -3F, -1F);
      Shape8.setTextureSize(64, 32);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, 0F);
      Shape9 = new ModelPart(this, 0, 9);
      Shape9.addCuboid(0F, 0F, 0F, 2, 5, 5);
      Shape9.setPivot(-2F, -2F, -2F);
      Shape9.setTextureSize(64, 32);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, 0F);
      Shape10 = new ModelPart(this, 44, 14);
      Shape10.addCuboid(0F, 0F, 0F, 7, 4, 3);
      Shape10.setPivot(-15F, -1F, -1F);
      Shape10.setTextureSize(64, 32);
      Shape10.mirror = true;
      setRotation(Shape10, 0F, 0F, 0F);
      Shape11 = new ModelPart(this, 48, 11);
      Shape11.addCuboid(0F, 0F, 0F, 7, 2, 1);
      Shape11.setPivot(-15F, 0F, -2F);
      Shape11.setTextureSize(64, 32);
      Shape11.mirror = true;
      setRotation(Shape11, 0F, 0F, 0F);
      Shape12 = new ModelPart(this, 51, 3);
      Shape12.addCuboid(0F, 0F, 0F, 2, 1, 3);
      Shape12.setPivot(-15F, 3F, -1F);
      Shape12.setTextureSize(64, 32);
      Shape12.mirror = true;
      setRotation(Shape12, 0F, 0F, 0F);
//      Shape13 = new ModelPart(this, 0, 0);
//      Shape13.addCuboid(0F, 0F, 0F, 22, 5, 3);
//      Shape13.setPivot(0F, -2F, -1F);
//      Shape13.setTextureSize(64, 32);
//      Shape13.mirror = true;
//      setRotation(Shape13, 0F, 0F, 0F);
      Shape14 = new ModelPart(this, 48, 11);
      Shape14.addCuboid(0F, 0F, 0F, 7, 2, 1);
      Shape14.setPivot(-15F, 0F, 2F);
      Shape14.setTextureSize(64, 32);
      Shape14.mirror = true;
      setRotation(Shape14, 0F, 0F, 0F);
      Shape15 = new ModelPart(this, 50, 0);
      Shape15.addCuboid(0F, 0F, 0F, 2, 6, 5);
      Shape15.setPivot(-17F, -1F, -2F);
      Shape15.setTextureSize(64, 32);
      Shape15.mirror = true;
      setRotation(Shape15, 0F, 0F, 0F);
  }
  
  @Override
  public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
		float reloadProgress, ModelTransformationMode transformType, int part, float fireProgress, float chargeProgress, int light,
	int overlay) {
    
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
//    Shape13.render(f5);
    Shape14.render(matrices, vertices, light, overlay);
    Shape15.render(matrices, vertices, light, overlay);
  }

}