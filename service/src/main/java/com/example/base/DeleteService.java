package com.example.base;

import com.example.common.exceptions.NotFoundException;

public interface DeleteService<Dto> {

    Boolean delete(Long id) throws NotFoundException;
}
