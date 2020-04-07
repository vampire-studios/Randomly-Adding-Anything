package io.github.vampirestudios.raa.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Vec3i;

import java.util.*;

public class JsonConverter {

    public StructureValues loadStructure(JsonObject structureJson) {
//        System.out.println("Idk: " + JsonHelper.getString(structureJson, "structureName", "test"));
        StructureValues structure = new StructureValues();
        structure.setName(JsonHelper.getString(structureJson, "structureName", "test"));

        if (JsonHelper.hasArray(structureJson, "nbt")) {
//            System.out.println("Old structure file! Will still load it.");
            JsonArray nbtArray = JsonHelper.getArray(structureJson, "nbt");
            JsonObject structureJsonObject = nbtArray.get(0).getAsJsonObject();
            JsonArray array = JsonHelper.getArray(structureJsonObject, "value");
            array.forEach(jsonElement -> {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                String name = JsonHelper.getString(jsonObject, "name");
                JsonObject valueArray = JsonHelper.getObject(jsonObject, "value");

                if (name.equals("size")) {
                    JsonArray list = JsonHelper.getArray(valueArray, "list");
                    List<Integer> size = new ArrayList<>();
                    size.add(list.get(0).getAsJsonPrimitive().getAsInt());
                    size.add(list.get(1).getAsJsonPrimitive().getAsInt());
                    size.add(list.get(2).getAsJsonPrimitive().getAsInt());
                    structure.setSize(size);
                }

                if (name.equals("entities")) structure.setEntities();

                if (name.equals("blocks")) {
                    JsonArray list = JsonHelper.getArray(valueArray, "list");
                    list.forEach(jsonElement1 -> {
                        JsonArray blockProperties = jsonElement1.getAsJsonArray();
                        blockProperties.forEach(jsonElement2 -> {
                            JsonObject blockProperty = jsonElement2.getAsJsonObject();
                            String propertyName = JsonHelper.getString(blockProperty, "name");
                            if (propertyName.equals("state")) {
                                int state = JsonHelper.getInt(blockProperty, "value");
                                structure.setBlockStates(state);
                            }
                            if (propertyName.equals("pos")) {
                                JsonObject valueObject = JsonHelper.getObject(blockProperty, "value");
                                JsonArray pos = JsonHelper.getArray(valueObject, "list");
                                List<Integer> posArray = new ArrayList<>();
                                posArray.add(pos.get(0).getAsJsonPrimitive().getAsInt());
                                posArray.add(pos.get(1).getAsJsonPrimitive().getAsInt());
                                posArray.add(pos.get(2).getAsJsonPrimitive().getAsInt());
                                structure.setBlockPositions(posArray);
                            }
                        });
                    });
                }

                if (name.equals("palette")) {
                    JsonArray list = JsonHelper.getArray(valueArray, "list");
                    list.forEach(jsonElement1 -> {
                        Map<String, String> blockPropertyMap = new HashMap<>();
                        JsonArray paletteProperties = jsonElement1.getAsJsonArray();
                        paletteProperties.forEach(jsonElement2 -> {
                            JsonObject paletteProperty = jsonElement2.getAsJsonObject();
                            String propertyName = JsonHelper.getString(paletteProperty, "name");
                            if (propertyName.equals("Name")) {
                                structure.setBlockProperties(blockPropertyMap);
                                String blockId = JsonHelper.getString(paletteProperty, "value");
                                structure.setBlockTypes(blockId);
                            }
                            if (propertyName.equals("Properties")) {
                                JsonArray properties = JsonHelper.getArray(paletteProperty, "value");
                                properties.forEach(jsonElement3 -> {
                                    JsonObject blockProperties = jsonElement3.getAsJsonObject();
                                    String blockPropertyName = JsonHelper.getString(blockProperties, "name");
                                    String propertyValue = JsonHelper.getString(blockProperties, "value");
                                    blockPropertyMap.put(blockPropertyName, propertyValue);
                                });
                            }
                        });
                    });
                }
            });
        }

        if (JsonHelper.hasArray(structureJson, "blocks")) {
            JsonArray blocksArray = JsonHelper.getArray(structureJson, "blocks");
            blocksArray.forEach(jsonElement -> {
//                System.out.println("State: " + jsonElement.getAsJsonObject().get("state").getAsInt());
                structure.setBlockStates(jsonElement.getAsJsonObject().get("state").getAsInt());
                List<Integer> pos = new ArrayList<>();
                JsonArray array = jsonElement.getAsJsonObject().get("pos").getAsJsonArray();
                pos.add(array.get(0).getAsJsonPrimitive().getAsInt());
                pos.add(array.get(1).getAsJsonPrimitive().getAsInt());
                pos.add(array.get(2).getAsJsonPrimitive().getAsInt());
                structure.setBlockPositions(pos);
            });
        }

        if (JsonHelper.hasArray(structureJson, "palette")) {
            JsonArray blocksArray = JsonHelper.getArray(structureJson, "palette");
            blocksArray.forEach(jsonElement -> {
                Identifier identifier = Identifier.tryParse(jsonElement.getAsJsonObject().get("name").getAsString());
                structure.setBlockTypes(Objects.requireNonNull(identifier).toString());
            });
        }
        return structure;
    }

    public static class StructureValues {
        private String name;
        private Vec3i size;
        private int entities; //Figure out what to do with this
        private List<Vec3i> blockPositions;
        private List<Integer> blockStates;
        private List<String> blockTypes;
        private List<Map<String, String>> blockProperties;

        StructureValues() {
            name = "";
            size = Vec3i.ZERO;
            entities = 0;
            blockPositions = new ArrayList<>();
            blockStates = new ArrayList<>();
            blockTypes = new ArrayList<>();
            blockProperties = new ArrayList<>();
        }

        public void setEntities() {
        }

        public String getName() {
            return name;
        }

        public void setName(String nameIn) {
            name = nameIn;
        }

        public Vec3i getSize() {
            return size;
        }

        public void setSize(List<Integer> sizeIn) {
            size = new Vec3i(sizeIn.get(0), sizeIn.get(1), sizeIn.get(2));
        }

        public int getEntities() {
            return entities;
        }

        public List<Vec3i> getBlockPositions() {
            return blockPositions;
        }

        public void setBlockPositions(List<Integer> positionsIn) {
            blockPositions.add(new Vec3i(positionsIn.get(0), positionsIn.get(1), positionsIn.get(2)));
        }

        public List<Integer> getBlockStates() {
            return blockStates;
        }

        public void setBlockStates(Integer statesIn) {
            blockStates.add(statesIn);
        }

        public List<String> getBlockTypes() {
            return blockTypes;
        }

        public void setBlockTypes(String blocksIn) {
            blockTypes.add(blocksIn);
        }

        public List<Map<String, String>> getBlockProperties() {
            return blockProperties;
        }

        public void setBlockProperties(Map<String, String> propertiesIn) {
            blockProperties.add(propertiesIn);
        }
    }
}