package techguns.client.particle;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.client.ClientProxy;
import techguns.client.particle.TGParticleSystemType.DirResult;
import techguns.util.EntityCondition;
import techguns.util.MathUtil;

/**
 * A particle system which spawns particles
 */
@Environment(EnvType.CLIENT)
public class TGParticleSystem extends Particle implements ITGParticle {
	
	public TGParticleSystemType type;
	
	public EntityCondition condition = EntityCondition.NONE;
		
	public ArrayList<ModelPart> models;
	public Identifier modelsTexture;
	
	int systemLifetime;
	int spawnDelay = 0;
	
	public float scale =1.0f; //global scale
	public float rotationYaw;
	public float rotationPitch;
	
	public float prevRotationYaw;
	public float prevRotationPitch;
	
	public int initialDelay;
	public int ticksExisted = 0;
	
	public float startSizeRate;
	public float startSizeRateDamping;
	public float startSize = 0.0f;
	
	protected TGParticleStreak prevParticle = null;
	
	public TGFXType attachedFXType = null; //This allows us to set a modified type
	
//	private long timediff = 0;
	
	Entity entity; //parent entity (if attached to an entity)
	public boolean attachToHead = false;
	public Vec3d entityOffset = null;
	TGParticle parent; //parent particle (if attached to a particle)
	
	protected boolean itemAttached=false;
	
	public TGParticleSystem(World worldIn, TGParticleSystemType type, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super((ClientWorld) worldIn, xCoordIn + type.offset.x, yCoordIn+type.offset.y, zCoordIn+type.offset.z);
		this.velocityX = xSpeedIn;
		this.velocityY = ySpeedIn;
		this.velocityZ = zSpeedIn;
		this.type = type;
		this.init();
	}
	
	public TGParticleSystem(Entity entity, TGParticleSystemType type) {
		this(entity.world, type, entity.getX(),entity.getY(),entity.getZ(),0,0,0);
		this.entity = entity;
	}
		

	public TGParticleSystem(World worldIn,TGParticle part, TGParticleSystemType type) {
		this(worldIn, type, part.posX(), part.posY(), part.posZ(),0,0,0);
		this.parent = part;
		//System.out.println("Spawn attached system : " + type.name);
	}

	/*public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		//This is a ParticleSystem which spawns the actual particles. Don't render anything
	}*/
	
	protected void init() {
		this.systemLifetime = MathUtil.randomInt(this.random, type.systemLifetimeMin, type.systemLifetimeMax);
		this.initialDelay = MathUtil.randomInt(this.random, type.initialDelayMin, type.initialDelayMax);
		this.startSizeRate = MathUtil.randomFloat(this.random, type.startSizeRateMin, type.startSizeRateMax) * this.scale;
		this.startSizeRateDamping = MathUtil.randomFloat(this.random, type.startSizeRateDampingMin, type.startSizeRateDampingMax);
		//Minecraft.getMinecraft().effectRenderer.addEffect(this);
		//timediff = System.currentTimeMillis();
		if (this.type.attachedSystem != null && !this.type.attachedSystem.equals("")) {
			if (TGFX.FXList.containsKey(this.type.attachedSystem.toLowerCase())) {
				this.attachedFXType = TGFX.FXList.get(this.type.attachedSystem.toLowerCase());
			}
		}
	}
	
	public void onUpdate() {	

		this.prevRotationPitch = this.rotationPitch;
		this.prevRotationYaw = this.rotationYaw;
		
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		
		if (type == null) {
			this.markDead();
			return;
		}

		if (this.entity!=null && !this.itemAttached){		
	    	if (!condition.evaluate(entity)) {
	    		this.markDead();
	    		return;
	    	}			
			
			if (this.attachToHead && entity instanceof LivingEntity) {
				LivingEntity elb = (LivingEntity) entity;

				this.rotationPitch=elb.getPitch();
				this.rotationYaw=elb.headYaw;
							
				Vec3d offset = type.offset;
				if (this.entityOffset != null) offset = offset.add(this.entityOffset);
				
				offset = offset.rotateX((float) (-elb.getPitch()*MathUtil.D2R));
				offset = offset.rotateY((float) ((-elb.headYaw)*MathUtil.D2R));		
				
				this.x = elb.prevX + offset.x;
				this.y = elb.prevY + elb.getEyeHeight(elb.getPose()) + offset.y;
				this.z = elb.prevZ + offset.z;
			}else {
				this.rotationPitch=entity.getPitch();
				this.rotationYaw=entity.getYaw();
						
				Vec3d offset = type.offset;
				if (this.entityOffset != null) offset = offset.add(this.entityOffset);
				
				offset = offset.rotateX((float) (-entity.getPitch()*MathUtil.D2R));
				offset = offset.rotateY((float) ((-entity.getYaw())*MathUtil.D2R));
				
				this.x = entity.prevX + offset.x;
				this.y = entity.prevY + offset.y;
				this.z = entity.prevZ + offset.z;
			}
			this.velocityX = entity.getVelocity().x;
			this.velocityY = entity.getVelocity().y;
			this.velocityZ = entity.getVelocity().z;
			
		} else if (this.parent != null) {
			this.x=parent.posX() + type.offset.x;
			this.y=parent.posY() + type.offset.y;
			this.z=parent.posZ() + type.offset.z;
		} else {
			this.x+=velocityX;
			this.y+=velocityY;
			this.z+=velocityZ;
		}
		
		if (initialDelay-- > 0) {
			return;
		}
		
		this.startSize = this.startSize+this.startSizeRate;
		this.startSizeRate = (this.startSizeRate * this.startSizeRateDamping);
		
		
		if (spawnDelay-- <= 0) {
			int count = MathUtil.randomInt(random, type.particleCountMin, type.particleCountMax);

			for (int i = 0; i < count; i++) {
				//Get position and motion data
				DirResult dir = this.type.new DirResult();
				Vec3d position = type.volumeType.getPosition(this, dir, i, count);
				//System.out.printf("Dir: %.3f,  %.3f,  %.3f\n", dir[0], dir[1], dir[2]);
				//position = position.addVector(type.offset.x, type.offset.y, type.offset.z);
				Vec3d motion = type.velocityType.getVelocity(this, dir.values);
				
				motion = motion.multiply(this.scale);
				position = position.multiply(this.scale);
				
				//apply ParticleSystem's entity rotation
				
				//System.out.println("ParticleSystemRotation - Pitch: "+this.rotationPitch+" - Yaw: "+this.rotationYaw);
				
				//System.out.println(String.format("Entity pitch : %.3f,  yaw : %.3f", this.rotationPitch, this.rotationYaw));				
				
				motion = motion.rotateX((float) (-this.rotationPitch*MathUtil.D2R));
				motion = motion.rotateY((float) ((-this.rotationYaw)*MathUtil.D2R));
				if (type.volumeType != TGParticleSystemType.VOL_TRAIL) {
					position = position.rotateX((float) (-this.rotationPitch*MathUtil.D2R));
					position = position.rotateY((float) ((-this.rotationYaw)*MathUtil.D2R));		
				}
				//System.out.println("Motion: ("+motion.x+ ", "+motion.y + ", "+ motion.z+")");
				//System.out.println("Position: ("+position.x+ ", "+position.y + ", "+ position.z+")");
				
				//Spawn particle
				double mf = 0.05D; //Per Second instead of Per Tick
				TGParticle particle=null;
				if (this.type.is3d) {
					if (this.models != null && this.modelsTexture != null) {
						ModelPart model = this.models.get(i % this.models.size());
						TGParticle3D particle3d = new TGParticle3D(this.world, this.x+position.x, this.y+position.y, this.z+position.z, motion.x*mf, motion.y*mf, motion.z*mf, this, model, this.modelsTexture);
						addEffect(particle3d);
						particle = particle3d;
					}
					//TODO: 3D and Streak combination
				} else if (this.type.streak) {
					TGParticleStreak particleStreak = new TGParticleStreak(this.world, this.x+position.x, this.y+position.y, this.z+position.z, motion.x*mf, motion.y*mf, motion.z*mf, this);
					if (prevParticle != null) {
						particleStreak.setPrev(prevParticle);
						prevParticle.setNext(particleStreak);
					}
					addEffect(particleStreak);
					prevParticle = particleStreak;
					particle = particleStreak;
				}  else { 
					if(this.itemAttached) {
						//TODO particle itemattached
						//particle = new TGParticleItemAttached(this.world, this.posX+position.x, this.posY+position.y, this.posZ+position.z, motion.x*mf, motion.y*mf, motion.z*mf, this, this.entity);
					} else {
						particle = new TGParticle(this.world, this.x+position.x, this.y+position.y, this.z+position.z, motion.x*mf, motion.y*mf, motion.z*mf, this);
					}
					addEffect(particle);
				}
				if (particle != null && this.attachedFXType != null) {
					List<TGParticleSystem> systems = this.attachedFXType.createParticleSystemsOnParticle(this.world, particle);
					if (systems!=null) {
						for (TGParticleSystem s : systems) {
							s.scale = this.scale;
							addEffect(s);
						}
					}
				}
			}			
			spawnDelay = MathUtil.randomInt(this.random, type.spawnDelayMin, type.spawnDelayMax);
		}
		
		if (this.entity!=null){
			if (!this.entity.isAlive()){
				this.markDead();
			} else if ( this.systemLifetime>0 && ticksExisted>= this.systemLifetime){
				this.markDead();
			}
			
		} else if (this.parent != null) {
			if (!this.parent.isAlive()) {
				this.markDead();
			}
		}else {
			if (ticksExisted >= this.systemLifetime) {
				this.markDead();
			}
		}
		ticksExisted++;
		

	}

	protected void addEffect(ITGParticle s) {
		ClientProxy.get().particleManager.addEffect(s);
	}
	
	/**
	 * Retrieve what effect layer (what texture) the particle should be rendered
	 * with. 0 for the particle sprite sheet, 1 for the main Texture atlas, and 3
	 * for a custom texture
	 */
	public int getFXLayer() {
		return 1;
	}
	
	public double posX() {
		return x;
	}
	
	public double posY() {
		return y;
	}
	
	public double posZ() {
		return z;
	}
	
	public double motionX() {
		return velocityX;
	}
	
	public double motionY() {
		return velocityY;
	}
	
	public double motionZ() {
		return velocityZ;
	}

	@Override
	public Vec3d getPos() {
		return new Vec3d(this.x, this.y, this.z);
	}

	@Override
	public boolean shouldRemove() {
		return !this.isAlive();
	}

	@Override
	public void updateTick() {
		this.onUpdate();
	}


	@Override
	public Box getRenderBoundingBox(float partialTickTime, Entity viewEntity) {
		//DOESN'T MATTER, SYSTEM DOES NOT RENDER STUFF ANYWAY
	    float s = 0.5f; 
		return new Box(this.x-s, this.y-s, this.z-s, this.x+s, this.y+s, this.z+s);
	}

	@Override
	public double getDepth() {
		return 0;
	}

	@Override
	public void setDepth(double depth) {
	}

	@Override
	public void setItemAttached() {
	}

	@Override
	public void markDead() {
		super.markDead();
		this.entity=null;
	}


	@Override
	public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
		//nothing to do
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.NO_RENDER;
	}

	@Override
	public void doRender(Immediate vertexConsumerProvider, Entity entityIn, float partialTickTime, float rotX,
			float rotZ, float rotYZ, float rotXY, float rotXZ, MatrixStack matrices, Camera camera) {
		//nothing to do
		
	}

}