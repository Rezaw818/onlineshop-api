package com.example.dataaccess.entity.site;

import jakarta.persistence.*;
import lombok.*;
import com.example.dataaccess.entity.file.File;
import com.example.dataaccess.enums.BlogStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String subtitle;

    private LocalDateTime publishDate;
    private BlogStatus status;
    private Long visitCount;

    @ManyToOne
    @JoinColumn(nullable = false)
    private File image;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
}
