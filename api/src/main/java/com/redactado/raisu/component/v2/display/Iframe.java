package com.redactado.raisu.component.v2.display;

import com.redactado.raisu.component.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public interface Iframe extends Component {

    @NotNull
    String url();

    @Nullable
    String title();

    /** Height in pixels. Default: 400. */
    int height();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.IFRAME;
    }

    record Impl(@NotNull String url, @Nullable String title, int height) implements Iframe {}

    static @NotNull Iframe of(@NotNull String url) {
        return new Impl(url, null, 400);
    }

    static @NotNull Iframe of(@NotNull String url, @NotNull String title) {
        return new Impl(url, title, 400);
    }

    static @NotNull Iframe of(@NotNull String url, @NotNull String title, int height) {
        return new Impl(url, title, height);
    }
}
