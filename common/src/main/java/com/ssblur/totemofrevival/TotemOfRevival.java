package com.ssblur.totemofrevival;

import com.google.common.base.Suppliers;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.api.EnvType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class TotemOfRevival {
  public static final String MOD_ID = "totemofrevival";
  public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

  
//  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
//  public static final RegistrySupplier<Item> TOTEM_OF_REVIVAL = ITEMS.register("totem_of_revival", () ->
//      new Item(new Item.Properties().arch$tab(CreativeModeTabs.COMBAT)));

  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
  public static final RegistrySupplier<Block> GRAVE = BLOCKS.register("grave", GraveBlock::new);

  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);
  public static final RegistrySupplier<BlockEntityType<GraveBlockEntity>> GRAVE_BLOCK_ENTITY = BLOCK_ENTITIES.register(
    "grave",
    () -> BlockEntityType.Builder.of(GraveBlockEntity::new, GRAVE.get()).build(null)
  );

  public static void init() {
//    ITEMS.register();
    BLOCKS.register();
    BLOCK_ENTITIES.register();

    if(!Platform.isForge() && Platform.getEnv() == EnvType.CLIENT)
      RenderTypeRegistry.register(RenderType.translucent(), GRAVE.get());

    EntityEvent.LIVING_DEATH.register((entity, source) -> {
      var level = entity.level();
      if(level == null || level.isClientSide || !level.getServer().isHardcore())
        return EventResult.pass();

      if(entity instanceof Player player) {
        var blockState = GRAVE.get().defaultBlockState();
        level.setBlockAndUpdate(entity.blockPosition(), blockState);

        var grave = new GraveBlockEntity(entity.blockPosition(), blockState);
        grave.setProfile(player.getGameProfile());
        level.setBlockEntity(grave);
      }

      return EventResult.pass();
    });
  }
}
