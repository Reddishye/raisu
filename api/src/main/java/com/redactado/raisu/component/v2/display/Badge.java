package com.redactado.raisu.component.v2.display;

import com.redactado.raisu.component.Component;
import com.redactado.raisu.component.v2.Severity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface Badge extends Component {

    @NotNull
    String text();

    @NotNull
    Severity severity();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.BADGE;
    }

    record Impl(@NotNull String text, @NotNull Severity severity) implements Badge {}

    static @NotNull Badge of(@NotNull String text) {
        return new Impl(text, Severity.DEFAULT);
    }

    static @NotNull Badge of(@NotNull String text, @NotNull Severity severity) {
        return new Impl(text, severity);
    }
}
