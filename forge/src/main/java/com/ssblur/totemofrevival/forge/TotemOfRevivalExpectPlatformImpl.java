package com.ssblur.totemofrevival.forge;

import com.ssblur.totemofrevival.TotemOfRevivalExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class TotemOfRevivalExpectPlatformImpl {
  /**
   * This is our actual method to {@link TotemOfRevivalExpectPlatform#getConfigDirectory()}.
   */
  public static Path getConfigDirectory() {
    return FMLPaths.CONFIGDIR.get();
  }
}
