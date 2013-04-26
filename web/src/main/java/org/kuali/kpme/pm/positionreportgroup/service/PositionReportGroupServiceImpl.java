package org.kuali.kpme.pm.positionreportgroup.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroup;
import org.kuali.kpme.pm.positionreportgroup.dao.PositionReportGroupDao;

public class PositionReportGroupServiceImpl implements PositionReportGroupService {

	private PositionReportGroupDao positionReportGroupDao;
	
	@Override
	public PositionReportGroup getPositionReportGroupById(
			String pmPositionReportGroupId) {
		return positionReportGroupDao.getPositionReportGroupById(pmPositionReportGroupId);
	}

	@Override
	public List<PositionReportGroup> getPositionReportGroupList(String positionReportGroup, String institution, String campus, LocalDate asOfDate) {
		return positionReportGroupDao.getPositionReportGroupList(positionReportGroup, institution, campus, asOfDate);
	}

	public PositionReportGroupDao getPositionReportGroupDao() {
		return positionReportGroupDao;
	}

	public void setPositionReportGroupDao(
			PositionReportGroupDao positionReportGroupDao) {
		this.positionReportGroupDao = positionReportGroupDao;
	}

}
