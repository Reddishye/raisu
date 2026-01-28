package com.redactado.raisu.core.component;

import com.redactado.raisu.component.ProgressBar;
import org.jetbrains.annotations.NotNull;

public final class ProgressBarImpl implements ProgressBar {

    private final String label;
    private final double current;
    private final double max;

    public ProgressBarImpl(@NotNull String label, double current, double max) {
        this.label = label;
        this.current = current;
        this.max = max;
    }

    @Override
    @NotNull
    public String label() {
        return label;
    }

    @Override
    public double current() {
        return current;
    }

    @Override
    public double max() {
        return max;
    }
}
