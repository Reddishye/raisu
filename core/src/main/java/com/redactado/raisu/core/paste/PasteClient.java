package com.redactado.raisu.core.paste;

import com.redactado.raisu.config.PasteProvider;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public interface PasteClient {
    @NotNull
    String upload(@NotNull byte[] data, @NotNull PasteProvider provider) throws IOException;
}
