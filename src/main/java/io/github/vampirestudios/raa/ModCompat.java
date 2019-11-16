package io.github.vampirestudios.raa;

import net.fabricmc.loader.FabricLoader;

public class ModCompat {
    private boolean techreborn = false;

    public ModCompat() {
        if (FabricLoader.INSTANCE.isModLoaded("techreborn")) {
            this.techreborn = true;
        }
    }

    public boolean isTechrebornLoaded() {
        return this.techreborn;
    }
}
