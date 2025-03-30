package com.example.dto;


import com.example.dto.payment.GoToPaymentDto;
import com.example.enums.PaymentGateway;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String mobile;
    private String tel;
    private String address;
    private String postalCode;

}
