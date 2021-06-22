package techguns.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(ShapedRecipe.class)
public interface ShapedRecipeAccessor {

    @Accessor
    public int getWidth();
    @Accessor
    public int getHeight();
    /*@Accessor
    public DefaultedList<Ingredient> getInputs();*/
    @Accessor
    public ItemStack getOutput();
    @Accessor
    public Identifier getId();
    @Accessor
    public String getGroup();

    @Invoker("readSymbols")
    public static Map<String, Ingredient> invokeReadSymbols(JsonObject json){
        throw new AssertionError();
    }

    @Invoker("removePadding")
    public static String[] invokeRemovePadding(String... lines) {
        throw new AssertionError();
    }

    @Invoker("getPattern")
    public static String[] invokeGetPattern(JsonArray json) {
        throw new AssertionError();
    }

    @Invoker("createPatternMatrix")
    public static DefaultedList<Ingredient> invokeCreatePatternMatrix(String[] pattern, Map<String, Ingredient> key, int width, int height){
        throw new AssertionError();
    }
}
