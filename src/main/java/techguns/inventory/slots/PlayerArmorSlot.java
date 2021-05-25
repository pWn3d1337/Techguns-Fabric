package techguns.inventory.slots;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

/**
 * Slot for player armor (helment, chest, pants, boots)
 * Copied from PlayerScreenHandler Anonymous Inner class and made into real class
 */
public class PlayerArmorSlot extends Slot {
    public static final Identifier[] EMPTY_ARMOR_SLOT_TEXTURES =
            new Identifier[]{PlayerScreenHandler.EMPTY_BOOTS_SLOT_TEXTURE,
                    PlayerScreenHandler.EMPTY_LEGGINGS_SLOT_TEXTURE,
                    PlayerScreenHandler.EMPTY_CHESTPLATE_SLOT_TEXTURE,
                    PlayerScreenHandler.EMPTY_HELMET_SLOT_TEXTURE};

    protected final EquipmentSlot equipmentSlot;

    public PlayerArmorSlot(Inventory inventory, int index, int x, int y, EquipmentSlot slot) {
        super(inventory, index, x, y);
        equipmentSlot = slot;
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return equipmentSlot == MobEntity.getPreferredEquipmentSlot(stack);
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        ItemStack itemStack = this.getStack();
        return !itemStack.isEmpty() && !playerEntity.isCreative() && EnchantmentHelper.hasBindingCurse(itemStack) ? false : super.canTakeItems(playerEntity);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Pair<Identifier, Identifier> getBackgroundSprite() {
        return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
    }

}
