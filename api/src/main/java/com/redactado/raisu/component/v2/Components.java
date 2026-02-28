package com.redactado.raisu.component.v2;

import com.redactado.raisu.component.v2.display.Alert;
import com.redactado.raisu.component.v2.display.Badge;
import com.redactado.raisu.component.v2.display.CodeBlock;
import com.redactado.raisu.component.v2.display.Gauge;
import com.redactado.raisu.component.v2.display.Iframe;
import com.redactado.raisu.component.v2.display.Link;
import com.redactado.raisu.component.v2.display.LogView;
import com.redactado.raisu.component.v2.display.Sparkline;
import com.redactado.raisu.component.v2.display.Stat;
import com.redactado.raisu.component.v2.display.Timeline;
import com.redactado.raisu.component.v2.layout.Column;
import com.redactado.raisu.component.v2.layout.Grid;
import com.redactado.raisu.component.v2.layout.Panel;
import com.redactado.raisu.component.v2.layout.Row;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class Components {

    private Components() {}

    @NotNull
    public static Column.Builder column() {
        return Column.builder();
    }

    @NotNull
    public static Row.Builder row() {
        return Row.builder();
    }

    @NotNull
    public static Grid.Builder grid(int columns) {
        return Grid.builder(columns);
    }

    @NotNull
    public static Panel.Builder panel(@NotNull String title) {
        return Panel.builder(title);
    }

    @NotNull
    public static Badge badge(@NotNull String text) {
        return Badge.of(text);
    }

    @NotNull
    public static Badge badge(@NotNull String text, @NotNull Severity severity) {
        return Badge.of(text, severity);
    }

    @NotNull
    public static Stat.Builder stat(@NotNull String label, @NotNull String value) {
        return Stat.builder(label, value);
    }

    @NotNull
    public static Alert alert(@NotNull Severity severity, @NotNull String message) {
        return Alert.of(severity, message);
    }

    @NotNull
    public static Alert alert(@NotNull Severity severity, @NotNull String title, @NotNull String message) {
        return Alert.of(severity, title, message);
    }

    @NotNull
    public static CodeBlock codeBlock(@NotNull String content) {
        return CodeBlock.of(content);
    }

    @NotNull
    public static CodeBlock codeBlock(@NotNull String content, @NotNull String language) {
        return CodeBlock.of(content, language);
    }

    @NotNull
    public static LogView.Builder logView() {
        return LogView.builder();
    }

    @NotNull
    public static Timeline.Builder timeline() {
        return Timeline.builder();
    }

    @NotNull
    public static Sparkline sparkline(@NotNull String label, @NotNull List<Double> values) {
        return Sparkline.of(label, values);
    }

    @NotNull
    public static Gauge gauge(@NotNull String label, double current, double max) {
        return Gauge.of(label, current, max);
    }

    @NotNull
    public static Gauge.Builder gaugeBuilder(@NotNull String label, double current, double max) {
        return Gauge.builder(label, current, max);
    }

    @NotNull
    public static Link link(@NotNull String label, @NotNull String url) {
        return Link.of(label, url);
    }

    @NotNull
    public static Iframe iframe(@NotNull String url) {
        return Iframe.of(url);
    }

    @NotNull
    public static Iframe iframe(@NotNull String url, @NotNull String title) {
        return Iframe.of(url, title);
    }

    @NotNull
    public static Iframe iframe(@NotNull String url, @NotNull String title, int height) {
        return Iframe.of(url, title, height);
    }
}
