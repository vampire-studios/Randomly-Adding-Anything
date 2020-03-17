package io.github.vampirestudios.raa.api.namegeneration.material;

import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.utils.Utils;

import java.util.*;

public class ChineseMaterials implements INameGenerator {
    public static final String[] PREFIXES = {
            "源", "高", "全", "古", "冠", "典", "捷", "康", "立", "缺", "牙", "反", "又", "自", "出",
            "兼", "使", "下", "内", "或", "近", "美", "否", "布", "阻", "公", "通", "延", "前", "多",
            "加", "尼", "巡", "重", "洪", "朋", "瑟", "塞", "森", "星", "索", "子", "秘", "超", "天",
            "路", "川", "兆", "吉", "托"
    };

    public static final String[] MIDDLES = {
            "奧", "阿姆", "安", "比", "博", "卡", "括", "德", "良", "依", "恩", "佛", "伽", "因", "卡利",
            "里", "洛", "拉", "米", "抹", "孟", "捺", "乃", "挪", "昵", "沃", "歐姆", "玻", "任", "柔", "瑞",
            "西克", "西", "坦", "拓", "三", "四", "武", "西", "兹"
    };

    public static final String[] ORE_SUFFIXES = { //Except "ium" and "ite" which will carry about 86% of the generated items
            "姆", "悟勒", "楹", "濛", "埃斯", "伊素", "偶素", "埃勒", "埃特", "安德", "安特", "尹", "艾素", "克斯"
    };

    public static void main(String[] args) {
        ChineseMaterials gen = new ChineseMaterials();
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
            ending = "艾素";
        } else if (endingRoll < 34) {
            ending = "埃司";
        } else if (endingRoll < 43) {
            ending = "素";
        } else if (endingRoll < 69) {
            ending = "伊克斯";
        } else if (endingRoll < 90) {
            ending = "伊特";
        } else if (endingRoll < 96) {
            ending = ORE_SUFFIXES[rnd.nextInt(ORE_SUFFIXES.length)];
        }

        String prefix = PREFIXES[rnd.nextInt(PREFIXES.length)];

        if (prefix.length() + ending.length() < 5 || prefix.equals("超") || rnd.nextInt(3) < 2) {
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
        return a + b;
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
        map.put("捷", "com");
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
        map.put("阿", "a");
        map.put("伊素", "ile");
        map.put("伊克斯", "ix");
        map.put("伊特", "ite");
        map.put("伊", "i");
        map.put("偶", "o");
        map.put("厄", "e");
        map.put("巴", "aba");
        map.put("茨", "itsi");
        map.put("滋", "izi");
        map.put("伺", "isi");
        map.put("置", "edge");
        map.put("蚩", "ichi");
        map.put("什", "ishi");
        map.put("珀", "opo");
        map.put("摩", "omo");
        map.put("珐", "afa");
        map.put("迪", "edi");
        map.put("挞", "ata");
        map.put("嚢", "ana");
        map.put("喇", "ala");
        map.put("咕", "ugu");
        map.put("喀", "aka");
        map.put("吽", "oho");
        map.put("己", "izy");
        map.put("琦", "itsi");
        map.put("希", "isy");
        map.put("姆", "um");
        map.put("悟勒", "ule");
        map.put("楹", "ion");
        map.put("濛", "ment");
        map.put("埃斯", "icle");
        map.put("偶素", "ole");
        map.put("埃勒", "ule");
        map.put("埃特", "ate");
        map.put("安德", "and");
        map.put("安特", "ant");
        map.put("尹", "yn");
        map.put("艾素", "ice");
        map.put("克斯", "ixe");
        map.put("艾司", "ise");
        map.put("素", "ium");
        return map;
    }


}