package fr.arthurbambou.randomlyaddinganything.api.namegeneration;

import java.util.Map;

public interface INameGenerator {
    public String generate();

    public Map<String, String> getSpecialCharatersMap();
}
