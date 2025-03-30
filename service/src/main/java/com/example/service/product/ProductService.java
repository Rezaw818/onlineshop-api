package com.example.service.product;

import com.example.base.CRUDService;
import com.example.base.HasValidation;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataaccess.entity.file.File;
import com.example.dataaccess.entity.product.Color;
import com.example.dataaccess.entity.product.Product;

import com.example.dataaccess.repository.product.ColorRepository;
import com.example.dataaccess.repository.product.ProductRepository;
import com.example.dataaccess.repository.product.ProductcategoryRepository;
import com.example.dataaccess.repository.product.SizeRepository;

import com.example.dto.product.ProductDto;

import com.example.dto.site.BlogDto;
import com.example.enums.ProductQueryType;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements CRUDService<ProductDto>, HasValidation<ProductDto> {

    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductcategoryRepository categoryRepository, ColorRepository colorRepository, SizeRepository sizeRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }



    @Cacheable(value = "topProducts", key = "'top5'")
    public List<ProductDto> read6TopProducts(ProductQueryType type) {
        List<Product> result = new ArrayList<>();
        switch (type){
            case Popular -> {
                result = productRepository.find6PopularProduct();
            }
            case Newest -> {
                result = productRepository.find6NewstProduct();
            }
            case Cheap -> {
                result = productRepository.find6CheapestProduct();
            }
            case Expensive -> {
                result = productRepository.find6ExpensiveProduct();
            }
        }
       return result.stream().map(x -> mapper.map(x, ProductDto.class)).toList();

    }


    @SneakyThrows
    public ProductDto readById(Long id) throws NotFoundException{
        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        return mapper.map(product,ProductDto.class);
    }



    @Override
    public Page<ProductDto> readAll(Integer page, Integer size){
        if (page == null){
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return productRepository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x->mapper.map(x,ProductDto.class));
    }

    @Override
    public ProductDto create(ProductDto productDto) throws Exception {
        checkValidation(productDto);
        Product product = mapper.map(productDto, Product.class);
        product.setEnable(true);
        product.setExist(true);
        product.setVisitCount(0l);
        product.setAddDate(LocalDateTime.now());
        productRepository.save(product);
        return mapper.map(product , ProductDto.class);
    }

    @Override
    public Boolean delete(Long id) throws NotFoundException {
        productRepository.deleteById(id);
        return true;
    }



    @Override
    public ProductDto update(ProductDto productDto) throws Exception {
        checkValidation(productDto);
        if (productDto.getId() == null || productDto.getId() <= 0){
            throw new ValidationException("please enter id to update");
        }
        Product product = productRepository.findById(productDto.getId()).orElseThrow(NotFoundException::new);
        product.setTitle(Optional.ofNullable(productDto.getTitle()).orElse(product.getTitle()));
        product.setPrice(Optional.ofNullable(productDto.getPrice()).orElse(product.getPrice()));
        product.setEnable(Optional.ofNullable(productDto.getEnable()).orElse(product.getEnable()));
        product.setExist(Optional.ofNullable(productDto.getExist()).orElse(product.getExist()));

        product.setImage(Optional.ofNullable(mapper.map(productDto.getImage(), File.class)).orElse(product.getImage()));


        product.setDescription(Optional.ofNullable(productDto.getDescription()).orElse(product.getDescription()));

        productRepository.save(product);
        return mapper.map(product,ProductDto.class);


    }



    @Override
    public void checkValidation(ProductDto dto) throws ValidationException {
        if (dto == null){
            throw new ValidationException("please fill content data");
        }
        if (dto.getTitle() == null || dto.getTitle().isEmpty()){
            throw new ValidationException("please enter title");
        }
        if (dto.getPrice() == null || dto.getPrice() < 0){
            throw new ValidationException("please enter price");
        }
    }


    public Page<ProductDto> readAllByCategory(Long cid, Integer page, Integer size) {
        if (page == null){
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return productRepository.findAllByCategory_id(cid, Pageable.ofSize(size).withPage(page)).map(x -> mapper.map(x, ProductDto.class));




    }
}
