package org.kuali.hr.job.service;

import org.kuali.hr.job.Job;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class JobMaintainableImpl extends KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void saveBusinessObject() {
		Job job = (Job)this.getBusinessObject();
		Job oldJob = (Job)KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
													Job.class, job.getHrJobId());
		if(oldJob!=null){
			oldJob.setActive(false);
			KNSServiceLocator.getBusinessObjectService().save(oldJob);
		}
		job.setHrJobId(null);
		job.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(job);
	}

}
