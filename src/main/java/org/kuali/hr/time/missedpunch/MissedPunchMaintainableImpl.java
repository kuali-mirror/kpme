package org.kuali.hr.time.missedpunch;

import java.sql.Time;
import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

public class MissedPunchMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = -1505817190754176279L;
	
	@Override
	public PersistableBusinessObject getBusinessObject() {
		MissedPunch missedPunch = (MissedPunch) super.getBusinessObject();
		if(missedPunch.getActionDate() != null) {
			missedPunch.setActionTime(new Time(missedPunch.getActionDate().getTime()));
		}
		return super.getBusinessObject();
	}
	
	@Override
	public void saveBusinessObject() {
		
		MissedPunch missedPunch = (MissedPunch)super.getBusinessObject();
		java.util.Date actionDate = missedPunch.getActionDate();
		java.sql.Time actionTime = missedPunch.getActionTime();
		
		LocalTime actionTimeLocal = new LocalTime(actionTime.getTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
		DateTime actionDateTime = new DateTime(actionDate.getTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
		actionDateTime = actionDateTime.plus(actionTimeLocal.getMillisOfDay());
		missedPunch.setActionDate(new java.util.Date(actionDateTime.getMillis()));
		
		missedPunch.setDocumentId(this.documentNumber);
		missedPunch.setTimestamp(new Timestamp(System.currentTimeMillis()));
		
		super.saveBusinessObject();
	}
}
