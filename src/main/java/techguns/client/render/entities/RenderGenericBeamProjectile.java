package techguns.client.render.entities;

import java.util.Random;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.Perspective;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import techguns.TGIdentifier;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.math.TGMatrixOps;
import techguns.entities.projectiles.BioGunProjectile;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericBeamProjectile;
import techguns.entities.projectiles.GenericProjectile;
import techguns.util.MathUtil;

public class RenderGenericBeamProjectile extends EntityRenderer<GenericBeamProjectile> {

	protected static final Identifier beamTextureNDR = new TGIdentifier("textures/fx/nukebeam.png");

	public RenderGenericBeamProjectile(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(GenericBeamProjectile entity, float yaw, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {

		// Generic stuff for all beam types
		// Random rand = new Random(entity.getEntityId());
		float prog = ((float) entity.age + tickDelta) / ((float) entity.maxTicks);

		Vec3d pos = entity.getPos();

		float laser_pitch;
		float laser_yaw;
		if (entity.moveWithShooter && entity.getOwner() != null) {

//			if (entity.getOwner() instanceof PlayerEntity
//					&& entity.getOwner() == MinecraftClient.getInstance().getCameraEntity()) {
//				setupViewBobbing(matrixStack, tickDelta);
//			}

			LivingEntity shooter = (LivingEntity) entity.getOwner();
			laser_pitch = MathHelper.lerp(tickDelta, shooter.prevPitch, shooter.pitch);
			laser_yaw = MathHelper.lerp(tickDelta, shooter.prevHeadYaw, shooter.headYaw);
			// pos = new Vec3d(shooter.getX(),
			// shooter.getY()+shooter.getEyeHeight(shooter.getPose()), shooter.getZ());
			
			
			float offsetSide = 0.15F;
			
			float offsetX = 0.35f;
			float offsetY = -0.10000000149011612F;
			float offsetZ = 0.0F;
			if (entity.firePos == EnumBulletFirePos.RIGHT) {
				offsetZ = offsetSide;
			} else if (entity.firePos == EnumBulletFirePos.LEFT) {
				offsetZ = -offsetSide;
			}
			
			
			double posX, posY, posZ;
			
			if (shooter == MinecraftClient.getInstance().getCameraEntity()
					&& MinecraftClient.getInstance().options.getPerspective() == Perspective.FIRST_PERSON) {
				Vec3d camPos = MinecraftClient.getInstance().getCameraEntity().getCameraPosVec(tickDelta);
				posX = camPos.x;
				posY = camPos.y;
				posZ = camPos.z;
				// setupViewBobbing(matrixStack, tickDelta);
				if (shooter instanceof PlayerEntity && MinecraftClient.getInstance().options.bobView) {
					Vec3d vb_offset = getViewBobbingOffset((PlayerEntity)shooter, tickDelta); //.multiply(10);
					//System.out.println("vb_offset:"+vb_offset);
					offsetX+=vb_offset.x;
					offsetY+=vb_offset.y;
					offsetZ+=vb_offset.z;
				}
			}else {	
				posX = MathHelper.lerp(tickDelta, shooter.prevX, shooter.getX());
				posY = MathHelper.lerp(tickDelta, shooter.prevY, shooter.getY())
						+ shooter.getEyeHeight(shooter.getPose());
				posZ = MathHelper.lerp(tickDelta, shooter.prevZ, shooter.getZ());
			}
			
//			double posX = shooter.getX();
//			double posY = shooter.getY() + shooter.getEyeHeight(shooter.getPose());
//			double posZ = shooter.getZ();
			

			// Vec3d v_view = camera.getFocusedEntity().getRotationVecClient();

			Vec3d offset_rot = new Vec3d(offsetX, offsetY, offsetZ).rotateZ((float)(MathUtil.D2R*laser_pitch)).rotateY((float) (MathUtil.D2R*-(laser_yaw+90f)));
			
			pos = new Vec3d(posX, posY, posZ).add(offset_rot); //.multiply(offsetForward));
		} else {
			laser_pitch = entity.laserPitch;
			laser_yaw = entity.laserYaw;
		}

		Vec3d hitVec = traceBeamHit(entity, pos);
		float distance = (float) pos.distanceTo(hitVec);

		if (distance <= 0) {
			distance = (float) entity.speed;
		}

		switch (entity.getProjectileType()) {
		case GenericBeamProjectile.BEAM_TYPE_NDR:
		default:
			float maxWidth = 0.05f;
			renderBeam(entity, prog, maxWidth, pos, laser_pitch, laser_yaw, distance, tickDelta, matrixStack,
					vertexConsumerProvider, light);
		}

	}

	protected Vec3d traceBeamHit(GenericBeamProjectile entity, Vec3d pos_src) {
		//TODO, make sure entity.velocity even makes sense
		Vec3d pos_dst = new Vec3d(pos_src.x + entity.getVelocity().x, pos_src.y + entity.getVelocity().y,
				pos_src.z + entity.getVelocity().z);
		Vec3d pos_dst_final = pos_dst;
		HitResult hitResult = entity.world.raycast(new RaycastContext(pos_src, pos_dst,
				RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));

		if (hitResult.getType() != HitResult.Type.MISS) {
			pos_dst = ((HitResult) hitResult).getPos();
		}

		boolean traceDone = false;
		while (!traceDone) {

			EntityHitResult entityHitResult = entity.getEntityCollision(pos_src, pos_dst);
			if (entityHitResult != null) {
				hitResult = entityHitResult;
			}

			if (hitResult != null && ((HitResult) hitResult).getType() == HitResult.Type.ENTITY) {
				Entity entity_h = ((EntityHitResult) hitResult).getEntity();
				Entity entity_player = entity.getOwner();
				if (entity_h instanceof PlayerEntity && entity_player instanceof PlayerEntity
						&& !((PlayerEntity) entity_player).shouldDamagePlayer((PlayerEntity) entity_h)) {
					hitResult = null;
					entityHitResult = null;
				}
			}

			if (hitResult != null) {
				traceDone = true;
				Vec3d hitVec = hitResult.getPos();
				pos_dst_final = hitVec;
			}

			if (entityHitResult == null) {
				break;
			}

			hitResult = null;
		}
		traceDone = false;

		return pos_dst_final;

	}

	private void setupViewBobbing(MatrixStack matrixStack, float ptt) {

//            PlayerEntity entityplayer = (PlayerEntity)MinecraftClient.getInstance().cameraEntity;
//            float f1 = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
//            float f2 = -(entityplayer.distanceWalkedModified + f1 * ptt);
//            float f3 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * p_78475_1_;
//            float f4 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * p_78475_1_;
//
//            float F1 = -1.0f; //(float) Keybinds.X;
//            float F2 = -1.0f; //(float) Keybinds.Y;
//            
//            GlStateManager.translate(MathHelper.sin(f2 * (float)Math.PI) * f3 * 0.5F * F1, -Math.abs(MathHelper.cos(f2 * (float)Math.PI) * f3) * F2, 0.0F);
//            GlStateManager.rotate(MathHelper.sin(f2 * (float)Math.PI) * f3 * 3.0F, 0.0F, 0.0F, 1.0F);
//            GlStateManager.rotate(Math.abs(MathHelper.cos(f2 * (float)Math.PI - 0.2F) * f3) * 5.0F, 1.0F, 0.0F, 0.0F);
//            GlStateManager.rotate(f4, 1.0F, 0.0F, 0.0F);

		if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) MinecraftClient.getInstance().getCameraEntity();
			float g = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
			float h = -(playerEntity.horizontalSpeed + g * ptt);
			float i = MathHelper.lerp(ptt, playerEntity.prevStrideDistance, playerEntity.strideDistance);
			matrixStack.translate((double) (-MathHelper.sin(h * 3.1415927F) * i * 0.5F),
					(double) (Math.abs(MathHelper.cos(h * 3.1415927F) * i)), 0.0D);
			 matrixStack.translate((double) (MathHelper.sin(h * 3.1415927F) * i * 0.5F), (double) (Math.abs(MathHelper.cos(h * 3.1415927F) * i)), 0.0D);
			 matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-MathHelper.sin(h
			 * 3.1415927F) * i * 3.0F));
			 matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-Math.abs(MathHelper.cos(h
			 * 3.1415927F - 0.2F) * i) * 5.0F));
		}
	}
	
	private Vec3d getViewBobbingOffset(PlayerEntity playerEntity, float ptt) {
		float g = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
		float h = -(playerEntity.horizontalSpeed + g * ptt);
		float i = MathHelper.lerp(ptt, playerEntity.prevStrideDistance, playerEntity.strideDistance);
		Vec3d vec = new Vec3d((double) (-MathHelper.sin(h * 3.1415927F) * i * 0.5F), (double) (Math.abs(MathHelper.cos(h * 3.1415927F) * i)), 0.0D);
		vec = vec.rotateX((float) (MathUtil.D2R * Math.abs(MathHelper.cos(h * 3.1415927F - 0.2F) * i) * 5.0F));
		
		vec = vec.rotateZ((float) (MathUtil.D2R * -MathHelper.sin(h	* 3.1415927F) * i * 3.0F));
		//		matrixStack.translate((double) (-MathHelper.sin(h * 3.1415927F) * i * 0.5F),
//				(double) (Math.abs(MathHelper.cos(h * 3.1415927F) * i)), 0.0D);
//		 matrixStack.translate((double) (MathHelper.sin(h * 3.1415927F) * i * 0.5F), (double) (Math.abs(MathHelper.cos(h * 3.1415927F) * i)), 0.0D);
//		 matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-MathHelper.sin(h
//		 * 3.1415927F) * i * 3.0F));
//		 matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-Math.abs(MathHelper.cos(h
//		 * 3.1415927F - 0.2F) * i) * 5.0F));
		return vec;
	}

	protected void renderBeam(GenericBeamProjectile entity, float prog, float maxWidth, Vec3d pos, float pitch,
			float yaw, float distance, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {
		// float distance = (float) entity.distance;
		float intensity = (float) Math.max(0, Math.min(1, ((Math.sin(Math.sqrt(prog) * Math.PI)))));
		//System.out.println("intensity:" + intensity);
		float width = maxWidth * intensity;

		matrixStack.push();
		
		double ex = MathHelper.lerp(tickDelta, entity.prevX, entity.getX());
		double ey = MathHelper.lerp(tickDelta, entity.prevY, entity.getY());
		double ez = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ());
		
		matrixStack.translate(pos.x-ex, pos.y-ey, pos.z-ez);

		// System.out.println("Entity - pitch:"+entity.pitch+" yaw:"+entity.yaw);
		//System.out.println("BEAM - pitch:" + pitch + " yaw:" + yaw + "distance:" + distance);

		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-(yaw - 90.0F)));
		matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(pitch));
		matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(360.0f * prog));
//		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(
//				MathHelper.lerp(tickDelta, entity.prevYaw, entity.yaw) - 90.0F));
//		matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(
//				MathHelper.lerp(tickDelta, entity.prevPitch, entity.pitch)));

		Matrix4f model_mat = matrixStack.peek().getModel();

		// RENDER BEAM
		double UVscale = 2.0D;
		int numFrames = 17;
		float frametime = 0.5f; // ticks per frame
		float u = (float) (distance / (maxWidth * 8.0 * UVscale));

		int frame = (int) ((((float) entity.age + tickDelta) * frametime) % numFrames);
		float v1 = (1.0f / numFrames) * frame;
		float v2 = (1.0f / numFrames) * (frame + 1);

		VertexConsumer vertexConsumer = vertexConsumerProvider
				.getBuffer(TGRenderHelper.get_fx_renderlayer(getTexture(entity)));

		// matrixStack.push();
		for (int i = 0; i < 4; ++i) {
			TGMatrixOps.rotate(matrixStack, 90f, 1f, 0f, 0f);

			// POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL
			vertexConsumer.vertex(model_mat, -distance, -width, 0.0f).texture((float) (0 + i), v1)
					.color(1.0f, 1.0f, 1.0f, intensity).light(light).next();
			vertexConsumer.vertex(model_mat, 0f, -width, 0.0f).texture((float) (u + i), v1)
					.color(1.0f, 1.0f, 1.0f, intensity).light(light).next();
			vertexConsumer.vertex(model_mat, 0f, width, 0.0f).texture((float) (u + i), v2)
					.color(1.0f, 1.0f, 1.0f, intensity).light(light).next();
			vertexConsumer.vertex(model_mat, -distance, width, 0.0f).texture((float) (0 + i), v2)
					.color(1.0f, 1.0f, 1.0f, intensity).light(light).next();

		}
		// matrixStack.pop();
		matrixStack.pop();
	}

	@Override
	public Identifier getTexture(GenericBeamProjectile entity) {
		switch (entity.getProjectileType()) {
		case GenericBeamProjectile.BEAM_TYPE_NDR:
		default:
			return beamTextureNDR;

		}
	}

}
