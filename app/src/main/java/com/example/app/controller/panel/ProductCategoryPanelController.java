package com.example.app.controller.panel;

import com.example.app.anotation.CheckPermission;
import com.example.app.controller.base.CRUDController;
import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;

import com.example.dto.product.ProductCategoryDto;
import com.example.service.product.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/product/category")
public class ProductCategoryPanelController implements CRUDController<ProductCategoryDto> {

    private final ProductCategoryService service;

    @Autowired
    public ProductCategoryPanelController(ProductCategoryService service) {
        this.service = service;
    }


    @Override
    @CheckPermission("add_product_category")
    public APIResponse<ProductCategoryDto> add(ProductCategoryDto productCategoryDto) {
        try{
            return APIResponse.<ProductCategoryDto>builder()
                    .data(service.create(productCategoryDto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<ProductCategoryDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }

    }

    @Override
    @CheckPermission("list_product_category")
    public APIPanelResponse<List<ProductCategoryDto>> getAll(Integer page, Integer size) {
        Page<ProductCategoryDto> data = service.readAll(page, size);
        return APIPanelResponse.<List<ProductCategoryDto>>builder()
                .data(data.getContent())
                .totalCount(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .message("")
                .build();
    }

    @Override
    @CheckPermission("edit_product_category")
    public APIResponse<ProductCategoryDto> edit(ProductCategoryDto productCategoryDto) throws Exception {

        return APIResponse.<ProductCategoryDto>builder()
                .status(APIStatus.Success)
                .data(service.update(productCategoryDto))
                .build();
    }

    @Override
    @CheckPermission("delete_product_category")
    public APIResponse<Boolean> delete(Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .data(service.delete(id))
                .status(APIStatus.Success)
                .build();
    }
}
