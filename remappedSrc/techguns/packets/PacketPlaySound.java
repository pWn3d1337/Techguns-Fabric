package techguns.packets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import techguns.TGPacketsS2C;
import techguns.client.ClientProxy;
import techguns.sounds.TGSoundCategory;
import techguns.util.EntityCondition;

public class PacketPlaySound extends TGBasePacket {

	String soundname;
	int entityId;
	float volume;
	float pitch;
	boolean repeat;
	boolean moving;
	boolean gunPosition = false;
	boolean playOnOwnPlayer=false;
	int soundx=0;
	short soundy=0;
	int soundz=0;
	TGSoundCategory category;
	
	EntityCondition condition;
	
	public PacketPlaySound(PacketByteBuf buf) {
		super(buf);
	}

	public PacketPlaySound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, boolean playOnOwnPlayer, TGSoundCategory category) {
		this(soundname, entity, volume, pitch, repeat, moving, gunPosition, category);
		this.playOnOwnPlayer=playOnOwnPlayer;
	}
	
	public PacketPlaySound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, boolean playOnOwnPlayer, TGSoundCategory category, EntityCondition condition) {
		this(soundname, entity, volume, pitch, repeat, moving, gunPosition, category);
		this.playOnOwnPlayer=playOnOwnPlayer;
		this.condition = condition;
	}
	
	public PacketPlaySound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, TGSoundCategory category) {
		if (entity == null) {
			this.entityId = -1;
		} else {
			this.entityId = entity.getEntityId();
		}
		
		this.soundname = Registry.SOUND_EVENT.getId(soundname).toString();
		this.volume = volume;
		this.pitch = pitch;
		this.repeat = repeat;
		this.moving = moving;
		this.gunPosition = gunPosition;
		//this.playOnOwnPlayer=false;
		this.category=category;
	}
	
	public PacketPlaySound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, TGSoundCategory category) {
		if (entity == null) {
			this.entityId = -1;
		} else {
			this.entityId = entity.getEntityId();
		}
		this.soundname = Registry.SOUND_EVENT.getId(soundname).toString();
		this.volume = volume;
		this.pitch = pitch;
		this.repeat = repeat;
		this.moving = moving;
		this.category=category;
	}
	
	public PacketPlaySound(SoundEvent soundname, int posx, int posy, int posz, float volume, float pitch, boolean repeat, TGSoundCategory category){
		this(soundname,null, volume, pitch, repeat, false, false, category);
		this.soundx = posx;
		this.soundy = (short) posy;
		this.soundz = posz;
	}
	
	
	@Override
	public void unpack(PacketByteBuf buf) {
		//this.soundname = buf.read
		this.entityId = buf.readInt();
		this.volume = buf.readFloat();
		this.pitch = buf.readFloat();
		this.repeat = buf.readBoolean();
		this.moving = buf.readBoolean();
		this.gunPosition = buf.readBoolean();
		this.playOnOwnPlayer=buf.readBoolean();
		
		this.soundx = buf.readInt();
		this.soundy = buf.readShort();
		this.soundz = buf.readInt();
		
		this.category=TGSoundCategory.get(buf.readByte());
		
		this.condition = EntityCondition.fromByte(buf.readByte());
		
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		soundname = new String(bytes);
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeInt(entityId);
		buf.writeFloat(volume);
		buf.writeFloat(pitch);
		buf.writeBoolean(repeat);
		buf.writeBoolean(moving);
		buf.writeBoolean(gunPosition);
		buf.writeBoolean(playOnOwnPlayer);
		
		buf.writeInt(soundx);
		buf.writeShort(soundy);
		buf.writeInt(soundz);
		
		buf.writeByte(category.getId());
		
		if (condition == null) {
			buf.writeByte(EntityCondition.NONE.id);
		}
		else {
			buf.writeByte(condition.id);
		}
		
		buf.writeBytes(soundname.getBytes());
	}


	@Override
	public void handle(PlayerEntity player) {
		SoundEvent event = Registry.SOUND_EVENT.get(new Identifier(this.soundname));
				
		ClientProxy cp = ClientProxy.get();
		if (this.entityId!=-1){	
			cp.handleSoundEvent(player, this.entityId, event, this.volume, this.pitch, this.repeat, this.moving, this.gunPosition, this.playOnOwnPlayer, this.category, this.condition);
		} else {
			cp.playSoundOnPosition(event,this.soundx, this.soundy, this.soundz, this.volume, this.pitch, this.repeat, this.category);
		}
	}

	@Override
	public Identifier getID() {
		return TGPacketsS2C.PLAY_SOUND;
	}
	
}
