package com.redactado.raisu.core;

import com.google.inject.Inject;
import com.redactado.raisu.Raisu;
import com.redactado.raisu.category.Category;
import com.redactado.raisu.config.EncodeConfig;
import com.redactado.raisu.core.category.CategoryRegistry;
import com.redactado.raisu.core.encoding.Encoder;
import com.redactado.raisu.core.paste.PasteClient;
import com.redactado.raisu.core.snapshot.SnapshotBuilderImpl;
import com.redactado.raisu.exception.EncodeException;
import com.redactado.raisu.snapshot.Snapshot;
import com.redactado.raisu.snapshot.SnapshotBuilder;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class RaisuImpl implements Raisu {

    private final Plugin plugin;
    private final CategoryRegistry categoryRegistry;
    private final Encoder encoder;
    private final PasteClient pasteClient;

    @Inject
    public RaisuImpl(Plugin plugin, CategoryRegistry categoryRegistry, Encoder encoder, PasteClient pasteClient) {
        this.plugin = plugin;
        this.categoryRegistry = categoryRegistry;
        this.encoder = encoder;
        this.pasteClient = pasteClient;
    }

    @Override
    public void register(@NotNull Category category) {
        categoryRegistry.register(category);
    }

    @Override
    public void unregister(@NotNull String categoryId) {
        categoryRegistry.unregister(categoryId);
    }

    @Override
    @NotNull
    public SnapshotBuilder snapshotBuilder() {
        return new SnapshotBuilderImpl();
    }

    @Override
    @NotNull
    public Snapshot snapshot() {
        return snapshotBuilder()
                .serverVersion(plugin.getServer().getVersion())
                .javaVersion(System.getProperty("java.version"))
                .build();
    }

    @Override
    @NotNull
    public String encode(@NotNull Snapshot snapshot, @NotNull EncodeConfig config) {
        try {
            byte[] encoded = encoder.encode(snapshot, config);
            String url = pasteClient.upload(encoded, config.provider());
            return url;
        } catch (Exception e) {
            throw new EncodeException("Failed to encode snapshot", e);
        }
    }
}
