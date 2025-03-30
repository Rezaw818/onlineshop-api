package com.example.app.controller.open;


import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.dto.site.BlogDto;
import com.example.dto.site.ContentDto;
import com.example.dto.site.SingleBlogDto;
import com.example.service.site.BlogService;
import com.example.service.site.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Blog")
public class BlogController {

    private final BlogService service;

    @Autowired
    public BlogController(BlogService service) {
        this.service = service;
    }

    @GetMapping("")
    public APIResponse<List<BlogDto>> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ){
        return APIResponse.<List<BlogDto>>builder()
                .status(APIStatus.Success)
                .data(service.readAllPublished(page,size))
                .build();
    }

    @GetMapping("{id}")
    public APIResponse<SingleBlogDto> getById(@PathVariable Long id){
        return APIResponse.<SingleBlogDto>builder()
                .status(APIStatus.Success)
                .data(service.readById(id))
                .build();
    }
}
