package com.redactado.raisu.core.config;

import static org.junit.jupiter.api.Assertions.*;

import com.redactado.raisu.config.EncodeConfig;
import com.redactado.raisu.config.PasteProvider;
import org.junit.jupiter.api.Test;

class EncodeConfigBuilderTest {

    @Test
    void testDefaultConfig() {
        EncodeConfig config = new EncodeConfigBuilderImpl().build();

        assertEquals(PasteProvider.HASTEBIN, config.provider());
        assertFalse(config.encrypt());
        assertNull(config.password());
    }

    @Test
    void testWithEncryption() {
        EncodeConfig config = new EncodeConfigBuilderImpl().password("secret").build();

        assertTrue(config.encrypt());
        assertEquals("secret", config.password());
    }

    @Test
    void testWithProvider() {
        EncodeConfig config =
                new EncodeConfigBuilderImpl().provider(PasteProvider.PASTES_DEV).build();

        assertEquals(PasteProvider.PASTES_DEV, config.provider());
    }

    @Test
    void testEncryptWithoutPassword() {
        assertThrows(IllegalStateException.class, () -> new EncodeConfigBuilderImpl().encrypt(true).build());
    }
}
