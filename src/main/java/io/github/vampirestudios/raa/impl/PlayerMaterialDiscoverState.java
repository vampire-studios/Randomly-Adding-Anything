package io.github.vampirestudios.raa.impl;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.state.OreDiscoverState;
import net.minecraft.SharedConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerMaterialDiscoverState {
    private List<OreDiscoverState> list;
    private boolean firstConnect;

    public PlayerMaterialDiscoverState() {
        this.list = new ArrayList<>(Materials.MATERIALS.getIds().size()+1);
//        this.firstConnect = true;
    }

    public void fromTag(CompoundTag compoundTag) {
        List<OreDiscoverState> list = new ArrayList<>(Materials.MATERIALS.getIds().size()+1);
        CompoundTag compoundTag_2 = compoundTag.getCompound("discoverList");
        if (compoundTag_2.isEmpty() || compoundTag_2 == null) return;
        Iterator var3 = compoundTag_2.getKeys().iterator();
        while (var3.hasNext()) {
            String string_1 = (String) var3.next();
            CompoundTag compoundTag1 = compoundTag_2.getCompound(string_1);
//            int index = Integer.parseInt(string_1);
            Material material = Materials.MATERIALS.get(new Identifier(RandomlyAddingAnything.MOD_ID, compoundTag1.getString("name")));
            if (material == null) {
                System.out.println("Could not find this material : " + compoundTag1.getString("name"));
            }
            int discoveredTimes = compoundTag1.getInt("discoverTimes");
            boolean discovered = compoundTag1.getBoolean("discovered");
            list.add(new OreDiscoverState(material, discoveredTimes, discovered));
        }
        this.list = list;
    }

    public CompoundTag toTag(CompoundTag compoundTag) {
        CompoundTag compoundTag_2 = new CompoundTag();
        CompoundTag compoundTag_3;
        for (int c = 0; c < list.size(); c++) {
            compoundTag_3 = new CompoundTag();
            compoundTag_3.putString("name", list.get(c).getMaterial().getName());
            compoundTag_3.putInt("discoverTimes", list.get(c).getDiscoverTimes());
            compoundTag_3.putBoolean("discovered", list.get(c).isDiscovered());
            compoundTag_2.put(""+c+"", compoundTag_3.method_10553());
        }
        compoundTag.put("discoverList", compoundTag_2);
        return compoundTag;
    }

    public List<OreDiscoverState> getList() {
        return list;
    }

    public boolean isFirstConnect() {
        return firstConnect;
    }

    public void setFirstConnect(boolean firstConnect) {
        this.firstConnect = firstConnect;
    }
}
