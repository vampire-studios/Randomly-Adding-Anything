package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.api.objects.OreTypes;

public class Material {
    private OreTypes oreType;

    public Material() {}

    public OreTypes getOreType() {
        return oreType;
    }

    public void setOreType(OreTypes oreType) {
        this.oreType = oreType;
    }
}
