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
package org.kuali.kpme.pm.classification.web;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.pm.classification.Classification;
import org.kuali.kpme.pm.classification.duty.ClassificationDuty;
import org.kuali.kpme.pm.classification.flag.ClassificationFlag;
import org.kuali.kpme.pm.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.service.base.PmServiceLocator;


public class ClassificationMaintainableImpl extends HrBusinessObjectMaintainableImpl {
	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getClassificationService().getClassificationById(id);
	}
	@Override
	public void customSaveLogic(HrBusinessObject hrObj){
		Classification aClss = (Classification) hrObj;
		for(ClassificationQualification aQual : aClss.getQualificationList()) {
			aQual.setPmPositionClassId(aClss.getPmPositionClassId());
			aQual.setPmClassificationQualificationId(null);
		}
		for(ClassificationDuty aDuty : aClss.getDutyList()) {
			aDuty.setPmPositionClassId(aClss.getPmPositionClassId());
			aDuty.setPmDutyId(null);
		}
		for(ClassificationFlag aFlag : aClss.getFlagList()) {
			aFlag.setPmPositionClassId(aClss.getPmPositionClassId());
			aFlag.setPmFlagId(null);
		}
		
	}
	

}
