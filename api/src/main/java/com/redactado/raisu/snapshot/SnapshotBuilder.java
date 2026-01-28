package com.redactado.raisu.snapshot;

import com.redactado.raisu.category.Category;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

public interface SnapshotBuilder {

    @NotNull
    SnapshotBuilder timestamp(@NotNull Instant timestamp);

    @NotNull
    SnapshotBuilder serverVersion(@NotNull String version);

    @NotNull
    SnapshotBuilder javaVersion(@NotNull String version);

    @NotNull
    SnapshotBuilder addCategory(@NotNull Category category);

    @NotNull
    Snapshot build();
}
