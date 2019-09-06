package fr.arthurbambou.randomlyaddinganything.registries;

import fr.arthurbambou.randomlyaddinganything.api.objects.OreTypes;
import fr.arthurbambou.randomlyaddinganything.config.Config;
import fr.arthurbambou.randomlyaddinganything.materials.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Materials {
    public static final List<Material> MATERIAL_LIST = new ArrayList<>();

    public static void init() {
        for (int a = 0; a < new Config().materialNumber; a++) {
            Material material = new Material();
            material.setOreType(enumf(OreTypes.values()));
            MATERIAL_LIST.add(material);
            System.out.println("oreType : " + material.getOreType().name().toLowerCase());
        }
    }

    private static int rand(int bound) {
        return new Random().nextInt(bound);
    }

    private static <O extends Object> O enumf(O[] values) {
        return values[rand(values.length)];
    }
}
