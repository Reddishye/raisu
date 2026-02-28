package com.redactado.raisu.component.v2.display;

import com.redactado.raisu.component.Component;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface Sparkline extends Component {

    @NotNull
    String label();

    @NotNull
    List<Double> values();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.SPARKLINE;
    }

    record Impl(@NotNull String label, @NotNull List<Double> values) implements Sparkline {}

    static @NotNull Sparkline of(@NotNull String label, @NotNull List<Double> values) {
        return new Impl(label, List.copyOf(values));
    }
}
