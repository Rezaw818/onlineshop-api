package com.example.dataaccess.entity.site;

import jakarta.persistence.*;
import lombok.*;
import com.example.dataaccess.entity.file.File;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Slider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String link;

    private Boolean enable = true;
    private Integer orderNumber;

    @ManyToOne
    @JoinColumn(nullable = false)
    private File image;
}
