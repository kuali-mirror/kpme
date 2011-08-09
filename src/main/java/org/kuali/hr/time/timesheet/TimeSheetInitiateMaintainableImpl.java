package org.kuali.hr.time.timesheet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

public class TimeSheetInitiateMaintainableImpl extends KualiMaintainableImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void saveBusinessObject() {
		super.saveBusinessObject();
	}
	
	
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if(StringUtils.equals(getMaintenanceAction(),"New")){
			if (!fieldValues.containsKey("documentId") || StringUtils.isEmpty(fieldValues.get("documentId"))) {
				TimeSheetInitiate tsi = (TimeSheetInitiate) this.getBusinessObject();
				fieldValues.put("documentId", tsi.getDocumentId());
			}
		}
		if (fieldValues.containsKey("endPeriodDateTime") && !StringUtils.isEmpty(fieldValues.get("endPeriodDateTime"))) {
			try {
				if(fieldValues.get("endPeriodDateTime").length() > 10) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
					java.util.Date aDate = dateFormat.parse(fieldValues.get("endPeriodDateTime"));
					dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					fieldValues.put("endPeriodDateTime", dateFormat.format(aDate));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument, methodToCall);
	}
		
}
