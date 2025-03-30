package com.example.base;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ReadService<Dto> {

    Page<Dto> readAll(Integer page, Integer size);
}
