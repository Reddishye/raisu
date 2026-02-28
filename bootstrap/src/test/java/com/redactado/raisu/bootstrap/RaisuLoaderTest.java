package com.redactado.raisu.bootstrap;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RaisuLoaderTest {

    private static final Logger LOGGER = Logger.getLogger("RaisuLoaderTest");

    @BeforeEach
    void setUp() {
        RaisuLoader.reset();
    }

    @Test
    void testLoadFirstInstance() {
        Object impl = new Object();
        Object result = RaisuLoader.load(LOGGER, "TestPlugin", "1.0.0", impl);

        assertSame(impl, result);
        assertEquals(Version.parse("1.0.0"), RaisuLoader.getCurrentVersion());
        assertEquals("TestPlugin", RaisuLoader.getLoadedFrom());
    }

    @Test
    void testLoadNewerVersion() {
        Object impl1 = new Object();
        Object impl2 = new Object();

        RaisuLoader.load(LOGGER, "TestPlugin", "1.0.0", impl1);
        Object result = RaisuLoader.load(LOGGER, "TestPlugin2", "2.0.0", impl2);

        assertSame(impl2, result);
        assertEquals(Version.parse("2.0.0"), RaisuLoader.getCurrentVersion());
        assertEquals("TestPlugin2", RaisuLoader.getLoadedFrom());
    }

    @Test
    void testLoadOlderVersion() {
        Object impl1 = new Object();
        Object impl2 = new Object();

        RaisuLoader.load(LOGGER, "TestPlugin", "2.0.0", impl1);
        Object result = RaisuLoader.load(LOGGER, "TestPlugin2", "1.0.0", impl2);

        assertSame(impl1, result);
        assertEquals(Version.parse("2.0.0"), RaisuLoader.getCurrentVersion());
        assertEquals("TestPlugin", RaisuLoader.getLoadedFrom());
    }

    @Test
    void testLoadSameVersion() {
        Object impl1 = new Object();
        Object impl2 = new Object();

        RaisuLoader.load(LOGGER, "TestPlugin", "1.0.0", impl1);
        Object result = RaisuLoader.load(LOGGER, "TestPlugin2", "1.0.0", impl2);

        // First loaded wins on same version
        assertSame(impl1, result);
        assertEquals(Version.parse("1.0.0"), RaisuLoader.getCurrentVersion());
        assertEquals("TestPlugin", RaisuLoader.getLoadedFrom());
    }
}
