package io.github.vampirestudios.raa.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;

public class StructureGenerateArgumentType implements ArgumentType<Identifier> {
   private static final Collection<String> EXAMPLES = Arrays.asList("minecraft:igloo", "mansion");
   public static final DynamicCommandExceptionType NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType((object) ->
           new TranslatableText("structure.notFound", object));

   public static StructureGenerateArgumentType structureGenerate() {
      return new StructureGenerateArgumentType();
   }

   public static Identifier getEntitySummon(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      return validate(context.getArgument(name, Identifier.class));
   }

   private static Identifier validate(Identifier identifier) throws CommandSyntaxException {
      Registry.STRUCTURE_FEATURE.getOrEmpty(identifier).orElseThrow(() -> NOT_FOUND_EXCEPTION.create(identifier));
      return identifier;
   }

   public Identifier parse(StringReader stringReader) throws CommandSyntaxException {
      return validate(Identifier.fromCommandInput(stringReader));
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
