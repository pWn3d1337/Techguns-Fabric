package techguns.plugins.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.util.Identifier;
import techguns.recipes.AmmoBenchRecipe;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AmmoBenchDisplay extends BasicDisplay {
    public AmmoBenchDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }

    public AmmoBenchDisplay(AmmoBenchRecipe recipe) {
        this(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput())), Optional.ofNullable(recipe.getId()));
    }

    public CategoryIdentifier<?> getCategoryIdentifier() {
        return TGREIPlugin.AMMO_BENCH;
    }
}
