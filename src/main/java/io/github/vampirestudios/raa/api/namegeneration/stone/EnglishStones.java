package io.github.vampirestudios.raa.api.namegeneration.stone;

import io.github.vampirestudios.raa.api.namegeneration.NameGenerator;
import io.github.vampirestudios.raa.utils.Utils;

import java.util.*;

public class EnglishStones implements NameGenerator {
    public static final String[] PREFIXES = {
            "vec", "cam", "shar", "sle", "varm", "met", "they", "canda", "vorm", "asg", "arg", "ray", "ans", "jun", "tre",
            "imp", "thresh", "maj", "sal", "prot", "pro", "aster", "phaist", "pha", "xal", "syt", "lun", "fer", "zar", "zat",
            "log", "sharr", "par", "vin", "ven", "vorc", "tio", "tin", "nep", "vey", "veyr", "ver", "non", "mai", "pendr",
            "pend", "pen", "heb", "lin", "et", "at", "nem", "mag", "mang", "magn", "tung", "ont", "cor", "ses", "farm",
            "farn", "far", "sup", "nod", "alk", "ak", "akl", "cit", "ag", "ren", "surt", "sak", "nam", "mask", "karum", "kar",
            "kob", "unt", "zesmen", "zes", "ac", "ak", "rel", "tal", "ter", "volk", "kom", "t", "cet", "derelic", "ur", "aig",
            "hit", "sol", "mar", "nerc", "aequit", "bann", "takk", "alch", "kor", "lor", "lok", "bal", "pheir"
    };

    public static final String[] SUFFIXES = {
            "adel", "ae", "nus", "ury", "mis", "one", "aeton", "uto", "er", "per", "asi", "ius", "ros", "eria", "tra",
            "aron", "kon", "ton", "nok", "ashi", "alus", "gos", "rum", "unthor", "ingri", "elrus", "rux", "erux",
            "anban", "ban", "crum", "gir", "bin", "baar", "malrus", "salra", "geuse", "arlon", "inor", "iror",
            "iron", "iroc", "aroc", "oroc", "eus", "ros", "ages", "alca", "olus", "oluis", "anre", "orron", "ania",
            "ana", "gan", "an", "ur", "u","iter", "aser", "azer", "acer", "aver", "au", "landia", "umbria", "land",
            "bury", "bex", "bal","carden", "caster", "combe", "cul", "dale", "din", "dol", "field", "gard", "fell",
            "ham", "em", "howe","lyng", "edra", "nor", "stead", "strath", "rigg", "tun", "ton", "weald", "wold",
            "wick", "worth","warth", "werth", "egar", "ster", "pol", "erro", "ruan", "ance", "em", "which", "bourne",
            "bern","barne", "ardine", "keth", "eld", "ae", "nus", "ury", "mis", "one", "aeton", "uto", "er", "per",
            "asi","ius", "ros", "eria", "tra", "aron", "kon", "ton", "nok", "ashi", "alus", "gos", "rum", "unthor",
            "ingri","elrus", "rux", "erux", "anban", "ban", "crum", "gir", "bin", "baar", "malrus", "salra", "geuse",
            "arlon","inor", "eus", "ros", "ages", "alca", "olus", "oluis", "anre", "orron", "ania", "ana", "gan", "an",
            "adu","oba", "idor", "udor", "edor", "armus", "arma", "arden", "emar", "agon", "ito", "ali", "ao", "sah",
            "alis","eles", "win", "ungel", "atol", "aka", "vir", "i", "rim", "a", "urn"
    };

    public static void main(String[] args) {
        EnglishStones gen = new EnglishStones();
        Collection<String> generated = gen.generate(200);

        System.out.println("Lowercase:" + generated);

        List<String> titleCased = new ArrayList<>();
        for (String s : generated) {
            titleCased.add(Utils.toTitleCase(s));
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