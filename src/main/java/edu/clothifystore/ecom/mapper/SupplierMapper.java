package edu.clothifystore.ecom.mapper;

import edu.clothifystore.ecom.dto.Supplier;
import edu.clothifystore.ecom.entity.SupplierEntity;
import edu.clothifystore.ecom.util.UtilFactory;
import org.modelmapper.ModelMapper;

public class SupplierMapper implements Mapper<Supplier, SupplierEntity> {
	@Override
	public Supplier toDTO (SupplierEntity entity) {
		return UtilFactory.getObject(ModelMapper.class).map(entity, Supplier.class);
	}

	@Override
	public SupplierEntity toEntity (Supplier dto) {
		return UtilFactory.getObject(ModelMapper.class).map(dto, SupplierEntity.class);
	}
}
