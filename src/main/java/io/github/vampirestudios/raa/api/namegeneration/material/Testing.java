package io.github.vampirestudios.raa.api.namegeneration.material;

import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;

import java.util.*;

public class Testing implements INameGenerator {
    public static final String[] IDK_LIST = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "Æ", "Ø", "Å", "_",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
    };

    public static void main(String[] args) {
        Testing gen = new Testing();
        Collection<String> generated = gen.generate(50);

        System.out.println("Lowercase:" + generated);

        List<String> titleCased = new ArrayList<>();
        for (String s : generated) {
            titleCased.add(Utils.toTitleCase(s));
        }

        System.out.println("TitleCase:" + titleCased);
    }

    public String generate() {
        Random rnd = new Random();
        StringBuilder builder = new StringBuilder();
        List<String> letters = new ArrayList<>();
        int nameLength = Rands.randIntRange(1, 100);

        for (int i = 0; i < nameLength; i++) {
            letters.add(IDK_LIST[rnd.nextInt(IDK_LIST.length)]);
        }
        String name = "";
        for (String string : letters) {
            name = builder.append(string).toString();
        }
        return name.toLowerCase();
    }

    public Collection<String> generate(int count) {
        HashSet<String> result = new HashSet<>(count);
        while (result.size() < count) {
            String cur = generate();
            result.add(cur); //Nothing happens on duplicates
        }

        return result;
    }

    public String combine(String a, String b) {
        if (a.isEmpty() || b.isEmpty()) return a + b;

        char bStart = b.charAt(0);
        char aEnd = a.charAt(a.length() - 1);
        if (bStart == aEnd) b = b.substring(1);
        if (isConsonant(aEnd) && isConsonant(bStart)) {
            return Utils.toTitleCase(a + fillConsonant(aEnd) + b);
        }

        return Utils.toTitleCase(a + b);
    }

    public boolean isConsonant(char ch) {
        switch (ch) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
            case 'y':
                return false;
            default:
                return true;
        }
    }

    public char fillConsonant(char src) {
        if (src == 'd') {
            return 'o';
        }
        return 'u';
    }

    @Override
    public Map<String, String> getSpecialCharactersMap() {
        return new HashMap<>();
    }

}