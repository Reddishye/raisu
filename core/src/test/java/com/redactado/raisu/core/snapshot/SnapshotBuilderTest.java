package com.redactado.raisu.core.snapshot;

import static org.junit.jupiter.api.Assertions.*;

import com.redactado.raisu.core.category.CategoryBuilderImpl;
import com.redactado.raisu.snapshot.Snapshot;
import java.time.Instant;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

class SnapshotBuilderTest {

    @Test
    void testBuildSnapshot() {
        Instant now = Instant.now();

        Snapshot snapshot = new SnapshotBuilderImpl()
                .timestamp(now)
                .serverVersion("Paper 1.21.4")
                .javaVersion("21.0.0")
                .build();

        assertEquals(now, snapshot.timestamp());
        assertEquals("Paper 1.21.4", snapshot.serverVersion());
        assertEquals("21.0.0", snapshot.javaVersion());
        assertTrue(snapshot.categories().isEmpty());
    }

    @Test
    void testWithCategory() {
        var category = new CategoryBuilderImpl()
                .id("test")
                .name(Component.text("Test"))
                .icon("🧪")
                .build();

        Snapshot snapshot = new SnapshotBuilderImpl().addCategory(category).build();

        assertEquals(1, snapshot.categories().size());
        assertTrue(snapshot.hasCategory("test"));
        assertEquals(category, snapshot.getCategory("test"));
    }

    @Test
    void testGetNonExistentCategory() {
        Snapshot snapshot = new SnapshotBuilderImpl().build();

        assertThrows(IllegalArgumentException.class, () -> snapshot.getCategory("nonexistent"));
    }
}
