package com.redactado.raisu.component;

import org.jetbrains.annotations.NotNull;

public interface Component {

    @NotNull
    ComponentType type();

    enum ComponentType {
        KEY_VALUE,
        TEXT,
        TABLE,
        LIST,
        PROGRESS_BAR,
        GRAPH,
        TREE
    }
}
