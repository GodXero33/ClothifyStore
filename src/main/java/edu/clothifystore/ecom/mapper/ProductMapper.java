package edu.clothifystore.ecom.mapper;


import edu.clothifystore.ecom.dto.Product;
import edu.clothifystore.ecom.entity.ProductEntity;

public class ProductMapper implements Mapper<Product, ProductEntity> {
	@Override
	public Product toDTO (ProductEntity entity) {
		return null;
	}

	@Override
	public ProductEntity toEntity (Product dto) {
		return null;
	}
}
