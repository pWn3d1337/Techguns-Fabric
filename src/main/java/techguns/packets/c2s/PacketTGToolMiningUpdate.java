package techguns.packets.c2s;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import techguns.TGPacketsC2S;
import techguns.TGPacketsS2C;
import techguns.packets.GunFiredMessage;
import techguns.packets.PacketPlaySound;
import techguns.packets.TGBasePacket;
import techguns.sounds.TGSoundCategory;

public class PacketTGToolMiningUpdate extends TGBasePacket {

	protected Vec3d hitpos;
	protected int recoiltime;
	protected int muzzleFlashtime;
	protected String soundname;
	
	public PacketTGToolMiningUpdate() {
	}
	
	public PacketTGToolMiningUpdate(int recoiltime, int muzzleFlashtime, Vec3d hitpos, SoundEvent sound) {
		this.hitpos = hitpos;
		this.soundname = Registries.SOUND_EVENT.getId(sound).toString();
		this.recoiltime = recoiltime;
		this.muzzleFlashtime = muzzleFlashtime;
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeFloat((float) hitpos.x);
		buf.writeFloat((float) hitpos.y);
		buf.writeFloat((float) hitpos.z);
		buf.writeShort((short)this.recoiltime);
		buf.writeShort((short)this.muzzleFlashtime);
		buf.writeBytes(soundname.getBytes());
	}

	@Override
	public void unpack(PacketByteBuf buf) {
		float x, y, z;
		x = buf.readFloat();
		y = buf.readFloat();
		z = buf.readFloat();
		this.hitpos = new Vec3d(x, y, z);
		this.recoiltime= buf.readShort();
		this.muzzleFlashtime = buf.readShort();
		
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		soundname = new String(bytes);
	}

	@Override
	public void handle(PlayerEntity player) {
		SoundEvent s = Registries.SOUND_EVENT.get(new Identifier(soundname));
		TGPacketsS2C.sendToAllTracking(new GunFiredMessage(player, recoiltime, muzzleFlashtime, (byte)0, true, Hand.MAIN_HAND), player, false);
		if (s!=null) {
			TGPacketsS2C.sendToAllTracking(new PacketPlaySound(s, player, 1.0f, 1.0f, false, false, true, false, TGSoundCategory.GUN_FIRE), player, false);
		}
	}

	@Override
	public Identifier getID() {
		return TGPacketsC2S.MINING_UPDATE;
	}

}
