package io.github.vampirestudios.raa.api.namegeneration.dimensions;

import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.utils.Utils;

import java.util.*;

public class SpanishDimensions implements INameGenerator {
    public static final String[] LATIN_PREFIXES = {
            "ab", "ad", "ambi", "ante", "circum", "co", "com", "con", "contra", "de", "den", "dis", "di", "ex", "extra",
            "in", "en", "infra", "inter", "intra", "juxta", "me", "ne", "non", "ob", "ox", "per", "post", "prae", "preter",
            "pro", "quasi", "ques", "re", "red", "retro", "se", "sed", "sen", "sin", "sod", "sub", "subter", "super", "supra",
            "tran", "trans", "ult", "ultra", "out", "outr"
    };

    public static final String[] MIDDLES = {
            "al", "am", "an", "be", "bor", "cal", "co", "de", "duo", "eth", "en", "for", "gal", "in", "kary",
            "li", "lo", "la", "mi", "ma", "mun", "nat", "net", "nor", "nit", "or", "om", "per", "rhen", "rho", "ri",
            "sic", "sit", "tan", "tor", "tri", "vi", "w", "x", "z"
    };

    public static final String[] CONSONANT_FILL = { //Stuffed between consonants
            "u", "o"
    };

    public static void main(String[] args) {
        SpanishDimensions gen = new SpanishDimensions();
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

        String prefix = LATIN_PREFIXES[rnd.nextInt(LATIN_PREFIXES.length)];
        String middle = MIDDLES[rnd.nextInt(MIDDLES.length)];

        return combine(prefix, middle);
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