package org.kuali.hr.job.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;

/**
 * Hooks in to Rice to over-ride the way we are saving our Business Objects.  We
 * treat our business objects as immutable, the default Rice behavior is to modify
 * existing rows in the database.
 */
public class JobMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void setJobNumber(Job job) {
		Long jobNumber = new Long("0");
		Job maxJob = TkServiceLocator.getJobSerivce().getMaxJob(job.getPrincipalId());
		
		if(maxJob != null) {
			// get the max of job number of the collection
			jobNumber = maxJob.getJobNumber() +1;
		}		
		job.setJobNumber(jobNumber);
	}
	/**
	 * Override to populate user information in Maintenance page
	 */
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
		if(StringUtils.equals(getMaintenanceAction(),"New")){
			if (!fieldValues.containsKey("jobNumber") || StringUtils.isEmpty(fieldValues.get("jobNumber"))) {
				if (fieldValues.containsKey("principalId") && StringUtils.isNotEmpty(fieldValues.get("principalId"))) {
					Job maxJob = TkServiceLocator.getJobSerivce().getMaxJob(fieldValues.get("principalId"));
					if(maxJob != null) {
						fieldValues.put("jobNumber", Long.toString(maxJob.getJobNumber() +1));
					} else {
						fieldValues.put("jobNumber", "0");
					}
				}
			} 
		}
		
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall); 
	}

	@Override
	public HrBusinessObject getObjectById(Long id) {
		return (HrBusinessObject)TkServiceLocator.getJobSerivce().getJob(id);
	}

	@Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		if(StringUtils.equals(getMaintenanceAction(),"New")){
			Job job = (Job)hrObj;
			this.setJobNumber(job);
		}
	}
	
	
}
