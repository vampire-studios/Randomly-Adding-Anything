package io.github.vampirestudios.raa.utils;

import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.math.Vec3i;

import java.io.File;
import java.util.*;

public class JsonConverter {

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

        public void setName(String nameIn) { name = nameIn; }
        public void setSize(List<Integer> sizeIn) { size = new Vec3i(sizeIn.get(0), sizeIn.get(1), sizeIn.get(2)); }
        public void setEntities() {}
        public void setBlockPositions(List<Integer> positionsIn) { blockPositions.add(new Vec3i(positionsIn.get(0), positionsIn.get(1), positionsIn.get(2))); }
        public void setBlockStates(Integer statesIn) { blockStates.add(statesIn); }
        public void setBlockTypes(String blocksIn) { blockTypes.add(blocksIn); }
        public void setBlockProperties(Map<String, String> propertiesIn) { blockProperties.add(propertiesIn); }

        public String getName() { return name; }
        public Vec3i getSize() { return size; }
        public int getEntities() { return entities; }
        public List<Vec3i> getBlockPositions() { return blockPositions; }
        public List<Integer> getBlockStates() { return blockStates; }
        public List<String> getBlockTypes() { return blockTypes; }
        public List<Map<String, String>> getBlockProperties() { return blockProperties; }
    }

    public StructureValues loadStructure(String FileIn) {
        StructureValues structure = new StructureValues();

        try {
            Scanner scanner = new Scanner(new File("../src/main/resources/assets/raa/structures/" + FileIn));

            int indentClass = 0;
            int indentList = 0;
            String section = "";
            String property = "";
            List<Integer> tempList = new ArrayList<>();
            Map<String, String> tempMap = new HashMap<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.contains("}")) { indentClass--; }
                if (line.contains("]")) { indentList--; }

                if (indentClass == 1 && line.contains("name")) {
                    structure.setName(line.substring(line.indexOf("name") + 8, line.length() - 2)); //Get the name of the structure
                }
                else if (indentClass == 3 && line.contains("name")) {
                    section = line.substring(line.indexOf("name") + 8, line.length() - 2);
                }
                else if (section.equals("size") && indentList == 3) {
                    tempList.add(Integer.parseInt(line.replaceAll("[^\\d-]", "")));
                    if (tempList.size() == 3) {
                        structure.setSize(tempList); //Get the size of the structure
                        tempList.clear();
                    }
                }
                else if (section.equals("entities") && indentClass == 4 && line.contains("list")) {
                    structure.setEntities(); //Get the entities
                }
                else if (section.contains("blocks") && line.contains(": \"nbt\"")) {
                    section = "blocks0"; //Structure block!
                }
                else if (section.contains("blocks") && line.contains(": \"pos\"")) {
                    section = "blocks1";
                }
                else if (section.contains("blocks") && line.contains(": \"state\"")) {
                    section = "blocks2";
                }
                else if (section.equals("blocks1") && indentList == 5) {
                    tempList.add(Integer.parseInt(line.replaceAll("[^\\d-]", "")));
                    if (tempList.size() == 3) {
                        structure.setBlockPositions(tempList); //Get the positions of the blocks in the structure
                        tempList.clear();
                    }
                }
                else if (section.equals("blocks2") && indentClass == 5 && line.contains("value")) {
                    structure.setBlockStates(Integer.parseInt(line.replaceAll("[^\\d-]", ""))); //Get the states of the blocks in the structure
                }
                else if (section.equals("palette") && line.contains("Properties")) {
                    section = "Properties";
                }
                else if ((section.equals("palette") || section.equals("Properties")) && line.contains("Name")) {
                    section = "Name";
                }
                else if (section.equals("Properties") && line.contains("name")) {
                    property = line.substring(line.indexOf("name") + 8, line.length() - 2);
                }
                else if (section.equals("Properties") && line.contains("value") && !line.contains("[")) {
                    tempMap.put(property, line.substring(line.indexOf("value") + 9, line.length() - 1).toUpperCase());
                }
                else if (section.equals("Name") && line.contains("value")) {
                    structure.setBlockProperties(tempMap); //Get the properties of the blocks used in the structure
                    tempMap = new HashMap<>();
                    structure.setBlockTypes(line.substring(line.indexOf("value") + 9, line.length() - 1)); //Get the type of blocks used in the structure
                    section = "palette";
                }

                if (line.contains("{")) { indentClass++; }
                if (line.contains("[")) { indentList++; }
            }

            return structure; //Success!
        }
        catch (Exception e) {
            e.printStackTrace();
            return null; //Something went wrong
        }
    }
}