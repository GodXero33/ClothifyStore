package edu.clothifystore.ecom.repository;

import java.util.List;

public interface CrudRepository<T, I> extends SuperRepository {
	boolean add (T entity);
	boolean update (T entity);
	boolean delete (I id);
	T get (I id);
	List<T> getAll ();
}
