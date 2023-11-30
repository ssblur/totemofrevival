package com.ssblur.totemofrevival.forge;

import dev.architectury.platform.forge.EventBuses;
import com.ssblur.totemofrevival.TotemOfRevival;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TotemOfRevival.MOD_ID)
public class TotemOfRevivalForge {
  public TotemOfRevivalForge() {
    // Submit our event bus to let architectury register our content on the right time
    EventBuses.registerModEventBus(TotemOfRevival.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
    TotemOfRevival.init();
  }
}
