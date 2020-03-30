package io.github.vampirestudios.raa.utils;

import io.github.vampirestudios.vampirelib.utils.Color;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Rands {

    private static long generateSeed(long min, long max) {
        long s1 = randomLong(min, max);
        long s2 = randomLong(min, max);

        while (s2 <= s1)
            s2 = randomLong(min, max);

        return randomLong(s1, s2);
    }

    private static long randomLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(max - min + 1) + min;
    }

    private static final Random rand = new Random(generateSeed(-1000000000L, 1000000000L));

    public static Random getRandom() {
        return rand;
    }

    public static int randInt(int bound) {
        return rand.nextInt(bound);
    }

    public static int randIntRange(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static float randFloatRange(float min, float max) {
        return min + rand.nextFloat() * (max - min);
    }

    public static float randFloat(float bound) {
        return ((float) rand.nextInt((int) (bound * 10) + 1)) / 10;
    }

    public static Color randColor() {
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public static boolean chance(int bound) {
        return randInt(bound) == 0;
    }

    public static <O extends Object> O values(O[] values) {
        return values[randInt(values.length)];
    }

    public static <O extends Object> O list(List<O> list) {
        return list.get(randInt(list.size()));
    }

    public static <O extends Object> List<O> lists(List<O> list, List<O> list2) {
        int int1 = randInt(list.size());
        int int2 = randInt(list2.size());
        return Arrays.asList(list.get(int1), list2.get(int2));
    }

    public static <K, V extends Object> Map.Entry<K, V> map(Map<K, V> map) {
        Set<Map.Entry<K, V>> entry = map.entrySet();
        return new ArrayList<>(entry).get(randInt(entry.size()));
    }

    public static <K, V extends Object> V mapValue(Map<K, V> map) {
        V[] values = (V[]) map.values().toArray();
        return values[rand.nextInt(values.length)];
    }
}
