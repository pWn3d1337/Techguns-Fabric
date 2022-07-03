package techguns.inventory.slots;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import techguns.TGIdentifier;
import techguns.TGItems;

import java.util.HashMap;

public class SlotTagItem extends Slot {

    public static final Identifier SLOT_BG_INGOT_DARK = new TGIdentifier("gui/emptyslots/emptyslot_ingot_dark");
    public static final Identifier SLOT_BG_INGOT = new TGIdentifier("gui/emptyslots/emptyslot_ingot");
    public static final Identifier SLOT_BG_POWDER = new TGIdentifier("gui/emptyslots/emptyslot_powder");

    public static final HashMap<TagKey<Item>, Identifier> SLOT_BG_MAP = new HashMap<>();

    public static void initSlotBGMap() {
        SLOT_BG_MAP.put(TGItems.TAG_BULLET_CORE, SLOT_BG_INGOT_DARK);
        SLOT_BG_MAP.put(TGItems.TAG_BULLET_CASING, SLOT_BG_INGOT);
        SLOT_BG_MAP.put(TGItems.TAG_BULLET_POWDER, SLOT_BG_POWDER);
    }

    protected final TagKey<Item> allowed_tag;

    public SlotTagItem(TagKey<Item> tag, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.allowed_tag = tag;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return !stack.isEmpty() &&
                stack.isIn(allowed_tag);
    }

    @Nullable
    @Environment(EnvType.CLIENT)
    @Override
    public Pair<Identifier, Identifier> getBackgroundSprite() {
        Identifier id = SLOT_BG_MAP.getOrDefault(this.allowed_tag, null);
        if (id !=null){
            return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, id);
        }
        return super.getBackgroundSprite();
    }
}
