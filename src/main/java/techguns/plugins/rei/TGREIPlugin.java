package techguns.plugins.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import techguns.TGItems;
import techguns.Techguns;
import techguns.recipes.AmmoBenchRecipe;
import techguns.recipes.CamoChangeRecipe;

public class TGREIPlugin implements REIClientPlugin {
    public static final CategoryIdentifier<CamoBenchDisplay> CAMO_BENCH = CategoryIdentifier.of(Techguns.MODID, "plugins/rei/camo_bench");
    public static final CategoryIdentifier<AmmoBenchDisplay> AMMO_BENCH = CategoryIdentifier.of(Techguns.MODID, "plugins/rei/ammo_bench");

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CamoBenchCategory(), new AmmoBenchCategory());
        registry.addWorkstations(CAMO_BENCH, new EntryStack[]{EntryStacks.of(TGItems.CAMO_BENCH)});
        registry.addWorkstations(AMMO_BENCH, new EntryStack[]{EntryStacks.of(TGItems.AMMO_BENCH)});
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(CamoChangeRecipe.class, CamoBenchDisplay::new);
        registry.registerFiller(AmmoBenchRecipe.class, AmmoBenchDisplay::new);
    }
}
