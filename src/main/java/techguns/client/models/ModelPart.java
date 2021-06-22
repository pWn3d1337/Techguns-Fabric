package techguns.client.models;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class ModelPart {
    Model parent;
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

    public ModelPart(Model parent, int u, int v) {
        this.parent = parent;
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
        this.cuboid.renderCuboid(matrices.peek(), vertices, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
        for (ModelPart child : this.children){
            child.render(matrices, vertices, light, overlay);
        }
    }

    public void addChild(ModelPart child) {
        this.children.add(child);
    }
}
