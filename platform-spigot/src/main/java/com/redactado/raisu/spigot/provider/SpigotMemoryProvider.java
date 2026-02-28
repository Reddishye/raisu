package com.redactado.raisu.spigot.provider;

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
import com.redactado.raisu.core.component.KeyValueImpl;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * Provides a {@code raisu:memory} category with JVM heap/non-heap statistics at snapshot time.
 */
public final class SpigotMemoryProvider implements CategoryProvider {

    @Override
    @NotNull
    public Category provide() {
        Runtime rt = Runtime.getRuntime();

        long usedBytes = rt.totalMemory() - rt.freeMemory();
        long maxBytes = rt.maxMemory();

        long usedMb = usedBytes / (1024 * 1024);
        long freeMb = rt.freeMemory() / (1024 * 1024);
        long totalMb = rt.totalMemory() / (1024 * 1024);
        long maxMb = maxBytes / (1024 * 1024);
        int cpuCores = rt.availableProcessors();

        double usedRatio = maxMb > 0 ? (double) usedMb / maxMb : 0.0;
        int usedPct = (int) Math.round(usedRatio * 100);

        CategoryBuilder builder = new CategoryBuilderImpl()
                .id("raisu:memory")
                .name(Component.text("Memory"))
                .icon("💾")
                .priority(2);

        if (usedRatio >= 0.9) {
            builder.add(Alert.of(
                    Severity.ERROR,
                    "Critical Memory",
                    "JVM heap is at " + usedPct + "% capacity — GC pressure likely."));
        } else if (usedRatio >= 0.75) {
            builder.add(Alert.of(Severity.WARNING, "High Memory Usage", "JVM heap is at " + usedPct + "% capacity."));
        }

        builder.add(Gauge.builder("Heap", (double) usedMb, (double) maxMb)
                        .unit("MB")
                        .build())
                .add(Row.builder()
                        .gap(Gap.MEDIUM)
                        .add(Stat.builder("Used", usedMb + " MB").build())
                        .add(Stat.builder("Free", freeMb + " MB").build())
                        .add(Stat.builder("Total", totalMb + " MB").build())
                        .add(Stat.builder("Max", maxMb + " MB").build())
                        .build())
                .add(new KeyValueImpl("CPU Cores", String.valueOf(cpuCores)));

        return builder.build();
    }
}
