package com.shortener.example.cache;

public class HitTrackingSimpleCacheItem<K, V> implements Comparable<HitTrackingSimpleCacheItem<K, V>> {

    private final K key;
    private final V value;
    private int hitCount = 1;

    public HitTrackingSimpleCacheItem(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void incrementHitCount() {
        this.hitCount++;
    }

    @Override
    public int compareTo(HitTrackingSimpleCacheItem<K, V> o) {
        return -Integer.compare(this.getHitCount(), o.getHitCount());
    }
}
