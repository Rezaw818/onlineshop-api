package com.example.app.controller.open;


import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.dataaccess.entity.site.Slider;
import com.example.dto.site.NavDto;
import com.example.dto.site.SliderDto;
import com.example.service.site.NavService;
import com.example.service.site.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/slider")
public class SliderController {

    private final SliderService service;

    @Autowired
    public SliderController(SliderService service) {
        this.service = service;
    }

    @GetMapping("")
    public APIResponse<List<SliderDto>> getAll(){
        return APIResponse.<List<SliderDto>>builder()
                .status(APIStatus.Success)
                .data(service.readAll())
                .build();
    }
}
