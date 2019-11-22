package io.github.vampirestudios.raa.registries;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RegistryInformation {

    public Text name;
    public Identifier registryName;
    public int id;

    public RegistryInformation(Text name, Identifier registryName, int id) {
        this.name = name;
        this.registryName = registryName;
        this.id = id;
    }

    public Text getName() {
        return name;
    }

    public Identifier getRegistryName() {
        return registryName;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return String.format("%d,%s,%s", this.id, this.name, this.registryName);
    }

}