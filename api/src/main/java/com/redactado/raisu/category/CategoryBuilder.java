package com.redactado.raisu.category;

import com.redactado.raisu.component.Component;
import org.jetbrains.annotations.NotNull;

public interface CategoryBuilder {

    @NotNull
    CategoryBuilder id(@NotNull String id);

    @NotNull
    CategoryBuilder name(@NotNull net.kyori.adventure.text.Component name);

    @NotNull
    CategoryBuilder icon(@NotNull String icon);

    @NotNull
    CategoryBuilder priority(int priority);

    @NotNull
    CategoryBuilder add(@NotNull Component component);

    @NotNull
    Category build();
}
