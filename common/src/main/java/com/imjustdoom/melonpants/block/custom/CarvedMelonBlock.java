package com.imjustdoom.melonpants.block.custom;

import com.imjustdoom.melonpants.block.MelonPantsBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import java.util.function.Predicate;

public class CarvedMelonBlock extends HorizontalDirectionalBlock {

    public static final DirectionProperty FACING;
    private static final Predicate<BlockState> PUMPKINS_PREDICATE;

    public CarvedMelonBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        PUMPKINS_PREDICATE = (blockState) -> blockState != null && (blockState.is(MelonPantsBlocks.CARVED_MELON.get()) || blockState.is(MelonPantsBlocks.HAPPY_CARVED_MELON.get()));
    }
}