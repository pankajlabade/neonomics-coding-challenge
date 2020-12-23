package io.bankbridge.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum Mapper {
    INSTANCE;
    private final ObjectMapper mapper = new ObjectMapper();

    Mapper() {
    }

    public ObjectMapper getObjectMapper() {
        return mapper;
    }
}
