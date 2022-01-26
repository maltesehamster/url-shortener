package com.shortener.example.shortener;

public class UrlShortenerResult {

    private String errorMessage;
    private String shortenedUrl;

    public static class Builder {

        private String errorMessage;
        private String shortenedUrl;

        public Builder withErrorMessage(String errorMessage){
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder withShortenedUrl(String shortenedUrl){
            this.shortenedUrl = shortenedUrl;
            return this;
        }

        public UrlShortenerResult build(){
            UrlShortenerResult urlShortenerResult = new UrlShortenerResult();  //Since the builder is in the BankAccount class, we can invoke its private constructor.
            urlShortenerResult.errorMessage = errorMessage;
            urlShortenerResult.shortenedUrl = shortenedUrl;

            return urlShortenerResult;
        }

    }

    private UrlShortenerResult() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

    public boolean wasUrlShortenedSuccessfully() {
        return errorMessage == null || errorMessage.length() == 0;
    }

}
