package techguns.client.render.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import techguns.client.render.math.TGMatrixOps;


public class RenderArmorItem extends RenderItemBase {

    protected BipedEntityModel modelBiped;

    public RenderArmorItem(BipedEntityModel model, Identifier texture, EquipmentSlot armorSlot) {
        super(null, texture);
        this.modelBiped=model;

        /*modelBiped.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
        modelBiped.bipedHeadwear.showModel = armorSlot == EntityEquipmentSlot.HEAD;

        modelBiped.bipedBody.showModel = armorSlot == EntityEquipmentSlot.CHEST || armorSlot == EntityEquipmentSlot.LEGS;
        modelBiped.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
        modelBiped.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
        modelBiped.bipedRightLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET;
        modelBiped.bipedLeftLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET;*/

        this.scale_thirdp = 1.0f;
        this.scale_ego = 1.0f;
        this.scale_gui = 1.5f;
        this.scale_itemframe = 1.5f;

        if (armorSlot == EquipmentSlot.HEAD) {
            this.scale_ground = 1.25f;
        } else {
            this.scale_ground = 1.5f;
        }

        //this.translateBase[1]=0;
        switch(armorSlot) {
            case HEAD:
                this.translateBase[1]=-0.54f;
                break;
            case CHEST:
                this.translateBase[1]= -0.9f;
                break;
            case LEGS:
                this.translateBase[1]= -1.29f;
                break;
            case FEET:
                this.translateBase[1]= -1.42f;
                break;
            default:
                break;
        }

        this.translateType[4][2] = 0.07f; //Item Frame, Z

        this.translateType[0][1] = 0.1f;
        this.translateType[1][1] = 0.04f;

    }

    @Override
    public void renderItem(LivingEntity elb, ModelTransformation.Mode transform, MatrixStack matrices, ItemStack stack, boolean leftHanded, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel bakedModel) {
        matrices.push();

        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);

        this.applyTranslation(matrices, transform);

        if (ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND == transform || ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND == transform) {

        } else if (ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND == transform || ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND == transform) {

        } else if (ModelTransformation.Mode.GUI == transform) {
            TGMatrixOps.rotate(matrices, 180.0f, 0, 1f, 0);

        } else if (ModelTransformation.Mode.GROUND == transform) {

        } else if (ModelTransformation.Mode.FIXED == transform) {

        }

        this.setBaseScale(elb,matrices, transform);
        this.setBaseRotation(matrices, transform);
        this.applyBaseTranslation(matrices);


        modelBiped.render(matrices, vertexConsumers.getBuffer(this.modelBiped.getLayer(this.texture)), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
    }


}
