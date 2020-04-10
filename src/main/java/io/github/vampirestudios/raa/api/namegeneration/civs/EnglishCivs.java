package io.github.vampirestudios.raa.api.namegeneration.civs;

import io.github.vampirestudios.raa.api.namegeneration.NameGenerator;
import org.apache.commons.lang3.text.WordUtils;

import java.util.*;

public class EnglishCivs implements NameGenerator {
    
    public static final String[] PREFIXES = {
            "ast", "arc", "av", "aan", "arx", "asc", "brad", "cul", "can", "cor", "cas", "dr", "dor", "gar", "eil",
            "fin", "for", "inver", "frost", "hard", "drom", "dom", "dun", "orm", "fal", "rang", "lang", "magna",
            "kal", "kald", "jald", "myndyn", "pen", "nor"
    };

    public static final String[] SUFFIXES = {
            "landia", "umbria", "land", "bury", "bex", "bal", "carden", "caster", "combe", "cul", "dale", "din",
            "dol", "field", "gard", "fell", "ham", "em", "howe", "lyng", "edra", "nor", "stead", "strath", "rigg",
            "tun", "ton", "weald", "wold", "wick", "worth", "warth", "werth", "egar", "ster", "pol", "erro",
            "ruan", "ance", "em", "which", "bourne", "bern", "barne", "ardine", "keth", "eld", "way"
    };

    public static void main(String[] args) {
        EnglishCivs gen = new EnglishCivs();
        Collection<String> generated = gen.generate(100);

        System.out.println("Lowercase:" + generated);

        List<String> titleCased = new ArrayList<>();
        for (String s : generated) {
            titleCased.add(WordUtils.capitalizeFully(s));
        }

        System.out.println("TitleCase:" + titleCased);
    }

    public String generate() {
        Random rnd = new Random();

        String prefix = PREFIXES[rnd.nextInt(PREFIXES.length)].toLowerCase();
        String endings = SUFFIXES[rnd.nextInt(SUFFIXES.length)];

        return combine(prefix, endings);
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
            return a + fillConsonant(aEnd) + b;
        }

        return a + b;
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