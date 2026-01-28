package com.redactado.raisu.component;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface Table extends Component {

    @NotNull
    List<String> headers();

    @NotNull
    List<List<String>> rows();

    @Override
    default ComponentType type() {
        return ComponentType.TABLE;
    }
}
