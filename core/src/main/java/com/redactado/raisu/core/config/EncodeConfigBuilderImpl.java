package com.redactado.raisu.core.config;

import com.redactado.raisu.config.EncodeConfig;
import com.redactado.raisu.config.PasteProvider;
import org.jetbrains.annotations.NotNull;

public final class EncodeConfigBuilderImpl implements EncodeConfig.Builder {

    private PasteProvider provider = PasteProvider.HASTEBIN;

    @Override
    @NotNull
    public EncodeConfig.Builder provider(@NotNull PasteProvider provider) {
        this.provider = provider;
        return this;
    }

    @Override
    @NotNull
    public EncodeConfig build() {
        return new EncodeConfigImpl(provider);
    }
}
