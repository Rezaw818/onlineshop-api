package com.example.service.site;

import com.example.base.CRUDService;
import com.example.base.HasValidation;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataaccess.entity.site.Nav;
import com.example.dataaccess.entity.site.Slider;
import com.example.dataaccess.entity.site.Slider;
import com.example.dataaccess.repository.site.SliderRepository;
import com.example.dto.site.NavDto;
import com.example.dto.site.SliderDto;
import com.example.dto.site.SliderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SliderService implements CRUDService<SliderDto>, HasValidation<SliderDto> {

    private final SliderRepository repository;

    private final ModelMapper mapper;

    @Autowired
    public SliderService(SliderRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SliderDto> readAll() {
        return repository.findAllByEnableIsTrueOrderByOrderNumberAsc().stream().map(x -> mapper.map(x, SliderDto.class)).toList();
    }

    @Override
    public Page<SliderDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x -> mapper.map(x, SliderDto.class));
    }

    @Override
    public SliderDto create(SliderDto sliderDto) throws Exception {
        checkValidation(sliderDto);
        Slider slider = mapper.map(sliderDto, Slider.class);
        slider.setEnable(true);
        Integer lastOrderNumber = repository.findLastOrderNumber();
        if (lastOrderNumber == null) {
            lastOrderNumber = 0;
        }
        slider.setOrderNumber(++lastOrderNumber);
        repository.save(slider);
        return mapper.map(slider, SliderDto.class);
    }

    @Override
    public Boolean delete(Long id) throws NotFoundException {
        repository.deleteById(id);
        return true;
    }

    @Override
    public SliderDto update(SliderDto sliderDto) throws Exception {
        checkValidation(sliderDto);
        if (sliderDto.getId() == null || sliderDto.getId() <= 0) {
            throw new ValidationException("please enter id to update");
        }
        Slider oldSlider = repository.findById(sliderDto.getId()).orElseThrow(NotFoundException::new);
        oldSlider.setOrderNumber(Optional.ofNullable(sliderDto.getOrderNumber()).orElse(oldSlider.getOrderNumber()));
        oldSlider.setLink(Optional.ofNullable(sliderDto.getLink()).orElse(oldSlider.getLink()));
        oldSlider.setTitle(Optional.ofNullable(sliderDto.getTitle()).orElse(oldSlider.getTitle()));
        repository.save(oldSlider);
        return mapper.map(oldSlider, SliderDto.class);
    }

    @Transactional
    public boolean swapUp(Long id) throws NotFoundException {
        Slider currentSlider = repository.findById(id).orElseThrow(NotFoundException::new);
        Optional<Slider> previous = repository.findFirstByOrderNumberLessThanOrderByOrderNumberDesc(currentSlider.getOrderNumber());
        if (previous.isPresent()) {
            Integer tempOrderNumber = currentSlider.getOrderNumber();
            currentSlider.setOrderNumber(previous.get().getOrderNumber());
            previous.get().setOrderNumber(tempOrderNumber);
            repository.save(currentSlider);
            repository.save(previous.get());
            return true;
        }

        return false;
    }

    @Transactional
    public boolean swapDown(Long id) throws NotFoundException {
        Slider currntSlider = repository.findById(id).orElseThrow(NotFoundException::new);
        Optional<Slider> next = repository.findFirstByOrderNumberGreaterThanOrderByOrderNumberDesc(currntSlider.getOrderNumber());
        if (next.isPresent()) {
            Integer tempOrderNumber = currntSlider.getOrderNumber();
            currntSlider.setOrderNumber(next.get().getOrderNumber());
            next.get().setOrderNumber(tempOrderNumber);
            repository.save(currntSlider);
            repository.save(next.get());
            return true;
        }
        return false;
    }

    @Override
    public void checkValidation(SliderDto dto) throws ValidationException {
        if (dto == null) {
            throw new ValidationException("please fill content data");
        }
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new ValidationException("please enter title");
        }
        if (dto.getLink() == null || dto.getLink().isEmpty()) {
            throw new ValidationException("please enter link");
        }
    }
}

