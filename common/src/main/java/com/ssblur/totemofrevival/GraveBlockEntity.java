package com.ssblur.totemofrevival;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.commands.GameModeCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class GraveBlockEntity extends BlockEntity {
  public GameProfile profile;

  public GraveBlockEntity(BlockPos blockPos, BlockState blockState) {
    super(TotemOfRevival.GRAVE_BLOCK_ENTITY.get(), blockPos, blockState);
  }

  public void setProfile(GameProfile profile) {
    this.profile = profile;
  }

  public void revive(Player activator) {
    if(level == null || level.isClientSide) return;
    if(profile != null) {
      ServerPlayer player;
      if(profile.getId() != null)
        player = level.getServer().getPlayerList().getPlayer(profile.getId());
      else
        player = level.getServer().getPlayerList().getPlayerByName(profile.getName());

      if(player == null) {
        activator.sendSystemMessage(Component.translatable("message.totemofrevival.online"));
        return;
      }

      if(!player.isSpectator()) return;
      player.setPos(worldPosition.getCenter());
      player.setGameMode(level.getServer().getDefaultGameType());
      level.broadcastEntityEvent(player, (byte) 35);
    }
    level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
    level.removeBlockEntity(worldPosition);
  }

  @Nullable
  @Override
  public Packet<ClientGamePacketListener> getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public CompoundTag getUpdateTag() {
    var tag = super.getUpdateTag();
    saveAdditional(tag);
    return tag;
  }

  @Override
  public void load(CompoundTag tag) {
    super.load(tag);
    if(tag.contains("id"))
      this.profile = new GameProfile(UUID.fromString(tag.getString("id")), null);
    else
      this.profile = new GameProfile(null, tag.getString("name"));
    setChanged();
  }

  @Override
  protected void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);

    if (profile.getId() != null)
      tag.putString("id", profile.getId().toString());
    else
      tag.putString("name", profile.getName());
  }
}
