package edu.clothifystore.ecom.repository;

import java.util.List;

public interface CrudRepository<T> extends SuperRepository {
	boolean add (T entity);
	boolean update (T entity);
	boolean delete (Integer id);
	T get (Integer id);
	List<T> getAll ();
}
