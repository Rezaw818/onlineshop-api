package com.example.app.controller.panel.order;

import com.example.app.anotation.CheckPermission;
import com.example.app.filter.JwtFilter;
import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataaccess.entity.user.User;
import com.example.dto.UserDto;
import com.example.dto.invoice.InvoiceDto;
import com.example.service.invoice.InvoiceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/invoice")
public class InvoicePanelController{

    private final InvoiceService service;

    @Autowired
    public InvoicePanelController(InvoiceService service) {
        this.service = service;
    }




    @GetMapping("/user/{uid}")
    @CheckPermission("list_invoice")
    public APIResponse<List<InvoiceDto>> getAllById(@PathVariable Long uid) {
            return APIResponse.<List<InvoiceDto>>builder()
                    .data(service.readAllByUserId(uid))
                    .message("")
                    .build();

    }

    @GetMapping("{id}")
    @CheckPermission("info_invoice")
    public APIResponse<InvoiceDto> get(@PathVariable Long id) {
        try{
            return APIResponse.<InvoiceDto>builder()
                    .data(service.read(id))
                    .message("")
                    .build();
        } catch (Exception e) {
            return APIResponse.<InvoiceDto>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }
    }

    @GetMapping("my-invoice")
    @CheckPermission("list_my_invoice")
    public APIResponse<List<InvoiceDto>> getMineList(HttpServletRequest request) {
        try{
            UserDto user =(UserDto) request.getAttribute(JwtFilter.CURRENT_USER);
            return APIResponse.<List<InvoiceDto>>builder()
                    .data(service.readAllByUserId(user.getId()))
                    .message("")
                    .build();
        } catch (Exception e) {
            return APIResponse.<List<InvoiceDto>>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }
    }

    @GetMapping("mine/{id}")
    @CheckPermission("info_invoice")
    public APIResponse<InvoiceDto> getMineInfo(@PathVariable Long id ,HttpServletRequest request) {
        UserDto user =(UserDto) request.getAttribute(JwtFilter.CURRENT_USER);
        try{
            InvoiceDto data = service.read(id);
            if(!data.getUser().getId().equals(user.getId())){
               throw new ValidationException("Access Denied to view this invoice!");
            }

            return APIResponse.<InvoiceDto>builder()
                    .data(service.read(user.getId()))
                    .message("")
                    .build();
        } catch (Exception e) {
            return APIResponse.<InvoiceDto>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }
    }


}
