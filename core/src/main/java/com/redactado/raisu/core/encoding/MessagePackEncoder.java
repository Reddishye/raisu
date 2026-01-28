package com.redactado.raisu.core.encoding;

import com.redactado.raisu.category.Category;
import com.redactado.raisu.component.Component;
import com.redactado.raisu.component.Graph;
import com.redactado.raisu.component.KeyValue;
import com.redactado.raisu.component.List;
import com.redactado.raisu.component.ProgressBar;
import com.redactado.raisu.component.Table;
import com.redactado.raisu.component.Text;
import com.redactado.raisu.component.Tree;
import com.redactado.raisu.config.EncodeConfig;
import com.redactado.raisu.snapshot.Snapshot;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;

public final class MessagePackEncoder implements Encoder {

    private final AESCipher cipher = new AESCipher();

    @Override
    @NotNull
    public byte[] encode(@NotNull Snapshot snapshot, @NotNull EncodeConfig config) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (MessagePacker packer = MessagePack.newDefaultPacker(baos)) {
            packSnapshot(packer, snapshot);
        }

        byte[] data = baos.toByteArray();

        if (config.encrypt() && config.password() != null) {
            return cipher.encrypt(data, config.password());
        }

        return data;
    }

    private void packSnapshot(@NotNull MessagePacker packer, @NotNull Snapshot snapshot) throws IOException {
        packer.packMapHeader(4);

        packer.packString("timestamp");
        packer.packLong(snapshot.timestamp().toEpochMilli());

        packer.packString("serverVersion");
        packer.packString(snapshot.serverVersion());

        packer.packString("javaVersion");
        packer.packString(snapshot.javaVersion());

        packer.packString("categories");
        packer.packArrayHeader(snapshot.categories().size());
        for (Category category : snapshot.categories()) {
            packCategory(packer, category);
        }
    }

    private void packCategory(@NotNull MessagePacker packer, @NotNull Category category) throws IOException {
        packer.packMapHeader(5);

        packer.packString("id");
        packer.packString(category.id());

        packer.packString("name");
        packer.packString(GsonComponentSerializer.gson().serialize(category.name()));

        packer.packString("icon");
        packer.packString(category.icon());

        packer.packString("priority");
        packer.packInt(category.priority());

        packer.packString("components");
        packer.packArrayHeader(category.components().size());
        for (Component component : category.components()) {
            packComponent(packer, component);
        }
    }

    private void packComponent(@NotNull MessagePacker packer, @NotNull Component component) throws IOException {
        packer.packMapHeader(2);

        packer.packString("type");
        packer.packString(component.type().name());

        packer.packString("data");
        switch (component.type()) {
            case KEY_VALUE -> packKeyValue(packer, (KeyValue) component);
            case TEXT -> packText(packer, (Text) component);
            case TABLE -> packTable(packer, (Table) component);
            case LIST -> packList(packer, (List) component);
            case PROGRESS_BAR -> packProgressBar(packer, (ProgressBar) component);
            case GRAPH -> packGraph(packer, (Graph) component);
            case TREE -> packTree(packer, (Tree) component);
        }
    }

    private void packKeyValue(@NotNull MessagePacker packer, @NotNull KeyValue kv) throws IOException {
        packer.packMapHeader(2);
        packer.packString("key");
        packer.packString(kv.key());
        packer.packString("value");
        packer.packString(kv.value());
    }

    private void packText(@NotNull MessagePacker packer, @NotNull Text text) throws IOException {
        packer.packMapHeader(1);
        packer.packString("content");
        packer.packString(text.content());
    }

    private void packTable(@NotNull MessagePacker packer, @NotNull Table table) throws IOException {
        packer.packMapHeader(2);

        packer.packString("headers");
        packer.packArrayHeader(table.headers().size());
        for (String header : table.headers()) {
            packer.packString(header);
        }

        packer.packString("rows");
        packer.packArrayHeader(table.rows().size());
        for (java.util.List<String> row : table.rows()) {
            packer.packArrayHeader(row.size());
            for (String cell : row) {
                packer.packString(cell);
            }
        }
    }

    private void packList(@NotNull MessagePacker packer, @NotNull List list) throws IOException {
        packer.packMapHeader(1);
        packer.packString("items");
        packer.packArrayHeader(list.items().size());
        for (String item : list.items()) {
            packer.packString(item);
        }
    }

    private void packProgressBar(@NotNull MessagePacker packer, @NotNull ProgressBar bar) throws IOException {
        packer.packMapHeader(3);
        packer.packString("label");
        packer.packString(bar.label());
        packer.packString("current");
        packer.packDouble(bar.current());
        packer.packString("max");
        packer.packDouble(bar.max());
    }

    private void packGraph(@NotNull MessagePacker packer, @NotNull Graph graph) throws IOException {
        packer.packMapHeader(2);
        packer.packString("title");
        packer.packString(graph.title());
        packer.packString("dataPoints");
        packer.packMapHeader(graph.dataPoints().size());
        for (Map.Entry<String, Double> entry : graph.dataPoints().entrySet()) {
            packer.packString(entry.getKey());
            packer.packDouble(entry.getValue());
        }
    }

    private void packTree(@NotNull MessagePacker packer, @NotNull Tree tree) throws IOException {
        packer.packMapHeader(1);
        packer.packString("root");
        packTreeNode(packer, tree.root());
    }

    private void packTreeNode(@NotNull MessagePacker packer, @NotNull Tree.TreeNode node) throws IOException {
        packer.packMapHeader(2);
        packer.packString("label");
        packer.packString(node.label());
        packer.packString("children");
        packer.packArrayHeader(node.children().size());
        for (Tree.TreeNode child : node.children()) {
            packTreeNode(packer, child);
        }
    }
}
