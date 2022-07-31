package techguns.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import techguns.TGEntities;
import techguns.TGIdentifier;
import techguns.client.models.armor.*;
import techguns.client.models.entities.GenericNPCModel;
import techguns.client.render.entities.npcs.RenderGenericNPC;
import techguns.entities.npcs.GenericNPC;

import java.util.function.Function;

public class TGClientEntityModels {

    private static final Dilation OUTER_ARMOR_DILATION = new Dilation(1.0f);
    private static final Dilation INNER_ARMOR_DILATION = new Dilation(0.5f);

    protected static final TexturedModelData texturedModelData_biped = TexturedModelData.of(BipedEntityModel.getModelData(Dilation.NONE, 0.0f), 64, 64);
    protected static final TexturedModelData texturedModelData_biped_inner_armor = TexturedModelData.of(BipedEntityModel.getModelData(INNER_ARMOR_DILATION, 0.0f), 64, 32);
    protected static final TexturedModelData texturedModelData_biped_outer_armor = TexturedModelData.of(BipedEntityModel.getModelData(OUTER_ARMOR_DILATION, 0.0f), 64, 32);



    public static final EntityModelLayer ARMOR_LAYER_T3_POWER_HEAD = new EntityModelLayer(new TGIdentifier("armor"), "t3_power_head");
    public static final EntityModelLayer ARMOR_LAYER_T3_POWER_CHEST = new EntityModelLayer(new TGIdentifier("armor"), "t3_power_chest");
    public static final EntityModelLayer ARMOR_LAYER_T3_POWER_LEGGINGS = new EntityModelLayer(new TGIdentifier("armor"), "t3_power_leggings");
    public static final EntityModelLayer ARMOR_LAYER_T3_POWER_BOOTS = new EntityModelLayer(new TGIdentifier("armor"), "t3_power_boots");

    public static final EntityModelLayer ARMOR_LAYER_T1_STEAM_HEAD = new EntityModelLayer(new TGIdentifier("armor"), "t1_steam_head");
    public static final EntityModelLayer ARMOR_LAYER_T1_STEAM_CHEST = new EntityModelLayer(new TGIdentifier("armor"), "t1_steam_chest");
    public static final EntityModelLayer ARMOR_LAYER_T1_STEAM_LEGGINGS = new EntityModelLayer(new TGIdentifier("armor"), "t1_steam_leggings");
    public static final EntityModelLayer ARMOR_LAYER_T1_STEAM_BOOTS = new EntityModelLayer(new TGIdentifier("armor"), "t1_steam_boots");

    public static final EntityModelLayer ARMOR_LAYER_T4_POWER_HEAD =     new EntityModelLayer(new TGIdentifier("armor"), "t4_power_head");
    public static final EntityModelLayer ARMOR_LAYER_T4_POWER_CHEST =    new EntityModelLayer(new TGIdentifier("armor"), "t4_power_chest");
    public static final EntityModelLayer ARMOR_LAYER_T4_POWER_LEGGINGS = new EntityModelLayer(new TGIdentifier("armor"), "t4_power_leggings");
    public static final EntityModelLayer ARMOR_LAYER_T4_POWER_BOOTS =    new EntityModelLayer(new TGIdentifier("armor"), "t4_power_boots");

    public static final EntityModelLayer ARMOR_LAYER_T2_RIOT_HEAD =     new EntityModelLayer(new TGIdentifier("armor"), "t2_riot_head");
    public static final EntityModelLayer ARMOR_LAYER_T2_RIOT_CHEST =    new EntityModelLayer(new TGIdentifier("armor"), "t2_riot_chest");
    public static final EntityModelLayer ARMOR_LAYER_T2_RIOT_LEGGINGS = new EntityModelLayer(new TGIdentifier("armor"), "t2_riot_leggings");
    public static final EntityModelLayer ARMOR_LAYER_T2_RIOT_BOOTS =    new EntityModelLayer(new TGIdentifier("armor"), "t2_riot_boots");

    public static final EntityModelLayer ARMOR_LAYER_T3_EXO_HEAD =     new EntityModelLayer(new TGIdentifier("armor"), "t3_exo_head");
    public static final EntityModelLayer ARMOR_LAYER_T3_EXO_CHEST =    new EntityModelLayer(new TGIdentifier("armor"), "t3_exo_chest");
    public static final EntityModelLayer ARMOR_LAYER_T3_EXO_LEGGINGS = new EntityModelLayer(new TGIdentifier("armor"), "t3_exo_leggings");
    public static final EntityModelLayer ARMOR_LAYER_T3_EXO_BOOTS =    new EntityModelLayer(new TGIdentifier("armor"), "t3_exo_boots");

    public static final EntityModelLayer ARMOR_LAYER_T1_MINER_HEAD =     new EntityModelLayer(new TGIdentifier("armor"), "t1_miner_head");
    public static final EntityModelLayer ARMOR_LAYER_T1_MINER_CHEST =    new EntityModelLayer(new TGIdentifier("armor"), "t1_miner_chest");
    public static final EntityModelLayer ARMOR_LAYER_T1_MINER_LEGGINGS = new EntityModelLayer(new TGIdentifier("armor"), "t1_miner_leggings");
    public static final EntityModelLayer ARMOR_LAYER_T1_MINER_BOOTS =    new EntityModelLayer(new TGIdentifier("armor"), "t1_miner_boots");

    public static final EntityModelLayer ARMOR_LAYER_T1_SCOUT_HEAD =     new EntityModelLayer(new TGIdentifier("armor"), "t1_scout_head");
    public static final EntityModelLayer ARMOR_LAYER_T1_SCOUT_CHEST =    new EntityModelLayer(new TGIdentifier("armor"), "t1_scout_chest");
    public static final EntityModelLayer ARMOR_LAYER_T1_SCOUT_LEGGINGS = new EntityModelLayer(new TGIdentifier("armor"), "t1_scout_leggings");
    public static final EntityModelLayer ARMOR_LAYER_T1_SCOUT_BOOTS =    new EntityModelLayer(new TGIdentifier("armor"), "t1_scout_boots");

    public static final EntityModelLayer ARMOR_LAYER_T2_COMBAT_HEAD =     new EntityModelLayer(new TGIdentifier("armor"), "t2_combat_head");
    public static final EntityModelLayer ARMOR_LAYER_T2_COMBAT_CHEST =    new EntityModelLayer(new TGIdentifier("armor"), "t2_combat_chest");
    public static final EntityModelLayer ARMOR_LAYER_T2_COMBAT_LEGGINGS = new EntityModelLayer(new TGIdentifier("armor"), "t2_combat_leggings");
    public static final EntityModelLayer ARMOR_LAYER_T2_COMBAT_BOOTS =    new EntityModelLayer(new TGIdentifier("armor"), "t2_combat_boots");

    public static final EntityModelLayer ARMOR_LAYER_T3_COMBAT_HEAD =     new EntityModelLayer(new TGIdentifier("armor"), "t3_combat_head");
    public static final EntityModelLayer ARMOR_LAYER_T3_COMBAT_CHEST =    new EntityModelLayer(new TGIdentifier("armor"), "t3_combat_chest");
    public static final EntityModelLayer ARMOR_LAYER_T3_COMBAT_LEGGINGS = new EntityModelLayer(new TGIdentifier("armor"), "t3_combat_leggings");
    public static final EntityModelLayer ARMOR_LAYER_T3_COMBAT_BOOTS =    new EntityModelLayer(new TGIdentifier("armor"), "t3_combat_boots");


    public static void initialize() {

        registerEntityRenderer(TGEntities.ZOMBIE_SOLDIER, GenericNPCModel::new, texturedModelData_biped, texturedModelData_biped_inner_armor, texturedModelData_biped_outer_armor);

        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_POWER_HEAD, () -> TexturedModelData.of(ModelT3PowerArmor.getModelData(Dilation.NONE, EquipmentSlot.HEAD), 128, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_POWER_CHEST, () -> TexturedModelData.of(ModelT3PowerArmor.getModelData(Dilation.NONE, EquipmentSlot.CHEST), 128, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_POWER_LEGGINGS, () -> TexturedModelData.of(ModelT3PowerArmor.getModelData(Dilation.NONE, EquipmentSlot.LEGS), 128, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_POWER_BOOTS, () -> TexturedModelData.of(ModelT3PowerArmor.getModelData(Dilation.NONE, EquipmentSlot.FEET), 128, 64));

        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T1_STEAM_HEAD, () -> TexturedModelData.of(ModelSteamArmor.getModelData(Dilation.NONE, EquipmentSlot.HEAD), 128, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T1_STEAM_CHEST, () -> TexturedModelData.of(ModelSteamArmor.getModelData(Dilation.NONE, EquipmentSlot.CHEST), 128, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T1_STEAM_LEGGINGS, () -> TexturedModelData.of(ModelSteamArmor.getModelData(Dilation.NONE, EquipmentSlot.LEGS), 128, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T1_STEAM_BOOTS, () -> TexturedModelData.of(ModelSteamArmor.getModelData(Dilation.NONE, EquipmentSlot.FEET), 128, 64));

        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T4_POWER_HEAD, () ->     TexturedModelData.of(ModelT4PowerArmorMk2.getModelData(Dilation.NONE, EquipmentSlot.HEAD), 128, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T4_POWER_CHEST, () ->    TexturedModelData.of(ModelT4PowerArmorMk2.getModelData(Dilation.NONE, EquipmentSlot.CHEST), 128, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T4_POWER_LEGGINGS, () -> TexturedModelData.of(ModelT4PowerArmorMk2.getModelData(Dilation.NONE, EquipmentSlot.LEGS), 128, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T4_POWER_BOOTS, () ->    TexturedModelData.of(ModelT4PowerArmorMk2.getModelData(Dilation.NONE, EquipmentSlot.FEET), 128, 64));

        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T2_RIOT_HEAD, () ->     TexturedModelData.of(ModelArmorCoat.getModelData(OUTER_ARMOR_DILATION, EquipmentSlot.HEAD), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T2_RIOT_CHEST, () ->    TexturedModelData.of(ModelArmorCoat.getModelData(INNER_ARMOR_DILATION, EquipmentSlot.CHEST), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T2_RIOT_LEGGINGS, () -> TexturedModelData.of(ModelArmorCoat.getModelData(INNER_ARMOR_DILATION, EquipmentSlot.LEGS), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T2_RIOT_BOOTS, () ->    TexturedModelData.of(ModelArmorCoat.getModelData(INNER_ARMOR_DILATION, EquipmentSlot.FEET), 64, 64));

        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_EXO_HEAD, () ->     TexturedModelData.of(ModelExoSuit.getModelData(OUTER_ARMOR_DILATION, EquipmentSlot.HEAD), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_EXO_CHEST, () ->    TexturedModelData.of(ModelExoSuit.getModelData(INNER_ARMOR_DILATION, EquipmentSlot.CHEST), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_EXO_LEGGINGS, () -> TexturedModelData.of(ModelExoSuit_Layer2.getModelData(INNER_ARMOR_DILATION, EquipmentSlot.LEGS), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_EXO_BOOTS, () ->    TexturedModelData.of(ModelExoSuit.getModelData(INNER_ARMOR_DILATION, EquipmentSlot.FEET), 64, 64));

        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T1_MINER_HEAD, () ->     texturedModelData_biped_outer_armor);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T1_MINER_CHEST, () ->    texturedModelData_biped_outer_armor);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T1_MINER_LEGGINGS, () -> texturedModelData_biped_inner_armor);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T1_MINER_BOOTS, () ->    texturedModelData_biped_outer_armor);

        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T1_SCOUT_HEAD, () ->     texturedModelData_biped_outer_armor);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T1_SCOUT_CHEST, () ->    texturedModelData_biped_outer_armor);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T1_SCOUT_LEGGINGS, () -> texturedModelData_biped_inner_armor);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T1_SCOUT_BOOTS, () ->    texturedModelData_biped_outer_armor);

        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T2_COMBAT_HEAD, () ->     texturedModelData_biped_outer_armor);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T2_COMBAT_CHEST, () ->    texturedModelData_biped_outer_armor);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T2_COMBAT_LEGGINGS, () -> texturedModelData_biped_inner_armor);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T2_COMBAT_BOOTS, () ->    texturedModelData_biped_outer_armor);

        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_COMBAT_HEAD, () ->     texturedModelData_biped_outer_armor);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_COMBAT_CHEST, () ->    texturedModelData_biped_outer_armor);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_COMBAT_LEGGINGS, () -> texturedModelData_biped_inner_armor);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_COMBAT_BOOTS, () ->    texturedModelData_biped_outer_armor);

    }


    /**
     * Registers an entity type, retrieves modellayer and texture from entity name
     *
     * @param entityType
     * @param modelData
     * @param <T>
     */
    protected static <T extends GenericNPC> void registerEntityRenderer(EntityType<T> entityType, Function<ModelPart, GenericNPCModel<T>> modelConstructor, TexturedModelData modelData, TexturedModelData modelDataInnerArmor, TexturedModelData modelDataOuterArmor) {
        String entityName = entityType.toString().substring("entity.techguns.".length());

        EntityModelLayer modelLayer = new EntityModelLayer(new TGIdentifier(entityName), "main");
        EntityModelLayer modelLayerLegsArmor = new EntityModelLayer(new TGIdentifier(entityName), "inner_armor");
        EntityModelLayer modelLayerBodyArmor = new EntityModelLayer(new TGIdentifier(entityName), "outer_armor");
        EntityRendererRegistry.register(entityType, (context) ->
                new RenderGenericNPC<T>(context, new TGIdentifier("textures/entity/" + entityName + ".png"), modelLayer, modelLayerLegsArmor, modelLayerBodyArmor, modelConstructor));
        EntityModelLayerRegistry.registerModelLayer(modelLayer, () -> modelData);
        EntityModelLayerRegistry.registerModelLayer(modelLayerLegsArmor, () -> modelDataInnerArmor);
        EntityModelLayerRegistry.registerModelLayer(modelLayerBodyArmor, () -> modelDataOuterArmor);
    }
}
