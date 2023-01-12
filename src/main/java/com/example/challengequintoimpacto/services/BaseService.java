package com.example.challengequintoimpacto.services;

import java.util.List;

public interface BaseService<E> {
    List<E> getAll() throws Exception;
    E getOne(Long id) throws Exception;
    E saveOne(E entity) throws Exception;
    E updateOne(E entity,Long id) throws Exception;
    Boolean deleteOneSoft(Long id) throws Exception;
}
