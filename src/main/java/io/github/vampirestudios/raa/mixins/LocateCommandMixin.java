package io.github.vampirestudios.raa.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.server.command.CommandManager.literal;

@Mixin(LocateCommand.class)
public abstract class LocateCommandMixin {

    @Shadow
    private static int execute(ServerCommandSource source, String name) {
        throw new AssertionError();
    }

    @Inject(method = "register", at = @At(value = "RETURN"))
    private static void onRegister(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo info) {
        dispatcher.register(literal("locate").requires(source -> source.hasPermissionLevel(2))
                .then(literal("Volcano").executes(ctx -> execute(ctx.getSource(), "Volcano"))));
        dispatcher.register(literal("locate").requires(source -> source.hasPermissionLevel(2))
                .then(literal("Canyon_Arch").executes(ctx -> execute(ctx.getSource(), "canyon_arch"))));
    }

}
