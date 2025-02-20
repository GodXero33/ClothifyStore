package edu.clothifystore.ecom.service.custom.impl;

import edu.clothifystore.ecom.dto.Supplier;
import edu.clothifystore.ecom.entity.SupplierEntity;
import edu.clothifystore.ecom.mapper.Mapper;
import edu.clothifystore.ecom.mapper.MapperFactory;
import edu.clothifystore.ecom.repository.RepositoryFactory;
import edu.clothifystore.ecom.repository.custom.SupplierRepository;
import edu.clothifystore.ecom.service.custom.SupplierService;
import edu.clothifystore.ecom.util.MapperType;
import edu.clothifystore.ecom.util.RepositoryType;
import edu.clothifystore.ecom.util.UtilFactory;

public class SupplierServiceImpl implements SupplierService {
	private final Mapper<Supplier, SupplierEntity> mapper;
	private final SupplierRepository supplierRepository;

	public SupplierServiceImpl () {
		this.mapper = UtilFactory.getObject(MapperFactory.class).getMapperType(MapperType.SUPPLIER);
		this.supplierRepository = UtilFactory.getObject(RepositoryFactory.class).getRepositoryType(RepositoryType.SUPPLIER);
	}

	@Override
	public int getCount () {
		return this.supplierRepository.getCount();
	}

	@Override
	public boolean add (Supplier supplier) {
		return this.supplierRepository.add(this.mapper.toEntity(supplier));
	}

	@Override
	public boolean update (Supplier supplier) {
		return this.supplierRepository.update(this.mapper.toEntity(supplier));
	}

	@Override
	public boolean delete (Integer id) {
		return this.supplierRepository.delete(id);
	}

	@Override
	public Supplier getByName (String name) {
		final SupplierEntity supplierEntity = this.supplierRepository.getByName(name);

		if (supplierEntity == null) return null;

		return this.mapper.toDTO(supplierEntity);
	}

	@Override
	public Supplier getByPhone (String phone) {
		final SupplierEntity supplierEntity = this.supplierRepository.getByPhone(phone);

		if (supplierEntity == null) return null;

		return this.mapper.toDTO(supplierEntity);
	}
}
