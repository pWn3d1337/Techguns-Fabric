package techguns.client.item;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.rei.impl.client.gui.widget.basewidgets.TextFieldWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import techguns.TGIdentifier;
import techguns.api.damagesystem.DamageType;

@Environment(value= EnvType.CLIENT)
public class TGArmorTooltipComponent implements TooltipComponent {
    public static final Identifier TEXTURE = new TGIdentifier("textures/gui/armor_value_icons.png");

    protected static final DamageType[] armorTypes = {DamageType.PHYSICAL, DamageType.PROJECTILE, DamageType.ENERGY,
            DamageType.EXPLOSION, DamageType.FIRE, DamageType.ICE, DamageType.LIGHTNING,
            DamageType.POISON, DamageType.DARK, DamageType.RADIATION};

    final TGArmorTooltipData data;

    public TGArmorTooltipComponent(TGArmorTooltipData data){
        this.data = data;
    }

    @Override
    public int getHeight() {
        return 25;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return 160;
    }

    @Override
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        final String FORMAT = "%,.01f";
        int offset=16;
        int i=0;
        textRenderer.draw(Formatting.WHITE      + String.format(FORMAT, this.data.armor_phys),       (float)(x + offset * i++), (float)y +14, -1, true, matrix, vertexConsumers, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        textRenderer.draw(Formatting.GRAY       + String.format(FORMAT, this.data.armor_projectile), (float)(x + offset * i++), (float)y +14, -1, true, matrix, vertexConsumers, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        textRenderer.draw(Formatting.AQUA       + String.format(FORMAT, this.data.armor_energy),     (float)(x + offset * i++), (float)y +14, -1, true, matrix, vertexConsumers, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        textRenderer.draw(Formatting.GOLD       + String.format(FORMAT, this.data.armor_explosion),  (float)(x + offset * i++), (float)y +14, -1, true, matrix, vertexConsumers, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        textRenderer.draw(Formatting.RED        + String.format(FORMAT, this.data.armor_fire),       (float)(x + offset * i++), (float)y +14, -1, true, matrix, vertexConsumers, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        textRenderer.draw(Formatting.BLUE       + String.format(FORMAT, this.data.armor_ice),        (float)(x + offset * i++), (float)y +14, -1, true, matrix, vertexConsumers, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        textRenderer.draw(Formatting.YELLOW     + String.format(FORMAT, this.data.armor_lightning),  (float)(x + offset * i++), (float)y +14, -1, true, matrix, vertexConsumers, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        textRenderer.draw(Formatting.DARK_GREEN + String.format(FORMAT, this.data.armor_poison),     (float)(x + offset * i++), (float)y +14, -1, true, matrix, vertexConsumers, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        textRenderer.draw(Formatting.DARK_GRAY  + String.format(FORMAT, this.data.armor_dark),       (float)(x + offset * i++), (float)y +14, -1, true, matrix, vertexConsumers, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        textRenderer.draw(Formatting.GREEN      + String.format(FORMAT, this.data.armor_radiation),  (float)(x + offset * i++), (float)y +14, -1, true, matrix, vertexConsumers, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, MatrixStack matrices, ItemRenderer itemRenderer, int z) {
        for (int i =0; i<10; i++) {
            this.draw(matrices, x+i*16, y, z, i*13, 0, 13, 13, 256, 16);
        }
    }

    private void draw(MatrixStack matrices, int x, int y, int z, int u, int v, int w, int h, int tex_width, int tex_height) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        DrawableHelper.drawTexture(matrices, x, y, z, u, v, w, h, tex_width, tex_height);
    }
}
