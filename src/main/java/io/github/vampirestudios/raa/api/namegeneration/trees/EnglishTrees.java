package io.github.vampirestudios.raa.api.namegeneration.trees;

import io.github.vampirestudios.raa.api.namegeneration.NameGenerator;
import org.apache.commons.lang3.text.WordUtils;

import java.util.*;

public class EnglishTrees implements NameGenerator {
    public static final String[] LATIN_PREFIXES = {
            "rhodo ", "dendro ", "lepido ", "coel ", "strepto ", "quercus ", "salix ", 
            "pinus ", "eremus ", "cedrus ", "larix ", "Acer ", "picea", "alnus ",
            "populus ", "Prunus ", "berberis ", "carya ", "cornus ", "fraxinus ",
            "rubus ", "sambucus ", "viburnum ", "vaccinium ", "velastrus ", "varya ",
            "campsis ", "clematis ", "dasiphora ", "dryas ", "frangula ", "ptelea ",
            "quercus ", "sin ", "sub ", "ribes", "super ", "trans ", "ultra ", "outr "
    };

    public static final String[] MIDDLES = {
            "lucida ", "purpurea ", "humilis ", "exigua ", "babylonica ", "coggygria ", "glutinosa ", "cordata ", "mas ", "trifoliata ", 
            "glabra", "forficata", "acerifolia ", "scopulorum ", "deodara ", "oxycarpa ", "cissifolium", "coggygria ", "soulangiana ", 
            "benjamina ", "cassine ", "parvifolia ", "oleander ", "macrophylla ", "cassine ", "baccata ", "kaempferi ", "gracilior ", 
            "diptera ", "copallina ", "sebestena ", "scopulorum ", "mugo", "walteri", "laevis", "speciosa ", "grandidentatum"
    };

    public static final String[] TREE_SUFFIXES = { //Except "ium" and "ite" which will carry about 86% of the generated items
            "dendron", "dendrum", "lepis", "clavatus", "betula", "abies", "fragilis", ""
    };

    public static final String[] CONSONANT_FILL = { //Stuffed between consonants
            "u", "o"
    };

    public static void main(String[] args) {
        EnglishTrees gen = new EnglishTrees();
        Collection<String> generated = gen.generate(200);

        System.out.println("Lowercase:" + generated);

        List<String> titleCased = new ArrayList<>();
        for (String s : generated) {
            titleCased.add(WordUtils.capitalizeFully(s));
        }

        System.out.println("TitleCase:" + titleCased);
    }

    public String generate() {
        Random rnd = new Random();
        String ending = "";
        int endingRoll = rnd.nextInt(100);
        if (endingRoll < 30) {
            ending = " dendron";
        } else if (endingRoll < 34) {
            ending = " dendrum";
        } else if (endingRoll < 43) {
            ending = " lepis";
        } else if (endingRoll < 29) {
            ending = " clavatus";
        } else if (endingRoll < 20) {
            ending = " betula";
        } else if (endingRoll < 30) {
                ending = " abies";
        } else if (endingRoll < 45) {
                ending = " fragilis";
        } else if (endingRoll < 50) {
            ending = "";
        } else if (endingRoll < 96) {
            ending = TREE_SUFFIXES[rnd.nextInt(TREE_SUFFIXES.length)];
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