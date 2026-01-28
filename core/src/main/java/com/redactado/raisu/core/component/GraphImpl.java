package com.redactado.raisu.core.component;

import com.redactado.raisu.component.Graph;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class GraphImpl implements Graph {

    private final String title;
    private final Map<String, Double> dataPoints;

    public GraphImpl(@NotNull String title, @NotNull Map<String, Double> dataPoints) {
        this.title = title;
        this.dataPoints = Collections.unmodifiableMap(new HashMap<>(dataPoints));
    }

    @Override
    @NotNull
    public String title() {
        return title;
    }

    @Override
    @NotNull
    public Map<String, Double> dataPoints() {
        return dataPoints;
    }
}
