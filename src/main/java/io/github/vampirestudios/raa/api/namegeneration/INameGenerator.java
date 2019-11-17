package io.github.vampirestudios.raa.api.namegeneration;

import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

public interface INameGenerator {
    String generate();

    default Pair<String, Identifier> generateUnique(Collection<Identifier> presentIds, final String modId) {
        int loops = 0;
        Identifier identifier;
        String name;
        do {
            name = generate();
            identifier = new Identifier(modId, asId(name));
            if (++loops > 50) {
                throw new RuntimeException("Couldn't find a new name anymore.");
            }
        } while (presentIds.contains(identifier));
        return new Pair<>(name, identifier);
    }

    Map<String, String> getSpecialCharactersMap();

    default String asId(String name) {
        String id = name;
        Map<String, String> specialCharacters = getSpecialCharactersMap();
        if (specialCharacters != null) {
            for (Map.Entry<String, String> specialCharacter : specialCharacters.entrySet()) {
                id = id.replace(specialCharacter.getKey(), specialCharacter.getValue());
            }
        }
        id = id.toLowerCase(Locale.ENGLISH);
        return id;
    }
}
