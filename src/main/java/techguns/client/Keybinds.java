package techguns.client;

import java.util.HashMap;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import techguns.ITGInitializer;
import techguns.TGPacketsC2S;
import techguns.api.client.entity.ITGExtendedPlayerClient;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.api.guns.GunManager;
import techguns.client.deatheffects.DeathEffectHandler;
import techguns.client.particle.TGFX;
import techguns.client.particle.TGFXType;
import techguns.items.guns.GenericGun;
import techguns.keybind.TGKeybindID;
import techguns.packets.c2s.PacketTGKeybindPress;
import techguns.util.InventoryUtil;

public class Keybinds implements ITGInitializer{
	public static final String TECHGUNS_CATEGORY = "techguns.key.categories.techguns";
	
	public KeyBinding KEY_FORECERELOAD;
		
	public KeyBinding KEY_TOGGLE_SAFEMODE;
	
	public KeyBinding DEBUG_KEY_RELOAD_FX;


	public KeyBinding DEBUG_OFFSET_X_INC;
	public KeyBinding DEBUG_OFFSET_X_DEC;
	public KeyBinding DEBUG_OFFSET_Y_INC;
	public KeyBinding DEBUG_OFFSET_Y_DEC;
	public KeyBinding DEBUG_OFFSET_Z_INC;
	public KeyBinding DEBUG_OFFSET_Z_DEC;
	public KeyBinding DEBUG_OFFSET_RESET;
	public KeyBinding DEBUG_OFFSET_PRINT;

	public static double OFFSET_INCREMENT = 0.05D;
	public static double OFFSET_X = 0.0D;
	public static double OFFSET_Y = 0.0D;
	public static double OFFSET_Z = 0.0D;

	@Override
	public void init() {
		KEY_FORECERELOAD = addKeybind("techguns.key.forcereload", GLFW.GLFW_KEY_R);
		KEY_TOGGLE_SAFEMODE = addKeybind("techguns.key.togglesafemode", GLFW.GLFW_KEY_B);
		DEBUG_KEY_RELOAD_FX = addKeybind("techings.key.debug.reloadfx", GLFW.GLFW_KEY_KP_0);

		//TODO debug only option
		DEBUG_OFFSET_X_INC = addKeybind("techguns.key.debug_x_inc", GLFW.GLFW_KEY_KP_8);
		DEBUG_OFFSET_X_DEC = addKeybind("techguns.key.debug_x_dec", GLFW.GLFW_KEY_KP_9);

		DEBUG_OFFSET_Y_INC = addKeybind("techguns.key.debug_y_inc", GLFW.GLFW_KEY_KP_5);
		DEBUG_OFFSET_Y_DEC = addKeybind("techguns.key.debug_y_dec", GLFW.GLFW_KEY_KP_6);

		DEBUG_OFFSET_Z_INC = addKeybind("techguns.key.debug_z_inc", GLFW.GLFW_KEY_KP_2);
		DEBUG_OFFSET_Z_DEC = addKeybind("techguns.key.debug_z_dec", GLFW.GLFW_KEY_KP_3);

		DEBUG_OFFSET_RESET = addKeybind("techguns.key.debug_reset", GLFW.GLFW_KEY_KP_DECIMAL);
		DEBUG_OFFSET_PRINT = addKeybind("techguns.key.debug_print", GLFW.GLFW_KEY_KP_0);

		
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
		    while (KEY_FORECERELOAD.wasPressed()) {
		    	handleKeybind_Reload();
		    };
		    while (KEY_TOGGLE_SAFEMODE.wasPressed()) {
		    	TGPacketsC2S.sendToServer(new PacketTGKeybindPress(TGKeybindID.TOGGLE_SAFEMODE, true));
		    };
		    while (DEBUG_KEY_RELOAD_FX.wasPressed()) {
		    	System.out.println("Reloading FXLIST...");
		    	TGFX.FXList = new HashMap<String, TGFXType>();
		    	TGFX.loadFXList();
		    	DeathEffectHandler.reloadAllFX();
		    }

		    while (DEBUG_OFFSET_X_INC.wasPressed()) {
		    	Keybinds.OFFSET_X += OFFSET_INCREMENT;
				printKeyBinds();
			}
			while (DEBUG_OFFSET_X_DEC.wasPressed()) {
				Keybinds.OFFSET_X -= OFFSET_INCREMENT;
				printKeyBinds();
			}
			while (DEBUG_OFFSET_Y_INC.wasPressed()) {
				Keybinds.OFFSET_Y += OFFSET_INCREMENT;
				printKeyBinds();
			}
			while (DEBUG_OFFSET_Y_DEC.wasPressed()) {
				Keybinds.OFFSET_Y -= OFFSET_INCREMENT;
				printKeyBinds();
			}
			while (DEBUG_OFFSET_Z_INC.wasPressed()) {
				Keybinds.OFFSET_Z += OFFSET_INCREMENT;
				printKeyBinds();
			}
			while (DEBUG_OFFSET_Z_DEC.wasPressed()) {
				Keybinds.OFFSET_Z -= OFFSET_INCREMENT;
				printKeyBinds();
			}
			while (DEBUG_OFFSET_RESET.wasPressed()) {
				Keybinds.OFFSET_X =0;
				Keybinds.OFFSET_Y =0;
				Keybinds.OFFSET_Z =0;
				printKeyBinds();
			}
			while (DEBUG_OFFSET_PRINT.wasPressed()) {
				printKeyBinds(false);
			}

		});
	}

	protected static void printKeyBinds(){
		printKeyBinds(true);
	}
	protected static void printKeyBinds(boolean bar){
		final String FORMAT = "%,.03f";
		ClientProxy.get().getPlayerClient().sendMessage(Text.of("X: "+String.format(FORMAT, OFFSET_X) +" Y:" + String.format(FORMAT, OFFSET_Y) + " Z:"+ String.format(FORMAT, OFFSET_Z)), bar);
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
