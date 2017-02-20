package com.myretail.products.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.util.Map;

/**
 * Created by Shreyas.
 */
public class Util {
    public ObjectMapper getMapper() {
        return new ObjectMapper();
    }

    public ObjectMapper getSnakeCaseObjectMapper() {
        return new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public boolean compareJsonObject(String input1, String input2) {
        ObjectMapper om = new ObjectMapper();
        boolean matches = false;
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> m1 = om.readValue(input1, Map.class);
            @SuppressWarnings("unchecked")
            Map<String, Object> m2 = om.readValue(input2, Map.class);
            matches = m1.equals(m2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }
}
