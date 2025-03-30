package com.example.service.payment.verify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZarinpalVerificationRequest {

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("authority")
    private String authority;

    @JsonProperty("amount")
    private long amount;

}
