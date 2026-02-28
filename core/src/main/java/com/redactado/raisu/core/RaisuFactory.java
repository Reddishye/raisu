package com.redactado.raisu.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.redactado.raisu.Raisu;
import com.redactado.raisu.platform.RaisuPlatform;
import org.jetbrains.annotations.NotNull;

public final class RaisuFactory {

    private RaisuFactory() {}

    @NotNull
    public static Raisu create(@NotNull RaisuPlatform platform) {
        Injector injector = Guice.createInjector(new RaisuModule(platform));
        return injector.getInstance(Raisu.class);
    }
}
