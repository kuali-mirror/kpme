package org.kuali.hr.pm.positionreportgroup.service;

import java.sql.Date;

import org.kuali.hr.pm.positionreportgroup.PositionReportGroup;
import org.kuali.hr.pm.positionreportgroup.dao.PositionReportGroupDao;

public class PositionReportGroupServiceImpl implements PositionReportGroupService {

	private PositionReportGroupDao positionReportGroupDao;
	
	@Override
	public PositionReportGroup getPositionReportGroupById(
			String pmPositionReportGroupId) {
		return positionReportGroupDao.getPositionReportGroupById(pmPositionReportGroupId);
	}

	@Override
	public PositionReportGroup getPositionReportGroupByGroupAndDate(
			String positionReportGroup, Date asOfDate) {
		return positionReportGroupDao.getPositionReportGroupByGroupAndDate(positionReportGroup, asOfDate);
	}

	public PositionReportGroupDao getPositionReportGroupDao() {
		return positionReportGroupDao;
	}

	public void setPositionReportGroupDao(
			PositionReportGroupDao positionReportGroupDao) {
		this.positionReportGroupDao = positionReportGroupDao;
	}

}
