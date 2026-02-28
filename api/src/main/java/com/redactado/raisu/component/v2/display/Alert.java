package com.redactado.raisu.component.v2.display;

import com.redactado.raisu.component.Component;
import com.redactado.raisu.component.v2.Severity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public interface Alert extends Component {

    @NotNull
    Severity severity();

    @Nullable
    String title();

    @NotNull
    String message();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.ALERT;
    }

    record Impl(@NotNull Severity severity, @Nullable String title, @NotNull String message)
            implements Alert {}

    static @NotNull Alert of(@NotNull Severity severity, @NotNull String message) {
        return new Impl(severity, null, message);
    }

    static @NotNull Alert of(
            @NotNull Severity severity, @NotNull String title, @NotNull String message) {
        return new Impl(severity, title, message);
    }
}
