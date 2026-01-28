package com.redactado.raisu.core.config;

import com.redactado.raisu.config.EncodeConfig;
import com.redactado.raisu.config.PasteProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class EncodeConfigBuilderImpl implements EncodeConfig.Builder {

    private PasteProvider provider = PasteProvider.HASTEBIN;
    private boolean encrypt = false;

    @Nullable
    private String password = null;

    @Override
    @NotNull
    public EncodeConfig.Builder provider(@NotNull PasteProvider provider) {
        this.provider = provider;
        return this;
    }

    @Override
    @NotNull
    public EncodeConfig.Builder encrypt(boolean encrypt) {
        this.encrypt = encrypt;
        return this;
    }

    @Override
    @NotNull
    public EncodeConfig.Builder password(@NotNull String password) {
        this.password = password;
        this.encrypt = true;
        return this;
    }

    @Override
    @NotNull
    public EncodeConfig build() {
        if (encrypt && password == null) {
            throw new IllegalStateException("Password required when encryption is enabled");
        }
        return new EncodeConfigImpl(provider, encrypt, password);
    }
}
