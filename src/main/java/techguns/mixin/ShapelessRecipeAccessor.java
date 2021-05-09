package techguns.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ShapelessRecipe.class)
public interface ShapelessRecipeAccessor {

    @Accessor
    public Identifier getId();

    @Accessor
    public String getGroup();

    @Accessor
    public ItemStack getOutput();

    @Accessor
    public DefaultedList<Ingredient> getInput();

}
