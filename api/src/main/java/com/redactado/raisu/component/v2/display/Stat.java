package com.redactado.raisu.component.v2.display;

import com.redactado.raisu.component.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public interface Stat extends Component {

    @NotNull
    String label();

    @NotNull
    String value();

    @Nullable
    String unit();

    @Nullable
    Double trend();

    @Nullable
    String description();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.STAT;
    }

    record Impl(
            @NotNull String label,
            @NotNull String value,
            @Nullable String unit,
            @Nullable Double trend,
            @Nullable String description)
            implements Stat {}

    static @NotNull Builder builder(@NotNull String label, @NotNull String value) {
        return new Builder(label, value);
    }

    final class Builder {

        private final String label;
        private final String value;
        @Nullable private String unit;
        @Nullable private Double trend;
        @Nullable private String description;

        private Builder(@NotNull String label, @NotNull String value) {
            this.label = label;
            this.value = value;
        }

        @NotNull
        public Builder unit(@NotNull String unit) {
            this.unit = unit;
            return this;
        }

        @NotNull
        public Builder trend(double trend) {
            this.trend = trend;
            return this;
        }

        @NotNull
        public Builder description(@NotNull String description) {
            this.description = description;
            return this;
        }

        @NotNull
        public Stat build() {
            return new Impl(label, value, unit, trend, description);
        }
    }
}
