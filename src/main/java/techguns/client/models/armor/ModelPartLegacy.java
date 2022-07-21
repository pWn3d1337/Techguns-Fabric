package techguns.client.models.armor;

import net.minecraft.client.model.*;

/**
 * This class is a Dummy for easier converting old models to new format
 */
public class ModelPartLegacy {

    protected int u;
    protected int v;

    protected float offsetX;
    protected float offsetY;
    protected float offsetZ;
    protected float sizeX;
    protected float sizeY;
    protected float sizeZ;
    protected Dilation scale;

    protected float pivotX;
    protected float pivotY;
    protected float pivotZ;

    protected float rotationX;
    protected float rotationY;
    protected float rotationZ;

    public boolean mirror=false;

    public ModelPartLegacy(Model m, int u, int v){
        this.u = u;
        this.v = v;
    }

    public ModelPartLegacy(int u, int v){
        this(null,u,v);
    }

    public void addCuboid(float offsetX, float offsetY, float offsetZ, float sizeX, float sizeY, float sizeZ, Dilation scale){
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.scale = scale;
    }

    public void addCuboid(float offsetX, float offsetY, float offsetZ, float sizeX, float sizeY, float sizeZ){
        this.addCuboid(offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ, Dilation.NONE);
    }

    public void setPivot(float pivotX, float pivotY, float pivotZ){
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.pivotZ = pivotZ;
    }

    public void setTextureSize(int textureSizeX, int textureSizeY){
        //not needed?
    }

    public void setRotation(float rotationX, float rotationY, float rotationZ){
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
    }

    public ModelPartData addTo(ModelPartData parent, String name){
        return parent.addChild(name, ModelPartBuilder.create().mirrored(this.mirror).uv(this.u, this.v).cuboid(this.offsetX, this.offsetY, this.offsetZ, this.sizeX, this.sizeY, this.sizeZ, this.scale), ModelTransform.of(this.pivotX, this.pivotY, this.pivotZ, this.rotationX, this.rotationY, this.rotationZ));
    }
}
