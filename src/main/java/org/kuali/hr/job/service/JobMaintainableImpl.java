package org.kuali.hr.job.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

/**
 * Hooks in to Rice to over-ride the way we are saving our Business Objects.  We
 * treat our business objects as immutable, the default Rice behavior is to modify
 * existing rows in the database.
 */
public class JobMaintainableImpl extends KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Used to preserve state of Job over time
	 */
	@Override
	public void saveBusinessObject() {
		Job job = (Job)this.getBusinessObject();
		job.setHrJobId(null);
		job.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(job);
	}
	/**
	 * Override to populate user information in Maintenance page
	 */
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
}
