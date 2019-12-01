package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.advancements.criterions.EnterRandomDimensionFirstTimeCriterion;
import net.minecraft.advancement.criterion.Criterion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Criterions {

    public static void init() {
            Method[] methods = net.minecraft.advancement.criterion.Criterions.class.getDeclaredMethods();
            for (Method method : methods)
                if (method.getName() == "register") {
                    System.out.println("Space!");
                    method.setAccessible(true);
                    try {
                        method.invoke(net.minecraft.advancement.criterion.Criterions.class, new EnterRandomDimensionFirstTimeCriterion());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
//            method.setAccessible(true);
//            method.invoke(new EnterRandomDimensionFirstTimeCriterion());
    }
}
