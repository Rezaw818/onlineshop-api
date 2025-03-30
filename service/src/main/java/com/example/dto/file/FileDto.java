package com.example.dto.file;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private Long id;
    private String path;
    private String name;
    private String uuid;
    private String extension;
    private String contentType;
    private Long size;

}
