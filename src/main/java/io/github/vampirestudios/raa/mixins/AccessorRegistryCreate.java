package io.github.vampirestudios.raa.mixins;

import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Supplier;

@Mixin(Registry.class)
public interface AccessorRegistryCreate {

    @Invoker("create(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/Supplier;)Lnet/minecraft/util/registry/DefaultedRegistry;")
    static <T> DefaultedRegistry<T> create(String string, String string2, Supplier<T> defaultEntry) {
        throw new RuntimeException("f");
    }

}