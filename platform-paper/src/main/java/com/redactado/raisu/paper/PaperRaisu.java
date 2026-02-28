package com.redactado.raisu.paper;

import com.redactado.raisu.Raisu;
import com.redactado.raisu.category.Category;
import com.redactado.raisu.category.CategoryProvider;
import com.redactado.raisu.paper.provider.PaperPerformanceProvider;
import com.redactado.raisu.spigot.SpigotRaisu;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Entry point for integrating Raisu into a Paper plugin.
 *
 * <p>Includes all Spigot defaults (system, memory, threads, plugins) plus a Paper-exclusive
 * {@code raisu:performance} category with TPS and MSPT readings.
 *
 * <pre>{@code
 * // Quick setup — all defaults registered:
 * Raisu raisu = PaperRaisu.create(this);
 *
 * // Custom setup — add extra categories or remove defaults:
 * Raisu raisu = PaperRaisu.builder(this)
 *     .addProvider("my:custom", () -> buildCustomCategory())
 *     .build();
 * }</pre>
 *
 * <p>Default providers registered (in addition to Spigot defaults):
 *
 * <ul>
 *   <li>{@code raisu:performance} — TPS (1m / 5m / 15m) and average MSPT
 * </ul>
 */
public final class PaperRaisu {

    private PaperRaisu() {}

    /**
     * Creates a fully configured {@link Raisu} instance with all default Paper + Spigot categories
     * registered.
     */
    @NotNull
    public static Raisu create(@NotNull Plugin plugin) {
        return builder(plugin).build();
    }

    @NotNull
    public static Builder builder(@NotNull Plugin plugin) {
        return new Builder(plugin);
    }

    public static final class Builder {

        private final Plugin plugin;
        private final List<Category> customCategories = new ArrayList<>();
        private final Map<String, CategoryProvider> customProviders = new LinkedHashMap<>();
        private boolean includeDefaultCategories = true;

        private Builder(@NotNull Plugin plugin) {
            this.plugin = plugin;
        }

        /** Registers a static {@link Category} that will be included in every snapshot. */
        @NotNull
        public Builder addCategory(@NotNull Category category) {
            customCategories.add(category);
            return this;
        }

        /**
         * Registers a dynamic {@link CategoryProvider} under the given id.
         *
         * <p>The provider is evaluated on each {@link Raisu#snapshot()} call.
         */
        @NotNull
        public Builder addProvider(@NotNull String id, @NotNull CategoryProvider provider) {
            customProviders.put(id, provider);
            return this;
        }

        /**
         * Disables all built-in default categories (Spigot + Paper). Only categories and providers
         * added via {@link #addCategory} and {@link #addProvider} will be present.
         */
        @NotNull
        public Builder excludeDefaultCategories() {
            this.includeDefaultCategories = false;
            return this;
        }

        @NotNull
        public Raisu build() {
            PaperPlatform platform = new PaperPlatform(plugin);
            Raisu raisu = Raisu.create(platform);

            if (includeDefaultCategories) {
                // Spigot defaults (system, memory, threads, plugins)
                SpigotRaisu.Builder.registerDefaultProviders(raisu, plugin);
                // Paper-specific
                raisu.registerProvider("raisu:performance", new PaperPerformanceProvider());
            }

            for (Category category : customCategories) {
                raisu.register(category);
            }

            for (Map.Entry<String, CategoryProvider> entry : customProviders.entrySet()) {
                raisu.registerProvider(entry.getKey(), entry.getValue());
            }

            return raisu;
        }
    }
}
