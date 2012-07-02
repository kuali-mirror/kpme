package org.kuali.hr.lm.leaveplan;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class LeavePlanInquirableImpl extends KualiInquirableImpl {
	
	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		
		LeavePlan lp = null;
		if(StringUtils.isNotBlank((String)fieldValues.get("lmLeavePlanId"))) {
			lp = TkServiceLocator.getLeavePlanService().getLeavePlan((String)fieldValues.get("lmLeavePlanId"));
			
		} else if(StringUtils.isNotBlank((String)fieldValues.get("leavePlan"))
					&& StringUtils.isNotBlank((String)fieldValues.get("effectiveDate"))) {
			java.util.Date uDate = null;
			try {
				uDate = new SimpleDateFormat("MM/dd/yyyy").parse(fieldValues.get("effectiveDate").toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date acEffdt = new java.sql.Date(uDate.getTime());
			lp = TkServiceLocator.getLeavePlanService().getLeavePlan((String)fieldValues.get("leavePlan"), acEffdt);
			
		} else {
			lp = (LeavePlan) super.getBusinessObject(fieldValues);
		}

		return lp;
	}

}
