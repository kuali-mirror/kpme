package org.kuali.hr.job.service;

import org.kuali.hr.job.Job;
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
	
	@Override
	public void saveBusinessObject() {
		Job job = (Job)this.getBusinessObject();
		job.setHrJobId(null);
		job.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(job);
	}

}
