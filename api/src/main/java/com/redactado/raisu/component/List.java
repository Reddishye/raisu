package com.redactado.raisu.component;

import org.jetbrains.annotations.NotNull;

public interface List extends Component {

    @NotNull
    java.util.List<String> items();

    @Override
    default ComponentType type() {
        return ComponentType.LIST;
    }
}
