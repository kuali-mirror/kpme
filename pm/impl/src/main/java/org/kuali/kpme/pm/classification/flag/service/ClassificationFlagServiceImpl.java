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
package org.kuali.kpme.pm.classification.flag.service;

import java.util.List;

import org.kuali.kpme.pm.classification.flag.ClassificationFlag;
import org.kuali.kpme.pm.classification.flag.dao.ClassificationFlagDao;

public class ClassificationFlagServiceImpl implements ClassificationFlagService{
	
	private ClassificationFlagDao classificationFlagDao;
	@Override
	public List<ClassificationFlag> getFlagListForClassification(String pmClassificationId) {
		return classificationFlagDao.getFlagListForClassification(pmClassificationId);
	}
	public ClassificationFlagDao getClassificationFlagDao() {
		return classificationFlagDao;
	}
	public void setClassificationFlagDao(ClassificationFlagDao classificationFlagDao) {
		this.classificationFlagDao = classificationFlagDao;
	}

}
