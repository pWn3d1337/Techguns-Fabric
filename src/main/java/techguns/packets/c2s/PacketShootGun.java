package techguns.packets.c2s;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import techguns.TGPacketsC2S;
import techguns.api.guns.IGenericGun;
import techguns.packets.TGBasePacket;

public class PacketShootGun extends TGBasePacket {

	public boolean isZooming=false;
	public boolean offHand=false;
	
	public PacketShootGun(boolean isZooming, Hand hand) {
		this.isZooming = isZooming;
		this.offHand = hand==Hand.OFF_HAND;
	}
	
	public PacketShootGun(PacketByteBuf buf) {
		super(buf);
	}

	@Override
	public void unpack(PacketByteBuf buf) {
		this.isZooming=buf.readBoolean();
		this.offHand=buf.readBoolean();
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeBoolean(isZooming);
		buf.writeBoolean(offHand);
	}
	
	public Hand getHand(){
		return offHand ? Hand.OFF_HAND : Hand.MAIN_HAND;
	}
	
	@Override
	public void handle(PlayerEntity player) {
		ItemStack stack = player.getStackInHand(this.getHand());
		if(!stack.isEmpty() && stack.getItem() instanceof IGenericGun){
			((IGenericGun) stack.getItem()).shootGunPrimary(stack, player.world, player, this.isZooming, this.getHand(), null);
		}
	}

	@Override
	public Identifier getID() {
		return TGPacketsC2S.SHOOT_GUN;
	}

}
