package techguns.client.models.armor;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.EquipmentSlot;

import java.util.function.Function;

public class ModelSteamArmor extends BipedEntityModel {

    public ModelSteamArmor(ModelPart root) {
        super(root);
    }

    public ModelSteamArmor(ModelPart root, Function renderLayerFactory) {
        super(root, renderLayerFactory);
    }

    public static ModelData getModelData(Dilation dilation, EquipmentSlot slot) {
        return ModelSteamArmor.getModelData(dilation,0F, 0F, slot);
    }

    public static ModelData getModelData(Dilation dilation, float offsetX, float offsetY, EquipmentSlot slot) {
        return ModelSteamArmor.getModelData(dilation, offsetX, offsetX, offsetX, offsetY, offsetY, offsetY,0F, 0F, slot);
    }

    public static ModelData getModelData(Dilation dilation, float offsetX_right, float offsetX_left, float offsetY, float offsetZ, EquipmentSlot slot) {
        return ModelSteamArmor.getModelData(dilation, offsetX_right, offsetX_left, offsetX_right, offsetY, offsetY, offsetY,offsetZ, offsetZ, slot);
    }

    public static ModelData getModelData(Dilation dilation, float offsetX, float offsetX_left, float offsetX_right, float offsetY, float offsetY_left,float offsetY_right, float offsetZ, float offsetZ2, EquipmentSlot slot) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        Dilation scale = Dilation.NONE;

        float legOffsetX = -0.9F;
        float legOffsetY = 8.0F;

        ModelPartLegacy RA1 = new ModelPartLegacy(108, 0);
        RA1.addCuboid(-1F, -4F, -1F, 2, 2, 2);
        RA1.setPivot(-2F, -1.5F, 0F);
        RA1.setTextureSize(128, 64);
        RA1.mirror = true;
        RA1.setRotation( 0F, 0F, -0.5235988F);
        ModelPartLegacy RA2 = new ModelPartLegacy(79, 48);
        RA2.addCuboid(0F, 0F, 0F, 2, 2, 6);
        RA2.setPivot(-1.5F, -3.5F, -3F);
        RA2.setTextureSize(128, 64);
        RA2.mirror = true;
        RA2.setRotation( 0F, 0F, 0F);
        ModelPartLegacy RA3 = new ModelPartLegacy(96, 0);
        RA3.addCuboid(-1.5F, -2F, -1.5F, 3, 2, 3);
        RA3.setPivot(-2F, -1.5F, 0F);
        RA3.setTextureSize(128, 64);
        RA3.mirror = true;
        RA3.setRotation( 0F, 0F, -0.5235988F);
        ModelPartLegacy RA4 = new ModelPartLegacy(71, 40);
        RA4.addCuboid(0F, 0F, 0F, 6, 2, 6);
        RA4.setPivot(-4F, 4.5F, -3F);
        RA4.setTextureSize(128, 64);
        RA4.mirror = true;
        RA4.setRotation( 0F, 0F, 0F);
        ModelPartLegacy RA5 = new ModelPartLegacy(48, 18);
        RA5.addCuboid(-3.5F, -2F, -2.5F, 5, 13, 5);
        RA5.setPivot(-3.5F+offsetX_right, -2.5F+offsetY, -2.5F+offsetZ);
        RA5.setTextureSize(128, 64);
        RA5.mirror = true;
        RA5.setRotation( 0F, 0F, 0F);
        ModelPartLegacy RA6 = new ModelPartLegacy(71, 48);
        RA6.addCuboid(0F, 0F, 0F, 2, 9, 2);
        RA6.setPivot(-4.5F, 0.5F, -1F);
        RA6.setTextureSize(128, 64);
        RA6.mirror = true;
        RA6.setRotation( 0F, 0F, 0F);
        ModelPartLegacy RA7 = new ModelPartLegacy(96, 5);
        RA7.addCuboid(0F, 0F, 0F, 2, 3, 3);
        RA7.setPivot(-5F, 4F, -1.5F);
        RA7.setTextureSize(128, 64);
        RA7.mirror = false;
        RA7.setRotation( 0F, 0F, 0F);
        ModelPartLegacy RA8 = new ModelPartLegacy(71, 32);
        RA8.addCuboid(0F, 0F, 0F, 6, 2, 6);
        RA8.setPivot(-4.5F, -1.5F, -3F);
        RA8.setTextureSize(128, 64);
        RA8.mirror = true;
        RA8.setRotation( 0F, 0F, 0F);
        ModelPartLegacy LA1 = new ModelPartLegacy(108, 0);
        LA1.addCuboid(-1F, -4F, -1F, 2, 2, 2);
        LA1.setPivot(2F, -1.5F, 0F);
        LA1.setTextureSize(128, 64);
        LA1.setRotation( 0F, 0F, 0.5235988F);
        ModelPartLegacy LA2 = new ModelPartLegacy(96, 5);
        LA2.addCuboid(18F, 0F, 0F, 2, 3, 3);
        LA2.setPivot(-15F, 4F, -1.5F);
        LA2.setTextureSize(128, 64);
        LA2.setRotation( 0F, 0F, 0F);
        LA2.mirror=true;
        ModelPartLegacy LA3 = new ModelPartLegacy(79, 48);
        LA3.addCuboid(0F, 0F, 0F, 2, 2, 6);
        LA3.setPivot(-0.5F, -3.5F, -3F);
        LA3.setTextureSize(128, 64);
        LA3.setRotation( 0F, 0F, 0F);
        ModelPartLegacy LA4 = new ModelPartLegacy(96, 0);
        LA4.addCuboid(-1.5F, -2F, -1.5F, 3, 2, 3);
        LA4.setPivot(2F, -1.5F, 0F);
        LA4.setTextureSize(128, 64);
        LA4.setRotation( 0F, 0F, 0.5235988F);
        ModelPartLegacy LA5 = new ModelPartLegacy(71, 32);
        LA5.addCuboid(0F, 0F, 0F, 6, 2, 6);
        LA5.setPivot(-1.5F, -1.5F, -3F);
        LA5.setTextureSize(128, 64);
        LA5.setRotation( 0F, 0F, 0F);
        ModelPartLegacy LA6 = new ModelPartLegacy(71, 40);
        LA6.addCuboid(12F, 0F, 0F, 6, 2, 6);
        LA6.setPivot(-14F, 4.5F, -3F);
        LA6.setTextureSize(128, 64);
        LA6.setRotation( 0F, 0F, 0F);
        ModelPartLegacy LA7 = new ModelPartLegacy(71, 48);
        LA7.addCuboid(0F, 0F, 0F, 2, 9, 2);
        LA7.setPivot(2.5F, 0.5F, -1F);
        LA7.setTextureSize(128, 64);
        LA7.setRotation( 0F, 0F, 0F);
        ModelPartLegacy LA8 = new ModelPartLegacy(48, 18);
        LA8.addCuboid(-1.5F, -2F, -2.5F, 5, 13, 5);
        LA8.setPivot(-1.5F-offsetX, -2.5F+offsetY, -2.5F+offsetZ);
        LA8.setTextureSize(128, 64);
        LA8.setRotation( 0F, 0F, 0F);
        ModelPartLegacy H1 = new ModelPartLegacy(95, 32);
        H1.addCuboid(0F, 0F, 0F, 4, 4, 2,scale);
        H1.setPivot(-2F, -3F, -6F);
        H1.setTextureSize(128, 64);
        H1.mirror = false;
        H1.setRotation( 0F, 0F, 0F);
        ModelPartLegacy H2 = new ModelPartLegacy(36, 0);
        H2.addCuboid(0F, 0F, 0F, 1, 6, 6,scale);
        H2.setPivot(-5.5F, -7F, -3F);
        H2.setTextureSize(128, 64);
        H2.mirror = false;
        H2.setRotation( 0F, 0F, 0F);
        ModelPartLegacy H3 = new ModelPartLegacy(0, 0);
        H3.addCuboid(-4.5F, -8.5F, -4.5F, 9, 9, 9,scale);
        H3.setPivot(-4.5F+offsetX, -8.5F+offsetY, -4.5F);
        H3.setTextureSize(128, 64);
        H3.mirror = false;
        H3.setRotation( 0F, 0F, 0F);
        ModelPartLegacy H4 = new ModelPartLegacy(107, 32);
        H4.addCuboid(0F, 0F, 0F, 7, 2, 1,scale);
        H4.setPivot(-3.5F, -1.5F, -5.5F);
        H4.setTextureSize(128, 64);
        H4.mirror = false;
        H4.setRotation( 0F, 0F, 0F);
        ModelPartLegacy H5 = new ModelPartLegacy(36, 0);
        H5.addCuboid(0F, 0F, 0F, 1, 6, 6,scale);
        H5.setPivot(4.5F, -7F, -3F);
        H5.setTextureSize(128, 64);
        H5.mirror = true;
        H5.setRotation( 0F, 0F, 0F);
        ModelPartLegacy H6 = new ModelPartLegacy(124, 0);
        H6.addCuboid(0F, 0F, 0F, 1, 6, 1,scale);
        H6.setPivot(-3F, -6.5F, 5.5F);
        H6.setTextureSize(128, 64);
        H6.mirror = false;
        H6.setRotation( 0F, 0F, 0F);
        ModelPartLegacy H7 = new ModelPartLegacy(107, 35);
        H7.addCuboid(0F, 0F, 0F, 2, 2, 1,scale);
        H7.setPivot(-3.5F, -2F, 4.5F);
        H7.setTextureSize(128, 64);
        H7.mirror = false;
        H7.setRotation( 0F, 0F, 0F);
        ModelPartLegacy H8 = new ModelPartLegacy(107, 35);
        H8.addCuboid(0F, 0F, 0F, 2, 2, 1,scale);
        H8.setPivot(-3.5F, -7F, 4.5F);
        H8.setTextureSize(128, 64);
        H8.mirror = false;
        H8.setRotation( 0F, 0F, 0F);
        ModelPartLegacy H9 = new ModelPartLegacy(107, 35);
        H9.addCuboid(0F, 0F, 0F, 2, 2, 1,scale);
        H9.setPivot(1.5F, -7F, 4.5F);
        H9.setTextureSize(128, 64);
        H9.mirror = false;
        H9.setRotation( 0F, 0F, 0F);
        ModelPartLegacy H10 = new ModelPartLegacy(124, 0);
        H10.addCuboid(0F, 0F, 0F, 1, 6, 1,scale);
        H10.setPivot(2F, -6.5F, 5.5F);
        H10.setTextureSize(128, 64);
        H10.mirror = false;
        H10.setRotation( 0F, 0F, 0F);
        ModelPartLegacy H11 = new ModelPartLegacy(107, 35);
        H11.addCuboid(0F, 0F, 0F, 2, 2, 1,scale);
        H11.setPivot(1.5F, -2F, 4.5F);
        H11.setTextureSize(128, 64);
        H11.mirror = false;
        H11.setRotation( 0F, 0F, 0F);

        ModelPartLegacy B1 = new ModelPartLegacy(20, 18);
        B1.addCuboid(-4.5F, -0.5F, -2F, 9, 12, 5);
        B1.setPivot(-0.5F, -0.5F, -0.5F);
        B1.setTextureSize(128, 64);
        B1.mirror = true;
        B1.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B2 = new ModelPartLegacy(50, 36);
        B2.addCuboid(-4F, 0F, -2F, 8, 10, 2);
        B2.setPivot(0F, 0.5F, 4.5F);
        B2.setTextureSize(128, 64);
        B2.mirror = true;
        B2.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B3 = new ModelPartLegacy(94, 38);
        B3.addCuboid(0F, 0F, 0F, 3, 1, 2);
        B3.setPivot(-3.5F, 7.5F, 4.5F);
        B3.setTextureSize(128, 64);
        B3.mirror = true;
        B3.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B4 = new ModelPartLegacy(96, 18);
        B4.addCuboid(0F, 0F, 0F, 2, 1, 2);
        B4.setPivot(1F, 8.5F, 4F);
        B4.setTextureSize(128, 64);
        B4.mirror = true;
        B4.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B5 = new ModelPartLegacy(96, 11);
        B5.addCuboid(-4F, 0F, 0F, 3, 4, 2);
        B5.setPivot(0.5F, 3.5F, 4.5F);
        B5.setTextureSize(128, 64);
        B5.mirror = true;
        B5.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B6 = new ModelPartLegacy(96, 18);
        B6.addCuboid(0F, 0F, 0F, 2, 1, 2);
        B6.setPivot(-3F, 8.5F, 4F);
        B6.setTextureSize(128, 64);
        B6.mirror = true;
        B6.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B7 = new ModelPartLegacy(96, 11);
        B7.addCuboid(0F, 0F, 0F, 3, 4, 2);
        B7.setPivot(0.5F, 3.5F, 4.5F);
        B7.setTextureSize(128, 64);
        B7.mirror = true;
        B7.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B8 = new ModelPartLegacy(94, 38);
        B8.addCuboid(0F, 0F, 0F, 3, 1, 2);
        B8.setPivot(0.5F, 7.5F, 4.5F);
        B8.setTextureSize(128, 64);
        B8.mirror = true;
        B8.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B9 = new ModelPartLegacy(94, 38);
        B9.addCuboid(0F, 0F, 0F, 3, 1, 2);
        B9.setPivot(-3.5F, 2.5F, 4.5F);
        B9.setTextureSize(128, 64);
        B9.mirror = true;
        B9.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B10 = new ModelPartLegacy(94, 38);
        B10.addCuboid(0F, 0F, 0F, 3, 1, 2);
        B10.setPivot(0.5F, 2.5F, 4.5F);
        B10.setTextureSize(128, 64);
        B10.mirror = true;
        B10.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B11 = new ModelPartLegacy(113, 18);
        B11.addCuboid(0F, 0F, 0F, 2, 3, 2);
        B11.setPivot(-3F, 4.5F, 4F);
        B11.setTextureSize(128, 64);
        B11.mirror = true;
        B11.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B12 = new ModelPartLegacy(96, 18);
        B12.addCuboid(0F, 0F, 0F, 2, 1, 2);
        B12.setPivot(-3F, 1.5F, 4F);
        B12.setTextureSize(128, 64);
        B12.mirror = true;
        B12.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B13 = new ModelPartLegacy(50, 5);
        B13.addCuboid(0F, 0F, 0F, 5, 4, 1);
        B13.setPivot(-2.5F, 6.5F, -3.5F);
        B13.setTextureSize(128, 64);
        B13.mirror = true;
        B13.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B14 = new ModelPartLegacy(50, 0);
        B14.addCuboid(0F, 0F, 0F, 7, 4, 1);
        B14.setPivot(-3.5F, 1.5F, -3.5F);
        B14.setTextureSize(128, 64);
        B14.mirror = true;
        B14.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B15 = new ModelPartLegacy(96, 18);
        B15.addCuboid(0F, 0F, 0F, 2, 1, 2);
        B15.setPivot(1F, 1.5F, 4F);
        B15.setTextureSize(128, 64);
        B15.mirror = true;
        B15.setRotation( 0F, 0F, 0F);
        ModelPartLegacy B16 = new ModelPartLegacy(120, 18);
        B16.addCuboid(0F, 0F, 0F, 2, 3, 2);
        B16.setPivot(1F, 4.5F, 4F);
        B16.setTextureSize(128, 64);
        B16.mirror = true;
        B16.setRotation( 0F, 0F, 0F);
        ModelPartLegacy P1 = new ModelPartLegacy(95, 41);
        P1.addCuboid(-5F, 11F, -3F, 10, 2, 6);
        P1.setPivot(-5F+offsetX, 11F+offsetY, -3F+offsetZ);
        P1.setTextureSize(128, 64);
        P1.mirror = true;
        P1.setRotation( 0F, 0F, 0F);
        ModelPartLegacy P2 = new ModelPartLegacy(20, 47);
        P2.addCuboid(0F, 0F, 0F, 9, 3, 5);
        P2.setPivot(-4.5F, 11.5F, -2.5F);
        P2.setTextureSize(128, 64);
        P2.mirror = true;
        P2.setRotation( 0F, 0F, 0F);
        ModelPartLegacy P3 = new ModelPartLegacy(84, 56);
        P3.addCuboid(0F, 0F, 0F, 2, 2, 6);
        P3.setPivot(-1F, 13F, -3F);
        P3.setTextureSize(128, 64);
        P3.mirror = true;
        P3.setRotation( 0F, 0F, 0F);
        ModelPartLegacy RL1 = new ModelPartLegacy(0, 47);
        RL1.addCuboid(-2.5F, -0.5F, -2.5F, 5, 9, 5);
        RL1.setPivot(-0.6F+offsetX_right, 0.5F+offsetY_right, -0.5F+offsetZ2);
        RL1.setTextureSize(128, 64);
        RL1.mirror = true;
        RL1.setRotation( 0F, 0F, 0F);
        ModelPartLegacy RL2 = new ModelPartLegacy(96, 5);
        RL2.addCuboid(0F, 0F, 0F, 2, 3, 3);
        RL2.setPivot(-4.1F, 4.5F, -1.5F);
        RL2.setTextureSize(128, 64);
        RL2.mirror = false;
        RL2.setRotation( 0F, 0F, 0F);
        ModelPartLegacy RL3 = new ModelPartLegacy(71, 32);
        RL3.addCuboid(0F, 0F, 0F, 5, 2, 6);
        RL3.setPivot(-3.1F, 5F, -3F);
        RL3.setTextureSize(128, 64);
        RL3.mirror = true;
        RL3.setRotation( 0F, 0F, 0F);
        ModelPartLegacy RL4 = new ModelPartLegacy(71, 48);
        RL4.addCuboid(0F, 0F, 0F, 2, 10, 2);
        RL4.setPivot(-3.6F, 0.5F, -1F);
        RL4.setTextureSize(128, 64);
        RL4.mirror = true;
        RL4.setRotation( 0F, 0F, 0F);
        ModelPartLegacy LL1 = new ModelPartLegacy(71, 48);
        LL1.addCuboid(0F, 0F, 0F, 2, 10, 2);
        LL1.setPivot(1.6F, 0.5F, -1F);
        LL1.setTextureSize(128, 64);
        LL1.setRotation( 0F, 0F, 0F);
        ModelPartLegacy LL2 = new ModelPartLegacy(0, 47);
        LL2.addCuboid(-2.5F, -0.5F, -2.5F, 5, 9, 5);
        LL2.setPivot(-0.4F+offsetX_left, 1.0F+offsetY_left, -0.5F+offsetZ2);
        LL2.setTextureSize(128, 64);
        LL2.setRotation( 0F, 0F, 0F);
        LL2.mirror=false;
        ModelPartLegacy LL3 = new ModelPartLegacy(96, 5);
        LL3.addCuboid(0F, 0F, 0F, 2, 3, 3);
        LL3.setPivot(2.1F, 4.5F, -1.5F);
        LL3.setTextureSize(128, 64);
        LL3.setRotation( 0F, 0F, 0F);
        LL3.mirror=true;
        ModelPartLegacy LL4 = new ModelPartLegacy(71, 32);
        LL4.addCuboid(0F, 0F, 0F, 5, 2, 6);
        LL4.setPivot(-1.9F, 5F, -3F);
        LL4.setTextureSize(128, 64);
        LL4.setRotation( 0F, 0F, 0F);
        ModelPartLegacy RB1 = new ModelPartLegacy(95, 49);
        RB1.addCuboid(-2F, 0F, -2F, 3, 3, 1);
        RB1.setPivot(0.4F, 9.0F, -1.5F);
        RB1.setTextureSize(128, 64);
        RB1.mirror = true;
        RB1.setRotation( 0F, 0F, 0F);
        ModelPartLegacy RB2 = new ModelPartLegacy(100, 56);
        RB2.addCuboid(0F, 0F, 0F, 6, 2, 6);
        RB2.setPivot(-3.1F, 10.001F, -3F);
        RB2.setTextureSize(128, 64);
        RB2.mirror = true;
        RB2.setRotation( 0F, 0F, 0F);
        ModelPartLegacy RB3 = new ModelPartLegacy(0, 18);
        RB3.addCuboid(-1.5F +legOffsetX, 0F+legOffsetY, -2.5F, 5, 4, 5);
        RB3.setPivot(-2.5F +offsetX_right, 20.5F + offsetY_right, -0.5F);
        RB3.setTextureSize(128, 64);
        RB3.mirror = true;
        RB3.setRotation( 0F, 0F, 0F);
        ModelPartLegacy LB1 = new ModelPartLegacy(95, 49);
        LB1.addCuboid(-2F, 0F, -2F, 3, 3, 1);
        LB1.setPivot(0.6F, 9.0F, -1.5F);
        LB1.setTextureSize(128, 64);
        LB1.setRotation( 0F, 0F, 0F);
        ModelPartLegacy LB2 = new ModelPartLegacy(100, 56);
        LB2.addCuboid(0F, 0F, 0F, 6, 2, 6);
        LB2.setPivot(-2.9F, 10.001F, -3F);
        LB2.setTextureSize(128, 64);
        LB2.setRotation( 0F, 0F, 0F);
        ModelPartLegacy LB3 = new ModelPartLegacy(0, 18);
        LB3.addCuboid(-1.5F +legOffsetX, 0F+legOffsetY, -2.5F, 5, 4, 5);
        LB3.setPivot(-4.4F+offsetX_left, 20.5F+offsetY_left, -0.5F);
        LB3.setTextureSize(128, 64);
        LB3.setRotation( 0F, 0F, 0F);

        if (slot == EquipmentSlot.CHEST) {
            var right_arm = RA5.addTo(modelPartData, EntityModelPartNames.RIGHT_ARM);
            RA1.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA1");
            RA2.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA2");
            RA3.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA3");
            RA4.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA4");
            RA6.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA6");
            RA7.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA7");
            RA8.addTo(right_arm, EntityModelPartNames.RIGHT_ARM + "_RA8");

            var left_arm = LA8.addTo(modelPartData, EntityModelPartNames.LEFT_ARM);
            LA1.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA1");
            LA2.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA2");
            LA3.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA3");
            LA4.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA4");
            LA5.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA5");
            LA6.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA6");
            LA7.addTo(left_arm, EntityModelPartNames.LEFT_ARM + "_LA7");


            var body = B1.addTo(modelPartData, EntityModelPartNames.BODY);
            B2.addTo(body, EntityModelPartNames.BODY + "_B2");
            B3.addTo(body, EntityModelPartNames.BODY + "_B3");
            B4.addTo(body, EntityModelPartNames.BODY + "_B4");
            B5.addTo(body, EntityModelPartNames.BODY + "_B5");
            B6.addTo(body, EntityModelPartNames.BODY + "_B6");
            B7.addTo(body, EntityModelPartNames.BODY + "_B7");
            B8.addTo(body, EntityModelPartNames.BODY + "_B8");
            B9.addTo(body, EntityModelPartNames.BODY + "_B9");
            B10.addTo(body, EntityModelPartNames.BODY + "_B10");
            B11.addTo(body, EntityModelPartNames.BODY + "_B11");
            B12.addTo(body, EntityModelPartNames.BODY + "_B12");
            B13.addTo(body, EntityModelPartNames.BODY + "_B13");
            B14.addTo(body, EntityModelPartNames.BODY + "_B14");
            B15.addTo(body, EntityModelPartNames.BODY + "_B15");
            B16.addTo(body, EntityModelPartNames.BODY + "_B16");
        }
        if (slot == EquipmentSlot.HEAD) {
            var head = H3.addTo(modelPartData, EntityModelPartNames.HEAD);
            H2.addTo(head, EntityModelPartNames.HEAD + "_H2");
            H1.addTo(head, EntityModelPartNames.HEAD + "_H1");
            H4.addTo(head, EntityModelPartNames.HEAD + "_H4");
            H5.addTo(head, EntityModelPartNames.HEAD + "_H5");
            H6.addTo(head, EntityModelPartNames.HEAD + "_H6");
            H7.addTo(head, EntityModelPartNames.HEAD + "_H7");
            H8.addTo(head, EntityModelPartNames.HEAD + "_H8");
            H9.addTo(head, EntityModelPartNames.HEAD + "_H9");
            H10.addTo(head, EntityModelPartNames.HEAD + "_H10");
            H11.addTo(head, EntityModelPartNames.HEAD + "_H11");
        }
        if (slot == EquipmentSlot.FEET) {
            var right_leg = RB3.addTo(modelPartData, EntityModelPartNames.RIGHT_LEG);
            RB2.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RB2");
            RB1.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RB1");

            var left_leg = LB3.addTo(modelPartData, EntityModelPartNames.LEFT_LEG);
            LB2.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LB2");
            LB1.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LB1");

        }
        if (slot == EquipmentSlot.LEGS) {
            var body = P1.addTo(modelPartData, EntityModelPartNames.BODY);
            P2.addTo(body, EntityModelPartNames.BODY + "_P2");
            P3.addTo(body, EntityModelPartNames.BODY + "_P3");

            var right_leg = RL1.addTo(modelPartData, EntityModelPartNames.RIGHT_LEG);
            RL2.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RL2");
            RL3.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RL3");
            RL4.addTo(right_leg, EntityModelPartNames.RIGHT_LEG + "_RL4");

            var left_leg = LL2.addTo(modelPartData, EntityModelPartNames.LEFT_LEG);
            LL1.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LL1");
            LL3.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LL3");
            LL4.addTo(left_leg, EntityModelPartNames.LEFT_LEG + "_LL4");

        }

        TGArmorModelRegistry.addMissingParts(modelPartData);

        return modelData;
    }


}
