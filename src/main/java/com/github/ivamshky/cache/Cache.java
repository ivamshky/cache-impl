package com.github.ivamshky.cache;

public interface Cache<K, V> {

    public void put(K key, V value);

    public V get(K key);

    public String getCacheState();
}
