package com.redactado.raisu.spigot.provider;

import com.redactado.raisu.category.Category;
import com.redactado.raisu.category.CategoryProvider;
import com.redactado.raisu.component.v2.Gap;
import com.redactado.raisu.component.v2.Severity;
import com.redactado.raisu.component.v2.display.Badge;
import com.redactado.raisu.component.v2.display.Stat;
import com.redactado.raisu.component.v2.layout.Panel;
import com.redactado.raisu.component.v2.layout.Row;
import com.redactado.raisu.core.category.CategoryBuilderImpl;
import com.redactado.raisu.core.component.KeyValueImpl;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Provides a {@code raisu:system} category capturing OS, Java, and server environment details at
 * snapshot time.
 */
public final class SpigotSystemProvider implements CategoryProvider {

    private final Plugin plugin;

    public SpigotSystemProvider(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    @NotNull
    public Category provide() {
        int online = plugin.getServer().getOnlinePlayers().size();
        int max = plugin.getServer().getMaxPlayers();
        String serverName = plugin.getServer().getName();
        String serverVersion = plugin.getServer().getVersion();
        String javaVersion = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");
        String jvmName = System.getProperty("java.vm.name");
        String jvmVersion = System.getProperty("java.vm.version");

        double fillRatio = max > 0 ? (double) online / max : 0.0;
        Severity playerSeverity =
                fillRatio >= 0.9 ? Severity.ERROR : fillRatio >= 0.75 ? Severity.WARNING : Severity.INFO;

        return new CategoryBuilderImpl()
                .id("raisu:system")
                .name(Component.text("System"))
                .icon("🖥️")
                .priority(1)
                .add(Row.builder()
                        .gap(Gap.SMALL)
                        .add(Badge.of(serverName, Severity.INFO))
                        .add(Badge.of("Java " + javaVersion, Severity.INFO))
                        .add(Badge.of(online + " / " + max + " players", playerSeverity))
                        .build())
                .add(Row.builder()
                        .gap(Gap.MEDIUM)
                        .add(Stat.builder("Players", String.valueOf(online))
                                .description("of " + max + " max")
                                .build())
                        .add(Stat.builder("Java", javaVersion).build())
                        .add(Stat.builder("Server", serverName).build())
                        .build())
                .add(Panel.builder("Environment")
                        .collapsible(true)
                        .add(new KeyValueImpl("OS", osName + " " + osVersion + " (" + osArch + ")"))
                        .add(new KeyValueImpl("Java", javaVersion + " — " + javaVendor))
                        .add(new KeyValueImpl("JVM", jvmName + " " + jvmVersion))
                        .add(new KeyValueImpl("Server", serverVersion))
                        .build())
                .build();
    }
}
