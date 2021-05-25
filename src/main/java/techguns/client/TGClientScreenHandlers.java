package techguns.client;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import techguns.TGBlocks;
import techguns.client.gui.AmmoBenchScreen;
import techguns.client.gui.CamoBenchScreen;

public class TGClientScreenHandlers {

    public static void initialize() {
        ScreenRegistry.register(TGBlocks.CAMO_BENCH_SCREEN_HANDLER, CamoBenchScreen::new);
        ScreenRegistry.register(TGBlocks.AMMO_BENCH_SCREEN_HANDLER, AmmoBenchScreen::new);
    }

}
