package io.github.vampirestudios.raa.api.namegeneration.material;

import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.utils.Utils;

import java.util.*;

public class SpanishMaterials implements INameGenerator {
    public static final String[] LATIN_PREFIXES = {
            "ab", "ad", "ambi", "ante", "circum", "co", "com", "contra", "de", "dis", "di", "ex", "extra",
            "in", "en", "infra", "inter", "intra", "juxta", "ne", "non", "ob", "per", "post", "prae", "preter",
            "pro", "quasi", "re", "red", "retro", "se", "sed", "sin", "sub", "subter", "super", "supra", "trans",
            "ultra", "outr"
    };

    public static final String[] MIDDLES = {
            "al", "am", "an", "be", "bor", "cal", "co", "de", "duo", "eth", "en", "for", "gal", "in", "kary",
            "li", "lo", "la", "mi", "ma", "mun", "nat", "nor", "nit", "or", "om", "per", "rhen", "rho", "ri",
            "sic", "sit", "tan", "tor", "tri", "vi", "w", "x", "z"
    };

    public static final String[] ORE_SUFFIXES = { //Except "ium" and "ite" which will carry about 86% of the generated items
            "um", "ule", "ion", "ment", "icle", "ile", "ole", "ule", "ate", "and", "ant", "yn", "ice", "ixe"
    };

    public static final String[] CONSONANT_FILL = { //Stuffed between consonants
            "u", "o"
    };

    public static void main(String[] args) {
        SpanishMaterials gen = new SpanishMaterials();
        Collection<String> generated = gen.generate(100);

        System.out.println("Lowercase:" + generated);

        List<String> titleCased = new ArrayList<>();
        for (String s : generated) {
            titleCased.add(Utils.toTitleCase(s));
        }

        System.out.println("TitleCase:" + titleCased);
    }

    public String generate() {
        Random rnd = new Random();
        String ending = "";
        int endingRoll = rnd.nextInt(100);
        if (endingRoll < 30) {
            ending = "ice";
        } else if (endingRoll < 34) {
            ending = "ise";
        } else if (endingRoll < 43) {
            ending = "io";
        } else if (endingRoll < 69) {
            ending = "ix";
        } else if (endingRoll < 90) {
            ending = "ita";
        } else if (endingRoll < 96) {
            ending = ORE_SUFFIXES[rnd.nextInt(ORE_SUFFIXES.length)];
        }

        String prefix = LATIN_PREFIXES[rnd.nextInt(LATIN_PREFIXES.length)];

        if (prefix.length() + ending.length() < 5 || prefix.equals("super") || rnd.nextInt(3) < 2) {
            String middle = MIDDLES[rnd.nextInt(MIDDLES.length)];
            return combine(combine(prefix, middle), ending);
        }

        return combine(prefix, ending);
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
        switch (src) {
            case 'd':
                return 'o';
            default:
                return 'u';
        }
    }

    @Override
    public Map<String, String> getSpecialCharactersMap() {
        return new HashMap<>();
    }

}