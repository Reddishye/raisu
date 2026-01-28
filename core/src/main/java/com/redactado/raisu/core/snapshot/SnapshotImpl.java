package com.redactado.raisu.core.snapshot;

import com.redactado.raisu.category.Category;
import com.redactado.raisu.snapshot.Snapshot;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class SnapshotImpl implements Snapshot {

    private final Instant timestamp;
    private final String serverVersion;
    private final String javaVersion;
    private final List<Category> categories;
    private final Map<String, Category> categoryMap;

    public SnapshotImpl(
            @NotNull Instant timestamp,
            @NotNull String serverVersion,
            @NotNull String javaVersion,
            @NotNull List<Category> categories) {
        this.timestamp = timestamp;
        this.serverVersion = serverVersion;
        this.javaVersion = javaVersion;
        this.categories = Collections.unmodifiableList(new ArrayList<>(categories));

        Map<String, Category> map = new HashMap<>();
        for (Category category : categories) {
            map.put(category.id(), category);
        }
        this.categoryMap = Collections.unmodifiableMap(map);
    }

    @Override
    @NotNull
    public Instant timestamp() {
        return timestamp;
    }

    @Override
    @NotNull
    public String serverVersion() {
        return serverVersion;
    }

    @Override
    @NotNull
    public String javaVersion() {
        return javaVersion;
    }

    @Override
    @NotNull
    public List<Category> categories() {
        return categories;
    }

    @Override
    @NotNull
    public Category getCategory(@NotNull String id) {
        Category category = categoryMap.get(id);
        if (category == null) {
            throw new IllegalArgumentException("Category not found: " + id);
        }
        return category;
    }

    @Override
    public boolean hasCategory(@NotNull String id) {
        return categoryMap.containsKey(id);
    }
}
