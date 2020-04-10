package io.github.vampirestudios.raa.entity;

import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.Identifier;

public class EntityData {
    public Identifier id;
    public float r;
    public float g;
    public float b;
    public float size;
    public boolean runFromSun;
    public float attackSpeed;
//    public EntityRenderingData renderingData;

    public EntityData(Identifier id) {
        this.id = id;

        r = Rands.getRandom().nextFloat();
        g = Rands.getRandom().nextFloat();
        b = Rands.getRandom().nextFloat();
        size = Rands.randFloatRange(0.5f, 2);
        runFromSun = Rands.chance(3);
        attackSpeed = Rands.randFloatRange(0.5f, 2f);
//        renderingData = EntityRenderingDatas.randomRenderData();
    }
}
