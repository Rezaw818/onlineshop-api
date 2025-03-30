package com.example.app.controller.panel;

import com.example.app.anotation.CheckPermission;
import com.example.app.controller.base.CRUDController;
import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;
import com.example.dto.product.ProductDto;
import com.example.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/product")
public class ProductPanelController implements CRUDController<ProductDto> {

    private final ProductService service;

    @Autowired
    public ProductPanelController(ProductService service) {
        this.service = service;
    }


    @Override
    @CheckPermission("add_product")
    public APIResponse<ProductDto> add(ProductDto productDto) {
        try{
            return APIResponse.<ProductDto>builder()
                    .data(service.create(productDto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<ProductDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }

    }

    @Override
    @CheckPermission("list_product")
    public APIPanelResponse<List<ProductDto>> getAll(Integer page, Integer size) {
        Page<ProductDto> data = service.readAll(page, size);
        return APIPanelResponse.<List<ProductDto>>builder()
                .data(data.getContent())
                .totalCount(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .message("")
                .build();
    }


    @GetMapping("cat/{cid}")
    @CheckPermission("list_product")
    public APIPanelResponse<List<ProductDto>> getAllByCategory(@PathVariable Long cid, Integer page, Integer size) {
        Page<ProductDto> data = service.readAllByCategory(cid, page, size);
        return APIPanelResponse.<List<ProductDto>>builder()
                .data(data.getContent())
                .totalCount(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .message("")
                .build();
    }

    @Override
    @CheckPermission("edit_product")
    public APIResponse<ProductDto> edit(ProductDto productDto) throws Exception {

        return APIResponse.<ProductDto>builder()
                .status(APIStatus.Success)
                .data(service.update(productDto))
                .build();
    }

    @Override
    @CheckPermission("delete_product")
    public APIResponse<Boolean> delete(Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .data(service.delete(id))
                .status(APIStatus.Success)
                .build();
    }
}
