package com.example.app.aspect;

import com.example.app.model.APIPanelResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import com.example.app.anotation.CheckPermission;
import com.example.app.filter.JwtFilter;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.dto.PermissionDto;
import com.example.dto.UserDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class PermissionAspect {
    private final HttpServletRequest request;


    @Autowired
    public PermissionAspect(HttpServletRequest request) {
        this.request = request;
    }

    @SneakyThrows
    @Around("@annotation(checkPermission)")
    public Object checkUserPermission(ProceedingJoinPoint joinPoint, CheckPermission checkPermission) {
        UserDto user = (UserDto) request.getAttribute(JwtFilter.CURRENT_USER);
        if (user == null) {
            return APIResponse.builder()
                    .message("Please Login First!")
                    .status(APIStatus.Forbidden)
                    .build();
        }
        List<String> permissions = user.getRoles().stream().flatMap(x -> x.getPermissions().stream().map(PermissionDto::getName)).toList();
        if (!permissions.contains(checkPermission.value())) {
            return APIResponse.builder()
                    .status(APIStatus.Forbidden)
                    .message("Access Denied!")
                    .build();
        }
        return joinPoint.proceed();
    }
}
