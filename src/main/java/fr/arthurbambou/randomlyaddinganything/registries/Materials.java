package fr.arthurbambou.randomlyaddinganything.registries;

import fr.arthurbambou.randomlyaddinganything.api.NameGenerator;
import fr.arthurbambou.randomlyaddinganything.api.objects.OreTypes;
import fr.arthurbambou.randomlyaddinganything.config.Config;
import fr.arthurbambou.randomlyaddinganything.helpers.Rands;
import fr.arthurbambou.randomlyaddinganything.materials.Material;

import java.util.ArrayList;
import java.util.List;

public class Materials {
    public static final List<Material> MATERIAL_LIST = new ArrayList<>();

    public static void init() {
        for (int a = 0; a < new Config().materialNumber; a++) {
            Material material = new Material();
            material.setOreType(Rands.values(OreTypes.values()));
            material.setName(NameGenerator.generate());
            MATERIAL_LIST.add(material);
            // Debug Only
            System.out.println("Material : " + material.getName() + " with oreType : " + material.getOreType().name().toLowerCase());
        }
    }


}
