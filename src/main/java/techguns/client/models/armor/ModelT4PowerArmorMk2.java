package techguns.client.models.armor;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.EquipmentSlot;
import techguns.client.TGClientEntityModels;

import java.util.function.Function;

public class ModelT4PowerArmorMk2 extends BipedEntityModel {
    public ModelT4PowerArmorMk2(ModelPart root) {
        super(root);
    }

    public ModelT4PowerArmorMk2(ModelPart root, Function renderLayerFactory) {
        super(root, renderLayerFactory);
    }

    public static ModelData getModelData(Dilation dilation, EquipmentSlot slot) {
        return getModelData(dilation, 0F, 0F, 0F, slot);
    }

    public static ModelData getModelData(Dilation dilation, float offsetX, float offsetY, EquipmentSlot slot) {
        return getModelData(dilation, offsetX, offsetY, 0F, slot);
    }
    public static ModelData getModelData(Dilation dilation, float offsetX, float offsetY, float offsetZ, EquipmentSlot slot) {
        return getModelData(dilation, offsetX, offsetX, offsetX, offsetY, offsetY, offsetZ, slot);
    }

    public static ModelData getModelData(Dilation dilation, float offsetX, float offsetX_left, float offsetX_right, float offsetY, float offsetY2, float offsetZ, EquipmentSlot slot) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        Dilation scale = Dilation.NONE;

        float ArmOffsetX = 5.0F;
        float ArmOffsetY = -2.0F;
        float LegOffsetX = 1.9F;
        float LegOffsetY = -12.0F;

        ModelPartLegacy H01 = new ModelPartLegacy(0, 0);
        H01.setPivot(-4.5F+offsetX, -8.5F+offsetY, -4.5F);
        H01.addCuboid(-4.5F,-8.5F,-4.5F, 9, 9, 9, scale);
        ModelPartLegacy H02 = new ModelPartLegacy(0, 19);
        H02.setPivot(-2.0F, -2.2F, -6.0F);
        H02.addCuboid(0.0F, 0.0F, 0.0F, 4, 3, 3, scale);
        ModelPartLegacy H03 = new ModelPartLegacy(0, 28);
        H03.setPivot(2.0F, -2.0F, -5.5F);
        H03.addCuboid(0.0F, 0.0F, 0.0F, 3, 2, 2, scale);
        ModelPartLegacy H04 = new ModelPartLegacy(0, 28);
        H04.setPivot(-2.0F, -2.0F, -5.5F);
        H04.addCuboid(-3.0F, 0.0F, 0.0F, 3, 2, 2, new Dilation(0.0F));
        ModelPartLegacy H05 = new ModelPartLegacy(13, 27);
        H05.setPivot(4.0F, -2.0F, -3.5F);
        H05.addCuboid(0.0F, 0.0F, 0.0F, 1, 2, 8, scale);
        ModelPartLegacy H06 = new ModelPartLegacy(13, 27);
        H06.setPivot(-5.0F, -2.0F, -3.5F);
        H06.addCuboid(0.0F, 0.0F, 0.0F, 1, 2, 8, scale);
        ModelPartLegacy H08 = new ModelPartLegacy(19, 19);
        H08.setPivot(-4.5F, -2.0F, 4.0F);
        H08.addCuboid(0.0F, 0.0F, 0.0F, 9, 2, 1, scale);
        ModelPartLegacy H07 = new ModelPartLegacy(0, 33);
        H07.setPivot(-1.5F, -4.2F, -6.0F);
        H07.addCuboid(0.0F, 0.0F, 0.0F, 3, 2, 2, scale);
        ModelPartLegacy H10 = new ModelPartLegacy(38, 0);
        H10.setPivot(4.5F, -5.6F, -1.5F);
        H10.addCuboid(0.0F, 0.0F, 0.0F, 1, 6, 3, scale);
        ModelPartLegacy H11 = new ModelPartLegacy(38, 0);
        H11.mirror = true;
        H11.setPivot(-5.5F, -5.6F, -1.5F);
        H11.addCuboid(0.0F, 0.0F, 0.0F, 1, 6, 3, scale);
        ModelPartLegacy H12 = new ModelPartLegacy(97, 15);
        H12.setPivot(-2.0F, -9.5F, -2.0F);
        H12.addCuboid(0.0F, 0.0F, 0.0F, 4, 1, 7, scale);
        ModelPartLegacy H09 = new ModelPartLegacy(24, 23);
        H09.setPivot(-2.0F, -8.5F, 4.2F);
        H09.addCuboid(0.0F, 0.0F, 0.0F, 4, 9, 1, scale);


        ModelPartLegacy RA01 = new ModelPartLegacy(69, 38);
        RA01.setPivot(-8.5F + ArmOffsetX+offsetX_right, -0.5F + ArmOffsetY+offsetY, -2.5F+offsetZ);
        RA01.addCuboid(-3.5F, -2F, -2.5F, 5, 13, 5, scale);
        ModelPartLegacy RA03 = new ModelPartLegacy(68, 27);
        RA03.mirror = true;
        RA03.setPivot(-9.0F + ArmOffsetX, -1.0F + ArmOffsetY, -3.0F);
        RA03.addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 6, scale);
        ModelPartLegacy RA05 = new ModelPartLegacy(56, 38);
        RA05.setPivot(-9.2F + ArmOffsetX, 6.5F + ArmOffsetY, -2.0F);
        RA05.addCuboid(0.0F, 0.0F, 0.0F, 1, 5, 4, scale);
        ModelPartLegacy RA06 = new ModelPartLegacy(46, 38);
        RA06.setPivot(-8.0F + ArmOffsetX, 4.0F + ArmOffsetY, 2.0F);
        RA06.addCuboid(0.0F, 0.0F, 0.0F, 3, 4, 1, scale);
        ModelPartLegacy RA04 = new ModelPartLegacy(75, 12);
        RA04.setPivot(-9.2F + ArmOffsetX, -3.0F + ArmOffsetY, -2.0F);
        RA04.addCuboid(0.0F, 0.0F, 0.0F, 1, 7, 4, scale);

        ModelPartLegacy LA03 = new ModelPartLegacy(68, 27);
        LA03.setPivot(5.0F - ArmOffsetX, -1.0F + ArmOffsetY, -3.0F);
        LA03.addCuboid(0.0F, 0.0F, 0.0F, 4, 4, 6, scale);
        ModelPartLegacy LA01 = new ModelPartLegacy(69, 38);
        LA01.mirror = true;
        LA01.setPivot(3.5F - ArmOffsetX-offsetX_left, -0.5F + ArmOffsetY+offsetY, -2.5F + offsetZ);
        LA01.addCuboid(-1.5F, -2F, -2.5F, 5, 13, 5, scale);
        ModelPartLegacy LA06 = new ModelPartLegacy(46, 38);
        LA06.setPivot(6.0F - ArmOffsetX, 5.0F + ArmOffsetY, 2.0F);
        LA06.addCuboid(-1.0F, -1.0F, 0.0F, 3, 4, 1, scale);
        ModelPartLegacy LA05 = new ModelPartLegacy(56, 38);
        LA05.mirror = true;
        LA05.setPivot(9.7F - ArmOffsetX, 9.0F + ArmOffsetY, 0.0F);
        LA05.addCuboid(-1.5F, -2.5F, -2.0F, 1, 5, 4, scale);
        ModelPartLegacy LA04 = new ModelPartLegacy(75, 12);
        LA04.setPivot(8.2F - ArmOffsetX, -3.0F + ArmOffsetY, -2.0F);
        LA04.addCuboid(0.0F, 0.0F, 0.0F, 1, 7, 4, scale);

        ModelPartLegacy B01 = new ModelPartLegacy(41, 19);
        B01.setPivot(-0.5F, -0.5F, -0.5F);
        B01.addCuboid(-4.0F+offsetX, 0.0F, -2.0F, 9, 6, 5, scale);
        ModelPartLegacy B02 = new ModelPartLegacy(48, 0);
        B02.setPivot(-4.5F, 1.5F, -4.5F);
        B02.addCuboid(0.0F, 0.0F, 0.0F, 9, 4, 2, scale);
        ModelPartLegacy B03 = new ModelPartLegacy(71, 0);
        B03.setPivot(-3.0F, 5.2F, -4.5F);
        B03.addCuboid(0.0F, 0.0F, 0.0F, 6, 7, 2, scale);
        B03.setRotation(0.2792526803190927F, -0.0F, 0.0F);
        ModelPartLegacy B04 = new ModelPartLegacy(48, 10);
        B04.setPivot(4.5F, 1.5F, 4.0F);
        B04.addCuboid(-4.0F, 0.0F, -2.0F, 3, 5, 2, scale);
        ModelPartLegacy B05 = new ModelPartLegacy(48, 10);
        B05.setPivot(0.5F, 1.5F, 4.0F);
        B05.addCuboid(-4.0F, 0.0F, -2.0F, 3, 5, 2, scale);
        ModelPartLegacy B06 = new ModelPartLegacy(60, 10);
        B06.setPivot(-2.5F, 5.0F, 2.5F);
        B06.addCuboid(0.0F, 0.0F, 0.0F, 5, 5, 2, scale);
        ModelPartLegacy B07 = new ModelPartLegacy(50, 32);
        B07.setPivot(-3.0F, 11.0F, 3.0F);
        B07.addCuboid(0.0F, -1.0F, -1.0F, 6, 2, 2, scale);
        B07.setRotation(0.7853981633974483F, 0.0F, 0.0F);
        ModelPartLegacy B10 = new ModelPartLegacy(89, 27);
        B10.setPivot(-1.0F, 0.8F, -4.8F);
        B10.addCuboid(0.0F, 0.0F, 0.0F, 2, 5, 3, scale);
        ModelPartLegacy B13 = new ModelPartLegacy(100, 24);
        B13.setPivot(2.2F, 5.9F, -3.0F);
        B13.addCuboid(0.0F, 0.0F, 0.0F, 2, 3, 6, scale);
        ModelPartLegacy B14 = new ModelPartLegacy(100, 24);
        B14.mirror = true;
        B14.setPivot(-4.2F, 5.9F, -3.0F);
        B14.addCuboid(0.0F, 0.0F, 0.0F, 2, 3, 6, scale);
        ModelPartLegacy B12 = new ModelPartLegacy(90, 36);
        B12.setPivot(0.0F, 5.5F, -0.6F);
        B12.addCuboid(-4.0F, 0.0F, -2.0F, 8, 6, 5, new Dilation(0.1F));

        ModelPartLegacy RL01 = new ModelPartLegacy(0, 47);
        RL01.setPivot(-2.5F + LegOffsetX+offsetX_right, 11.5F + LegOffsetY +offsetY2, -0.5F+offsetZ);
        RL01.addCuboid(-2.5F, -1.0F, -2.5F, 5, 9, 5, scale);
        ModelPartLegacy RL02 = new ModelPartLegacy(21, 56);
        RL02.setPivot(-2.3F + LegOffsetX, 15.5F + LegOffsetY, -3.0F);
        RL02.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 1, scale);
        RL02.setRotation(0.0F, -0.0F, 0.7853981633974483F);
        ModelPartLegacy RL03 = new ModelPartLegacy(30, 56);
        RL03.setPivot(-4.8F + LegOffsetX, 17.5F + LegOffsetY, 0.0F);
        RL03.addCuboid(0.0F, -1.0F, -1.0F, 1, 2, 2, scale);
        RL03.setRotation(0.7853981633974483F, -0.0F, 0.0F);

        ModelPartLegacy LL02 = new ModelPartLegacy(21, 56);
        LL02.setPivot(2.3F - LegOffsetX, 15.5F + LegOffsetY, -3.0F);
        LL02.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 1, scale);
        LL02.setRotation(0.0F, -0.0F, 0.7853981633974483F);
        ModelPartLegacy LL03 = new ModelPartLegacy(30, 56);
        LL03.mirror = true;
        LL03.setPivot(3.8F - LegOffsetX, 17.5F + LegOffsetY, 0.0F);
        LL03.addCuboid(0.0F, -1.0F, -1.0F, 1, 2, 2, scale);
        LL03.setRotation(0.7853981633974483F, -0.0F, 0.0F);
        ModelPartLegacy LL01 = new ModelPartLegacy(0, 47);
        LL01.mirror = true;
        LL01.setPivot(1.5F - LegOffsetX+offsetX_left, 11.5F + LegOffsetY+offsetY2, -0.5F+offsetZ);
        LL01.addCuboid(-2.5F, -1.0F, -2.5F, 5, 9, 5, scale);

        ModelPartLegacy LB01 = new ModelPartLegacy(103, 0);
        LB01.setPivot(-2.5F - LegOffsetX-offsetX_left, 20.5F + LegOffsetY+offsetY, -0.5F);
        LB01.addCuboid(-2.5F, 8F, -2.5F, 5, 4, 5, scale);
        ModelPartLegacy LB02 = new ModelPartLegacy(103, 10);
        LB02.mirror = true;
        LB02.setPivot(2.0F - LegOffsetX, 22.0F + LegOffsetY, -2.0F);
        LB02.addCuboid(-2.0F, 0.0F, -2.0F, 4, 2, 2, scale);

        ModelPartLegacy RB01 = new ModelPartLegacy(103, 0);
        RB01.setPivot(-2.5F + LegOffsetX+offsetX_right, 20.5F + LegOffsetY+offsetY, -0.5F);
        RB01.addCuboid(-2.5F, 8F, -2.5F, 5, 4, 5, scale);
        ModelPartLegacy RB02 = new ModelPartLegacy(103, 10);
        RB02.setPivot(-2.0F + LegOffsetX, 22.0F + LegOffsetY, -2.0F);
        RB02.addCuboid(-2.0F, 0.0F, -2.0F, 4, 2, 2, scale);



       /* ModelPartLegacy P01 = new ModelPartLegacy(40, 48);
        P01.setPivot(-4.5F, 12.6F, -2.5F);
        P01.addCuboid(0.0F, 0.0F, 0.0F, 9, 2, 5, -0.3F);*/

        ModelPartLegacy P03 = new ModelPartLegacy(21, 47);
        P03.setPivot(-1.5F, 12.9F, -3.0F);
        P03.addCuboid(0.0F, 0.0F, 0.0F, 3, 2, 6, scale);

        ModelPartLegacy P02 = new ModelPartLegacy(0, 38);
        P02.setPivot(-5.0F+offsetX, 9.9F+offsetY, -3.0F);
        P02.addCuboid(-5F, 9.9F, -3.0F, 10, 3, 6, scale);

        if (slot == EquipmentSlot.HEAD){
            var head = H01.addTo(modelPartData, EntityModelPartNames.HEAD);
            H02.addTo(head, EntityModelPartNames.HEAD + "_H02");
            H03.addTo(head, EntityModelPartNames.HEAD + "_H03");
            H04.addTo(head, EntityModelPartNames.HEAD + "_H04");
            H05.addTo(head, EntityModelPartNames.HEAD + "_H05");
            H06.addTo(head, EntityModelPartNames.HEAD + "_H06");
            H07.addTo(head, EntityModelPartNames.HEAD + "_H07");
            H08.addTo(head, EntityModelPartNames.HEAD + "_H08");
            H09.addTo(head, EntityModelPartNames.HEAD + "_H09");
            H10.addTo(head, EntityModelPartNames.HEAD + "_H10");
            H11.addTo(head, EntityModelPartNames.HEAD + "_H11");
            H12.addTo(head, EntityModelPartNames.HEAD + "_H12");

        } else if (slot == EquipmentSlot.CHEST){
            var body = B01.addTo(modelPartData, EntityModelPartNames.BODY);
            B02.addTo(body, EntityModelPartNames.BODY + "_B02");
            B03.addTo(body, EntityModelPartNames.BODY + "_B03");
            B04.addTo(body, EntityModelPartNames.BODY + "_B04");
            B05.addTo(body, EntityModelPartNames.BODY + "_B05");
            B06.addTo(body, EntityModelPartNames.BODY + "_B06");
            B07.addTo(body, EntityModelPartNames.BODY + "_B07");
            //B08.addTo(body, EntityModelPartNames.BODY + "_B08");
            //B09.addTo(body, EntityModelPartNames.BODY + "_B09");
            B10.addTo(body, EntityModelPartNames.BODY + "_B10");
            // B11.addTo(body, EntityModelPartNames.BODY + "_B11");
            B12.addTo(body, EntityModelPartNames.BODY + "_B12");
            B13.addTo(body, EntityModelPartNames.BODY + "_B13");
            B14.addTo(body, EntityModelPartNames.BODY + "_B14");

            var left_arm = LA01.addTo(modelPartData, EntityModelPartNames.LEFT_ARM);
            //LA02.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA02");
            LA03.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA03");
            LA04.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA04");
            LA05.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA05");
            LA06.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA06");

            var right_arm = RA01.addTo(modelPartData, EntityModelPartNames.RIGHT_ARM);
            //RA02.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA02");
            RA03.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA03");
            RA04.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA04");
            RA05.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA05");
            RA06.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA06");

        } else if (slot == EquipmentSlot.LEGS) {
            var body = P02.addTo(modelPartData, EntityModelPartNames.BODY);
            //P01.addTo(body, EntityModelPartNames.BODY + "_P01");
            P03.addTo(body, EntityModelPartNames.BODY + "_P03");

            var right_leg = RL01.addTo(modelPartData, EntityModelPartNames.RIGHT_LEG);
            RL02.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RL02");
            RL03.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RL03");
            //RL04.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RL04");
            //RL05.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RL05");

            var left_leg = LL01.addTo(modelPartData, EntityModelPartNames.LEFT_LEG);
            LL02.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LL02");
            LL03.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LL03");
            //LL04.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LL04");
            //LL05.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LL05");

        } else if (slot == EquipmentSlot.FEET) {

            var right_leg = RB01.addTo(modelPartData, EntityModelPartNames.RIGHT_LEG);
            RB02.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RB02");

            var left_leg = LB01.addTo(modelPartData, EntityModelPartNames.LEFT_LEG);
            LB02.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LB02");
        }

        TGClientEntityModels.addMissingParts(modelPartData);
        return modelData;
    }
}