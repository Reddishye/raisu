package com.redactado.raisu.core.paste;

import com.redactado.raisu.config.PasteProvider;
import java.io.IOException;
import java.util.Base64;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public final class HastebinClient implements PasteClient {

    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");

    @Override
    @NotNull
    public String upload(@NotNull byte[] data, @NotNull PasteProvider provider) throws IOException {
        String encoded = Base64.getEncoder().encodeToString(data);

        RequestBody body = RequestBody.create(encoded, TEXT);
        Request request = new Request.Builder()
                .url(provider.getDefaultUrl() + "/documents")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Hastebin upload failed: HTTP " + response.code());
            }

            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new IOException("Empty response from Hastebin");
            }

            String json = responseBody.string();
            JSONObject result = new JSONObject(json);
            String key = result.getString("key");

            return provider.getDefaultUrl() + "/" + key;
        }
    }
}
