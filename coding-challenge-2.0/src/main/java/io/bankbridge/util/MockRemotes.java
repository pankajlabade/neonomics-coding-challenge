package io.bankbridge.util;

import spark.Service;
import spark.Spark;

/**
 * This class mocks the remote URLs. Returns response as JSON - list of BankModel
 * v2 should return: name, id, countryCode and auth
 */
public class MockRemotes {

    public static final Service remoteService = Service.ignite().port(1234).threadPool(20);

    public static void init() throws Exception {
        remoteService.get("/bes", (request, response) -> "{\n" +
                "\"bic\":\"PARIATURDEU0XXX\",\n" +
                "\"name\":\"Banco de espiritu santo\",\n" +
                "\"countryCode\":\"GB\",\n" +
                "\"auth\":\"oauth\"\n" +
                "}");
        remoteService.get("/cs", (request, response) -> "{\n" +
                "\"bic\":\"CUPIDATATSP1XXX\",\n" +
                "\"name\":\"Credit Sweets\",\n" +
                "\"countryCode\":\"CH\",\n" +
                "\"auth\":\"open-id\"\n" +
                "}");
        remoteService.get("/rbf", (request, response) -> "{\n" +
                "\"bic\":\"DOLORENOR2XXX\",\n" +
                "\"name\":\"Royal Bank of Fun\",\n" +
                "\"countryCode\":\"GB\",\n" +
                "\"auth\":\"oauth\"\n" +
                "}");
        remoteService.get("/bcd", (request, response) -> "{\n" +
                "\"bic\":\"DESERUNTSP3XXX\",\n" +
                "\"name\":\"Banco Con Deserts\",\n" +
                "\"countryCode\":\"SP\",\n" +
                "\"auth\":\"ssl-certificate\"\n" +
                "}");
        remoteService.get("/mbn", (request, response) -> "{\n" +
                "\"bic\":\"MOLLITNOR4XXX\",\n" +
                "\"name\":\"Mbanken\",\n" +
                "\"countryCode\":\"NO\",\n" +
                "\"auth\":\"open-id\"\n" +
                "}");
        remoteService.get("/mbs", (request, response) -> "{\n" +
                "\"bic\":\"MOLLITSWE5XXX\",\n" +
                "\"name\":\"Mbanken\",\n" +
                "\"countryCode\":\"SE\",\n" +
                "\"auth\":\"oauth\"\n" +
                "}");
        remoteService.get("/br", (request, response) -> "{\n" +
                "\"bic\":\"REPSP6XXX\",\n" +
                "\"name\":\"Banco Republico\",\n" +
                "\"countryCode\":\"SP\",\n" +
                "\"auth\":\"oauth\"\n" +
                "}");
        remoteService.get("/amt", (request, response) -> "{\n" +
                "\"bic\":\"ANIMDEU7XXX\",\n" +
                "\"name\":\"Animat\",\n" +
                "\"countryCode\":\"DE\",\n" +
                "\"auth\":\"oauth\"\n" +
                "}");
        remoteService.get("/bdr", (request, response) -> "{\n" +
                "\"bic\":\"DODEU8XXX\",\n" +
                "\"name\":\"Bank Dariatur\",\n" +
                "\"countryCode\":\"CH\",\n" +
                "\"auth\":\"open-id\"\n" +
                "}");
        remoteService.get("/bds", (request, response) -> "{\n" +
                "\"bic\":\"DOLORENOR9XXX\",\n" +
                "\"name\":\"Bank Dolores\",\n" +
                "\"countryCode\":\"NO\",\n" +
                "\"auth\":\"ssl-certificate\"\n" +
                "}");
        remoteService.get("/con", (request, response) -> "{\n" +
                "\"bic\":\"CONSSWE10XXX\",\n" +
                "\"name\":\"Constantie Bank\",\n" +
                "\"countryCode\":\"SE\",\n" +
                "\"auth\":\"oauth\"\n" +
                "}");
        remoteService.get("/nnb", (request, response) -> "{\n" +
                "\"bic\":\"NONNOR11XXX\",\n" +
                "\"name\":\"Norway National Bank\",\n" +
                "\"countryCode\":\"NO\",\n" +
                "\"auth\":\"ssl-certificate\"\n" +
                "}");
        remoteService.get("/nsb", (request, response) -> "{\n" +
                "\"bic\":\"NSAVNOR12XXX\",\n" +
                "\"name\":\"National Savings Bank\",\n" +
                "\"countryCode\":\"NO\",\n" +
                "\"auth\":\"oauth\"\n" +
                "}");
        remoteService.get("/bnu", (request, response) -> "{\n" +
                "\"bic\":\"MOLLITSP13XXX\",\n" +
                "\"name\":\"Bank Nulla\",\n" +
                "\"countryCode\":\"PT\",\n" +
                "\"auth\":\"oauth\"\n" +
                "}");
        remoteService.get("/onb", (request, response) -> "{\n" +
                "\"bic\":\"VELITDEU14XXX\",\n" +
                "\"name\":\"One Nations Bank\",\n" +
                "\"countryCode\":\"DE\",\n" +
                "\"auth\":\"oauth\"\n" +
                "}");
        remoteService.get("/fgg", (request, response) -> "{\n" +
                "\"bic\":\"FIRSTSP15XXX\",\n" +
                "\"name\":\"First Guarantee Group\",\n" +
                "\"countryCode\":\"PT\",\n" +
                "\"auth\":\"ssl-certificate\"\n" +
                "}");
        remoteService.get("/blc", (request, response) -> "{\n" +
                "\"bic\":\"ULLAMCOSP16XXX\",\n" +
                "\"name\":\"Bank Ullamco\",\n" +
                "\"countryCode\":\"SP\",\n" +
                "\"auth\":\"ssl-certificate\"\n" +
                "}");
        remoteService.get("/lnb", (request, response) -> "{\n" +
                "\"bic\":\"NULLASP17XXX\",\n" +
                "\"name\":\"Last National Bank\",\n" +
                "\"countryCode\":\"NO\",\n" +
                "\"auth\":\"ssl-certificate\"\n" +
                "}");
        remoteService.get("/scu", (request, response) -> "{\n" +
                "\"bic\":\"SOARCDEU18XXX\",\n" +
                "\"name\":\"Soar Credit Union\",\n" +
                "\"countryCode\":\"DE\",\n" +
                "\"auth\":\"oauth\"\n" +
                "}");
        remoteService.get("/csh", (request, response) -> "{\n" +
                "\"bic\":\"ETSWE19XXX\",\n" +
                "\"name\":\"Cash Financial\",\n" +
                "\"countryCode\":\"SE\",\n" +
                "\"auth\":\"ssl-certificate\"\n" +
                "}");
    }

    public static void end() {
        remoteService.stop();
        Service.ignite().stop();
        Spark.stop();
    }

}