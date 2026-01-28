package com.redactado.raisu.category;

import com.redactado.raisu.component.Component;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface Category {

    @NotNull
    String id();

    @NotNull
    net.kyori.adventure.text.Component name();

    @NotNull
    String icon();

    int priority();

    @NotNull
    List<Component> components();

    @NotNull
    static CategoryBuilder builder() {
        try {
            Class<?> builderClass = Class.forName("com.redactado.raisu.core.category.CategoryBuilderImpl");
            return (CategoryBuilder) builderClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create CategoryBuilder. Is raisu-core on classpath?", e);
        }
    }
}
