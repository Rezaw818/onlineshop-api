package com.example.app.controller.panel;

import com.example.app.anotation.CheckPermission;
import com.example.app.controller.base.CRUDController;
import com.example.app.controller.base.CreateController;
import com.example.app.controller.base.ReadController;
import com.example.app.controller.base.UpdateController;
import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;

import com.example.dto.site.ContentDto;

import com.example.service.site.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/content")
public class ContentPanelController implements CreateController<ContentDto>,
        UpdateController<ContentDto>,
        ReadController<ContentDto>
{

    private final ContentService service;

    @Autowired
    public ContentPanelController(ContentService service) {
        this.service = service;
    }


    @Override
    @CheckPermission("add_content")
    public APIResponse<ContentDto> add(ContentDto contentDto) {
        try{
            return APIResponse.<ContentDto>builder()
                    .data(service.create(contentDto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<ContentDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }

    }

    @Override
    @CheckPermission("list_content")
    public APIPanelResponse<List<ContentDto>> getAll(Integer page, Integer size) {
        Page<ContentDto> data = service.readAll(page, size);
        return APIPanelResponse.<List<ContentDto>>builder()
                .data(data.getContent())
                .totalCount(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .message("")
                .build();
    }

    @Override
    @CheckPermission("edit_content")
    public APIResponse<ContentDto> edit(ContentDto contentDto) throws Exception {

        return APIResponse.<ContentDto>builder()
                .status(APIStatus.Success)
                .data(service.update(contentDto))
                .build();
    }

}
