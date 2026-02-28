package com.redactado.raisu.core.category;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.redactado.raisu.category.Category;
import com.redactado.raisu.category.CategoryProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CategoryRegistry {

    /** Static categories — stored for up to 30 minutes after last write. */
    private final Cache<String, Category> cache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    /** Dynamic category providers — evaluated on each snapshot call. */
    private final ConcurrentHashMap<String, CategoryProvider> providers = new ConcurrentHashMap<>();

    public void register(@NotNull Category category) {
        cache.put(category.id(), category);
    }

    public void unregister(@NotNull String categoryId) {
        cache.invalidate(categoryId);
    }

    public void registerProvider(@NotNull String id, @NotNull CategoryProvider provider) {
        providers.put(id, provider);
    }

    public void unregisterProvider(@NotNull String id) {
        providers.remove(id);
    }

    @Nullable
    public Category get(@NotNull String categoryId) {
        return cache.getIfPresent(categoryId);
    }

    @NotNull
    public List<Category> getAll() {
        return new ArrayList<>(cache.asMap().values());
    }

    @NotNull
    public Collection<CategoryProvider> getAllProviders() {
        return providers.values();
    }

    public void clear() {
        cache.invalidateAll();
        providers.clear();
    }
}
