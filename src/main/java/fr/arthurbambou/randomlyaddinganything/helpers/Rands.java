package fr.arthurbambou.randomlyaddinganything.helpers;

import java.util.List;
import java.util.Random;

public class Rands {
    public static int rand(int bound) {
        return new Random().nextInt(bound);
    }

    public static <O extends Object> O values(O[] values) {
        return values[rand(values.length)];
    }

    public static <O extends Object> O list(List<O> list) {
        return list.get(rand(list.size()));
    }
}
