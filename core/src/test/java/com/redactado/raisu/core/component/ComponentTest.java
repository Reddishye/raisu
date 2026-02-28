package com.redactado.raisu.core.component;

import static org.junit.jupiter.api.Assertions.*;

import com.redactado.raisu.component.Component.ComponentType;
import com.redactado.raisu.component.v2.Alignment;
import com.redactado.raisu.component.v2.Gap;
import com.redactado.raisu.component.v2.Severity;
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
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ComponentTest {

    @Test
    void testKeyValue() {
        var kv = new KeyValueImpl("Memory", "2048MB");

        assertEquals("Memory", kv.key());
        assertEquals("2048MB", kv.value());
        assertEquals(ComponentType.KEY_VALUE, kv.type());
    }

    @Test
    void testText() {
        var text = new TextImpl("Hello World");

        assertEquals("Hello World", text.content());
        assertEquals(ComponentType.TEXT, text.type());
    }

    @Test
    void testTable() {
        var table = new TableImpl(List.of("Name", "Age"), List.of(List.of("Alice", "25"), List.of("Bob", "30")));

        assertEquals(List.of("Name", "Age"), table.headers());
        assertEquals(2, table.rows().size());
        assertEquals(ComponentType.TABLE, table.type());
    }

    @Test
    void testList() {
        var list = new ListImpl(List.of("Item 1", "Item 2", "Item 3"));

        assertEquals(3, list.items().size());
        assertEquals("Item 1", list.items().getFirst());
        assertEquals(ComponentType.LIST, list.type());
    }

    @Test
    void testProgressBar() {
        var bar = new ProgressBarImpl("Loading", 75, 100);

        assertEquals("Loading", bar.label());
        assertEquals(75, bar.current());
        assertEquals(100, bar.max());
        assertEquals(75.0, bar.percentage());
        assertEquals(ComponentType.PROGRESS_BAR, bar.type());
    }

    @Test
    void testGraph() {
        var graph = new GraphImpl("TPS", Map.of("10s", 20.0, "20s", 19.8, "30s", 19.9));

        assertEquals("TPS", graph.title());
        assertEquals(3, graph.dataPoints().size());
        assertEquals(ComponentType.GRAPH, graph.type());
    }

    @Test
    void testTree() {
        var root = new TreeNodeImpl(
                "Root", List.of(new TreeNodeImpl("Child 1", List.of()), new TreeNodeImpl("Child 2", List.of())));

        var tree = new TreeImpl(root);

        assertEquals("Root", tree.root().label());
        assertEquals(2, tree.root().children().size());
        assertEquals(ComponentType.TREE, tree.type());
    }

    // --- V2 display tests ---

    @Test
    void testBadgeDefault() {
        var badge = Badge.of("ONLINE");

        assertEquals("ONLINE", badge.text());
        assertEquals(Severity.DEFAULT, badge.severity());
        assertEquals(ComponentType.BADGE, badge.type());
    }

    @Test
    void testBadgeWithSeverity() {
        var badge = Badge.of("SUCCESS", Severity.SUCCESS);

        assertEquals("SUCCESS", badge.text());
        assertEquals(Severity.SUCCESS, badge.severity());
    }

    @Test
    void testStatRequiredOnly() {
        var stat = Stat.builder("TPS", "19.95").build();

        assertEquals("TPS", stat.label());
        assertEquals("19.95", stat.value());
        assertNull(stat.unit());
        assertNull(stat.trend());
        assertNull(stat.description());
        assertEquals(ComponentType.STAT, stat.type());
    }

    @Test
    void testStatAllFields() {
        var stat = Stat.builder("Memory", "3.2")
                .unit("GB")
                .trend(0.05)
                .description("Heap usage")
                .build();

        assertEquals("Memory", stat.label());
        assertEquals("3.2", stat.value());
        assertEquals("GB", stat.unit());
        assertEquals(0.05, stat.trend());
        assertEquals("Heap usage", stat.description());
    }

    @Test
    void testAlertNoTitle() {
        var alert = Alert.of(Severity.WARNING, "High GC pause detected");

        assertEquals(Severity.WARNING, alert.severity());
        assertNull(alert.title());
        assertEquals("High GC pause detected", alert.message());
        assertEquals(ComponentType.ALERT, alert.type());
    }

    @Test
    void testAlertWithTitle() {
        var alert = Alert.of(Severity.ERROR, "Crash", "Server crashed");

        assertEquals(Severity.ERROR, alert.severity());
        assertEquals("Crash", alert.title());
        assertEquals("Server crashed", alert.message());
    }

    @Test
    void testCodeBlockNoLanguage() {
        var block = CodeBlock.of("System.out.println(\"hello\");");

        assertEquals("System.out.println(\"hello\");", block.content());
        assertEquals("", block.language());
        assertEquals(ComponentType.CODE_BLOCK, block.type());
    }

    @Test
    void testCodeBlockWithLanguage() {
        var block = CodeBlock.of("int x = 1;", "java");

        assertEquals("int x = 1;", block.content());
        assertEquals("java", block.language());
    }

    @Test
    void testLogView() {
        long ts = System.currentTimeMillis();
        var logView = LogView.builder()
                .add(ts, Severity.INFO, "Server started")
                .add(ts + 1000, Severity.WARNING, "Low memory")
                .build();

        assertEquals(2, logView.entries().size());
        assertEquals(Severity.INFO, logView.entries().get(0).severity());
        assertEquals("Server started", logView.entries().get(0).message());
        assertEquals(ComponentType.LOG_VIEW, logView.type());
    }

    @Test
    void testTimeline() {
        long ts = 1_000_000L;
        var timeline = Timeline.builder()
                .add("Start", "Server boot", ts)
                .add("Ready", "Plugins loaded", ts + 5000)
                .build();

        assertEquals(2, timeline.events().size());
        assertEquals("Start", timeline.events().get(0).label());
        assertEquals("Server boot", timeline.events().get(0).description());
        assertEquals(ts, timeline.events().get(0).timestamp());
        assertEquals(ComponentType.TIMELINE, timeline.type());
    }

    @Test
    void testSparkline() {
        var sparkline = Sparkline.of("TPS", List.of(20.0, 19.8, 19.9, 20.0));

        assertEquals("TPS", sparkline.label());
        assertEquals(4, sparkline.values().size());
        assertEquals(20.0, sparkline.values().get(0));
        assertEquals(ComponentType.SPARKLINE, sparkline.type());
    }

    @Test
    void testGaugeSimple() {
        var gauge = Gauge.of("Memory", 3200.0, 8192.0);

        assertEquals("Memory", gauge.label());
        assertEquals(3200.0, gauge.current());
        assertEquals(8192.0, gauge.max());
        assertNull(gauge.unit());
        assertEquals(ComponentType.GAUGE, gauge.type());
    }

    @Test
    void testGaugeWithUnit() {
        var gauge = Gauge.builder("Memory", 3200.0, 8192.0).unit("MB").build();

        assertEquals("MB", gauge.unit());
    }

    @Test
    void testLink() {
        var link = Link.of("GitHub", "https://github.com");

        assertEquals("GitHub", link.label());
        assertEquals("https://github.com", link.url());
        assertEquals(ComponentType.LINK, link.type());
    }

    // --- V2 layout tests ---

    @Test
    void testColumn() {
        var column = Column.builder()
                .alignment(Alignment.CENTER)
                .gap(Gap.MEDIUM)
                .add(Badge.of("ONLINE"))
                .add(Badge.of("READY", Severity.SUCCESS))
                .build();

        assertEquals(Alignment.CENTER, column.alignment());
        assertEquals(Gap.MEDIUM, column.gap());
        assertEquals(2, column.children().size());
        assertEquals(ComponentType.COLUMN, column.type());
    }

    @Test
    void testColumnDefaults() {
        var column = Column.builder().build();

        assertEquals(Alignment.START, column.alignment());
        assertEquals(Gap.NONE, column.gap());
        assertTrue(column.children().isEmpty());
    }

    @Test
    void testRow() {
        var row = Row.builder()
                .gap(Gap.SMALL)
                .wrap(true)
                .add(Badge.of("A"))
                .add(Badge.of("B"))
                .build();

        assertEquals(Gap.SMALL, row.gap());
        assertTrue(row.wrap());
        assertEquals(2, row.children().size());
        assertEquals(ComponentType.ROW, row.type());
    }

    @Test
    void testGrid() {
        var grid = Grid.builder(3)
                .gap(Gap.LARGE)
                .add(Badge.of("One"))
                .add(Badge.of("Two"))
                .add(Badge.of("Three"))
                .build();

        assertEquals(3, grid.columns());
        assertEquals(Gap.LARGE, grid.gap());
        assertEquals(3, grid.children().size());
        assertEquals(ComponentType.GRID, grid.type());
    }

    @Test
    void testPanel() {
        var panel = Panel.builder("Plugins")
                .collapsible(true)
                .collapsed(false)
                .add(new KeyValueImpl("Count", "23"))
                .build();

        assertEquals("Plugins", panel.title());
        assertTrue(panel.collapsible());
        assertFalse(panel.collapsed());
        assertEquals(1, panel.children().size());
        assertEquals(ComponentType.PANEL, panel.type());
    }

    @Test
    void testNestedLayout() {
        var layout = Column.builder()
                .add(Row.builder()
                        .add(Badge.of("ONLINE", Severity.SUCCESS))
                        .add(Stat.builder("TPS", "19.95").unit("tps").build())
                        .build())
                .add(Alert.of(Severity.WARNING, "High GC", "GC pause > 50ms"))
                .build();

        assertEquals(ComponentType.COLUMN, layout.type());
        assertEquals(2, layout.children().size());
        assertEquals(ComponentType.ROW, layout.children().get(0).type());
        assertEquals(ComponentType.ALERT, layout.children().get(1).type());

        var row = (Row) layout.children().get(0);
        assertEquals(2, row.children().size());
        assertEquals(ComponentType.BADGE, row.children().get(0).type());
        assertEquals(ComponentType.STAT, row.children().get(1).type());
    }
}
