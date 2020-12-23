package io.bankbridge.util;

import io.bankbridge.model.BankModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class to filter the response on the basis of query parameters provided
 */
public class FilterResult {

    private static final Set<String> ELEMENTS = Stream.of("bic", "name", "countryCode", "auth", "products", "page", "pageSize")
            .collect(Collectors.toCollection(HashSet::new));
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterResult.class);

    public static List<BankModel> filter(Request request, Response response, List<BankModel> result) {

        if (!ELEMENTS.containsAll(request.queryParams())) {
            LOGGER.error("Wrong URL. Invalid query parameters. Valid query parameters are: " + ELEMENTS);
            throw new RuntimeException("Wrong URL. Invalid query parameters. Valid query parameters are: " + ELEMENTS);
        }

        String bic = request.queryParams("bic");
        String name = request.queryParams("name");
        String countryCode = request.queryParams("countryCode");
        String auth = request.queryParams("auth");
        String products = request.queryParams("products");
        String page = request.queryParams("page");
        String pageSize = request.queryParams("pageSize");

        List<BankModel> resultFiltered = result;

        if (null != bic) {
            resultFiltered = resultFiltered.stream()
                    .filter(b -> b.getBic() != null)
                    .filter(b -> bic.equals(b.getBic()))
                    .collect(Collectors.toList());
        }

        if (null != name) {
            resultFiltered = resultFiltered.stream()
                    .filter(b -> b.getName() != null)
                    .filter(b -> name.equals(b.getName()))
                    .collect(Collectors.toList());
        }

        if (null != countryCode) {
            resultFiltered = resultFiltered.stream()
                    .filter(b -> b.getCountryCode() != null)
                    .filter(b -> countryCode.equals(b.getCountryCode()))
                    .collect(Collectors.toList());
        }

        if (null != auth) {
            resultFiltered = resultFiltered.stream()
                    .filter(b -> b.getAuth() != null)
                    .filter(b -> auth.equals(b.getAuth()))
                    .collect(Collectors.toList());
        }
        if (null != products) {
            resultFiltered = resultFiltered.stream()
                    .filter(b -> b.getProducts() != null)
                    .filter(b -> b.getProducts().contains(products))
                    .collect(Collectors.toList());
        }

        int pageNum = 1;
        int pageSizeNum = 5;
        if (null != page) {
            pageNum = Integer.parseInt(page);
        }
        if (null != pageSize) {
            pageSizeNum = Integer.parseInt(pageSize);
        }

        resultFiltered = getPage(resultFiltered, pageNum, pageSizeNum);

        return resultFiltered;
    }

    /**
     * a static getPage method that breaks a generic collection into a list of pages (which are also lists)
     * returns a view (not a new list) of the sourceList for the
     * range based on page and pageSize
     *
     * @param sourceList
     * @param page,      page number should start from 1
     * @param pageSize
     */
    public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
        if (pageSize <= 0 || page <= 0) {
            LOGGER.error("invalid page size: " + pageSize);
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }

        int fromIndex = (page - 1) * pageSize;
        if (sourceList == null || sourceList.size() <= fromIndex) {
            return Collections.emptyList();
        }

        // toIndex exclusive
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }
}
