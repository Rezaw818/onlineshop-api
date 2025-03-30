package com.example.config;

import jakarta.persistence.Column;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ZarinpalConfig {


    @Value("${app.payment.gatway.zarinpal.merchant_id}")
    private String merchantId;

    @Value("${app.payment-gateway.zarinpal.callback-url}")
    private String callbackUrl;

    @Value("${app.payment.zarinpal.payment-url}")
    private String paymentUrl;

    @Value("${zarinpal.verification-url}")
    private String verificationUrl;

    @Value("${app.payment-gateway.zarinpal.ipg-url}")
    private String ipgUrl;


}
