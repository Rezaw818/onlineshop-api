package com.example.service.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZarinpalPaymentRequest {

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("amount")
    private int amount;

    @JsonProperty("callback_url")
    private String callbackUrl;

    @JsonProperty("description")
    private String description;
}
