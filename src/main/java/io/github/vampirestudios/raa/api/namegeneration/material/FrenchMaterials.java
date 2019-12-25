package io.github.vampirestudios.raa.api.namegeneration.material;

import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.utils.Rands;

import java.util.HashMap;
import java.util.Map;

public class FrenchMaterials implements INameGenerator {

    private static String[] vowels = new String[]{
            "a", "e", "i", "o", "u", "y", "ou", "oi", "au", "ai", "ei", "eu", "eau", "é", "è"
    };
    private static String[] list1 = new String[]{"r", "l", "s", "f", "m", "ch", "n", "v", "z", "p", "c", "b", "j", "g"};
    private static String[] list2 = new String[]{"d", "t", "h", "k", "qu", "br", "cr", "dr", "gr", "fr", "vr", "pr", "tr", "bl"};
    private static String[] list3 = new String[]{"cl", "gl", "fl", "pl", "n", "g", "ph", "ç", "gn", "y", "mp", "mb", "w", "gr"};
    private static String[] list6 = new String[]{"ite", "ium"};
    private static String[] list4 = new String[list1.length];

    private static String lf(String string, String string1) {
        String string2 = string + string1;
        char[] chars = string2.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return String.valueOf(chars);
    }

    @Override
    public String generate() {
        for (int o = 0; o < list1.length; o++) {
            String[] choice = {list3[o], list2[o], list1[o]};
            list4[o] = Rands.values(choice);
        }
        int ab = 1;
        int aa = Rands.randInt(ab + 1);
        int b = 1;
        int c = Rands.randInt(b + 1);
        int d = Rands.randInt(c + 1);
        String[] l1 = new String[]{"", Rands.values(list4)};
        String[] l2 = new String[]{"", Rands.values(vowels)};
        String[] l3 = new String[]{"", Rands.values(list4)};
        String[] l4 = new String[]{"", Rands.values(vowels)};
        String[] l5 = new String[]{"", Rands.values(list4)};
        String string = (lf(l1[aa], l2[ab]) + l3[b] + l4[c] + l5[d] + Rands.values(list6));
        if (string.startsWith("mp")) string = string.replace("mp", "p");
        return string;
    }

    @Override
    public Map<String, String> getSpecialCharactersMap() {
        Map<String, String> map = new HashMap<>();
        map.put("é", "e");
        map.put("è", "e");
        map.put("ç", "c");
        return map;
    }
}
