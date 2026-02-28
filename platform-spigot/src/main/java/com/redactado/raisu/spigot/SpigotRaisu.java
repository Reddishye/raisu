package com.redactado.raisu.spigot;

import com.redactado.raisu.Raisu;
import com.redactado.raisu.category.Category;
import com.redactado.raisu.category.CategoryProvider;
import com.redactado.raisu.spigot.provider.SpigotMemoryProvider;
import com.redactado.raisu.spigot.provider.SpigotPluginsProvider;
import com.redactado.raisu.spigot.provider.SpigotSystemProvider;
import com.redactado.raisu.spigot.provider.SpigotThreadsProvider;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Entry point for integrating Raisu into a Spigot/Bukkit plugin.
 *
 * <p>The simplest usage registers all built-in debug categories automatically:
 *
 * <pre>{@code
 * Raisu raisu = SpigotRaisu.create(this);
 * }</pre>
 *
 * <p>For fine-grained control use the builder:
 *
 * <pre>{@code
 * Raisu raisu = SpigotRaisu.builder(this)
 *     .addCategory(myStaticCategory)
 *     .addProvider("my:metrics", () -> buildMetricsCategory())
 *     .build();
 * }</pre>
 *
 * <p>Default providers registered (all under the {@code raisu:} namespace):
 *
 * <ul>
 *   <li>{@code raisu:system} — OS, Java, server software, online players
 *   <li>{@code raisu:memory} — JVM heap and non-heap usage
 *   <li>{@code raisu:threads} — active thread count and states
 *   <li>{@code raisu:plugins} — loaded plugin list with enable/disable status
 * </ul>
 */
public final class SpigotRaisu {

    private SpigotRaisu() {}

    /**
     * Creates a fully configured {@link Raisu} instance with all default Spigot categories
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
         * Disables all built-in default categories. Only categories and providers added via
         * {@link #addCategory} and {@link #addProvider} will be present.
         */
        @NotNull
        public Builder excludeDefaultCategories() {
            this.includeDefaultCategories = false;
            return this;
        }

        @NotNull
        public Raisu build() {
            SpigotPlatform platform = new SpigotPlatform(plugin);
            Raisu raisu = Raisu.create(platform);

            if (includeDefaultCategories) {
                registerDefaultProviders(raisu, plugin);
            }

            for (Category category : customCategories) {
                raisu.register(category);
            }

            for (Map.Entry<String, CategoryProvider> entry : customProviders.entrySet()) {
                raisu.registerProvider(entry.getKey(), entry.getValue());
            }

            return raisu;
        }

        /**
         * Registers all default Spigot providers onto the given {@link Raisu} instance.
         *
         * <p>Called internally by {@link #build()} and exposed so sub-classes (such as
         * {@code PaperRaisu.Builder}) can reuse the defaults before adding their own.
         */
        public static void registerDefaultProviders(@NotNull Raisu raisu, @NotNull Plugin plugin) {
            raisu.registerProvider("raisu:system", new SpigotSystemProvider(plugin));
            raisu.registerProvider("raisu:memory", new SpigotMemoryProvider());
            raisu.registerProvider("raisu:threads", new SpigotThreadsProvider());
            raisu.registerProvider("raisu:plugins", new SpigotPluginsProvider(plugin));
        }
    }
}
