/**
 * Copyright 2004-2013 The Kuali Foundation
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
