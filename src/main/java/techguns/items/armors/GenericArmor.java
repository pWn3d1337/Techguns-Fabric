package techguns.items.armors;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import techguns.TGItems;
import techguns.api.render.ITGArmorSpecialRenderer;
import techguns.client.render.ITGItemRenderer;

public class GenericArmor extends ArmorItem implements FabricItem, ITGItemRenderer, ITGArmorSpecialRenderer {

    protected boolean hasInvRenderhack = false;
    protected boolean hasEntityModelRenderhack = false;
    protected boolean shouldRenderDefaultArmor = true;

    public GenericArmor(ArmorMaterial material, EquipmentSlot slot) {
        super(material, slot, new Item.Settings().group(TGItems.ITEM_GROUP_TECHGUNS));
    }

    @Override
    public boolean hasCustomRenderer() {
        return this.hasEntityModelRenderhack;
    }

    @Override
    public boolean shouldRenderDefaultArmor() {
        return this.shouldRenderDefaultArmor;
    }

    @Override
    public boolean shouldUseRenderHack(ItemStack stack) {
        return this.hasInvRenderhack;
    }

    @Override
    public boolean isModelBase(ItemStack stack) {
        return this.hasInvRenderhack;
    }
}
