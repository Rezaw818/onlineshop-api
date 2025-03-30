package com.example.app.controller.panel;

import com.example.app.anotation.CheckPermission;
import com.example.app.controller.base.CRUDController;
import com.example.app.filter.JwtFilter;
import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;
import com.example.dto.ChangePassDto;
import com.example.dto.UpdateProfileDto;
import com.example.dto.UserDto;
import com.example.service.user.UserService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/panel/user")
public class UserPanelController implements CRUDController<UserDto> {

    private final UserService service;

    @Autowired
    public UserPanelController(UserService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @CheckPermission("info_user")
    public APIResponse<UserDto> getById(@PathVariable Long id, HttpServletRequest request){
        return APIResponse.<UserDto>builder()
                .status(APIStatus.Success)
                .data(service.getById(id))
                .build();
    }

    @Override
    @CheckPermission("add_user")
    public APIResponse<UserDto> add(UserDto userDto) {
        try{
            return APIResponse.<UserDto>builder()
                    .data(service.create(userDto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<UserDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }

    }

    @Override
    @CheckPermission("list_user")
    public APIPanelResponse<List<UserDto>> getAll(Integer page, Integer size) {
        Page<UserDto> data = service.readAll(page, size);
        return APIPanelResponse.<List<UserDto>>builder()
                .data(data.getContent())
                .totalCount(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .message("")
                .build();
    }

    @Override
    @CheckPermission("edit_user")
    public APIResponse<UserDto> edit(UserDto userDto) throws Exception {

        return APIResponse.<UserDto>builder()
                .status(APIStatus.Success)
                .data(service.update(userDto))
                .build();
    }

    @PutMapping("change_pass/admin")
    @CheckPermission("change_password_by_admin")
    public APIResponse<UserDto> changePasswordByAdmin(UserDto userDto) throws Exception {

        return APIResponse.<UserDto>builder()
                .status(APIStatus.Success)
                .data(service.changePasswordByAdmin(userDto))
                .build();
    }

    @PutMapping("change_pass")
    @CheckPermission("change_password_by_user")
    public APIResponse<UserDto> changePassword(ChangePassDto dto , HttpServletRequest request) throws Exception {
        UserDto user =(UserDto) request.getAttribute(JwtFilter.CURRENT_USER);
        return APIResponse.<UserDto>builder()
                .status(APIStatus.Success)
                .data(service.changePasswordByUser(dto, user))
                .build();
    }

    @PutMapping("update_profile")
    @CheckPermission("edit_my_user")
    public APIResponse<UserDto> editProfile(@RequestBody UpdateProfileDto dto , HttpServletRequest request) throws Exception {
        UserDto user =(UserDto) request.getAttribute(JwtFilter.CURRENT_USER);
        dto.setId(user.getId());
        return APIResponse.<UserDto>builder()
                .status(APIStatus.Success)
                .data(service.updateProfile(dto))
                .build();
    }

    @Override
    @CheckPermission("delete_user")
    public APIResponse<Boolean> delete(Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .data(service.delete(id))
                .status(APIStatus.Success)
                .build();
    }


}
