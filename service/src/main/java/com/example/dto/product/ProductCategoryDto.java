package com.example.dto.product;

import com.example.dataaccess.entity.file.File;
import com.example.dto.file.FileDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDto {

    private Long id;

    private String title;

    private String description;

    private FileDto image;

    private Boolean enable;
}
