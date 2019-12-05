package io.github.vampirestudios.raa.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandLocateRAAStructure {

    // First make method to register
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode<ServerCommandSource> basenode = dispatcher.register(literal("locateRAA")
                .then(CommandManager.argument("RAAstructure", greedyString()).suggests(suggestedStrings()) .executes(ctx -> locateStructure(ctx.getSource(),
                        getString(ctx, "RAAstructure"))))
        );
    }

    private static int locateStructure(ServerCommandSource source, String structureName) {
        int found = -1;
        float distance = -1f;
        List<Integer> spawnPos = Arrays.asList(0, 0, 0);
        try {
            if (!"Tower,Outpost,Campfire,SpiderLair,Tomb,Fossil,PortalHub,Shrine".contains(structureName)) {
                found = 0;
                throw new SimpleCommandExceptionType(new TranslatableText("structure.notfound", structureName)).create();
            }

            String worldPath;
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) worldPath = "saves/" + source.getWorld().getSaveHandler().getWorldDir().getName();
            else worldPath = source.getWorld().getLevelProperties().getLevelName();

            String spawnPath = worldPath + "/DIM_raa_" + source.getWorld().getDimension().getType().getSuffix().substring(4) + "/data/";

            if (structureName.equals("PortalHub") && source.getWorld().getDimension().getType().getSuffix().equals("")) {
                spawnPath = worldPath + "/data/portal_hub_spawns.txt";
            }
            else if (structureName.equals("Tower") && isRaaDimension(source)) {
                spawnPath += "tower_spawns.txt";
            } else if (structureName.equals("Outpost") && isRaaDimension(source)) {
                spawnPath += "outpost_spawns.txt";
            } else if (structureName.equals("Campfire") && isRaaDimension(source)) {
                spawnPath += "campfire_spawns.txt";
            } else if (structureName.equals("SpiderLair") && isRaaDimension(source)) {
                spawnPath += "spider_lair_spawns.txt";
            } else if (structureName.equals("Tomb") && isRaaDimension(source)) {
                spawnPath += "tomb_spawns.txt";
            } else if (structureName.equals("Fossil") && isRaaDimension(source)) {
                spawnPath += "fossil_spawns.txt";
            } else if (structureName.equals("Shrine") && isRaaDimension(source)) {
                spawnPath += "shrine_spawns.txt";
            } else {
                throw new SimpleCommandExceptionType(new TranslatableText("structure.notfound", structureName)).create();
            }

            BufferedReader reader = new BufferedReader(new FileReader(spawnPath));
            String spawn = reader.readLine();
            while (spawn != null) {
                float spawnDistance = (float) Math.sqrt(Math.pow(source.getPosition().x - Integer.parseInt(spawn.split(",")[0]), 2) + Math.pow(source.getPosition().y - Integer.parseInt(spawn.split(",")[1]), 2) + Math.pow(source.getPosition().z - Integer.parseInt(spawn.split(",")[2]), 2));
                if (distance == -1f || spawnDistance < distance) {
                    distance = spawnDistance;
                    String[] coords = spawn.split(",");
                    spawnPos.set(0, Integer.parseInt(coords[0]));
                    spawnPos.set(1, Integer.parseInt(coords[1]));
                    spawnPos.set(2, Integer.parseInt(coords[2]));
                    found = 1;
                }
                spawn = reader.readLine();
            }

            if (found == -1) {
                throw new SimpleCommandExceptionType(new TranslatableText("structure.notfound", structureName)).create();
            }
        } catch (CommandSyntaxException | IOException e) {
            e.printStackTrace();
        }

        if (found == 1) {
            Text teleportButtonPopup = Texts.bracketed(new TranslatableText("chat.coordinates", spawnPos.get(0), spawnPos.get(1), spawnPos.get(2))).styled((style_1x) -> style_1x.setColor(Formatting.GREEN).setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + spawnPos.get(0) + " " + spawnPos.get(1) + " " + spawnPos.get(2))).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.coordinates.tooltip"))));
            source.sendFeedback(new TranslatableText("commands.locate.success", new TranslatableText(structureName), teleportButtonPopup, Math.round(distance)), false);
            return Command.SINGLE_SUCCESS;
        } else if (found == -1) {
            source.getMinecraftServer().getPlayerManager().broadcastChatMessage(new LiteralText("Could not find that structure in this biome").formatted(Formatting.RED), false);
            return -1;
        } else {
            source.getMinecraftServer().getPlayerManager().broadcastChatMessage(new LiteralText("The structure '" + structureName + "' is not a valid RAA structure").formatted(Formatting.RED), false);
            return -1;
        }
    }

    private static SuggestionProvider<ServerCommandSource> suggestedStrings() {
        return (ctx, builder) -> getSuggestionsBuilder(builder, Arrays.asList("Tower", "Outpost", "Campfire", "SpiderLair", "Tomb", "Fossil", "PortalHub", "Shrine"));
    }

    private static CompletableFuture<Suggestions> getSuggestionsBuilder(SuggestionsBuilder builder, List<String> list) {
        String remaining = builder.getRemaining().toLowerCase(Locale.ROOT);

        if(list.isEmpty()) { // If the list is empty then return no suggestions
            return Suggestions.empty(); // No suggestions
        }

        for (String str : list) { // Iterate through the supplied list
            if (str.toLowerCase(Locale.ROOT).startsWith(remaining)) {
                builder.suggest(str); // Add every single entry to suggestions list.
            }
        }
        return builder.buildFuture(); // Create the CompletableFuture containing all the suggestions
    }

    private static boolean isRaaDimension(ServerCommandSource source) {
        String dim = source.getWorld().getDimension().getType().getSuffix();
        return !dim.equals("") && !dim.equals("_end") && !dim.equals("_nether");
    }
}