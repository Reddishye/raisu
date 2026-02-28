package com.redactado.raisu.component.v2.layout;

import com.redactado.raisu.component.Component;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface Panel extends Component {

    @NotNull
    String title();

    boolean collapsible();

    boolean collapsed();

    @NotNull
    List<Component> children();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.PANEL;
    }

    record Impl(@NotNull String title, boolean collapsible, boolean collapsed, @NotNull List<Component> children)
            implements Panel {}

    static @NotNull Builder builder(@NotNull String title) {
        return new Builder(title);
    }

    final class Builder {

        private final String title;
        private boolean collapsible = false;
        private boolean collapsed = false;
        private final List<Component> children = new ArrayList<>();

        private Builder(@NotNull String title) {
            this.title = title;
        }

        @NotNull
        public Builder collapsible(boolean collapsible) {
            this.collapsible = collapsible;
            return this;
        }

        @NotNull
        public Builder collapsed(boolean collapsed) {
            this.collapsed = collapsed;
            return this;
        }

        @NotNull
        public Builder add(@NotNull Component component) {
            children.add(component);
            return this;
        }

        @NotNull
        public Panel build() {
            return new Impl(title, collapsible, collapsed, List.copyOf(children));
        }
    }
}
