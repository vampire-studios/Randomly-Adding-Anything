package fr.arthurbambou.randomlyaddinganything.helpers;

import java.util.Random;

public class Rands {
    public static int rand(int bound) {
        return new Random().nextInt(bound);
    }

    public static <O extends Object> O values(O[] values) {
        return values[rand(values.length)];
    }
}
