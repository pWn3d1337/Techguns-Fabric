package techguns.recipes;

import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import techguns.TGItems;
import techguns.Techguns;
import techguns.items.guns.GenericGun;
import techguns.items.guns.ammo.AmmoType;
import techguns.items.guns.ammo.AmmoVariant;
import techguns.util.InventoryUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AmmoChangeRecipe extends SpecialCraftingRecipe {

    public AmmoChangeRecipe(Identifier id) {
        super(id);
    }

    private class CraftingTarget {
        protected AmmoVariant ammoVariant;
        protected ItemStack gun;
        protected ItemStack ammo;
        protected GenericGun guntype;

        public CraftingTarget(AmmoVariant ammoVariant, ItemStack gun, ItemStack ammo, GenericGun guntype) {
            this.ammoVariant = ammoVariant;
            this.gun = gun;
            this.ammo = ammo;
            this.guntype = guntype;
        }
    }

    private CraftingTarget getTargetVariant(CraftingInventory inv){
        ItemStack gun_found = ItemStack.EMPTY;
        ItemStack ammo_found = ItemStack.EMPTY;
        for(int i = 0; i < inv.getWidth(); ++i) {
            for(int j = 0; j < inv.getHeight(); ++j) {
                ItemStack itemStack = inv.getStack(i + j * inv.getWidth());
                if(!itemStack.isEmpty()){
                    if (itemStack.getItem() instanceof GenericGun){
                        if (!gun_found.isEmpty()){
                            return null;
                        }
                        gun_found = itemStack;
                    } else {
                        if (!ammo_found.isEmpty()){
                            return null;
                        }
                        ammo_found = itemStack;
                    }
                }
            }
        }
        if (ammo_found.isEmpty() || gun_found.isEmpty()) return null;

        //we found a gun and another item, check if this is a valid ammo change
        GenericGun gun = (GenericGun) gun_found.getItem();
        AmmoType ammoType = gun.getAmmoType();
        AmmoVariant target_variant = null;
        ArrayList<AmmoVariant> variants = ammoType.getVariants();
        for (AmmoVariant variant : variants){
            if (variant.isAmmoForThisVariant(ammo_found)){
                target_variant = variant;
            }
        }
        if (target_variant != null){
            String current_variant = gun.getCurrentAmmoVariantKey(gun_found);
            if (!current_variant.equals(target_variant.getKey())){
                return new CraftingTarget(target_variant, gun_found, ammo_found, gun);
            }
        }

        return null;
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        return getTargetVariant(inv) !=null;
    }

    @Override
    public ItemStack craft(CraftingInventory inv) {
        CraftingTarget craftingTarget = getTargetVariant(inv);
        if (craftingTarget !=null){
            GenericGun guntype = craftingTarget.guntype;

            ItemStack newStack = craftingTarget.gun.copy();
            CompoundTag tag = newStack.getTag();
            tag.putString("ammovariant", craftingTarget.ammoVariant.getKey());

            int ammocount = guntype.getAmmoCount();
            tag.putShort("ammo", (short) (ammocount>1?1:guntype.getClipsize()));

            return newStack;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 2 && height >=2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Techguns.AMMO_CHANGE_SERIALIZER;
    }

    @Override
    public DefaultedList<ItemStack> getRemainingStacks(CraftingInventory inventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        List<ItemStack> itemsBack = new LinkedList<ItemStack>();
        for(int i = 0; i < defaultedList.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if(!stack.isEmpty() && stack.getItem() instanceof GenericGun){
                GenericGun gun = (GenericGun) stack.getItem();
                int oldAmmo = gun.getCurrentAmmo(inventory.getStack(i));

                String variant = gun.getCurrentAmmoVariantKey(stack);
                int ammos = gun.getAmmoType().getEmptyMag().length;

                for (int a=0;a<ammos; a++) {
                    if (!gun.getAmmoType().getEmptyMag()[a].isEmpty()) {

                        int bulletsBack = (int) Math.floor(oldAmmo / gun.getAmmoType().getShotsPerBullet(gun.getClipsize(), oldAmmo));
                        if (bulletsBack == gun.getAmmoType().getBulletsPerMag()){
                            defaultedList.set(i, TGItems.newStack(gun.getAmmoType().getAmmo(variant)[a],1));
                        } else {
                            if (bulletsBack > 0) {
                                ItemStack stack1 = TGItems.newStack(gun.getAmmoType().getBullet(gun.getCurrentAmmoVariant(stack))[a], bulletsBack);
                                itemsBack.add(stack1);
                            }
                            for (ItemStack mag : gun.getAmmoType().getEmptyMag()) {
                                if (defaultedList.get(i).isEmpty()) {
                                    defaultedList.set(i, TGItems.newStack(mag, 1));
                                } else {
                                    itemsBack.add(TGItems.newStack(mag, 1));
                                }
                            }
                        }
                    }
                }

            }
            else if (stack.getItem().hasRecipeRemainder()) {
                defaultedList.set(i, new ItemStack(stack.getItem().getRecipeRemainder()));
            }
        }

        if (!itemsBack.isEmpty()) {
            Iterator<ItemStack> it = itemsBack.iterator();
            for (int i = 0; i < defaultedList.size() && it.hasNext(); i++) {
                if (defaultedList.get(i).isEmpty()){
                    defaultedList.set(i, it.next());
                }
            }
        }

        return defaultedList;
    }
}
