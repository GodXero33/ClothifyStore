package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.ReportEntity;
import edu.clothifystore.ecom.repository.custom.ReportRepository;

import java.util.List;

public class ReportRepositoryImpl implements ReportRepository {
	@Override
	public boolean add (ReportEntity entity) {
		return false;
	}

	@Override
	public boolean update (ReportEntity entity) {
		return false;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public ReportEntity get (Integer id) {
		return null;
	}

	@Override
	public List<ReportEntity> getAll () {
		return List.of();
	}
}
