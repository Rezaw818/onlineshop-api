package com.example.app.controller.open;


import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.dataaccess.entity.site.Nav;
import com.example.dto.site.NavDto;
import com.example.service.site.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nav")
public class NavController {

    private final NavService service;

    @Autowired
    public NavController(NavService service) {
        this.service = service;
    }

    @GetMapping("")
    public APIResponse<List<NavDto>> getAll(){
        return APIResponse.<List<NavDto>>builder()
                .status(APIStatus.Success)
                .data(service.readAll())
                .build();
    }
}
