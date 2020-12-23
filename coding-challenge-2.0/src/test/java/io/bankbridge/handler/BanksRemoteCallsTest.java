package io.bankbridge.handler;

import io.bankbridge.model.BankModel;
import io.bankbridge.util.MockRemotes;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BanksRemoteCallsTest {

    @Test
    public void testBanksRemoteCalls() throws Exception {
        BanksRemoteCalls.init();
        List<BankModel> remoteBankModels = BanksRemoteCalls.result;
        assertNotNull(remoteBankModels);
        assertFalse(remoteBankModels.isEmpty());
        assertEquals(20, remoteBankModels.size());
        assertEquals("NULLASP17XXX", remoteBankModels.stream().filter(b -> b.getName()
                .equals("Last National Bank")).findFirst().get().getBic());
        assertEquals("Soar Credit Union", remoteBankModels.stream().filter(b -> b.getBic().
                equals("SOARCDEU18XXX")).findFirst().get().getName());
        assertEquals("SP", remoteBankModels.stream().filter(b -> b.getName()
                .equals("Bank Ullamco")).findFirst().get().getCountryCode());
        assertEquals("ssl-certificate", remoteBankModels.stream().filter(b -> b.getName()
                .equals("First Guarantee Group")).findFirst().get().getAuth());
        MockRemotes.end();
    }

    @Test
    public void getConfig() throws IOException {
        Map config = BanksRemoteCalls.getConfig();
        assertNotNull(config);
        assertFalse(config.isEmpty());
        assertEquals(20, config.size());
        assertEquals("http://localhost:1234/cs", config.get("Credit Sweets"));
        assertEquals("http://localhost:1234/bnu", config.get("Bank Nulla"));
    }

}