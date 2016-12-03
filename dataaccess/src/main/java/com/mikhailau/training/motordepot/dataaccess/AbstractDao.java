package com.mikhailau.training.motordepot.dataaccess;

import java.util.List;

public interface AbstractDao<T, ID> {
	
	List<T> getAll();

	T get(final ID id);

	T insert(final T entity);

	T update(T entity);

	void delete(final ID id);
}
