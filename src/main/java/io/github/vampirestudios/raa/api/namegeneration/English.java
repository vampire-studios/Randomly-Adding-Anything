package io.github.vampirestudios.raa.api.namegeneration;

import io.github.vampirestudios.raa.utils.Rands;

import java.util.HashMap;
import java.util.Map;

public class English implements INameGenerator {

    private static String[] list1 = new String[]{"e","a","u","o","i","io","oi","ou","ee","oo","ia","ea","ao"};
    private static String[] list2 = new String[list1.length];
    private static String[] list3 = new String[]{"l","c","cw","vw","z","x","cr","v","qu","m","bw","br","n"};
    private static String[] list4 = new String[]{"st","sk","sh","th","f","fr","fw","g","gr","gw","j","h","vr"};
    private static String[] list5 = new String[]{"w","r","t","tr","tw","y","p","pw","pr","s","sr","sp","b"};
    private static String[] list6 = new String[]{"ite","ium","",""};

    public String generate() {
        for (int o = 0; o < list1.length; o++) {
            String[] choice = {list5[o], list4[o],list3[o]};
            list2[o] = Rands.values(choice);
        }
        int ab = 1;
        int aa = Rands.randInt(ab + 1);
        int b = 1;
        int c = Rands.randInt(b + 1);
        int d = Rands.randInt(c + 1);
        int e = Rands.randInt(d + 1);
        String[] l1 = new String[]{"",Rands.values(list2)};
        String[] l2 = new String[]{"",Rands.values(list1)};
        String[] l3 = new String[]{"",Rands.values(list2)};
        String[] l4 = new String[]{"",Rands.values(list1)};
        String[] l5 = new String[]{"",Rands.values(list2)};
        String[] l6 = new String[]{"",Rands.values(list6)};
        return lf(l1[aa],l2[ab]) + l3[b] + l4[c] + l5[d] + l6[e];
    }

    @Override
    public Map<String, String> getSpecialCharatersMap() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    private static String lf(String string, String string1) {
        String string2 = string + string1;
        char[] chars = string2.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return String.valueOf(chars);
    }
}
