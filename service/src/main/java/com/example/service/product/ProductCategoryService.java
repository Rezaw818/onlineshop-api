package com.example.service.product;

import com.example.base.CRUDService;
import com.example.base.HasValidation;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataaccess.entity.file.File;
import com.example.dataaccess.entity.product.ProductCategory;

import com.example.dataaccess.repository.product.ProductcategoryRepository;


import com.example.dto.product.ProductCategoryDto;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService implements CRUDService<ProductCategoryDto> , HasValidation<ProductCategoryDto> {

    private final ProductcategoryRepository repository;
    private final ModelMapper mapper;
    private final PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer;

    @Autowired
    public ProductCategoryService(ProductcategoryRepository repository, ModelMapper mapper, PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer) {
        this.repository = repository;
        this.mapper = mapper;
        this.pageableCustomizer = pageableCustomizer;
    }


    @SneakyThrows
    public ProductCategoryDto readById(Long id){
        ProductCategory ProductCategory = repository.findById(id).orElseThrow(NotFoundException::new);
        return mapper.map(ProductCategory,ProductCategoryDto.class);
    }


    public List<ProductCategoryDto> readAllCategories() {
        return repository.findAllByEnableIsTrue()
                .stream().map(x -> mapper.map(x, ProductCategoryDto.class)).toList();

    }

    @Override
    public Page<ProductCategoryDto> readAll(Integer page, Integer size){
        if (page == null){
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x->mapper.map(x,ProductCategoryDto.class));
    }

    @Override
    public ProductCategoryDto create(ProductCategoryDto ProductCategoryDto) throws Exception {
        checkValidation(ProductCategoryDto);
        ProductCategory productCategory = mapper.map(ProductCategoryDto, ProductCategory.class);
        productCategory.setEnable(true);
        repository.save(productCategory);
        return mapper.map(productCategory , ProductCategoryDto.class);
    }

    @Override
    public Boolean delete(Long id) throws NotFoundException {
        repository.deleteById(id);
        return true;
    }



    @Override
    public ProductCategoryDto update(ProductCategoryDto ProductCategoryDto) throws Exception {
        checkValidation(ProductCategoryDto);
        if (ProductCategoryDto.getId() == null || ProductCategoryDto.getId() <= 0){
            throw new ValidationException("please enter id to update");
        }
        ProductCategory ProductCategory = repository.findById(ProductCategoryDto.getId()).orElseThrow(NotFoundException::new);
        ProductCategory.setTitle(Optional.ofNullable(ProductCategoryDto.getTitle()).orElse(ProductCategory.getTitle()));
        ProductCategory.setDescription(Optional.ofNullable(ProductCategoryDto.getDescription()).orElse(ProductCategory.getDescription()));
        ProductCategory.setEnable(Optional.ofNullable(ProductCategoryDto.getEnable()).orElse(ProductCategory.getEnable()));
        if (ProductCategoryDto.getImage() != null) {
            ProductCategory.setImage(Optional.ofNullable(mapper.map(ProductCategory.getImage(), File.class)).orElse(ProductCategory.getImage()));
        }
        repository.save(ProductCategory);
       return mapper.map(ProductCategory,ProductCategoryDto.class);


    }



    @Override
    public void checkValidation(ProductCategoryDto dto) throws ValidationException {
        if (dto == null){
            throw new ValidationException("please fill content data");
        }
        if (dto.getTitle() == null || dto.getTitle().isEmpty()){
            throw new ValidationException("please enter title");
        }
        if (dto.getDescription() == null || dto.getDescription().isEmpty()){
            throw new ValidationException("please enter Description");
        }
    }




}
