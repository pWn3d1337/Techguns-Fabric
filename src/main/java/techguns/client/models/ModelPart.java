package techguns.client.models;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import techguns.client.render.math.TGMatrixOps;

import java.util.ArrayList;
import java.util.List;

public class ModelPart {
    /**
     * 1.0/16.0
     */
    protected static final double SCALE = 0.0625;
    protected static final float RAD2DEG = 180.0F / (float) Math.PI;

    ModelMultipart parent;
    float x;
    float y;
    float z;
    float width;
    float height;
    float length;

    public float pivotX;
    public float pivotY;
    public float pivotZ;

    int texture_width;
    int texture_height;

    public boolean mirror;

    public float pitch;
    public float yaw;
    public float roll;

    int u;
    int v;

    private net.minecraft.client.model.ModelPart.Cuboid cuboid = null;

    private List<ModelPart> children = new ArrayList<>();

    public ModelPart(ModelMultipart parent, int u, int v) {
        this.parent = parent;
        this.texture_width = parent.textureWidth;
        this.texture_height = parent.textureHeight;
        this.u = u;
        this.v = v;
    }

    public void addCuboid(float x, float y, float z, float w, float h, float l) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = w;
        this.height = h;
        this.length = l;
    }

    public void addCuboid(float x, float y, float z, float w, float h, float l, float scale) {
        this.addCuboid(x,y,z, w,h,l);
    }

    public void setPivot(float px, float py, float pz) {
        this.pivotX = px;
        this.pivotY = py;
        this.pivotZ = pz;
    }

    public void setTextureSize(int texW, int texH) {
        this.texture_width=texW;
        this.texture_height=texH;
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        if (cuboid == null){
            this.cuboid = new net.minecraft.client.model.ModelPart.Cuboid(u, v, x, y, z, width, height, length, 0,0,0, mirror, texture_width, texture_height);
        }
        matrices.push();
        matrices.translate(pivotX*SCALE, pivotY*SCALE, pivotZ*SCALE);

        matrices.push();
        if (this.pitch!=0.0){
            TGMatrixOps.rotate(matrices, this.pitch * RAD2DEG, 1F,0F, 0F);
        }
        if (this.yaw!=0.0){
            TGMatrixOps.rotate(matrices, this.yaw * RAD2DEG, 0F,1F, 0F);
        }
        if (this.roll!=0.0){
            TGMatrixOps.rotate(matrices, this.roll * RAD2DEG, 0F,0F, 1F);
        }
        this.cuboid.renderCuboid(matrices.peek(), vertices, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
        matrices.pop();
        for (ModelPart child : this.children){
            child.render(matrices, vertices, light, overlay);
        }
        matrices.pop();
    }

    public void addChild(ModelPart child) {
        this.children.add(child);
    }
}
