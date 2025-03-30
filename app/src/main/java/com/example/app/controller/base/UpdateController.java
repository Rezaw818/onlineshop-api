package com.example.app.controller.base;

import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UpdateController<Dto> {

    @PutMapping("edit")
    APIResponse<Dto> edit(@RequestBody Dto dto) throws Exception;

}
