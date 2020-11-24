package techguns.packets.c2s;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import techguns.TGPacketsC2S;
import techguns.Techguns;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.items.guns.GenericGun;
import techguns.keybind.TGKeybindID;
import techguns.packets.TGBasePacket;

public class PacketTGKeybindPress extends TGBasePacket {
	public byte buttonID;
	public Hand hand=Hand.MAIN_HAND;
	public boolean showMsg=false;
	
	
	public PacketTGKeybindPress() {
	}

	public PacketTGKeybindPress(TGKeybindID id) {
		this.buttonID = (byte)id.ordinal();
	}
	
	public PacketTGKeybindPress(TGKeybindID id, Hand hand) {
		this(id);
		this.hand=hand;
	}

	public PacketTGKeybindPress(TGKeybindID id, boolean showMsg) {
		this(id);
		this.showMsg=showMsg;
	}
	
	public TGKeybindID getKeybind() {
		return TGKeybindID.values()[this.buttonID];
	}
	
	@Override
	public void unpack(PacketByteBuf buf) {
		buttonID=buf.readByte();
		byte h=buf.readByte();
		this.hand=Hand.values()[h];
		this.showMsg=buf.readBoolean();
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeByte(buttonID);
		buf.writeByte((byte)hand.ordinal());
		buf.writeBoolean(showMsg);
	}

	@Override
	public void handle(PlayerEntity ply) {
		
		switch(getKeybind()) {
		case FORCE_RELOAD:
			ItemStack item = ply.getStackInHand(this.hand);
			if (!item.isEmpty() && item.getItem() instanceof GenericGun){
				
				GenericGun gun = (GenericGun) item.getItem();
				gun.tryForcedReload(item, ply.world, ply, this.hand);
			}
			break;
		case TOGGLE_SAFEMODE:
			ITGExtendedPlayer props = (ITGExtendedPlayer)ply;
			
			props.setSafeMode(!props.hasEnabledSafemode());
			
			//TODO safemode permission
			/*if (!props.enableSafemode && !Techguns.instance.permissions.canUseUnsafeMode(ply)) {
				props.enableSafemode=true;
			} */
			if(this.showMsg) {
				//TODO
				//TGPackets.network.sendTo(new PacketShowKeybindConfirmMessage(message.buttonID, props.enableSafemode), (EntityPlayerMP)ply);
			}
			//TODO
			//TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply,props, true), (EntityPlayerMP)ply);
	
			break;
		default:
			break;
		}
	}

	@Override
	public Identifier getID() {
		return TGPacketsC2S.KEYBIND_PRESS;
	}
}
