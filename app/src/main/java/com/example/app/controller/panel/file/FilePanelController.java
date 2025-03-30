package com.example.app.controller.panel.file;

import com.example.app.anotation.CheckPermission;

import com.example.app.controller.base.DeleteController;
import com.example.app.controller.base.ReadController;
import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;
import com.example.dto.file.FileDto;
import com.example.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/panel/file")
public class FilePanelController implements ReadController<FileDto>,
        DeleteController<FileDto>
{

    private final FileService service;

    @Autowired
    public FilePanelController(FileService service) {
        this.service = service;
    }



    @PostMapping("upload")
    @CheckPermission("add_file")
    public APIResponse<FileDto> upload(@RequestParam MultipartFile file) {

        try{
            return APIResponse.<FileDto>builder()
                    .data(service.upload(file))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<FileDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }

    }

    @Override
    @CheckPermission("list_file")
    public APIPanelResponse<List<FileDto>> getAll(Integer page, Integer size) {
        Page<FileDto> data = service.readAll(page, size);
        return APIPanelResponse.<List<FileDto>>builder()
                .data(data.getContent())
                .totalCount(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .message("")
                .build();
    }



    @Override
    @CheckPermission("delete_file")
    public APIResponse<Boolean> delete(Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .data(service.delete(id))
                .status(APIStatus.Success)
                .build();
    }
}
