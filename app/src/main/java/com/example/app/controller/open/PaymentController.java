package com.example.app.controller.open;


import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;
import com.example.dto.payment.GoToPaymentDto;
import com.example.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService zarinpalService;
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService service, PaymentService zarinpalService, PaymentService paymentService) {
        this.zarinpalService = zarinpalService;
        this.paymentService = paymentService;
    }

    @Transactional
    @PostMapping("/request")
    public APIResponse<String> requestPayment(@RequestBody GoToPaymentDto dto) {
        try {
            return APIResponse.<String>builder()
                    .status(APIStatus.Success)
                    .data(zarinpalService.createPaymentRequest(dto))
                    .build();
        } catch (Exception e) {
            return APIResponse.<String>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }

    }

    @GetMapping("/verify")
    public APIResponse<String> verifyPayment(@RequestParam String Authority, @RequestParam String Status) throws Exception {
//        ZarinpalVerificationResponse response = zarinpalService.verifyPayment(Authority, Status);
//        return "Payment verified with refId: " + response.getData().getRefId();
        try {
            return APIResponse.<String>builder()
                    .status(APIStatus.Success)
                    .data(paymentService.verifyPayment(Authority, Status))
                    .message(Status)
                    .build();
        }catch (NotFoundException e){
            return APIResponse.<String>builder()
                    .status(APIStatus.Error)
                    .data(e.getMessage())
                    .message(Status)
                    .build();
        }

    }
}
