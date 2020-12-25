package techguns;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import techguns.blocks.BlockBioBlob;

public class TGBlocks implements ITGInitializer {

    private static boolean always(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    public static Block COPPER_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES, 1).strength(2.5F, 3.0F));
    public static Block TIN_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES, 1).strength(3.0F, 3.0F));
    public static Block LEAD_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES, 2).strength(4.0F, 4.0F));
    public static Block URANIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES, 3).strength(5.0F, 4.0F));
    public static Block TITANIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES, 3).strength(5.0F, 4.0F));

    public static Block BIOBLOB = new BlockBioBlob(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).dropsNothing().emissiveLighting(TGBlocks::always).luminance((BlockState state) -> (state.get(BlockBioBlob.SIZE)+1)*2));

    @Override
    public void init() {
        TGItems.COPPER_ORE = registerBlockAndItem("copper_ore", COPPER_ORE);
        TGItems.TIN_ORE = registerBlockAndItem("tin_ore", TIN_ORE);
        TGItems.LEAD_ORE = registerBlockAndItem("lead_ore", LEAD_ORE);
        TGItems.URANIUM_ORE = registerBlockAndItem("uranium_ore", URANIUM_ORE);
        TGItems.TITANIUM_ORE = registerBlockAndItem("titanium_ore", TITANIUM_ORE);

        TGItems.BIOBLOB = registerBlockAndItem("bioblob", BIOBLOB);
    }

    public static Item registerBlockAndItem(String id, Block b){
        Identifier identifier = new TGIdentifier(id);
        Registry.register(Registry.BLOCK, identifier, b);
        return Registry.register(Registry.ITEM, identifier, new BlockItem(b, new Item.Settings().group(TGItems.ITEM_GROUP_TECHGUNS)));
    }

    public static void registerBlock(String id, Block b){
        Registry.register(Registry.BLOCK, new TGIdentifier(id), b);
    }

}
