package techguns.items;

import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import techguns.TGItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class AmmoBagItem extends GenericItem {
    protected static final int ITEM_BAR_COLOR = MathHelper.packRgb(0.4F, 0.4F, 1.0F);
    protected final int max_size;

    public AmmoBagItem(Settings settings, int max_size) {
        super(settings.maxCount(1));
        this.max_size = max_size;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            ItemStack itemStack = slot.getStack();

            if (itemStack.getItem() instanceof AmmoBagItem){

                boolean tryNext = true;
                while (tryNext) {
                    Optional<ItemStack> removed = removeFirstStack(stack);
                    if (removed.isPresent()) {
                        int added = addToBag(itemStack, removed.get());
                        removed.get().decrement(added);
                        if (!removed.get().isEmpty()) {
                            tryNext = false;
                            addToBag(stack, removed.get());
                        }
                    } else {
                        tryNext = false;
                    }
                }

            }

            return true;
        }
    }

    protected int getItemOccupancy(ItemStack itemStack){
        return (int) (Math.round(((double) itemStack.getCount() / (double) itemStack.getMaxCount()) * 64.0D));
    }

    protected int getOccupancy(ItemStack ammoBag){
        return getBundledStacks(ammoBag).mapToInt((ItemStack itemStack) -> {
            if(!itemStack.isEmpty()) {
                return getItemOccupancy(itemStack);
            } else {
                return 0;
            }
        }).sum();
    }

    protected int getRemainingCapacity(ItemStack ammoBag){
       return Math.max(this.max_size - getOccupancy(ammoBag), 0);
    }

    protected static boolean isAmmoItem(ItemStack stack){
        return stack.isIn(TGItems.TAG_AMMO);
    }

    /**
     * Return 0 if item is contained in passed amount, otherwise missing number of items is returned.
     * @param bag the ammobag item
     * @param item the item to check for removal
     * @return missing amount [0, item.getCount()]
     */
    public static int canRemoveAmount(ItemStack bag, ItemStack item) {
        int needed = item.getCount();
        NbtCompound nbtCompound = bag.getOrCreateNbt();
        if (!nbtCompound.contains("Items")) {
            return needed;
        } else {
            NbtList nbtList = nbtCompound.getList("Items", 10);
            if (nbtList.isEmpty()) {
                return needed;
            } else {
                int i = 0;
                while (needed > 0 && i < nbtList.size()) {
                    NbtCompound nbtCompound2 = nbtList.getCompound(i);
                    ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);

                    if (itemStack.isItemEqual(item)){
                        needed -= itemStack.getCount();
                    }
                    i++;
                }

                return Math.min(0, needed);
            }
        }
    }

    /**
     * Remove amount of item from ammobag
     * @param bag
     * @param item
     * @return amount that could not be removed
     */
    public static int removeAmount(ItemStack bag, ItemStack item) {
        int amount_to_remove = item.getCount();
        NbtCompound nbtCompound = bag.getOrCreateNbt();
        if (!nbtCompound.contains("Items")) {
            return amount_to_remove;
        } else {
            NbtList nbtList = nbtCompound.getList("Items", 10);
            if (nbtList.isEmpty()) {
                return amount_to_remove;
            } else {
                int i = 0;
                boolean stack_got_empty=false;
                while (amount_to_remove > 0 && i < nbtList.size()) {
                    NbtCompound nbtCompound2 = nbtList.getCompound(i);
                    ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);

                    if (itemStack.isItemEqual(item)){
                        int ammount_consumed = Math.min(itemStack.getCount(), amount_to_remove);
                        itemStack.decrement(ammount_consumed);
                        if (itemStack.isEmpty()) stack_got_empty=true;
                        amount_to_remove -= ammount_consumed;

                        NbtCompound nbtUpdatedStack = new NbtCompound();
                        itemStack.writeNbt(nbtUpdatedStack);
                        nbtList.setElement(i, nbtUpdatedStack);
                    }
                    i++;
                }
                if (stack_got_empty) {
                    //remove empty stacks
                    List<ItemStack> stacks = new ArrayList<>(nbtList.size());
                    for(int j=0; j< nbtList.size(); j++){
                        NbtCompound nbtCompound2 = nbtList.getCompound(j);
                        ItemStack stack = ItemStack.fromNbt(nbtCompound2);
                        if(!stack.isEmpty()){
                            stacks.add(stack);
                        }
                    }
                    nbtList.clear();
                    //rewrite back to box
                    for(int j = 0; j<stacks.size(); j++){
                        NbtCompound nbtCompound2 = new NbtCompound();
                        stacks.get(j).writeNbt(nbtCompound2);
                        nbtList.add(nbtCompound2);
                    }
                }

                return Math.max(0, amount_to_remove);
            }
        }
    }


    /**
     * Return amount of items added to bag
     * @param ammoBag - the bag
     * @param stack - item to be added
     * @return amount of added items
     */
    public int addToBag(ItemStack ammoBag, ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem().canBeNested() && isAmmoItem(stack)) {
            NbtCompound nbtCompound = ammoBag.getOrCreateNbt();
            if (!nbtCompound.contains("Items")) {
                nbtCompound.put("Items", new NbtList());
            }

            int space = this.getRemainingCapacity(ammoBag);
            int amountToAdd = Math.min(space, stack.getCount());

            if (amountToAdd <= 0) {
                return 0;

            } else {
                NbtList nbtList = nbtCompound.getList("Items", 10);
                /*Optional<NbtCompound> optional = canMergeStack(stack, nbtList);
                if (optional.isPresent()) {
                    NbtCompound nbtCompound2 = optional.get();
                    ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                    itemStack.increment(amountToAdd);
                    itemStack.writeNbt(nbtCompound2);
                    nbtList.remove(nbtCompound2);
                    nbtList.add(0, nbtCompound2);
                } else {
                    ItemStack itemStack2 = stack.copy();
                    itemStack2.setCount(amountToAdd);
                    NbtCompound nbtCompound3 = new NbtCompound();
                    itemStack2.writeNbt(nbtCompound3);
                    nbtList.add(0, nbtCompound3);
                }*/
                //Merge into
                for(int i=0; i<nbtList.size(); i++){
                    ItemStack stack1 = ItemStack.fromNbt((NbtCompound) nbtList.get(i));
                    if(stack1.isItemEqual(stack)){
                        int amount_added =  Math.min(stack1.getMaxCount()-stack1.getCount(), stack.getCount());
                        stack1.increment(amount_added);
                        stack.decrement(amount_added);

                        NbtCompound updated_stack1 = new NbtCompound();
                        stack1.writeNbt(updated_stack1);
                        nbtList.setElement(i, updated_stack1);
                    }
                }
                if (!stack.isEmpty()) {
                    NbtCompound nbtCompound3 = new NbtCompound();
                    stack.writeNbt(nbtCompound3);
                    nbtList.add(0, nbtCompound3);
                }
                return amountToAdd;
            }

        } else {
            return 0;
        }
    }

    protected static Optional<NbtCompound> canMergeStack(ItemStack stack, NbtList items) {
        Stream<NbtElement> nbtItems = items.stream();
        nbtItems = nbtItems.filter(NbtCompound.class::isInstance);
        return nbtItems.map(NbtCompound.class::cast).filter((item) -> {
            ItemStack other = ItemStack.fromNbt(item);
            return ItemStack.canCombine(other, stack) && (other.getCount() + stack.getCount()) <= stack.getMaxCount();
        }).findFirst();
    }

    protected Optional<ItemStack> removeFirstStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains("Items")) {
            return Optional.empty();
        } else {
            NbtList nbtList = nbtCompound.getList("Items", 10);
            if (nbtList.isEmpty()) {
                return Optional.empty();
            } else {
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
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {

        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            if (otherStack.isEmpty()){
                Optional<ItemStack> removedStack = removeFirstStack(stack);
                Objects.requireNonNull(cursorStackReference);
                removedStack.ifPresent(cursorStackReference::set);
            } else {
                otherStack.decrement(addToBag(stack, otherStack));
            }
            return true;
        } else {
            return false;
        }
    }

    protected static Stream<ItemStack> getBundledStacks(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null) {
            return Stream.empty();
        } else {
            NbtList nbtList = nbtCompound.getList("Items", 10);
            Stream<NbtElement> var10000 = nbtList.stream();
            Objects.requireNonNull(NbtCompound.class);
            return var10000.map(NbtCompound.class::cast).map(ItemStack::fromNbt);
        }
    }

    protected static boolean dropAllBundledItems(ItemStack stack, PlayerEntity player) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains("Items")) {
            return false;
        } else {
            if (player instanceof ServerPlayerEntity) {
                NbtList nbtList = nbtCompound.getList("Items", 10);

                for(int i = 0; i < nbtList.size(); ++i) {
                    NbtCompound nbtCompound2 = nbtList.getCompound(i);
                    ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                    player.dropItem(itemStack, true);
                }
            }

            stack.removeSubNbt("Items");
            return true;
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (dropAllBundledItems(itemStack, user)) {
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            return TypedActionResult.success(itemStack, world.isClient());
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        getBundledStacks(stack).forEach(defaultedList::add);
        return Optional.of(new BundleTooltipData(defaultedList, getRemainingCapacity(stack)));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.minecraft.bundle.fullness", new Object[]{getOccupancy(stack), this.max_size}).formatted(Formatting.GRAY));
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        ItemUsage.spawnItemContents(entity, getBundledStacks(entity.getStack()));
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return getOccupancy(stack) > 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.min(1 + 12 * getOccupancy(stack) / this.max_size, 13);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ITEM_BAR_COLOR;
    }

    public static int countItemInBag(ItemStack ammoBag, ItemStack item){
        return getBundledStacks(ammoBag).mapToInt((ItemStack itemStack) -> {
            if(!itemStack.isEmpty() && itemStack.isItemEqual(item)) {
                return itemStack.getCount();
            } else {
                return 0;
            }
        }).sum();
    }
}
