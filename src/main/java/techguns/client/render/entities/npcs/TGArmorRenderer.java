package techguns.client.render.entities.npcs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import techguns.TGCamos;
import techguns.TGIdentifier;
import techguns.api.ICamoChangeable;
import techguns.items.armors.GenericArmor;

import java.util.List;
import java.util.function.Function;

@Environment(value= EnvType.CLIENT)
public class TGArmorRenderer implements ArmorRenderer {
    protected final Function<ModelPart, BipedEntityModel<LivingEntity>> modelConstructor;
    protected final EntityModelLayer layer;

    //used as fallback texture
    protected static final Identifier armorTexure = new TGIdentifier("textures/armors/powerarmor.png");
    protected static final Identifier DEFAULT_CAMO = new TGIdentifier("default");

    protected final EquipmentSlot slot;

    protected BipedEntityModel<LivingEntity> cached_model = null;

    public TGArmorRenderer(Function<ModelPart, BipedEntityModel<LivingEntity>> modelConstructor, EntityModelLayer layer, GenericArmor armor){
        this.modelConstructor = modelConstructor;
        this.layer = layer;
        this.slot = armor.getSlotType();
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (cached_model == null) {
            cached_model = this.modelConstructor.apply(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(this.layer));
        }
        renderArmor(matrices, vertexConsumers, entity, this.slot, light, cached_model, contextModel);
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> model, BipedEntityModel<LivingEntity> contextModel) {
        ItemStack armor = entity.getEquippedStack(armorSlot);
        if(model != null) {
            contextModel.setAttributes(model);
            this.setVisible(model, armorSlot);
            //this cast is save, getModel will return null when not an ArmorItem
            ArmorItem armorItem = (ArmorItem) armor.getItem();

            Identifier texture = armorTexure;
            if (armorItem instanceof ICamoChangeable) {
                ICamoChangeable camoItem = (ICamoChangeable) armorItem;
                List<Identifier> camos = camoItem.getCurrentCamoTextures(armor);
                if (camos !=null && camos.size()>0){
                    texture = camos.get(0);
                } else {
                    //Try default camo
                    TGCamos.CamoEntry entry = TGCamos.getCamoEntry(armorItem, DEFAULT_CAMO);
                    if (entry!=null && !entry.textures.isEmpty()) {
                        texture = entry.textures.get(0);
                    }
                }
            }
            this.renderArmorParts(matrices, vertexConsumers, texture, light, armorItem, false, model,  1.0f, 1.0f, 1.0f, null);
        }
    }

    protected void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Identifier texture, int light, ArmorItem item, boolean usesSecondLayer, BipedEntityModel<LivingEntity> model,  float red, float green, float blue, @Nullable String overlay) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), false, usesSecondLayer);
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
    }

    protected void setVisible(BipedEntityModel<LivingEntity> bipedModel, EquipmentSlot slot) {
        bipedModel.setVisible(false);
        switch (slot) {
            case HEAD: {
                bipedModel.head.visible = true;
                bipedModel.hat.visible = true;
                break;
            }
            case CHEST: {
                bipedModel.body.visible = true;
                bipedModel.rightArm.visible = true;
                bipedModel.leftArm.visible = true;
                break;
            }
            case LEGS: {
                bipedModel.body.visible = true;
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
                break;
            }
            case FEET: {
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
            }
        }
    }
}
