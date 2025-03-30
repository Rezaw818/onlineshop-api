package com.example.service.payment;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZarinpalPaymentResponse {

private Data data;


    @Getter
    @Setter
   public static class Data{
       private int code;
       private String authority;
   }
}
