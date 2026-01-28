package com.redactado.raisu.component;

import org.jetbrains.annotations.NotNull;

public interface KeyValue extends Component {

    @NotNull
    String key();

    @NotNull
    String value();

    @Override
    default ComponentType type() {
        return ComponentType.KEY_VALUE;
    }
}
