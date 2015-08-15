package org.archvile.util;

import java.util.*;

public class ListUtil {

    public static <T> T getRandomItemFromList(List<T> list) {
        if (list == null || list.isEmpty()) return null;
        return list.get(NumberUtil.getRandomIntFromZeroTo(list.size() - 1));
    }

    public static <T> T getRandomItemFromArray(T[] array) {
        if (array == null || array.length == 0) return null;
        return getRandomItemFromList(Arrays.asList(array));
    }

    public static <T> T getRandomItemFromSet(Set<T> set) {
        if (set == null || set.isEmpty()) return null;

        int index = 0;
        int stopAtIndex = NumberUtil.getRandomIntFromZeroTo(set.size()-1);

        for (T t : set) {
            if (index == stopAtIndex) {
                return t;
            }
            index++;
        }

        return null;
    }

    public static <K,V> V getRandomEntryFromMap(Map<K,V> map) {
        if (map == null || map.isEmpty()) return null;
        return map.get(getRandomItemFromSet(map.keySet()));
    }

    public static long[] getArrayOfLongsFromList(List<Long> list) {
        long[] array = new long[list.size()];
        for (int i=0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
