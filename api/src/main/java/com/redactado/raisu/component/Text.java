package com.redactado.raisu.component;

import org.jetbrains.annotations.NotNull;

public interface Text extends Component {

    @NotNull
    String content();

    @Override
    default ComponentType type() {
        return ComponentType.TEXT;
    }
}
