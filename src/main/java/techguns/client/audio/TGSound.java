package techguns.client.audio;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec2f;
import techguns.sounds.TGSoundCategory;
import techguns.util.EntityCondition;
import techguns.util.MathUtil;

@Environment(EnvType.CLIENT)
public class TGSound extends MovingSoundInstance {

	Entity entity;

	boolean gunPosition;
	boolean moving;
	TGSoundCategory tgcategory=null;
	EntityCondition condition = EntityCondition.NONE;
	
	public TGSound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, TGSoundCategory category, EntityCondition condition) {
		this(soundname, entity, volume, pitch, repeat, moving, gunPosition, category);
		this.condition = condition;
	}
	
	public TGSound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, TGSoundCategory category) {
		this(soundname, entity, volume, pitch, repeat, moving, category);
		this.gunPosition = gunPosition;
		if (gunPosition) {
			if (entity instanceof LivingEntity) {
    			Vec2f pos = MathUtil.polarOffsetXZ(x, z, 1.0f, (float)(((LivingEntity)entity).getHeadYaw()*Math.PI/180.0f));
    			x = pos.x;
    			z = pos.y;
    			y = y + (entity.getHeight()*0.5f);		
    		}
		}
	}
	
	public TGSound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, TGSoundCategory category) {
		this(soundname, entity, volume, pitch, repeat, category);
		this.moving = moving;
		if (entity != null) {
			x = (float) entity.getX();
	        y = (float) entity.getY();
	        z = (float) entity.getZ();
		}
	}
	
	public TGSound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, TGSoundCategory category) {
		super(soundname, category.getVanillaCategory());
		this.tgcategory=category;
		this.volume = volume;
		this.pitch = pitch;
		this.repeat = repeat;
		this.entity = entity;
	}
	
	public TGSound(SoundEvent soundname, float posx, float posy, float posz, float volume, float pitch, boolean repeat, TGSoundCategory category){
		this(soundname,null,volume,pitch,repeat, category);
		this.x= posx;
		this.y = posy;
		this.z = posz;
	}
	
	public void setDonePlaying()
    {
        this.setDone();
        this.repeatDelay = 0;
    }

    @Override
    public void tick()
    {	
    	if (entity != null && !condition.evaluate(entity)) {
    		this.setDonePlaying();
    	}
    	
       //TODO: Auto-remove?
        if (moving && entity != null && !entity.isRemoved()) {
	        x = (float) entity.getX();
	        y = (float) entity.getY();
	        z = (float) entity.getZ();
        	if (gunPosition) {
        		if (entity instanceof LivingEntity) {
        			Vec2f pos = MathUtil.polarOffsetXZ(x, z, 1.0f, (float) (((LivingEntity)entity).getHeadYaw()*Math.PI/180.0f));
        			x = pos.x;
        			z = pos.y;
        			y = y + (entity.getHeight()*0.5f);
        			
        		}
        		
        	}
        } else if (moving){
        	this.setDonePlaying();
        }
    }

	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setRepeatDelay(int delay)
    {
        this.repeatDelay = delay;
    }


}
