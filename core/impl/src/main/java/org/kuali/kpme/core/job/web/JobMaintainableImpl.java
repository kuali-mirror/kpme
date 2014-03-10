/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.job.web;

import java.util.Map;

import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;

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

	@Override
	public void processAfterCopy(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		super.processAfterCopy(document, parameters);
		JobBo job = (JobBo) document.getNewMaintainableObject().getDataObject();
		job.setPrincipalId(null);
		job.setJobNumber(null);
	}	

	@Override
	public HrBusinessObject getObjectById(String id) {
		return JobBo.from(HrServiceLocator.getJobService().getJob(id));
	}

    //attribute query, populates name when principalID is selected
    public EntityName getName(String principalId) {
        return KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId).getDefaultName();
    }

    //attribute query, populates name and job number when principalID is selected, for new jobs
    public JobBo getNameAndJob(String principalId) {
        JobBo aJob = new JobBo();

        EntityNamePrincipalName p = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
        if (p != null && p.getDefaultName() != null) {
            aJob.setPrincipalName(p.getDefaultName().getCompositeName());
        }else{
            aJob.setPrincipalName("");
        }

        JobContract maxJob = HrServiceLocator.getJobService().getMaxJob(principalId);
        if(maxJob != null) {
            aJob.setJobNumber(maxJob.getJobNumber() +1);
        } else {
            aJob.setJobNumber(0L);
        }

        return aJob;
    }
	
}
