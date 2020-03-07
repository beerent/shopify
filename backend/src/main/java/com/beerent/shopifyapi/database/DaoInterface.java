package com.beerent.shopifyapi.database;

import java.io.Serializable;
import java.util.List;

public interface DaoInterface<T, Id extends Serializable> {
    public void persist(T entity);

    public void update(T entity);

    public T findById(Id id);

    public List<T> findAll();

    public void delete(T entity);

    public void deleteAll();
}