package com.redactado.raisu.component.v2.display;

import com.redactado.raisu.component.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public interface Gauge extends Component {

    @NotNull
    String label();

    double current();

    double max();

    @Nullable
    String unit();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.GAUGE;
    }

    record Impl(@NotNull String label, double current, double max, @Nullable String unit)
            implements Gauge {}

    static @NotNull Gauge of(@NotNull String label, double current, double max) {
        return new Impl(label, current, max, null);
    }

    static @NotNull Builder builder(@NotNull String label, double current, double max) {
        return new Builder(label, current, max);
    }

    final class Builder {

        private final String label;
        private final double current;
        private final double max;
        @Nullable private String unit;

        private Builder(@NotNull String label, double current, double max) {
            this.label = label;
            this.current = current;
            this.max = max;
        }

        @NotNull
        public Builder unit(@NotNull String unit) {
            this.unit = unit;
            return this;
        }

        @NotNull
        public Gauge build() {
            return new Impl(label, current, max, unit);
        }
    }
}
