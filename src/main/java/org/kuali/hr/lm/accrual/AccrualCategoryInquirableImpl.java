package org.kuali.hr.lm.accrual;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.base.web.TkInquirableImpl;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.krad.bo.BusinessObject;

public class AccrualCategoryInquirableImpl extends TkInquirableImpl {
	
	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		AccrualCategory ac = null;
		if(StringUtils.isNotBlank((String)fieldValues.get("lmAccrualCategoryId"))) {
			ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory((String)fieldValues.get("lmAccrualCategoryId"));
			
		} else if(StringUtils.isNotBlank((String)fieldValues.get("accrualCategory"))
					&& StringUtils.isNotBlank((String)fieldValues.get("effectiveDate"))) {
			java.util.Date uDate = null;
			try {
				uDate = new SimpleDateFormat("MM/dd/yyyy").parse(fieldValues.get("effectiveDate").toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date effdt = new java.sql.Date(uDate.getTime());
			ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory((String)fieldValues.get("accrualCategory"), effdt);
			
		} else {
			ac = (AccrualCategory) super.getBusinessObject(fieldValues);
		}

		return ac;
	}
}
