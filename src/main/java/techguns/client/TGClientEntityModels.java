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
import techguns.client.models.armor.ModelT3PowerArmor;
import techguns.client.models.entities.GenericNPCModel;
import techguns.client.render.entities.npcs.RenderGenericNPC;
import techguns.client.render.entities.npcs.TGArmorFeatureRenderer;
import techguns.entities.npcs.GenericNPC;

import java.util.function.Function;

public class TGClientEntityModels {
    protected static final TexturedModelData texturedModelData_biped = TexturedModelData.of(BipedEntityModel.getModelData(Dilation.NONE, 0.0f), 64, 64);
    protected static final TexturedModelData texturedModelData_biped_inner_armor = TexturedModelData.of(BipedEntityModel.getModelData(new Dilation(0.5f), 0.0f), 64, 32);
    protected static final TexturedModelData texturedModelData_biped_outer_armor = TexturedModelData.of(BipedEntityModel.getModelData(new Dilation(1.0f), 0.0f), 64, 32);


    public static final EntityModelLayer ARMOR_LAYER_T3_POWER_HEAD = new EntityModelLayer(new TGIdentifier("armor"), "t3_power_head");
    public static final EntityModelLayer ARMOR_LAYER_T3_POWER_CHEST = new EntityModelLayer(new TGIdentifier("armor"), "t3_power_chest");
    public static final EntityModelLayer ARMOR_LAYER_T3_POWER_LEGGINGS= new EntityModelLayer(new TGIdentifier("armor"), "t3_power_leggings");
    public static final EntityModelLayer ARMOR_LAYER_T3_POWER_BOOTS = new EntityModelLayer(new TGIdentifier("armor"), "t3_power_boots");

    public static void initialize() {

        registerEntityRenderer(TGEntities.ZOMBIE_SOLDIER, GenericNPCModel::new, texturedModelData_biped, texturedModelData_biped_inner_armor, texturedModelData_biped_outer_armor);

        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_POWER_HEAD, () -> TexturedModelData.of(ModelT3PowerArmor.getModelData(Dilation.NONE, EquipmentSlot.HEAD), 128, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_POWER_CHEST, () -> TexturedModelData.of(ModelT3PowerArmor.getModelData(Dilation.NONE, EquipmentSlot.CHEST), 128, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_POWER_LEGGINGS, () -> TexturedModelData.of(ModelT3PowerArmor.getModelData(Dilation.NONE, EquipmentSlot.LEGS), 128, 64));
        EntityModelLayerRegistry.registerModelLayer(ARMOR_LAYER_T3_POWER_BOOTS, () -> TexturedModelData.of(ModelT3PowerArmor.getModelData(Dilation.NONE, EquipmentSlot.FEET), 128, 64));
    }




    /**
     * Registers an entity type, retrieves modellayer and texture from entity name
     * @param entityType
     * @param modelData
     * @param <T>
     */
    protected static <T extends GenericNPC> void registerEntityRenderer(EntityType<T> entityType, Function<ModelPart, GenericNPCModel<T>> modelConstructor, TexturedModelData modelData, TexturedModelData modelDataInnerArmor, TexturedModelData modelDataOuterArmor){
        String entityName = entityType.toString().substring("entity.techguns.".length());

        EntityModelLayer modelLayer = new EntityModelLayer(new TGIdentifier(entityName), "main");
        EntityModelLayer modelLayerLegsArmor = new EntityModelLayer(new TGIdentifier(entityName), "inner_armor");
        EntityModelLayer modelLayerBodyArmor = new EntityModelLayer(new TGIdentifier(entityName), "outer_armor");
        EntityRendererRegistry.register(entityType, (context) ->
                new RenderGenericNPC<T>(context, new TGIdentifier("textures/entity/"+entityName+".png"), modelLayer, modelLayerLegsArmor, modelLayerBodyArmor, modelConstructor));
        EntityModelLayerRegistry.registerModelLayer(modelLayer, () -> modelData);
        EntityModelLayerRegistry.registerModelLayer(modelLayerLegsArmor, () -> modelDataInnerArmor);
        EntityModelLayerRegistry.registerModelLayer(modelLayerBodyArmor, () -> modelDataOuterArmor);
    }
}
