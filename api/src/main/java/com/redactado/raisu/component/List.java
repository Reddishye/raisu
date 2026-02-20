package com.redactado.raisu.component;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface List extends Component {

    @NotNull
    java.util.List<String> items();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.LIST;
    }
}
