package techguns.client.models.armor;

import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import techguns.TGArmors;
import techguns.TGIdentifier;
import techguns.api.ICamoChangeable;
import techguns.client.TGClientEntityModels;
import techguns.items.armors.GenericArmor;

import java.util.HashMap;
import java.util.Map;

public class TGArmorModelRegistry<T extends LivingEntity> {

    protected final HashMap<Item, BipedEntityModel<T>> models;

    public TGArmorModelRegistry(EntityRendererFactory.Context ctx) {
        var inner_armor = new BipedEntityModel<T>(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR));
        var outer_armor = new BipedEntityModel<T>(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR));

        models = new HashMap<>();
        models.put(TGArmors.T3_POWER_HELMET, new ModelT3PowerArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T3_POWER_HEAD)));
        models.put(TGArmors.T3_POWER_CHESTPLATE, new ModelT3PowerArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T3_POWER_CHEST)));
        models.put(TGArmors.T3_POWER_LEGGINGS, new ModelT3PowerArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T3_POWER_LEGGINGS)));
        models.put(TGArmors.T3_POWER_BOOTS, new ModelT3PowerArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T3_POWER_BOOTS)));

        models.put(TGArmors.T1_STEAM_HELMET, new ModelSteamArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T1_STEAM_HEAD)));
        models.put(TGArmors.T1_STEAM_CHESTPLATE, new ModelSteamArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T1_STEAM_CHEST)));
        models.put(TGArmors.T1_STEAM_LEGGINGS, new ModelSteamArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T1_STEAM_LEGGINGS)));
        models.put(TGArmors.T1_STEAM_BOOTS, new ModelSteamArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T1_STEAM_BOOTS)));

        models.put(TGArmors.T4_POWER_HELMET, new ModelT4PowerArmorMk2(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T4_POWER_HEAD)));
        models.put(TGArmors.T4_POWER_CHESTPLATE, new ModelT4PowerArmorMk2(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T4_POWER_CHEST)));
        models.put(TGArmors.T4_POWER_LEGGINGS, new ModelT4PowerArmorMk2(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T4_POWER_LEGGINGS)));
        models.put(TGArmors.T4_POWER_BOOTS, new ModelT4PowerArmorMk2(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T4_POWER_BOOTS)));

        models.put(TGArmors.T2_RIOT_HELMET,     new ModelArmorCoat(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T2_RIOT_HEAD)));
        models.put(TGArmors.T2_RIOT_CHESTPLATE, new ModelArmorCoat(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T2_RIOT_CHEST)));
        models.put(TGArmors.T2_RIOT_LEGGINGS,   new ModelArmorCoat(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T2_RIOT_LEGGINGS)));
        models.put(TGArmors.T2_RIOT_BOOTS,      new ModelArmorCoat(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T2_RIOT_BOOTS)));

        models.put(TGArmors.T3_EXO_HELMET,     new ModelExoSuit(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T3_EXO_HEAD)));
        models.put(TGArmors.T3_EXO_CHESTPLATE, new ModelExoSuit(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T3_EXO_CHEST)));
        models.put(TGArmors.T3_EXO_LEGGINGS,   new ModelExoSuit_Layer2(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T3_EXO_LEGGINGS)));
        models.put(TGArmors.T3_EXO_BOOTS,      new ModelExoSuit(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T3_EXO_BOOTS)));

        models.put(TGArmors.T1_MINER_HELMET,     outer_armor);
        models.put(TGArmors.T1_MINER_CHESTPLATE, outer_armor);
        models.put(TGArmors.T1_MINER_LEGGINGS,   inner_armor);
        models.put(TGArmors.T1_MINER_BOOTS,      outer_armor);

        models.put(TGArmors.T1_SCOUT_HELMET,     outer_armor);
        models.put(TGArmors.T1_SCOUT_CHESTPLATE, outer_armor);
        models.put(TGArmors.T1_SCOUT_LEGGINGS,   inner_armor);
        models.put(TGArmors.T1_SCOUT_BOOTS,      outer_armor);

        models.put(TGArmors.T2_COMBAT_HELMET,     outer_armor);
        models.put(TGArmors.T2_COMBAT_CHESTPLATE, outer_armor);
        models.put(TGArmors.T2_COMBAT_LEGGINGS,   inner_armor);
        models.put(TGArmors.T2_COMBAT_BOOTS,      outer_armor);

        models.put(TGArmors.T3_COMBAT_HELMET,     outer_armor);
        models.put(TGArmors.T3_COMBAT_CHESTPLATE, outer_armor);
        models.put(TGArmors.T3_COMBAT_LEGGINGS,   inner_armor);
        models.put(TGArmors.T3_COMBAT_BOOTS,      outer_armor);

    }

    @Nullable
    public BipedEntityModel<T> getModel(ItemStack stack) {
        if (!stack.isEmpty()) {
            Item item = stack.getItem();
            if (item instanceof GenericArmor) {
                return models.getOrDefault(item, null);
            }
        }
        return null;
    }

    public static void addMissingParts(ModelPartData modelPartData) {
        for (var part : new String[]{EntityModelPartNames.HEAD, EntityModelPartNames.HAT, EntityModelPartNames.LEFT_ARM, EntityModelPartNames.RIGHT_ARM, EntityModelPartNames.BODY, EntityModelPartNames.LEFT_LEG, EntityModelPartNames.RIGHT_LEG}) {
            if (modelPartData.getChild(part) == null) {
                modelPartData.addChild(part, ModelPartBuilder.create(), ModelTransform.NONE);
            }
        }
    }

    protected static class ArmorTextureData<T extends LivingEntity> {
        protected static final Identifier DEFAULT_CAMO = new TGIdentifier("default");

        final BipedEntityModel<T> model;
        final Map<Identifier, Identifier> textures;

        protected ArmorTextureData(BipedEntityModel<T> model, Identifier texture){
            this.textures = new HashMap<>();
            this.textures.put(DEFAULT_CAMO, texture);
            this.model = model;
        }

        @Nullable
        protected Identifier getCamoFor(ItemStack stack){
            if(!stack.isEmpty() && stack.getItem() instanceof ICamoChangeable){
                ICamoChangeable camoItem = (ICamoChangeable) stack.getItem();
                //amoItem.getCurrentCamoName()

            }
            return textures.getOrDefault(DEFAULT_CAMO, null);
        }
    }

}
