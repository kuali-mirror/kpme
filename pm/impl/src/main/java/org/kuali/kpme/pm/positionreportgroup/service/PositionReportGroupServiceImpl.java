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
package org.kuali.kpme.pm.positionreportgroup.service;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.positionreportgroup.PositionReportGroup;
import org.kuali.kpme.pm.api.positionreportgroup.service.PositionReportGroupService;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroupBo;
import org.kuali.kpme.pm.positionreportgroup.dao.PositionReportGroupDao;

public class PositionReportGroupServiceImpl implements PositionReportGroupService {

	private PositionReportGroupDao positionReportGroupDao;
	
	@Override
	public PositionReportGroup getPositionReportGroupById(String pmPositionReportGroupId) {
		return PositionReportGroupBo.to(positionReportGroupDao.getPositionReportGroupById(pmPositionReportGroupId));
	}
	
	@Override
	public PositionReportGroup getPositionReportGroup(String positionReportGroup, LocalDate asOfDate) {
		return PositionReportGroupBo.to(positionReportGroupDao.getPositionReportGroup(positionReportGroup, asOfDate));
	}
	
	public PositionReportGroupDao getPositionReportGroupDao() {
		return positionReportGroupDao;
	}

	public void setPositionReportGroupDao(PositionReportGroupDao positionReportGroupDao) {
		this.positionReportGroupDao = positionReportGroupDao;
	}

	

}
