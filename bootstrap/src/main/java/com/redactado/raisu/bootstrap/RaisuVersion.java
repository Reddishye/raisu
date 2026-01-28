package com.redactado.raisu.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class RaisuVersion {

    public static final String VERSION;

    static {
        Properties props = new Properties();
        try (InputStream is = RaisuVersion.class.getResourceAsStream("/raisu.properties")) {
            if (is == null) {
                throw new IllegalStateException("raisu.properties not found");
            }
            props.load(is);
            VERSION = props.getProperty("version", "UNKNOWN");
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private RaisuVersion() {}
}
