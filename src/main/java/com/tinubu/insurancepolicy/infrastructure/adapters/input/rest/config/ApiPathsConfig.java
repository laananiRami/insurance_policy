package com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiPathsConfig {

    public static final String API_BASE_PATH = "/api";

    public static final String HEALTH_PATH = API_BASE_PATH + "/health";

    public static final String API_VERSION = "/v1";

    public static final String INSURANCE_MODULE = "/insurance";

    public static final String POLICIES_RESOURCE = "/policies";

    public static final String POLICIES_BASE_PATH = API_BASE_PATH + API_VERSION + INSURANCE_MODULE + POLICIES_RESOURCE;
}
