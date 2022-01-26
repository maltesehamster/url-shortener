package com.shortener.example.cache;

public interface SimpleCache<K, V> {

    boolean containsKey(K key);

    V get(K key);

    void put(K key, V value);

}
