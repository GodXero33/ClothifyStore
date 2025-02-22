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

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
	private final Mapper<Product, ProductEntity> mapper;
	private final ProductRepository productRepository;

	public ProductServiceImpl () {
		this.mapper = UtilFactory.getObject(MapperFactory.class).getMapperType(MapperType.PRODUCT);
		this.productRepository = UtilFactory.getObject(RepositoryFactory.class).getRepositoryType(RepositoryType.PRODUCT);
	}

	@Override
	public boolean add (Product product) {
		return this.productRepository.add(this.mapper.toEntity(product));
	}

	@Override
	public int addAndGetID (Product product) {
		return this.productRepository.addAndGetID(this.mapper.toEntity(product));
	}

	@Override
	public boolean update (Product product) {
		return this.productRepository.update(this.mapper.toEntity(product));
	}

	@Override
	public Product get (int id) {
		final ProductEntity productEntity = this.productRepository.get(id);

		if (productEntity == null) return null;

		return this.mapper.toDTO(productEntity);
	}

	@Override
	public boolean delete (int id) {
		return this.productRepository.delete(id);
	}

	@Override
	public int getCount () {
		return this.productRepository.getCount();
	}

	@Override
	public List<Product> getAll () {
		final List<ProductEntity> productEntities = this.productRepository.getAll();
		final List<Product> products = new ArrayList<>();

		productEntities.forEach(productEntity -> products.add(this.mapper.toDTO(productEntity)));

		return products;
	}
}
