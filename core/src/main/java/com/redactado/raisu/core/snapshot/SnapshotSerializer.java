package com.redactado.raisu.core.snapshot;

import com.redactado.raisu.snapshot.Snapshot;
import java.io.IOException;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface SnapshotSerializer {

    @NotNull
    Map<String, Object> serialize(@NotNull Snapshot snapshot) throws IOException;

    @NotNull
    Snapshot deserialize(@NotNull Map<String, Object> data) throws IOException;
}
