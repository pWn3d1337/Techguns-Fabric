package techguns.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.mixin.ShapelessRecipeAccessor;

import java.util.Iterator;

public class MiningHeadUpgradeRecipe extends ShapelessRecipe {

    public static MiningHeadUpgradeRecipe.Serializer SERIALIZER=null;

    public MiningHeadUpgradeRecipe(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input) {
        super(id, group, output, input);
    }

    private static class RecipeResult {
        protected static final RecipeResult INVALID = new RecipeResult(-1, ItemStack.EMPTY);

        protected int target_level;
        protected ItemStack source_gun;

        public RecipeResult(int target_level, ItemStack source_gun) {
            this.target_level = target_level;
            this.source_gun = source_gun;
        }

        protected boolean isValid(){
            return target_level > -1 && !source_gun.isEmpty();
        }
    }

    private RecipeResult getTargetLevel(CraftingInventory inv){
        ItemStack gun_found = ItemStack.EMPTY;
        ItemStack head_found = ItemStack.EMPTY;
        for(int i = 0; i < inv.getWidth(); ++i) {
            for(int j = 0; j < inv.getHeight(); ++j) {
                ItemStack itemStack = inv.getStack(i + j * inv.getWidth());
                if(!itemStack.isEmpty()){
                    if (itemStack.getItem() instanceof GenericGunMeleeCharge){
                        if (!gun_found.isEmpty()){
                            return RecipeResult.INVALID;
                        }
                        gun_found = itemStack;
                    } else {
                        if (!head_found.isEmpty()){
                            return RecipeResult.INVALID;
                        }
                        head_found = itemStack;
                    }
                }
            }
        }
        if (head_found.isEmpty() || gun_found.isEmpty()) return RecipeResult.INVALID;

        //we found a gun and another item, check if this is a valid head swap
        GenericGunMeleeCharge gun = (GenericGunMeleeCharge) gun_found.getItem();

        int target_level = gun.getMiningHeadLevelForItem(head_found);
        int current_level = gun.getMiningHeadLevel(gun_found);

        if (target_level >= 0 && target_level != current_level){
            return new RecipeResult(target_level, gun_found);
        }
        return RecipeResult.INVALID;
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        return super.matches(inv, world) && getTargetLevel(inv).isValid();
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if(!stack.isEmpty() && stack.getItem() instanceof GenericGunMeleeCharge){
                GenericGunMeleeCharge gun = (GenericGunMeleeCharge) stack.getItem();
                int oldlevel = gun.getMiningHeadLevel(stack);

                ItemStack headback = gun.getMiningHeadItemForLevel(oldlevel);
                if(!headback.isEmpty()){
                    defaultedList.set(i, headback);
                }
            }
            else if (stack.getItem().hasRecipeRemainder()) {
                defaultedList.set(i, new ItemStack(stack.getItem().getRecipeRemainder()));
            }
        }
        return defaultedList;
    }

    @Override
    public ItemStack craft(CraftingInventory inv) {
        ItemStack result = super.craft(inv);

        RecipeResult res = getTargetLevel(inv);
        if (res.target_level > -1){
            NbtCompound tag = res.source_gun.getNbt().copy();
            tag.putByte("mininghead", (byte) res.target_level);
            result.setNbt(tag);
            return result;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Serializer implements RecipeSerializer<MiningHeadUpgradeRecipe> {
        public MiningHeadUpgradeRecipe read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            DefaultedList<Ingredient> defaultedList = getIngredients(JsonHelper.getArray(jsonObject, "ingredients"));
            if (defaultedList.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (defaultedList.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe");
            } else {
                ItemStack itemStack = NBTShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
                return new MiningHeadUpgradeRecipe(identifier, string, itemStack, defaultedList);
            }
        }

        public MiningHeadUpgradeRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String string = packetByteBuf.readString(32767);
            int i = packetByteBuf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);

            for(int j = 0; j < defaultedList.size(); ++j) {
                defaultedList.set(j, Ingredient.fromPacket(packetByteBuf));
            }

            ItemStack itemStack = packetByteBuf.readItemStack();
            return new MiningHeadUpgradeRecipe(identifier, string, itemStack, defaultedList);
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.of();

            for(int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (!ingredient.isEmpty()) {
                    defaultedList.add(ingredient);
                }
            }

            return defaultedList;
        }

        public void write(PacketByteBuf packetByteBuf, MiningHeadUpgradeRecipe shapelessRecipe) {
            ShapelessRecipeAccessor rec = (ShapelessRecipeAccessor) shapelessRecipe;
            packetByteBuf.writeString(rec.getGroup());
            packetByteBuf.writeVarInt(rec.getInput().size());
            Iterator var3 = rec.getInput().iterator();

            while(var3.hasNext()) {
                Ingredient ingredient = (Ingredient)var3.next();
                ingredient.write(packetByteBuf);
            }

            packetByteBuf.writeItemStack(rec.getOutput());
        }
    }
}
