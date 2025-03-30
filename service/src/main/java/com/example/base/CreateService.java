package com.example.base;

public interface CreateService<Dto> {
    Dto create(Dto dto) throws Exception;
}
