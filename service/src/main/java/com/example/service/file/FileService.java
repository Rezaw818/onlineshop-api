package com.example.service.file;

import com.example.base.CreateService;
import com.example.base.DeleteService;
import com.example.base.HasValidation;
import com.example.base.ReadService;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataaccess.entity.file.File;

import com.example.dataaccess.repository.file.FileRepository;
import com.example.dto.file.FileDto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService implements ReadService<FileDto>,
        DeleteService<FileDto>
{

    @Value("${app.file.upload.path}")
    private String uploadPath;

    private final FileRepository repository;

    private final ModelMapper mapper;

    @Autowired
    public FileService(FileRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<FileDto> readAll(Integer page, Integer size){
        if (page == null){
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x->mapper.map(x,FileDto.class));
    }

    @Override
    public Boolean delete(Long id) throws NotFoundException {
        repository.deleteById(id);
        return true;
    }


    public FileDto upload(MultipartFile file) throws Exception {
        if (file == null){
            throw new ValidationException("please select file to upload");
        }
        String head = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") +1);
        String filename = head + "." + extension;
        File entity = File.builder()
                .createDate(LocalDateTime.now())
                .extension(extension)
                .uuid(UUID.randomUUID().toString())
                .path(filename)
                .size(file.getSize())
                .contentType(file.getContentType())
                .build();

        String filePath = uploadPath + java.io.File.separator + filename;
        Path savePath = Paths.get(filePath);
        java.nio.file.Files.write(savePath, file.getBytes());

        File saveFile = repository.save(entity);
        return mapper.map(saveFile, FileDto.class);
    }

    public FileDto readByName(String name) throws NotFoundException {
        File file = repository.findFirstByNameEqualsIgnoreCase(name).orElseThrow(NotFoundException::new);
        return mapper.map(file, FileDto.class);
    }
}
