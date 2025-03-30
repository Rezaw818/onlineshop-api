package com.example.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;

    private String firstname;

    private String lastname;

    private String tel;

    private String address;

    private String postalCode;
}
