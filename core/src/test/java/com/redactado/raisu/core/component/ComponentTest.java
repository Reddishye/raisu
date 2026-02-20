package com.redactado.raisu.core.component;

import static org.junit.jupiter.api.Assertions.*;

import com.redactado.raisu.component.Component.ComponentType;
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
        assertEquals("Item 1", list.items().get(0));
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
}
