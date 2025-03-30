package com.example.service.site;

import com.example.base.CRUDService;
import com.example.base.HasValidation;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataaccess.entity.site.Nav;
import com.example.dataaccess.repository.site.NavRepository;
import com.example.dto.site.NavDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NavService implements CRUDService<NavDto>, HasValidation<NavDto> {

    private final NavRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public NavService(NavRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public List<NavDto> readAll(){
        return repository.findAllByEnableIsTrueOrderByOrderNumberAsc().stream().map(x->mapper.map(x,NavDto.class)).toList();
    }

    @Override
    public Page<NavDto> readAll(Integer page, Integer size){
        if (page == null){
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x->mapper.map(x,NavDto.class));
    }

    @Override
    public NavDto create(NavDto navDto) throws Exception {
        checkValidation(navDto);
        Nav nav = mapper.map(navDto,Nav.class);
        nav.setEnable(true);
        Integer lastOrderNumber = repository.findLastOrderNumber();
        if (lastOrderNumber == null){
            lastOrderNumber = 0;
        }
        nav.setOrderNumber(++lastOrderNumber);
        repository.save(nav);
        return mapper.map(nav , NavDto.class);
    }

    @Override
    public Boolean delete(Long id) throws NotFoundException {
        repository.deleteById(id);
        return true;
    }

    @Override
    public NavDto update(NavDto navDto) throws Exception {
        checkValidation(navDto);
        if (navDto.getId() == null || navDto.getId() <= 0){
            throw new ValidationException("please enter id to update");
        }
        Nav oldNav = repository.findById(navDto.getId()).orElseThrow(NotFoundException::new);
        oldNav.setOrderNumber(Optional.ofNullable(navDto.getOrderNumber()).orElse(oldNav.getOrderNumber()));
        oldNav.setLink(Optional.ofNullable(navDto.getLink()).orElse(oldNav.getLink()));
        oldNav.setTitle(Optional.ofNullable(navDto.getTitle()).orElse(oldNav.getTitle()));
        repository.save(oldNav);
        return mapper.map(oldNav,NavDto.class);


    }

    @Override
    public void checkValidation(NavDto navDto) throws ValidationException {
        if (navDto == null){
            throw new ValidationException("please fill nav data");
        }
        if (navDto.getTitle() == null || navDto.getTitle().isEmpty()){
            throw new ValidationException("please enter title");
        }
        if (navDto.getLink() == null || navDto.getLink().isEmpty()){
            throw new ValidationException("please enter link");
        }
    }


    @Transactional
    public boolean swapUp(Long id) throws NotFoundException {
        Nav currentNav = repository.findById(id).orElseThrow(NotFoundException::new);
        Optional<Nav>  previous= repository.findFirstByOrderNumberLessThanOrderByOrderNumberDesc(currentNav.getOrderNumber());
        if (previous.isPresent()){
            Integer tempOrderNumber = currentNav.getOrderNumber();
            currentNav.setOrderNumber(previous.get().getOrderNumber());
            previous.get().setOrderNumber(tempOrderNumber);
            repository.save(currentNav);
            repository.save(previous.get());
            return true;
        }

        return false;

    }


    @Transactional
    public boolean swapDown(Long id) throws NotFoundException {
        Nav currentNav = repository.findById(id).orElseThrow(NotFoundException::new);
        Optional<Nav>  next= repository.findFirstByOrderNumberGreaterThanOrderByOrderNumberDesc(currentNav.getOrderNumber());
        if (next.isPresent()){
            Integer tempOrderNumber = currentNav.getOrderNumber();
            currentNav.setOrderNumber(next.get().getOrderNumber());
            next.get().setOrderNumber(tempOrderNumber);
            repository.save(currentNav);
            repository.save(next.get());
            return true;
        }

        return false;

    }


}
