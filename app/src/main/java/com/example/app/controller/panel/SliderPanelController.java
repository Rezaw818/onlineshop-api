package com.example.app.controller.panel;

import com.example.app.anotation.CheckPermission;
import com.example.app.controller.base.CRUDController;
import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;
import com.example.dto.site.NavDto;
import com.example.dto.site.SliderDto;
import com.example.service.site.NavService;
import com.example.service.site.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/slider")
public class SliderPanelController implements CRUDController<SliderDto> {

    private final SliderService service;

    @Autowired
    public SliderPanelController(SliderService service) {
        this.service = service;
    }


    @Override
    @CheckPermission("add_slider")
    public APIResponse<SliderDto> add(SliderDto sliderDto) {
        try{
            return APIResponse.<SliderDto>builder()
                    .data(service.create(sliderDto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<SliderDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }

    }

    @Override
    @CheckPermission("list_slider")
    public APIPanelResponse<List<SliderDto>> getAll(Integer page, Integer size) {
        Page<SliderDto> data = service.readAll(page, size);
        return APIPanelResponse.<List<SliderDto>>builder()
                .data(data.getContent())
                .totalCount(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .message("")
                .build();
    }

    @Override
    @CheckPermission("edit_slider")
    public APIResponse<SliderDto> edit(SliderDto sliderDto) throws Exception {

        return APIResponse.<SliderDto>builder()
                .status(APIStatus.Success)
                .data(service.update(sliderDto))
                .build();
    }

    @PutMapping("swap_up/{id}")
    @CheckPermission("edit_slider")
    public APIResponse<Boolean> swapUp(@PathVariable Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .data(service.swapUp(id))
                .status(APIStatus.Success)
                .build();
    }

    @PutMapping("swap_down/{id}")
    @CheckPermission("edit_slider")
    public APIResponse<Boolean> swapDown(@PathVariable Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .data(service.swapDown(id))
                .status(APIStatus.Success)
                .build();
    }

    @Override
    @CheckPermission("delete_slider")
    public APIResponse<Boolean> delete(Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .data(service.delete(id))
                .status(APIStatus.Success)
                .build();
    }
}
