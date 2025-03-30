package com.example.app.controller.open;

import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.dto.*;
import com.example.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public APIResponse<LimitedUserDto> login(@RequestBody LoginDto dto) {
        try {
            return APIResponse.<LimitedUserDto>builder()
                    .data(userService.login(dto))
                    .status(APIStatus.Success)
                    .message("login")
                    .build();

        } catch (Exception e) {
            return APIResponse.<LimitedUserDto>builder()
                    .data(null)
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }
    }
}
