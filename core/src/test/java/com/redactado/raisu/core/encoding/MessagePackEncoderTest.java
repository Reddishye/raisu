package com.redactado.raisu.core.encoding;

import static org.junit.jupiter.api.Assertions.*;

import com.redactado.raisu.core.category.CategoryBuilderImpl;
import com.redactado.raisu.core.component.KeyValueImpl;
import com.redactado.raisu.core.config.EncodeConfigBuilderImpl;
import com.redactado.raisu.core.snapshot.SnapshotBuilderImpl;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

class MessagePackEncoderTest {

    private final MessagePackEncoder encoder = new MessagePackEncoder();

    @Test
    void testEncodeWithoutEncryption() throws Exception {
        var snapshot = new SnapshotBuilderImpl()
                .serverVersion("Paper 1.21.4")
                .javaVersion("21.0.0")
                .addCategory(new CategoryBuilderImpl()
                        .id("test")
                        .name(Component.text("Test"))
                        .icon("🧪")
                        .add(new KeyValueImpl("key", "value"))
                        .build())
                .build();

        var config = new EncodeConfigBuilderImpl()
                .encrypt(false)
                .build();

        byte[] encoded = encoder.encode(snapshot, config);

        assertNotNull(encoded);
        assertTrue(encoded.length > 0);
    }

    @Test
    void testEncodeWithEncryption() throws Exception {
        var snapshot = new SnapshotBuilderImpl()
                .serverVersion("Paper 1.21.4")
                .javaVersion("21.0.0")
                .build();

        var config = new EncodeConfigBuilderImpl()
                .password("secret123")
                .build();

        byte[] encoded = encoder.encode(snapshot, config);

        assertNotNull(encoded);
        assertTrue(encoded.length > 0);
    }
}
