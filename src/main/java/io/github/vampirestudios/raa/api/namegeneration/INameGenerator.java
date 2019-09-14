package io.github.vampirestudios.raa.api.namegeneration;

import java.util.Map;

public interface INameGenerator {
    public String generate();

    public Map<String, String> getSpecialCharatersMap();
}
