package io.bankbridge;

import io.bankbridge.handler.BanksCacheBased;
import io.bankbridge.handler.BanksRemoteCalls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;

import static spark.Spark.exception;

/**
 * Starting point of application. Also acts as controller
 * There are two version of the API:
 * /v1/banks/all
 * /v2/banks/all
 * Response can be fetched with pagination and filtering to v1 and v2 of the banks APIs.
 * Ex.
 * localhost:8080/v1/banks/all
 * localhost:8080/v1/banks/all?countryCode=NO&name=Norway National Bank
 * localhost:8080/v1/banks/all?products=accounts
 * localhost:8080/v1/banks/all?page=1&pageSize=3&countryCode=NO
 * localhost:8080/v2/banks/all
 * localhost:8080/v2/banks/all?countryCode=SE
 * localhost:8080/v2/banks/all?name=Mbanken
 * localhost:8080/v2/banks/all?page=2
 * localhost:8080/v2/banks/all?auth=oauth
 * <p>
 * Default values: page=1, pageSize=5
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Logger LOGGER = LoggerFactory.getLogger(Main.class);

        Service service = Service.ignite()
                .port(8080)
                .threadPool(20);

        LOGGER.debug("Server started on port 8080");

        BanksCacheBased.init();
        BanksRemoteCalls.init();
        service.get("/v1/banks/all", (request, response) -> BanksCacheBased.handle(request, response));
        service.get("/v2/banks/all", (request, response) -> BanksRemoteCalls.handle(request, response));
        exception(Exception.class, (error, request, response) -> {
            LOGGER.error("--------------error occurred-----------: " + error.getMessage() + " " + error);
            response.type("application/text");
            response.body(error.getMessage());
            response.status(400);
        });
    }
}