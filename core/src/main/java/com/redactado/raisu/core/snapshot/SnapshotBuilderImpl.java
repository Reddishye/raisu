package com.redactado.raisu.core.snapshot;

import com.redactado.raisu.category.Category;
import com.redactado.raisu.snapshot.Snapshot;
import com.redactado.raisu.snapshot.SnapshotBuilder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class SnapshotBuilderImpl implements SnapshotBuilder {

    private Instant timestamp = Instant.now();
    private String serverVersion = "Unknown";
    private String javaVersion = "Unknown";
    private final List<Category> categories = new ArrayList<>();

    @Override
    @NotNull
    public SnapshotBuilder timestamp(@NotNull Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    @NotNull
    public SnapshotBuilder serverVersion(@NotNull String version) {
        this.serverVersion = version;
        return this;
    }

    @Override
    @NotNull
    public SnapshotBuilder javaVersion(@NotNull String version) {
        this.javaVersion = version;
        return this;
    }

    @Override
    @NotNull
    public SnapshotBuilder addCategory(@NotNull Category category) {
        this.categories.add(category);
        return this;
    }

    @Override
    @NotNull
    public Snapshot build() {
        return new SnapshotImpl(timestamp, serverVersion, javaVersion, categories);
    }
}
