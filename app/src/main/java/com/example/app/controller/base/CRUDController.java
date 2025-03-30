package com.example.app.controller.base;

public interface CRUDController<Dto> extends
        ReadController<Dto>,
        UpdateController<Dto>,
        CreateController<Dto>,
        DeleteController<Dto>{

}
