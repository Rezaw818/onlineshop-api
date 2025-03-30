package com.example.dto.invoice;

import com.example.dataaccess.entity.order.InvoiceItem;
import com.example.dataaccess.entity.user.User;
import com.example.dataaccess.enums.OrderStatus;
import com.example.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {


    private Long id;


    private LocalDateTime createDate;
    private LocalDateTime payedDate;
    private OrderStatus status;
    private Long totalAmount;
    private UserDto user;

    private List<InvoiceItemDto> items;
}
