package com.example.dataaccess.entity.payment;


import com.example.dataaccess.entity.order.Invoice;
import com.example.dataaccess.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trx")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @ManyToOne
    private Invoice invoice;

    @ManyToOne
    private User customer;

    private String authority;
    private String code;
    private String description;
    private String resultMessage;
    private String verifyMessage;
    private String cardHash;
    private String cardPan;
    private String refId;
    private String verifyCode;

    @ManyToOne
    private Payment payment;
}
