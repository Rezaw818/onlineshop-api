package com.example.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassDto {


    private String oldPassword;
    private String newPassword;
    private String newPassword2;
}
