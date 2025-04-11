package com.bushido.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record ExchangeRateResponse(
        boolean success,
        long timestamp,
        String source,
        @JsonProperty("quotes") Map<String, Float> quotes
) {
}
