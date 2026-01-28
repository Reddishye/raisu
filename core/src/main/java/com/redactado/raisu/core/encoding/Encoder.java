package com.redactado.raisu.core.encoding;

import com.redactado.raisu.config.EncodeConfig;
import com.redactado.raisu.snapshot.Snapshot;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public interface Encoder {

    @NotNull
    byte[] encode(@NotNull Snapshot snapshot, @NotNull EncodeConfig config) throws IOException;
}
