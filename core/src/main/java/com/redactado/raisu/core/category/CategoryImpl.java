package com.redactado.raisu.core.category;

import com.redactado.raisu.category.Category;
import com.redactado.raisu.component.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class CategoryImpl implements Category {

    private final String id;
    private final net.kyori.adventure.text.Component name;
    private final String icon;
    private final int priority;
    private final List<Component> components;

    public CategoryImpl(
            @NotNull String id,
            @NotNull net.kyori.adventure.text.Component name,
            @NotNull String icon,
            int priority,
            @NotNull List<Component> components) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.priority = priority;
        this.components = Collections.unmodifiableList(new ArrayList<>(components));
    }

    @Override
    @NotNull
    public String id() {
        return id;
    }

    @Override
    @NotNull
    public net.kyori.adventure.text.Component name() {
        return name;
    }

    @Override
    @NotNull
    public String icon() {
        return icon;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    @NotNull
    public List<Component> components() {
        return components;
    }
}
