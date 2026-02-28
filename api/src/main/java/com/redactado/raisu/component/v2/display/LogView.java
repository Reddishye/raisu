package com.redactado.raisu.component.v2.display;

import com.redactado.raisu.component.Component;
import com.redactado.raisu.component.v2.Severity;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface LogView extends Component {

    @NotNull
    List<LogEntry> entries();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.LOG_VIEW;
    }

    record LogEntry(long timestamp, @NotNull Severity severity, @NotNull String message) {}

    record Impl(@NotNull List<LogEntry> entries) implements LogView {}

    static @NotNull Builder builder() {
        return new Builder();
    }

    final class Builder {

        private final List<LogEntry> entries = new ArrayList<>();

        @NotNull
        public Builder add(long timestamp, @NotNull Severity severity, @NotNull String message) {
            entries.add(new LogEntry(timestamp, severity, message));
            return this;
        }

        @NotNull
        public LogView build() {
            return new Impl(List.copyOf(entries));
        }
    }
}
