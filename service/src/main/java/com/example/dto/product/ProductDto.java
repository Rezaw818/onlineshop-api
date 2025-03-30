package com.example.dto.product;

import com.example.dataaccess.entity.file.File;
import com.example.dataaccess.entity.product.Color;
import com.example.dataaccess.entity.product.ProductCategory;
import com.example.dataaccess.entity.product.Size;
import com.example.dto.file.FileDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private String title;

    private String description;

    private Boolean enable;
    private Boolean exist;

    private Long price;
    private Long visitCount;
    private LocalDateTime addDate;

    private FileDto image;
    private Set<ColorDto> colors;

    private Set<SizeDto> sizes;

    private ProductCategoryDto category;

}
