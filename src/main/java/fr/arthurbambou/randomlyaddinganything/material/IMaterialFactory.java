package fr.arthurbambou.randomlyaddinganything.material;

public interface IMaterialFactory<T> {

    T create(IMaterial material, String modId);

    default String getSuffix() {
        return "";
    }

    String getName();

}
