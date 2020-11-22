package techguns.packets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import techguns.TGPacketsS2C;
import techguns.api.entity.ITGExtendedPlayer;

public class PacketSwapWeapon extends TGBasePacket {

	protected int playerid;
	
	public PacketSwapWeapon(PlayerEntity player) {
		this.playerid = player.getEntityId();
	}

	public PacketSwapWeapon() {
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeInt(playerid);
	}

	@Override
	public void unpack(PacketByteBuf buf) {
		this.playerid=buf.readInt();
	}

	@Override
	public void handle(PlayerEntity player) {
		World w = player.world;
		Entity ent = w.getEntityById(this.playerid);
		if (ent!=null && ent instanceof PlayerEntity) {
			ITGExtendedPlayer caps = (ITGExtendedPlayer)ent;
			caps.swapAttackTimes();
		}
	}

	@Override
	public Identifier getID() {
		return TGPacketsS2C.SWAP_WEAPON;
	}

}
