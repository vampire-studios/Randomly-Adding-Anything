package fr.arthurbambou.randomlyaddinganything.client;

import com.google.common.base.Charsets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Environment(EnvType.CLIENT)
public class ModelHelper {

    public static final ModelTransformation DEFAULT_ITEM_TRANSFORMS = loadTransformFromJson(new Identifier("minecraft:models/item/generated"));
    public static final ModelTransformation HANDHELD_ITEM_TRANSFORMS = loadTransformFromJson(new Identifier("minecraft:models/item/handheld"));

    public static ModelTransformation loadTransformFromJson(Identifier location) {
        try {

            return JsonUnbakedModel.deserialize(getReaderForResource(location)).getTransformations();
        } catch (IOException exception) {
            System.out.println("Can't load resource " + location);
            exception.printStackTrace();
            return null;
        }
    }

    public static Reader getReaderForResource(Identifier location) throws IOException {
        Identifier file = new Identifier(location.getNamespace(), location.getPath() + ".json");
        Resource iresource = MinecraftClient.getInstance().getResourceManager().getResource(file);
        return new BufferedReader(new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8));
    }
}
