package com.example.service.payment;

import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.config.ZarinpalConfig;
import com.example.dataaccess.entity.order.Invoice;
import com.example.dataaccess.entity.payment.Payment;
import com.example.dataaccess.entity.payment.Transaction;
import com.example.dataaccess.entity.user.User;
import com.example.dataaccess.repository.payment.PaymentRepository;
import com.example.dataaccess.repository.payment.TransactionRepository;
import com.example.dto.CustomerDto;
import com.example.dto.UserDto;
import com.example.dto.invoice.InvoiceDto;
import com.example.dto.invoice.InvoiceItemDto;
import com.example.dto.payment.GoToPaymentDto;
import com.example.dto.product.ColorDto;
import com.example.dto.product.ProductDto;
import com.example.service.invoice.InvoiceService;
import com.example.service.payment.verify.ZarinpalVerificationRequest;
import com.example.service.payment.verify.ZarinpalVerificationResponse;
import com.example.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {


    private final ZarinpalConfig zarinpalConfig;


    private final RestTemplate restTemplate = new RestTemplate();
    private final UserService userService;
    private final InvoiceService invoiceService;
    private final ModelMapper mapper;
    private final PaymentRepository paymentRepository;
    private final TransactionRepository trxRepository;

    @Autowired
    public PaymentService(ZarinpalConfig zarinpalConfig, UserService userService, InvoiceService invoiceService, ModelMapper mapper, PaymentRepository paymentRepository, TransactionRepository trxRepository) {
        this.zarinpalConfig = zarinpalConfig;
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.mapper = mapper;
        this.paymentRepository = paymentRepository;
        this.trxRepository = trxRepository;
    }

    @Transactional
    public String createPaymentRequest(GoToPaymentDto dto) throws Exception {

            checkValidation(dto);
            UserDto user = userService.create(UserDto.builder()
                    .username(dto.getUsername())
                    .email(dto.getEmail())
                    .mobile(dto.getMobile())
                    .password(dto.getPassword())
                    .customerDto(CustomerDto.builder()
                            .firstname(dto.getFirstname())
                            .lastname(dto.getLastname())
                            .tel(dto.getTel())
                            .address(dto.getAddress())
                            .postalCode(dto.getPostalCode())
                            .build())
                    .build());

            InvoiceDto invoice =  invoiceService.create(InvoiceDto.builder()
                            .user(UserDto.builder().id(user.getId()).build())
                    .items(dto.getItems().stream().map(x -> InvoiceItemDto.builder()
                            .product(ProductDto.builder().id(x.getProductId()).build())
                            .color(ColorDto.builder().id(x.getColorId()).build())
                            .quantity(x.getQuantity())
                            .build()).toList())
                    .build());

            Payment gateway = paymentRepository.findFirstByNameEqualsIgnoreCase(dto.getGateway().toString()).orElseThrow(NotFoundException::new);


            Transaction trx = Transaction.builder()
                    .amount(invoice.getTotalAmount())
                    .payment(gateway)
                    .description(invoice.getId()+"_" + invoice.getTotalAmount())
                    .customer(mapper.map(user, User.class))
                    .invoice(mapper.map(invoice, Invoice.class))
                    .build();


            String url = zarinpalConfig.getPaymentUrl();

            ZarinpalPaymentRequest request = new ZarinpalPaymentRequest();
            request.setMerchantId(zarinpalConfig.getMerchantId());
            request.setCallbackUrl(zarinpalConfig.getCallbackUrl());
            request.setAmount(trx.getAmount().intValue());
            request.setDescription(trx.getDescription());

            ZarinpalPaymentResponse response = restTemplate.postForObject(url,request,ZarinpalPaymentResponse.class);
            trx.setAuthority(response.getData().getAuthority());
            trxRepository.save(trx);

            if (response != null && response.getData() != null && response.getData().getCode() == 100){
                return zarinpalConfig.getIpgUrl() + response.getData().getAuthority();
            }else {
                throw new RuntimeException("failed to create payment request");
            }


    }

    public String verifyPayment(String Authority, String status) throws Exception {
        String url = zarinpalConfig.getVerificationUrl();
        ZarinpalVerificationRequest request = new ZarinpalVerificationRequest();


        if (status == null || status.isEmpty() || status.equalsIgnoreCase("NOK")){
            return "NOK";
        }

        if (status.equalsIgnoreCase("OK")){

            Transaction trx = trxRepository.findFirstByAuthorityIgnoreCase(Authority).orElseThrow(NotFoundException::new);
            request.setMerchantId(zarinpalConfig.getMerchantId());
            request.setAmount(trx.getAmount());
            request.setAuthority(trx.getAuthority());
            ZarinpalVerificationResponse response = restTemplate.postForObject(url, request, ZarinpalVerificationResponse.class);

           trx.setRefId(response.getData().getRefId());
           trx.setCode(String.valueOf(response.getData().getCode()));
           trxRepository.save(trx);
           return "OK";

        }



       return "NOK";


    }

    private static void checkValidation(GoToPaymentDto dto) throws ValidationException {
        if (dto.getGateway() == null){
            throw new ValidationException("please enter payment gateway");
        }
        if (dto.getFirstname() == null || dto.getFirstname().isEmpty()){
            throw new ValidationException("please enter your firstname");
        }
        if (dto.getLastname() == null || dto.getLastname().isEmpty()){
            throw new ValidationException("please enter your lastname");
        }
        if (dto.getUsername() == null || dto.getUsername().isEmpty()){
            throw new ValidationException("please enter your username");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()){
            throw new ValidationException("please enter your password");
        }
        if (dto.getEmail() == null || dto.getEmail().isEmpty()){
            throw new ValidationException("please enter your email");
        }
        if (dto.getMobile() == null || dto.getMobile().isEmpty()) {
            throw new ValidationException("Please enter mobile");
        }
        if (dto.getTel() == null || dto.getTel().isEmpty()) {
            throw new ValidationException("Please enter tel");
        }
        if (dto.getAddress() == null || dto.getAddress().isEmpty()) {
            throw new ValidationException("Please enter address");
        }
        if (dto.getPostalCode() == null || dto.getPostalCode().isEmpty()) {
            throw new ValidationException("Please enter postalCode");
        }
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new ValidationException("Please add at least one product to your basket");
        }


    }
}
