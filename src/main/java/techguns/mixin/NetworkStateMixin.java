package techguns.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.network.NetworkState;

@Mixin(NetworkState.class)
public interface NetworkStateMixin {

	@Accessor(value = "packetHandlers")
	public Map getPacketHandlers();
	
	@Accessor(value = "HANDLER_STATE_MAP")
	public Map getHANDLER_STATE_MAP();
	
}
