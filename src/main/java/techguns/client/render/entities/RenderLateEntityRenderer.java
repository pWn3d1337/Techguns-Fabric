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

        //System.out.println("tD: "+tickDelta);

        EntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        renderQueue.stream().forEach( entity -> {

            RenderLateEntityRenderer entityRenderer = (RenderLateEntityRenderer) dispatcher.getRenderer(entity);
            float yaw = MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw());

            // This code is shit:

//            double px = MathHelper.lerp((double) tickDelta, entity.lastRenderX, entity.getX());
//            double py = MathHelper.lerp((double) tickDelta, entity.lastRenderY, entity.getY());
//            double pz = MathHelper.lerp((double) tickDelta, entity.lastRenderZ, entity.getZ());
//
//            double x = px - camera.getPos().x;
//            double y = py - camera.getPos().y;
//            double z = pz - camera.getPos().z;

            double x = MathHelper.lerp((double)tickDelta, entity.prevX, entity.getX()) - camera.getPos().x;
            double y = MathHelper.lerp((double)tickDelta, entity.prevY, entity.getY()) - camera.getPos().y;
            double z = MathHelper.lerp((double)tickDelta, entity.prevZ, entity.getZ()) - camera.getPos().z;

            Vec3d vec3d = entityRenderer.getPositionOffset(entity, tickDelta);
            double x_ = x + vec3d.getX();
            double y_ = y + vec3d.getY();
            double z_ = z + vec3d.getZ();
            matrices.push();
            matrices.translate(x_, y_, z_);

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
