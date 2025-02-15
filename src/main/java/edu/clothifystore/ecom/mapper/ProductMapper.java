package edu.clothifystore.ecom.mapper;


import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.dto.Product;
import edu.clothifystore.ecom.entity.ProductEntity;
import edu.clothifystore.ecom.util.UtilFactory;
import org.modelmapper.ModelMapper;

public class ProductMapper implements Mapper<Product, ProductEntity> {
	@Override
	public Product toDTO (ProductEntity entity) {
		return UtilFactory.getObject(ModelMapper.class).map(entity, Product.class);
	}

	@Override
	public ProductEntity toEntity (Product dto) {
		return UtilFactory.getObject(ModelMapper.class).map(dto, ProductEntity.class);
	}
}
