package io.bankbridge.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.bankbridge.model.BankModel;
import io.bankbridge.util.FilterResult;
import io.bankbridge.util.Mapper;
import io.bankbridge.util.OkHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;

/**
 * Service layer of /v2/banks/all api
 */
public class BanksRemoteCalls {

    public static List<BankModel> result;
    private static final Logger LOGGER = LoggerFactory.getLogger(BanksRemoteCalls.class);
    private static Map config;

    /**
     * Initialize a Map named config with data from banks-v2.json
     * Fetching response from URLs mentioned in banks-v2.json and storing it in List<BankModel> result
     */
    public static void init() throws Exception {
        config = getConfig();
        result = OkHttpUtils.fetchRemoteBanks(config);
    }

    /**
     * Read banks-v2.json into Map
     *
     * @return Map of bank name and remote URL
     */
    public static Map getConfig() throws java.io.IOException {
        return Mapper.INSTANCE.getObjectMapper()
                .readValue(Thread.currentThread().getContextClassLoader().getResource("banks-v2.json"), Map.class);
    }

    /**
     * Handling /v2/banks/all api call.
     * Defined response type as JSON. Get filtered and paginated response based on QueryParams provided in URL
     *
     * @param request  HTTP Request
     * @param response list of BankModel in JSON format
     */
    public static String handle(Request request, Response response) {

        response.type("application/json");
        List<BankModel> resultFiltered = FilterResult.filter(request, response, result);

        String resultAsString;
        try {
            resultAsString = Mapper.INSTANCE.getObjectMapper().writeValueAsString(resultFiltered);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Error while processing request");
        }

        return resultAsString;
    }

}
