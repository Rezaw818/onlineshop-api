package com.example.dto.site;


import com.example.dataaccess.entity.file.File;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SliderDto {

    private Long id;

    private String title;

    private String link;


    private Integer orderNumber;

    private File image;
}
