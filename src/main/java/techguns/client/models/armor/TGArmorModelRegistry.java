package techguns.client.models.armor;

import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import techguns.TGArmors;
import techguns.client.TGClientEntityModels;
import techguns.items.armors.GenericArmor;

import java.util.HashMap;

public class TGArmorModelRegistry<T extends LivingEntity> {

    protected final HashMap<Item, BipedEntityModel<T>> models;

    public TGArmorModelRegistry(EntityRendererFactory.Context ctx){
        models = new HashMap<>();
        models.put(TGArmors.T3_POWER_HELMET, new ModelT3PowerArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T3_POWER_HEAD)));
        models.put(TGArmors.T3_POWER_CHESTPLATE, new ModelT3PowerArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T3_POWER_CHEST)));
        models.put(TGArmors.T3_POWER_LEGGINGS, new ModelT3PowerArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T3_POWER_LEGGINGS)));
        models.put(TGArmors.T3_POWER_BOOTS, new ModelT3PowerArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T3_POWER_BOOTS)));

        models.put(TGArmors.T1_STEAM_HELMET,     new ModelSteamArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T1_STEAM_HEAD)));
        models.put(TGArmors.T1_STEAM_CHESTPLATE, new ModelSteamArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T1_STEAM_CHEST)));
        models.put(TGArmors.T1_STEAM_LEGGINGS,   new ModelSteamArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T1_STEAM_LEGGINGS)));
        models.put(TGArmors.T1_STEAM_BOOTS,      new ModelSteamArmor(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T1_STEAM_BOOTS)));

        models.put(TGArmors.T4_POWER_HELMET,     new ModelT4PowerArmorMk2(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T4_POWER_HEAD)));
        models.put(TGArmors.T4_POWER_CHESTPLATE, new ModelT4PowerArmorMk2(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T4_POWER_CHEST)));
        models.put(TGArmors.T4_POWER_LEGGINGS,   new ModelT4PowerArmorMk2(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T4_POWER_LEGGINGS)));
        models.put(TGArmors.T4_POWER_BOOTS,      new ModelT4PowerArmorMk2(ctx.getPart(TGClientEntityModels.ARMOR_LAYER_T4_POWER_BOOTS)));
    }

    @Nullable
    public BipedEntityModel<T> getModel(ItemStack stack) {
        if (!stack.isEmpty()){
            Item item = stack.getItem();
            if (item instanceof GenericArmor){
               return models.getOrDefault(item, null);
            }
        }
        return null;
    }

    public static void addMissingParts(ModelPartData modelPartData){
        for (var part : new String[]{EntityModelPartNames.HEAD, EntityModelPartNames.HAT, EntityModelPartNames.LEFT_ARM, EntityModelPartNames.RIGHT_ARM, EntityModelPartNames.BODY, EntityModelPartNames.LEFT_LEG, EntityModelPartNames.RIGHT_LEG}) {
            if (modelPartData.getChild(part) == null){
                modelPartData.addChild(part, ModelPartBuilder.create(), ModelTransform.NONE);
            }
        }
    }
}
