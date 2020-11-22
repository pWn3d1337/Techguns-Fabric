package techguns.packets;

import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public abstract class TGBasePacket {

	public TGBasePacket() {};
	
	public abstract void pack(PacketByteBuf buf);
	public abstract void unpack(PacketByteBuf buf);
	
	public void handle(PacketContext context) {
		PlayerEntity ply = context.getPlayer();
		this.handle(ply);
	}
	
	public abstract void handle(PlayerEntity player);
	
	public abstract Identifier getID();
	
}
