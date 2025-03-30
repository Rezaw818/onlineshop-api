package com.example.dataaccess.repository.invoice;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemRepository extends JpaRepository<com.example.dataaccess.entity.order.InvoiceItem, Long> {
}
