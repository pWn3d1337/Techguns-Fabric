package techguns.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import techguns.inventory.AmmoBenchScreenHandler;
import techguns.recipes.AmmoBenchRecipe;

public class AmmoBenchScreen extends StoneCutterStyleScreen<AmmoBenchScreenHandler, AmmoBenchRecipe>{
    protected static final Identifier TEXTURE = new Identifier("textures/gui/container/stonecutter.png");

    public AmmoBenchScreen(AmmoBenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.getTexture());

        //overpaint original slot
        this.drawTexture(matrices, this.x+19, this.y+32, 19, 14, 18, 18);

        //draw 3 new slot backgrounds
        this.drawTexture(matrices, this.x+19, this.y+14, 19, 32, 18, 18);
        this.drawTexture(matrices, this.x+19, this.y+33, 19, 32, 18, 18);
        this.drawTexture(matrices, this.x+19, this.y+52, 19, 32, 18, 18);
    }

    @Override
    public Identifier getTexture() {
        return TEXTURE;
    }

    @Override
    protected void renderRecipeTooltip(AmmoBenchRecipe item, MatrixStack matrixStack, int x, int y) {
        this.renderTooltip(matrixStack, item.getOutput(), x, y);
    }

    @Override
    protected void renderRecipeItem(AmmoBenchRecipe item, int k, int m) {
        this.client.getItemRenderer().renderInGuiWithOverrides(item.getOutput(), k, m);
    }
}
