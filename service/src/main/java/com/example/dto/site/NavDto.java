package com.example.dto.site;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NavDto {
    private Long id;

    private String title;

    private String link;

    private Integer orderNumber;

    private Boolean enable;
}
