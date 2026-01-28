package com.redactado.raisu.bootstrap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RaisuLoaderTest {

    private Plugin plugin;
    private Logger logger;

    @BeforeEach
    void setUp() {
        logger = mock(Logger.class);
        plugin = createMockPlugin("TestPlugin");

        // Reset loader state
        RaisuLoader.reset();
    }

    private Plugin createMockPlugin(String name) {
        Plugin p = mock(Plugin.class);
        when(p.getName()).thenReturn(name);
        when(p.getLogger()).thenReturn(logger);
        return p;
    }

    @Test
    void testLoadFirstInstance() {
        Object impl = new Object();
        Object result = RaisuLoader.load(plugin, "1.0.0", impl);

        assertSame(impl, result);
        assertEquals(Version.parse("1.0.0"), RaisuLoader.getCurrentVersion());
        assertEquals("TestPlugin", RaisuLoader.getLoadedFrom());
    }

    @Test
    void testLoadNewerVersion() {
        Object impl1 = new Object();
        Object impl2 = new Object();

        Plugin plugin2 = createMockPlugin("TestPlugin2");

        RaisuLoader.load(plugin, "1.0.0", impl1);
        Object result = RaisuLoader.load(plugin2, "2.0.0", impl2);

        assertSame(impl2, result);
        assertEquals(Version.parse("2.0.0"), RaisuLoader.getCurrentVersion());
        assertEquals("TestPlugin2", RaisuLoader.getLoadedFrom());
    }

    @Test
    void testLoadOlderVersion() {
        Object impl1 = new Object();
        Object impl2 = new Object();

        Plugin plugin2 = createMockPlugin("TestPlugin2");

        RaisuLoader.load(plugin, "2.0.0", impl1);
        Object result = RaisuLoader.load(plugin2, "1.0.0", impl2);

        assertSame(impl1, result);
        assertEquals(Version.parse("2.0.0"), RaisuLoader.getCurrentVersion());
        assertEquals("TestPlugin", RaisuLoader.getLoadedFrom());
    }

    @Test
    void testLoadSameVersion() {
        Object impl1 = new Object();
        Object impl2 = new Object();

        Plugin plugin2 = createMockPlugin("TestPlugin2");

        RaisuLoader.load(plugin, "1.0.0", impl1);
        Object result = RaisuLoader.load(plugin2, "1.0.0", impl2);

        // First loaded wins on same version
        assertSame(impl1, result);
        assertEquals(Version.parse("1.0.0"), RaisuLoader.getCurrentVersion());
        assertEquals("TestPlugin", RaisuLoader.getLoadedFrom());
    }
}
