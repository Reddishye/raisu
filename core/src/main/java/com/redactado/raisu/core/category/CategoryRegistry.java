package com.redactado.raisu.core.category;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.redactado.raisu.category.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CategoryRegistry {

    private final Cache<String, Category> cache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    public void register(@NotNull Category category) {
        cache.put(category.id(), category);
    }

    public void unregister(@NotNull String categoryId) {
        cache.invalidate(categoryId);
    }

    @Nullable
    public Category get(@NotNull String categoryId) {
        return cache.getIfPresent(categoryId);
    }

    @NotNull
    public List<Category> getAll() {
        return new ArrayList<>(cache.asMap().values());
    }

    public void clear() {
        cache.invalidateAll();
    }
}
