package com.example.app.controller.base;

import com.example.app.model.APIPanelResponse;
import com.example.app.model.APIResponse;
import com.example.common.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DeleteController<Dto> {

    @DeleteMapping("{id}")
    APIResponse<Boolean> delete(@PathVariable Long id) throws NotFoundException;

}
