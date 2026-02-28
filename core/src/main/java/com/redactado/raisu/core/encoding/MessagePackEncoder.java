package com.redactado.raisu.core.encoding;

import com.redactado.raisu.category.Category;
import com.redactado.raisu.component.Component;
import com.redactado.raisu.component.Graph;
import com.redactado.raisu.component.KeyValue;
import com.redactado.raisu.component.List;
import com.redactado.raisu.component.ProgressBar;
import com.redactado.raisu.component.Table;
import com.redactado.raisu.component.Text;
import com.redactado.raisu.component.Tree;
import com.redactado.raisu.component.v2.display.Alert;
import com.redactado.raisu.component.v2.display.Badge;
import com.redactado.raisu.component.v2.display.CodeBlock;
import com.redactado.raisu.component.v2.display.Gauge;
import com.redactado.raisu.component.v2.display.Link;
import com.redactado.raisu.component.v2.display.LogView;
import com.redactado.raisu.component.v2.display.Sparkline;
import com.redactado.raisu.component.v2.display.Stat;
import com.redactado.raisu.component.v2.display.Timeline;
import com.redactado.raisu.component.v2.layout.Column;
import com.redactado.raisu.component.v2.layout.Grid;
import com.redactado.raisu.component.v2.layout.Panel;
import com.redactado.raisu.component.v2.layout.Row;
import com.redactado.raisu.snapshot.Snapshot;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;

public final class MessagePackEncoder implements Encoder {

    @Override
    public byte @NotNull [] encode(@NotNull Snapshot snapshot) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (MessagePacker packer = MessagePack.newDefaultPacker(baos)) {
            packSnapshot(packer, snapshot);
        }
        return baos.toByteArray();
    }

    private void packSnapshot(@NotNull MessagePacker packer, @NotNull Snapshot snapshot) throws IOException {
        packer.packMapHeader(5);

        packer.packString("version");
        packer.packInt(SnapshotVersion.CURRENT);

        packer.packString("timestamp");
        packer.packLong(snapshot.timestamp().toEpochMilli());

        packer.packString("serverVersion");
        packer.packString(snapshot.serverVersion());

        packer.packString("javaVersion");
        packer.packString(snapshot.javaVersion());

        packer.packString("categories");
        packer.packArrayHeader(snapshot.categories().size());
        for (Category category : snapshot.categories()) {
            packCategory(packer, category);
        }
    }

    private void packCategory(@NotNull MessagePacker packer, @NotNull Category category) throws IOException {
        packer.packMapHeader(5);

        packer.packString("id");
        packer.packString(category.id());

        packer.packString("name");
        packer.packString(GsonComponentSerializer.gson().serialize(category.name()));

        packer.packString("icon");
        packer.packString(category.icon());

        packer.packString("priority");
        packer.packInt(category.priority());

        packer.packString("components");
        packer.packArrayHeader(category.components().size());
        for (Component component : category.components()) {
            packComponent(packer, component);
        }
    }

    private void packComponent(@NotNull MessagePacker packer, @NotNull Component component) throws IOException {
        packer.packMapHeader(2);

        packer.packString("type");
        packer.packString(component.type().name());

        packer.packString("data");
        switch (component.type()) {
            case KEY_VALUE -> packKeyValue(packer, (KeyValue) component);
            case TEXT -> packText(packer, (Text) component);
            case TABLE -> packTable(packer, (Table) component);
            case LIST -> packList(packer, (List) component);
            case PROGRESS_BAR -> packProgressBar(packer, (ProgressBar) component);
            case GRAPH -> packGraph(packer, (Graph) component);
            case TREE -> packTree(packer, (Tree) component);
            case COLUMN -> packColumn(packer, (Column) component);
            case ROW -> packRow(packer, (Row) component);
            case GRID -> packGrid(packer, (Grid) component);
            case PANEL -> packPanel(packer, (Panel) component);
            case BADGE -> packBadge(packer, (Badge) component);
            case STAT -> packStat(packer, (Stat) component);
            case ALERT -> packAlert(packer, (Alert) component);
            case CODE_BLOCK -> packCodeBlock(packer, (CodeBlock) component);
            case LOG_VIEW -> packLogView(packer, (LogView) component);
            case TIMELINE -> packTimeline(packer, (Timeline) component);
            case SPARKLINE -> packSparkline(packer, (Sparkline) component);
            case GAUGE -> packGauge(packer, (Gauge) component);
            case LINK -> packLink(packer, (Link) component);
        }
    }

    private void packKeyValue(@NotNull MessagePacker packer, @NotNull KeyValue kv) throws IOException {
        packer.packMapHeader(2);
        packer.packString("key");
        packer.packString(kv.key());
        packer.packString("value");
        packer.packString(kv.value());
    }

    private void packText(@NotNull MessagePacker packer, @NotNull Text text) throws IOException {
        packer.packMapHeader(1);
        packer.packString("content");
        packer.packString(text.content());
    }

    private void packTable(@NotNull MessagePacker packer, @NotNull Table table) throws IOException {
        packer.packMapHeader(2);

        packer.packString("headers");
        packer.packArrayHeader(table.headers().size());
        for (String header : table.headers()) {
            packer.packString(header);
        }

        packer.packString("rows");
        packer.packArrayHeader(table.rows().size());
        for (java.util.List<String> row : table.rows()) {
            packer.packArrayHeader(row.size());
            for (String cell : row) {
                packer.packString(cell);
            }
        }
    }

    private void packList(@NotNull MessagePacker packer, @NotNull List list) throws IOException {
        packer.packMapHeader(1);
        packer.packString("items");
        packer.packArrayHeader(list.items().size());
        for (String item : list.items()) {
            packer.packString(item);
        }
    }

    private void packProgressBar(@NotNull MessagePacker packer, @NotNull ProgressBar bar) throws IOException {
        packer.packMapHeader(3);
        packer.packString("label");
        packer.packString(bar.label());
        packer.packString("current");
        packer.packDouble(bar.current());
        packer.packString("max");
        packer.packDouble(bar.max());
    }

    private void packGraph(@NotNull MessagePacker packer, @NotNull Graph graph) throws IOException {
        packer.packMapHeader(2);
        packer.packString("title");
        packer.packString(graph.title());
        packer.packString("dataPoints");
        packer.packMapHeader(graph.dataPoints().size());
        for (Map.Entry<String, Double> entry : graph.dataPoints().entrySet()) {
            packer.packString(entry.getKey());
            packer.packDouble(entry.getValue());
        }
    }

    private void packTree(@NotNull MessagePacker packer, @NotNull Tree tree) throws IOException {
        packer.packMapHeader(1);
        packer.packString("root");
        packTreeNode(packer, tree.root());
    }

    private void packTreeNode(@NotNull MessagePacker packer, @NotNull Tree.TreeNode node) throws IOException {
        packer.packMapHeader(2);
        packer.packString("label");
        packer.packString(node.label());
        packer.packString("children");
        packer.packArrayHeader(node.children().size());
        for (Tree.TreeNode child : node.children()) {
            packTreeNode(packer, child);
        }
    }

    private void packChildren(@NotNull MessagePacker packer, @NotNull java.util.List<Component> children)
            throws IOException {
        packer.packArrayHeader(children.size());
        for (Component child : children) {
            packComponent(packer, child);
        }
    }

    private void packColumn(@NotNull MessagePacker packer, @NotNull Column column) throws IOException {
        packer.packMapHeader(3);
        packer.packString("alignment");
        packer.packString(column.alignment().name());
        packer.packString("gap");
        packer.packString(column.gap().name());
        packer.packString("children");
        packChildren(packer, column.children());
    }

    private void packRow(@NotNull MessagePacker packer, @NotNull Row row) throws IOException {
        packer.packMapHeader(4);
        packer.packString("alignment");
        packer.packString(row.alignment().name());
        packer.packString("gap");
        packer.packString(row.gap().name());
        packer.packString("wrap");
        packer.packBoolean(row.wrap());
        packer.packString("children");
        packChildren(packer, row.children());
    }

    private void packGrid(@NotNull MessagePacker packer, @NotNull Grid grid) throws IOException {
        packer.packMapHeader(3);
        packer.packString("columns");
        packer.packInt(grid.columns());
        packer.packString("gap");
        packer.packString(grid.gap().name());
        packer.packString("children");
        packChildren(packer, grid.children());
    }

    private void packPanel(@NotNull MessagePacker packer, @NotNull Panel panel) throws IOException {
        packer.packMapHeader(4);
        packer.packString("title");
        packer.packString(panel.title());
        packer.packString("collapsible");
        packer.packBoolean(panel.collapsible());
        packer.packString("collapsed");
        packer.packBoolean(panel.collapsed());
        packer.packString("children");
        packChildren(packer, panel.children());
    }

    private void packBadge(@NotNull MessagePacker packer, @NotNull Badge badge) throws IOException {
        packer.packMapHeader(2);
        packer.packString("text");
        packer.packString(badge.text());
        packer.packString("severity");
        packer.packString(badge.severity().name());
    }

    private void packStat(@NotNull MessagePacker packer, @NotNull Stat stat) throws IOException {
        packer.packMapHeader(5);
        packer.packString("label");
        packer.packString(stat.label());
        packer.packString("value");
        packer.packString(stat.value());
        packer.packString("unit");
        packNullableString(packer, stat.unit());
        packer.packString("trend");
        packNullableDouble(packer, stat.trend());
        packer.packString("description");
        packNullableString(packer, stat.description());
    }

    private void packAlert(@NotNull MessagePacker packer, @NotNull Alert alert) throws IOException {
        packer.packMapHeader(3);
        packer.packString("severity");
        packer.packString(alert.severity().name());
        packer.packString("title");
        packNullableString(packer, alert.title());
        packer.packString("message");
        packer.packString(alert.message());
    }

    private void packCodeBlock(@NotNull MessagePacker packer, @NotNull CodeBlock codeBlock) throws IOException {
        packer.packMapHeader(2);
        packer.packString("content");
        packer.packString(codeBlock.content());
        packer.packString("language");
        packer.packString(codeBlock.language());
    }

    private void packLogView(@NotNull MessagePacker packer, @NotNull LogView logView) throws IOException {
        packer.packMapHeader(1);
        packer.packString("entries");
        packer.packArrayHeader(logView.entries().size());
        for (LogView.LogEntry entry : logView.entries()) {
            packer.packMapHeader(3);
            packer.packString("timestamp");
            packer.packLong(entry.timestamp());
            packer.packString("severity");
            packer.packString(entry.severity().name());
            packer.packString("message");
            packer.packString(entry.message());
        }
    }

    private void packTimeline(@NotNull MessagePacker packer, @NotNull Timeline timeline) throws IOException {
        packer.packMapHeader(1);
        packer.packString("events");
        packer.packArrayHeader(timeline.events().size());
        for (Timeline.TimelineEvent event : timeline.events()) {
            packer.packMapHeader(3);
            packer.packString("label");
            packer.packString(event.label());
            packer.packString("description");
            packer.packString(event.description());
            packer.packString("timestamp");
            packer.packLong(event.timestamp());
        }
    }

    private void packSparkline(@NotNull MessagePacker packer, @NotNull Sparkline sparkline) throws IOException {
        packer.packMapHeader(2);
        packer.packString("label");
        packer.packString(sparkline.label());
        packer.packString("values");
        packer.packArrayHeader(sparkline.values().size());
        for (Double value : sparkline.values()) {
            packer.packDouble(value);
        }
    }

    private void packGauge(@NotNull MessagePacker packer, @NotNull Gauge gauge) throws IOException {
        packer.packMapHeader(4);
        packer.packString("label");
        packer.packString(gauge.label());
        packer.packString("current");
        packer.packDouble(gauge.current());
        packer.packString("max");
        packer.packDouble(gauge.max());
        packer.packString("unit");
        packNullableString(packer, gauge.unit());
    }

    private void packLink(@NotNull MessagePacker packer, @NotNull Link link) throws IOException {
        packer.packMapHeader(2);
        packer.packString("label");
        packer.packString(link.label());
        packer.packString("url");
        packer.packString(link.url());
    }

    private void packNullableString(@NotNull MessagePacker packer, @Nullable String value) throws IOException {
        if (value == null) {
            packer.packNil();
        } else {
            packer.packString(value);
        }
    }

    private void packNullableDouble(@NotNull MessagePacker packer, @Nullable Double value) throws IOException {
        if (value == null) {
            packer.packNil();
        } else {
            packer.packDouble(value);
        }
    }
}
