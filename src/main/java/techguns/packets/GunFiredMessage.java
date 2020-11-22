package techguns.packets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import techguns.TGPacketsS2C;
import techguns.client.ShooterValues;

public class GunFiredMessage extends TGBasePacket {

	protected int shooterid;
	protected int recoiltime;
	protected int muzzleflashtime;
	protected byte attackType;
	protected boolean checkRecoil;
	protected boolean offhand;
		
	public GunFiredMessage(LivingEntity shooter, int recoiltime, int muzzleflashtime, byte attackType, boolean checkRecoil,
			Hand hand) {
		this.shooterid = shooter.getEntityId();
		this.recoiltime = recoiltime;
		this.muzzleflashtime = muzzleflashtime;
		this.attackType = attackType;
		this.checkRecoil = checkRecoil;
		this.offhand = hand==Hand.OFF_HAND?true:false;
	}

	public GunFiredMessage() {
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeInt(shooterid);
		buf.writeInt(recoiltime);
		buf.writeInt(muzzleflashtime);
		buf.writeByte(attackType);
		buf.writeBoolean(checkRecoil);
		buf.writeBoolean(offhand);
	}
	
	@Override
	public void unpack(PacketByteBuf buf) {
		this.shooterid = buf.readInt();
		this.recoiltime = buf.readInt();
		this.muzzleflashtime = buf.readInt();
		this.attackType = buf.readByte();
		this.checkRecoil = buf.readBoolean();
		this.offhand = buf.readBoolean();
	}
	
	@Override
	public void handle(PlayerEntity ply) {
		Entity shooter = ply.world.getEntityById(this.shooterid);
		if(shooter !=null && shooter instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity)shooter;
			if (!this.checkRecoil || !ShooterValues.isStillRecoiling(ent, this.offhand,this.attackType)){
				ShooterValues.setRecoiltime(ent, this.offhand, System.currentTimeMillis()+this.recoiltime, this.recoiltime,this.attackType);
				ShooterValues.setMuzzleFlashTime(ent, this.offhand, System.currentTimeMillis()+this.muzzleflashtime, this.muzzleflashtime);	
			}
		}
	}

	@Override
	public Identifier getID() {
		return TGPacketsS2C.GUN_FIRED;
	};
	
}
