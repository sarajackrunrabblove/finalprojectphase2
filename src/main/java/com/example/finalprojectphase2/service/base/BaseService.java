package com.example.finalprojectphase2.service.base;

import java.util.List;

public interface BaseService<T> {
    List<T> findAll() throws Exception;

    T findById(Long pk) throws Exception;

    T save(T model) throws Exception;

    void update(T model) throws Exception;

    void delete(T model) throws Exception;
}
