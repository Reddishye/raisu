package com.redactado.raisu.core.encoding;

import com.redactado.raisu.snapshot.Snapshot;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public interface Encoder {

    byte @NotNull [] encode(@NotNull Snapshot snapshot) throws IOException;
}
