package com.redactado.raisu.category;

import org.jetbrains.annotations.NotNull;

/**
 * Produces a {@link Category} on demand, evaluated each time a snapshot is taken.
 *
 * <p>Use this instead of {@link com.redactado.raisu.Raisu#register(Category)} when your category
 * data is dynamic — for example, current memory usage, thread states, or live performance metrics.
 *
 * <p>This is a functional interface; lambdas are welcome:
 *
 * <pre>{@code
 * raisu.registerProvider("my:stats", () -> buildStatsCategory());
 * }</pre>
 */
@FunctionalInterface
public interface CategoryProvider {

    /**
     * Builds and returns the current {@link Category}.
     *
     * <p>Called from the thread that invokes {@link com.redactado.raisu.Raisu#snapshot()}. Keep
     * implementations fast; avoid blocking I/O.
     */
    @NotNull
    Category provide();
}
