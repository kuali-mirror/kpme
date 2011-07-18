package org.kuali.hr.paygrade.service;

import java.sql.Timestamp;

import org.kuali.hr.paygrade.PayGrade;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class PayGradeMaintainableServiceImpl extends KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void saveBusinessObject() {
		PayGrade payGrade = (PayGrade) this.getBusinessObject();
		
		//Inactivate the old assignment as of the effective date of new assignment
		if(payGrade.getHrPayGradeId()!=null && payGrade.isActive()){
			PayGrade oldPayGrade = TkServiceLocator.getPayGradeService().getPayGrade(payGrade.getHrPayGradeId());
			if(payGrade.getEffectiveDate().equals(oldPayGrade.getEffectiveDate())){
				payGrade.setTimestamp(null);
			} else{
				if(oldPayGrade!=null){
					oldPayGrade.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldPayGrade.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldPayGrade.setEffectiveDate(payGrade.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldPayGrade);
				}
				payGrade.setTimestamp(new Timestamp(System.currentTimeMillis()));
				payGrade.setHrPayGradeId(null);
			}
		}
		
		KNSServiceLocator.getBusinessObjectService().save(payGrade);

	}
}
