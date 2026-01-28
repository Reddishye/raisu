package com.redactado.raisu.core.component;

import com.redactado.raisu.component.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class TableImpl implements Table {

    private final List<String> headers;
    private final List<List<String>> rows;

    public TableImpl(@NotNull List<String> headers, @NotNull List<List<String>> rows) {
        this.headers = Collections.unmodifiableList(new ArrayList<>(headers));

        List<List<String>> immutableRows = new ArrayList<>();
        for (List<String> row : rows) {
            immutableRows.add(Collections.unmodifiableList(new ArrayList<>(row)));
        }
        this.rows = Collections.unmodifiableList(immutableRows);
    }

    @Override
    @NotNull
    public List<String> headers() {
        return headers;
    }

    @Override
    @NotNull
    public List<List<String>> rows() {
        return rows;
    }
}
