package com.example.dataaccess.entity.product;

import jakarta.persistence.*;
import lombok.*;
import com.example.dataaccess.entity.file.File;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private Boolean enable = true;

    @ManyToOne
    @JoinColumn(nullable = false)
    private File image;

    @OneToMany
    private List<Product> products;


}
