package org.kuali.kpme.pm.positionflag.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionflag.PositionFlag;
import org.kuali.kpme.pm.positionflag.dao.PositionFlagDao;

public class PositionFlagServiceImpl implements PositionFlagService {

	private PositionFlagDao positionFlagDao;
	
	@Override
	public PositionFlag getPositionFlagById(String pmPositionFlagId) {
		return positionFlagDao.getPositionFlagById(pmPositionFlagId);
	}
	@Override
	public List<String> getAllActiveFlagCategories() {
		return positionFlagDao.getAllActiveFlagCategories();
	}
	@Override
	public List<PositionFlag> getAllActivePositionFlags(String category, String name, LocalDate effDate) {
		return positionFlagDao.getAllActivePositionFlags(category, name, effDate);
	}
	@Override
	public List<PositionFlag> getAllActivePositionFlagsWithCategory(String category, LocalDate effDate) {
		return positionFlagDao.getAllActivePositionFlagsWithCategory(category, effDate);
	}
	public PositionFlagDao getPositionFlagDao() {
		return positionFlagDao;
	}

	public void setPositionFlagDao(PositionFlagDao positionFlagDao) {
		this.positionFlagDao = positionFlagDao;
	}

}
