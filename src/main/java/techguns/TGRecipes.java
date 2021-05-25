package techguns;

import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;
import techguns.recipes.*;

public class TGRecipes implements ITGInitializer {

    //Recipe Types
    public static RecipeType<AmmoBenchRecipe> AMMOBENCH_RECIPE_TYPE;

    @Override
    public void init() {
        AMMOBENCH_RECIPE_TYPE = RecipeType.register(Techguns.MODID+":ammobench_crafting");


        NBTShapedRecipe.SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new TGIdentifier("crafting_shaped_nbt"), new NBTShapedRecipe.Serializer());

        TransferAmmoRecipe.SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new TGIdentifier("transfer_ammo"), new TransferAmmoRecipe.Serializer());

        MiningHeadUpgradeRecipe.SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new TGIdentifier("mininghead_upgrade"), new MiningHeadUpgradeRecipe.Serializer());

        AmmoChangeRecipe.SERIALIZER = (SpecialRecipeSerializer<AmmoChangeRecipe>)Registry.register(Registry.RECIPE_SERIALIZER, new TGIdentifier("ammo_change_recipe"), new SpecialRecipeSerializer<AmmoChangeRecipe>(AmmoChangeRecipe::new));

        AmmoBenchRecipe.SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new TGIdentifier("ammobench_crafting"), new AmmoBenchRecipe.Serializer());



        if (Recipewriter.WRITE_RECIPES) {
            Recipewriter.generateItemRecipes();
        }
    }
}
