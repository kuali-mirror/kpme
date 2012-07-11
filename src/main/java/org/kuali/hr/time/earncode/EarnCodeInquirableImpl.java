package org.kuali.hr.time.earncode;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;

public class EarnCodeInquirableImpl extends KualiInquirableImpl {
	
	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		EarnCode ec = null;
		if(StringUtils.isNotBlank((String)fieldValues.get("hrEarnCodeId"))) {
			ec = TkServiceLocator.getEarnCodeService().getEarnCodeById((String)fieldValues.get("hrEarnCodeId"));
			
		} else if(StringUtils.isNotBlank((String)fieldValues.get("earnCode"))
					&& StringUtils.isNotBlank((String)fieldValues.get("effectiveDate"))) {
			java.util.Date uDate = null;
			try {
				uDate = new SimpleDateFormat("MM/dd/yyyy").parse(fieldValues.get("effectiveDate").toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date effdt = new java.sql.Date(uDate.getTime());
			ec = TkServiceLocator.getEarnCodeService().getEarnCode((String)fieldValues.get("earnCode"), effdt);
			
		} else {
			ec = (EarnCode) super.getBusinessObject(fieldValues);
		}

		return ec;
	}

}
