package com.shortener.example.io;

import com.shortener.example.cache.HitTrackingSimpleCacheItem;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ShortenUrlCommandLineUserInterface implements ShortenUrlUserInterface {

    private final Supplier<String> in;
    private final Consumer<String> out;
    private final Runnable exit;
    private String urlToShorten;

    public ShortenUrlCommandLineUserInterface(Supplier<String> in, Consumer<String> out, Runnable exit) {
        this.in = in;
        this.out = out;
        this.exit = exit;
    }

    @Override
    public void init() {
        out.accept("\nWelcome to the URL Shortener!");
    }

    @Override
    public void destroy() {
        out.accept("\nGoodbye!");
        exit.run();
    }

    @Override
    public void displayMostFrequentlyRequestedUrls(List<HitTrackingSimpleCacheItem<String, String>> topCacheItems) {
        if (topCacheItems.size() == 0) {
            out.accept("You have not yet shortened any URLs.");
        }
        else {
            out.accept("Most frequently-requested urls:");
            topCacheItems.forEach(cacheItem -> out.accept(cacheItem.getKey() + " => " + cacheItem.getValue() + " (" + cacheItem.getHitCount() + ")"));
        }
    }

    @Override
    public ShortenUrlCommand getCommand() {
        out.accept("\nPlease enter a URL to shorten (or " + ShortenUrlCommand.QUIT.getLabel() + " to Quit, or " + ShortenUrlCommand.TOP.getLabel() + " to see the most frequently-requested urls)");
        String command = in.get();
        if (command == null || command.length() == 0) {
            out.accept("No command entered.");
            return ShortenUrlCommand.NO_COMMAND;
        }
        if (isQuit(command)) {
            return ShortenUrlCommand.QUIT;
        }
        if (isPrintTop(command)) {
            return ShortenUrlCommand.TOP;
        }
        urlToShorten = command;
        return ShortenUrlCommand.SHORTEN_URL;
    }

    @Override
    public String getUrlToShorten() {
        return urlToShorten;
    }

    @Override
    public void reportError(String error) {
        out.accept("Failed getting short url.  Error message: " + error);
    }

    @Override
    public void reportShortUrl(String shortUrl) {
        out.accept("Short url: " + shortUrl);
    }

    private boolean isQuit(String input) {
        return input.equalsIgnoreCase(ShortenUrlCommand.QUIT.getLabel());
    }

    private boolean isPrintTop(String input) {
        return input.equalsIgnoreCase(ShortenUrlCommand.TOP.getLabel());
    }

}
