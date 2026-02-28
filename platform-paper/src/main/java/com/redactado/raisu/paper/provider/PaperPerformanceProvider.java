package com.redactado.raisu.paper.provider;

import com.redactado.raisu.category.Category;
import com.redactado.raisu.category.CategoryBuilder;
import com.redactado.raisu.category.CategoryProvider;
import com.redactado.raisu.component.v2.Gap;
import com.redactado.raisu.component.v2.Severity;
import com.redactado.raisu.component.v2.display.Alert;
import com.redactado.raisu.component.v2.display.Gauge;
import com.redactado.raisu.component.v2.display.Stat;
import com.redactado.raisu.component.v2.layout.Row;
import com.redactado.raisu.core.category.CategoryBuilderImpl;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * Provides a {@code raisu:performance} category with Paper's TPS and MSPT readings.
 *
 * <p>Uses Paper-exclusive APIs ({@code Server#getTPS()}, {@code Server#getAverageTickTime()}).
 * This provider is only registered by {@link com.redactado.raisu.paper.PaperRaisu} — it must not
 * be used on vanilla Spigot.
 */
public final class PaperPerformanceProvider implements CategoryProvider {

    private static final double TARGET_TPS = 20.0;
    private static final double MAX_MSPT = 50.0;

    @Override
    @NotNull
    public Category provide() {
        double[] tps = Bukkit.getServer().getTPS();
        double tps1m = tps.length > 0 ? tps[0] : 0.0;
        double tps5m = tps.length > 1 ? tps[1] : 0.0;
        double tps15m = tps.length > 2 ? tps[2] : 0.0;
        double mspt = Bukkit.getServer().getAverageTickTime();

        CategoryBuilder builder = new CategoryBuilderImpl()
                .id("raisu:performance")
                .name(Component.text("Performance"))
                .icon("📊")
                .priority(5);

        // Most critical condition first
        if (mspt > 48.0) {
            builder.add(Alert.of(
                    Severity.ERROR,
                    "Server Overloaded",
                    String.format("Average tick time is %.1fms — TPS loss imminent.", mspt)));
        } else if (tps1m < 15.0) {
            builder.add(Alert.of(
                    Severity.ERROR,
                    "Critical TPS Drop",
                    String.format("Server TPS has dropped to %.2f — severe lag.", tps1m)));
        } else if (mspt > 40.0) {
            builder.add(Alert.of(
                    Severity.WARNING,
                    "High Tick Time",
                    String.format("Average tick time is %.1fms — performance may degrade.", mspt)));
        } else if (tps1m < 18.0) {
            builder.add(Alert.of(
                    Severity.WARNING,
                    "Reduced TPS",
                    String.format("Server TPS is %.2f — some lag occurring.", tps1m)));
        }

        builder.add(Row.builder()
                        .gap(Gap.MEDIUM)
                        .add(Gauge.builder("TPS", tps1m, TARGET_TPS).unit("tps").build())
                        .add(Gauge.builder("MSPT", mspt, MAX_MSPT).unit("ms").build())
                        .build())
                .add(Row.builder()
                        .gap(Gap.MEDIUM)
                        .add(Stat.builder("TPS 1m", String.format("%.2f", tps1m))
                                .unit("tps")
                                .trend(tps1m - tps5m)
                                .build())
                        .add(Stat.builder("TPS 5m", String.format("%.2f", tps5m))
                                .unit("tps")
                                .build())
                        .add(Stat.builder("TPS 15m", String.format("%.2f", tps15m))
                                .unit("tps")
                                .build())
                        .add(Stat.builder("MSPT", String.format("%.2f", mspt))
                                .unit("ms")
                                .build())
                        .build());

        return builder.build();
    }
}
