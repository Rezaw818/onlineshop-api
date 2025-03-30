package com.example.app.controller.open;


import com.example.common.exceptions.NotFoundException;
import com.example.dto.file.FileDto;
import com.example.service.file.FileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/file")
public class FileController {


    @Value("${app.file.upload.path}")
    private String uploadPath;

    private final FileService service;

    @Autowired
    public FileController(FileService service) {
        this.service = service;
    }

    @SneakyThrows
    @GetMapping("{name}")
    public ResponseEntity<InputStreamResource> getFileByName(@PathVariable String name){
        try{
            FileDto fileDto = service.readByName(name);
            File file = new File(uploadPath + File.separator + fileDto.getPath());
            if (!file.exists()){
                 throw new NotFoundException();
            }
            InputStream inputStream = new FileInputStream(file);
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(fileDto.getContentType()));
            headers.setContentLength(fileDto.getSize());
            return new ResponseEntity<>(inputStreamResource , headers, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}
