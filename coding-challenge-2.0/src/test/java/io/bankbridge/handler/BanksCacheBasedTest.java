package io.bankbridge.handler;

import io.bankbridge.model.BankModel;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BanksCacheBasedTest {

    @Test
    public void testCacheBasedBanks() throws Exception {
        BanksCacheBased.init();
        List<BankModel> bankModels = BanksCacheBased.getBankModels();
        assertNotNull(bankModels);
        assertFalse(bankModels.isEmpty());
        assertEquals(20, bankModels.size());
        assertEquals("LNBSP17XXX", bankModels.stream().filter(b -> b.getName()
                .equals("Last National Bank")).findFirst().get().getBic());
        assertEquals("Soar Credit Union", bankModels.stream().filter(b -> b.getBic().
                equals("SOARCDEU18XXX")).findFirst().get().getName());
        assertEquals("SP", bankModels.stream().filter(b -> b.getName()
                .equals("Bank Ullamco")).findFirst().get().getCountryCode());
        assertEquals("ssl-certificate", bankModels.stream().filter(b -> b.getName()
                .equals("First Guarantee Group")).findFirst().get().getAuth());
    }

}