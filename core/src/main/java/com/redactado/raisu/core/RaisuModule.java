package com.redactado.raisu.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.redactado.raisu.Raisu;
import com.redactado.raisu.config.PasteProvider;
import com.redactado.raisu.core.category.CategoryRegistry;
import com.redactado.raisu.core.encoding.Encoder;
import com.redactado.raisu.core.encoding.MessagePackEncoder;
import com.redactado.raisu.core.paste.HastebinClient;
import com.redactado.raisu.core.paste.PasteClient;
import com.redactado.raisu.core.paste.PastesDevClient;
import org.bukkit.plugin.Plugin;

public final class RaisuModule extends AbstractModule {

    private final Plugin plugin;

    public RaisuModule(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(Plugin.class).toInstance(plugin);
        bind(Raisu.class).to(RaisuImpl.class);
        bind(Encoder.class).to(MessagePackEncoder.class);
        bind(CategoryRegistry.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    PasteClient providePasteClient() {
        return new PasteClientRouter();
    }

    private static class PasteClientRouter implements PasteClient {
        private final HastebinClient hastebin = new HastebinClient();
        private final PastesDevClient pastesDev = new PastesDevClient();

        @Override
        public String upload(byte[] data, PasteProvider provider) throws java.io.IOException {
            return switch (provider) {
                case HASTEBIN -> hastebin.upload(data, provider);
                case PASTES_DEV -> pastesDev.upload(data, provider);
            };
        }
    }
}
