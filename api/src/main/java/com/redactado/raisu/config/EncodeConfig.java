package com.redactado.raisu.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EncodeConfig {

    @NotNull
    PasteProvider provider();

    boolean encrypt();

    @Nullable
    String password();

    @NotNull
    static Builder builder() {
        try {
            Class<?> builderClass = Class.forName("com.redactado.raisu.core.config.EncodeConfigBuilderImpl");
            return (Builder) builderClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create EncodeConfig.Builder. Is raisu-core on classpath?", e);
        }
    }

    interface Builder {
        @NotNull
        Builder provider(@NotNull PasteProvider provider);

        @NotNull
        Builder encrypt(boolean encrypt);

        @NotNull
        Builder password(@NotNull String password);

        @NotNull
        EncodeConfig build();
    }
}
