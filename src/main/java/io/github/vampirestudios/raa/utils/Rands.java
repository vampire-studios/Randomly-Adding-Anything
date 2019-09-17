package io.github.vampirestudios.raa.utils;

import java.util.List;
import java.util.Random;

public class Rands {
    public static int randInt(int bound) {
        return new Random().nextInt(bound);
    }

    public static int randIntRange(int min, int max) {
        int random = randInt(max + 1);
        if (random < min) {
            random = min;
        }
        return random;
    }
    public static float randFloat(float bound) {
        return ((float) new Random().nextInt((int) (bound*10) + 1))/10;
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
}
