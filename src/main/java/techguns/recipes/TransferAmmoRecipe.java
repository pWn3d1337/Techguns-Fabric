package techguns.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import techguns.items.guns.GenericGun;
import techguns.items.guns.ammo.AmmoTypes;
import techguns.mixin.ShapedRecipeAccessor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class TransferAmmoRecipe extends NBTShapedRecipe {

    public static Serializer SERIALIZER;

    public TransferAmmoRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> ingredients, ItemStack output) {
        super(id, group, width, height, ingredients, output);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        if(!super.matches(craftingInventory, world)) return false;

        Set<String> variants = new HashSet<>();

        //check if all input guns have the same ammotype
        for(int i=0; i<craftingInventory.size(); i++){
            ItemStack stack=craftingInventory.getStack(i);
            if(!stack.isEmpty() && stack.getItem() instanceof GenericGun){
                GenericGun gun = (GenericGun) stack.getItem();
                String var = gun.getCurrentAmmoVariantKey(stack);
                variants.add(var);
            }
        }

        return variants.size() <=1;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        int input_ammo=0;
        String ammotype = AmmoTypes.TYPE_DEFAULT;
        for (int i =0; i<craftingInventory.size(); i++){
            ItemStack stack = craftingInventory.getStack(i);
            if(!stack.isEmpty() && stack.getItem() instanceof GenericGun){
                GenericGun g = (GenericGun) stack.getItem();
                input_ammo += g.getCurrentAmmo(stack);
                ammotype = g.getCurrentAmmoVariantKey(stack);
            }
        }
        ItemStack output = super.craft(craftingInventory);
        if(output.getItem() instanceof GenericGun){
            GenericGun out_gun = (GenericGun) output.getItem();
            if (input_ammo > out_gun.getClipsize()){
                input_ammo = out_gun.getClipsize();
            }
            if(!out_gun.getAmmoType().hasVariant(ammotype)){
                ammotype = AmmoTypes.TYPE_DEFAULT;
            }
            NbtCompound tag = output.getTag();
            if (tag!=null){
                tag.putShort("ammo", (short) input_ammo);
                tag.putString("ammovariant", ammotype);
            }
        }
        return output;
    }

    public static class Serializer implements RecipeSerializer<TransferAmmoRecipe> {

        @Override
        public TransferAmmoRecipe read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            Map<String, Ingredient> map = ShapedRecipeAccessor.invokeReadSymbols(JsonHelper.getObject(jsonObject, "key"));
            String[] strings = ShapedRecipeAccessor.invokeRemovePadding(ShapedRecipeAccessor.invokeGetPattern(JsonHelper.getArray(jsonObject, "pattern")));
            int i = strings[0].length();
            int j = strings.length;
            DefaultedList<Ingredient> defaultedList = ShapedRecipeAccessor.invokeCreatePatternMatrix(strings, map, i, j);
            ItemStack itemStack = NBTShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
            return new TransferAmmoRecipe(identifier, string, i, j, defaultedList, itemStack);
        }

        @Override
        public TransferAmmoRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            int i = packetByteBuf.readVarInt();
            int j = packetByteBuf.readVarInt();
            String string = packetByteBuf.readString(32767);
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < defaultedList.size(); ++k) {
                defaultedList.set(k, Ingredient.fromPacket(packetByteBuf));
            }

            ItemStack itemStack = packetByteBuf.readItemStack();
            return new TransferAmmoRecipe(identifier, string, i, j, defaultedList, itemStack);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, TransferAmmoRecipe nbtshapedRecipe) {
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
