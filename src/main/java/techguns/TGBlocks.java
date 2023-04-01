package techguns;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.registry.Registry;
import net.minecraft.world.BlockView;
import techguns.blocks.AmmoBenchBlock;
import techguns.blocks.BlockBioBlob;
import techguns.blocks.CamoBenchBlock;
import techguns.blocks.entity.AmmoBenchBlockEntity;
import techguns.blocks.entity.CamoBenchBlockEntity;
import techguns.inventory.AmmoBenchScreenHandler;
import techguns.inventory.CamoBenchScreenHandler;

import java.util.function.Supplier;

public class TGBlocks implements ITGInitializer {

    private static boolean always(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    public static Block TIN_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F));
    public static Block DEEPSLATE_TIN_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().mapColor(MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE).strength(4.5F, 3.0F));

    public static Block LEAD_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F, 4.0F));
    public static Block DEEPSLATE_LEAD_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().mapColor(MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE).strength(6.0F, 4.0F));

    public static Block URANIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(5.0F, 4.0F));
    public static Block DEEPSLATE_URANIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().mapColor(MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE).strength(7.5F, 4.0F));

    public static Block TITANIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(5.0F, 4.0F));
    public static Block DEEPSLATE_TITANIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().mapColor(MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE).strength(7.5F, 4.0F));

    public static Block BIOBLOB = new BlockBioBlob(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).dropsNothing().emissiveLighting(TGBlocks::always).luminance((BlockState state) -> (state.get(BlockBioBlob.SIZE)+1)*2));


    //Machines
    public static Block CAMO_BENCH = new CamoBenchBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 5.0F).sounds(BlockSoundGroup.METAL));
    public static Block AMMO_BENCH = new AmmoBenchBlock(FabricBlockSettings.of(Material.WOOD).strength(5.0F, 5.0F).sounds(BlockSoundGroup.WOOD));

    //Building Blocks
    private static final FabricBlockSettings CONCRETE_SETTINGS = FabricBlockSettings.of(Material.STONE).requiresTool().strength(8.0F);
    public static Block CONCRETE_BROWN = new Block(CONCRETE_SETTINGS);
    public static Block CONCRETE_BROWN_LIGHT = new Block(CONCRETE_SETTINGS);
    public static Block CONCRETE_BROWN_LIGHT_SCAFF = new Block(CONCRETE_SETTINGS);
    public static Block CONCRETE_BROWN_PIPES = new Block(CONCRETE_SETTINGS);
    public static Block CONCRETE_GREY = new Block(CONCRETE_SETTINGS);
    public static Block CONCRETE_GREY_DARK = new Block(CONCRETE_SETTINGS);

    /*
        ScreenHandler
     */
    public static ScreenHandlerType<CamoBenchScreenHandler> CAMO_BENCH_SCREEN_HANDLER;
    public static ScreenHandlerType<AmmoBenchScreenHandler> AMMO_BENCH_SCREEN_HANDLER;

    @Override
    public void init() {
        TGItems.TIN_ORE = registerBlockAndItem("tin_ore", TIN_ORE);
        TGItems.DEEPSLATE_TIN_ORE = registerBlockAndItem("deepslate_tin_ore", DEEPSLATE_TIN_ORE);

        TGItems.LEAD_ORE = registerBlockAndItem("lead_ore", LEAD_ORE);
        TGItems.DEEPSLATE_LEAD_ORE = registerBlockAndItem("deepslate_lead_ore", DEEPSLATE_LEAD_ORE);

        TGItems.URANIUM_ORE = registerBlockAndItem("uranium_ore", URANIUM_ORE);
        TGItems.DEEPSLATE_URANIUM_ORE = registerBlockAndItem("deepslate_uranium_ore",DEEPSLATE_URANIUM_ORE);

        TGItems.TITANIUM_ORE = registerBlockAndItem("titanium_ore", TITANIUM_ORE);
        TGItems.DEEPSLATE_TITANIUM_ORE = registerBlockAndItem("deepslate_titanium_ore",DEEPSLATE_TITANIUM_ORE);

        TGItems.BIOBLOB = registerBlockAndItem("bioblob", BIOBLOB);

        TGItems.CAMO_BENCH = registerBlockAndItem("camo_bench", CAMO_BENCH);
        TGItems.AMMO_BENCH = registerBlockAndItem("ammo_bench", AMMO_BENCH);

        //Building Blocks
        TGItems.CONCRETE_BROWN = registerBlockAndItem("concrete_brown", CONCRETE_BROWN);
        TGItems.CONCRETE_BROWN_LIGHT = registerBlockAndItem("concrete_brown_light", CONCRETE_BROWN_LIGHT);
        TGItems.CONCRETE_BROWN_LIGHT_SCAFF = registerBlockAndItem("concrete_brown_light_scaff", CONCRETE_BROWN_LIGHT_SCAFF);
        TGItems.CONCRETE_BROWN_PIPES = registerBlockAndItem("concrete_brown_pipes", CONCRETE_BROWN_PIPES);
        TGItems.CONCRETE_GREY = registerBlockAndItem("concrete_grey", CONCRETE_GREY);
        TGItems.CONCRETE_GREY_DARK = registerBlockAndItem("concrete_grey_dark", CONCRETE_GREY_DARK);

        registerBlockEntities();
        registerScreenHandlers();
    }

    public static Item registerBlockAndItem(String id, Block b){
        Identifier identifier = new TGIdentifier(id);
        Registry.register(Registries.BLOCK, identifier, b);
        return TGItems.registerItemTG(identifier, new BlockItem(b, new Item.Settings()));
    }

    public static void registerBlock(String id, Block b){
        Registry.register(Registries.BLOCK, new TGIdentifier(id), b);
    }

    //not yet needed for 1.17
    /*protected static <T extends BlockEntity> BlockEntityType<T> regBlockEnt(String id, Block b, Supplier<T> ctr){
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new TGIdentifier(id), BlockEntityType.Builder.create(ctr, b).build(null));
    }*/

    public void registerBlockEntities(){

    }

    public void registerScreenHandlers(){
        CAMO_BENCH_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new TGIdentifier("camo_bench"), CamoBenchScreenHandler::new);
        AMMO_BENCH_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new TGIdentifier("ammo_bench"), AmmoBenchScreenHandler::new);
    }
}
