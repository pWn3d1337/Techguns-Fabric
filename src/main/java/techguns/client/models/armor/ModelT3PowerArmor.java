package techguns.client.models.armor;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.EquipmentSlot;

import java.util.function.Function;

public class ModelT3PowerArmor extends BipedEntityModel {

    public ModelT3PowerArmor(ModelPart root) {
        super(root);
    }

    public ModelT3PowerArmor(ModelPart root, Function renderLayerFactory) {
        super(root, renderLayerFactory);
    }

    public static ModelData getModelData(Dilation dilation, EquipmentSlot slot) {
        return ModelT3PowerArmor.getModelData(dilation, 0F, 0F, 0F, slot);
    }

    public static ModelData getModelData(Dilation dilation, float offsetX, float offsetY, EquipmentSlot slot) {
        return ModelT3PowerArmor.getModelData(dilation, offsetX, offsetY, 0F, slot);
    }

    public static ModelData getModelData(Dilation dilation, float offsetX, float offsetY, float offsetZ, EquipmentSlot slot) {
        //this method is a bit hack for item rendering and the offsets are sometimes used arbitrary
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        Dilation scale = Dilation.NONE;

        float ArmOffsetX = 5F; //5.0F;
        float ArmOffsetY = -2F; //-2.0F;
        float LegOffsetX = 1.9F;
        float LegOffsetY = -12.0F;

        if (slot == EquipmentSlot.HEAD) {
            var H04 = new ModelPartLegacy(0, 28);
            H04.addCuboid(-3F, 0F, 0F, 3, 2, 2, scale);
            H04.setPivot(-2F, -2F, -5.5F);
            H04.setTextureSize(128, 64);
            H04.mirror = false;
            H04.setRotation(0F, 0F, 0F);

            var H06 = new ModelPartLegacy(89, 27);
            H06.addCuboid(0F, 0F, 0F, 1, 2, 8, scale);
            H06.setPivot(-5F, -2F, -3.5F);
            H06.setTextureSize(128, 64);
            H06.mirror = false;
            H06.setRotation(0F, 0F, 0F);

            var H03 = new ModelPartLegacy(0, 33);
            H03.addCuboid(0F, 0F, 0F, 3, 2, 2, scale);
            H03.setPivot(2F, -2F, -5.5F);
            H03.setTextureSize(128, 64);
            H03.mirror = false;
            H03.setRotation(0F, 0F, 0F);

            var H09 = new ModelPartLegacy(24, 23);
            H09.addCuboid(0F, 0F, 0F, 4, 3, 1, scale);
            H09.setPivot(-2F, -2.5F, 4.5F);
            H09.setTextureSize(128, 64);
            H09.mirror = false;
            H09.setRotation(0F, 0F, 0F);

            var H08 = new ModelPartLegacy(19, 19);
            H08.addCuboid(0F, 0F, 0F, 9, 2, 1, scale);
            H08.setPivot(-4.5F, -2F, 4F);
            H08.setTextureSize(128, 64);
            H08.mirror = false;
            H08.setRotation(0F, 0F, 0F);

            var H05 = new ModelPartLegacy(13, 27);
            H05.addCuboid(0F, 0F, 0F, 1, 2, 8, scale);
            H05.setPivot(4F, -2F, -3.5F);
            H05.setTextureSize(128, 64);
            H05.mirror = false;
            H05.setRotation(0F, 0F, 0F);

            var H10 = new ModelPartLegacy(28, 0);
            H10.addCuboid(-1F, -1F, 0F, 2, 2, 4, scale);
            H10.setPivot(-5.5F, -6F, -3F);
            H10.setTextureSize(128, 64);
            H10.mirror = false;
            H10.setRotation(0F, 0F, 0.7853982F);

            var H02 = new ModelPartLegacy(0, 19);
            H02.addCuboid(0F, -0.05F, 0F, 4, 4, 3, scale);
            H02.setPivot(-2F, -3F, -6F);
            H02.setTextureSize(128, 64);
            H02.mirror = true;
            H02.setRotation(0F, 0F, 0F);

            var H07 = new ModelPartLegacy(0, 5);
            H07.addCuboid(0F, 0F, 0F, 2, 2, 1, scale);
            H07.setPivot(0.99F, -8F, -5F);
            H07.setTextureSize(128, 64);
            H07.mirror = false;
            H07.setRotation(0F, 0F, 0F);

            ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F,-8.5F,-4.5F, 9, 9, 9, dilation).mirrored(false), ModelTransform.pivot(-4.5F +offsetX, -8.5F+offsetY, -4.5F));

            H02.addTo(head, EntityModelPartNames.HEAD + "_H02");
            H03.addTo(head, EntityModelPartNames.HEAD + "_H03");
            H04.addTo(head, EntityModelPartNames.HEAD + "_H04");
            H05.addTo(head, EntityModelPartNames.HEAD + "_H05");
            H06.addTo(head, EntityModelPartNames.HEAD + "_H06");
            H07.addTo(head, EntityModelPartNames.HEAD + "_H07");
            H08.addTo(head, EntityModelPartNames.HEAD + "_H08");
            H09.addTo(head, EntityModelPartNames.HEAD + "_H09");
            H10.addTo(head, EntityModelPartNames.HEAD + "_H10");
        } else {
            modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.NONE);
        }

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);

        if (slot == EquipmentSlot.CHEST) {
            var B01 = new ModelPartLegacy(41, 19);
            B01.addCuboid(-4F, 0F, -2F, 9, 12, 5);
            B01.setPivot(-0.5F, -0.5F, -0.5F);
            B01.setTextureSize(128, 64);
            B01.mirror = true;
            B01.setRotation(0F, 0F, 0F);

            var B02 = new ModelPartLegacy(48, 0);
            B02.addCuboid(0F, 0F, 0F, 8, 5, 3);
            B02.setPivot(-4F, 2F, -5F);
            B02.setTextureSize(128, 64);
            B02.mirror = true;
            B02.setRotation(0F, 0F, 0F);

            var B03 = new ModelPartLegacy(71, 0);
            B03.addCuboid(0F, 0F, 0F, 7, 5, 2);
            B03.setPivot(-3.5F, 7F, -4.5F);
            B03.setTextureSize(128, 64);
            B03.mirror = true;
            B03.setRotation(0.3665191F, 0F, 0F);

            var B04 = new ModelPartLegacy(48, 10);
            B04.addCuboid(-4F, 0F, -2F, 3, 5, 2);
            B04.setPivot(4.5F, 5F, 4F);
            B04.setTextureSize(128, 64);
            B04.mirror = true;
            B04.setRotation(0F, 0F, 0F);

            var B05 = new ModelPartLegacy(48, 10);
            B05.addCuboid(-4F, 0F, -2F, 3, 5, 2);
            B05.setPivot(0.5F, 5F, 4F);
            B05.setTextureSize(128, 64);
            B05.mirror = true;
            B05.setRotation(0F, 0F, 0F);

            var B09 = new ModelPartLegacy(64, 10);
            B09.addCuboid(-1F, -1F, 0F, 2, 2, 1);
            B09.setPivot(0F, 2.5F, 3F);
            B09.setTextureSize(128, 64);
            B09.mirror = true;
            B09.setRotation(0F, 0F, 0.7853982F);

            var B07 = new ModelPartLegacy(59, 10);
            B07.addCuboid(0F, 0F, 0F, 1, 2, 1);
            B07.setPivot(1.5F, 3F, 2.5F);
            B07.setTextureSize(128, 64);
            B07.mirror = true;
            B07.setRotation(0F, 0F, 0F);

            var B06 = new ModelPartLegacy(59, 10);
            B06.addCuboid(0F, 0F, 0F, 1, 2, 1);
            B06.setPivot(-2.5F, 3F, 2.5F);
            B06.setTextureSize(128, 64);
            B06.mirror = true;
            B06.setRotation(0F, 0F, 0F);

            var B08 = new ModelPartLegacy(59, 15);
            B08.addCuboid(0F, 0F, 0F, 5, 1, 1);
            B08.setPivot(-2.5F, 2F, 2.5F);
            B08.setTextureSize(128, 64);
            B08.mirror = true;
            B08.setRotation(0F, 0F, 0F);

            ModelPartData chest = B01.addTo(modelPartData, EntityModelPartNames.BODY);
            B02.addTo(chest, EntityModelPartNames.BODY + "_B02");
            B03.addTo(chest, EntityModelPartNames.BODY + "_B03");
            B04.addTo(chest, EntityModelPartNames.BODY + "_B04");
            B05.addTo(chest, EntityModelPartNames.BODY + "_B05");
            B06.addTo(chest, EntityModelPartNames.BODY + "_B06");
            B07.addTo(chest, EntityModelPartNames.BODY + "_B07");
            B08.addTo(chest, EntityModelPartNames.BODY + "_B08");
            B09.addTo(chest, EntityModelPartNames.BODY + "_B09");

            var RA02 = new ModelPartLegacy(72, 8);
            RA02.addCuboid(-8F, 0F, 0F, 7, 2, 8);
            RA02.setPivot(-5F+ArmOffsetX, -2F+ArmOffsetY, -4F);
            RA02.setTextureSize(128, 64);
            RA02.mirror = true;
            RA02.setRotation(0F, 0F, -0.5235988F);

            var RA03 = new ModelPartLegacy(70, 27);
            RA03.addCuboid(0F, 0F, 0F, 3, 3, 6);
            RA03.setPivot(-8F+ArmOffsetX, 0F+ArmOffsetY, -3F);
            RA03.setTextureSize(128, 64);
            RA03.mirror = true;
            RA03.setRotation(0F, 0F, 0F);

            var RA01 = new ModelPartLegacy(69, 38);
            RA01.addCuboid(-3.5F, -2F, -2.5F, 5, 13, 5);
            RA01.setPivot(-8.5F+offsetX, -0.5F+offsetY, -2.5F+offsetZ);
            RA01.setTextureSize(128, 64);
            RA01.mirror = false;
            RA01.setRotation(0F, 0F, 0F);

            var RA04 = new ModelPartLegacy(73, 19);
            RA04.addCuboid(-8.5F, 0F, 0F, 7, 1, 6);
            RA04.setPivot(-5F+ArmOffsetX, -3F+ArmOffsetY, -3F);
            RA04.setTextureSize(128, 64);
            RA04.mirror = true;
            RA04.setRotation(0F, 0F, -0.5235988F);

            var RA05 = new ModelPartLegacy(56, 38);
            RA05.addCuboid(0F, 0F, 0F, 1, 5, 4);
            RA05.setPivot(-9.5F+ArmOffsetX, 6.5F+ArmOffsetY, -2F);
            RA05.setTextureSize(128, 64);
            RA05.mirror = true;
            RA05.setRotation(0F, 0F, 0F);

            var RA06 = new ModelPartLegacy(46, 38);
            RA06.addCuboid(0F, 0F, 0F, 3, 4, 1);
            RA06.setPivot(-8F+ArmOffsetX, 4F+ArmOffsetY, 2F);
            RA06.setTextureSize(128, 64);
            RA06.mirror = true;
            RA06.setRotation(0F, 0F, 0F);

            var LA01 = new ModelPartLegacy(90, 38);
            LA01.addCuboid(-1.5F, -2F, -2.5F, 5, 13, 5);
            LA01.setPivot(3.5F+offsetX, -0.5F+offsetY, -2.5F+offsetZ);
            LA01.setTextureSize(128, 64);
            LA01.mirror = false;
            LA01.setRotation(0F, 0F, 0F);


            var LA02 = new ModelPartLegacy(72, 8);
            LA02.addCuboid(1F, 0F, 0F, 7, 2, 8);
            LA02.setPivot(5F-ArmOffsetX, -2F+ArmOffsetY, -4F);
            LA02.setTextureSize(128, 64);
            LA02.mirror = true;
            LA02.setRotation(0F, 0F, 0.5235988F);

            var LA03 = new ModelPartLegacy(70, 27);
            LA03.addCuboid(0F, 0F, 0F, 3, 3, 6);
            LA03.setPivot(5F-ArmOffsetX, 0F+ArmOffsetY, -3F);
            LA03.setTextureSize(128, 64);
            LA03.mirror = true;
            LA03.setRotation(0F, 0F, 0F);

            var LA04 = new ModelPartLegacy(100, 19);
            LA04.addCuboid(1.5F, 0F, 0F, 7, 1, 6);
            LA04.setPivot(5F-ArmOffsetX, -3F+ArmOffsetY, -3F);
            LA04.setTextureSize(128, 64);
            LA04.mirror = true;
            LA04.setRotation(0F, 0F, 0.5235988F);

            var LA05 = new ModelPartLegacy(56, 38);
            LA05.addCuboid(-1.5F, -2.5F, -2F, 1, 5, 4);
            LA05.setPivot(10F-ArmOffsetX, 9F+ArmOffsetY, 0F);
            LA05.setTextureSize(128, 64);
            LA05.mirror = true;
            LA05.setRotation(0F, 0F, 0F);

            var LA06 = new ModelPartLegacy(46, 38);
            LA06.addCuboid(-1F, -1F, 0F, 3, 4, 1);
            LA06.setPivot(6F-ArmOffsetX, 5F+ArmOffsetY, 2F);
            LA06.setTextureSize(128, 64);
            LA06.mirror = true;
            LA06.setRotation(0F, 0F, 0F);

            ModelPartData left_arm = LA01.addTo(modelPartData, EntityModelPartNames.LEFT_ARM);
            LA02.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA02");
            LA03.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA03");
            LA04.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA04");
            LA05.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA05");
            LA06.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA06");

            ModelPartData right_arm = RA01.addTo(modelPartData, EntityModelPartNames.RIGHT_ARM);
            RA02.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA02");
            RA03.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA03");
            RA04.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA04");
            RA05.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA05");
            RA06.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA06");
        } else {
            modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.NONE);
            modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.NONE);
            modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.NONE);
        }

        if (slot == EquipmentSlot.LEGS) {
            var P03 = new ModelPartLegacy(21, 47);
            P03.addCuboid(0F, 0F, 0F, 3, 2, 6);
            P03.setPivot(-1.5F, 13F, -3F);
            P03.setTextureSize(128, 64);
            P03.mirror = true;
            P03.setRotation(0F, 0F, 0F);

            var P01 = new ModelPartLegacy(40, 48);
            P01.addCuboid(0F, 0F, 0F, 9, 3, 5);
            P01.setPivot(-4.5F, 11.5F, -2.5F);
            P01.setTextureSize(128, 64);
            P01.mirror = true;
            P01.setRotation(0F, 0F, 0F);

            var P02 = new ModelPartLegacy(0, 38);
            P02.addCuboid(0F, 0F, 0F, 10, 2, 6);
            P02.setPivot(-5F, 11F, -3F);
            P02.setTextureSize(128, 64);
            P02.mirror = true;
            P02.setRotation(0F, 0F, 0F);

            var chest = modelPartData.getChild(EntityModelPartNames.BODY);
            P01.addTo(chest, EntityModelPartNames.BODY + "_P01");
            P02.addTo(chest, EntityModelPartNames.BODY + "_P02");
            P03.addTo(chest, EntityModelPartNames.BODY + "_P03");


            var RL01 = new ModelPartLegacy(0, 47);
            RL01.addCuboid(-2.5F, 0F, -2.5F, 5, 9, 5);
            RL01.setPivot(-2.5F + LegOffsetX - offsetX + offsetZ, 11.5F + LegOffsetY + offsetY, -0.5F);
            RL01.setTextureSize(128, 64);
            RL01.mirror = false;
            RL01.setRotation(0F, 0F, 0F);

            var RL02 = new ModelPartLegacy(21, 56);
            RL02.addCuboid(0F, 0F, 0F, 3, 3, 1);
            RL02.setPivot(-2.3F + LegOffsetX, 14.5F + LegOffsetY, -3F);
            RL02.setTextureSize(128, 64);
            RL02.mirror = true;
            RL02.setRotation(0F, 0F, 0.7853982F);

            var RL03 = new ModelPartLegacy(30, 56);
            RL03.addCuboid(0F, -1F, -1F, 1, 2, 2);
            RL03.setPivot(-5F + LegOffsetX, 16.5F + LegOffsetY, 0F);
            RL03.setTextureSize(128, 64);
            RL03.mirror = true;
            RL03.setRotation(0.7853982F, 0F, 0F);

            var RL04 = new ModelPartLegacy(33, 38);
            RL04.addCuboid(0F, -3F, -1F, 1, 3, 2);
            RL04.setPivot(-5F + LegOffsetX, 15.5F + LegOffsetY, 0F);
            RL04.setTextureSize(128, 64);
            RL04.mirror = true;
            RL04.setRotation(0F, 0F, 0.148353F);

            var RL05 = new ModelPartLegacy(33, 38);
            RL05.addCuboid(0F, 0F, -1F, 1, 3, 2);
            RL05.setPivot(-5F + LegOffsetX, 17.5F + LegOffsetY, 0F);
            RL05.setTextureSize(128, 64);
            RL05.mirror = true;
            RL05.setRotation(0F, 0F, -0.148353F);

            var LL01 = new ModelPartLegacy(0, 47);
            LL01.addCuboid(-2.5F, 0F, -2.5F, 5, 9, 5);
            LL01.setPivot(1.5F - LegOffsetX + offsetX, 11.5F + LegOffsetY + offsetY, -0.5F);
            LL01.setTextureSize(128, 64);
            LL01.mirror = true;
            LL01.setRotation(0F, 0F, 0F);

            var LL02 = new ModelPartLegacy(21, 56);
            LL02.addCuboid(0F, 0F, 0F, 3, 3, 1);
            LL02.setPivot(2.3F - LegOffsetX, 14.5F + LegOffsetY, -3F);
            LL02.setTextureSize(128, 64);
            LL02.mirror = true;
            LL02.setRotation(0F, 0F, 0.7853982F);

            var LL03 = new ModelPartLegacy(30, 56);
            LL03.addCuboid(0F, -1F, -1F, 1, 2, 2);
            LL03.setPivot(4F - LegOffsetX, 16.5F + LegOffsetY, 0F);
            LL03.setTextureSize(128, 64);
            LL03.mirror = true;
            LL03.setRotation(0.7853982F, 0F, 0F);

            var LL04 = new ModelPartLegacy(33, 38);
            LL04.addCuboid(0F, -3F, -1F, 1, 3, 2);
            LL04.setPivot(4F - LegOffsetX, 15.5F + LegOffsetY, 0F);
            LL04.setTextureSize(128, 64);
            LL04.mirror = true;
            LL04.setRotation(0F, 0F, -0.148353F);

            var LL05 = new ModelPartLegacy(33, 38);
            LL05.addCuboid(0F, 0F, -1F, 1, 3, 2);
            LL05.setPivot(4F - LegOffsetX, 17.5F + LegOffsetY, 0F);
            LL05.setTextureSize(128, 64);
            LL05.mirror = true;
            LL05.setRotation(0F, 0F, 0.148353F);

            ModelPartData left_leg = LL01.addTo(modelPartData, EntityModelPartNames.LEFT_LEG);
            LL02.addTo(left_leg, EntityModelPartNames.LEFT_LEG+"_LL02");
            LL03.addTo(left_leg, EntityModelPartNames.LEFT_LEG+"_LL03");
            LL04.addTo(left_leg, EntityModelPartNames.LEFT_LEG+"_LL04");
            LL05.addTo(left_leg, EntityModelPartNames.LEFT_LEG+"_LL05");

            ModelPartData right_leg = RL01.addTo(modelPartData, EntityModelPartNames.RIGHT_LEG);
            RL02.addTo(right_leg, EntityModelPartNames.RIGHT_LEG+"_RL02");
            RL03.addTo(right_leg, EntityModelPartNames.RIGHT_LEG+"_RL03");
            RL04.addTo(right_leg, EntityModelPartNames.RIGHT_LEG+"_RL04");
            RL05.addTo(right_leg, EntityModelPartNames.RIGHT_LEG+"_RL05");
        } else {
            modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.NONE);
            modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.NONE);
        }

        if (slot == EquipmentSlot.FEET) {
            var RB01 = new ModelPartLegacy(103, 0);
            RB01.addCuboid(-2F, 0F, -2F, 5, 4, 5);
            RB01.setPivot(-2.5F + LegOffsetX +offsetX, 20.5F + LegOffsetY +offsetY, -0.5F);
            RB01.setTextureSize(128, 64);
            RB01.mirror = true;
            RB01.setRotation(0F, 0F, 0F);

            var RB02 = new ModelPartLegacy(103, 10);
            RB02.addCuboid(-2F, 0F, -2F, 4, 2, 2);
            RB02.setPivot(-2F + LegOffsetX +offsetX, 22F + LegOffsetY +offsetY, -2F);
            RB02.setTextureSize(128, 64);
            RB02.mirror = true;
            RB02.setRotation(0F, 0F, 0F);

            var LB01 = new ModelPartLegacy(103, 0);
            LB01.addCuboid(2F, 0F, -2F, 5, 4, 5);
            LB01.setPivot(-2.5F - LegOffsetX -offsetX, 20.5F + LegOffsetY +offsetY, -0.5F);
            LB01.setTextureSize(128, 64);
            LB01.mirror = true;
            LB01.setRotation(0F, 0F, 0F);

            var LB02 = new ModelPartLegacy(103, 10);
            LB02.addCuboid(-2F, 0F, -2F, 4, 2, 2);
            LB02.setPivot(2F - LegOffsetX -offsetX, 22F + LegOffsetY +offsetY, -2F);
            LB02.setTextureSize(128, 64);
            LB02.mirror = true;
            LB02.setRotation(0F, 0F, 0F);

            var left_leg = modelPartData.getChild(EntityModelPartNames.LEFT_LEG);
            LB01.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LB01");
            LB02.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LB02");

            var right_leg = modelPartData.getChild(EntityModelPartNames.RIGHT_LEG);
            RB01.addTo(right_leg, EntityModelPartNames.LEFT_LEG + "_RB01");
            RB02.addTo(right_leg, EntityModelPartNames.LEFT_LEG + "_RB02");
        }

        return modelData;
    }

}
