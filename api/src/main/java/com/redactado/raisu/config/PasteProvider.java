package com.redactado.raisu.config;

public enum PasteProvider {
    PASTES_DEV("https://pastes.dev"),
    HASTEBIN("https://hastebin.com");

    private final String defaultUrl;

    PasteProvider(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }
}
