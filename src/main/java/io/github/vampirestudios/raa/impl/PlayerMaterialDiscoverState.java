package io.github.vampirestudios.raa.impl;

import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.state.OreDiscoverState;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.PersistentState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerMaterialDiscoverState extends PersistentState {
    private Map<UUID, List<OreDiscoverState>> playerMap = new HashMap();

    public PlayerMaterialDiscoverState() {
        super("materialDiscover");
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {
        CompoundTag compoundTag_2 = compoundTag.getCompound("playerMap");
        System.out.println(compoundTag.toString());
        Iterator var3 = compoundTag_2.getKeys().iterator();

        while(var3.hasNext()) {
            String string_1 = (String)var3.next();
            CompoundTag compoundTag1 = compoundTag_2.getCompound(string_1);
            Iterator var4 = compoundTag1.getKeys().iterator();
            List<OreDiscoverState> list = new ArrayList<>();
            while (var4.hasNext()) {
                String string = (String)var4.next();
                int index = Integer.getInteger(string);
                CompoundTag compoundTag2 = compoundTag1.getCompound(string);
                AtomicReference<Material> material = null;
                Materials.MATERIALS.forEach((materiale) -> {
                    String string7 = compoundTag2.getString("name");
                    if (materiale.getName() == string7) {
                        material.set(materiale);
                        return;
                    }
                });
                int discoveredTimes = compoundTag2.getInt("discoverTimes");
                boolean discovered = compoundTag2.getBoolean("discovered");
                list.set(index, new OreDiscoverState(material.get(), discoveredTimes, discovered));
            }
            this.playerMap.put(UUID.fromString(string_1), list);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        CompoundTag compoundTag_2 = new CompoundTag();
        this.playerMap.forEach((uuid, list) -> {
            CompoundTag compoundTag_3 = new CompoundTag();
            CompoundTag compoundTag_4;
            for (int c = 0; c < list.size(); c++) {
                compoundTag_4 = new CompoundTag();
                compoundTag_4.putString("name", list.get(c).getMaterial().getName());
                compoundTag_4.putInt("discoverTimes", list.get(c).getDiscoverTimes());
                compoundTag_4.putBoolean("discovered", list.get(c).isDiscovered());
                compoundTag_3.put(""+c+"", compoundTag_4.method_10553());
            }
            compoundTag_2.put(uuid.toString(), compoundTag_3.method_10553());
        });
        compoundTag.put("playerMap", compoundTag_2);
        System.out.println(compoundTag.toString());
        return compoundTag;
    }

    public Map<UUID, List<OreDiscoverState>> getPlayerMap() {
        return playerMap;
    }

    @Override
    public void save(File file_1) {
        if (this.isDirty()) {
            CompoundTag compoundTag_1 = new CompoundTag();
            compoundTag_1.put("data", this.toTag(new CompoundTag()));
            compoundTag_1.putInt("DataVersion", SharedConstants.getGameVersion().getWorldVersion());
            System.out.println(compoundTag_1.toString());

            try {
                FileOutputStream fileOutputStream_1 = new FileOutputStream(file_1);
                Throwable var4 = null;

                try {
                    NbtIo.writeCompressed(compoundTag_1, fileOutputStream_1);
                } catch (Throwable var14) {
                    var4 = var14;
                    throw var14;
                } finally {
                    if (fileOutputStream_1 != null) {
                        if (var4 != null) {
                            try {
                                fileOutputStream_1.close();
                            } catch (Throwable var13) {
                                var4.addSuppressed(var13);
                            }
                        } else {
                            fileOutputStream_1.close();
                        }
                    }

                }
            } catch (IOException var16) {
//                System.out.println("Could not save data {}", this, var16);
            }

            this.setDirty(false);
        }
    }
}
