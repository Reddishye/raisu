package com.redactado.raisu.component;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public interface Tree extends Component {

    @NotNull
    TreeNode root();

    @Override
    default @NonNull ComponentType type() {
        return ComponentType.TREE;
    }

    interface TreeNode {
        @NotNull
        String label();

        @NotNull
        List<TreeNode> children();
    }
}
