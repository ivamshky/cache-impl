package com.github.ivamshky.cache;

public class Node<K, V> {
    private K key;
    private V value;
    Node next;
    Node prev;

    Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K key() {
        return key;
    }

    public V value() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public boolean equals(Node other) {
        return this.key.equals(other.key);
    }
}
