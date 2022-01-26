package com.shortener.example;

import com.shortener.example.cache.HitTrackingSimpleCache;
import com.shortener.example.cache.HitTrackingSimpleCacheItem;
import com.shortener.example.io.ShortenUrlCommand;
import com.shortener.example.io.ShortenUrlUserInterface;
import com.shortener.example.shortener.UrlShortener;
import com.shortener.example.shortener.UrlShortenerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
@Component
public class UrlShortenerApplication implements CommandLineRunner {

	private static final int TOP_SIZE = 3;

	private final ShortenUrlUserInterface shortenUrlUserInterface;
	private final UrlShortener urlShortener;
	private final HitTrackingSimpleCache<String, String> hitTrackingSimpleCache;

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApplication.class);
	}

	@Autowired
	public UrlShortenerApplication(ShortenUrlUserInterface shortenUrlUserInterface, UrlShortener urlShortener,
			HitTrackingSimpleCache<String, String> hitTrackingSimpleCache) {
		this.shortenUrlUserInterface = shortenUrlUserInterface;
		this.urlShortener = urlShortener;
		this.hitTrackingSimpleCache = hitTrackingSimpleCache;
	}

	@Override
	public void run(String... args) throws Exception {
		shortenUrlUserInterface.init();

		while (true) {
			ShortenUrlCommand shortenUrlCommand = shortenUrlUserInterface.getCommand();
			if (shortenUrlCommand == ShortenUrlCommand.NO_COMMAND) {
				continue;
			}
			if (shortenUrlCommand == ShortenUrlCommand.QUIT) {
				break;
			}
			if (shortenUrlCommand == ShortenUrlCommand.TOP) {
				List<HitTrackingSimpleCacheItem<String, String>> cacheItems = hitTrackingSimpleCache.topItems(TOP_SIZE);
				shortenUrlUserInterface.displayMostFrequentlyRequestedUrls(cacheItems);
				continue;
			}

			shortenUrl();
		}

		shortenUrlUserInterface.destroy();
	}

	private void shortenUrl() {
		String longUrl = shortenUrlUserInterface.getUrlToShorten();

		if (hitTrackingSimpleCache.containsKey(longUrl)) {
			String shortUrl = hitTrackingSimpleCache.get(longUrl);
			shortenUrlUserInterface.reportShortUrl(shortUrl);
		} else {
			UrlShortenerResult result = urlShortener.shortenUrl(longUrl);
			if (result.wasUrlShortenedSuccessfully()) {
				hitTrackingSimpleCache.put(longUrl, result.getShortenedUrl());
				shortenUrlUserInterface.reportShortUrl(result.getShortenedUrl());
			} else {
				shortenUrlUserInterface.reportError(result.getErrorMessage());
			}
		}
	}

}
