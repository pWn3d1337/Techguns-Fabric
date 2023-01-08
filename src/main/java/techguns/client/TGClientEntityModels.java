package techguns.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import techguns.TGArmors;
import techguns.TGEntities;
import techguns.TGIdentifier;
import techguns.client.models.armor.*;
import techguns.client.models.entities.GenericNPCModel;
import techguns.client.render.entities.npcs.RenderGenericNPC;
import techguns.client.render.entities.npcs.TGArmorRenderer;
import techguns.entities.npcs.GenericNPC;
import techguns.items.armors.GenericArmor;

import java.util.function.Function;

public class TGClientEntityModels {

    private static final Dilation OUTER_ARMOR_DILATION = new Dilation(1.0f);
    private static final Dilation INNER_ARMOR_DILATION = new Dilation(0.5f);

    protected static final TexturedModelData texturedModelData_biped = TexturedModelData.of(BipedEntityModel.getModelData(Dilation.NONE, 0.0f), 64, 64);
    protected static final TexturedModelData texturedModelData_biped_inner_armor = TexturedModelData.of(BipedEntityModel.getModelData(INNER_ARMOR_DILATION, 0.0f), 64, 32);
    protected static final TexturedModelData texturedModelData_biped_outer_armor = TexturedModelData.of(BipedEntityModel.getModelData(OUTER_ARMOR_DILATION, 0.0f), 64, 32);

    public static void initialize() {

        registerEntityRenderer(TGEntities.ZOMBIE_SOLDIER, GenericNPCModel::new, texturedModelData_biped, texturedModelData_biped_inner_armor, texturedModelData_biped_outer_armor);
        registerEntityRenderer(TGEntities.ZOMBIE_MINER, GenericNPCModel::new, texturedModelData_biped, texturedModelData_biped_inner_armor, texturedModelData_biped_outer_armor, "zombie_soldier");
        registerEntityRenderer(TGEntities.ZOMBIE_FARMER, GenericNPCModel::new, texturedModelData_biped, texturedModelData_biped_inner_armor, texturedModelData_biped_outer_armor, "zombie_soldier");
        registerEntityRenderer(TGEntities.ZOMBIE_POLICEMAN, GenericNPCModel::new, texturedModelData_biped, texturedModelData_biped_inner_armor, texturedModelData_biped_outer_armor);
        registerEntityRenderer(TGEntities.ARMY_SOLDIER, GenericNPCModel::new, texturedModelData_biped, texturedModelData_biped_inner_armor, texturedModelData_biped_outer_armor);

        addArmorRendering(ModelT3PowerArmor::new, ModelT3PowerArmor::getModelData, 128, 64,
                TGArmors.T3_POWER_HELMET,
                TGArmors.T3_POWER_CHESTPLATE,
                TGArmors.T3_POWER_LEGGINGS,
                TGArmors.T3_POWER_BOOTS
        );

        addArmorRendering(ModelSteamArmor::new, ModelSteamArmor::getModelData, 128, 64,
                TGArmors.T1_STEAM_HELMET,
                TGArmors.T1_STEAM_CHESTPLATE,
                TGArmors.T1_STEAM_LEGGINGS,
                TGArmors.T1_STEAM_BOOTS
        );

        addArmorRendering(ModelT4PowerArmorMk2::new, ModelT4PowerArmorMk2::getModelData, 128, 64,
                TGArmors.T4_POWER_HELMET,
                TGArmors.T4_POWER_CHESTPLATE,
                TGArmors.T4_POWER_LEGGINGS,
                TGArmors.T4_POWER_BOOTS
        );

        addArmorRendering(TGArmors.T2_RIOT_HELMET,    ModelArmorCoat::new, TexturedModelData.of(ModelArmorCoat.getModelData(OUTER_ARMOR_DILATION, EquipmentSlot.HEAD), 64, 64));
        addArmorRendering(TGArmors.T2_RIOT_CHESTPLATE,ModelArmorCoat::new, TexturedModelData.of(ModelArmorCoat.getModelData(INNER_ARMOR_DILATION, EquipmentSlot.CHEST), 64, 64));
        addArmorRendering(TGArmors.T2_RIOT_LEGGINGS,  ModelArmorCoat::new, TexturedModelData.of(ModelArmorCoat.getModelData(INNER_ARMOR_DILATION, EquipmentSlot.LEGS), 64, 64));
        addArmorRendering(TGArmors.T2_RIOT_BOOTS,     ModelArmorCoat::new, TexturedModelData.of(ModelArmorCoat.getModelData(INNER_ARMOR_DILATION, EquipmentSlot.FEET), 64, 64));

        addArmorRendering(TGArmors.T3_EXO_HELMET,    ModelExoSuit::new, TexturedModelData.of(ModelExoSuit.getModelData(OUTER_ARMOR_DILATION, EquipmentSlot.HEAD), 64, 64));
        addArmorRendering(TGArmors.T3_EXO_CHESTPLATE,ModelExoSuit::new, TexturedModelData.of(ModelExoSuit.getModelData(INNER_ARMOR_DILATION, EquipmentSlot.CHEST), 64, 64));
        addArmorRendering(TGArmors.T3_EXO_LEGGINGS,  ModelExoSuit_Layer2::new, TexturedModelData.of(ModelExoSuit_Layer2.getModelData(INNER_ARMOR_DILATION, EquipmentSlot.LEGS), 64, 64));
        addArmorRendering(TGArmors.T3_EXO_BOOTS,     ModelExoSuit::new, TexturedModelData.of(ModelExoSuit.getModelData(INNER_ARMOR_DILATION, EquipmentSlot.FEET), 64, 64));

        addArmorRenderingBiped(
                TGArmors.T1_MINER_HELMET,
                TGArmors.T1_MINER_CHESTPLATE,
                TGArmors.T1_MINER_LEGGINGS,
                TGArmors.T1_MINER_BOOTS
        );

        addArmorRenderingBiped(
                TGArmors.T1_SCOUT_HELMET,
                TGArmors.T1_SCOUT_CHESTPLATE,
                TGArmors.T1_SCOUT_LEGGINGS,
                TGArmors.T1_SCOUT_BOOTS
        );

        addArmorRenderingBiped(
                TGArmors.T1_COMBAT_HELMET,
                TGArmors.T1_COMBAT_CHESTPLATE,
                TGArmors.T1_COMBAT_LEGGINGS,
                TGArmors.T1_COMBAT_BOOTS
        );

        addArmorRenderingBiped(
                TGArmors.T2_COMBAT_HELMET,
                TGArmors.T2_COMBAT_CHESTPLATE,
                TGArmors.T2_COMBAT_LEGGINGS,
                TGArmors.T2_COMBAT_BOOTS
        );

        addArmorRenderingBiped(
                TGArmors.T3_COMBAT_HELMET,
                TGArmors.T3_COMBAT_CHESTPLATE,
                TGArmors.T3_COMBAT_LEGGINGS,
                TGArmors.T3_COMBAT_BOOTS
        );

        addArmorRenderingBiped(
                TGArmors.T2_HAZMAT_HELMET,
                TGArmors.T2_HAZMAT_CHESTPLATE,
                TGArmors.T2_HAZMAT_LEGGINGS,
                TGArmors.T2_HAZMAT_BOOTS
        );

        addArmorRenderingBiped(
                TGArmors.T2_COMMANDO_HELMET,
                TGArmors.T2_COMMANDO_CHESTPLATE,
                TGArmors.T2_COMMANDO_LEGGINGS,
                TGArmors.T2_COMMANDO_BOOTS
        );

        addArmorRenderingBiped(
                TGArmors.T3_MINER_HELMET,
                TGArmors.T3_MINER_CHESTPLATE,
                TGArmors.T3_MINER_LEGGINGS,
                TGArmors.T3_MINER_BOOTS
        );

        addArmorRenderingBiped(
                TGArmors.T4_PRAETOR_HELMET,
                TGArmors.T4_PRAETOR_CHESTPLATE,
                TGArmors.T4_PRAETOR_LEGGINGS,
                TGArmors.T4_PRAETOR_BOOTS
        );

        addArmorRendering(TGArmors.T2_BERET_HELMET, ModelBeret::new, TexturedModelData.of(ModelBeret.getModelData(OUTER_ARMOR_DILATION, EquipmentSlot.HEAD), 32, 32));
    }

    public static void addMissingParts(ModelPartData modelPartData) {
        for (var part : new String[]{EntityModelPartNames.HEAD, EntityModelPartNames.HAT, EntityModelPartNames.LEFT_ARM, EntityModelPartNames.RIGHT_ARM, EntityModelPartNames.BODY, EntityModelPartNames.LEFT_LEG, EntityModelPartNames.RIGHT_LEG}) {
            if (modelPartData.getChild(part) == null) {
                modelPartData.addChild(part, ModelPartBuilder.create(), ModelTransform.NONE);
            }
        }
    }

    @FunctionalInterface
    public interface ModelDataGetter{
        ModelData apply(Dilation dilation, EquipmentSlot slot);
    }

    public static void addArmorRendering(Function<ModelPart, BipedEntityModel<LivingEntity>> modelConstructor, ModelDataGetter modelDataGetter, int tex_width, int tex_height, GenericArmor... armors){
        for (GenericArmor armor: armors){
            addArmorRendering(armor, modelConstructor, modelDataGetter, tex_width, tex_height);
        }
    }

    public static void addArmorRendering(GenericArmor armorItem, Function<ModelPart, BipedEntityModel<LivingEntity>> modelConstructor, ModelDataGetter modelDataGetter, int tex_width, int tex_height){
        addArmorRendering(armorItem, modelConstructor, TexturedModelData.of(modelDataGetter.apply(Dilation.NONE, armorItem.getSlotType()), tex_width, tex_height));
    }

    public static void addArmorRendering(GenericArmor armorItem, Function<ModelPart, BipedEntityModel<LivingEntity>> modelConstructor, TexturedModelData modelData){
        String name = Registries.ITEM.getId(armorItem).getPath();
        EntityModelLayer layer = new EntityModelLayer(new TGIdentifier("armor"), name);
        EntityModelLayerRegistry.registerModelLayer(layer, () -> modelData);
        ArmorRenderer.register(new TGArmorRenderer(modelConstructor, layer, armorItem), armorItem);
    }

    /**
     * For default armor models that have camo switch
     */
    public static void addArmorRenderingBiped(GenericArmor head, GenericArmor chest, GenericArmor leggings, GenericArmor boots){
        ArmorRenderer.register(new TGArmorRenderer(BipedEntityModel::new, EntityModelLayers.PLAYER_OUTER_ARMOR,  head), head);
        ArmorRenderer.register(new TGArmorRenderer(BipedEntityModel::new, EntityModelLayers.PLAYER_OUTER_ARMOR,  chest), chest);
        ArmorRenderer.register(new TGArmorRenderer(BipedEntityModel::new, EntityModelLayers.PLAYER_INNER_ARMOR,  leggings), leggings);
        ArmorRenderer.register(new TGArmorRenderer(BipedEntityModel::new, EntityModelLayers.PLAYER_OUTER_ARMOR,  boots), boots);
    }


    /**
     * Registers an entity type, retrieves modellayer from entity name, pass different texture name
     */
    protected static <T extends GenericNPC> void registerEntityRenderer(EntityType<T> entityType, Function<ModelPart, GenericNPCModel<T>> modelConstructor, TexturedModelData modelData, TexturedModelData modelDataInnerArmor, TexturedModelData modelDataOuterArmor, String texturename) {
        String entityName = entityType.toString().substring("entity.techguns.".length());

        EntityModelLayer modelLayer = new EntityModelLayer(new TGIdentifier(entityName), "main");
        EntityModelLayer modelLayerLegsArmor = new EntityModelLayer(new TGIdentifier(entityName), "inner_armor");
        EntityModelLayer modelLayerBodyArmor = new EntityModelLayer(new TGIdentifier(entityName), "outer_armor");
        EntityRendererRegistry.register(entityType, (context) ->
                new RenderGenericNPC<T>(context, new TGIdentifier("textures/entity/" + texturename + ".png"), modelLayer, modelLayerLegsArmor, modelLayerBodyArmor, modelConstructor));
        EntityModelLayerRegistry.registerModelLayer(modelLayer, () -> modelData);
        EntityModelLayerRegistry.registerModelLayer(modelLayerLegsArmor, () -> modelDataInnerArmor);
        EntityModelLayerRegistry.registerModelLayer(modelLayerBodyArmor, () -> modelDataOuterArmor);
    }

    /**
     * Registers an entity type, retrieves modellayer and texture from entity name
     */
    protected static <T extends GenericNPC> void registerEntityRenderer(EntityType<T> entityType, Function<ModelPart, GenericNPCModel<T>> modelConstructor, TexturedModelData modelData, TexturedModelData modelDataInnerArmor, TexturedModelData modelDataOuterArmor) {
        String entityName = entityType.toString().substring("entity.techguns.".length());
        registerEntityRenderer(entityType, modelConstructor, modelData, modelDataInnerArmor, modelDataOuterArmor, entityName);
    }
}
