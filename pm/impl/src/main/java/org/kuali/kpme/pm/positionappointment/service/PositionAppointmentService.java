package org.kuali.kpme.pm.positionappointment.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionappointment.PositionAppointment;

public interface PositionAppointmentService {

	/**
	 * retrieve the PositionAppointment with given id
	 * 
	 * @param pmPositionAppointmentId
	 * @return
	 */
	public PositionAppointment getPositionAppointmentById(String pmPositionAppointmentId);

	/**
	 * Get list of PositionAppointment with given group, institution, campus and
	 * effective date wild card allowed
	 * 
	 * @param positionAppointment
	 * @param institution
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public List<PositionAppointment> getPositionAppointmentList(String positionAppointment, String institution, String campus, LocalDate asOfDate);
}
