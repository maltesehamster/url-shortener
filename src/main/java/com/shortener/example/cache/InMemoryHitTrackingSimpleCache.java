package com.shortener.example.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryHitTrackingSimpleCache<K, V extends Comparable<V>> implements HitTrackingSimpleCache<K, V> {

    private final Map<K, HitTrackingSimpleCacheItem<K, V>> cache = new HashMap<>();

    @Override
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    @Override
    public V get(K key) {
        if (containsKey(key)) {
            cache.get(key).incrementHitCount();
            return cache.get(key).getValue();
        }

        return null;
    }

    @Override
    public void put(K key, V value) {
        if (containsKey(key)) {
            return;
        }
        cache.put(key, new HitTrackingSimpleCacheItem<>(key, value));
    }

    @Override
    public List<HitTrackingSimpleCacheItem<K, V>> topItems(int count) {
        return cache.values().stream()
                .sorted()
                .limit(count)
                .collect(Collectors.toList());
    }

}
