package com.redactado.raisu.core.dump;

import static org.junit.jupiter.api.Assertions.*;

import com.redactado.raisu.core.category.CategoryBuilderImpl;
import com.redactado.raisu.core.component.*;
import com.redactado.raisu.config.PasteProvider;
import com.redactado.raisu.core.config.EncodeConfigBuilderImpl;
import com.redactado.raisu.core.encoding.MessagePackEncoder;
import com.redactado.raisu.core.paste.PastesDevClient;
import com.redactado.raisu.core.snapshot.SnapshotBuilderImpl;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

class DumpGeneratorTest {

    @Test
    void generateDump() throws Exception {
        var snapshot = new SnapshotBuilderImpl()
                .timestamp(Instant.ofEpochMilli(1708646400000L))
                .serverVersion("Paper 1.21.4")
                .javaVersion("21.0.3")
                .addCategory(new CategoryBuilderImpl()
                        .id("server-info")
                        .name(Component.text("Server Info"))
                        .icon("🖥️")
                        .priority(1)
                        .add(new KeyValueImpl("Server Version", "Paper 1.21.4"))
                        .add(new KeyValueImpl("Java Version", "21.0.3"))
                        .add(new KeyValueImpl("Max Players", "100"))
                        .add(new TextImpl(
                                "A high-performance Minecraft server running Paper 1.21.4 with 23 active plugins."))
                        .build())
                .addCategory(new CategoryBuilderImpl()
                        .id("performance")
                        .name(Component.text("Performance"))
                        .icon("📊")
                        .priority(2)
                        .add(new ProgressBarImpl("TPS", 19.8, 20.0))
                        .add(new ProgressBarImpl("Memory", 3072.0, 8192.0))
                        .add(new GraphImpl("TPS History", tpsHistory()))
                        .build())
                .addCategory(new CategoryBuilderImpl()
                        .id("players")
                        .name(Component.text("Players"))
                        .icon("👥")
                        .priority(3)
                        .add(new TableImpl(
                                List.of("Name", "Ping", "Game Mode"),
                                List.of(
                                        List.of("Steve", "23ms", "SURVIVAL"),
                                        List.of("Alex", "47ms", "CREATIVE"),
                                        List.of("Notch", "12ms", "SPECTATOR"))))
                        .add(new ListImpl(List.of("Notch", "Dinnerbone", "jeb_")))
                        .build())
                .addCategory(new CategoryBuilderImpl()
                        .id("plugins")
                        .name(Component.text("Plugins"))
                        .icon("🔌")
                        .priority(4)
                        .add(new ListImpl(List.of(
                                "EssentialsX 2.21.0",
                                "WorldEdit 7.3.1",
                                "LuckPerms 5.4.137",
                                "Vault 1.7.3",
                                "Citizens 2.0.33")))
                        .add(new TreeImpl(new TreeNodeImpl(
                                "Plugins",
                                List.of(
                                        new TreeNodeImpl(
                                                "EssentialsX",
                                                List.of(new TreeNodeImpl("Vault (soft-depend)", List.of()))),
                                        new TreeNodeImpl(
                                                "Citizens",
                                                List.of(new TreeNodeImpl("Vault (depend)", List.of())))))))
                        .build())
                .addCategory(new CategoryBuilderImpl()
                        .id("worlds")
                        .name(Component.text("Worlds"))
                        .icon("🌍")
                        .priority(5)
                        .add(new KeyValueImpl("World", "world"))
                        .add(new KeyValueImpl("Seed", "-1234567890123456789"))
                        .add(new KeyValueImpl("Time", "6000"))
                        .add(new KeyValueImpl("Weather", "Clear"))
                        .add(new TableImpl(
                                List.of("World", "Chunks Loaded", "Entities"),
                                List.of(
                                        List.of("world", "441", "1203"),
                                        List.of("world_nether", "128", "87"),
                                        List.of("world_the_end", "16", "4"))))
                        .build())
                .build();

        var config = new EncodeConfigBuilderImpl().encrypt(false).build();
        byte[] encoded = new MessagePackEncoder().encode(snapshot, config);

        String pasteKey = new PastesDevClient().upload(encoded, PasteProvider.PASTES_DEV);
        String shortcode = PasteProvider.PASTES_DEV.shortId() + ":" + pasteKey;

        System.out.println("=== FRONTEND TEST DUMP ===");
        System.out.println(shortcode);

        assertTrue(encoded.length > 0);
    }

    private Map<String, Double> tpsHistory() {
        var map = new LinkedHashMap<String, Double>();
        map.put("5m ago", 19.5);
        map.put("4m ago", 19.7);
        map.put("3m ago", 19.9);
        map.put("2m ago", 20.0);
        map.put("1m ago", 19.8);
        map.put("now", 19.8);
        return map;
    }
}
