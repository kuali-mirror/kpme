package org.kuali.hr.time.principal.calendar.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class PrincipalCalendarMaintainableImpl extends KualiMaintainableImpl {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("principalId")
				&& StringUtils.isNotEmpty(fieldValues.get("principalId"))) {
			Person p = KIMServiceLocator.getPersonService().getPerson(
					fieldValues.get("principalId"));
			if (p != null) {
				fieldValues.put("name", p.getName());
			}else{
				fieldValues.put("name", "");
			}
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}

	@Override
	public void saveBusinessObject() {
		PrincipalCalendar prinCal = (PrincipalCalendar) this.getBusinessObject();
		prinCal.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(prinCal);
	}
	
	
}
