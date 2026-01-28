package com.redactado.raisu.core.component;

import com.redactado.raisu.component.KeyValue;
import org.jetbrains.annotations.NotNull;

public final class KeyValueImpl implements KeyValue {

    private final String key;
    private final String value;

    public KeyValueImpl(@NotNull String key, @NotNull String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    @NotNull
    public String key() {
        return key;
    }

    @Override
    @NotNull
    public String value() {
        return value;
    }
}
