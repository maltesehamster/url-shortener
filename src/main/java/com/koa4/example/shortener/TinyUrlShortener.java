package com.koa4.example.shortener;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class TinyUrlShortener implements UrlShortener {

    private static final String TINYURL_ENDPOINT = "https://tinyurl.com/api-create.php?url=";

    private final OkHttpClient okHttpClient;

    @Autowired
    public TinyUrlShortener(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public UrlShortenerResult shortenUrl(String url) {
        if (url == null || url.trim().length() == 0) {
            throw new IllegalArgumentException("url is required");
        }

        Call call = generateCall(url.trim());
        Response response;
        try {
            response = call.execute();
        }
        catch (Exception ex) {
            return new UrlShortenerResult.Builder()
                    .withErrorMessage("TinyUrlShortener: failed calling TinyUrl: " + ex.getMessage())
                    .build();
        }

        if (response.code() != 200) {
            return new UrlShortenerResult.Builder()
                    .withErrorMessage("TinyUrlShortener: unexpected HTTP status code received from TinyUrl: " + response.code())
                    .build();
        }

        String shortenedUrl;
        try {
            shortenedUrl = response.body().string();
        }
        catch (IOException ex) {
            return new UrlShortenerResult.Builder()
                    .withErrorMessage("TinyUrlShortener: failed reading shortened url from HTTP response body: " + ex.getMessage())
                    .build();
        }

        return new UrlShortenerResult.Builder()
                .withShortenedUrl(shortenedUrl)
                .build();
    }

    private Call generateCall(String url) {
        Request request = new Request.Builder()
                .url(TINYURL_ENDPOINT + url)
                .build();
        return okHttpClient.newCall(request);
    }

}
