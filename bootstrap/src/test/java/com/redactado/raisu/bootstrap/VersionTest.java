package com.redactado.raisu.bootstrap;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VersionTest {

    @Test
    void testParseMajorMinorPatch() {
        Version v = Version.parse("1.2.3");
        assertEquals("1.2.3", v.toString());
    }

    @Test
    void testParseWithSuffix() {
        Version v = Version.parse("1.0.0-SNAPSHOT");
        assertEquals("1.0.0-SNAPSHOT", v.toString());
    }

    @Test
    void testComparison() {
        Version v1 = Version.parse("1.0.0");
        Version v2 = Version.parse("1.0.1");
        Version v3 = Version.parse("2.0.0");

        assertTrue(v2.isNewerThan(v1));
        assertTrue(v3.isNewerThan(v2));
        assertFalse(v1.isNewerThan(v2));
    }

    @Test
    void testSnapshotVsRelease() {
        Version snapshot = Version.parse("1.0.0-SNAPSHOT");
        Version release = Version.parse("1.0.0");

        assertTrue(release.isNewerThan(snapshot));
        assertFalse(snapshot.isNewerThan(release));
    }

    @Test
    void testEquals() {
        Version v1 = Version.parse("1.2.3");
        Version v2 = Version.parse("1.2.3");

        assertEquals(v1, v2);
        assertEquals(v1.hashCode(), v2.hashCode());
    }

    @Test
    void testInvalidVersion() {
        Version v = Version.parse("invalid");
        assertEquals("0.0.0", v.toString());
    }
}
