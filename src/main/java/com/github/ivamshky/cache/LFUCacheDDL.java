package com.github.ivamshky.cache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class LFUCacheDDL<K, V> implements Cache<K, V> {

    private int capacity;
    private Map<K, Integer> countMap;
    private TreeMap<Integer, DLL> freqMap;
    private Map<K, Node> cache;

    public LFUCacheDDL(int capacity) {
        this.capacity = capacity;
        freqMap = new TreeMap<Integer, DLL>(Comparator.reverseOrder());
        cache = new HashMap<K, Node>();
        countMap = new HashMap<>();
    }

    @Override
    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }

        Node node = this.cache.get(key);
        int freq = countMap.get(key);
        freqMap.get(freq).remove(node);
        if (freqMap.get(freq).size() == 0) {
            freqMap.remove(freq);
        }
        freqMap.computeIfAbsent(freq + 1, k -> new DLL()).addToLast(node);
        countMap.put(key, freq + 1);
        return (V) node.value();
    }

    @Override
    public void put(K key, V value) {
        if (!cache.containsKey(key)) {
            Node node = new Node(key, value);
            if (cache.size() == capacity) {
                int lowest = freqMap.lastKey();
                Node nodeToDelete = freqMap.get(lowest).head();
                freqMap.get(lowest).remove(nodeToDelete);
                if (freqMap.get(lowest).size() == 0) {
                    freqMap.remove(lowest);
                }
                cache.remove(nodeToDelete.key());
                countMap.remove(nodeToDelete.key());
            }
            cache.put(key, node);
            countMap.put(key, 1);
            freqMap.computeIfAbsent(1, k -> new DLL()).addToLast(node);
        } else {
            Node node = cache.get(key);
            node.setValue(value);
            cache.put(key, node);
            int freq = countMap.get(key);
            freqMap.get(freq).remove(node);
            if (freqMap.get(freq).size() == 0) {
                freqMap.remove(freq);
            }
            freqMap.computeIfAbsent(freq + 1, k -> new DLL()).addToLast(node);
            countMap.put(key, freq + 1);
        }
    }

    public String getCacheState() {
        return countMap.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue()).collect(Collectors.joining(","));
    }

}
