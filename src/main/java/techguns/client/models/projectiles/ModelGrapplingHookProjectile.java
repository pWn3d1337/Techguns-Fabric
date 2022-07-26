package techguns.client.models.projectiles;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;
import techguns.client.models.ModelPart;

public class ModelGrapplingHookProjectile extends ModelMultipart {
    public ModelPart hook01;
    public ModelPart hook01_1;
    public ModelPart hook02;
    public ModelPart hook03;
    public ModelPart hook04;
    public ModelPart hook05;
    public ModelPart hook06;

    public ModelGrapplingHookProjectile() {
        super(RenderLayer::getEntitySolid);
        textureWidth = 64;
        textureHeight = 32;

        this.textureWidth = 64;
        this.textureHeight = 32;
        this.hook05 = new ModelPart(this, 0, 0);
        this.hook05.setPivot(1.4F, 0.0F, 0.0F);
        this.hook05.addCuboid(0.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, -0.2F, -0.2F, -0.2F);
        setRotation(hook05, 0.0F, 0.0F, 0.7853981633974483F);
        this.hook06 = new ModelPart(this, 0, 0);
        this.hook06.setPivot(1.4F, 0.0F, 0.0F);
        this.hook06.addCuboid(0.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, -0.2F, -0.2F, -0.2F);
        setRotation(hook06, 0.0F, -0.7853981633974483F, 0.0F);
        this.hook01_1 = new ModelPart(this, 19, 20);
        this.hook01_1.setPivot(3.0F, 0.0F, 0.0F);
        this.hook01_1.addCuboid(0.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        setRotation(hook01_1, 0.7853981633974483F, 0.0F, 0.0F);
        this.hook04 = new ModelPart(this, 0, 0);
        this.hook04.setPivot(1.4F, 0.0F, 0.0F);
        this.hook04.addCuboid(0.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, -0.2F, -0.2F, -0.2F);
        setRotation(hook04, 0.0F, 0.7853981633974483F, 0.0F);
        this.hook03 = new ModelPart(this, 0, 0);
        this.hook03.setPivot(1.4F, 0.0F, 0.0F);
        this.hook03.addCuboid(0.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, -0.2F, -0.2F, -0.2F);
        setRotation(hook03, 0.0F, 0.0F, -0.7853981633974483F);
        this.hook02 = new ModelPart(this, 24, 19);
        this.hook02.setPivot(1.0F, 0.0F, 0.0F);
        this.hook02.addCuboid(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        setRotation(hook02, 0.7853981633974483F, 0.0F, 0.0F);
        this.hook01 = new ModelPart(this, 31, 19);
        this.hook01.setPivot(0.0F, 0.0F, 0.0F);
        this.hook01.addCuboid(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, -0.3F, -0.3F);
        setRotation(hook01, 0.7853981633974483F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
                       float reloadProgress, ModelTransformation.Mode transformType, int part, float fireProgress, float chargeProgress, int light,
                       int overlay) {
        ImmutableList.of(this.hook05, this.hook06, this.hook01_1, this.hook04, this.hook03, this.hook02, this.hook01).forEach((modelRenderer) -> {
            modelRenderer.render(matrices, vertices, light, overlay);
        });
    }

}