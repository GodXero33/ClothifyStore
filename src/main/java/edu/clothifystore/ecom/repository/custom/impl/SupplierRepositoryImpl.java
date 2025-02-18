package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.SupplierEntity;
import edu.clothifystore.ecom.repository.custom.SupplierRepository;
import edu.clothifystore.ecom.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierRepositoryImpl implements SupplierRepository {
	@Override
	public boolean add (SupplierEntity entity) {
		return false;
	}

	@Override
	public boolean update (SupplierEntity entity) {
		return false;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public SupplierEntity get (Integer id) {
		return null;
	}

	@Override
	public List<SupplierEntity> getAll () {
		return List.of();
	}

	@Override
	public int getCount () {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) FROM supplier");

			if (resultSet.next()) return resultSet.getInt(1);

			return -1;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return -1;
		}
	}
}
