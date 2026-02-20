package com.redactado.raisu.component;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface KeyValue extends Component {

    @NotNull
    String key();

    @NotNull
    String value();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.KEY_VALUE;
    }
}
