package com.redactado.raisu.component.v2.display;

import com.redactado.raisu.component.Component;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface Link extends Component {

    @NotNull
    String label();

    @NotNull
    String url();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.LINK;
    }

    record Impl(@NotNull String label, @NotNull String url) implements Link {}

    static @NotNull Link of(@NotNull String label, @NotNull String url) {
        return new Impl(label, url);
    }
}
