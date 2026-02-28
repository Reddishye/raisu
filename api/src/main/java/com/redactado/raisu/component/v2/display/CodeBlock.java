package com.redactado.raisu.component.v2.display;

import com.redactado.raisu.component.Component;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface CodeBlock extends Component {

    @NotNull
    String content();

    @NotNull
    String language();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.CODE_BLOCK;
    }

    record Impl(@NotNull String content, @NotNull String language) implements CodeBlock {}

    static @NotNull CodeBlock of(@NotNull String content) {
        return new Impl(content, "");
    }

    static @NotNull CodeBlock of(@NotNull String content, @NotNull String language) {
        return new Impl(content, language);
    }
}
