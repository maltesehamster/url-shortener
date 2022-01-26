package com.shortener.example.io;

import com.shortener.example.cache.HitTrackingSimpleCacheItem;

import java.util.List;

public interface ShortenUrlUserInterface {

    void init();

    void destroy();

    void displayMostFrequentlyRequestedUrls(List<HitTrackingSimpleCacheItem<String, String>> topCacheItems);

    ShortenUrlCommand getCommand();

    String getUrlToShorten();

    void reportError(String error);

    void reportShortUrl(String shortUrl);

}
