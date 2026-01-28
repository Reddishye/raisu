package com.redactado.raisu.core.config;

import com.redactado.raisu.config.EncodeConfig;
import com.redactado.raisu.config.PasteProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class EncodeConfigImpl implements EncodeConfig {

    private final PasteProvider provider;
    private final boolean encrypt;

    @Nullable
    private final String password;

    public EncodeConfigImpl(@NotNull PasteProvider provider, boolean encrypt, @Nullable String password) {
        this.provider = provider;
        this.encrypt = encrypt;
        this.password = password;
    }

    @Override
    @NotNull
    public PasteProvider provider() {
        return provider;
    }

    @Override
    public boolean encrypt() {
        return encrypt;
    }

    @Override
    @Nullable
    public String password() {
        return password;
    }
}
