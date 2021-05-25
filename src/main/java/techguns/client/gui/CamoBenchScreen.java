package techguns.client.gui;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import techguns.inventory.CamoBenchScreenHandler;

public class CamoBenchScreen extends StoneCutterStyleScreen<CamoBenchScreenHandler, ItemStack> {
    protected static final Identifier TEXTURE = new Identifier("textures/gui/container/stonecutter.png");

    public CamoBenchScreen(CamoBenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public Identifier getTexture() {
        return TEXTURE;
    }

    @Override
    protected void renderRecipeTooltip(ItemStack item, MatrixStack matrices, int x, int y) {
        CompoundTag tag = item.getTag();
        if (tag!=null) {
            String camoname = tag.getString("camo");
            if (camoname != null) {
                this.renderTooltip(matrices, new TranslatableText(camoname.replace(':', '.')), x, y);
            }
        }
    }

    @Override
    protected void renderRecipeItem(ItemStack item, int k, int m) {
        this.client.getItemRenderer().renderInGuiWithOverrides(item, k, m);
    }
}
