package techguns.client.render.entities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import techguns.TGIdentifier;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.math.TGMatrixOps;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericBeamProjectile;
import techguns.util.MathUtil;

import java.util.HashMap;
import java.util.Random;

public class RenderGenericBeamProjectile extends RenderLateEntityRenderer<GenericBeamProjectile> {

	
	public static HashMap<Byte, BeamRenderParams> BeamRenderParamDict;
	
	static {
		BeamRenderParamDict = new HashMap<Byte, BeamRenderParams>();
		BeamRenderParamDict.put(GenericBeamProjectile.BEAM_TYPE_NDR, new BeamRenderParams( "textures/fx/nukebeam.png",  17, 0.5f, 2.0f, true, false));
		BeamRenderParamDict.put(GenericBeamProjectile.BEAM_TYPE_LASER, new BeamRenderParams("textures/fx/laser3_combined.png",  1, 1.0f, 2.0f, false, true));
		BeamRenderParamDict.put(GenericBeamProjectile.BEAM_TYPE_TESLA, new BeamRenderParams("textures/fx/laser_blue.png",  1, 1.0f, 2.0f, false, false));
	}

	public RenderGenericBeamProjectile(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	protected RenderLayer getRenderLayer(GenericBeamProjectile entity) {
		return TGRenderHelper.get_fx_renderlayer(getTexture(entity));
	}

	@Override
	public void renderLate(GenericBeamProjectile entity, float yaw, float tickDelta, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int light) {

		//tickDelta = 0.0f;

		//System.out.println("tD: "+tickDelta);

		// Generic stuff for all beam types
		Random rand = new Random(entity.getId());
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
			laser_pitch = MathHelper.lerp(tickDelta, shooter.prevPitch, shooter.getPitch());
			laser_yaw = MathHelper.lerp(tickDelta, shooter.prevHeadYaw, shooter.headYaw);
			System.out.println("pitch: "+laser_pitch+", yaw: " + laser_yaw);
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

//				posX = MathHelper.lerp(tickDelta, shooter.prevX, shooter.getX());
//				posY = MathHelper.lerp(tickDelta, shooter.prevY, shooter.getY())
//						+ shooter.getEyeHeight(shooter.getPose());
//				posZ = MathHelper.lerp(tickDelta, shooter.prevZ, shooter.getZ());

				// setupViewBobbing(matrixStack, tickDelta);

				if (shooter instanceof PlayerEntity && MinecraftClient.getInstance().options.getBobView().getValue()) {
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
			//pos = new Vec3d(posX, posY, posZ); //.multiply(offsetForward));

			//System.out.println("pos: ("+pos+")");
		} else {
			laser_pitch = entity.laserPitch;
			laser_yaw = entity.laserYaw;
		}

		Vec3d hitVec = traceBeamHit(entity, pos);
		float distance = (float) pos.distanceTo(hitVec);

		if (distance <= 0) {
			distance = (float) entity.speed;
		}
		BeamRenderParams params = BeamRenderParamDict.get(entity.getProjectileType());

		// float distance = (float) entity.distance;
		matrixStack.push();
		setupBeamTransforms(entity, pos, laser_pitch, laser_yaw, prog, matrixStack, tickDelta, params.spinning);

		float u1, u2, v1, v2;
		if (params != null) {
			Pair<Vec2f, Vec2f> uv = params.getUV(distance, entity.age, tickDelta);
			u1 = uv.getLeft().x;
			v1= uv.getLeft().y;
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
			case GenericBeamProjectile.BEAM_TYPE_TESLA:
				renderLightning(entity, rand, prog, 0.2f, -distance, u1, u2, v1, v2, tickDelta, matrixStack,
						vertexConsumerProvider, light);
				break;
			default:
				renderBeam(entity, rand, prog, 0.05f, distance, u1, u2, v1, v2, tickDelta, matrixStack,
						vertexConsumerProvider, light);
//				if (params.beamTextureStart != null) {
//					renderBeamStart(entity, rand, prog, 0.05f, distance, 1f, 0f, 1f, 0f, tickDelta, matrixStack,
//							vertexConsumerProvider, light);
//				}
				break;

		}
		matrixStack.pop();

	}

	protected void setupBeamTransforms(Entity entity, Vec3d pos, float pitch, float yaw, float prog, MatrixStack matrixStack, float tickDelta, boolean spinning) {

		double ex = MathHelper.lerp(tickDelta, entity.prevX, entity.getX());
		double ey = MathHelper.lerp(tickDelta, entity.prevY, entity.getY());
		double ez = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ());

		matrixStack.translate(pos.x-ex, pos.y-ey, pos.z-ez);

		//System.out.println("Entity - pitch:"+entity.getPitch()+" yaw:"+entity.getYaw()
		//System.out.println("BEAM - pitch: " + pitch + " - yaw: " + yaw + " - pos = ("+(pos.x-ex)+", "+(pos.y-ey)+", "+(pos.z-ez)+")");

		// System.out.println("tD: "+tickDelta);

		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-(yaw - 90.0F)));
		matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(pitch));
		if(spinning) {
			matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(360.0f * prog));
		}
		
	}

	protected Vec3d traceBeamHit(GenericBeamProjectile entity, Vec3d pos_src) {
		Vec3d scaled_velocity = entity.getRotationVector(entity.laserPitch, entity.laserYaw).normalize().multiply(entity.speed);
		Vec3d pos_dst = new Vec3d(pos_src.x + scaled_velocity.x, pos_src.y + scaled_velocity.y,
				pos_src.z + scaled_velocity.z);

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

	
	protected Vec3d getViewBobbingOffset(PlayerEntity playerEntity, float ptt) {
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

		BeamRenderParams params = BeamRenderParamDict.get(entity.getProjectileType());

		float beamStartLength = 0.0f;
		if (params.beamStart) {
			beamStartLength = maxWidth * params.uscale * 4.0f; //Not sure if this makes sense
		}
        boolean drawBeam = distance > beamStartLength;
        beamStartLength = Math.min(beamStartLength, distance);

		VertexConsumer vertexConsumer = vertexConsumerProvider
				.getBuffer(this.getRenderLayer(entity));

		matrixStack.push();

        for (int i = 0; i < 4; ++i) {
            TGMatrixOps.rotate(matrixStack, 90f, 1f, 0f, 0f);

            Matrix4f model_mat = matrixStack.peek().getPositionMatrix();

            if (drawBeam) {
                // POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL
                vertexConsumer.vertex(model_mat, -beamStartLength, -width, 0.0f)
                        .color(1.0f, 1.0f, 1.0f, intensity).texture((float) u1, v1).light(light).next();
                vertexConsumer.vertex(model_mat, -distance, -width, 0.0f)
                        .color(1.0f, 1.0f, 1.0f, intensity).texture((float) u2, v1).light(light).next();
                vertexConsumer.vertex(model_mat, -distance, width, 0.0f)
                        .color(1.0f, 1.0f, 1.0f, intensity).texture((float) u2, v2).light(light).next();
                vertexConsumer.vertex(model_mat, -beamStartLength, width, 0.0f)
                        .color(1.0f, 1.0f, 1.0f, intensity).texture((float) u1, v2).light(light).next();
            }


            // POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL
            vertexConsumer.vertex(model_mat, 0.0f, -width, 0.0f)
                    .color(1.0f, 1.0f, 1.0f, intensity).texture((float) 0f, v1+0.5f).light(light).next();
            vertexConsumer.vertex(model_mat, -beamStartLength, -width, 0.0f)
                    .color(1.0f, 1.0f, 1.0f, intensity).texture((float) 1f, v1+0.5f).light(light).next();
            vertexConsumer.vertex(model_mat, -beamStartLength, width, 0.0f)
                    .color(1.0f, 1.0f, 1.0f, intensity).texture((float) 1f, v2+0.5f).light(light).next();
            vertexConsumer.vertex(model_mat, 0.0f, width, 0.0f)
                    .color(1.0f, 1.0f, 1.0f, intensity).texture((float) 0f, v2+0.5f).light(light).next();

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
				.getBuffer(this.getRenderLayer(entity));
		
		matrixStack.push();
		
		matrixStack.translate(-0.30, 0, 0);
		Matrix4f model_mat = matrixStack.peek().getPositionMatrix();
		
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
			vertexConsumer.vertex(model_mat, prevX - w, prevY, prevZ).color(1.0f, 1.0f, 1.0f, intensity).texture(prevu, v2).light(light).next();
			vertexConsumer.vertex(model_mat, x - w, y, z).color(1.0f, 1.0f, 1.0f, intensity).texture(u, v2).light(light).next();
			vertexConsumer.vertex(model_mat, x + w, y, z).color(1.0f, 1.0f, 1.0f, intensity).texture(u, v1).light(light).next();
			vertexConsumer.vertex(model_mat, prevX + w, prevY, prevZ).color(1.0f, 1.0f, 1.0f, intensity).texture(prevu, v1).light(light).next();

			prevX = x;
			prevY = y;
			prevZ = z;
			prevu = u;
		}
		
		matrixStack.pop();
	}

	protected void renderLightning(GenericBeamProjectile laser, Random rand, float prog, float maxWidth, float distance,
									  float u1, float u2, float v1, float v2, float tickDelta, MatrixStack matrixStack,
								   VertexConsumerProvider vertexConsumerProvider, int light) {

		final int SIN_COUNT = 5; // Number of overlapping sin functions
		final float WIDTH = 0.5f;
		final float SIN_DISTANCE = 10.0f; //ideal distance for one sinus curve;

		float offset = 0.20f; // Distance per bolt vertex

		int maxTicks = laser.maxTicks;

		double[] dY = new double[SIN_COUNT];
		double[] dZ = new double[SIN_COUNT];

		for (int i = 0; i < SIN_COUNT; i++) {
			//TODO: Y/Z random vectors with fixed length, or whatever
			dY[i] = 0.5-rand.nextDouble(); //fixed for this bolt
			dZ[i] = 0.5-rand.nextDouble();
		}

		int count = (int) Math.round((Math.abs(distance) / offset));
		offset = (distance / (float) count);

		float xOffset = 0.0f;

		int xreps = Math.max(1, (int) Math.round(distance / SIN_DISTANCE)); //TODO: get modulo as additional y/z scale?
		float xprev = 0f, yprev = 0f, zprev = 0f, widthprev = 1.0f, alphaprev = 1.0f;

		double u = (distance / maxWidth) * 2.0D;

		VertexConsumer vertexConsumer = vertexConsumerProvider
				.getBuffer(this.getRenderLayer(laser));

		matrixStack.push();

		//TGMatrixOps.rotate(matrixStack, laser.laserYaw-90F, 0.0F, 1.0F, 0.0F);
		//TGMatrixOps.rotate(matrixStack, laser.laserPitch, 0.0F, 0.0F, 1.0F);

		for (int i = 0; i <= count; i++) {
			float d = (float)i/(float)count; //distance progress (0-1)


			float x = xOffset + (float)i*offset;
			float y = 0;
			float z = 0;
			double randomness = 0.00;

			if (i > 1) {
				for (int j = 1; j <= SIN_COUNT; j++) {
					double yfactor = ((rand.nextDouble()-0.5) + (prog*dY[j-1] *1.0)) * (2.0/(double)j);
					double zfactor = ((rand.nextDouble()-0.5) + (prog*dZ[j-1] *1.0)) * (2.0/(double)j);
					y+= Math.sin((d * Math.PI) * (double)( j * xreps))*yfactor;
					z+= Math.sin((d * Math.PI) * (double)( j * xreps))*zfactor;
				}
			}


			float pulse = (float) (1.0f - Math.sqrt(Math.abs(prog-d)*2.0f));
			//1-WURZEL(ABS(B2-C2))

			//System.out.printf("X/Y/Z: (%2.2f/%2.2f/%2.2f)\n",x,y,z);
			float width =  Math.max(0.0f, WIDTH*pulse); //WIDTH+(WIDTH*10.0*pulse);
			if (i >= 1) {
				drawSegment(matrixStack, vertexConsumer, xprev, yprev, zprev, x,y,z, widthprev, width, alphaprev, pulse, light);
			}
			widthprev = width;
			alphaprev = pulse;
			xprev = x;
			yprev = y;
			zprev = z;
		}

		matrixStack.pop();
	}

	void drawSegment(MatrixStack matrixStack, VertexConsumer vertexConsumer, float x1, float y1, float z1, float x2, float y2, float z2, float width1, float width2, float a1, float a2, int light) {

		double scale = 0.5;
		y1*=scale;
		y2*=scale;
		z1*=scale;
		z2*=scale;
		width1*=scale;
		width2*=scale;

		matrixStack.push();
		TGMatrixOps.rotate(matrixStack, 45.0f, 1.0f, 0.0f, 0.0f);

		Matrix4f modelMat = matrixStack.peek().getPositionMatrix();

		//set alpha1
		vertexConsumer.vertex(modelMat, x1,  y1- width1,  z1).color(1f,1f,1f, a1).texture(0,0).light(light).next();
		vertexConsumer.vertex(modelMat, x1,  y1+ width1,  z1).color(1f,1f,1f, a1).texture(0,1).light(light).next();
		vertexConsumer.vertex(modelMat, x2,  y2+ width2,  z2).color(1f,1f,1f, a1).texture(1,1).light(light).next();
		vertexConsumer.vertex(modelMat, x2,  y2- width2,  z2).color(1f,1f,1f, a1).texture(1,0).light(light).next();

		//set alpha2
		vertexConsumer.vertex(modelMat, x1,  y1, z1-width1).color(1f,1f,1f, a1).texture(0,0).light(light).next();
		vertexConsumer.vertex(modelMat, x1,  y1, z1+width1).color(1f,1f,1f, a1).texture(0,1).light(light).next();
		vertexConsumer.vertex(modelMat, x2,  y2, z2+width2).color(1f,1f,1f, a1).texture(1,1).light(light).next();
		vertexConsumer.vertex(modelMat, x2,  y2, z2-width2).color(1f,1f,1f, a1).texture(1,0).light(light).next();

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
		Identifier beamTexture = null;
		int numFrames;
		float frametime;
		float uscale;
		boolean spinning; //rotate around beam axis
		boolean beamStart;
		
		public BeamRenderParams(String beamTexture,
				int numFrames, float frametime, float uscale, boolean spinning, boolean beamStart) {
			if (beamTexture != null) this.beamTexture = new TGIdentifier(beamTexture);
			this.numFrames = numFrames;
			this.frametime = frametime;
			this.uscale = uscale;
			this.spinning = spinning;
			this.beamStart = beamStart;
		}
		
		
		protected Pair<Vec2f, Vec2f> getUV(float distance, int entityAge, float tickDelta) {
	        
			float u1 = 0;
			float u2 = distance / uscale; //TODO: I think this is dumb

            float v1, v2;
            if (beamStart) {
                v1 = 0f;
                v2 = 0.5f;
            }else {
                int frame = (int) ((((float) entityAge + tickDelta) * frametime) % numFrames);
                v1 = (1.0f / (float) numFrames) * (float) frame;
                v2 = (1.0f / (float) numFrames) * (float) (frame + 1);
            }
            return new Pair<Vec2f, Vec2f>(new Vec2f(u1, v1), new Vec2f(u2, v2));
		}
		
	}

}
