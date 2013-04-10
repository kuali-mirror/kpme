package org.kuali.hr.pm.positiontype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.pm.positiontype.PositionType;
import org.kuali.hr.pm.positiontype.dao.PositionTypeDao;

public class PositionTypeServiceImpl implements PositionTypeService {

	private PositionTypeDao positionTypeDao;
	
	@Override
	public PositionType getPositionTypeById(
			String pmPositionTypeId) {
		return positionTypeDao.getPositionTypeById(pmPositionTypeId);
	}

	@Override
	public List<PositionType> getPositionTypeList(String positionType, String institution, String campus, LocalDate asOfDate) {
		return positionTypeDao.getPositionTypeList(positionType, institution, campus, asOfDate);
	}

	public PositionTypeDao getPositionTypeDao() {
		return positionTypeDao;
	}

	public void setPositionTypeDao(
			PositionTypeDao positionTypeDao) {
		this.positionTypeDao = positionTypeDao;
	}


}
