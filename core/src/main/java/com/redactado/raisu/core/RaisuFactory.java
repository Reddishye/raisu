package com.redactado.raisu.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.redactado.raisu.Raisu;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class RaisuFactory {

    private RaisuFactory() {}

    @NotNull
    public static Raisu create(@NotNull Plugin plugin) {
        Injector injector = Guice.createInjector(new RaisuModule(plugin));
        return injector.getInstance(Raisu.class);
    }
}
