package com.redactado.raisu.core.component;

import com.redactado.raisu.component.List;
import java.util.ArrayList;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;

public final class ListImpl implements List {

    private final java.util.List<String> items;

    public ListImpl(@NotNull java.util.List<String> items) {
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
    }

    @Override
    @NotNull
    public java.util.List<String> items() {
        return items;
    }
}
