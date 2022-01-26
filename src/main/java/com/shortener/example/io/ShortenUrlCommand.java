package com.shortener.example.io;

public enum ShortenUrlCommand {

    NO_COMMAND(""),
    QUIT("Q"),
    SHORTEN_URL(""),
    TOP("T");

    private final String label;

    ShortenUrlCommand(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
