package com.example.dataaccess.repository.invoice;


import com.example.dataaccess.entity.order.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {


    List<Invoice> findAllByUser_Id(Long userId);
}


