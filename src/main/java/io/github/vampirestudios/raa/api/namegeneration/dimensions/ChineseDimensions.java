package io.github.vampirestudios.raa.api.namegeneration.dimensions;

import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.api.namegeneration.biomes.ChineseBiomes;
import io.github.vampirestudios.raa.utils.Utils;

import java.util.*;

public class ChineseDimensions implements INameGenerator {
    public static final String[] PREFIXES = {
            "源", "高", "全", "古", "冠", "典", "捷", "康", "立", "缺", "牙", "反", "又", "自", "出",
            "兼", "使", "下", "内", "或", "近", "美", "否", "布", "阻", "公", "通", "延", "前", "多",
            "加", "尼", "巡", "重", "洪", "朋", "瑟", "塞", "森", "星", "索", "子", "秘", "超", "天",
            "路", "川", "兆", "吉", "外", "托"
    };

    public static final String[] SUFFIXES = {
            "奧", "阿姆", "安", "比", "博", "卡", "括", "德", "良", "依", "恩", "佛", "伽", "因", "卡利",
            "里", "洛", "拉", "米", "抹", "孟", "捺", "乃", "挪", "昵", "沃", "歐", "玻", "任", "柔", "瑞",
            "西克", "西", "坦", "拓", "三", "四", "武", "西", "兹"
    };

    public static final String[] MIDDLES = {
    		"阿", "伊", "偶", "厄", "巴", "茨", "滋", "伺", "置", "蚩", "什", "珀", "摩", "珐", "迪",
    		"挞", "嚢", "喇", "咕", "喀", "吽", "己", "琦", "希"
    };

    public static void main(String[] args) {
        ChineseDimensions gen = new ChineseDimensions();
        Collection<String> generated = gen.generate(100);

        System.out.println("Chinese:" + generated);
        
        List<String> generated_identifier = new ArrayList<>();
        for (String s : generated) {
        	String id = s;
        	
            ChineseDimensions dimensions = new ChineseDimensions();
            Map<String, String> specialCharacters = dimensions.getSpecialCharactersMap();
            SortedMap<String, String> specialCharactersSorted = dimensions.getSpecialCharactersMapSorted();
            if (specialCharacters != null) {
                for (Map.Entry<String, String> specialCharacter : specialCharacters.entrySet()) {
                    id = id.replace(specialCharacter.getKey(), specialCharacter.getValue());
                }
            }
            if (specialCharactersSorted != null) {
                for (Map.Entry<String, String> specialCharacter : specialCharactersSorted.entrySet()) {
                    id = id.replace(specialCharacter.getKey(), specialCharacter.getValue());
                }
            }
            id = id.toLowerCase(Locale.ENGLISH);
            generated_identifier.add(id);
            
        }
        System.out.println("Identifier:" + generated_identifier);
    }

    public String generate() {
        Random rnd = new Random();

        String prefix = PREFIXES[rnd.nextInt(PREFIXES.length)];
        String middle = MIDDLES[rnd.nextInt(MIDDLES.length)];
        String suffix = SUFFIXES[rnd.nextInt(SUFFIXES.length)];

        return combine(prefix, middle, suffix);
    }

    public Collection<String> generate(int count) {
        HashSet<String> result = new HashSet<>(count);
        while (result.size() < count) {
            String cur = generate();
            result.add(cur); //Nothing happens on duplicates
        }

        return result;
    }

    public String combine(String a, String b, String c) {
        return a + b + c;
    }

    @Override
    public SortedMap<String, String> getSpecialCharactersMapSorted() {
        SortedMap<String, String> map = new TreeMap<>(new Comparator<String>() {
        	public int compare(String a, String b) {
        		if (a.equals(b)) {
        			return 0;
        		}
        		if (a.length() == b.length()) {
        			return -1;
        		}
        		return a.length() > b.length() ? -1 : 1;
        	}
        });
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
        map.put("卡", "ka");
        map.put("利", "ry");
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
        map.put("歐", "om");
        map.put("玻", "per");
        map.put("任", "rhen");
        map.put("柔", "rho");
        map.put("瑞", "ri");
        map.put("克", "c");
        map.put("西", "sit");
        map.put("坦", "tan");
        map.put("拓", "tor");
        map.put("三", "tri");
        map.put("四", "vi");
        map.put("武", "w");
        map.put("西", "x");
        map.put("兹", "z");
        map.put("阿", "a");
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
        return map;
    }

    @Override
    public Map<String, String> getSpecialCharactersMap() {
        return new HashMap<>();
    }
}