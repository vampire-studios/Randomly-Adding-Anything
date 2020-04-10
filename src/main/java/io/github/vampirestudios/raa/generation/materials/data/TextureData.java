package io.github.vampirestudios.raa.generation.materials.data;

import net.minecraft.util.Identifier;

public class TextureData {

    private final Identifier head;
    private final Identifier stick;

    public TextureData(Identifier head, Identifier stick) {
        this.head = head;
        this.stick = stick;
    }

    public Identifier getHead() {
        return head;
    }

    public Identifier getStick() {
        return stick;
    }

}
