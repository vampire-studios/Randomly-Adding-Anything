package io.github.vampirestudios.raa.entity;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class EntityRenderingData {

    public EntityModel<? extends MobEntity> model;
    public Identifier texture;

    public EntityRenderingData(EntityModel<? extends MobEntity> model, Identifier texture) {
        this.model = model;
        this.texture = texture;
    }

}
