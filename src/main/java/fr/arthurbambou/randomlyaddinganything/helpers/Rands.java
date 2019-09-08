package fr.arthurbambou.randomlyaddinganything.helpers;

import java.util.List;
import java.util.Random;

public class Rands {
    public static int randInt(int bound) {
        return new Random().nextInt(bound);
    }
    public static float randFloat(float bound) {
        return ((float) new Random().nextInt((int) (bound*10) + 1))/10;
    }

    public static <O extends Object> O values(O[] values) {
        return values[randInt(values.length)];
    }

    public static <O extends Object> O list(List<O> list) {
        return list.get(randInt(list.size()));
    }
}
