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
package org.kuali.kpme.pm.position.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.kpme.pm.api.position.service.PositionService;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.position.dao.PositionDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class PositionServiceImpl implements PositionService {

	private PositionDao positionDao;
	
	protected List<Position> convertToImmutable(List<PositionBo> bos) {
					return ModelObjectUtils.transform(bos, PositionBo.toImmutable);
	}
	
	@Override
	public Position getPosition(String id) {
			return PositionBo.to(positionDao.getPosition(id));
	}
	
	@Override
	public List<Position> getPositions(String positionNum, String description, String groupKeyCode, String classificationTitle, String positionType,
            String poolEligible, String positionStatus, LocalDate fromEffdt, LocalDate toEffdt, String active,
			String showHistory) {
		return convertToImmutable(positionDao.getPositions(positionNum, description, groupKeyCode, classificationTitle, positionType, poolEligible, positionStatus, fromEffdt, toEffdt, active, showHistory));
	}
	
	public PositionDao getPositionDao() {
		return positionDao;
	}
	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}
	
}
