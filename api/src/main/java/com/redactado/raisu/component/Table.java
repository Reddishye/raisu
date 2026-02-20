package com.redactado.raisu.component;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface Table extends Component {

    @NotNull
    List<String> headers();

    @NotNull
    List<List<String>> rows();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.TABLE;
    }
}
