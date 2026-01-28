package com.redactado.raisu.core.category;

import com.redactado.raisu.category.Category;
import com.redactado.raisu.category.CategoryBuilder;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class CategoryBuilderImpl implements CategoryBuilder {

    private String id = "unknown";
    private net.kyori.adventure.text.Component name = net.kyori.adventure.text.Component.text("Unknown");
    private String icon = "📦";
    private int priority = 0;
    private final List<com.redactado.raisu.component.Component> components = new ArrayList<>();

    @Override
    @NotNull
    public CategoryBuilder id(@NotNull String id) {
        this.id = id;
        return this;
    }

    @Override
    @NotNull
    public CategoryBuilder name(@NotNull net.kyori.adventure.text.Component name) {
        this.name = name;
        return this;
    }

    @Override
    @NotNull
    public CategoryBuilder icon(@NotNull String icon) {
        this.icon = icon;
        return this;
    }

    @Override
    @NotNull
    public CategoryBuilder priority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    @NotNull
    public CategoryBuilder add(@NotNull com.redactado.raisu.component.Component component) {
        this.components.add(component);
        return this;
    }

    @Override
    @NotNull
    public Category build() {
        return new CategoryImpl(id, name, icon, priority, components);
    }
}
