package io.github.vampirestudios.raa.generation.surface.random;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.namegeneration.NameGenerator;
import io.github.vampirestudios.raa.generation.surface.random.elements.*;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

import java.util.*;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class SurfaceBuilderGenerator {
    private static final SimpleRegistry<SurfaceBuilderHolder> SURFACE_BUILDERS = new SimpleRegistry<>();
    public static SimpleRegistry<SurfaceBuilder> RANDOM_SURFACE_BUILDER = new SimpleRegistry<>();
    private static final Map<String, Class<? extends SurfaceElement>> ID_SURFACE_ELEMENT_MAP = new HashMap<>();
    private static final WeightedList<Class<? extends SurfaceElement>> WEIGHTED_ELEMENTS = new WeightedList<>();

    public static void registerElements() {
        //grass has special spawning rules
        ID_SURFACE_ELEMENT_MAP.put(new GrassSurfaceElement().getType().toString(), GrassSurfaceElement.class);

        registerElement(new DesertSurfaceElement(), 6);
        registerElement(new RedDesertSurfaceElement(), 5);
        registerElement(new GravelSurfaceElement(), 5);
        registerElement(new PatchyBadlandsSurfaceElement(), 5);
        registerElement(new PatchyDarkBadlandsSurfaceElement(), 5);
        registerElement(new DunesSurfaceElement(), 5);
        registerElement(new SandyDunesSurfaceElement(), 5);
        registerElement(new FloatingIslandSurfaceElement(), 5);
        registerElement(new HyperflatSurfaceElement(), 5);
        registerElement(new LazyNoiseSurfaceElement(), 5);
        registerElement(new ClassicCliffsSurfaceElement(), 2);
        registerElement(new StratifiedCliffsSurfaceElement(), 2);
        registerElement(new RandomSpiresSurfaceElement(), 1);
    }

    private static void registerElement(SurfaceElement e, int weight) {
        ID_SURFACE_ELEMENT_MAP.put(e.getType().toString(), e.getClass());
        WEIGHTED_ELEMENTS.add(e.getClass(), weight);
    }

    public static void generate() {
        Set<Identifier> names = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            //generate names
            NameGenerator nameGenerator = RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionNameGenerator();
            Pair<String, Identifier> name = nameGenerator.generateUnique(names, MOD_ID);
            names.add(name.getRight());


            //add the surface builder to the registry
            List<SurfaceElement> elements = new ArrayList<>();
            for (int j = 0; j < 2; j++) { //add 2 elements
                try { //catch exceptions because yay reflection
                    SurfaceElement e = WEIGHTED_ELEMENTS.pickRandom(Rands.getRandom()).newInstance();

                    //ensure that every element is unique
                    while (elements.contains(e)) {
                        e = WEIGHTED_ELEMENTS.pickRandom(Rands.getRandom()).newInstance();
                    }

                    //add to list
                    elements.add(e);
                } catch (InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }

            //add grass randomly
            if (Rands.randInt(10) > 2) {
                elements.add(new GrassSurfaceElement());
            }
            SURFACE_BUILDERS.add(name.getRight(), new SurfaceBuilderHolder("basic", elements));
            elements.sort(Comparator.comparingInt(SurfaceElement::getPriority));

            //register the actual surface builder
            RandomSurfaceBuilder sb = new RandomSurfaceBuilder(elements);
            Registry.register(Registry.SURFACE_BUILDER, name.getRight(), sb);
            Registry.register(RANDOM_SURFACE_BUILDER, name.getRight(), sb);
        }
    }

    public static void load(JsonObject obj) {
        obj.entrySet().forEach(e -> {
            if (e.getKey().startsWith("raa")) { //check if the key is a new surface builder key
                List<SurfaceElement> elements = new ArrayList<>();
                //blessed code to get the elements in the data
                e.getValue().getAsJsonObject().get("elements").getAsJsonArray().forEach(se -> {
                    String type = se.getAsJsonObject().get("type").getAsString();
                    try {
                        //add the element to the array
                        SurfaceElement element = ID_SURFACE_ELEMENT_MAP.get(type).newInstance();
                        //deserialize the element
                        element.deserialize(se.getAsJsonObject().get("data").getAsJsonObject());

                        elements.add(element);
                    } catch (InstantiationException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                });

                //sort the elements
                elements.sort(Comparator.comparingInt(SurfaceElement::getPriority));

                //register the surface builder
                RandomSurfaceBuilder sb = new RandomSurfaceBuilder(elements);
                Registry.register(Registry.SURFACE_BUILDER, e.getKey(), sb);
                Registry.register(RANDOM_SURFACE_BUILDER, e.getKey(), sb);
            }
        });
    }

    public static void save(JsonObject obj) {
        SURFACE_BUILDERS.getIds().forEach(id -> {
            SurfaceBuilderHolder sb = SURFACE_BUILDERS.get(id);
            if (sb == null) return;

            //add the type to the json
            JsonObject holder = new JsonObject();
            holder.addProperty("type", sb.type);

            //this holds every SurfaceElement
            JsonArray elements = new JsonArray();
            //iterate through the elements and serialize them all
            sb.elements.forEach(se -> {
                System.out.println(se.getType());
                JsonObject element = new JsonObject();
                element.addProperty("type", se.getType().toString());
                JsonObject data = new JsonObject();
                se.serialize(data);
                element.add("data", data);
                elements.add(element);
            });
            //add the elements
            holder.add("elements", elements);


            obj.add(id.toString(), holder);
        });
    }

    private static class SurfaceBuilderHolder {
        private String type;
        private List<SurfaceElement> elements;

        private SurfaceBuilderHolder(String type, List<SurfaceElement> elements) {
            this.type = type;
            this.elements = elements;
        }
    }
}
