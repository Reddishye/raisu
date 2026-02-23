package com.redactado.raisu.core.config;

import static org.junit.jupiter.api.Assertions.*;

import com.redactado.raisu.config.EncodeConfig;
import com.redactado.raisu.config.PasteProvider;
import org.junit.jupiter.api.Test;

class EncodeConfigBuilderTest {

    @Test
    void testDefaultProvider() {
        EncodeConfig config = new EncodeConfigBuilderImpl().build();

        assertEquals(PasteProvider.HASTEBIN, config.provider());
    }

    @Test
    void testWithProvider() {
        EncodeConfig config =
                new EncodeConfigBuilderImpl().provider(PasteProvider.PASTES_DEV).build();

        assertEquals(PasteProvider.PASTES_DEV, config.provider());
    }
}
