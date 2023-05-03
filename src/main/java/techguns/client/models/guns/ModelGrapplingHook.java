package techguns.client.models.guns;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.client.models.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;
import techguns.client.render.math.TGMatrixOps;
import techguns.entities.projectiles.GrapplingHookProjectile;
import techguns.util.MathUtil;


public class ModelGrapplingHook extends ModelMultipart {
    public ModelPart main01;
    public ModelPart tank01;
    public ModelPart tank02;
    public ModelPart main02;
    public ModelPart hook01;
    public ModelPart tank03;
    public ModelPart main03;
    public ModelPart tank04;
    public ModelPart tank05;
    public ModelPart main04;
    public ModelPart tank06;
    public ModelPart tank07;
    public ModelPart tank08;
    public ModelPart wheel1;
    public ModelPart grip01;
    public ModelPart main05;
    public ModelPart grip02;
    public ModelPart grip03;
    public ModelPart grip04;
    public ModelPart grip05;
    public ModelPart main06;
    public ModelPart main07;
    public ModelPart main08;
    public ModelPart main09;
    public ModelPart main10;
    public ModelPart main11;
    public ModelPart main12;
    public ModelPart grip06;
    public ModelPart hook01_1;
    public ModelPart hook02;
    public ModelPart hook03;
    public ModelPart hook04;
    public ModelPart hook05;
    public ModelPart hook06;
    public ModelPart wheel2;

    public ModelGrapplingHook() {
        super(RenderLayer::getEntitySolid);
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.grip05 = new ModelPart(this, 46, 15);
        this.grip05.setPivot(0.0F, 6.5F, 0.0F);
        this.grip05.addCuboid(-0.5F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
       setRotation(grip05, 0.7853981633974483F, 0.0F, 0.0F);
        this.hook04 = new ModelPart(this, 0, 0);
        this.hook04.setPivot(13.4F, 0.0F, 0.0F);
        this.hook04.addCuboid(0.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, -0.2F, -0.2F, -0.2F);
       setRotation(hook04, 0.0F, 0.7853981633974483F, 0.0F);
        this.main03 = new ModelPart(this, 20, 8);
        this.main03.setPivot(12.5F, 0.0F, 0.0F);
        this.main03.addCuboid(-4.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
       setRotation(main03, 0.7853981633974483F, 0.0F, 0.0F);
        this.grip01 = new ModelPart(this, 36, 10);
        this.grip01.setPivot(0.0F, 2.0F, 0.0F);
        this.grip01.addCuboid(0.0F, 0.0F, -0.5F, 2.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.hook01_1 = new ModelPart(this, 19, 20);
        this.hook01_1.setPivot(15.0F, 0.0F, 0.0F);
        this.hook01_1.addCuboid(0.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
       setRotation(hook01_1, 0.7853981633974483F, 0.0F, 0.0F);
        this.tank01 = new ModelPart(this, 32, 0);
        this.tank01.setPivot(2.0F, -4.0F, 0.0F);
        this.tank01.addCuboid(0.0F, -1.0F, -1.0F, 7.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.main07 = new ModelPart(this, 0, 14);
        this.main07.setPivot(0.0F, 2.0F, 0.5F);
        this.main07.addCuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
       setRotation(main07, 0.0F, 0.0F, 0.7853981633974483F);
        this.hook03 = new ModelPart(this, 0, 0);
        this.hook03.setPivot(13.4F, 0.0F, 0.0F);
        this.hook03.addCuboid(0.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, -0.2F, -0.2F, -0.2F);
       setRotation(hook03, 0.0F, 0.0F, -0.7853981633974483F);
        this.main08 = new ModelPart(this, 0, 18);
        this.main08.setPivot(-2.6F, -2.0F, 0.0F);
        this.main08.addCuboid(-0.5F, -0.5F, 1.0F, 1.0F, 2.5F, 0.5F, 0.0F, 0.0F, 0.0F);
        this.main11 = new ModelPart(this, 0, 18);
        this.main11.setPivot(-2.6F, -2.0F, 0.0F);
        this.main11.addCuboid(-0.5F, -0.5F, -1.5F, 1.0F, 2.5F, 0.5F, 0.0F, 0.0F, 0.0F);
        this.tank05 = new ModelPart(this, 58, 4);
        this.tank05.setPivot(9.0F, -2.0F, 0.0F);
        this.tank05.addCuboid(0.0F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.grip04 = new ModelPart(this, 36, 15);
        this.grip04.setPivot(0.0F, 6.0F, 0.0F);
        this.grip04.addCuboid(0.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.tank03 = new ModelPart(this, 58, 0);
        this.tank03.setPivot(9.5F, -3.5F, 0.0F);
        this.tank03.addCuboid(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, -0.3F, -0.3F);
       setRotation(tank03, 0.7853981633974483F, 0.0F, 0.0F);
        this.main05 = new ModelPart(this, 21, 14);
        this.main05.setPivot(0.0F, 0.0F, 0.0F);
        this.main05.addCuboid(0.0F, 0.0F, -1.0F, 5.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.main10 = new ModelPart(this, 4, 18);
        this.main10.setPivot(-3.1F, 0.0F, 0.0F);
        this.main10.addCuboid(0.0F, 0.0F, 1.0F, 1.0F, 3.0F, 0.5F, 0.0F, 0.0F, 0.0F);
       setRotation(main10, 0.0F, 0.0F, -0.7853981633974483F);
        this.hook01 = new ModelPart(this, 31, 19);
        this.hook01.setPivot(12.0F, 0.0F, 0.0F);
        this.hook01.addCuboid(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, -0.3F, -0.3F);
       setRotation(hook01, 0.7853981633974483F, 0.0F, 0.0F);
        this.grip02 = new ModelPart(this, 42, 10);
        this.grip02.setPivot(5.0F, 0.5F, 0.0F);
        this.grip02.addCuboid(0.0F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.wheel2 = new ModelPart(this, 22, 4);
        this.wheel2.setPivot(0.0F, 0.0F, 0.0F);
        this.wheel2.addCuboid(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
       setRotation(wheel2, 0.0F, 0.0F, 1.5707963267948966F);
        this.main02 = new ModelPart(this, 0, 10);
        this.main02.setPivot(1.0F, 0.0F, 0.0F);
        this.main02.addCuboid(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
       setRotation(main02, 0.7853981633974483F, 0.0F, 0.0F);
        this.tank08 = new ModelPart(this, 58, 0);
        this.tank08.setPivot(-0.5F, -3.5F, 0.0F);
        this.tank08.addCuboid(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, -0.3F, -0.3F);
       setRotation(tank08, 0.7853981633974483F, 0.0F, 0.0F);
        this.main04 = new ModelPart(this, 0, 4);
        this.main04.setPivot(-0.5F, 0.0F, 0.0F);
        this.main04.addCuboid(0.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
       setRotation(main04, 0.7853981633974483F, 0.0F, 0.0F);
        this.main09 = new ModelPart(this, 4, 18);
        this.main09.setPivot(-3.1F, 0.0F, 0.0F);
        this.main09.addCuboid(0.0F, 0.0F, -1.5F, 1.0F, 3.0F, 0.5F, 0.0F, 0.0F, 0.0F);
       setRotation(main09, 0.0F, 0.0F, -0.7853981633974483F);
        this.main01 = new ModelPart(this, 50, 0);
        this.main01.setPivot(10.0F, 0.0F, 0.0F);
        this.main01.addCuboid(0.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
       setRotation(main01, 0.7853981633974483F, 0.0F, 0.0F);
        this.grip06 = new ModelPart(this, 32, 10);
        this.grip06.setPivot(2.0F, 2.0F, 0.0F);
        this.grip06.addCuboid(0.0F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
       setRotation(grip06, 0.0F, 0.0F, -0.39269908169872414F);
        this.tank06 = new ModelPart(this, 52, 7);
        this.tank06.setPivot(0.0F, -3.5F, 0.0F);
        this.tank06.addCuboid(0.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
       setRotation(tank06, 0.7853981633974483F, 0.0F, 0.0F);
        this.main12 = new ModelPart(this, 8, 20);
        this.main12.setPivot(-2.7F, -1.0F, 0.0F);
        this.main12.addCuboid(0.0F, 0.0F, -0.5F, 3.0F, 0.5F, 1.0F, 0.0F, 0.0F, 0.0F);
       setRotation(main12, 0.0F, 0.0F, 0.3127630032889644F);
        this.grip03 = new ModelPart(this, 54, 11);
        this.grip03.setPivot(6.0F, 4.5F, 0.0F);
        this.grip03.addCuboid(-1.0F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
       setRotation(grip03, 0.0F, 0.0F, 0.7853981633974483F);
        this.main06 = new ModelPart(this, 13, 14);
        this.main06.setPivot(-0.9F, 1.5F, 0.0F);
        this.main06.addCuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
       setRotation(main06, 0.0F, 0.0F, 0.7853981633974483F);
        this.hook05 = new ModelPart(this, 0, 0);
        this.hook05.setPivot(13.4F, 0.0F, 0.0F);
        this.hook05.addCuboid(0.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, -0.2F, -0.2F, -0.2F);
       setRotation(hook05, 0.0F, 0.0F, 0.7853981633974483F);
        this.tank02 = new ModelPart(this, 31, 5);
        this.tank02.setPivot(2.0F, -3.5F, 0.0F);
        this.tank02.addCuboid(0.0F, -1.0F, -1.5F, 7.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.wheel1 = new ModelPart(this, 22, 0);
        this.wheel1.setPivot(-2.6F, -2.0F, 0.0F);
        this.wheel1.addCuboid(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.tank04 = new ModelPart(this, 48, 4);
        this.tank04.setPivot(9.0F, -3.5F, 0.0F);
        this.tank04.addCuboid(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
       setRotation(tank04, 0.7853981633974483F, 0.0F, 0.0F);
        this.hook06 = new ModelPart(this, 0, 0);
        this.hook06.setPivot(13.4F, 0.0F, 0.0F);
        this.hook06.addCuboid(0.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, -0.2F, -0.2F, -0.2F);
       setRotation(hook06, 0.0F, -0.7853981633974483F, 0.0F);
        this.tank07 = new ModelPart(this, 46, 10);
        this.tank07.setPivot(0.0F, -2.0F, 0.0F);
        this.tank07.addCuboid(0.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.hook02 = new ModelPart(this, 24, 19);
        this.hook02.setPivot(13.0F, 0.0F, 0.0F);
        this.hook02.addCuboid(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
       setRotation(hook02, 0.7853981633974483F, 0.0F, 0.0F);
        this.wheel1.addChild(this.wheel2);
    }

    @Override
    public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
                       float reloadProgress, ModelTransformationMode transformType, int part, float fireProgress, float chargeProgress, int light,
                       int overlay) {
        ImmutableList.of(this.grip05,  this.main03, this.grip01, this.tank01, this.main07, this.main08, this.main11, this.tank05, this.grip04, this.tank03, this.main05, this.main10, this.grip02, this.main02, this.tank08, this.main04, this.main09, this.main01, this.grip06, this.tank06, this.main12, this.grip03, this.main06, this.tank02,this.tank04, this.tank07).forEach((modelRenderer) -> {
            modelRenderer.render(matrices, vertices, light, overlay);
        });

        GrapplingHookProjectile.GrapplingStatus status = GrapplingHookProjectile.GrapplingStatus.NONE;
        if (entityIn != null && entityIn instanceof ITGExtendedPlayer) {
            ITGExtendedPlayer tge = (ITGExtendedPlayer) entityIn;
            status = tge.getGrapplingStatus();
        }

        //Hook (render when not charging)
        if (status == GrapplingHookProjectile.GrapplingStatus.NONE) {
            ImmutableList.of(this.hook04, this.hook01_1, this.hook03, this.hook01, this.hook05, this.hook06, this.hook02).forEach((modelRenderer) -> {
                modelRenderer.render(matrices, vertices, light, overlay);
            });
            this.wheel1.render(matrices, vertices, light, overlay);
        }else {
            //Wheel (spin when charging)
            float spin_dir = 0.0f;
            if (status == GrapplingHookProjectile.GrapplingStatus.LAUNCHING) spin_dir = 1.0f;
            else spin_dir = -1.0f;
            float t = ((float)(MinecraftClient.getInstance().world.getTime()) + MinecraftClient.getInstance().getTickDelta());
            float spin = ((t * 90.0f) % 360.0f) * spin_dir;
            this.wheel1.render(matrices, vertices, light, overlay, 0f, 0f, spin);
        }
    }
}
