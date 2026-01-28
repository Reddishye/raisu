package com.redactado.raisu.core.component;

import com.redactado.raisu.component.Tree;
import org.jetbrains.annotations.NotNull;

public final class TreeImpl implements Tree {

    private final TreeNode root;

    public TreeImpl(@NotNull TreeNode root) {
        this.root = root;
    }

    @Override
    @NotNull
    public TreeNode root() {
        return root;
    }
}
