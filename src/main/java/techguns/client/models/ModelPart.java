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

    float expansionX = 0.0f;
    float expansionY = 0.0f;
    float expansionZ = 0.0f;

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

    public void addCuboid(float x, float y, float z, float w, float h, float l, float expansionX, float expansionY, float expansionZ) {
        this.addCuboid(x,y,z, w,h,l);
        this.expansionX = expansionX;
        this.expansionY = expansionY;
        this.expansionZ = expansionZ;
    }


    public void addCuboid(float x, float y, float z, float w, float h, float l, float scale) {
        this.addCuboid(x,y,z, w,h,l);
    }

    public void setPivot(float px, float py, float pz) {
        this.pivotX = px;
        this.pivotY = py;
        this.pivotZ = pz;
    }

    //This method is used by some Tabula exports
    public ModelPart setTextureOffset(float u, float v) {
        this.u += u;
        this.v += v;
        return this;
    }

    public void setTextureSize(int texW, int texH) {
        this.texture_width=texW;
        this.texture_height=texH;
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        this.render(matrices, vertices, light, overlay, 0f, 0f, 0f);
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float rx, float ry, float rz) {
        if (cuboid == null){
            this.cuboid = new net.minecraft.client.model.ModelPart.Cuboid(u, v, x, y, z, width, height, length, 0,0,0, mirror, texture_width, texture_height);
        }
        matrices.push();

        matrices.translate(pivotX*SCALE, pivotY*SCALE, pivotZ*SCALE);

        matrices.push();
        if (this.pitch!=0.0 || rx != 0.0f){
            TGMatrixOps.rotate(matrices, this.pitch * RAD2DEG + rx, 1F,0F, 0F);
        }
        if (this.yaw!=0.0 || ry != 0.0f){
            TGMatrixOps.rotate(matrices, this.yaw * RAD2DEG + ry, 0F,1F, 0F);
        }
        if (this.roll!=0.0 || rz != 0.0f){
            TGMatrixOps.rotate(matrices, this.roll * RAD2DEG + rz, 0F,0F, 1F);
        }

        //Expansion
        if (this.expansionX != 0 || this.expansionY != 0.0f || this.expansionZ != 0.0f) {
            float scaleX = (this.width + (this.expansionX * 2.0f)) / this.width;
            float scaleY = (this.height + (this.expansionY * 2.0f)) / this.height;
            float scaleZ = (this.length + (this.expansionZ * 2.0f)) / this.length;
            matrices.scale(scaleX, scaleY, scaleZ);
        }

        this.cuboid.renderCuboid(matrices.peek(), vertices, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
        matrices.pop();
        for (ModelPart child : this.children){
            child.render(matrices, vertices, light, overlay, rx, ry, rz);
        }
        matrices.pop();
    }

    public void addChild(ModelPart child) {
        this.children.add(child);
    }
}
