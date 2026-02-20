package com.redactado.raisu.component;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface Text extends Component {

    @NotNull
    String content();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.TEXT;
    }
}
