package techguns.items;

import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import techguns.TGItems;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class AmmoBagItemOld extends BundleItem {
    protected static final int MAX_SIZE = 512;

    public AmmoBagItemOld(Item.Settings settings) {
        super(settings.group(TGItems.ITEM_GROUP_TECHGUNS));
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            if (otherStack.isEmpty()) {
                Optional<ItemStack> removedStack = removeFirstStack(stack);
                Objects.requireNonNull(cursorStackReference);
                removedStack.ifPresent(cursorStackReference::set);
            } else {
                otherStack.decrement(addToAmmoBag(stack, otherStack));
            }
            return true;
        } else {
            return false;
        }
    }

    public static Optional<ItemStack> removeFirstStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains("Items")) {
            return Optional.empty();
        } else {
            NbtList nbtList = nbtCompound.getList("Items", 10);
            if (nbtList.isEmpty()) {
                return Optional.empty();
            } else {
                int i = 0;
                NbtCompound nbtCompound2 = nbtList.getCompound(0);
                ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                nbtList.remove(0);
                if (nbtList.isEmpty()) {
                    stack.removeSubNbt("Items");
                }
                return Optional.of(itemStack);
            }
        }
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            ItemStack itemStack = slot.getStack();
            if (itemStack.isEmpty()) {
                removeFirstStack(stack).ifPresent((removedStack) -> {
                    addToAmmoBag(stack, slot.insertStack(removedStack));
                });
            } else if (itemStack.getItem().canBeNested()) {
                int i = (MAX_SIZE - getBundleOccupancy(stack)) / getItemOccupancy(itemStack);
                addToAmmoBag(stack, slot.takeStackRange(itemStack.getCount(), i, player));
            }

            return true;
        }
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.min(1 + 12 * getBundleOccupancy(stack) / MAX_SIZE, 13);
    }

    protected static Optional<NbtCompound> canMergeStack(ItemStack stack, NbtList items) {
        if (!stack.isEmpty() && stack.getItem() instanceof AmmoBagItemOld) {
            return Optional.empty();
        } else {
            Stream<NbtElement> var10000 = items.stream();
            Objects.requireNonNull(NbtCompound.class);
            var10000 = var10000.filter(NbtCompound.class::isInstance);
            Objects.requireNonNull(NbtCompound.class);
            return var10000.map(NbtCompound.class::cast).filter((item) -> {
                return ItemStack.canCombine(ItemStack.fromNbt(item), stack);
            }).findFirst();
        }
    }

    public static int getBundleOccupancy(ItemStack stack) {
        /*return getBundledStacks(stack).mapToInt((itemStack) -> {
            return getItemOccupancy(itemStack) * itemStack.getCount();
        }).sum();*/
        return 0;
    }

    public static int getItemOccupancy(ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem() instanceof AmmoBagItemOld) {
            return 4 + getBundleOccupancy(stack);
        } else {
            return MAX_SIZE / stack.getMaxCount();
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add((new TranslatableText("item.minecraft.bundle.fullness", new Object[]{getBundleOccupancy(stack), MAX_SIZE})).formatted(Formatting.GRAY));
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        //Stream<ItemStack> var10000 = BundleItem.getBundledStacks(stack);
        Objects.requireNonNull(defaultedList);
        //var10000.forEach(defaultedList::add);
        return Optional.of(new BundleTooltipData(defaultedList, getBundleOccupancy(stack)));
    }

    public static int addToAmmoBag(ItemStack bundle, ItemStack stack) {
        boolean inTag = true; //stack.isIn(TGItems.TAG_AMMO);
        if (!stack.isEmpty() && stack.getItem().canBeNested() && inTag) {
            NbtCompound nbtCompound = bundle.getOrCreateNbt();
            if (!nbtCompound.contains("Items")) {
                nbtCompound.put("Items", new NbtList());
            }

            int i = getBundleOccupancy(bundle);
            int j = getItemOccupancy(stack);
            int k = Math.min(MAX_SIZE - i, stack.getCount());
            if (k == 0) {
                return 0;
            } else {
                NbtList nbtList = nbtCompound.getList("Items", 10);
                Optional<NbtCompound> optional = canMergeStack(stack, nbtList);
                if (optional.isPresent()) {
                    NbtCompound nbtCompound2 = (NbtCompound)optional.get();
                    ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                    itemStack.increment(k);
                    itemStack.writeNbt(nbtCompound2);
                    nbtList.remove(nbtCompound2);
                    nbtList.add(0, (NbtElement)nbtCompound2);
                } else {
                    ItemStack itemStack2 = stack.copy();
                    itemStack2.setCount(k);
                    NbtCompound nbtCompound3 = new NbtCompound();
                    itemStack2.writeNbt(nbtCompound3);
                    nbtList.add(0, (NbtElement)nbtCompound3);
                }

                return k;
            }
        } else {
            return 0;
        }
    }
}
