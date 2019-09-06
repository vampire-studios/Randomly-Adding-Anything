package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.api.objects.OreTypes;

public class Material {
    private OreTypes oreType;
    private String name;

    public Material() {}

    public OreTypes getOreType() {
        return oreType;
    }

    public void setOreType(OreTypes oreType) {
        this.oreType = oreType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
