package io.github.vampirestudios.raa.material;

public interface IMaterialFactory<T> {

    T create(IMaterial material, String modId);

    default String getSuffix() {
        return "";
    }

    String getName();

}
