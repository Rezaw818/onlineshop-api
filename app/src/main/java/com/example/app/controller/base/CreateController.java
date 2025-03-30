package com.example.app.controller.base;

import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CreateController<Dto> {

    @PostMapping("add")
    APIResponse<Dto> add(@RequestBody Dto dto);

}
