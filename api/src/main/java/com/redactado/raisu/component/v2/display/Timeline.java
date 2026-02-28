package com.redactado.raisu.component.v2.display;

import com.redactado.raisu.component.Component;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface Timeline extends Component {

    @NotNull
    List<TimelineEvent> events();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.TIMELINE;
    }

    record TimelineEvent(@NotNull String label, @NotNull String description, long timestamp) {}

    record Impl(@NotNull List<TimelineEvent> events) implements Timeline {}

    static @NotNull Builder builder() {
        return new Builder();
    }

    final class Builder {

        private final List<TimelineEvent> events = new ArrayList<>();

        @NotNull
        public Builder add(@NotNull String label, @NotNull String description, long timestamp) {
            events.add(new TimelineEvent(label, description, timestamp));
            return this;
        }

        @NotNull
        public Timeline build() {
            return new Impl(List.copyOf(events));
        }
    }
}
