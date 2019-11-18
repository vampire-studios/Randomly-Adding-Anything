package io.github.vampirestudios.raa.api.enums;

import io.github.vampirestudios.raa.generation.materials.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.awt.*;

public class DebugMessagesBuilder {

    private Material material;
    private PlayerEntity playerEntity;

    private DebugMessagesBuilder(Material material, PlayerEntity playerEntity) {
        this.material = material;
        this.playerEntity = playerEntity;
    }

    public static DebugMessagesBuilder create(Material material, PlayerEntity playerEntity) {
        return new DebugMessagesBuilder(material, playerEntity);
    }

    public DebugMessagesBuilder name() {
        Color color = new Color(material.getRGBColor());
        playerEntity.addChatMessage(new LiteralText("Name: " + material.getName()), false);
        playerEntity.addChatMessage(new LiteralText(String.format("Color: " + "\n" + "  R: %d, G: %d, B: %d, A: %d", color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())), false);
        return this;
    }

    public DebugMessagesBuilder oreType() {
        playerEntity.addChatMessage(new LiteralText("Ore Type: " + material.getOreInformation().getOreType().name()), false);
        return this;
    }

    public DebugMessagesBuilder generatesIn() {
        playerEntity.addChatMessage(new LiteralText("Generates In: " + material.getOreInformation().getGeneratesIn().getIdentifier().toString()), false);
        return this;
    }

    public DebugMessagesBuilder textures() {
        playerEntity.addChatMessage(new LiteralText(Formatting.BOLD + "Textures"), false);
        playerEntity.addChatMessage(new LiteralText("Overlay Texture: " + material.getTexturesInformation().getOverlayTexture()), false);
        playerEntity.addChatMessage(new LiteralText("Resource Item Texture: " + material.getTexturesInformation().getResourceItemTexture()), false);
        playerEntity.addChatMessage(new LiteralText("Storage Block Texture: " + material.getTexturesInformation().getStorageBlockTexture()), false);
        return this;
    }

    public DebugMessagesBuilder armor() {
        playerEntity.addChatMessage(new LiteralText("Has Armor: " + material.hasArmor()), false);
        return this;
    }

    public DebugMessagesBuilder weapons() {
        playerEntity.addChatMessage(new LiteralText("Has Weapons: " + material.hasWeapons()), false);
        return this;
    }

    public DebugMessagesBuilder tools() {
        playerEntity.addChatMessage(new LiteralText("Has Tools: " + material.hasTools()), false);
        return this;
    }

    public DebugMessagesBuilder glowing() {
        playerEntity.addChatMessage(new LiteralText("Is Glowing: " + material.isGlowing()), false);
        return this;
    }

    public DebugMessagesBuilder oreFlower() {
        playerEntity.addChatMessage(new LiteralText("Has Ore Flower: " + material.hasOreFlower()), false);
        return this;
    }

}
