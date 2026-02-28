package com.redactado.raisu.spigot;

import com.redactado.raisu.platform.RaisuPlatform;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * {@link RaisuPlatform} backed by a Bukkit/Spigot {@link Plugin}.
 *
 * <p>Delegates server version and logging to the host plugin. Override this class to customise
 * any behaviour for your own platform wrapper.
 */
public class SpigotPlatform implements RaisuPlatform {

    private final Plugin plugin;

    public SpigotPlatform(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    @NotNull
    public String platformName() {
        return plugin.getName();
    }

    @Override
    @NotNull
    public String serverVersion() {
        return plugin.getServer().getVersion();
    }

    @Override
    @NotNull
    public String javaVersion() {
        return System.getProperty("java.version");
    }

    @Override
    @NotNull
    public Logger logger() {
        return plugin.getLogger();
    }

    /** Exposes the underlying {@link Plugin} for use by sub-classes and providers. */
    @NotNull
    protected Plugin plugin() {
        return plugin;
    }
}
