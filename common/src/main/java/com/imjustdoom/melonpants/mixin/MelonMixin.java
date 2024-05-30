package com.imjustdoom.melonpants.mixin;

import com.imjustdoom.melonpants.block.MelonPantsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.MelonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MelonBlock.class)
public class MelonMixin extends Block {

    public MelonMixin(Properties properties) {
        super(properties);
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(Items.SHEARS)) {
            if (!level.isClientSide) {
                Direction blockHitDirection = blockHitResult.getDirection();
                Direction blockDirection = blockHitDirection.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : blockHitDirection;
                level.playSound(null, blockPos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
                BlockState block = Math.random() >= 0.75 ? MelonPantsBlocks.HAPPY_CARVED_MELON.get().defaultBlockState() : MelonPantsBlocks.CARVED_MELON.get().defaultBlockState();
                level.setBlock(blockPos, block.setValue(CarvedPumpkinBlock.FACING, blockDirection), 11);
                ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5 + blockDirection.getStepX() * 0.65, blockPos.getY() + 0.1, blockPos.getZ() + 0.5 + blockDirection.getStepZ() * 0.65, new ItemStack(Items.MELON_SEEDS, 4));
                itemEntity.setDeltaMovement(0.05 * blockDirection.getStepX() + level.random.nextDouble() * 0.02, 0.05, 0.05 * blockDirection.getStepZ() + level.random.nextDouble() * 0.02);
                level.addFreshEntity(itemEntity);
                itemStack.hurtAndBreak(1, player, (playerx) -> {
                    playerx.broadcastBreakEvent(interactionHand);
                });
                level.gameEvent(player, GameEvent.SHEAR, blockPos);
                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        }
    }
}
