package com.redactado.raisu.snapshot;

import com.redactado.raisu.category.Category;
import java.time.Instant;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface Snapshot {

    @NotNull
    Instant timestamp();

    @NotNull
    String serverVersion();

    @NotNull
    String javaVersion();

    @NotNull
    List<Category> categories();

    @NotNull
    Category getCategory(@NotNull String id);

    boolean hasCategory(@NotNull String id);
}
