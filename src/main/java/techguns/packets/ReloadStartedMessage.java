package techguns.packets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import techguns.TGPacketsS2C;
import techguns.client.ShooterValues;

public class ReloadStartedMessage extends TGBasePacket {

	protected int entityID;
	protected int time;
	protected byte attackType;
	protected boolean offHand;

	public ReloadStartedMessage(LivingEntity shooter, Hand hand, int firetime, int attackType) {
		this.entityID = shooter.getId();
		this.time = firetime;
		this.attackType = (byte) attackType;
		this.offHand = hand == Hand.OFF_HAND;
	}

	public ReloadStartedMessage() {
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeInt(time);
		buf.writeByte(attackType);
		buf.writeBoolean(offHand);
	}

	@Override
	public void unpack(PacketByteBuf buf) {
		this.entityID = buf.readInt();
		this.time = buf.readInt();
		this.attackType = buf.readByte();
		this.offHand = buf.readBoolean();
	}

	@Override
	public void handle(PlayerEntity player) {
		LivingEntity shooter = (LivingEntity) player.world.getEntityById(this.entityID);
		
		if (shooter !=null){
			MinecraftClient mc = MinecraftClient.getInstance();
			if (shooter!=mc.player){
				ShooterValues.setReloadtime(shooter, this.offHand, System.currentTimeMillis()+this.time, this.time, this.attackType);
			}
		}	
	}

	@Override
	public Identifier getID() {
		return TGPacketsS2C.RELOAD_STARTED;
	}

}
