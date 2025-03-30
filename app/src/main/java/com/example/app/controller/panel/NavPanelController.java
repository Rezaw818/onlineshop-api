package com.example.app.controller.panel;

import com.example.app.anotation.CheckPermission;
import com.example.app.controller.base.CRUDController;
import com.example.app.controller.base.ReadController;
import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;
import com.example.dto.site.NavDto;
import com.example.service.site.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/nav")
public class NavPanelController implements CRUDController<NavDto> {

    private final NavService service;

    @Autowired
    public NavPanelController(NavService service) {
        this.service = service;
    }


    @Override
    @CheckPermission("add_nav")
    public APIResponse<NavDto> add(NavDto navDto) {
        try{
            return APIResponse.<NavDto>builder()
                    .data(service.create(navDto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<NavDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }

    }

    @Override
    @CheckPermission("list_nav")
    public APIPanelResponse<List<NavDto>> getAll(Integer page, Integer size) {
        Page<NavDto> data = service.readAll(page, size);
        return APIPanelResponse.<List<NavDto>>builder()
                .data(data.getContent())
                .totalCount(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .message("")
                .build();
    }

    @Override
    @CheckPermission("edit_nav")
    public APIResponse<NavDto> edit(NavDto navDto) throws Exception {

        return APIResponse.<NavDto>builder()
                .status(APIStatus.Success)
                .data(service.update(navDto))
                .build();
    }

    @Override
    @CheckPermission("delete_nav")
    public APIResponse<Boolean> delete(Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .data(service.delete(id))
                .status(APIStatus.Success)
                .build();
    }

    @PutMapping("swap_up/{id}")
    @CheckPermission("edit_nav")
    public APIResponse<Boolean> swapUp(@PathVariable Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .data(service.swapUp(id))
                .status(APIStatus.Success)
                .build();
    }

    @PutMapping("swap_down/{id}")
    @CheckPermission("edit_nav")
    public APIResponse<Boolean> swapDown(@PathVariable Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .data(service.swapDown(id))
                .status(APIStatus.Success)
                .build();
    }


}
