package techguns.client.models.guns;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import techguns.client.models.ModelPart;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelFragGrenade extends ModelMultipart {

    public ModelPart Top;
    public ModelPart MainRot1;
    public ModelPart MainRot2;
    public ModelPart Main;
    public ModelPart TopRotated;
    public ModelPart Bottom;
    public ModelPart BottomRotated;
    public ModelPart TopPart;
    public ModelPart Box01;
    public ModelPart Box02;
    public ModelPart Ring;

    protected boolean showRing;
    
    public ModelFragGrenade(boolean showRing) {
    	super(RenderLayer::getEntitySolid);
    	
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.Main = new ModelPart(this, 13, 0);
        this.Main.setPivot(-1.5F, -16.0F, -1.5F);
        this.Main.addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.MainRot2 = new ModelPart(this, 13, 16);
        this.MainRot2.setPivot(0.5F, -14.0F, 0.5F);
        this.MainRot2.addCuboid(-2.0F, 0.0F, -2.0F, 4, 2, 4, -0.1F);
        this.setRotation(MainRot2, 0.0F, 0.7853981633974483F, 0.0F);
        this.BottomRotated = new ModelPart(this, 15, 11);
        this.BottomRotated.setPivot(0.5F, -12.0F, 0.5F);
        this.BottomRotated.addCuboid(-1.5F, 0.0F, -1.5F, 3, 1, 3, -0.1F);
        this.setRotation(BottomRotated, 0.0F, 0.7853981633974483F, 0.0F);
        this.TopPart = new ModelPart(this, 15, 23);
        this.TopPart.setPivot(-0.5F, -18.7F, -0.5F);
        this.TopPart.addCuboid(0.0F, 0.0F, 0.0F, 2, 2, 2, -0.2F);
        this.Box02 = new ModelPart(this, 1, 28);
        this.Box02.setPivot(4.1F, -17.3F, -0.5F);
        this.Box02.addCuboid(0.0F, 0.0F, 0.0F, 5, 1, 2, -0.3F);
        this.setRotation(Box02, 0.0F, 0.0F, 1.5707963267948966F);
        this.TopRotated = new ModelPart(this, 15, 11);
        this.TopRotated.setPivot(0.5F, -17.0F, 0.5F);
        this.TopRotated.addCuboid(-1.5F, 0.0F, -1.5F, 3, 1, 3, -0.1F);
        this.setRotation(TopRotated, 0.0F, 0.7853981633974483F, 0.0F);
        
        this.showRing=showRing;
        if (showRing){
	        this.Ring = new ModelPart(this, 0, 7);
	        this.Ring.setPivot(-0.5F, -20.2F, -3.3F);
	        this.Ring.addCuboid(0.0F, 0.0F, 0.0F, 2, 4, 4, -0.9F);
        }
        
        this.MainRot1 = new ModelPart(this, 13, 16);
        this.MainRot1.setPivot(0.5F, -16.0F, 0.5F);
        this.MainRot1.addCuboid(-2.0F, 0.0F, -2.0F, 4, 2, 4, -0.1F);
        this.setRotation(MainRot1, 0.0F, 0.7853981633974483F, 0.0F);
        this.Top = new ModelPart(this, 15, 11);
        this.Top.setPivot(-1.0F, -17.0F, -1.0F);
        this.Top.addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.Box01 = new ModelPart(this, 19, 28);
        this.Box01.setPivot(0.6F, -18.8F, -0.5F);
        this.Box01.addCuboid(0.0F, 0.0F, 0.0F, 4, 1, 2, -0.3F);
        this.setRotation(Box01, 0.0F, 0.0F, 0.40980330836826856F);
        this.Bottom = new ModelPart(this, 15, 11);
        this.Bottom.setPivot(-1.0F, -12.0F, -1.0F);
        this.Bottom.addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
    }    
    
	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
		float reloadProgress, ModelTransformationMode transformType, int part, float fireProgress, float chargeProgress, int light,
	int overlay) {
		
		this.Main.render(matrices, vertices, light, overlay);
        this.BottomRotated.render(matrices, vertices, light, overlay);
        this.MainRot2.render(matrices, vertices, light, overlay);
        this.Box02.render(matrices, vertices, light, overlay);
        this.Bottom.render(matrices, vertices, light, overlay);
        if (showRing && fireProgress<=0.0f){
        	this.Ring.render(matrices, vertices, light, overlay);
        }
        this.TopPart.render(matrices, vertices, light, overlay);
        this.MainRot1.render(matrices, vertices, light, overlay);
        this.Top.render(matrices, vertices, light, overlay);
        this.TopRotated.render(matrices, vertices, light, overlay);
        this.Box01.render(matrices, vertices, light, overlay);
	}

}
