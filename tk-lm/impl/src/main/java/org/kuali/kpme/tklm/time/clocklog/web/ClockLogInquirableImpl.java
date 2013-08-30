package org.kuali.kpme.tklm.time.clocklog.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.inquirable.KPMEInquirableImpl;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class ClockLogInquirableImpl extends KualiInquirableImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -972713168985512037L;

	
	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		ClockLog clocklog = null;
		
		if(StringUtils.isNotBlank((String)fieldValues.get("tkClockLogId"))) {
		clocklog = TkServiceLocator.getClockLogService().getClockLog((String)fieldValues.get("tkClockLogId"));
		MissedPunch missedPunch = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId((String)fieldValues.get("tkClockLogId"));
		if (missedPunch != null) {
			clocklog.setClockedByMissedPunch(true);
        	}
		}
		

		return clocklog;
	}
	


}
