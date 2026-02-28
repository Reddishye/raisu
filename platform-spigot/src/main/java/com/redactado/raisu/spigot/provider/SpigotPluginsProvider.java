package com.redactado.raisu.spigot.provider;

import com.redactado.raisu.category.Category;
import com.redactado.raisu.category.CategoryBuilder;
import com.redactado.raisu.category.CategoryProvider;
import com.redactado.raisu.component.v2.Gap;
import com.redactado.raisu.component.v2.Severity;
import com.redactado.raisu.component.v2.display.Alert;
import com.redactado.raisu.component.v2.display.Stat;
import com.redactado.raisu.component.v2.layout.Panel;
import com.redactado.raisu.component.v2.layout.Row;
import com.redactado.raisu.core.category.CategoryBuilderImpl;
import com.redactado.raisu.core.component.TableImpl;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

/**
 * Provides a {@code raisu:plugins} category listing all loaded plugins, their versions, and
 * enabled/disabled status at snapshot time.
 */
public final class SpigotPluginsProvider implements CategoryProvider {

    private final Plugin plugin;

    public SpigotPluginsProvider(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    @NotNull
    public Category provide() {
        PluginManager pm = plugin.getServer().getPluginManager();
        Plugin[] plugins = pm.getPlugins();

        int enabled = 0;
        List<List<String>> rows = new ArrayList<>(plugins.length);
        for (Plugin p : plugins) {
            if (p.isEnabled()) enabled++;
            rows.add(List.of(p.getName(), p.getPluginMeta().getVersion(), p.isEnabled() ? "enabled" : "disabled"));
        }
        int disabled = plugins.length - enabled;

        CategoryBuilder builder = new CategoryBuilderImpl()
                .id("raisu:plugins")
                .name(Component.text("Plugins"))
                .icon("🔌")
                .priority(4);

        builder.add(Row.builder()
                .gap(Gap.MEDIUM)
                .add(Stat.builder("Total", String.valueOf(plugins.length)).build())
                .add(Stat.builder("Enabled", String.valueOf(enabled)).build())
                .add(Stat.builder("Disabled", String.valueOf(disabled)).build())
                .build());

        if (disabled > 0) {
            builder.add(Alert.of(
                    Severity.WARNING,
                    "Disabled Plugins",
                    disabled + " plugin(s) are disabled and may not be functioning."));
        }

        builder.add(Panel.builder("Plugin Details")
                .collapsible(true)
                .add(new TableImpl(List.of("Name", "Version", "Status"), rows))
                .build());

        return builder.build();
    }
}
