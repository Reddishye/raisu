package com.redactado.raisu.component;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface Graph extends Component {

    @NotNull
    String title();

    @NotNull
    Map<String, Double> dataPoints();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.GRAPH;
    }
}
