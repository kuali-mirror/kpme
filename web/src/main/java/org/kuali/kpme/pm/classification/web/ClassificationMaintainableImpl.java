package org.kuali.kpme.pm.classification.web;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.pm.classification.Classification;
import org.kuali.kpme.pm.classification.ClassificationDuty;
import org.kuali.kpme.pm.classification.ClassificationFlag;
import org.kuali.kpme.pm.classification.ClassificationQualification;
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
