package techguns.client.render.entities.npcs;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import techguns.TGArmors;
import techguns.TGIdentifier;

public class TGArmorFeatureRenderer <T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>>
        extends FeatureRenderer<T, M> {

    protected A model;

    protected static final Identifier armorTexure = new TGIdentifier("textures/armors/powerarmor.png");

    public TGArmorFeatureRenderer(FeatureRendererContext<T, M> context, A model) {
        super(context);
        this.model = model;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        this.renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.CHEST, light);
        this.renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.LEGS, light);
        this.renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.FEET, light);
        this.renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.HEAD, light);
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light) {
        ItemStack armor = entity.getEquippedStack(armorSlot);
        if(!armor.isEmpty() && armor.getItem() == TGArmors.T3_POWER_HELMET){ //todo check for ArmorItem
            this.getContextModel().setAttributes(model);
            this.setVisible(model, armorSlot);
            ArmorItem armorItem = (ArmorItem) armor.getItem();

            this.renderArmorParts(matrices, vertexConsumers, light, armorItem, false, model, EquipmentSlot.LEGS==armorSlot, 1.0f, 1.0f, 1.0f, null);
        }
    }

    protected void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean usesSecondLayer, A model, boolean legs, float red, float green, float blue, @Nullable String overlay) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(armorTexure), false, usesSecondLayer);
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
    }

    protected void setVisible(A bipedModel, EquipmentSlot slot) {
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
