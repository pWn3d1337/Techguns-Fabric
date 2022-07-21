package techguns.client.models.armor;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
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

}
