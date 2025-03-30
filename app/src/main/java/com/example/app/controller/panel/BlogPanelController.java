package com.example.app.controller.panel;

import com.example.app.anotation.CheckPermission;
import com.example.app.controller.base.CRUDController;
import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;
import com.example.dto.site.BlogDto;
import com.example.dto.site.SliderDto;
import com.example.service.site.BlogService;
import com.example.service.site.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/blog")
public class BlogPanelController implements CRUDController<BlogDto> {

    private final BlogService service;

    @Autowired
    public BlogPanelController(BlogService service) {
        this.service = service;
    }


    @Override
    @CheckPermission("add_blog")
    public APIResponse<BlogDto> add(BlogDto blogDto) {
        try{
            return APIResponse.<BlogDto>builder()
                    .data(service.create(blogDto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<BlogDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }

    }

    @Override
    @CheckPermission("list_blog")
    public APIPanelResponse<List<BlogDto>> getAll(Integer page, Integer size) {
        Page<BlogDto> data = service.readAll(page, size);
        return APIPanelResponse.<List<BlogDto>>builder()
                .data(data.getContent())
                .totalCount(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .message("")
                .build();
    }

    @Override
    @CheckPermission("edit_blog")
    public APIResponse<BlogDto> edit(BlogDto blogDto) throws Exception {

        return APIResponse.<BlogDto>builder()
                .status(APIStatus.Success)
                .data(service.update(blogDto))
                .build();
    }

    @Override
    @CheckPermission("delete_blog")
    public APIResponse<Boolean> delete(Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .data(service.delete(id))
                .status(APIStatus.Success)
                .build();
    }
}
