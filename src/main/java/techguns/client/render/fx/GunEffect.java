package techguns.client.render.fx;

//
public class GunEffect {
    public IScreenEffect screenEffect = null;

    public float offsetX_l=0f;
    public float offsetX_r=0f;
    public float offsetY=0f;
    public float offsetZ=0f;
    public float effectScale=1.0f;

    public float offsetY_3p=0f;
    public float offsetZ_3p=0f;
    public float effectScale_3p=0.5f;

    public float jitterX = 0f;
    public float jitterY = 0f;
    public float jitterAngle = 0f;
    public float jitterScale = 0f;

    public GunEffect(IScreenEffect screenEffect, float offsetX_l, float offsetX_r, float offsetY, float offsetZ, float effectScale) {
        this.screenEffect = screenEffect;
        this.offsetX_l = offsetX_l;
        this.offsetX_r = offsetX_r;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.effectScale = effectScale;
    }

    public GunEffect(IScreenEffect screenEffect, float offsetX_l, float offsetX_r, float offsetY, float offsetZ, float effectScale, float offsetY_3p, float offsetZ_3p, float effectScale_3p) {
        this.screenEffect = screenEffect;
        this.offsetX_l = offsetX_l;
        this.offsetX_r = offsetX_r;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.effectScale = effectScale;
        this.offsetY_3p = offsetY_3p;
        this.offsetZ_3p = offsetZ_3p;
        this.effectScale_3p = effectScale_3p;
    }

    public GunEffect(IScreenEffect screenEffect, float offsetX_l, float offsetX_r, float offsetY, float offsetZ, float effectScale, float offsetY_3p, float offsetZ_3p, float effectScale_3p, float jitterX, float jitterY, float jitterAngle, float jitterScale) {
        this.screenEffect = screenEffect;
        this.offsetX_l = offsetX_l;
        this.offsetX_r = offsetX_r;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.effectScale = effectScale;
        this.offsetY_3p = offsetY_3p;
        this.offsetZ_3p = offsetZ_3p;
        this.effectScale_3p = effectScale_3p;
        this.jitterX = jitterX;
        this.jitterY = jitterY;
        this.jitterAngle = jitterAngle;
        this.jitterScale = jitterScale;
    }
}
