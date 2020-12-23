package io.bankbridge.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.bankbridge.model.BankModel;
import io.bankbridge.model.BankModelList;
import io.bankbridge.util.FilterResult;
import io.bankbridge.util.Mapper;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service layer of /v1/banks/all api
 */
public class BanksCacheBased {

    private static Logger LOGGER = LoggerFactory.getLogger(BanksCacheBased.class);
    public static CacheManager cacheManager;

    /**
     * Initialize a cache with data from banks-v1.json
     *
     * @throws Exception
     */
    public static void init() throws Exception {
        cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder().withCache("banks", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(String.class, BankModel.class, ResourcePoolsBuilder.heap(20)))
                .build();
        cacheManager.init();
        Cache cache = cacheManager.getCache("banks", String.class, BankModel.class);
        try {
            // Created only one instance of ObjectMapper for whole application. Here, replaced existing new ObjectMapper() code with that.
            BankModelList models = Mapper.INSTANCE.getObjectMapper().readValue(
                    Thread.currentThread().getContextClassLoader().getResource("banks-v1.json"), BankModelList.class);
            for (BankModel model : models.banks) {
                cache.put(model.bic, model);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Handling /v1/banks/all api call.
     * Defining response type as JSON. Get filtered and paginated response based on QueryParams provided in URL
     *
     * @param request
     * @param response
     * @return
     */
    public static String handle(Request request, Response response) {
        response.type("application/json");
        List<BankModel> result = getBankModels();
        try {
            List<BankModel> resultFiltered = FilterResult.filter(request, response, result);

            /**
             * v1 should return: name, id, countryCode and product
             */
            Set<BankModel> v1BankModels = resultFiltered.stream()
                    .map(b -> new BankModel(b.getBic(), b.getName(), b.getCountryCode(), b.getProducts())).collect(Collectors.toSet());

            /**
             * Created only one instance of ObjectMapper for whole application.
             * Here, replaced existing new ObjectMapper() code with that.
             */
            String resultAsString = Mapper.INSTANCE.getObjectMapper().writeValueAsString(v1BankModels);

            return resultAsString;
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Error while processing request");
        }

    }

    @NotNull
    public static List<BankModel> getBankModels() {
        List<BankModel> result = new ArrayList<>();
        cacheManager.getCache("banks", String.class, BankModel.class).forEach(entry -> {
            result.add(entry.getValue());
        });
        return result;
    }

}
