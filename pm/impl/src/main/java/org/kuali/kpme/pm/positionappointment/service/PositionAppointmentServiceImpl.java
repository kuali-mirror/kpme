/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.pm.positionappointment.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.positionappointment.PositionAppointment;
import org.kuali.kpme.pm.api.positionappointment.service.PositionAppointmentService;
import org.kuali.kpme.pm.positionappointment.PositionAppointmentBo;
import org.kuali.kpme.pm.positionappointment.dao.PositionAppointmentDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class PositionAppointmentServiceImpl  implements PositionAppointmentService {

	private PositionAppointmentDao positionAppointmentDao;
	

	public PositionAppointment getPositionAppointmentById(String pmPositionAppointmentId) {
		return PositionAppointmentBo.to(positionAppointmentDao.getPositionAppointmentById(pmPositionAppointmentId));
	}


	public List<PositionAppointment> getPositionAppointmentList(String positionAppointment, String description, String groupKeyCode, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
		return ModelObjectUtils.transform(positionAppointmentDao.getPositionAppointmentList(positionAppointment, description, groupKeyCode, fromEffdt, toEffdt, active, showHistory),PositionAppointmentBo.toImmutable);
	}
	
	public List<PositionAppointment> getPositionAppointmentList(String positionAppointment, String groupKeyCode, LocalDate asOfDate) {
		return ModelObjectUtils.transform(positionAppointmentDao.getPositionAppointmentList(positionAppointment, groupKeyCode, asOfDate), PositionAppointmentBo.toImmutable);
	}

	public PositionAppointmentDao getPositionAppointmentDao() {
		return positionAppointmentDao;
	}

	public void setPositionAppointmentDao(
			PositionAppointmentDao positionAppointmentDao) {
		this.positionAppointmentDao = positionAppointmentDao;
	}

}
