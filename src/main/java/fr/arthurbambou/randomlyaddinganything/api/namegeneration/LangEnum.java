package fr.arthurbambou.randomlyaddinganything.api.namegeneration;

import java.util.Map;

public enum LangEnum {
    en(new English()),
    fr(new French());

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
