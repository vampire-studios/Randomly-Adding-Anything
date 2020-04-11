package io.github.vampirestudios.raa.api.namegeneration.civs;

import io.github.vampirestudios.raa.api.namegeneration.NameGenerator;
import org.apache.commons.lang3.text.WordUtils;

import java.util.*;

public class FrenchCivs implements NameGenerator {
    
    public static final String[] PREFIXES = {
            "neus","aus","austra","sou","soua","bre","bour","a","aqui","pro","proven","va","vasco",
            "fran","gas","gasco","ga","nor","norman","flan","go","an","lor","lo","lorrain",
            "cham","champa","main","ma","mar","pé","pér","péri","rouer","au","ver","auver","fo",
            "ge","gev","gevau","ver","verman"
    };

    public static final String[] SUFFIXES = {
            "trie","trasie","be","tons","gogne","gne","tain","taine","vence","ce","nie","sconie",
            "scogne","die","mandie","dre","thie","jou","e","rain","raine","pagne","che","bon","gord",
            "gue","rez","dan","vaudan","ange","dois","mandois"
    };

    public static void main(String[] args) {
        FrenchCivs gen = new FrenchCivs();
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
            case 'é':
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
        Map<String, String> map = new HashMap<>();
        map.put("é", "e");
        return map;
    }

}