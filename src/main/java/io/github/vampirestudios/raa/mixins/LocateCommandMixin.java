package io.github.vampirestudios.raa.mixins;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.arguments.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Set;

@Mixin(LocateCommand.class)
public abstract class LocateCommandMixin {

    private static final SuggestionProvider<ServerCommandSource> SUGGESTIONS =
            (ctx, builder) -> {
                Set<Identifier> ids = Registry.STRUCTURE_FEATURE.getIds();
                return CommandSource.suggestIdentifiers(ids, builder);
            };

    private static final DynamicCommandExceptionType NOT_FOUND_EXCEPTION =
            new DynamicCommandExceptionType(obj -> new TranslatableText("commands.locate.notfound", obj));


    @Shadow
    private static native int execute(ServerCommandSource src, String arg) throws CommandSyntaxException;

    @ModifyArg(
            method = "register",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/brigadier/CommandDispatcher;register(Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;)Lcom/mojang/brigadier/tree/LiteralCommandNode;",
                    remap = false
            )
    )
    private static LiteralArgumentBuilder<ServerCommandSource> modifyLocate(LiteralArgumentBuilder<ServerCommandSource> cmd) {
        return cmd.then(CommandManager.argument("structure", IdentifierArgumentType.identifier())
                .suggests(SUGGESTIONS)
                .executes(ctx -> {
                    Identifier id = ctx.getArgument("structure", Identifier.class);
                    StructureFeature<?> feature = Registry.STRUCTURE_FEATURE.get(id);
                    if(feature != null) {
                        return execute(ctx.getSource(), feature.getName());
                    } else {
                        throw NOT_FOUND_EXCEPTION.create(id);
                    }
                }));
    }
}