package io.github.vampirestudios.raa.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JsonConverter {

    public class StructureValues {
        private String name;
        private List<Integer> size;
        private int entities; //Figure out what to do with this
        private List<List<Integer>> block_positions;
        private List<Integer> block_states;
        private List<String> block_types;

        StructureValues() {
            name = "";
            size = new ArrayList<>();
            entities = 0;
            block_positions = new ArrayList<>();
            block_states = new ArrayList<>();
            block_types = new ArrayList<>();
        }

        public void setName(String name_in) { name = name_in; }
        public void setSize(List<Integer> size_in) { size = new ArrayList<>(size_in); }
        public void setEntities() {}
        public void setBlock_positions(List<Integer> positions_in) { block_positions.add(new ArrayList<>(positions_in)); }
        public void setBlock_states(Integer states_in) { block_states.add(states_in); }
        public void setBlock_types(String blocks_in) { block_types.add(blocks_in); }

        public String getName() { return name; }
        public List<Integer> getSize() { return size; }
        public int getEntities() { return entities; }
        public List<List<Integer>> getBlock_positions() { return block_positions; }
        public List<Integer> getBlock_states() { return block_states; }
        public List<String> getBlock_types() { return block_types; }
    }

    public StructureValues LoadStructure(String FileIn) {
        StructureValues structure = new StructureValues();

        try {
            Scanner scanner = new Scanner(new File("../src/main/resources/assets/raa/structures/" + FileIn));

            int indent_class = 0;
            int indent_list = 0;
            String section = "";
            List<Integer> temp_list = new ArrayList<>();

            int bla = 0;

            while (scanner.hasNextLine()) {
                bla++;

                String line = scanner.nextLine();

                if (line.contains("}")) { indent_class--; }
                if (line.contains("]")) { indent_list--; }

                if (indent_class == 1 && line.contains("name")) {
                    structure.setName(line.substring(line.indexOf("name") + 8, line.length() - 2)); //Get the name of the structure
                }
                else if (indent_class == 3 && line.contains("name")) {
                    section = line.substring(line.indexOf("name") + 8, line.length() - 2);
                }
                else if (section.equals("size") && indent_list == 3) {
                    temp_list.add(Integer.parseInt(line.replaceAll("[^\\d-]", "")));
                    if (temp_list.size() == 3) {
                        structure.setSize(temp_list); //Get the size of the structure
                        temp_list.clear();
                    }
                }
                else if (section.equals("entities") && indent_class == 4 && line.contains("list")) {
                    structure.setEntities(); //Get the entities
                }
                else if (section.contains("blocks") && line.contains("pos")) {
                    section = "blocks1";
                }
                else if (section.contains("blocks") && line.contains("state")) {
                    section = "blocks2";
                }
                else if (section.equals("blocks1") && indent_list == 5) {
                    temp_list.add(Integer.parseInt(line.replaceAll("[^\\d-]", "")));
                    if (temp_list.size() == 3) {
                        structure.setBlock_positions(temp_list); //Get the positions of the blocks in the structure
                        temp_list.clear();
                    }
                }
                else if (section.equals("blocks2") && indent_class == 5 && line.contains("value")) {
                    structure.setBlock_states(Integer.parseInt(line.replaceAll("[^\\d-]", ""))); //Get the states of the blocks in the structure
                }
                else if (section.equals("palette") && line.contains("Name")) {
                    section = "Name";
                }
                else if (section.equals("Name") && line.contains("value")) {
                    structure.setBlock_types(line.substring(line.indexOf("value") + 9, line.length() - 1)); //Get the type of blocks used in the structure
                    section = "palette";
                }

                if (line.contains("{")) { indent_class++; }
                if (line.contains("[")) { indent_list++; }
            }

            return structure; //Success!
        }
        catch (Exception e) {
            e.printStackTrace();
            return null; //Something went wrong
        }
    }
}