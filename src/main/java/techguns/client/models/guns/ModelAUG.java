package techguns.client.models.guns;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelAUG extends ModelMultipart {
    public ModelPart Barrel;
    public ModelPart Receiver03;
    public ModelPart Receiver01;
    public ModelPart Foregrip;
    public ModelPart Barrel2;
    public ModelPart Scope03;
    public ModelPart Bolt;
    public ModelPart Barrel2_1;
    public ModelPart Grip2;
    public ModelPart Grip3;
    public ModelPart Trigger02;
    public ModelPart Grip1;
    public ModelPart Stock1;
    public ModelPart Stock3;
    public ModelPart Stock2;
    public ModelPart Stock6;
    public ModelPart Stock4;
    public ModelPart Stock5;
    public ModelPart Magazine01;
    public ModelPart MagSocket;
    public ModelPart Magazine02;
    public ModelPart ScopeMount1;
    public ModelPart Scope01;
    public ModelPart ScopeMount2;

    public ModelAUG() {
        super(RenderLayer::getEntitySolid);
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.ScopeMount1 = new ModelPart(this, 1, 0);
        this.ScopeMount1.setPivot(-1.5F, -10.0F, -7.0F);
        this.ScopeMount1.addCuboid(0.0F, 0.0F, 0.0F, 2, 8, 1, 0.0F);
        this.setRotation(ScopeMount1, -0.6108652381980153F, 0.0F, 0.0F);
        this.Scope03 = new ModelPart(this, 1, 13);
        this.Scope03.setPivot(-0.5F, -11.5F, 1.0F);
        this.Scope03.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 10, 0.0F);
        this.setRotation(Scope03, 0.0F, 0.0F, 0.7853981633974483F);
        this.Stock2 = new ModelPart(this, 39, 27);
        this.Stock2.setPivot(-2.0F, -2.0F, 21.0F);
        this.Stock2.addCuboid(0.0F, 0.0F, 0.0F, 3, 7, 4, 0.0F);
        this.Scope01 = new ModelPart(this, 1, 1);
        this.Scope01.setPivot(-0.5F, -11.0F, -8.0F);
        this.Scope01.addCuboid(0.0F, 0.0F, 0.0F, 2, 2, 9, 0.0F);
        this.setRotation(Scope01, 0.0F, 0.0F, 0.7853981633974483F);
        this.Barrel2_1 = new ModelPart(this, 0, 49);
        this.Barrel2_1.setPivot(-0.5F, -4.5F, -41.0F);
        this.Barrel2_1.addCuboid(0.0F, 0.0F, 0.0F, 2, 2, 3, 0.0F);
        this.setRotation(Barrel2_1, 0.0F, -0.0F, 0.7853981633974483F);
        this.Stock6 = new ModelPart(this, 49, 6);
        this.Stock6.setPivot(-2.0F, -2.0F, 8.0F);
        this.Stock6.addCuboid(0.0F, 0.0F, 0.0F, 3, 2, 4, 0.0F);
        this.Trigger02 = new ModelPart(this, 64, 28);
        this.Trigger02.setPivot(-1.0F, 0.5F, -1.0F);
        this.Trigger02.addCuboid(0.0F, 0.0F, 0.0F, 1, 3, 2, 0.0F);
        this.Stock1 = new ModelPart(this, 64, 0);
        this.Stock1.setPivot(-2.5F, -2.0F, 25.0F);
        this.Stock1.addCuboid(0.0F, 0.0F, 0.0F, 4, 12, 4, 0.0F);
        this.Bolt = new ModelPart(this, 1, 33);
        this.Bolt.setPivot(2.5F, -5.0F, -13.0F);
        this.Bolt.addCuboid(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.setRotation(Bolt, 0.0F, 0.0F, 0.7853981633974483F);
        this.Grip1 = new ModelPart(this, 64, 43);
        this.Grip1.setPivot(-2.0F, 1.0F, 0.0F);
        this.Grip1.addCuboid(0.0F, 0.0F, 0.0F, 3, 9, 4, 0.0F);
        this.setRotation(Grip1, 0.4461061568097506F, 0.0F, 0.0F);
        this.Barrel = new ModelPart(this, 0, 32);
        this.Barrel.setPivot(-0.5F, -4.5F, -38.0F);
        this.Barrel.addCuboid(0.0F, 0.0F, 0.0F, 2, 2, 23, 0.0F);
        this.setRotation(Barrel, 0.0F, 0.0F, 0.7853981633974483F);
        this.Stock5 = new ModelPart(this, 28, 11);
        this.Stock5.setPivot(-2.0F, -2.0F, 11.0F);
        this.Stock5.addCuboid(0.0F, 0.0F, 0.0F, 3, 5, 10, 0.0F);
        this.Barrel2 = new ModelPart(this, 0, 40);
        this.Barrel2.setPivot(-2.0F, -2.7F, -21.0F);
        this.Barrel2.addCuboid(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.setRotation(Barrel2, 0.0F, 0.0F, 0.7853981633974483F);
        this.Magazine02 = new ModelPart(this, 95, 19);
        this.Magazine02.setPivot(-1.5F, 8.0F, 10.6F);
        this.Magazine02.addCuboid(0.0F, 0.0F, 0.0F, 2, 4, 6, 0.0F);
        this.setRotation(Magazine02, -0.36826447217080355F, 0.0F, 0.0F);
        this.Grip3 = new ModelPart(this, 72, 23);
        this.Grip3.setPivot(-2.0F, 1.0F, -7.0F);
        this.Grip3.addCuboid(0.0F, 0.0F, 0.0F, 3, 9, 1, 0.0F);
        this.setRotation(Grip3, 0.5918411493512771F, 0.0F, 0.0F);
        this.Stock4 = new ModelPart(this, 28, 0);
        this.Stock4.setPivot(-2.0F, -2.0F, 5.0F);
        this.Stock4.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 7, 0.0F);
        this.setRotation(Stock4, -0.31869712141416456F, 0.0F, 0.0F);
        this.ScopeMount2 = new ModelPart(this, 20, 0);
        this.ScopeMount2.setPivot(-1.5F, -10.0F, 2.0F);
        this.ScopeMount2.addCuboid(0.0F, 0.0F, 0.0F, 2, 7, 1, 0.0F);
        this.setRotation(ScopeMount2, -0.4363323129985824F, 0.0F, 0.0F);
        this.Receiver03 = new ModelPart(this, 85, 37);
        this.Receiver03.setPivot(-2.0F, -1.0F, -13.0F);
        this.Receiver03.addCuboid(0.0F, 0.0F, 0.0F, 3, 2, 18, 0.0F);
        this.Stock3 = new ModelPart(this, 72, 8);
        this.Stock3.setPivot(-2.0F, 1.3F, 18.0F);
        this.Stock3.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 10, 0.0F);
        this.setRotation(Stock3, -0.4553564018453205F, 0.0F, 0.0F);
        this.Magazine01 = new ModelPart(this, 112, 19);
        this.Magazine01.setPivot(-1.5F, 4.0F, 11.4F);
        this.Magazine01.addCuboid(0.0F, 0.0F, 0.0F, 2, 5, 6, 0.0F);
        this.setRotation(Magazine01, -0.20943951023931953F, 0.0F, 0.0F);
        this.Receiver01 = new ModelPart(this, 10, 15);
        this.Receiver01.setPivot(-0.5F, -5.0F, -15.0F);
        this.Receiver01.addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 44, 0.0F);
        this.setRotation(Receiver01, 0.0F, 0.0F, 0.7853981633974483F);
        this.Grip2 = new ModelPart(this, 64, 34);
        this.Grip2.setPivot(-2.0F, 8.0F, -2.0F);
        this.Grip2.addCuboid(0.0F, 0.0F, 0.0F, 3, 1, 6, 0.0F);
        this.MagSocket = new ModelPart(this, 101, 4);
        this.MagSocket.setPivot(-2.0F, 1.0F, 11.0F);
        this.MagSocket.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 8, 0.0F);
        this.setRotation(MagSocket, -0.20943951023931953F, 0.0F, 0.0F);
        this.Foregrip = new ModelPart(this, 32, 40);
        this.Foregrip.setPivot(-2.5F, 0.0F, -14.0F);
        this.Foregrip.addCuboid(0.0F, 0.0F, 0.0F, 3, 9, 3, 0.0F);
        this.setRotation(Foregrip, 0.0F, 0.7853981633974483F, 0.0F);
    }


    	
    @Override
    public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft, float reloadProgress,
    		Mode transformType, int part, float fireProgress, float chargeProgress, int light, int overlay) {
    	if(part==0){
	        this.ScopeMount1.render(matrices, vertices, light, overlay);
	        this.Scope03.render(matrices, vertices, light, overlay);
	        this.Stock2.render(matrices, vertices, light, overlay);
	        this.Scope01.render(matrices, vertices, light, overlay);
	        this.Barrel2_1.render(matrices, vertices, light, overlay);
	        this.Stock6.render(matrices, vertices, light, overlay);
	        this.Trigger02.render(matrices, vertices, light, overlay);
	        this.Stock1.render(matrices, vertices, light, overlay);
	        this.Grip1.render(matrices, vertices, light, overlay);
	        this.Barrel.render(matrices, vertices, light, overlay);
	        this.Stock5.render(matrices, vertices, light, overlay);
	        this.Barrel2.render(matrices, vertices, light, overlay);
	        this.Magazine02.render(matrices, vertices, light, overlay);
	        this.Grip3.render(matrices, vertices, light, overlay);
	        this.Stock4.render(matrices, vertices, light, overlay);
	        this.ScopeMount2.render(matrices, vertices, light, overlay);
	        this.Receiver03.render(matrices, vertices, light, overlay);
	        this.Stock3.render(matrices, vertices, light, overlay);
	        this.Magazine01.render(matrices, vertices, light, overlay);
	        this.Receiver01.render(matrices, vertices, light, overlay);
	        this.Grip2.render(matrices, vertices, light, overlay);
	        this.MagSocket.render(matrices, vertices, light, overlay);
	        this.Foregrip.render(matrices, vertices, light, overlay);
    	} else {
    		matrices.push();
			if (fireProgress>0) {
				float movebolt=0f;
				if (fireProgress >0.4f) {
					movebolt = 1.0f-(fireProgress-0.4f)/0.6f;
				} else {
					movebolt = fireProgress/0.4f;
				}
				matrices.translate(0, 0, movebolt*0.5f * scale);
			}
    		this.Bolt.render(matrices, vertices, light, overlay);
    		matrices.pop();
    	}
    }


}
