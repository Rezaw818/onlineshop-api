package com.example.service.product;

import com.example.base.CreateService;
import com.example.base.HasValidation;
import com.example.base.ReadService;
import com.example.base.UpdateService;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataaccess.entity.product.Color;
import com.example.dataaccess.repository.product.ColorRepository;
import com.example.dataaccess.repository.product.SizeRepository;
import com.example.dto.product.ColorDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColorService implements CreateService<ColorDto>
         , UpdateService<ColorDto>,
        ReadService<ColorDto>
        , HasValidation<ColorDto> {

    private final ColorRepository repository;
    private final ModelMapper mapper;
    private final PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer;

    @Autowired
    public ColorService(ColorRepository repository, ModelMapper mapper, PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer) {
        this.repository = repository;
        this.mapper = mapper;
        this.pageableCustomizer = pageableCustomizer;
    }

    


    @Override
    public Page<ColorDto> readAll(Integer page, Integer size){
        if (page == null){
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x->mapper.map(x,ColorDto.class));
    }

    @Override
    public ColorDto create(ColorDto colorDto) throws Exception {
        checkValidation(colorDto);
        Color color = mapper.map(colorDto, Color.class);
        repository.save(color);
        return mapper.map(color , ColorDto.class);
    }


    @Override
    public ColorDto update(ColorDto colorDto) throws Exception {
        checkValidation(colorDto);
        if (colorDto.getId() == null || colorDto.getId() <= 0){
            throw new ValidationException("please enter id to update");
        }
        Color color = repository.findById(colorDto.getId()).orElseThrow(NotFoundException::new);
        color.setHex(Optional.ofNullable(colorDto.getHex()).orElse(color.getHex()));
        color.setName(Optional.ofNullable(colorDto.getName()).orElse(color.getName()));
        repository.save(color);
       return mapper.map(color,ColorDto.class);


    }



    @Override
    public void checkValidation(ColorDto dto) throws ValidationException {
        if (dto == null){
            throw new ValidationException("please fill content data");
        }
        if (dto.getHex() == null || dto.getHex().isEmpty()){
            throw new ValidationException("please enter hex");
        }
        if (dto.getName() == null || dto.getName().isEmpty()){
            throw new ValidationException("please enter name");
        }
    }




}
