package com.example.service.payment.verify;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZarinpalVerificationResponse {

    private Data data;


    @Getter
    @Setter
    public static class Data {
        private int code;
        private String refId;

        // Getter and Setter methods
    }
}
