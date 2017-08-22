package gambol.examples.guava;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenbao.zhou on 17/7/20.
 */
public class MapTest {
    public static void main(String[] args) {

        Map<String,String> map = new HashMap<String, String>();
        map.put("3","33");
        map.put("1","a");
        map.put("2","b");
        map.put("4","t");
        System.out.println("========原生Map=======");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        Map<String,String> guavaMap = Maps.newHashMap();
        guavaMap.put("3","c");
        guavaMap.put("1","a");
        guavaMap.put("2","b");
        guavaMap.put("5","t");
        System.out.println("========Guava Map=======");
        for (Map.Entry<String, String> entry : guavaMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        MapDifference<String,String> diffMap = Maps.difference(map,guavaMap);
        System.out.println("========Guava diff Map=======");
        for (Map.Entry<String, String> entry : diffMap.entriesInCommon().entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("========Guava diff Left Map=======");
        for (Map.Entry<String, String> entry : diffMap.entriesOnlyOnLeft().entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("========Guava diff Right Map=======");
        for (Map.Entry<String, String> entry : diffMap.entriesOnlyOnRight().entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        System.out.println("========Guava diff  Map=======");
        for (Map.Entry<String, MapDifference.ValueDifference<String>> entry : diffMap.entriesDiffering().entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

//        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
//        Map<String, Integer> right = ImmutableMap.of("a", 1, "b", 2, "c", 3);
//        MapDifference<String, Integer> diff = Maps.difference(left, right);
//        Map<String,Integer> map2 = diff.entriesInCommon();
//        System.out.println("========Guava diff Map=======");
//        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }


    }
}
