/*
package io.github.vampirestudios.raa.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.github.vampirestudios.raa.commands.arguments.StructureGenerateArgumentType;
import net.minecraft.command.arguments.EntitySummonArgumentType;
import net.minecraft.command.arguments.NbtCompoundTagArgumentType;
import net.minecraft.command.arguments.Vec3ArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class GenerateStructureCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.summon.failed"));
    private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(new TranslatableText("commands.summon.invalidPosition"));

    // First make method to register
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("generateStructure").requires((serverCommandSource) -> {
            return serverCommandSource.hasPermissionLevel(2);
        })).then(((RequiredArgumentBuilder)CommandManager.argument("structure", StructureGenerateArgumentType.structureGenerate())
                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES).executes((commandContext) ->
                        execute(commandContext.getSource(), StructureGenerateArgumentType.getEntitySummon(commandContext, "structure"),
                                commandContext.getSource().getPosition(), new CompoundTag(), true))
        ).then(((RequiredArgumentBuilder)CommandManager.argument("pos", Vec3ArgumentType.vec3()).executes((commandContext) -> {
            return execute(commandContext.getSource(), EntitySummonArgumentType.getEntitySummon(commandContext, "entity"), Vec3ArgumentType.getVec3(commandContext, "pos"), new CompoundTag(), true);
        })).then(CommandManager.argument("nbt", NbtCompoundTagArgumentType.nbtCompound()).executes((commandContext) -> {
            return execute(commandContext.getSource(), EntitySummonArgumentType.getEntitySummon(commandContext, "entity"), Vec3ArgumentType.getVec3(commandContext, "pos"), NbtCompoundTagArgumentType.getCompoundTag(commandContext, "nbt"), false);
        })))));
    }

    private static int execute(ServerCommandSource source, Identifier entity, Vec3d pos, CompoundTag nbt, boolean initialize) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(pos);

        ServerWorld serverWorld = source.getWorld();
        Entity entity2 = EntityType.loadEntityWithPassengers(nbt, serverWorld, (entityx) -> {
            entityx.refreshPositionAndAngles(pos.x, pos.y, pos.z, entityx.yaw, entityx.pitch);
            return !serverWorld.tryLoadEntity(entityx) ? null : entityx;
        });
        if (entity2 == null) {
            throw FAILED_EXCEPTION.create();
        } else {
            if (initialize && entity2 instanceof MobEntity) {
                ((MobEntity)entity2).initialize(source.getWorld(), source.getWorld().getLocalDifficulty(entity2.getBlockPos()), SpawnType.COMMAND, null, null);
            }

            source.sendFeedback(new TranslatableText("commands.summon.success", entity2.getDisplayName()), true);
            return 1;
        }
    }

}
*/
