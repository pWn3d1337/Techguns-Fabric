package techguns.client.particle;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public interface ITGParticle {

	public Vec3d getPos();
	
	public boolean shouldRemove();
	public void updateTick();
	

	public void doRender(VertexConsumerProvider.Immediate vertexConsumerProvider, Entity entityIn, float partialTickTime, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ, MatrixStack matrices, Camera camera);
	//public void doRender(VertexConsumerProvider.Immediate vertexConsumerProvider, Camera camera, float partialTickTime);

	
	public Box getRenderBoundingBox(float ptt, Entity viewEnt);
	
	public default boolean doNotSort() {
		return false;
	}
	
	public double getDepth();
	
	public void setDepth(double depth);
	public void setItemAttached();

}
