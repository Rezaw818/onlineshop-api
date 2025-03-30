package com.example.app.controller.panel;

import com.example.app.anotation.CheckPermission;
import com.example.app.controller.base.CreateController;
import com.example.app.controller.base.ReadController;
import com.example.app.controller.base.UpdateController;
import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;

import com.example.dto.product.SizeDto;
import com.example.service.product.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/size")
public class SizePanelController implements CreateController<SizeDto>,
        UpdateController<SizeDto>,
        ReadController<SizeDto>
{

    private final SizeService service;

    @Autowired
    public SizePanelController(SizeService service) {
        this.service = service;
    }


    @Override
    @CheckPermission("add_size")
    public APIResponse<SizeDto> add(SizeDto sizeDto) {
        try{
            return APIResponse.<SizeDto>builder()
                    .data(service.create(sizeDto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<SizeDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }

    }

    @Override
    @CheckPermission("list_size")
    public APIPanelResponse<List<SizeDto>> getAll(Integer page, Integer size) {
        Page<SizeDto> data = service.readAll(page, size);
        return APIPanelResponse.<List<SizeDto>>builder()
                .data(data.getContent())
                .totalCount(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .message("")
                .build();
    }

    @Override
    @CheckPermission("edit_size")
    public APIResponse<SizeDto> edit(SizeDto sizeDto) throws Exception {

        return APIResponse.<SizeDto>builder()
                .status(APIStatus.Success)
                .data(service.update(sizeDto))
                .build();
    }

}
