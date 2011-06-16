package org.kuali.hr.time.warning;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public class TkWarningServiceImpl implements TkWarningService{
	/**
	 * This is used for perpetual warnings that need to stick to the timesheet
	 */
	@Override
	public List<String> getWarnings(String documentNumber) {
        List<String> jsonWarnings = new ArrayList<String>();
		TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentNumber);
        JSONArray warnMsgJson = new JSONArray();
		//Validate accrual hours
        warnMsgJson = TkServiceLocator.getTimeOffAccrualService().validateAccrualHoursLimit(td);
        if (!warnMsgJson.isEmpty()) {
        	jsonWarnings.add(warnMsgJson.toJSONString());
        }
        
        return jsonWarnings;
	}

}
