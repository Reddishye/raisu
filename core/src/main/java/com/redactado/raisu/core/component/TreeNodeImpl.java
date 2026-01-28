package com.redactado.raisu.core.component;

import com.redactado.raisu.component.Tree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class TreeNodeImpl implements Tree.TreeNode {

    private final String label;
    private final List<Tree.TreeNode> children;

    public TreeNodeImpl(@NotNull String label, @NotNull List<Tree.TreeNode> children) {
        this.label = label;
        this.children = Collections.unmodifiableList(new ArrayList<>(children));
    }

    @Override
    @NotNull
    public String label() {
        return label;
    }

    @Override
    @NotNull
    public List<Tree.TreeNode> children() {
        return children;
    }
}
