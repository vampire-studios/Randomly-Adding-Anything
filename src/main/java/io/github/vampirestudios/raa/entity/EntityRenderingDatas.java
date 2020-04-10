package io.github.vampirestudios.raa.entity;

import com.google.common.collect.ImmutableList;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class EntityRenderingDatas {

    public static final EntityRenderingData MOB_RENDER = randomRenderData();

    public static EntityRenderingData randomRenderData() {
        List<EntityModel<? extends MobEntity>> models = ImmutableList.of();
        List<Identifier> textures = ImmutableList.of();

        return new EntityRenderingData(Rands.list(models), Rands.list(textures));
    }

}
