package techguns.client;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import techguns.ITGInitializer;
import techguns.TGPacketsC2S;
import techguns.api.client.entity.ITGExtendedPlayerClient;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.api.guns.GunManager;
import techguns.items.guns.GenericGun;
import techguns.keybind.TGKeybindID;
import techguns.packets.c2s.PacketTGKeybindPress;
import techguns.util.InventoryUtil;

public class Keybinds implements ITGInitializer{
	public static final String TECHGUNS_CATEGORY = "techguns.key.categories.techguns";
	
	public KeyBinding KEY_FORECERELOAD;
		
	@Override
	public void init() {
		KEY_FORECERELOAD = addKeybind("techguns.key.forcereload", GLFW.GLFW_KEY_R);
	
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
		    while (KEY_FORECERELOAD.wasPressed()) {
		    	handleKeybind_Reload();
		    };
		});
	}
	
	protected static KeyBinding addKeybind(String translation, int default_code) {
		KeyBinding k = new KeyBinding(translation, InputUtil.Type.KEYSYM, default_code, TECHGUNS_CATEGORY);
		KeyBindingHelper.registerKeyBinding(k);
		return k;
	}
	
	protected void handleKeybind_Reload() {
		MinecraftClient mc = MinecraftClient.getInstance(); 
		PlayerEntity ply = mc.player;
		ITGExtendedPlayerClient props = (ITGExtendedPlayerClient)ply;
    	
    	if (props!=null){
    	
        	ItemStack stack_main = ply.getMainHandStack();
        	ItemStack stack_off = ply.getOffHandStack();
        	boolean canReloadMainhand = this.canReloadGun(props, ply,stack_main, Hand.MAIN_HAND);
        	boolean canReloadOffhand = GunManager.canUseOffhand(stack_main, stack_off, ply) && this.canReloadGun(props, ply, stack_off, Hand.OFF_HAND);
    	
        	
        	if (canReloadMainhand || canReloadOffhand) {
        		Hand hand;
        		ItemStack gunToReload;
        		
        		if(!canReloadOffhand) { //only mainhand can be reloaded
        			hand =Hand.MAIN_HAND;
        			gunToReload=stack_main;
        		} else if (!canReloadMainhand) { //only offhand can be reloaded
        			hand = Hand.OFF_HAND;
        			gunToReload=stack_off;
        		} else {	//both can be reloaded
        			
        			GenericGun gunMain = (GenericGun) stack_main.getItem();
        			GenericGun gunOff = (GenericGun) stack_main.getItem();
        			
        			double ammoPercent = gunMain.getPercentAmmoLeft(stack_main);
        			double ammoPercent_off = gunOff.getPercentAmmoLeft(stack_off);
        			
        			if(ammoPercent_off<ammoPercent) {
        				hand = Hand.OFF_HAND;
	        			gunToReload=stack_off;
        			} else {
        				hand = Hand.MAIN_HAND;
	        			gunToReload=stack_main;
        			}
        		}
        		
        		//TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindID.FORCE_RELOAD,hand));
        		TGPacketsC2S.sendToServer(new PacketTGKeybindPress(TGKeybindID.FORCE_RELOAD, hand));
        		((GenericGun)gunToReload.getItem()).tryForcedReload(gunToReload, ply.world,ply, hand);
        		
        	}
    	}
	}
	
	private boolean canReloadGun(ITGExtendedPlayer props, PlayerEntity ply, ItemStack stack, Hand hand) {
		if(!stack.isEmpty() && stack.getItem() instanceof GenericGun) {
			GenericGun gun = (GenericGun) stack.getItem();
			if (props.getFireDelay(hand)<=0 && !gun.isFullyLoaded(stack) && !(ply.getActiveItem() == stack)){
				
				ItemStack[] ammo = gun.getReloadItem(stack);
				for (ItemStack s : ammo) {
					if(!InventoryUtil.canConsumeAmmoPlayer(ply, s)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
}
