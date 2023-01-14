package com.example.challengequintoimpacto.services;

import com.example.challengequintoimpacto.exceptions.BaseException;

import java.util.List;

public interface BaseService<E> {
    List<E> getAll() throws BaseException;
    E getOne(Long id) throws BaseException;
    E saveOne(E entity) throws BaseException;
    E updateOne(E entity,Long id) throws BaseException;
    Boolean deleteOne(Long id) throws BaseException;
}
