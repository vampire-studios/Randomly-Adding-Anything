package io.github.vampirestudios.raa.generation.materials.data;

import net.minecraft.util.Identifier;

public class SwordTextureData {

    private Identifier handle;
    private Identifier stick;
    private Identifier blade;

    public SwordTextureData(Identifier blade, Identifier handle) {
        this.blade = blade;
        this.handle = handle;
        this.stick = new Identifier("raa", "item/tools/sword/stick");
    }

    public Identifier getHandle() {
        return handle;
    }

    public Identifier getStick() {
        return stick;
    }

    public Identifier getBlade() {
        return blade;
    }

}
