package com.redactado.raisu.paper;

import com.redactado.raisu.spigot.SpigotPlatform;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * {@link com.redactado.raisu.platform.RaisuPlatform} implementation for Paper servers.
 *
 * <p>Extends {@link SpigotPlatform} and may be used anywhere a {@code SpigotPlatform} is accepted.
 * Paper-specific behaviour can be overridden here as the Paper API evolves.
 */
public final class PaperPlatform extends SpigotPlatform {

    public PaperPlatform(@NotNull Plugin plugin) {
        super(plugin);
    }

    @Override
    @NotNull
    public String platformName() {
        return plugin().getName() + "@paper";
    }
}
