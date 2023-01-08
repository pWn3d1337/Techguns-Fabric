package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.TickPriority;
import org.jetbrains.annotations.Nullable;
import techguns.TGPacketsS2C;
import techguns.TGSounds;
import techguns.damagesystem.TGDamageSource;
import techguns.damagesystem.TGExplosion;
import techguns.deatheffects.EntityDeathUtils;
import techguns.packets.PacketSpawnParticle;

public class BlockBioBlob extends Block {
    public static final IntProperty SIZE = IntProperty.of("size",0,2);
    public static final DirectionProperty FACING = DirectionProperty.of("facing", Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);

    private static VoxelShape getBBForRota(int W, int H, Direction facing) {
        final double FSIZE = 0.0625;
        switch(facing) {
            case WEST:
                return VoxelShapes.cuboid( 0,FSIZE*W, FSIZE*W, FSIZE*H, FSIZE*(16-W), FSIZE*(16-W));
            case NORTH:
                return VoxelShapes.cuboid(FSIZE*W, FSIZE*W, 0, FSIZE*(16-W), FSIZE*(16-W), FSIZE*H);
            case SOUTH:
                return VoxelShapes.cuboid(FSIZE*W, FSIZE*W, FSIZE*(16-H), FSIZE*(16-W), FSIZE*(16-W), 1);
            case UP:
                return VoxelShapes.cuboid(FSIZE*W, FSIZE*(16-H), FSIZE*W, FSIZE*(16-W), 1, FSIZE*(16-W));
            case EAST:
                return VoxelShapes.cuboid(FSIZE*(16-H),FSIZE*W, FSIZE*W, 1, FSIZE*(16-W), FSIZE*(16-W));
            case DOWN:
            default:
                return VoxelShapes.cuboid(FSIZE*W, 0, FSIZE*W, FSIZE*(16-W), FSIZE*H, FSIZE*(16-W));
        }
    }

    private static final int SIZE_2_W = 1;
    private static final int SIZE_2_H = 5;

    private static final int SIZE_1_W = 3;
    private static final int SIZE_1_H = 4;

    private static final int SIZE_0_W = 5;
    private static final int SIZE_0_H = 4;
    private static final VoxelShape[][] boundingShapes = {
            {getBBForRota(SIZE_0_W, SIZE_0_H, Direction.DOWN),getBBForRota(SIZE_1_W, SIZE_1_H, Direction.DOWN), getBBForRota(SIZE_2_W, SIZE_2_H, Direction.DOWN)},
            {getBBForRota(SIZE_0_W, SIZE_0_H, Direction.UP),getBBForRota(SIZE_1_W, SIZE_1_H, Direction.UP), getBBForRota(SIZE_2_W, SIZE_2_H, Direction.UP)},
            {getBBForRota(SIZE_0_W, SIZE_0_H, Direction.NORTH),getBBForRota(SIZE_1_W, SIZE_1_H, Direction.NORTH), getBBForRota(SIZE_2_W, SIZE_2_H, Direction.NORTH)},
            {getBBForRota(SIZE_0_W, SIZE_0_H, Direction.SOUTH),getBBForRota(SIZE_1_W, SIZE_1_H, Direction.SOUTH), getBBForRota(SIZE_2_W, SIZE_2_H, Direction.SOUTH)},
            {getBBForRota(SIZE_0_W, SIZE_0_H, Direction.WEST),getBBForRota(SIZE_1_W, SIZE_1_H, Direction.WEST), getBBForRota(SIZE_2_W, SIZE_2_H, Direction.WEST)},
            {getBBForRota(SIZE_0_W, SIZE_0_H, Direction.EAST),getBBForRota(SIZE_1_W, SIZE_1_H, Direction.EAST), getBBForRota(SIZE_2_W, SIZE_2_H, Direction.EAST)}
    };

    public BlockBioBlob(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(SIZE, 0).with(FACING, Direction.DOWN));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(SIZE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int facing = state.get(FACING).ordinal();
        return boundingShapes[facing > 1 ? 0 : facing][state.get(SIZE)];
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        //BlockState state = world.getBlockState(pos);
        int size = state.get(SIZE);
        if (size > 0) {
            BlockState newState = state.with(SIZE, size-1);
            world.setBlockState(pos, newState);
            scheduleTick(world, pos, random);
        } else {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }

    /**
     * Schedule an update tick for this bioblob
     * @param world
     * @param pos
     * @param rnd
     */
    public void scheduleTick(World world, BlockPos pos, Random rnd){
        world.scheduleBlockTick(pos, this, MathHelper.nextInt(rnd, 80, 120), TickPriority.LOW);
        //world.createAndScheduleBlockTick(pos, this, MathHelper.nextInt(rnd, 80, 120), TickPriority.LOW); //TODO check 1.19.3
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        scheduleTick(world, pos, world.random);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {

        int explosion_size = state.get(SIZE)+1;

        float radius = 1.0F*explosion_size;

        TGDamageSource dmgSrc = TGDamageSource.causePoisonDamage(null, entity, EntityDeathUtils.DeathType.BIO);
        dmgSrc.goreChance=1.0f;
        dmgSrc.armorPenetration=0.35f;

        TGExplosion explosion = new TGExplosion(world, entity, null, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 10*radius, 0.5*radius, radius, radius*1.5f,0.0f);
        explosion.setDmgSrc(dmgSrc);

        world.setBlockState(pos, Blocks.AIR.getDefaultState());

        explosion.doExplosion(false);
        world.playSound((PlayerEntity) null, pos, TGSounds.DEATH_BIO, SoundCategory.BLOCKS, 4.0F, 1.0F);

        if(!world.isClient){
            TGPacketsS2C.sentToAllTrackingPos(new PacketSpawnParticle("bioblobExplosion", pos.getX()+0.5,pos.getY()+0.5, pos.getZ()+0.5), world, pos);
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        WorldView worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        Direction[] directions = ctx.getPlacementDirections();

        for(int i = 0; i < directions.length; ++i) {
            Direction direction = directions[i];
            blockState = blockState.with(FACING, direction);
            if (blockState.canPlaceAt(worldView, blockPos)) {
                return blockState;
            }
        }

        return null;
    }
}
