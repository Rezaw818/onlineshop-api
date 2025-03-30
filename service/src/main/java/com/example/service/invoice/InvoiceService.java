package com.example.service.invoice;


import com.example.base.CreateService;
import com.example.base.HasValidation;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataaccess.entity.order.Invoice;
import com.example.dataaccess.entity.order.InvoiceItem;
import com.example.dataaccess.entity.user.User;
import com.example.dataaccess.enums.OrderStatus;
import com.example.dataaccess.repository.invoice.InvoiceRepository;
import com.example.dto.invoice.InvoiceDto;
import com.example.dto.product.ProductDto;
import com.example.service.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService implements CreateService<InvoiceDto> , HasValidation<InvoiceDto> {

    private final ModelMapper mapper;
    private final InvoiceRepository invoiceRepository;
    private final ProductService productService;

    @Autowired
    public InvoiceService(ModelMapper mapper, InvoiceRepository invoiceRepository, ProductService productService) {
        this.mapper = mapper;
        this.invoiceRepository = invoiceRepository;
        this.productService = productService;
    }

    @Override
    public InvoiceDto create(InvoiceDto invoiceDto) throws NotFoundException {
        Invoice invoice = mapper.map(invoiceDto,Invoice.class);
        invoice.setCreateDate(LocalDateTime.now());
        invoice.setPayedDate(null);
        invoice.setStatus(OrderStatus.InProgress);
        invoice.setUser(mapper.map(invoiceDto.getUser() , User.class));
        long totalAmount = 0l;
        if (invoice.getItems() != null && invoice.getItems().size() > 0){
            for (InvoiceItem ii : invoice.getItems()){
                ProductDto product = productService.readById(ii.getProduct().getId());
                ii.setPrice(product.getPrice());
                totalAmount += product.getPrice() * ii.getQuantity();
            }
        }

        invoice.setTotalAmount(totalAmount);
        Invoice savedInvoice = invoiceRepository.save(invoice);
       return mapper.map(invoice , InvoiceDto.class);

    }

    @Override
    public void checkValidation(InvoiceDto invoiceDto) throws ValidationException {
    }

    public List<InvoiceDto> readAllByUserId(Long id){
       return invoiceRepository.findAllByUser_Id(id).stream().map(x -> mapper.map(x , InvoiceDto.class)).toList();
    }

    public InvoiceDto read(Long id) throws NotFoundException {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(NotFoundException::new);
        return mapper.map(invoice , InvoiceDto.class);
    }
}
