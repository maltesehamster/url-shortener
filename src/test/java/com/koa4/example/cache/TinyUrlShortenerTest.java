package com.koa4.example.cache;

import com.koa4.example.shortener.TinyUrlShortener;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class TinyUrlShortenerTest {

    @Mock
    private OkHttpClient okHttpClient;

    private TinyUrlShortener unitUnderTest;

    @BeforeEach
    public void setup() {
        unitUnderTest = new TinyUrlShortener(okHttpClient);
    }

    @Test
    public void givenNullUrl_whenShortenUrl_thenThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> unitUnderTest.shortenUrl(null));
    }

}
