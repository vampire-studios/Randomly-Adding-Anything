package io.github.vampirestudios.raa.api.namegeneration.biomes;

import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.utils.Utils;

import java.util.*;

public class ChineseBiomes implements INameGenerator {
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

    public static void main(String[] args) {
        ChineseBiomes gen = new ChineseBiomes();
        Collection<String> generated = gen.generate(100);

        System.out.println("Lowercase:" + generated);

        //List<String> titleCased = new ArrayList<>();
        //for (String s : generated) {
        //    titleCased.add(Utils.toTitleCase(s));
        //}

        //System.out.println("TitleCase:" + titleCased);
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
        Map<String, String> map = new HashMap<>();
        map.put("源", "ab");
        map.put("高", "ad");
        map.put("全", "ambi");
        map.put("古", "ante");
        map.put("冠", "circum");
        map.put("典", "co");
        map.put("接", "com");
        map.put("康", "con");
        map.put("立", "contra");
        map.put("缺", "de");
        map.put("牙", "den");
        map.put("反", "dis");
        map.put("又", "di");
        map.put("自", "ex");
        map.put("出", "extra");
        map.put("兼", "in");
        map.put("使", "en");
        map.put("下", "infra");
        map.put("内", "inter");
        map.put("或", "intra");
        map.put("近", "juxta");
        map.put("美", "me");
        map.put("否", "ne");
        map.put("布", "non");
        map.put("阻", "ob");
        map.put("公", "ox");
        map.put("通", "per");
        map.put("延", "post");
        map.put("前", "prae");
        map.put("多", "preter");
        map.put("加", "pro");
        map.put("尼", "quasi");
        map.put("巡", "ques");
        map.put("重", "re");
        map.put("洪", "red");
        map.put("朋", "retro");
        map.put("瑟", "se");
        map.put("塞", "sed");
        map.put("森", "sen");
        map.put("星", "sin");
        map.put("索", "sod");
        map.put("子", "sub");
        map.put("秘", "subter");
        map.put("超", "super");
        map.put("天", "supra");
        map.put("路", "tran");
        map.put("川", "trans");
        map.put("兆", "ult");
        map.put("吉", "ultra");
        map.put("外", "out");
        map.put("托", "outr");
        map.put("奧", "al");
        map.put("阿姆", "am");
        map.put("安", "an");
        map.put("比", "be");
        map.put("博", "bor");
        map.put("卡", "cal");
        map.put("括", "co");
        map.put("德", "de");
        map.put("良", "duo");
        map.put("依", "eth");
        map.put("恩", "en");
        map.put("佛", "for");
        map.put("伽", "gal");
        map.put("因", "in");
        map.put("卡利", "kary");
        map.put("里", "li");
        map.put("洛", "lo");
        map.put("拉", "la");
        map.put("米", "mi");
        map.put("抹", "ma");
        map.put("孟", "mun");
        map.put("捺", "nat");
        map.put("乃", "net");
        map.put("挪", "nor");
        map.put("昵", "nit");
        map.put("沃", "or");
        map.put("歐姆", "om");
        map.put("玻", "per");
        map.put("任", "rhen");
        map.put("柔", "rho");
        map.put("瑞", "ri");
        map.put("西克", "sic");
        map.put("西", "sit");
        map.put("坦", "tan");
        map.put("拓", "tor");
        map.put("三", "tri");
        map.put("四", "vi");
        map.put("武", "w");
        map.put("西", "x");
        map.put("兹", "z");
        return map;
    }

}