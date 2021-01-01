package techguns.client.render.entities;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.Perspective;
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
import net.minecraft.util.Pair;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import techguns.TGIdentifier;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.math.TGMatrixOps;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericBeamProjectile;
import techguns.util.MathUtil;

public class RenderGenericBeamProjectile extends EntityRenderer<GenericBeamProjectile> {

	
	public static HashMap<Byte, BeamRenderParams> BeamRenderParamDict;
	
	static {
		BeamRenderParamDict = new HashMap<Byte, BeamRenderParams>();
		BeamRenderParamDict.put(GenericBeamProjectile.BEAM_TYPE_NDR, new BeamRenderParams(null, "textures/fx/nukebeam.png", null, 17, 0.5f, 2.0f));
		BeamRenderParamDict.put(GenericBeamProjectile.BEAM_TYPE_LASER, new BeamRenderParams("textures/fx/laser3_start.png", "textures/fx/laser3.png", null, 1, 1.0f, 2.0f));
	}

	public RenderGenericBeamProjectile(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(GenericBeamProjectile entity, float yaw, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {

		// Generic stuff for all beam types
		Random rand = new Random(entity.getEntityId());
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
		
		// float distance = (float) entity.distance;
		matrixStack.push();
		setupBeamTransforms(entity, pos, laser_pitch, laser_yaw, prog, matrixStack, tickDelta);

		float u1, u2, v1, v2;
		BeamRenderParams params = BeamRenderParamDict.get(entity.getProjectileTypeEnum());
		if (params != null) {
			Pair<Vec2f, Vec2f> uv = params.getUV(distance, entity.age, tickDelta);
			u1 = uv.getLeft().x;
			v1 = uv.getLeft().y;
			u2 = uv.getRight().x;
			v2 = uv.getRight().y;
		} else {
			u1 = 0f;
			u2 = 1f;
			v1 = 0f;
			v2 = 1f;
		}
		switch (entity.getProjectileType()) {
			case GenericBeamProjectile.BEAM_TYPE_NDR:
				float maxWidth = 0.05f;
				renderBeam(entity, rand, prog, maxWidth, distance, u1, u2, v1, v2, tickDelta, matrixStack,
						vertexConsumerProvider, light);
				renderSpiralEffect(entity, rand, prog, maxWidth, distance, u1, u2, v1, v2, tickDelta, matrixStack,
						vertexConsumerProvider, light);
				break;
			default:
				renderBeam(entity, rand, prog, 0.05f, distance, u1, u2, v1, v2, tickDelta, matrixStack,
						vertexConsumerProvider, light);
				break;

		}
		matrixStack.pop();

	}

	private void setupBeamTransforms(Entity entity, Vec3d pos, float pitch, float yaw, float prog, MatrixStack matrixStack, float tickDelta) {
	
		double ex = MathHelper.lerp(tickDelta, entity.prevX, entity.getX());
		double ey = MathHelper.lerp(tickDelta, entity.prevY, entity.getY());
		double ez = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ());
		
		matrixStack.translate(pos.x-ex, pos.y-ey, pos.z-ez);

		// System.out.println("Entity - pitch:"+entity.pitch+" yaw:"+entity.yaw);
		//System.out.println("BEAM - pitch:" + pitch + " yaw:" + yaw + "distance:" + distance);

		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-(yaw - 90.0F)));
		matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(pitch));
		matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(360.0f * prog));
		
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

	
	private Vec3d getViewBobbingOffset(PlayerEntity playerEntity, float ptt) {
		float g = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
		float h = -(playerEntity.horizontalSpeed + g * ptt);
		float i = MathHelper.lerp(ptt, playerEntity.prevStrideDistance, playerEntity.strideDistance);
		Vec3d vec = new Vec3d((double) (-MathHelper.sin(h * 3.1415927F) * i * 0.5F), (double) (Math.abs(MathHelper.cos(h * 3.1415927F) * i)), 0.0D);
		vec = vec.rotateX((float) (MathUtil.D2R * Math.abs(MathHelper.cos(h * 3.1415927F - 0.2F) * i) * 5.0F));
		
		vec = vec.rotateZ((float) (MathUtil.D2R * -MathHelper.sin(h	* 3.1415927F) * i * 3.0F));

		return vec;
	}


	protected void renderBeam(GenericBeamProjectile entity, Random rand, float prog, float maxWidth, float distance,
			float u1, float u2, float v1, float v2, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {

		float intensity = (float) Math.max(0, Math.min(1, ((Math.sin(Math.sqrt(prog) * Math.PI)))));
		float width = maxWidth * intensity;

		VertexConsumer vertexConsumer = vertexConsumerProvider
				.getBuffer(TGRenderHelper.get_fx_renderlayer(getTexture(entity)));
		Matrix4f model_mat = matrixStack.peek().getModel();
		
		matrixStack.push();
		for (int i = 0; i < 4; ++i) {
			TGMatrixOps.rotate(matrixStack, 90f, 1f, 0f, 0f);

			// POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL
			vertexConsumer.vertex(model_mat, -distance, -width, 0.0f).texture((float) u1, v1)
					.color(1.0f, 1.0f, 1.0f, intensity).light(light).next();
			vertexConsumer.vertex(model_mat, 0f, -width, 0.0f).texture((float) u2, v1)
					.color(1.0f, 1.0f, 1.0f, intensity).light(light).next();
			vertexConsumer.vertex(model_mat, 0f, width, 0.0f).texture((float) u2, v2)
					.color(1.0f, 1.0f, 1.0f, intensity).light(light).next();
			vertexConsumer.vertex(model_mat, -distance, width, 0.0f).texture((float) u1, v2)
					.color(1.0f, 1.0f, 1.0f, intensity).light(light).next();

		}
		matrixStack.pop();
	}

	
	protected void renderSpiralEffect(GenericBeamProjectile entity, Random rand, float prog, float maxWidth, float distance,
			float u1, float u2, float v1, float v2, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {
		
		float intensity = (float) Math.max(0, Math.min(1, (1.0-(Math.cos(Math.sqrt(prog) *2.0* Math.PI)))*0.5));
		
		float maxDistance = 15.0f;
		distance = (float) Math.min(distance, maxDistance); // SHORTEN LENGTH FOR SPIRAL
		int segments = (int) (distance * 4.0);
		float w = 0.15f; // width/2
		float radius = 0.0f;
		float angle = (float) (Math.PI / 8.0);
		float prevX = 0.0f;
		float prevY = 0.0f;
		float prevZ = 0.0f;
		float prevu = 0.0f;
		float cos2 = (float) (Math.cos((Math.PI - angle) * 0.5) * 2.0);
		float angleOffset = (float) (rand.nextDouble() * Math.PI * 2.0);
		float UVscale = 8.0f;
		// -
		
		VertexConsumer vertexConsumer = vertexConsumerProvider
				.getBuffer(TGRenderHelper.get_fx_renderlayer(getTexture(entity)));
		
		matrixStack.push();
		
		matrixStack.translate(-0.30, 0, 0);
		Matrix4f model_mat = matrixStack.peek().getModel();
		
		float d = distance / segments;
		for (int i = 0; i < segments; i++) {
			//float x = -d * i;

			float iprog = (float) i / (maxDistance*4.0f);
			float x = (float) -Math.pow((iprog)*maxDistance, 2.0);
			
			//radius = (float) (0.05 * (1 - Math.cos(2.0 * Math.sqrt(iprog) * Math.PI)) * 8.0);
			radius = 0.20f * (float) Math.max(0, Math.min(1, (1.0-(Math.cos(Math.sqrt(iprog) *2.0* Math.PI))))*0.5);
			//radius = 0.5f;
			
			Vec2f yz = MathUtil.polarOffsetXZ(0, 0, (float) radius, (float) (angleOffset - angle * i));
			float y = yz.x;
			float z = yz.y;
			float u = prevu + (cos2 * radius / UVscale);


			// POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL
			vertexConsumer.vertex(model_mat, prevX - w, prevY, prevZ).texture(prevu, v2).color(1.0f, 1.0f, 1.0f, intensity).light(light).next();
			vertexConsumer.vertex(model_mat, x - w, y, z).texture(u, v2).color(1.0f, 1.0f, 1.0f, intensity).light(light).next();
			vertexConsumer.vertex(model_mat, x + w, y, z).texture(u, v1).color(1.0f, 1.0f, 1.0f, intensity).light(light).next();
			vertexConsumer.vertex(model_mat, prevX + w, prevY, prevZ).texture(prevu, v1).color(1.0f, 1.0f, 1.0f, intensity).light(light).next();

			prevX = x;
			prevY = y;
			prevZ = z;
			prevu = u;
		}
		
		matrixStack.pop();
	}
	
	@Override
	public Identifier getTexture(GenericBeamProjectile entity) {
		if (BeamRenderParamDict.containsKey(entity.getProjectileType())) {
			return BeamRenderParamDict.get(entity.getProjectileType()).beamTexture;
		}else {
			return null;
		}
	}
	
	protected static class BeamRenderParams{
		Identifier beamTextureStart = null;
		Identifier beamTexture = null;
		Identifier beamTextureEnd = null;	
		int numFrames;
		float frametime;
		float uscale;
		
		public BeamRenderParams(String beamTextureStart, String beamTexture, String beamTextureEnd,
				int numFrames, float frametime, float uscale) {
			if (beamTextureStart != null) this.beamTextureStart = new TGIdentifier(beamTextureStart);
			if (beamTexture != null) this.beamTexture = new TGIdentifier(beamTexture);
			if (beamTextureEnd != null) this.beamTextureEnd = new TGIdentifier(beamTextureEnd);
			this.numFrames = numFrames;
			this.frametime = frametime;
			this.uscale = uscale;
		}
		
		
		protected Pair<Vec2f, Vec2f> getUV(float distance, int entityAge, float tickDelta) {
	        
			float u1 = 0;
			float u2 = distance / uscale;
	        	
	        int frame = (int) ((((float)entityAge+tickDelta) * frametime) % numFrames);
	        float v1 = (1.0f / (float)numFrames) * (float)frame;
	        float v2 = (1.0f / (float)numFrames) * (float)(frame+1);
	        return new Pair<Vec2f, Vec2f>(new Vec2f(u1, v1), new Vec2f(u2, v2));
		}
		
	}

}
