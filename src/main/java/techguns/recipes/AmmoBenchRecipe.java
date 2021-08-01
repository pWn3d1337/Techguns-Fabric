package techguns.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import techguns.TGItems;
import techguns.TGRecipes;
import techguns.blocks.entity.AmmoBenchBlockEntity;

import java.util.List;

public class AmmoBenchRecipe implements Recipe<Inventory> {
    public static AmmoBenchRecipe.Serializer SERIALIZER;

    protected final ItemStack output;
    protected final Identifier id;

    protected static DefaultedList<Ingredient> getInputs()
    {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(3, Ingredient.empty());
        list.set(0, Ingredient.fromTag(TGItems.TAG_BULLET_CASING));
        list.set(1, Ingredient.fromTag(TGItems.TAG_BULLET_CORE));
        list.set(2, Ingredient.fromTag(TGItems.TAG_BULLET_POWDER));
        return list;
    };

    public AmmoBenchRecipe(Identifier id, ItemStack result){
        this.id = id;
        this.output = result;
    }

    @Override
    public boolean matches(Inventory inv, World world) {
        //We need at least one of each input
        for (int i = 0; i< AmmoBenchBlockEntity.INVENTORY_SIZE; i++) {
            if (inv.getStack(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return getInputs();
    }

    @Override
    public ItemStack craft(Inventory inv) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        //at least 3 slots
        return width * height >= 3;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
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
        return TGRecipes.AMMOBENCH_RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<AmmoBenchRecipe> {
        public AmmoBenchRecipe read(Identifier identifier, JsonObject jsonObject) {
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new AmmoBenchRecipe(identifier, itemStack);
        }

        public AmmoBenchRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            ItemStack result = packetByteBuf.readItemStack();
            return new AmmoBenchRecipe(identifier, result);
        }

        public void write(PacketByteBuf packetByteBuf, AmmoBenchRecipe ammoBenchRecipe) {
            packetByteBuf.writeItemStack(ammoBenchRecipe.getOutput());
        }
    }
}
