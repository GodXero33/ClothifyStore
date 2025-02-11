package edu.clothifystore.ecom.mapper;

public interface Mapper<D, E> {
	D toDTO (E entity);
	E toEntity (D dto);
}
