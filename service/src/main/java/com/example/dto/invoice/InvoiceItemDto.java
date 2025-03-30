package com.example.dto.invoice;

import com.example.dataaccess.entity.order.Invoice;
import com.example.dataaccess.entity.product.Color;
import com.example.dataaccess.entity.product.Product;
import com.example.dataaccess.entity.product.Size;
import com.example.dto.product.ColorDto;
import com.example.dto.product.ProductDto;
import com.example.dto.product.SizeDto;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItemDto {


    private Long id;

    private Invoice invoice;

    private ProductDto product;

    private SizeDto size;

    private ColorDto color;

    private Integer quantity;
    private Long price;
}
