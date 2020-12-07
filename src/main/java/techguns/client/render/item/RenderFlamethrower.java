package techguns.client.render.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import techguns.client.models.ModelMultipart;
import techguns.client.render.fx.ScreenEffect;

public class RenderFlamethrower extends RenderGunBase90{
    public RenderFlamethrower(ModelMultipart model, Identifier texture) {
        super(model, texture);
    }

    public RenderFlamethrower(ModelMultipart model, int parts, Identifier texture) {
        super(model, parts, texture);
    }

    @Override
    protected void drawIdleFx(LivingEntity entity, MatrixStack matrices, VertexConsumerProvider verticesProvider, boolean leftHand) {
        if (entity !=null && !entity.isSubmergedInWater()) {
            float p = ((float)(MinecraftClient.getInstance().world.getTime() % 20) / 20.0f);
            float x= leftHand?this.muzzleFX_x_l:this.muzzleFX_x_r;
            ScreenEffect.FlamethrowerMuzzleFlame.doRender(matrices, verticesProvider, p, x, this.muzzleFX_y, this.muzzleFX_z, this.muzzleFX_scale*0.5f, false);
        }
    }

    @Override
    protected void drawIdleFx3P(LivingEntity entity, MatrixStack matrices, VertexConsumerProvider verticesProvider, boolean leftHand) {
        if (entity!=null && !entity.isSubmergedInWater()) {
            float p = ((float)(MinecraftClient.getInstance().world.getTime()  % 20) / 20.0f);
            float x= leftHand?this.muzzleFX_x_l:this.muzzleFX_x_r;
            ScreenEffect.FlamethrowerMuzzleFlame.doRender(matrices, verticesProvider, p, 0, this.muzzleFX_3p_y, this.muzzleFX_3p_z, this.muzzleFX_scale*0.5f*this.muzzleFX_3p_scale, true);
        }
    }
}
