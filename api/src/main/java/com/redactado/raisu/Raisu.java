package com.redactado.raisu;

import com.redactado.raisu.bootstrap.RaisuLoader;
import com.redactado.raisu.bootstrap.RaisuVersion;
import com.redactado.raisu.category.Category;
import com.redactado.raisu.category.CategoryProvider;
import com.redactado.raisu.config.EncodeConfig;
import com.redactado.raisu.platform.RaisuPlatform;
import com.redactado.raisu.snapshot.Snapshot;
import com.redactado.raisu.snapshot.SnapshotBuilder;
import org.jetbrains.annotations.NotNull;

public interface Raisu {

    /**
     * Creates a new {@link Raisu} instance for the given platform.
     *
     * <p>Requires {@code raisu-core} on the classpath. Platform modules ({@code raisu-spigot},
     * {@code raisu-paper}) provide higher-level factory methods and default categories.
     */
    @NotNull
    static Raisu create(@NotNull RaisuPlatform platform) {
        try {
            Class<?> factoryClass = Class.forName("com.redactado.raisu.core.RaisuFactory");
            java.lang.reflect.Method createMethod =
                    factoryClass.getMethod("create", RaisuPlatform.class);
            Raisu instance = (Raisu) createMethod.invoke(null, platform);

            return RaisuLoader.load(
                    platform.logger(), platform.platformName(), RaisuVersion.VERSION, instance);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "Raisu core implementation not found. Add raisu-core dependency to your project.",
                    e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Raisu instance", e);
        }
    }

    /** Registers a static {@link Category}. It will be included in every snapshot. */
    void register(@NotNull Category category);

    /** Removes a previously registered static category. */
    void unregister(@NotNull String categoryId);

    /**
     * Registers a {@link CategoryProvider} under the given id.
     *
     * <p>The provider is evaluated on each {@link #snapshot()} call. Use this for categories whose
     * data changes over time (memory, threads, TPS, etc.).
     */
    void registerProvider(@NotNull String id, @NotNull CategoryProvider provider);

    /** Removes a previously registered provider. */
    void unregisterProvider(@NotNull String id);

    @NotNull
    SnapshotBuilder snapshotBuilder();

    /**
     * Builds a snapshot that includes all registered static categories and evaluates all registered
     * providers.
     */
    @NotNull
    Snapshot snapshot();

    @NotNull
    String encode(@NotNull Snapshot snapshot, @NotNull EncodeConfig config);
}
