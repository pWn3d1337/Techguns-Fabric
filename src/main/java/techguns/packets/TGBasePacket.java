package techguns.packets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public abstract class TGBasePacket {

	public TGBasePacket() {};
	
	public abstract void pack(PacketByteBuf buf);
	public abstract void unpack(PacketByteBuf buf);

	public abstract void handle(PlayerEntity player);

	public abstract Identifier getID();
	
}
