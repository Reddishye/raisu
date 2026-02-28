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
        TREE,
        // layout
        COLUMN,
        ROW,
        GRID,
        PANEL,
        // display v2
        BADGE,
        STAT,
        ALERT,
        CODE_BLOCK,
        LOG_VIEW,
        TIMELINE,
        SPARKLINE,
        GAUGE,
        LINK
    }
}
