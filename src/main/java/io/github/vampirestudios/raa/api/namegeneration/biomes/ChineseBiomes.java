package io.github.vampirestudios.raa.api.namegeneration.biomes;

import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.utils.Utils;

import java.util.*;

public class ChineseBiomes implements INameGenerator {
    public static final String[] LATIN_PREFIXES = {
            "源", "高", "全", "古", "冠", "典", "捷", "康", "立", "缺", "牙", "反", "又", "自", "出",
            "兼", "使", "下", "内", "或", "近", "美", "否", "布", "阻", "公", "通", "延", "前", "多",
            "加", "尼", "巡", "重", "洪", "朋", "瑟", "塞", "森", "星", "索", "子", "秘", "超", "天",
            "路", "川", "兆", "吉", "外", "托"
    };

    public static final String[] MIDDLES = {
            "奧", "阿姆", "安", "比", "博", "卡", "括", "德", "良", "依", "恩", "佛", "伽", "因", "卡利",
            "里", "洛", "拉", "米", "抹", "孟", "捺", "乃", "挪", "昵", "沃", "歐姆", "玻", "任", "柔", "瑞",
            "西克", "西", "坦", "拓", "三", "四", "武", "西", "兹"
    };

    public static Map<String, String> prefixMap = new HashMap<>(50);
    public static Map<String, String> middleMap = new HashMap<>(39);

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
        prefixMap.put("源", "ab");
        prefixMap.put("高", "ad");
        prefixMap.put("全", "ambi");
        prefixMap.put("古", "ante");
        prefixMap.put("冠", "circum");
        prefixMap.put("典", "co");
        prefixMap.put("接", "com");
        prefixMap.put("康", "con");
        prefixMap.put("立", "contra");
        prefixMap.put("缺", "de");
        prefixMap.put("牙", "den");
        prefixMap.put("反", "dis");
        prefixMap.put("又", "di");
        prefixMap.put("自", "ex");
        prefixMap.put("出", "extra");
        prefixMap.put("兼", "in");
        prefixMap.put("使", "en");
        prefixMap.put("下", "infra");
        prefixMap.put("内", "inter");
        prefixMap.put("或", "intra");
        prefixMap.put("近", "juxta");
        prefixMap.put("美", "me");
        prefixMap.put("否", "ne");
        prefixMap.put("布", "non");
        prefixMap.put("阻", "ob");
        prefixMap.put("公", "ox");
        prefixMap.put("通", "per");
        prefixMap.put("延", "post");
        prefixMap.put("前", "prae");
        prefixMap.put("多", "preter");
        prefixMap.put("加", "pro");
        prefixMap.put("尼", "quasi");
        prefixMap.put("巡", "ques");
        prefixMap.put("重", "re");
        prefixMap.put("洪", "red");
        prefixMap.put("朋", "retro");
        prefixMap.put("瑟", "se");
        prefixMap.put("塞", "sed");
        prefixMap.put("森", "sen");
        prefixMap.put("星", "sin");
        prefixMap.put("索", "sod");
        prefixMap.put("子", "sub");
        prefixMap.put("秘", "subter");
        prefixMap.put("超", "super");
        prefixMap.put("天", "supra");
        prefixMap.put("路", "tran");
        prefixMap.put("川", "trans");
        prefixMap.put("兆", "ult");
        prefixMap.put("吉", "ultra");
        prefixMap.put("外", "out");
        prefixMap.put("托", "outr");

        middleMap.put("奧", "al");
        middleMap.put("阿姆", "am");
        middleMap.put("安", "an");
        middleMap.put("比", "be");
        middleMap.put("博", "bor");
        middleMap.put("卡", "cal");
        middleMap.put("括", "co");
        middleMap.put("德", "de");
        middleMap.put("良", "duo");
        middleMap.put("依", "eth");
        middleMap.put("恩", "en");
        middleMap.put("佛", "for");
        middleMap.put("伽", "gal");
        middleMap.put("因", "in");
        middleMap.put("卡利", "kary");
        middleMap.put("里", "li");
        middleMap.put("洛", "lo");
        middleMap.put("拉", "la");
        middleMap.put("米", "mi");
        middleMap.put("抹", "ma");
        middleMap.put("孟", "mun");
        middleMap.put("捺", "nat");
        middleMap.put("乃", "net");
        middleMap.put("挪", "nor");
        middleMap.put("昵", "nit");
        middleMap.put("沃", "or");
        middleMap.put("歐姆", "om");
        middleMap.put("玻", "per");
        middleMap.put("任", "rhen");
        middleMap.put("柔", "rho");
        middleMap.put("瑞", "ri");
        middleMap.put("西克", "sic");
        middleMap.put("西", "sit");
        middleMap.put("坦", "tan");
        middleMap.put("拓", "tor");
        middleMap.put("三", "tri");
        middleMap.put("四", "vi");
        middleMap.put("武", "w");
        middleMap.put("西", "x");
        middleMap.put("兹", "z");

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

        char bStart = middleMap.get(b).charAt(0);
        char aEnd = prefixMap.get(a).charAt(a.length() - 1);
        // if (bStart == aEnd) b = b.substring(1);
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
    }

}