package com.example.service.site;

import com.example.base.CRUDService;
import com.example.base.HasValidation;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataaccess.entity.site.Blog;
import com.example.dataaccess.entity.site.Content;
import com.example.dataaccess.entity.site.Slider;
import com.example.dataaccess.enums.BlogStatus;
import com.example.dataaccess.repository.site.BlogRepository;
import com.example.dto.site.BlogDto;
import com.example.dto.site.ContentDto;
import com.example.dto.site.SingleBlogDto;
import com.example.dto.site.SliderDto;
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
public class BlogService implements CRUDService<BlogDto> , HasValidation<BlogDto> {

    private final BlogRepository repository;
    private final ModelMapper mapper;
    private final PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer;

    @Autowired
    public BlogService(BlogRepository repository, ModelMapper mapper, PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer) {
        this.repository = repository;
        this.mapper = mapper;
        this.pageableCustomizer = pageableCustomizer;
    }

    public List<BlogDto> readAllPublished(Integer page, Integer size){
        if (page == null){
            page=0;
        }
        if (size == null){
            size = 16;
        }
        return repository.findAllPublished(Pageable.ofSize(size).withPage(page)).stream().map(x->mapper.map(x, BlogDto.class)).toList();
    }

    @SneakyThrows
    public SingleBlogDto readById(Long id){
        Blog blog = repository.findById(id).orElseThrow(NotFoundException::new);
        return mapper.map(blog,SingleBlogDto.class);
    }


    @Override
    public Page<BlogDto> readAll(Integer page, Integer size){
        if (page == null){
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x->mapper.map(x,BlogDto.class));
    }

    @Override
    public BlogDto create(BlogDto blogDto) throws Exception {
        checkValidation(blogDto);
        Blog blog = mapper.map(blogDto, Blog.class);
       blog.setPublishDate(LocalDateTime.now());
        blog.setStatus(BlogStatus.Published);
        blog.setVisitCount(0l);
        repository.save(blog);
        return mapper.map(blog , BlogDto.class);
    }

    @Override
    public Boolean delete(Long id) throws NotFoundException {
        repository.deleteById(id);
        return true;
    }



    @Override
    public BlogDto update(BlogDto blogDto) throws Exception {
        checkValidation(blogDto);
        if (blogDto.getId() == null || blogDto.getId() <= 0){
            throw new ValidationException("please enter id to update");
        }
        Blog blog = repository.findById(blogDto.getId()).orElseThrow(NotFoundException::new);
        blog.setTitle(Optional.ofNullable(blogDto.getTitle()).orElse(blog.getTitle()));
        blog.setSubtitle(Optional.ofNullable(blogDto.getSubtitle()).orElse(blog.getSubtitle()));
        blog.setDescription(Optional.ofNullable(blogDto.getDescription()).orElse(blog.getDescription()));
        blog.setStatus(Optional.ofNullable(blogDto.getStatus()).orElse(blog.getStatus()));
        blog.setPublishDate(Optional.ofNullable(blogDto.getPublishDate()).orElse(blog.getPublishDate()));
        repository.save(blog);
       return mapper.map(blog,BlogDto.class);


    }



    @Override
    public void checkValidation(BlogDto dto) throws ValidationException {
        if (dto == null){
            throw new ValidationException("please fill content data");
        }
        if (dto.getTitle() == null || dto.getTitle().isEmpty()){
            throw new ValidationException("please enter title");
        }
        if (dto.getSubtitle() == null || dto.getSubtitle().isEmpty()){
            throw new ValidationException("please enter link");
        }
    }




}
