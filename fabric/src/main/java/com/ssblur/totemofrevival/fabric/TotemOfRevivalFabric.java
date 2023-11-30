package com.ssblur.totemofrevival.fabric;

import com.ssblur.totemofrevival.TotemOfRevival;
import net.fabricmc.api.ModInitializer;

public class TotemOfRevivalFabric implements ModInitializer {
  @Override
  public void onInitialize() {
    TotemOfRevival.init();
  }
}
