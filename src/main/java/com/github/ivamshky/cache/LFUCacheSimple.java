package com.github.ivamshky.cache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class LFUCacheSimple<K, V> implements Cache<K, V> {

    private int capacity;
    private Map<K, Integer> countMap;
    private TreeMap<Integer, List<K>> freqMap;
    private Map<K, V> cache;

    public LFUCacheSimple(int capacity) {
        this.capacity = capacity;
        freqMap = new TreeMap<Integer, List<K>>(Comparator.reverseOrder());
        cache = new HashMap<K, V>();
        countMap = new HashMap<>();
    }

    @Override
    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }

        V value = this.cache.get(key);
        int freq = countMap.get(key);
        freqMap.get(freq).removeIf(k -> key.equals(k));
        if (freqMap.get(freq).size() == 0) {
            freqMap.remove(freq);
        }
        freqMap.computeIfAbsent(freq + 1, k -> new LinkedList<>()).add(key);
        countMap.put(key, freq + 1);
        return value;
    }

    @Override
    public void put(K key, V value) {
        if (!cache.containsKey(key)) {
            if (cache.size() == capacity) {
                int lowest = freqMap.lastKey();
                K keyToDelete = freqMap.get(lowest).remove(0);
                if (freqMap.get(lowest).size() == 0) {
                    freqMap.remove(lowest);
                }
                cache.remove(keyToDelete);
                countMap.remove(keyToDelete);
            }
            cache.put(key, value);
            countMap.put(key, 1);
            freqMap.computeIfAbsent(1, k -> new LinkedList<>()).add(key);
        } else {
            cache.put(key, value);
            int freq = countMap.get(key);
            freqMap.get(freq).removeIf(k -> key.equals(k));
            if (freqMap.get(freq).size() == 0) {
                freqMap.remove(freq);
            }
            freqMap.computeIfAbsent(freq + 1, k -> new LinkedList<>()).add(key);
            countMap.put(key, freq + 1);
        }
    }

    public String getCacheState() {
        return countMap.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue()).collect(Collectors.joining(","));
    }

}
