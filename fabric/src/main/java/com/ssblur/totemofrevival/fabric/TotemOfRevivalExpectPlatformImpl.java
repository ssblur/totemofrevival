package com.ssblur.totemofrevival.fabric;

import com.ssblur.totemofrevival.TotemOfRevivalExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class TotemOfRevivalExpectPlatformImpl {
  /**
   * This is our actual method to {@link TotemOfRevivalExpectPlatform#getConfigDirectory()}.
   */
  public static Path getConfigDirectory() {
    return FabricLoader.getInstance().getConfigDir();
  }
}
