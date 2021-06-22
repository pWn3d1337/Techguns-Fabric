package techguns.entities.projectiles;

import net.minecraft.block.Material;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.client.ClientProxy;
import techguns.client.deatheffects.DeathEffectHandler.GoreData;
import techguns.client.particle.TGParticleSystem;

public class FlyingGibs extends Entity{

	public ModelPart modelPart = null;
	public Identifier texture = null;
	
	public int maxTimeToLive = 200;
	public int timeToLive = 200;

	public double gravity = 0.029999999329447746D;
	public float size;
	public Vec3d rotationAxis;
	//public int bodypart;
	public int hitGroundTTL = 0;
	
	//the exploding entity
	public LivingEntity entity;
	
	public GoreData data;
	
	public TGParticleSystem trail_system;
	
	//public EntityDT entityDT;
	
	public FlyingGibs(EntityType<?> type, World world, LivingEntity entity, GoreData data, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float size, ModelPart modelPart, Identifier texture /*,int bodypart*/) {
		super(type, world);
		this.setPos(posX, posY, posZ);
		this.setVelocity(motionX, motionY, motionZ);
		this.size = size;
		this.maxTimeToLive = 75 + this.random.nextInt(50);
		this.timeToLive = maxTimeToLive;
		this.entity = entity;
		//this.bodypart = bodypart;
		//this.entityDT = entityDT;
		this.rotationAxis = new Vec3d(random.nextDouble(), random.nextDouble(), random.nextDouble());
		this.data = data;
		
		this.modelPart = modelPart;
		this.texture = texture;
		
		trail_system = new TGParticleSystem(this, data.type_trail);
		ClientProxy.get().particleManager.addEffect(trail_system);
	}


	public FlyingGibs(EntityType<FlyingGibs> type, World world) {
		super(type, world);
	}


	@Override
	public void tick()
    {
        super.tick();

        if (this.timeToLive > 0)
        {
            --timeToLive;
        }else {
        	//this.destroy(); //FIXME: 1.17 check if correct?
        	this.remove(RemovalReason.DISCARDED);
        }

        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();

        float velX = (float) this.getVelocity().x;
        float velY = (float) (this.getVelocity().y - this.gravity);
        float velZ = (float) this.getVelocity().z;

        if (this.world.getBlockState(new BlockPos(this.getPos())).getMaterial() == Material.LAVA)
        {
        	velX = 0.20000000298023224F;
        	velY = (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
        	velZ = (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
            this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
        }

        this.setVelocity(velX, velY, velZ);
        this.move(MovementType.SELF, this.getVelocity());

        float f = 0.98F;

        if (this.onGround)
        {
        	//System.out.println("onGround.");
        	if (hitGroundTTL == 0) {
        		hitGroundTTL = timeToLive;
                trail_system.markDead();
        	}
            f = (float) (this.world.getBlockState(new BlockPos(this.getPos())).getBlock().getSlipperiness() * 0.98);

        }

        velX *= f;
        velY *= 0.9800000190734863F;
        velZ *= f;

        if (this.onGround)
        {
        	velY *= -0.8999999761581421D;
        }
        
        this.setVelocity(velX, velY, velZ);
    }


	@Override
	protected void initDataTracker() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void readCustomDataFromNbt(NbtCompound tag) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void writeCustomDataToNbt(NbtCompound tag) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Packet<?> createSpawnPacket() {
	    return null;
	}
}
