package com.example.app.controller.panel;

import com.example.app.anotation.CheckPermission;
import com.example.app.controller.base.CreateController;
import com.example.app.controller.base.ReadController;
import com.example.app.controller.base.UpdateController;
import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.dto.product.ColorDto;
import com.example.service.product.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/color")
public class ColorPanelController implements CreateController<ColorDto>,
        UpdateController<ColorDto>,
        ReadController<ColorDto>
{

    private final ColorService service;

    @Autowired
    public ColorPanelController(ColorService service) {
        this.service = service;
    }


    @Override
    @CheckPermission("add_color")
    public APIResponse<ColorDto> add(ColorDto colorDto) {
        try{
            return APIResponse.<ColorDto>builder()
                    .data(service.create(colorDto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<ColorDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }

    }

    @Override
    @CheckPermission("list_color")
    public APIPanelResponse<List<ColorDto>> getAll(Integer page, Integer color) {
        Page<ColorDto> data = service.readAll(page, color);
        return APIPanelResponse.<List<ColorDto>>builder()
                .data(data.getContent())
                .totalCount(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .message("")
                .build();
    }

    @Override
    @CheckPermission("edit_color")
    public APIResponse<ColorDto> edit(ColorDto colorDto) throws Exception {

        return APIResponse.<ColorDto>builder()
                .status(APIStatus.Success)
                .data(service.update(colorDto))
                .build();
    }

}
