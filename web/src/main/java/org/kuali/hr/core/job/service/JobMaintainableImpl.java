/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.core.job.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.core.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.core.job.Job;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
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
		Job maxJob = HrServiceLocator.getJobService().getMaxJob(job.getPrincipalId());
		
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
			EntityNamePrincipalName p = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(fieldValues.get("principalId"));
			if (p != null && p.getDefaultName() != null) {
				fieldValues.put("name", p.getDefaultName().getCompositeName());
			}else{
				fieldValues.put("name", "");
			}
		}
		if(StringUtils.equals(getMaintenanceAction(),"New")){
			if (!fieldValues.containsKey("jobNumber") || StringUtils.isEmpty(fieldValues.get("jobNumber"))) {
				if (fieldValues.containsKey("principalId") && StringUtils.isNotEmpty(fieldValues.get("principalId"))) {
					Job maxJob = HrServiceLocator.getJobService().getMaxJob(fieldValues.get("principalId"));
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
	public HrBusinessObject getObjectById(String id) {
		return (HrBusinessObject)HrServiceLocator.getJobService().getJob(id);
	}

	@Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		if(StringUtils.equals(getMaintenanceAction(),"New")){
			Job job = (Job)hrObj;
			this.setJobNumber(job);
		}
	}
	
	
}
