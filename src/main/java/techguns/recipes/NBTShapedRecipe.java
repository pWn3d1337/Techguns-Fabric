package techguns.recipes;

import com.google.gson.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import techguns.mixin.ShapedRecipeAccessor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NBTShapedRecipe extends ShapedRecipe {

    public static NBTShapedRecipe.Serializer SERIALIZER;

    protected static Map<String, Class> NUMBER_DTYPES = new HashMap<>();
    //also add type to if-statement below
    static {
        NUMBER_DTYPES.put("ammo", Short.class);
        NUMBER_DTYPES.put("mininghead", Byte.class);
    }

    public NBTShapedRecipe(Identifier id, String group, CraftingRecipeCategory category, int width, int height, DefaultedList<Ingredient> ingredients, ItemStack output) {
        super(id, group, category, width, height, ingredients, output);
    }

    public static void parseTagCompound(NbtCompound tags, String key, JsonElement element){
        if (key==null && !element.isJsonObject()) {
            throw new UnsupportedOperationException("Data must be a JsonObject");
        }
        if(element.isJsonPrimitive()){
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if(primitive.isBoolean()){
                tags.putBoolean(key, primitive.getAsBoolean());
            } else if(primitive.isNumber()){
                Number number = primitive.getAsNumber();
                Class type = NUMBER_DTYPES.getOrDefault(key, Integer.class);
                if(type == Short.class) {
                    tags.putShort(key, number.shortValue());
                } else if(type == Byte.class){
                    tags.putByte(key, number.byteValue());
                } else {
                    tags.putInt(key, number.intValue());
                }
            } else if(primitive.isString()){
                tags.putString(key, primitive.getAsString());
            }
        } else if (element.isJsonArray()){
            throw new UnsupportedOperationException("NBT arrays are Not Yet implemented!");

        } else if( element.isJsonObject()){

            if (key == null) {
                //First level, directly write to tagCompound
                for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                    parseTagCompound(tags, entry.getKey(), entry.getValue());
                }
            } else {
                NbtCompound subtag = new NbtCompound();
                for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                    parseTagCompound(subtag, entry.getKey(), entry.getValue());
                }
                tags.put(key, subtag);
            }
        }
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static ItemStack getItemStack(JsonObject json) {
        String string = JsonHelper.getString(json, "item");
        Item item = (Item) Registries.ITEM.getOrEmpty(new Identifier(string)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + string + "'");
        });
        NbtCompound tags =null;
        if (json.has("data")) {
            tags = new NbtCompound();
            JsonObject data = json.getAsJsonObject("data");
            parseTagCompound(tags, null, data);
        }

        int i = JsonHelper.getInt(json, "count", 1);
        ItemStack stack = new ItemStack(item,i);
        if(tags!=null){
            stack.setNbt(tags);
        }
        return stack;
    }

    public static class Serializer implements RecipeSerializer<NBTShapedRecipe> {

        @Override
        public NBTShapedRecipe read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            Map<String, Ingredient> map = ShapedRecipeAccessor.invokeReadSymbols(JsonHelper.getObject(jsonObject, "key"));
            String[] strings = ShapedRecipeAccessor.invokeRemovePadding(ShapedRecipeAccessor.invokeGetPattern(JsonHelper.getArray(jsonObject, "pattern")));
            int i = strings[0].length();
            int j = strings.length;
            DefaultedList<Ingredient> defaultedList = ShapedRecipeAccessor.invokeCreatePatternMatrix(strings, map, i, j);
            ItemStack itemStack = NBTShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
            return new NBTShapedRecipe(identifier, string, CraftingRecipeCategory.MISC,  i, j, defaultedList, itemStack);
        }

        @Override
        public NBTShapedRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            int i = packetByteBuf.readVarInt();
            int j = packetByteBuf.readVarInt();
            String string = packetByteBuf.readString(32767);
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < defaultedList.size(); ++k) {
                defaultedList.set(k, Ingredient.fromPacket(packetByteBuf));
            }

            ItemStack itemStack = packetByteBuf.readItemStack();
            //TODO 1.19.3 REWRITE THIS CLASS
            return new NBTShapedRecipe(identifier, string, CraftingRecipeCategory.MISC, i, j, defaultedList, itemStack);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, NBTShapedRecipe nbtshapedRecipe) {
            ShapedRecipeAccessor shapedRecipe = (ShapedRecipeAccessor) nbtshapedRecipe;
            packetByteBuf.writeVarInt(shapedRecipe.getWidth());
            packetByteBuf.writeVarInt(shapedRecipe.getHeight());
            packetByteBuf.writeString(shapedRecipe.getGroup());
            Iterator var3 = nbtshapedRecipe.getIngredients().iterator();

            while(var3.hasNext()) {
                Ingredient ingredient = (Ingredient)var3.next();
                ingredient.write(packetByteBuf);
            }

            packetByteBuf.writeItemStack(shapedRecipe.getOutput());
        }
    }
}
