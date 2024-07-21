package com.github.ivamshky.cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V> {

    private Map<K, Node> cache;
    private int capacity;
    private DLL dll;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        dll = new DLL();
    }

    @Override
    public void put(K key, V value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.setValue(value);
            dll.moveToFront(node);
        } else {
            Node node = new Node<K, V>(key, value);
            if (dll.size() == capacity) {
                Node toDelete = dll.removeLast();
                cache.remove(toDelete.key());
            }
            dll.addToFront(node);
            cache.put(key, node);
        }
    }

    @Override
    public V get(K key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            dll.moveToFront(node);
            return (V) node.value();
        } else {
            return null;
        }
    }

    @Override
    public String getCacheState() {
        StringBuilder strBuilder = new StringBuilder();
        Node tmp = dll.head();
        while (tmp != null) {
            strBuilder.append(tmp.key() + ",");
            tmp = tmp.next;
        }
        return strBuilder.toString();
    }

}
