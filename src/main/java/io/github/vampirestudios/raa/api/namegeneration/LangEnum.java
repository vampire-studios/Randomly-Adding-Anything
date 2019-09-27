package io.github.vampirestudios.raa.api.namegeneration;

import java.util.Map;

public enum LangEnum {
    ENGLISH(new English()),
    FRENCH(new French()),
    NORWEGIAN(new Norwegian());

    private INameGenerator nameGenerator;

    LangEnum(INameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public String generate() {
        return nameGenerator.generate();
    }

    public Map<String, String> getCharMap() {
        return nameGenerator.getSpecialCharatersMap();
    }
}
