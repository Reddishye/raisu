package com.redactado.raisu.component;

import org.jetbrains.annotations.NotNull;

public interface ProgressBar extends Component {

    @NotNull
    String label();

    double current();

    double max();

    default double percentage() {
        return max() > 0 ? (current() / max()) * 100.0 : 0.0;
    }

    @Override
    default ComponentType type() {
        return ComponentType.PROGRESS_BAR;
    }
}
