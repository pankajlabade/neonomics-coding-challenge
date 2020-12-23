package io.bankbridge.util;

import io.bankbridge.model.BankModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP Client for remote URLs
 */
public class OkHttpUtils {

    private static final OkHttpClient httpClient = new OkHttpClient();

    public static List<BankModel> fetchRemoteBanks(Map<String, String> config) throws Exception {
        MockRemotes.init();
        List<BankModel> models = new ArrayList<>(config.size());
        config.forEach((name, url) -> {
            try {
                getBank(url, models);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return models;
    }

    /**
     * Get response for Remote URLS (ex. http://localhost:1234/cs)
     * Add response of each bank in List<BankModel> models
     *
     * @param url
     * @param models
     * @throws IOException
     */
    public static void getBank(String url, List<BankModel> models) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            // Get response body
            BankModel model = readModel(response.body().string());
            models.add(model);
        }
    }

    private static BankModel readModel(String text) throws IOException {
        return Mapper.INSTANCE.getObjectMapper().readValue(text, BankModel.class);
    }

}
