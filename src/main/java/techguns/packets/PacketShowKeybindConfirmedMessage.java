package techguns.packets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import techguns.keybind.TGKeybindID;
import techguns.util.TextUtil;

public class PacketShowKeybindConfirmedMessage extends TGBasePacket {

	byte messageID;
	boolean state;
	
	public PacketShowKeybindConfirmedMessage() {
		super();
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeByte(messageID);
		buf.writeBoolean(state);
	}

	@Override
	public void unpack(PacketByteBuf buf) {
		messageID=buf.readByte();
		state=buf.readBoolean();
	}

	private String getLangKeyForID(int id) {
		if (id >= 0 && id < TGKeybindID.values().length) {
			
			TGKeybindID value = TGKeybindID.values()[id];
			switch(value) {
				/*case TOGGLE_NIGHTVISION:
					return TextUtil.trans("techguns.armorTooltip.nightvision");
				case TOGGLE_JETPACK:
					return TextUtil.trans("techguns.msg.enablejetpack");*/
				case TOGGLE_SAFEMODE:
					return TextUtil.transTG("msg.safemode");
				/*case TOGGLE_STEP_ASSIST:
					return TextUtil.trans("techguns.armorTooltip.stepassist");*/
				default:
					break;
			}
		}
		return "UNKNOWN";
	}
	
	@Override
	public void handle(PlayerEntity ply) {
		ply.sendMessage(new LiteralText(Formatting.YELLOW+
				"[Techguns]: "+Formatting.WHITE+
				TextUtil.trans("techguns.msg.keybindtogglechange")+Formatting.YELLOW+" ["+
				getLangKeyForID(this.messageID)+"] "+Formatting.WHITE+
				TextUtil.trans("techguns.msg.keybindtogglechange2")+
				" "+(this.state?Formatting.GREEN:Formatting.DARK_RED)+"["+TextUtil.trans("techguns.container.info."+(this.state?"on":"off"))+"]"), true);
	}

	@Override
	public Identifier getID() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
