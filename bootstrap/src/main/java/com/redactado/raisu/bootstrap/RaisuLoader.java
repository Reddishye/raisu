package com.redactado.raisu.bootstrap;

import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class RaisuLoader {

//    private static final Logger LOGGER = Logger.getLogger("Raisu");

    @Nullable
    private static Object currentInstance;

    @Nullable
    private static Version currentVersion;

    @Nullable
    private static String loadedFrom;

    private RaisuLoader() {}

    @NotNull
    public static <T> T load(@NotNull Plugin plugin, @NotNull String version, @NotNull T implementation) {
        Version parsedVersion = Version.parse(version);
        String pluginName = plugin.getName();

        if (currentInstance == null || currentVersion == null) {
            // First load
            setCurrentInstance(implementation, parsedVersion, pluginName);
            plugin.getLogger().info(String.format("Raisu v%s loaded", parsedVersion));
            return implementation;
        }

        // At this point, currentVersion is guaranteed to be non-null
        if (parsedVersion.isNewerThan(currentVersion)) {
            plugin.getLogger().info(String.format("Raisu updated from v%s to v%s", currentVersion, parsedVersion));
            setCurrentInstance(implementation, parsedVersion, pluginName);
            return implementation;
        } else {
            plugin.getLogger()
                    .fine(String.format(
                            "Raisu v%s is older than current v%s, using existing", parsedVersion, currentVersion));
            // Type is guaranteed because we always store T and return T
            Class<T> expectedType = getClass(implementation);
            return expectedType.cast(currentInstance);
        }
    }

    private static <T> void setCurrentInstance(@NotNull T instance, @NotNull Version version, @NotNull String plugin) {
        currentInstance = instance;
        currentVersion = version;
        loadedFrom = plugin;
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> getClass(@NotNull T instance) {
        return (Class<T>) instance.getClass();
    }

    @Nullable
    public static Version getCurrentVersion() {
        return currentVersion;
    }

    @Nullable
    public static String getLoadedFrom() {
        return loadedFrom;
    }

    /**
     * Resets the loader state. For testing purposes only.
     */
    static void reset() {
        currentInstance = null;
        currentVersion = null;
        loadedFrom = null;
    }
}
