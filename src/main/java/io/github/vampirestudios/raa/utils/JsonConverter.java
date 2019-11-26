package io.github.vampirestudios.raa.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JsonConverter {

    public static class StructureValues {
        private String name;
        private List<Integer> size;
        private int entities; //Figure out what to do with this
        private List<List<Integer>> blockPositions;
        private List<Integer> blockStates;
        private List<String> blockTypes;

        StructureValues() {
            name = "";
            size = new ArrayList<>();
            entities = 0;
            blockPositions = new ArrayList<>();
            blockStates = new ArrayList<>();
            blockTypes = new ArrayList<>();
        }

        public void setName(String nameIn) { name = nameIn; }
        public void setSize(List<Integer> sizeIn) { size = new ArrayList<>(sizeIn); }
        public void setEntities() {}
        public void setBlockPositions(List<Integer> positionsIn) { blockPositions.add(new ArrayList<>(positionsIn)); }
        public void setBlockStates(Integer statesIn) { blockStates.add(statesIn); }
        public void setBlockTypes(String blocksIn) { blockTypes.add(blocksIn); }

        public String getName() { return name; }
        public List<Integer> getSize() { return size; }
        public int getEntities() { return entities; }
        public List<List<Integer>> getBlockPositions() { return blockPositions; }
        public List<Integer> getBlockStates() { return blockStates; }
        public List<String> getBlockTypes() { return blockTypes; }
    }

    public StructureValues loadStructure(String FileIn) {
        StructureValues structure = new StructureValues();

        try {
            Scanner scanner = new Scanner(new File("../src/main/resources/assets/raa/structures/" + FileIn));

            int indentClass = 0;
            int indentList = 0;
            String section = "";
            List<Integer> tempList = new ArrayList<>();

            int bla = 0;

            while (scanner.hasNextLine()) {
                bla++;

                String line = scanner.nextLine();

                if (line.contains("}")) { indentClass--; }
                if (line.contains("]")) { indentList--; }

                String name = line.substring(line.indexOf("name") + 8, line.length() - 2);
                if (indentClass == 1 && line.contains("name")) {
                    structure.setName(name); //Get the name of the structure
                }
                else if (indentClass == 3 && line.contains("name")) {
                    section = name;
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
                else if (section.contains("blocks") && line.contains("pos")) {
                    section = "blocks1";
                }
                else if (section.contains("blocks") && line.contains("state")) {
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
                else if (section.equals("palette") && line.contains("Name")) {
                    section = "Name";
                }
                else if (section.equals("Name") && line.contains("value")) {
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