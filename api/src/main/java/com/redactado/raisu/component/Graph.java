package com.redactado.raisu.component;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface Graph extends Component {

    @NotNull
    String title();

    @NotNull
    Map<String, Double> dataPoints();

    @Override
    default ComponentType type() {
        return ComponentType.GRAPH;
    }
}
