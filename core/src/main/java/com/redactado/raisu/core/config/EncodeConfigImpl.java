package com.redactado.raisu.core.config;

import com.redactado.raisu.config.EncodeConfig;
import com.redactado.raisu.config.PasteProvider;
import org.jetbrains.annotations.NotNull;

public final class EncodeConfigImpl implements EncodeConfig {

    private final PasteProvider provider;

    public EncodeConfigImpl(@NotNull PasteProvider provider) {
        this.provider = provider;
    }

    @Override
    @NotNull
    public PasteProvider provider() {
        return provider;
    }
}
