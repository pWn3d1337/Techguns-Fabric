package techguns.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;
import techguns.TGRecipes;
import techguns.blocks.entity.AmmoBenchBlockEntity;

public class AmmoBenchRecipe implements Recipe<Inventory> {
    public static AmmoBenchRecipe.Serializer SERIALIZER;

    protected final ItemStack output;
    protected final Identifier id;

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
            ItemStack itemStack = ShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
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
