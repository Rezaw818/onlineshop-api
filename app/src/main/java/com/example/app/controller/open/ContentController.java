package com.example.app.controller.open;


import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.dto.site.ContentDto;
import com.example.dto.site.NavDto;
import com.example.service.site.ContentService;
import com.example.service.site.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService service;

    @Autowired
    public ContentController(ContentService service) {
        this.service = service;
    }

    @GetMapping("")
    public APIResponse<List<ContentDto>> getAll(){
        return APIResponse.<List<ContentDto>>builder()
                .status(APIStatus.Success)
                .data(service.readAll())
                .build();
    }

    @GetMapping("{key}")
    public APIResponse<ContentDto> getByKey(@PathVariable String key){
        return APIResponse.<ContentDto>builder()
                .status(APIStatus.Success)
                .data(service.readByKey(key))
                .build();
    }
}
