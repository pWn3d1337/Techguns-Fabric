package techguns.client.render.entities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import techguns.TGEntities;
import techguns.TGIdentifier;
import techguns.client.render.math.TGMatrixOps;
import techguns.entities.projectiles.GrenadeProjectile;

public class RenderGrenadeProjectile extends EntityRenderer<GrenadeProjectile> {

    private static final Identifier texture_40mm = new TGIdentifier("textures/entity/launchergrenade.png");

    private static final ModelIdentifier grenade_40mm = new ModelIdentifier(new TGIdentifier("item/grenade40mm.obj"), null);

    public RenderGrenadeProjectile(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public Identifier getTexture(GrenadeProjectile entity) {
        switch (entity.getProjectileType()){
            case 0:
            default:
                return texture_40mm;
        }
    }

    @Override
    public void render(GrenadeProjectile entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        TGMatrixOps.rotate(matrices, entity.prevYaw + (entity.yaw-entity.prevYaw)*tickDelta -90.0f, 0F, 1F, 0F);
        TGMatrixOps.rotate(matrices, entity.prevPitch + (entity.pitch-entity.prevPitch)*tickDelta, 0F, 0F, 1F);

        float rot = ((entity.age % 20) +tickDelta) * -0.025f;
        TGMatrixOps.rotate(matrices, 360.0f*rot, 0F, 0F, 1F);

        switch (entity.getProjectileType()){
            case 0:
            default:
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(texture_40mm));
                renderModel_40mm(matrices, vertexConsumer, light);
        }

        matrices.pop();
    }

    protected void renderModel_40mm(MatrixStack matrices, VertexConsumer vertices, int light){
        MatrixStack.Entry entry = matrices.peek();
        BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(grenade_40mm);

        for (BakedQuad quad : model.getQuads(null, null, null)){
            vertices.quad(entry, quad, 1.0f, 1.0f, 1.0f, light, OverlayTexture.DEFAULT_UV);
        }
    }
}
