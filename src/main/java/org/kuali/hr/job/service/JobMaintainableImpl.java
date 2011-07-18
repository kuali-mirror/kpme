package org.kuali.hr.job.service;

import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentAccount;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
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
		Job job = (Job) this.getBusinessObject();
		
		//Inactivate the old assignment as of the effective date of new assignment
		if(job.getHrJobId()!=null && job.isActive()){
			Job oldJob = TkServiceLocator.getJobSerivce().getJob(job.getHrJobId());
			if(job.getEffectiveDate().equals(oldJob.getEffectiveDate())){
				job.setTimestamp(null);
			} else{
				if(oldJob!=null){
					oldJob.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldJob.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldJob.setEffectiveDate(job.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldJob);
				}
				job.setTimestamp(new Timestamp(System.currentTimeMillis()));
				job.setHrJobId(null);
			}
		}
		
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
