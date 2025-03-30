package com.example.dataaccess.entity.order;

import jakarta.persistence.*;
import lombok.*;
import com.example.dataaccess.entity.product.Color;
import com.example.dataaccess.entity.product.Product;
import com.example.dataaccess.entity.product.Size;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Invoice invoice;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Size size;

    @ManyToOne
    private Color color;

    private Integer quantity;
    private Long price;
}
