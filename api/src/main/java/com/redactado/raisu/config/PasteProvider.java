package com.redactado.raisu.config;

import org.jetbrains.annotations.NotNull;

public enum PasteProvider {
    PASTES_DEV(0, "https://pastes.dev"),
    HASTEBIN(1, "https://hastebin.com");

    private final byte id;
    private final String defaultUrl;

    PasteProvider(int id, String defaultUrl) {
        this.id = (byte) id;
        this.defaultUrl = defaultUrl;
    }

    public byte id() {
        return id;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    @NotNull
    public static PasteProvider fromId(byte id) {
        for (PasteProvider provider : values()) {
            if (provider.id == id) return provider;
        }
        throw new IllegalArgumentException("Unknown provider id: " + id);
    }
}
