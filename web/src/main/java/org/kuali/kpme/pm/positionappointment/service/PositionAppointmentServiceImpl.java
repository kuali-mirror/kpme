package org.kuali.kpme.pm.positionappointment.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionappointment.PositionAppointment;
import org.kuali.kpme.pm.positionappointment.dao.PositionAppointmentDao;

public class PositionAppointmentServiceImpl  implements PositionAppointmentService {

	private PositionAppointmentDao positionAppointmentDao;
	

	public PositionAppointment getPositionAppointmentById(String pmPositionAppointmentId) {
		return positionAppointmentDao.getPositionAppointmentById(pmPositionAppointmentId);
	}


	public List<PositionAppointment> getPositionAppointmentList(String positionAppointment, String institution, String campus, LocalDate asOfDate) {
		return positionAppointmentDao.getPositionAppointmentList(positionAppointment, institution, campus, asOfDate);
	}

	public PositionAppointmentDao getPositionAppointmentDao() {
		return positionAppointmentDao;
	}

	public void setPositionAppointmentDao(
			PositionAppointmentDao positionAppointmentDao) {
		this.positionAppointmentDao = positionAppointmentDao;
	}

}
