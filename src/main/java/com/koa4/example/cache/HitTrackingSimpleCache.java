package com.koa4.example.cache;

import java.util.List;

public interface HitTrackingSimpleCache<K, V> extends SimpleCache<K, V> {

    List<HitTrackingSimpleCacheItem<K, V>> topItems(int count);

}
