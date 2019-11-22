package io.github.vampirestudios.raa.generation.feature;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;

import static net.minecraft.server.command.CommandManager.literal;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class CommandLocateRAAStructure {

    // First make method to register 
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode<ServerCommandSource> basenode = dispatcher.register(literal("locateRAA")
                .then(CommandManager.argument("RAAstructure", greedyString()).suggests(suggestedStrings()) .executes(ctx -> locateStructure(ctx.getSource(), getString(ctx, "RAAstructure"))))
        );
    }

    private static int locateStructure(ServerCommandSource source, String RAAstructure) {
        int found = -1;
        float distance = -1f;
        List<Integer> spawn_pos = Arrays.asList(0, 0, 0);
        try {
            BufferedReader reader = new BufferedReader(new FileReader("saves/" + source.getWorld().getLevelProperties().getLevelName() + "/DIM_raa_" + source.getWorld().getDimension().getType().getSuffix().substring(4) + "/data/tower_spawns.txt"));
            if (RAAstructure.equals("Tower")) {
            } else if (RAAstructure.equals("Outpost")) {
                reader = new BufferedReader(new FileReader("saves/" + source.getWorld().getLevelProperties().getLevelName() + "/DIM_raa_" + source.getWorld().getDimension().getType().getSuffix().substring(4) + "/data/outpost_spawns.txt"));
            } else if (RAAstructure.equals("Campfire")) {
                reader = new BufferedReader(new FileReader("saves/" + source.getWorld().getLevelProperties().getLevelName() + "/DIM_raa_" + source.getWorld().getDimension().getType().getSuffix().substring(4) + "/data/campfire_spawns.txt"));
            } else {
                found = 0;
                throw new SimpleCommandExceptionType(new TranslatableText("structure.notfound", RAAstructure)).create();
            }
            String spawn = reader.readLine();
            while (spawn != null) {
                float spawn_distance = (float) Math.sqrt(Math.pow(source.getPosition().x - Integer.parseInt(spawn.split(",")[0]), 2) + Math.pow(source.getPosition().z - Integer.parseInt(spawn.split(",")[2]), 2));
                if (distance == -1f || spawn_distance < distance) {
                    distance = spawn_distance;
                    spawn_pos.set(0, Integer.parseInt(spawn.split(",")[0]));
                    spawn_pos.set(1, Integer.parseInt(spawn.split(",")[1]));
                    spawn_pos.set(2, Integer.parseInt(spawn.split(",")[2]));
                    found = 1;
                }
                spawn = reader.readLine();
            }
            if (found == -1) {
                throw new SimpleCommandExceptionType(new TranslatableText("structure.notfound", RAAstructure)).create();
            }
        } catch (FileNotFoundException e) {
            source.getMinecraftServer().getPlayerManager().broadcastChatMessage(new LiteralText("ERROR: " + RAAstructure.toLowerCase() + "_spawns.txt could not be loaded").formatted(Formatting.RED), false);
            e.printStackTrace();
            return -1;
        } catch (CommandSyntaxException | IOException e) {
            e.printStackTrace();
        }

        if (found == 1) {
            Text teleportButtonPopup = Texts.bracketed(new TranslatableText("chat.coordinates", spawn_pos.get(0), spawn_pos.get(1), spawn_pos.get(2))).styled((style_1x) -> {
                style_1x.setColor(Formatting.GREEN).setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + spawn_pos.get(0) + " " + spawn_pos.get(1) + " " + spawn_pos.get(2))).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.coordinates.tooltip", new Object[0])));
            });
            source.sendFeedback(new TranslatableText("commands.locate.success", new TranslatableText(RAAstructure), teleportButtonPopup, Math.round(distance)), false);
            return Command.SINGLE_SUCCESS;
        } else if (found == -1) {
            source.getMinecraftServer().getPlayerManager().broadcastChatMessage(new LiteralText("Could not find that structure in this biome").formatted(Formatting.RED), false);
            return -1;
        } else {
            source.getMinecraftServer().getPlayerManager().broadcastChatMessage(new LiteralText("The structure \'" + RAAstructure + "\' is not a valid RAA structure").formatted(Formatting.RED), false);
            return -1;
        }
    }

    public static SuggestionProvider<ServerCommandSource> suggestedStrings() {
        return (ctx, builder) -> getSuggestionsBuilder(builder, Arrays.asList("Tower", "Outpost", "Campfire"));
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
}
