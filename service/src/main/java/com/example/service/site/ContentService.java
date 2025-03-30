package com.example.service.site;

import com.example.base.*;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataaccess.entity.site.Content;
import com.example.dataaccess.entity.site.Content;
import com.example.dataaccess.repository.site.ContentRepository;
import com.example.dto.site.ContentDto;
import com.example.dto.site.ContentDto;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentService implements CreateService<ContentDto>,
        UpdateService<ContentDto>,
        ReadService<ContentDto>
        , HasValidation<ContentDto> {

    private final ContentRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public ContentService(ContentRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ContentDto> readAll() {
        return repository.findAll().stream().map(x -> mapper.map(x, ContentDto.class)).toList();
    }

    @SneakyThrows
    public ContentDto readByKey(String key) {
        Content content = repository.findFirstByKeyNameEqualsIgnoreCase(key).orElseThrow(NotFoundException::new);
        return mapper.map(content, ContentDto.class);
    }

    @Override
    public Page<ContentDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x -> mapper.map(x, ContentDto.class));
    }

    @Override
    public ContentDto create(ContentDto contentDto) throws Exception {
        checkValidation(contentDto);
        Content content = mapper.map(contentDto, Content.class);
        repository.save(content);
        return mapper.map(content, ContentDto.class);
    }


    @Override
    public ContentDto update(ContentDto contentDto) throws Exception {
        checkValidation(contentDto);
        if (contentDto.getId() == null || contentDto.getId() <= 0) {
            throw new ValidationException("please enter id to update");
        }
        Content oldContent = repository.findById(contentDto.getId()).orElseThrow(NotFoundException::new);
        oldContent.setValueContent(Optional.ofNullable(contentDto.getValueContent()).orElse(oldContent.getValueContent()));
        oldContent.setKeyName(Optional.ofNullable(contentDto.getKeyName()).orElse(oldContent.getKeyName()));
        repository.save(oldContent);
        return mapper.map(oldContent, ContentDto.class);


    }

    @Override
    public void checkValidation(ContentDto contentDto) throws ValidationException {
        if (contentDto == null) {
            throw new ValidationException("please fill content data");
        }
        if (contentDto.getValueContent() == null || contentDto.getValueContent().isEmpty()) {
            throw new ValidationException("please enter title");
        }
        if (contentDto.getKeyName() == null || contentDto.getKeyName().isEmpty()) {
            throw new ValidationException("please enter link");
        }
    }
}
