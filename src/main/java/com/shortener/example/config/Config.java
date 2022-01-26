package com.shortener.example.config;

import com.shortener.example.cache.HitTrackingSimpleCache;
import com.shortener.example.cache.InMemoryHitTrackingSimpleCache;
import com.shortener.example.io.ShortenUrlCommandLineUserInterface;
import com.shortener.example.io.ShortenUrlUserInterface;
import com.shortener.example.shortener.TinyUrlShortener;
import com.shortener.example.shortener.UrlShortener;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class Config {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder().build();
    }

    @Bean
    public ShortenUrlUserInterface shortenUrlUserInterface() {
        return new ShortenUrlCommandLineUserInterface(
                new Scanner(System.in)::nextLine,
                System.out::println,
                () -> System.exit(0)
        );
    }

    @Bean
    public HitTrackingSimpleCache<String, String> hitTrackingSimpleCache() {
        return new InMemoryHitTrackingSimpleCache<>();
    }

    @Bean
    public UrlShortener urlShortener(OkHttpClient okHttpClient) {
        return new TinyUrlShortener(okHttpClient);
    }

}
