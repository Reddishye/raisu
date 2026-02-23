package com.redactado.raisu.config;

public enum PasteProvider {
    PASTES_DEV("P", "https://pastes.dev"),
    HASTEBIN("H", "https://hastebin.com");

    private final String shortId;
    private final String defaultUrl;

    PasteProvider(String shortId, String defaultUrl) {
        this.shortId = shortId;
        this.defaultUrl = defaultUrl;
    }

    public String shortId() {
        return shortId;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }
}
