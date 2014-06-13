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
package org.kuali.kpme.core.position.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.position.PositionBase;
import org.kuali.kpme.core.api.position.service.PositionBaseService;
import org.kuali.kpme.core.position.PositionBaseBo;
import org.kuali.kpme.core.position.dao.PositionBaseDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class PositionBaseServiceImpl implements PositionBaseService {

	private PositionBaseDao positionBaseDao;
	
	protected List<PositionBase> convertToImmutable(List<PositionBaseBo> bos) {
		return ModelObjectUtils.transform(bos, PositionBaseBo.toImmutable);
	}
	
	@Override
	public PositionBase getPosition(String hrPositionId) {
		return PositionBaseBo.to(positionBaseDao.getPosition(hrPositionId));
	}

    @Override
    public PositionBase getPosition(String hrPositionNbr, LocalDate effectiveDate) {
        return PositionBaseBo.to(positionBaseDao.getPosition(hrPositionNbr, effectiveDate));
    }

    @Override
    public List<PositionBase> getPositions(String positionNum, String positionDescr, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
        return convertToImmutable(positionBaseDao.getPositions(positionNum, positionDescr, fromEffdt, toEffdt, active, showHistory));
    }

	public PositionBaseDao getPositionBaseDao() {
		return positionBaseDao;
	}

	public void setPositionBaseDao(PositionBaseDao positionBaseDao) {
		this.positionBaseDao = positionBaseDao;
	}


}