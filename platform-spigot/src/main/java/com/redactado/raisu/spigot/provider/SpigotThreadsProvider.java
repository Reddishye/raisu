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
import org.jetbrains.annotations.NotNull;

/**
 * Provides a {@code raisu:threads} category with a snapshot of all active JVM threads, their
 * states, and whether they are daemon threads.
 */
public final class SpigotThreadsProvider implements CategoryProvider {

    @Override
    @NotNull
    public Category provide() {
        Thread[] threads = new Thread[Thread.activeCount() * 2];
        int count = Thread.enumerate(threads);

        int runnable = 0;
        int waiting = 0;
        int blocked = 0;
        int daemon = 0;

        List<List<String>> rows = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Thread t = threads[i];
            if (t == null) continue;

            Thread.State state = t.getState();
            switch (state) {
                case RUNNABLE -> runnable++;
                case BLOCKED -> blocked++;
                case WAITING, TIMED_WAITING -> waiting++;
                default -> {
                    // NEW / TERMINATED — not counted separately
                }
            }
            if (t.isDaemon()) daemon++;

            rows.add(List.of(
                    t.getName(),
                    state.name(),
                    t.isDaemon() ? "daemon" : "normal",
                    String.valueOf(t.getPriority())));
        }

        CategoryBuilder builder = new CategoryBuilderImpl()
                .id("raisu:threads")
                .name(Component.text("Threads"))
                .icon("🧵")
                .priority(3);

        builder.add(Row.builder()
                .gap(Gap.MEDIUM)
                .add(Stat.builder("Total", String.valueOf(count)).build())
                .add(Stat.builder("Runnable", String.valueOf(runnable)).build())
                .add(Stat.builder("Waiting", String.valueOf(waiting)).build())
                .add(Stat.builder("Blocked", String.valueOf(blocked)).build())
                .add(Stat.builder("Daemon", String.valueOf(daemon)).build())
                .build());

        if (blocked > 0) {
            builder.add(Alert.of(
                    Severity.WARNING,
                    "Blocked Threads",
                    blocked + " thread(s) are BLOCKED — possible lock contention or deadlock."));
        }

        builder.add(Panel.builder("Thread Details")
                .collapsible(true)
                .collapsed(true)
                .add(new TableImpl(List.of("Name", "State", "Type", "Priority"), rows))
                .build());

        return builder.build();
    }
}
