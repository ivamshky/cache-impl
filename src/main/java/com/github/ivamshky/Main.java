package com.github.ivamshky;

import com.github.ivamshky.cache.Cache;
import com.github.ivamshky.cache.LFUCacheDDL;
import com.github.ivamshky.cache.LRUCache;

public class Main {
    public static void main(String[] args) {
        Cache<String, String> cache = new LRUCache<>(5);
        cache.put("a", "A");
        cache.put("b", "B");
        cache.put("c", "C");
        cache.put("d", "D");
        cache.put("e", "E");

        System.out.println("e: " + cache.get("e"));
        System.out.println("b: " + cache.get("b"));
        System.out.println("c: " + cache.get("c"));

        System.out.println("c: " + cache.get("c"));

        cache.put("c", "C2");
        cache.put("d", "D2");

        System.out.println("current state: " + cache.getCacheState());

        cache.put("f", "F");
        cache.put("g", "G");
        System.out.println("current state: " + cache.getCacheState());
    }
}
