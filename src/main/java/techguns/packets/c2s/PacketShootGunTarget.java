package techguns.packets.c2s;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import techguns.TGPacketsC2S;
import techguns.api.guns.IGenericGun;
import techguns.packets.TGBasePacket;

public class PacketShootGunTarget extends TGBasePacket {

	public boolean isZooming=false;
	public boolean offHand=false;
	public int entityId=-1;
	
	public PacketShootGunTarget(boolean isZooming, Hand hand, Entity target) {
		this.isZooming = isZooming;
		this.offHand = hand==Hand.OFF_HAND;
		this.entityId = target.getId();
	}
	
	public PacketShootGunTarget() {
		
	}

	@Override
	public void unpack(PacketByteBuf buf) {
		this.isZooming=buf.readBoolean();
		this.offHand=buf.readBoolean();
		this.entityId=buf.readInt();
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeBoolean(isZooming);
		buf.writeBoolean(offHand);
		buf.writeInt(entityId);
	}
	
	public Hand getHand(){
		return offHand ? Hand.OFF_HAND : Hand.MAIN_HAND;
	}
	
	@Override
	public void handle(PlayerEntity player) {
		ItemStack stack = player.getStackInHand(this.getHand());
		Entity target = player.world.getEntityById(this.entityId);
		if(!stack.isEmpty() && stack.getItem() instanceof IGenericGun){
			((IGenericGun) stack.getItem()).shootGunPrimary(stack, player.world, player, this.isZooming, this.getHand(), target);
		}
	}

	@Override
	public Identifier getID() {
		return TGPacketsC2S.SHOOT_GUN_TARGET;
	}

}
