package techguns;

import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import techguns.recipes.*;

public class TGRecipes implements ITGInitializer {

    //Recipe Types
    public static RecipeType<AmmoBenchRecipe> AMMOBENCH_RECIPE_TYPE;
    public static RecipeType<CamoChangeRecipe> CAMO_CHANGE_RECIPE_TYPE;

    @Override
    public void init() {
        AMMOBENCH_RECIPE_TYPE = RecipeType.register(Techguns.MODID+":ammobench_crafting");
        CAMO_CHANGE_RECIPE_TYPE = RecipeType.register(Techguns.MODID+":camo_change");

        NBTShapedRecipe.SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new TGIdentifier("crafting_shaped_nbt"), new NBTShapedRecipe.Serializer());

        TransferAmmoRecipe.SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new TGIdentifier("transfer_ammo"), new TransferAmmoRecipe.Serializer());

        MiningHeadUpgradeRecipe.SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new TGIdentifier("mininghead_upgrade"), new MiningHeadUpgradeRecipe.Serializer());

        AmmoChangeRecipe.SERIALIZER = (SpecialRecipeSerializer<AmmoChangeRecipe>)Registry.register(Registries.RECIPE_SERIALIZER, new TGIdentifier("ammo_change_recipe"), new SpecialRecipeSerializer<AmmoChangeRecipe>(AmmoChangeRecipe::new));

        AmmoBenchRecipe.SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new TGIdentifier("ammobench_crafting"), new AmmoBenchRecipe.Serializer());

        CamoChangeRecipe.SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new TGIdentifier("camo_change"), new CamoChangeRecipe.Serializer());


        if (Recipewriter.WRITE_RECIPES) {
            Recipewriter.generateItemRecipes();
        }
    }
}
