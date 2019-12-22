package io.github.vampirestudios.raa.mixins;

import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Supplier;

@Mixin(Registry.class)
public interface AccessorRegistry<T> {
    @Invoker("create")
    static <T> Registry<T> create(String string, String string2, Supplier<T> defaultEntry) {
        throw new RuntimeException("f");
    }
}