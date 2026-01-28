package com.redactado.raisu;

import com.redactado.raisu.bootstrap.RaisuLoader;
import com.redactado.raisu.bootstrap.RaisuVersion;
import com.redactado.raisu.category.Category;
import com.redactado.raisu.config.EncodeConfig;
import com.redactado.raisu.snapshot.Snapshot;
import com.redactado.raisu.snapshot.SnapshotBuilder;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface Raisu {

    @NotNull
    static Raisu create(@NotNull Plugin plugin) {
        try {
            Class<?> factoryClass = Class.forName("com.redactado.raisu.core.RaisuFactory");
            java.lang.reflect.Method createMethod = factoryClass.getMethod("create", Plugin.class);
            Raisu instance = (Raisu) createMethod.invoke(null, plugin);

            return RaisuLoader.load(plugin, RaisuVersion.VERSION, instance);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "Raisu core implementation not found. Add raisu-core dependency to your project.", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Raisu instance", e);
        }
    }

    void register(@NotNull Category category);

    void unregister(@NotNull String categoryId);

    @NotNull
    SnapshotBuilder snapshotBuilder();

    @NotNull
    Snapshot snapshot();

    @NotNull
    String encode(@NotNull Snapshot snapshot, @NotNull EncodeConfig config);
}
