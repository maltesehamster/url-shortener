package com.koa4.example.config;

import com.koa4.example.cache.HitTrackingSimpleCache;
import com.koa4.example.cache.InMemoryHitTrackingSimpleCache;
import com.koa4.example.io.ShortenUrlCommandLineUserInterface;
import com.koa4.example.io.ShortenUrlUserInterface;
import com.koa4.example.shortener.TinyUrlShortener;
import com.koa4.example.shortener.UrlShortener;
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
