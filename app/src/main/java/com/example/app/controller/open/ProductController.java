package com.example.app.controller.open;


import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;
import com.example.dto.product.ProductCategoryDto;
import com.example.dto.product.ProductDto;
import com.example.enums.ProductQueryType;
import com.example.service.product.ProductCategoryService;
import com.example.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;
    private final ProductCategoryService categoryService;


    @Autowired
    public ProductController(ProductService service, ProductCategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    @GetMapping("category")
    public APIResponse<List<ProductCategoryDto>> getAllCategories() {
        return APIResponse.<List<ProductCategoryDto>>builder()
                .status(APIStatus.Success)
                .data(categoryService.readAllCategories())
                .build();
    }

    @GetMapping("top/{type}")
    public APIResponse<List<ProductDto>> getTopProduct(@PathVariable ProductQueryType type) {
        return APIResponse.<List<ProductDto>>builder()
                .status(APIStatus.Success)
                .data(service.read6TopProducts(type))
                .build();
    }

    @GetMapping("{id}")
    public APIResponse<ProductDto> getById(@PathVariable Long id){
        try{
        return APIResponse.<ProductDto>builder()
                .status(APIStatus.Success)
                .data(service.readById(id))
                .build();
        } catch (NotFoundException exception) {
            return APIResponse.<ProductDto>builder()
                    .status(APIStatus.Error)
                    .message(exception.getMessage())
                    .build();
        }
    }


}
