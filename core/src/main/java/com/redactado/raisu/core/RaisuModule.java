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
import com.redactado.raisu.platform.RaisuPlatform;

public final class RaisuModule extends AbstractModule {

    private final RaisuPlatform platform;

    public RaisuModule(RaisuPlatform platform) {
        this.platform = platform;
    }

    @Override
    protected void configure() {
        bind(RaisuPlatform.class).toInstance(platform);
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
