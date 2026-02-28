package com.redactado.raisu.component.v2.layout;

import com.redactado.raisu.component.Component;
import com.redactado.raisu.component.v2.Alignment;
import com.redactado.raisu.component.v2.Gap;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface Row extends Component {

    @NotNull
    Alignment alignment();

    @NotNull
    Gap gap();

    boolean wrap();

    @NotNull
    List<Component> children();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.ROW;
    }

    record Impl(
            @NotNull Alignment alignment,
            @NotNull Gap gap,
            boolean wrap,
            @NotNull List<Component> children)
            implements Row {}

    static @NotNull Builder builder() {
        return new Builder();
    }

    final class Builder {

        private Alignment alignment = Alignment.START;
        private Gap gap = Gap.NONE;
        private boolean wrap = false;
        private final List<Component> children = new ArrayList<>();

        @NotNull
        public Builder alignment(@NotNull Alignment alignment) {
            this.alignment = alignment;
            return this;
        }

        @NotNull
        public Builder gap(@NotNull Gap gap) {
            this.gap = gap;
            return this;
        }

        @NotNull
        public Builder wrap(boolean wrap) {
            this.wrap = wrap;
            return this;
        }

        @NotNull
        public Builder add(@NotNull Component component) {
            children.add(component);
            return this;
        }

        @NotNull
        public Row build() {
            return new Impl(alignment, gap, wrap, List.copyOf(children));
        }
    }
}
