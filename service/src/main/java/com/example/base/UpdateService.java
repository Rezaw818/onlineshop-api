package com.example.base;

import java.util.List;

public interface UpdateService<Dto> {

    Dto update(Dto dto) throws Exception;
}
