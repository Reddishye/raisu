package com.redactado.raisu.platform;

import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Abstraction over the host environment (Spigot, Paper, standalone, etc.)
 *
 * <p>Implement this interface to integrate Raisu into any platform. Platform modules such as
 * {@code platform-spigot} and {@code platform-paper} provide ready-made implementations.
 */
public interface RaisuPlatform {

    /** Human-readable identifier for this platform instance, e.g. the plugin name. */
    @NotNull
    default String platformName() {
        return "raisu";
    }

    /**
     * A short description of the server software and version, e.g. {@code "Paper 1.21.4 (git-…)"}.
     */
    @NotNull
    String serverVersion();

    /** The JVM version string, typically {@code System.getProperty("java.version")}. */
    @NotNull
    default String javaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * The {@link Logger} used by Raisu for info/warning messages.
     *
     * <p>Defaults to {@code Logger.getLogger("Raisu")}. Override to use the platform's own logger
     * (e.g. {@code plugin.getLogger()}).
     */
    @NotNull
    default Logger logger() {
        return Logger.getLogger("Raisu");
    }

    default void logInfo(@NotNull String message) {
        logger().info(message);
    }

    default void logWarning(@NotNull String message) {
        logger().warning(message);
    }
}
