package io.github.vampirestudios.raa.utils;

import net.minecraft.util.Identifier;

public class AxeTexture {

    private final Identifier head;
    private final Identifier stick;

    public AxeTexture(Identifier head, Identifier stick) {
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
