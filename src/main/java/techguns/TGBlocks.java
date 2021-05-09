package techguns;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import techguns.blocks.BlockBioBlob;
import techguns.blocks.CamoBenchBlock;
import techguns.blocks.TGSimpleMachineBlock;
import techguns.blocks.entity.CamoBenchBlockEntity;
import techguns.inventory.CamoBenchScreenHandler;
import techguns.inventory.CamoBenchScreenHandlerOld;

import java.util.function.Supplier;

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


    //Machines
    public static Block CAMO_BENCH = new CamoBenchBlock(FabricBlockSettings.of(Material.METAL).breakByTool(FabricToolTags.PICKAXES, 0).strength(5.0F, 5.0F).sounds(BlockSoundGroup.METAL));

    /*
        Block Entities
    */
    public static BlockEntityType<CamoBenchBlockEntity> CAMO_BENCH_BLOCK_ENTITY;

    /*
        ScreenHandler
     */
    public static ScreenHandlerType<CamoBenchScreenHandler> CAMO_BENCH_SCREEN_HANDLER;

    @Override
    public void init() {
        TGItems.COPPER_ORE = registerBlockAndItem("copper_ore", COPPER_ORE);
        TGItems.TIN_ORE = registerBlockAndItem("tin_ore", TIN_ORE);
        TGItems.LEAD_ORE = registerBlockAndItem("lead_ore", LEAD_ORE);
        TGItems.URANIUM_ORE = registerBlockAndItem("uranium_ore", URANIUM_ORE);
        TGItems.TITANIUM_ORE = registerBlockAndItem("titanium_ore", TITANIUM_ORE);

        TGItems.BIOBLOB = registerBlockAndItem("bioblob", BIOBLOB);

        TGItems.CAMO_BENCH = registerBlockAndItem("camo_bench", CAMO_BENCH);

        registerBlockEntities();
        registerScreenHandlers();
    }

    public static Item registerBlockAndItem(String id, Block b){
        Identifier identifier = new TGIdentifier(id);
        Registry.register(Registry.BLOCK, identifier, b);
        return Registry.register(Registry.ITEM, identifier, new BlockItem(b, new Item.Settings().group(TGItems.ITEM_GROUP_TECHGUNS)));
    }

    public static void registerBlock(String id, Block b){
        Registry.register(Registry.BLOCK, new TGIdentifier(id), b);
    }

    protected static <T extends BlockEntity> BlockEntityType<T> regBlockEnt(String id, Block b, Supplier<T> ctr){
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new TGIdentifier(id), BlockEntityType.Builder.create(ctr, b).build(null));
    }

    public void registerBlockEntities(){
        //CAMO_BENCH_BLOCK_ENTITY = regBlockEnt("camo_bench_block_entity", CAMO_BENCH, CamoBenchBlockEntity::new);
    }

    public void registerScreenHandlers(){
        CAMO_BENCH_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new TGIdentifier("camo_bench"), CamoBenchScreenHandler::new);
    }
}
