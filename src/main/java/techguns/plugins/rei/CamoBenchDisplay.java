package techguns.plugins.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.util.Identifier;
import techguns.recipes.CamoChangeRecipe;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CamoBenchDisplay extends BasicDisplay {
    public CamoBenchDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }

    public CamoBenchDisplay(CamoChangeRecipe recipe) {
        this(Collections.singletonList(EntryIngredients.ofItemStacks(recipe.getAllItems())), Collections.singletonList(EntryIngredients.ofItemStacks(recipe.getAllItems())), Optional.ofNullable(recipe.getId()));
    }

    public CategoryIdentifier<?> getCategoryIdentifier() {
        return TGREIPlugin.CAMO_BENCH;
    }
}
