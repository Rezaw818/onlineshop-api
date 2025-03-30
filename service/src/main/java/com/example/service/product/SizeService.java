package com.example.service.product;

import com.example.base.*;
import com.example.dto.product.SizeDto;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataaccess.entity.product.Size;

import com.example.dataaccess.repository.product.SizeRepository;

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
public class SizeService implements CreateService<SizeDto>
         , UpdateService<SizeDto>,
        ReadService<SizeDto>
        , HasValidation<SizeDto> {

    private final SizeRepository repository;
    private final ModelMapper mapper;
    private final PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer;

    @Autowired
    public SizeService(SizeRepository repository, ModelMapper mapper, PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer) {
        this.repository = repository;
        this.mapper = mapper;
        this.pageableCustomizer = pageableCustomizer;
    }

    


    @Override
    public Page<SizeDto> readAll(Integer page, Integer size){
        if (page == null){
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x->mapper.map(x,SizeDto.class));
    }

    @Override
    public SizeDto create(SizeDto sizeDto) throws Exception {
        checkValidation(sizeDto);
        Size size = mapper.map(sizeDto, Size.class);
        repository.save(size);
        return mapper.map(size , SizeDto.class);
    }


    @Override
    public SizeDto update(SizeDto sizeDto) throws Exception {
        checkValidation(sizeDto);
        if (sizeDto.getId() == null || sizeDto.getId() <= 0){
            throw new ValidationException("please enter id to update");
        }
        Size size = repository.findById(sizeDto.getId()).orElseThrow(NotFoundException::new);
        size.setTitle(Optional.ofNullable(sizeDto.getTitle()).orElse(size.getTitle()));

        size.setDescription(Optional.ofNullable(sizeDto.getDescription()).orElse(size.getDescription()));
        repository.save(size);
       return mapper.map(size,SizeDto.class);


    }



    @Override
    public void checkValidation(SizeDto dto) throws ValidationException {
        if (dto == null){
            throw new ValidationException("please fill content data");
        }
        if (dto.getTitle() == null || dto.getTitle().isEmpty()){
            throw new ValidationException("please enter title");
        }
        if (dto.getDescription() == null || dto.getDescription().isEmpty()){
            throw new ValidationException("please enter link");
        }
    }




}
