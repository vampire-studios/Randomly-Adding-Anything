package io.github.vampirestudios.raa.advancements.criterions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EnterRandomDimensionFirstTimeCriterion extends AbstractCriterion<EnterRandomDimensionFirstTimeCriterion.Conditions> {
    private static final Identifier ID = new Identifier(RandomlyAddingAnything.MOD_ID,"first_random_dimension");

    public EnterRandomDimensionFirstTimeCriterion() {
    }

    public Identifier getId() {
        return ID;
    }

    public EnterRandomDimensionFirstTimeCriterion.Conditions conditionsFromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        return new EnterRandomDimensionFirstTimeCriterion.Conditions();
    }

    public void trigger(ServerPlayerEntity player) {
        this.test(player.getAdvancementTracker(), (conditions) -> {
            return conditions.matches(player);
        });
    }

    public static class Conditions extends AbstractCriterionConditions {
        public Conditions() {
            super(EnterRandomDimensionFirstTimeCriterion.ID);
        }

        public static EnterRandomDimensionFirstTimeCriterion.Conditions to() {
            return new EnterRandomDimensionFirstTimeCriterion.Conditions();
        }

        public boolean matches(ServerPlayerEntity player) {
            if (Registry.DIMENSION.getId(player.getServerWorld().dimension.getType()).getNamespace().equals(RandomlyAddingAnything.MOD_ID)) return true;
            return false;
        }
    }
}
