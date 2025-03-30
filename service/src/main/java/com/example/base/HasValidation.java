package com.example.base;

import com.example.common.exceptions.ValidationException;

public interface HasValidation<Dto> {

    void checkValidation(Dto dto) throws ValidationException;
}
