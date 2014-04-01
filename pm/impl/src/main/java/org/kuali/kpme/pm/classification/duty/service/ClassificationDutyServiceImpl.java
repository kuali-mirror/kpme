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
package org.kuali.kpme.pm.classification.duty.service;

import java.util.List;

import org.kuali.kpme.pm.api.classification.duty.service.ClassificationDutyService;
import org.kuali.kpme.pm.classification.duty.ClassificationDutyBo;
import org.kuali.kpme.pm.classification.duty.dao.ClassificationDutyDao;

public class ClassificationDutyServiceImpl implements ClassificationDutyService {

	private ClassificationDutyDao classificationDutyDao;
	
	@Override
	public List<ClassificationDutyBo> getDutyListForClassification(String pmClassificationId) {
		return classificationDutyDao.getDutyListForClassification(pmClassificationId);
	}

	public ClassificationDutyDao getClassificationDutyDao() {
		return classificationDutyDao;
	}

	public void setClassificationDutyDao(ClassificationDutyDao classificationDutyDao) {
		this.classificationDutyDao = classificationDutyDao;
	}
}
