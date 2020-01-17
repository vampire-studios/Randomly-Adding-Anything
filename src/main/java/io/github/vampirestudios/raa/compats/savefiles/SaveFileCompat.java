package io.github.vampirestudios.raa.compats.savefiles;

import io.github.vampirestudios.raa.config.RAADataConfig;
import io.github.vampirestudios.raa.generation.materials.Material;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

public abstract class SaveFileCompat {

    private File path;
    private File file;

    public SaveFileCompat(String filename, String subpath) {
        this.path = new File(RAADataConfig.CONFIG_PATH, subpath);
        this.file = new File(this.path, filename + ".json");
    }

    public File getFile() {
        return file;
    }

    public abstract void generate();

    public abstract Map<Material, Integer> load();

    public boolean savingFileExist() {
        return this.file.exists();
    }
}
