package io.github.vampirestudios.raa.api.namegeneration.dimensions;

import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class NorwegianDimensions implements INameGenerator {

    @Override
    public String generate() {
        return null;
    }

    @Override
    public Map<String, String> getSpecialCharactersMap() {
        return null;
    }

    @Override
    public SortedMap<String, String> getSpecialCharactersMapSorted() {
        return new TreeMap<>();
    }
}