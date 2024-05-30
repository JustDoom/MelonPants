package com.melonpants.block.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.function.Predicate;

public class CarvedMelonBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING;
    @Nullable
    private BlockPattern snowGolemBase;
    @Nullable
    private BlockPattern snowGolemFull;
    @Nullable
    private BlockPattern ironGolemBase;
    @Nullable
    private BlockPattern ironGolemFull;
    private static final Predicate<BlockState> PUMPKINS_PREDICATE;

    public CarvedMelonBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
    }

    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.is(blockState.getBlock())) {
            this.trySpawnGolem(level, blockPos);
        }
    }

    public boolean canSpawnGolem(LevelReader levelReader, BlockPos blockPos) {
        return this.getOrCreateSnowGolemBase().find(levelReader, blockPos) != null || this.getOrCreateIronGolemBase().find(levelReader, blockPos) != null;
    }

    private void trySpawnGolem(Level level, BlockPos blockPos) {
        BlockPattern.BlockPatternMatch blockPatternMatch = this.getOrCreateSnowGolemFull().find(level, blockPos);
        if (blockPatternMatch != null) {
            SnowGolem snowGolem = (SnowGolem) EntityType.SNOW_GOLEM.create(level);
            if (snowGolem != null) {
                spawnGolemInWorld(level, blockPatternMatch, snowGolem, blockPatternMatch.getBlock(0, 2, 0).getPos());
            }
        } else {
            BlockPattern.BlockPatternMatch blockPatternMatch2 = this.getOrCreateIronGolemFull().find(level, blockPos);
            if (blockPatternMatch2 != null) {
                IronGolem ironGolem = (IronGolem)EntityType.IRON_GOLEM.create(level);
                if (ironGolem != null) {
                    ironGolem.setPlayerCreated(true);
                    spawnGolemInWorld(level, blockPatternMatch2, ironGolem, blockPatternMatch2.getBlock(1, 2, 0).getPos());
                }
            }
        }

    }

    private static void spawnGolemInWorld(Level level, BlockPattern.BlockPatternMatch blockPatternMatch, Entity entity, BlockPos blockPos) {
        clearPatternBlocks(level, blockPatternMatch);
        entity.moveTo((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.05, (double)blockPos.getZ() + 0.5, 0.0F, 0.0F);
        level.addFreshEntity(entity);
        Iterator var4 = level.getEntitiesOfClass(ServerPlayer.class, entity.getBoundingBox().inflate(5.0)).iterator();

        while(var4.hasNext()) {
            ServerPlayer serverPlayer = (ServerPlayer)var4.next();
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, entity);
        }

        updatePatternBlocks(level, blockPatternMatch);
    }

    public static void clearPatternBlocks(Level level, BlockPattern.BlockPatternMatch blockPatternMatch) {
        for(int i = 0; i < blockPatternMatch.getWidth(); ++i) {
            for(int j = 0; j < blockPatternMatch.getHeight(); ++j) {
                BlockInWorld blockInWorld = blockPatternMatch.getBlock(i, j, 0);
                level.setBlock(blockInWorld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                level.levelEvent(2001, blockInWorld.getPos(), Block.getId(blockInWorld.getState()));
            }
        }

    }

    public static void updatePatternBlocks(Level level, BlockPattern.BlockPatternMatch blockPatternMatch) {
        for(int i = 0; i < blockPatternMatch.getWidth(); ++i) {
            for(int j = 0; j < blockPatternMatch.getHeight(); ++j) {
                BlockInWorld blockInWorld = blockPatternMatch.getBlock(i, j, 0);
                level.blockUpdated(blockInWorld.getPos(), Blocks.AIR);
            }
        }

    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return (BlockState)this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    private BlockPattern getOrCreateSnowGolemBase() {
        if (this.snowGolemBase == null) {
            this.snowGolemBase = BlockPatternBuilder.start().aisle(new String[]{" ", "#", "#"}).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
        }

        return this.snowGolemBase;
    }

    private BlockPattern getOrCreateSnowGolemFull() {
        if (this.snowGolemFull == null) {
            this.snowGolemFull = BlockPatternBuilder.start().aisle(new String[]{"^", "#", "#"}).where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
        }

        return this.snowGolemFull;
    }

    private BlockPattern getOrCreateIronGolemBase() {
        if (this.ironGolemBase == null) {
            this.ironGolemBase = BlockPatternBuilder.start().aisle(new String[]{"~ ~", "###", "~#~"}).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', (blockInWorld) -> {
                return blockInWorld.getState().isAir();
            }).build();
        }

        return this.ironGolemBase;
    }

    private BlockPattern getOrCreateIronGolemFull() {
        if (this.ironGolemFull == null) {
            this.ironGolemFull = BlockPatternBuilder.start().aisle(new String[]{"~^~", "###", "~#~"}).where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', (blockInWorld) -> {
                return blockInWorld.getState().isAir();
            }).build();
        }

        return this.ironGolemFull;
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        PUMPKINS_PREDICATE = (blockState) -> {
            return blockState != null && (blockState.is(Blocks.CARVED_PUMPKIN) || blockState.is(Blocks.JACK_O_LANTERN));
        };
    }
}