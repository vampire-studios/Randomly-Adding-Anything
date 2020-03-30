package io.github.vampirestudios.raa.generation.targets;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

public class OreTargetData {

    private Identifier name;
    private boolean topOnly;

    public OreTargetData(Identifier name, boolean topOnly) {
        this.name = name;
        this.topOnly = topOnly;
    }

    public Identifier getName() {
        return name;
    }

    public boolean hasTopOnly() {
        return topOnly;
    }

    public void serialize(JsonObject obj) {
        obj.addProperty("name", this.name.toString());
        obj.addProperty("topOnly", this.topOnly);
    }

    public void deserialize(JsonObject obj) {
        this.name = Identifier.tryParse(obj.get("name").getAsString());
        this.topOnly = obj.get("topOnly").getAsBoolean();
    }

    public static class Builder {

        private Identifier name;
        private boolean topOnly;

        public static Builder create() {
            return new Builder();
        }

        public Builder name(Identifier name) {
            this.name = name;
            return this;
        }

        public Builder topOnly(boolean topOnly) {
            this.topOnly = topOnly;
            return this;
        }

        public OreTargetData build() {
            return new OreTargetData(name, topOnly);
        }

    }

}
