package com.redactado.raisu.component;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface ProgressBar extends Component {

    @NotNull
    String label();

    double current();

    double max();

    default double percentage() {
        return max() > 0 ? (current() / max()) * 100.0 : 0.0;
    }

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.PROGRESS_BAR;
    }
}
