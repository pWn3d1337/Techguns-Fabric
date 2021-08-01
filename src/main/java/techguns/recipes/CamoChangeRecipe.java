package techguns.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;
import techguns.TGRecipes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CamoChangeRecipe implements Recipe<Inventory> {

    public static CamoChangeRecipe.Serializer SERIALIZER;

    protected final ArrayList<ItemStack> camogroup;
    protected final Identifier id;

    public CamoChangeRecipe(Identifier id, List<ItemStack> entries) {
        this.id = id;
        this.camogroup = new ArrayList<>(entries);
    }

    public ArrayList<ItemStack> getAllItems() {
        return camogroup;
    }

    @Override
    public boolean matches(Inventory inv, World world) {
        ItemStack stack = inv.getStack(0);
        if (!stack.isEmpty()) {
            return camogroup.stream().anyMatch((ItemStack s) -> ItemStack.areItemsEqual(s, stack));
        }
        return false;
    }

    public int getNumEntries() {
        return camogroup.size();
    }

    @Override
    public ItemStack craft(Inventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public ItemStack getOutput() {
        return getOutput(0);
    }

    public ItemStack getOutput(int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return TGRecipes.CAMO_CHANGE_RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<CamoChangeRecipe> {
        @Override
        public CamoChangeRecipe read(Identifier id, JsonObject json) {
            List<ItemStack> list = new LinkedList<>();
            if (JsonHelper.hasArray(json, "entries")){
                JsonArray entries = JsonHelper.getArray(json, "entries");

                for (JsonElement entry : entries){
                    if (entry instanceof JsonObject){
                        JsonObject obj = (JsonObject) entry;
                        list.add(ShapedRecipe.outputFromJson(obj));
                    }
                }
            }
            return new CamoChangeRecipe(id, list);
        }

        @Override
        public CamoChangeRecipe read(Identifier id, PacketByteBuf buf) {
            int amount = buf.readInt();
            List<ItemStack> entries = new LinkedList<>();
            for (int i=0; i< amount; i++){
                entries.add(buf.readItemStack());
            }
            return new CamoChangeRecipe(id, entries);
        }

        @Override
        public void write(PacketByteBuf buf, CamoChangeRecipe recipe) {
            buf.writeInt(recipe.getNumEntries());
            for (int i=0; i< recipe.getNumEntries(); i++){
                buf.writeItemStack(recipe.getOutput(i));
            }
        }
    }
}
