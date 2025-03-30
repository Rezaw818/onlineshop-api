package com.example.dto.site;


import com.example.dataaccess.enums.BlogStatus;
import com.example.dto.file.FileDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleBlogDto {

    private Long id;
    private String title;
    private String subtitle;

    private LocalDateTime publishDate;
    private Long visitCount;

    private FileDto image;

    private String description;
}
