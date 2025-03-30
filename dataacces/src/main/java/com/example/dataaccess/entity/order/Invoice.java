package com.example.dataaccess.entity.order;

import com.example.dataaccess.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import com.example.dataaccess.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime createDate;
    private LocalDateTime payedDate;
    private OrderStatus status;
    private Long totalAmount;


    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceItem> items;
}
