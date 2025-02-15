package edu.clothifystore.ecom.service.custom.impl;

import edu.clothifystore.ecom.dto.Product;
import edu.clothifystore.ecom.entity.ProductEntity;
import edu.clothifystore.ecom.mapper.Mapper;
import edu.clothifystore.ecom.mapper.MapperFactory;
import edu.clothifystore.ecom.repository.RepositoryFactory;
import edu.clothifystore.ecom.repository.custom.ProductRepository;
import edu.clothifystore.ecom.service.custom.ProductService;
import edu.clothifystore.ecom.util.MapperType;
import edu.clothifystore.ecom.util.RepositoryType;
import edu.clothifystore.ecom.util.UtilFactory;

public class ProductServiceImpl implements ProductService {
	private final Mapper<Product, ProductEntity> mapper;
	private final ProductRepository productRepository;

	public ProductServiceImpl () {
		this.mapper = UtilFactory.getObject(MapperFactory.class).getMapperType(MapperType.PRODUCT);
		this.productRepository = UtilFactory.getObject(RepositoryFactory.class).getRepositoryType(RepositoryType.PRODUCT);
	}

	@Override
	public int addAndGetID (Product product) {
		return this.productRepository.addAndGetID(this.mapper.toEntity(product));
	}
}
