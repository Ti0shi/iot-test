package com.huitiemev.iot.misc;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.util.*;

public class HashMapOperations {
    @Operation(summary = "Sort the HashMap by value in sorting order")
    @Parameter(name = "map", description = "The HashMap to be sorted")
    public static <K, V extends Comparable<? super V>> HashMap<K, V> sortHashMapByValue(HashMap<K, V> map, boolean ascending) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        if (!ascending) {
            Collections.reverse(list);
        }

        HashMap<K, V> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
