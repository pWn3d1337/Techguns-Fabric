package techguns.client.models.armor;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.EquipmentSlot;

import java.util.function.Function;

public class ModelExoSuitOld extends BipedEntityModel {
    public ModelExoSuitOld(ModelPart root) {
        super(root);
    }

    public ModelExoSuitOld(ModelPart root, Function renderLayerFactory) {
        super(root, renderLayerFactory);
    }

    public static ModelData getModelData(Dilation dilation, EquipmentSlot slot) {
        return getModelData(dilation, 0F, 0F, 0F,0F, 0F, 0F, slot);
    }

    public static ModelData getModelData(Dilation dilation, float offsetX, float offsetX_left, float offsetX_right, float offsetY, float offsetY2, float offsetZ, EquipmentSlot slot) {
        ModelData modelData = BipedEntityModel.getModelData(dilation, offsetY);
        ModelPartData modelPartData = modelData.getRoot();

        Dilation scale = Dilation.NONE;

        float ArmOffsetX = 5.0F;
        float ArmOffsetY = -2.0F;
        float LegOffsetX = 1.9F;
        float LegOffsetY = -12.0F;

        ModelPartLegacy RA4 = new ModelPartLegacy(24, 32);
        RA4.addCuboid(0F, 0F, 0F, 6, 2, 6, scale);
        RA4.setPivot(-9F + ArmOffsetX, 6.5F + ArmOffsetY, -3F);
        RA4.mirror = true;
        RA4.setRotation( 0F, 0F, 0F);

        ModelPartLegacy RA6 = new ModelPartLegacy(48, 32);
        RA6.addCuboid(0F, 0F, 0F, 2, 8, 2, scale);
        RA6.setPivot(-9.5F + ArmOffsetX, 3.5F + ArmOffsetY, -1F);
        RA6.mirror = true;
        RA6.setRotation( 0F, 0F, 0F);

        ModelPartLegacy RA7 = new ModelPartLegacy(27, 41);
        RA7.addCuboid(0F, 0F, 0F, 2, 3, 3, scale);
        RA7.setPivot(-10F + ArmOffsetX, 6F + ArmOffsetY, -1.5F);
        RA7.mirror = true;
        RA7.setRotation( 0F, 0F, 0F);

        ModelPartLegacy RA8 = new ModelPartLegacy(0, 32);
        RA8.addCuboid(0F, 0F, 0F, 6, 5, 6, scale);
        RA8.setPivot(-9.5F + ArmOffsetX, -1.5F + ArmOffsetY, -3F);
        RA8.mirror = true;
        RA8.setRotation( 0F, 0F, 0F);

        ModelPartLegacy LA2 = new ModelPartLegacy(27, 41);
        LA2.addCuboid(3F, 4F, -1.5F, 2, 3, 3, scale);
        LA2.setPivot(5F - ArmOffsetX, 2F + ArmOffsetY, 0F);
        LA2.mirror = true;
        LA2.setRotation( 0F, 0F, 0F);

        ModelPartLegacy LA5 = new ModelPartLegacy(0, 32);
        LA5.addCuboid(-1.5F, -1.5F, -3F, 6, 5, 6, scale);
        LA5.setPivot(5F - ArmOffsetX, 0F + ArmOffsetY, 0F);
        LA5.mirror = true;
        LA5.setRotation( 0F, 0F, 0F);

        ModelPartLegacy LA6 = new ModelPartLegacy(24, 32);
        LA6.addCuboid(-2F, 4.5F, -3F, 6, 2, 6, scale);
        LA6.setPivot(5F - ArmOffsetX, 2F + ArmOffsetY, 0F);
        LA6.mirror = true;
        LA6.setRotation( 0F, 0F, 0F);

        ModelPartLegacy LA7 = new ModelPartLegacy(48, 32);
        LA7.addCuboid(2.5F, 0.5F, -1F, 2, 8, 2, scale);
        LA7.setPivot(5F - ArmOffsetX, 3F + ArmOffsetY, 0F);
        LA7.mirror = true;
        LA7.setRotation( 0F, 0F, 0F);

        ModelPartLegacy B2 = new ModelPartLegacy(0, 60);
        B2.addCuboid(-0F, 0F, -2F, 10, 2, 2, scale);
        B2.setPivot(-3F, 1F, 3.533333F);
        B2.mirror = true;
        B2.setRotation( 0F, 0F, 0F);

        ModelPartLegacy B3 = new ModelPartLegacy(38, 40);
        B3.addCuboid(-0F, 0F, -2F, 3, 9, 2, scale);
        B3.setPivot(0.5F, 3F, 3.533333F);
        B3.mirror = true;
        B3.setRotation( 0F, 0F, 0F);

        ModelPartLegacy P1 = new ModelPartLegacy(0, 43);
        P1.addCuboid(0F, 0F, 0F, 10, 2, 6, scale);
        P1.setPivot(-5F, 11F, -3F);
        P1.mirror = true;
        P1.setRotation( 0F, 0F, 0F);

        ModelPartLegacy P3 = new ModelPartLegacy(0, 52);
        P3.addCuboid(0F, 0F, 0F, 2, 2, 6, scale);
        P3.setPivot(-1F, 13F, -3F);
        P3.mirror = true;
        P3.setRotation( 0F, 0F, 0F);

        ModelPartLegacy RL2 = new ModelPartLegacy(27, 41);
        RL2.addCuboid(0F, 0F, 0F, 2, 3, 3, scale);
        RL2.setPivot(-6F + LegOffsetX, 16.5F + LegOffsetY, -1.5F);
        RL2.mirror = true;
        RL2.setRotation( 0F, 0F, 0F);

        ModelPartLegacy RL3 = new ModelPartLegacy(17, 52);
        RL3.addCuboid(0F, 0F, 0F, 5, 2, 6, scale);
        RL3.setPivot(-5F + LegOffsetX, 17F + LegOffsetY, -3F);
        RL3.mirror = true;
        RL3.setRotation( 0F, 0F, 0F);

        ModelPartLegacy RL4 = new ModelPartLegacy(56, 32);
        RL4.addCuboid(0F, 0F, 0F, 2, 10, 2, scale);
        RL4.setPivot(-5.5F + LegOffsetX, 12.5F + LegOffsetY, -1F);
        RL4.mirror = true;
        RL4.setRotation( 0F, 0F, 0F);

        ModelPartLegacy LL1 = new ModelPartLegacy(56, 32);
        LL1.addCuboid(0F, 0F, 0F, 2, 10, 2, scale);
        LL1.setPivot(3.5F - LegOffsetX, 12.5F + LegOffsetY, -1F);
        LL1.mirror = true;
        LL1.setRotation( 0F, 0F, 0F);

        ModelPartLegacy LL3 = new ModelPartLegacy(27, 41);
        LL3.addCuboid(0F, 0F, 0F, 2, 3, 3, scale);
        LL3.mirror = true;
        LL3.setRotation( 0F, 0F, 0F);

        ModelPartLegacy LL4 = new ModelPartLegacy(17, 52);
        LL4.addCuboid(0F, 0F, 0F, 5, 2, 6, scale);
        LL4.setPivot(0F - LegOffsetX, 17F + LegOffsetY, -3F);
        LL4.mirror = true;
        LL4.setRotation( 0F, 0F, 0F);

        ModelPartLegacy RB1 = new ModelPartLegacy(56, 51);
        RB1.addCuboid(-2F, 0F, -2F, 3, 3, 1, scale);
        RB1.setPivot(-1.5F + LegOffsetX, 21.5F + LegOffsetY, -1.5F);
        RB1.mirror = true;
        RB1.setRotation( 0F, 0F, 0F);

        ModelPartLegacy RB2 = new ModelPartLegacy(40, 56);
        RB2.addCuboid(0F, 0F, 0F, 6, 2, 6, scale);
        RB2.setPivot(-5F + LegOffsetX, 22F + LegOffsetY, -3F);
        RB2.mirror = true;
        RB2.setRotation( 0F, 0F, 0F);

        ModelPartLegacy LB1 = new ModelPartLegacy(56, 51);
        LB1.addCuboid(-2F, 0F, -2F, 3, 3, 1, scale);
        LB1.setPivot(2.5F - LegOffsetX, 21.5F + LegOffsetY, -1.5F);
        LB1.mirror = true;
        LB1.setRotation( 0F, 0F, 0F);

        ModelPartLegacy LB2 = new ModelPartLegacy(40, 56);
        LB2.addCuboid(0F, 0F, 0F, 6, 2, 6, scale);
        LB2.setPivot(-1F - LegOffsetX, 22F + LegOffsetY, -3F);
        LB2.mirror = true;
        LB2.setRotation( 0F, 0F, 0F);

        if (slot == EquipmentSlot.HEAD) {

        } else if (slot == EquipmentSlot.CHEST) {
            var right_arm = modelPartData.getChild(EntityModelPartNames.RIGHT_ARM);
            RA4.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA4");
            RA6.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA6");
            RA7.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA7");
            RA8.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA8");

            var left_arm = modelPartData.getChild(EntityModelPartNames.LEFT_ARM);
            LA2.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA2");
            LA5.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA5");
            LA6.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA6");
            LA7.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA7");

            var body =modelPartData.getChild(EntityModelPartNames.HEAD);
            B2.addTo(body, EntityModelPartNames.BODY + "_B2");
            B3.addTo(body, EntityModelPartNames.BODY + "_B3");

        } else if (slot == EquipmentSlot.LEGS){
            var body =modelPartData.getChild(EntityModelPartNames.HEAD);
            P1.addTo(body, EntityModelPartNames.BODY + "_P1");
            P3.addTo(body, EntityModelPartNames.BODY + "_P3");

            var right_leg =modelPartData.getChild(EntityModelPartNames.RIGHT_LEG);
            RL2.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RL2");
            RL3.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RL3");
            RL4.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RL4");

            var left_leg =modelPartData.getChild(EntityModelPartNames.LEFT_LEG);
            LL1.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LL1");
            LL3.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LL3");
            LL4.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LL4");

        } else if (slot == EquipmentSlot.FEET){
            var right_leg =modelPartData.getChild(EntityModelPartNames.RIGHT_LEG);
            RB1.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RB1");
            RB2.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RB2");

            var left_leg =modelPartData.getChild(EntityModelPartNames.LEFT_LEG);
            LB1.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LB1");
            LB2.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LB2");
        }

        return modelData;
    }
}
