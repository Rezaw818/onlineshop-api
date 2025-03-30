package com.example.dto.payment;


import com.example.enums.PaymentGateway;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoToPaymentDto {

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String tel;
    private String address;
    private String postalCode;
    private List<BasketItem> items;
    private PaymentGateway gateway;

    @Getter
    @Setter
    @Builder
    public static class BasketItem{
        private Long productId;
        private Long colorId;
        private Long sizeId;
        private Integer quantity;
    }
}
