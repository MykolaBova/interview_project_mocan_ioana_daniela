package com.ioana.google.places.dao.api;

public interface GenericDao<T> {
	
	T create(T t);
    T read(Integer id);
    T update(T t);
    void delete(T t);

}
