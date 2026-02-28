package com.redactado.raisu.component.v2.layout;

import com.redactado.raisu.component.Component;
import com.redactado.raisu.component.v2.Gap;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface Grid extends Component {

    int columns();

    @NotNull
    Gap gap();

    @NotNull
    List<Component> children();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.GRID;
    }

    record Impl(int columns, @NotNull Gap gap, @NotNull List<Component> children) implements Grid {}

    static @NotNull Builder builder(int columns) {
        return new Builder(columns);
    }

    final class Builder {

        private final int columns;
        private Gap gap = Gap.NONE;
        private final List<Component> children = new ArrayList<>();

        private Builder(int columns) {
            this.columns = columns;
        }

        @NotNull
        public Builder gap(@NotNull Gap gap) {
            this.gap = gap;
            return this;
        }

        @NotNull
        public Builder add(@NotNull Component component) {
            children.add(component);
            return this;
        }

        @NotNull
        public Grid build() {
            return new Impl(columns, gap, List.copyOf(children));
        }
    }
}
