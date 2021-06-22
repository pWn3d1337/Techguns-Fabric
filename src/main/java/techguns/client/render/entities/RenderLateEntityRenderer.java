package techguns.client.render.entities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Render an entity at a Late point, intended to fix alpha blending
 * @param <T>
 */
public abstract class RenderLateEntityRenderer<T extends Entity> extends EntityRenderer<T> {

    protected static Queue<Entity> renderQueue = new ConcurrentLinkedQueue<>();

    public static void renderEntities (VertexConsumerProvider.Immediate vertexConsumerProvider, Entity cameraEntity, float tickDelta, MatrixStack matrices, Camera camera, Matrix4f matrix4f){

        EntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        renderQueue.stream().forEach( entity -> {
            double d2 = MathHelper.lerp((double)tickDelta, entity.lastRenderX, entity.getX());
            double e2 = MathHelper.lerp((double)tickDelta, entity.lastRenderY, entity.getY());
            double f2 = MathHelper.lerp((double)tickDelta, entity.lastRenderZ, entity.getZ());
            float yaw = MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw());

            double x = d2 - camera.getPos().x;
            double y = e2 - camera.getPos().y;
            double z = f2 - camera.getPos().z;

            RenderLateEntityRenderer entityRenderer = (RenderLateEntityRenderer) dispatcher.getRenderer(entity);
            Vec3d vec3d = entityRenderer.getPositionOffset(entity, tickDelta);
            double d = x + vec3d.getX();
            double e = y + vec3d.getY();
            double f = z + vec3d.getZ();
            matrices.push();
            matrices.translate(d, e, f);
            entityRenderer.renderLate(entity, yaw, tickDelta, matrices, vertexConsumerProvider, dispatcher.getLight(entity, tickDelta));
            vertexConsumerProvider.draw(entityRenderer.getRenderLayer(entity));
            matrices.pop();
        });
        renderQueue.clear();
    }

    public RenderLateEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    protected abstract RenderLayer getRenderLayer(T entity);

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        renderQueue.add(entity);
    }

    public abstract void renderLate(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

}
