package com.redactado.raisu.core.component;

import com.redactado.raisu.component.Text;
import org.jetbrains.annotations.NotNull;

public final class TextImpl implements Text {

    private final String content;

    public TextImpl(@NotNull String content) {
        this.content = content;
    }

    @Override
    @NotNull
    public String content() {
        return content;
    }
}
