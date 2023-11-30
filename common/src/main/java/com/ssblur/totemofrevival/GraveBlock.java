package com.ssblur.totemofrevival;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class GraveBlock extends Block implements EntityBlock {
  public GraveBlock() {
    super(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.0f).noOcclusion());
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new GraveBlockEntity(blockPos, blockState);
  }

  @Override
  public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
    if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() != Items.TOTEM_OF_UNDYING)
      return InteractionResult.PASS;

    if(level.getBlockEntity(blockPos) instanceof GraveBlockEntity entity) {
      player.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);

      entity.revive(player);
    }
    return InteractionResult.SUCCESS;
  }
}
