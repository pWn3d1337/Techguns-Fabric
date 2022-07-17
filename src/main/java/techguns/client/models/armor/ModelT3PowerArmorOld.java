package techguns.client.models.armor;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.ModelPart;

public class ModelT3PowerArmorOld extends BipedEntityModel {

    ModelPart RA02;
    ModelPart RA03;
    ModelPart RA01;
    ModelPart RA04;
    ModelPart RA05;
    ModelPart RA06;
    ModelPart LA01;
    ModelPart LA02;
    ModelPart LA03;
    ModelPart LA04;
    ModelPart LA05;
    ModelPart LA06;
    ModelPart H01;
    ModelPart H04;
    ModelPart H06;
    ModelPart H03;
    ModelPart H09;
    ModelPart H08;
    ModelPart H05;
    ModelPart H10;
    ModelPart H02;
    ModelPart H07;
    ModelPart B01;
    ModelPart B02;
    ModelPart B03;
    ModelPart B04;
    ModelPart B05;
    ModelPart B09;
    ModelPart B07;
    ModelPart B06;
    ModelPart B08;
    ModelPart P03;
    ModelPart P01;
    ModelPart P02;
    ModelPart RL01;
    ModelPart RL02;
    ModelPart RL03;
    ModelPart RL04;
    ModelPart RL05;
    ModelPart LL01;
    ModelPart LL02;
    ModelPart LL03;
    ModelPart LL04;
    ModelPart LL05;
    ModelPart RB01;
    ModelPart RB02;
    ModelPart LB01;
    ModelPart LB02;

    public ModelT3PowerArmorOld(ModelPart root) {
        super(root);
    }


  /*  public ModelT3PowerArmorOld(int type) {
        this(type, 0.0f);
    }

    public ModelT3PowerArmorOld(int type, float scale)
    {
        super(scale);

        this.bipedHead = new ModelPart(this, 0, 0);
        this.bipedHead.setPivot(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear = new ModelPart(this, 32, 0);
        this.bipedHeadwear.setPivot(0.0F, 0.0F, 0.0F);
        this.bipedBody = new ModelPart(this, 16, 16);
        this.bipedBody.setPivot(0.0F, 0.0F, 0.0F);
        this.bipedRightArm = new ModelPart(this, 40, 16);
        this.bipedRightArm.setPivot(-5.0F, 2.0F, 0.0F);
        this.bipedLeftArm = new ModelPart(this, 40, 16);
        //this.bipedLeftArm.mirror = false;
        this.bipedLeftArm.setPivot(5.0F, 2.0F, 0.0F);
        this.bipedRightLeg = new ModelPart(this, 0, 16);
        this.bipedRightLeg.setPivot(-1.9F, 12.0F, 0.0F);
        this.bipedLeftLeg = new ModelPart(this, 0, 16);
        //this.bipedLeftLeg.mirror = false;
        this.bipedLeftLeg.setPivot(1.9F, 12.0F, 0.0F);


        textureWidth = 128;
        textureHeight = 64;



        float ArmOffsetX = 5.0F;
        float ArmOffsetY = -2.0F;
        float LegOffsetX = 1.9F;
        float LegOffsetY = -12.0F;
        //float RARotZ = 0.0F;

        RA02 = new ModelPart(this, 72, 8);
        RA02.addCuboid(-8F, 0F, 0F, 7, 2, 8);
        RA02.setPivot(-5F+ArmOffsetX, -2F+ArmOffsetY, -4F);
        RA02.setTextureSize(128, 64);
        RA02.mirror = true;
        setRotation(RA02, 0F, 0F, -0.5235988F);

        RA03 = new ModelPart(this, 70, 27);
        RA03.addCuboid(0F, 0F, 0F, 3, 3, 6);
        RA03.setPivot(-8F+ArmOffsetX, 0F+ArmOffsetY, -3F);
        RA03.setTextureSize(128, 64);
        RA03.mirror = true;
        setRotation(RA03, 0F, 0F, 0F);

        RA01 = new ModelPart(this, 69, 38);
        RA01.addCuboid(0F, 0F, 0F, 5, 13, 5);
        RA01.setPivot(-8.5F+ArmOffsetX, -0.5F+ArmOffsetY, -2.5F);
        RA01.setTextureSize(128, 64);
        RA01.mirror = true;
        setRotation(RA01, 0F, 0F, 0F);

        RA04 = new ModelPart(this, 73, 19);
        RA04.addCuboid(-8.5F, 0F, 0F, 7, 1, 6);
        RA04.setPivot(-5F+ArmOffsetX, -3F+ArmOffsetY, -3F);
        RA04.setTextureSize(128, 64);
        RA04.mirror = true;
        setRotation(RA04, 0F, 0F, -0.5235988F);

        RA05 = new ModelPart(this, 56, 38);
        RA05.addCuboid(0F, 0F, 0F, 1, 5, 4);
        RA05.setPivot(-9.5F+ArmOffsetX, 6.5F+ArmOffsetY, -2F);
        RA05.setTextureSize(128, 64);
        RA05.mirror = true;
        setRotation(RA05, 0F, 0F, 0F);

        RA06 = new ModelPart(this, 46, 38);
        RA06.addCuboid(0F, 0F, 0F, 3, 4, 1);
        RA06.setPivot(-8F+ArmOffsetX, 4F+ArmOffsetY, 2F);
        RA06.setTextureSize(128, 64);
        RA06.mirror = true;
        setRotation(RA06, 0F, 0F, 0F);

        LA01 = new ModelPart(this, 90, 38);
        LA01.addCuboid(0F, 0F, 0F, 5, 13, 5);
        LA01.setPivot(3.5F-ArmOffsetX, -0.5F+ArmOffsetY, -2.5F);
        LA01.setTextureSize(128, 64);
        LA01.mirror = true;
        setRotation(LA01, 0F, 0F, 0F);


        LA02 = new ModelPart(this, 72, 8);
        LA02.addCuboid(1F, 0F, 0F, 7, 2, 8);
        LA02.setPivot(5F-ArmOffsetX, -2F+ArmOffsetY, -4F);
        LA02.setTextureSize(128, 64);
        LA02.mirror = true;
        setRotation(LA02, 0F, 0F, 0.5235988F);

        LA03 = new ModelPart(this, 70, 27);
        LA03.addCuboid(0F, 0F, 0F, 3, 3, 6);
        LA03.setPivot(5F-ArmOffsetX, 0F+ArmOffsetY, -3F);
        LA03.setTextureSize(128, 64);
        LA03.mirror = true;
        setRotation(LA03, 0F, 0F, 0F);

        LA04 = new ModelPart(this, 100, 19);
        LA04.addCuboid(1.5F, 0F, 0F, 7, 1, 6);
        LA04.setPivot(5F-ArmOffsetX, -3F+ArmOffsetY, -3F);
        LA04.setTextureSize(128, 64);
        LA04.mirror = true;
        setRotation(LA04, 0F, 0F, 0.5235988F);

        LA05 = new ModelPart(this, 56, 38);
        LA05.addCuboid(-1.5F, -2.5F, -2F, 1, 5, 4);
        LA05.setPivot(10F-ArmOffsetX, 9F+ArmOffsetY, 0F);
        LA05.setTextureSize(128, 64);
        LA05.mirror = true;
        setRotation(LA05, 0F, 0F, 0F);

        LA06 = new ModelPart(this, 46, 38);
        LA06.addCuboid(-1F, -1F, 0F, 3, 4, 1);
        LA06.setPivot(6F-ArmOffsetX, 5F+ArmOffsetY, 2F);
        LA06.setTextureSize(128, 64);
        LA06.mirror = true;
        setRotation(LA06, 0F, 0F, 0F);

        H01 = new ModelPart(this, 0, 0);
        H01.addCuboid(0F, 0F, 0F, 9, 9, 9,scale);
        H01.setPivot(-4.5F, -8.5F, -4.5F);
        H01.setTextureSize(128, 64);
        H01.mirror = true;
        setRotation(H01, 0F, 0F, 0F);

        H04 = new ModelPart(this, 0, 28);
        H04.addCuboid(-3F, 0F, 0F, 3, 2, 2,scale);
        H04.setPivot(-2F, -2F, -5.5F);
        H04.setTextureSize(128, 64);
        H04.mirror = true;
        setRotation(H04, 0F, 0F, 0F);

        H06 = new ModelPart(this, 89, 27);
        H06.addCuboid(0F, 0F, 0F, 1, 2, 8,scale);
        H06.setPivot(-5F, -2F, -3.5F);
        H06.setTextureSize(128, 64);
        H06.mirror = true;
        setRotation(H06, 0F, 0F, 0F);

        H03 = new ModelPart(this, 0, 33);
        H03.addCuboid(0F, 0F, 0F, 3, 2, 2,scale);
        H03.setPivot(2F, -2F, -5.5F);
        H03.setTextureSize(128, 64);
        H03.mirror = true;
        setRotation(H03, 0F, 0F, 0F);

        H09 = new ModelPart(this, 24, 23);
        H09.addCuboid(0F, 0F, 0F, 4, 3, 1,scale);
        H09.setPivot(-2F, -2.5F, 4.5F);
        H09.setTextureSize(128, 64);
        H09.mirror = true;
        setRotation(H09, 0F, 0F, 0F);

        H08 = new ModelPart(this, 19, 19);
        H08.addCuboid(0F, 0F, 0F, 9, 2, 1,scale);
        H08.setPivot(-4.5F, -2F, 4F);
        H08.setTextureSize(128, 64);
        H08.mirror = true;
        setRotation(H08, 0F, 0F, 0F);

        H05 = new ModelPart(this, 13, 27);
        H05.addCuboid(0F, 0F, 0F, 1, 2, 8, scale);
        H05.setPivot(4F, -2F, -3.5F);
        H05.setTextureSize(128, 64);
        H05.mirror = true;
        setRotation(H05, 0F, 0F, 0F);

        H10 = new ModelPart(this, 28, 0);
        H10.addCuboid(-1F, -1F, 0F, 2, 2, 4,scale);
        H10.setPivot(-5.5F, -6F, -3F);
        H10.setTextureSize(128, 64);
        H10.mirror = true;
        setRotation(H10, 0F, 0F, 0.7853982F);

        H02 = new ModelPart(this, 0, 19);
        H02.addCuboid(0F, 0F, 0F, 4, 4, 3,scale);
        H02.setPivot(-2F, -3F, -6F);
        H02.setTextureSize(128, 64);
        H02.mirror = true;
        setRotation(H02, 0F, 0F, 0F);

        H07 = new ModelPart(this, 0, 5);
        H07.addCuboid(0F, 0F, 0F, 2, 2, 1,scale);
        H07.setPivot(1F, -8F, -5F);
        H07.setTextureSize(128, 64);
        H07.mirror = true;
        setRotation(H07, 0F, 0F, 0F);

        B01 = new ModelPart(this, 41, 19);
        B01.addCuboid(-4F, 0F, -2F, 9, 12, 5);
        B01.setPivot(-0.5F, -0.5F, -0.5F);
        B01.setTextureSize(128, 64);
        B01.mirror = true;
        setRotation(B01, 0F, 0F, 0F);

        B02 = new ModelPart(this, 48, 0);
        B02.addCuboid(0F, 0F, 0F, 8, 5, 3);
        B02.setPivot(-4F, 2F, -5F);
        B02.setTextureSize(128, 64);
        B02.mirror = true;
        setRotation(B02, 0F, 0F, 0F);

        B03 = new ModelPart(this, 71, 0);
        B03.addCuboid(0F, 0F, 0F, 7, 5, 2);
        B03.setPivot(-3.5F, 7F, -4.5F);
        B03.setTextureSize(128, 64);
        B03.mirror = true;
        setRotation(B03, 0.3665191F, 0F, 0F);

        B04 = new ModelPart(this, 48, 10);
        B04.addCuboid(-4F, 0F, -2F, 3, 5, 2);
        B04.setPivot(4.5F, 5F, 4F);
        B04.setTextureSize(128, 64);
        B04.mirror = true;
        setRotation(B04, 0F, 0F, 0F);

        B05 = new ModelPart(this, 48, 10);
        B05.addCuboid(-4F, 0F, -2F, 3, 5, 2);
        B05.setPivot(0.5F, 5F, 4F);
        B05.setTextureSize(128, 64);
        B05.mirror = true;

        setRotation(B05, 0F, 0F, 0F);
        B09 = new ModelPart(this, 64, 10);
        B09.addCuboid(-1F, -1F, 0F, 2, 2, 1);
        B09.setPivot(0F, 2.5F, 3F);
        B09.setTextureSize(128, 64);
        B09.mirror = true;

        setRotation(B09, 0F, 0F, 0.7853982F);
        B07 = new ModelPart(this, 59, 10);
        B07.addCuboid(0F, 0F, 0F, 1, 2, 1);
        B07.setPivot(1.5F, 3F, 2.5F);
        B07.setTextureSize(128, 64);
        B07.mirror = true;
        setRotation(B07, 0F, 0F, 0F);

        B06 = new ModelPart(this, 59, 10);
        B06.addCuboid(0F, 0F, 0F, 1, 2, 1);
        B06.setPivot(-2.5F, 3F, 2.5F);
        B06.setTextureSize(128, 64);
        B06.mirror = true;
        setRotation(B06, 0F, 0F, 0F);

        B08 = new ModelPart(this, 59, 15);
        B08.addCuboid(0F, 0F, 0F, 5, 1, 1);
        B08.setPivot(-2.5F, 2F, 2.5F);
        B08.setTextureSize(128, 64);
        B08.mirror = true;
        setRotation(B08, 0F, 0F, 0F);

        P03 = new ModelPart(this, 21, 47);
        P03.addCuboid(0F, 0F, 0F, 3, 2, 6);
        P03.setPivot(-1.5F, 13F, -3F);
        P03.setTextureSize(128, 64);
        P03.mirror = true;
        setRotation(P03, 0F, 0F, 0F);

        P01 = new ModelPart(this, 40, 48);
        P01.addCuboid(0F, 0F, 0F, 9, 3, 5);
        P01.setPivot(-4.5F, 11.5F, -2.5F);
        P01.setTextureSize(128, 64);
        P01.mirror = true;
        setRotation(P01, 0F, 0F, 0F);

        P02 = new ModelPart(this, 0, 38);
        P02.addCuboid(0F, 0F, 0F, 10, 2, 6);
        P02.setPivot(-5F, 11F, -3F);
        P02.setTextureSize(128, 64);
        P02.mirror = true;
        setRotation(P02, 0F, 0F, 0F);

        RL01 = new ModelPart(this, 0, 47);
        RL01.addCuboid(-2F, 0F, -2F, 5, 9, 5);
        RL01.setPivot(-2.5F+LegOffsetX, 11.5F+LegOffsetY, -0.5F);
        RL01.setTextureSize(128, 64);
        RL01.mirror = true;
        setRotation(RL01, 0F, 0F, 0F);

        RL02 = new ModelPart(this, 21, 56);
        RL02.addCuboid(0F, 0F, 0F, 3, 3, 1);
        RL02.setPivot(-2.3F+LegOffsetX, 14.5F+LegOffsetY, -3F);
        RL02.setTextureSize(128, 64);
        RL02.mirror = true;
        setRotation(RL02, 0F, 0F, 0.7853982F);

        RL03 = new ModelPart(this, 30, 56);
        RL03.addCuboid(0F, -1F, -1F, 1, 2, 2);
        RL03.setPivot(-5F+LegOffsetX, 16.5F+LegOffsetY, 0F);
        RL03.setTextureSize(128, 64);
        RL03.mirror = true;
        setRotation(RL03, 0.7853982F, 0F, 0F);

        RL04 = new ModelPart(this, 33, 38);
        RL04.addCuboid(0F, -3F, -1F, 1, 3, 2);
        RL04.setPivot(-5F+LegOffsetX, 15.5F+LegOffsetY, 0F);
        RL04.setTextureSize(128, 64);
        RL04.mirror = true;
        setRotation(RL04, 0F, 0F, 0.148353F);

        RL05 = new ModelPart(this, 33, 38);
        RL05.addCuboid(0F, 0F, -1F, 1, 3, 2);
        RL05.setPivot(-5F+LegOffsetX, 17.5F+LegOffsetY, 0F);
        RL05.setTextureSize(128, 64);
        RL05.mirror = true;
        setRotation(RL05, 0F, 0F, -0.148353F);

        LL01 = new ModelPart(this, 0, 47);
        LL01.addCuboid(-2F, 0F, -2F, 5, 9, 5);
        LL01.setPivot(1.5F-LegOffsetX, 11.5F+LegOffsetY, -0.5F);
        LL01.setTextureSize(128, 64);
        LL01.mirror = true;
        setRotation(LL01, 0F, 0F, 0F);

        LL02 = new ModelPart(this, 21, 56);
        LL02.addCuboid(0F, 0F, 0F, 3, 3, 1);
        LL02.setPivot(2.3F-LegOffsetX, 14.5F+LegOffsetY, -3F);
        LL02.setTextureSize(128, 64);
        LL02.mirror = true;
        setRotation(LL02, 0F, 0F, 0.7853982F);

        LL03 = new ModelPart(this, 30, 56);
        LL03.addCuboid(0F, -1F, -1F, 1, 2, 2);
        LL03.setPivot(4F-LegOffsetX, 16.5F+LegOffsetY, 0F);
        LL03.setTextureSize(128, 64);
        LL03.mirror = true;
        setRotation(LL03, 0.7853982F, 0F, 0F);

        LL04 = new ModelPart(this, 33, 38);
        LL04.addCuboid(0F, -3F, -1F, 1, 3, 2);
        LL04.setPivot(4F-LegOffsetX, 15.5F+LegOffsetY, 0F);
        LL04.setTextureSize(128, 64);
        LL04.mirror = true;
        setRotation(LL04, 0F, 0F, -0.148353F);

        LL05 = new ModelPart(this, 33, 38);
        LL05.addCuboid(0F, 0F, -1F, 1, 3, 2);
        LL05.setPivot(4F-LegOffsetX, 17.5F+LegOffsetY, 0F);
        LL05.setTextureSize(128, 64);
        LL05.mirror = true;
        setRotation(LL05, 0F, 0F, 0.148353F);

        RB01 = new ModelPart(this, 103, 0);
        RB01.addCuboid(-2F, 0F, -2F, 5, 4, 5);
        RB01.setPivot(-2.5F+LegOffsetX, 20.5F+LegOffsetY, -0.5F);
        RB01.setTextureSize(128, 64);
        RB01.mirror = true;
        setRotation(RB01, 0F, 0F, 0F);

        RB02 = new ModelPart(this, 103, 10);
        RB02.addCuboid(-2F, 0F, -2F, 4, 2, 2);
        RB02.setPivot(-2F+LegOffsetX, 22F+LegOffsetY, -2F);
        RB02.setTextureSize(128, 64);
        RB02.mirror = true;
        setRotation(RB02, 0F, 0F, 0F);

        LB01 = new ModelPart(this, 103, 0);
        LB01.addCuboid(2F, 0F, -2F, 5, 4, 5);
        LB01.setPivot(-2.5F-LegOffsetX, 20.5F+LegOffsetY, -0.5F);
        LB01.setTextureSize(128, 64);
        LB01.mirror = true;
        setRotation(LB01, 0F, 0F, 0F);

        LB02 = new ModelPart(this, 103, 10);
        LB02.addCuboid(-2F, 0F, -2F, 4, 2, 2);
        LB02.setPivot(2F-LegOffsetX, 22F+LegOffsetY, -2F);
        LB02.setTextureSize(128, 64);
        LB02.mirror = true;
        setRotation(LB02, 0F, 0F, 0F);

        if (type==0){
            this.bipedLeftArm.addChild(LA01);
            this.bipedLeftArm.addChild(LA02);
            this.bipedLeftArm.addChild(LA03);
            this.bipedLeftArm.addChild(LA04);
            this.bipedLeftArm.addChild(LA05);
            this.bipedLeftArm.addChild(LA06);

            this.bipedRightArm.addChild(RA01);
            this.bipedRightArm.addChild(RA02);
            this.bipedRightArm.addChild(RA03);
            this.bipedRightArm.addChild(RA04);
            this.bipedRightArm.addChild(RA05);
            this.bipedRightArm.addChild(RA06);

            this.bipedHead.addChild(H01);
            this.bipedHead.addChild(H02);
            this.bipedHead.addChild(H03);
            this.bipedHead.addChild(H04);
            this.bipedHead.addChild(H05);
            this.bipedHead.addChild(H06);
            this.bipedHead.addChild(H07);
            this.bipedHead.addChild(H08);
            this.bipedHead.addChild(H09);
            this.bipedHead.addChild(H10);

            this.bipedBody.addChild(B01);
            this.bipedBody.addChild(B02);
            this.bipedBody.addChild(B03);
            this.bipedBody.addChild(B04);
            this.bipedBody.addChild(B05);
            this.bipedBody.addChild(B06);
            this.bipedBody.addChild(B07);
            this.bipedBody.addChild(B08);
            this.bipedBody.addChild(B09);

            this.bipedRightLeg.addChild(RB01);
            this.bipedRightLeg.addChild(RB02);

            this.bipedLeftLeg.addChild(LB01);
            this.bipedLeftLeg.addChild(LB02);

        } else {
            this.bipedBody.addChild(P01);
            this.bipedBody.addChild(P02);
            this.bipedBody.addChild(P03);


            this.bipedRightLeg.addChild(RL01);
            this.bipedRightLeg.addChild(RL02);
            this.bipedRightLeg.addChild(RL03);
            this.bipedRightLeg.addChild(RL04);
            this.bipedRightLeg.addChild(RL05);

            this.bipedLeftLeg.addChild(LL01);
            this.bipedLeftLeg.addChild(LL02);
            this.bipedLeftLeg.addChild(LL03);
            this.bipedLeftLeg.addChild(LL04);
            this.bipedLeftLeg.addChild(LL05);
        }

    }

    private void setRotation(ModelPart model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
*/
}
