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
package org.kuali.kpme.core.job.web;


import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class JobInquirableImpl extends KualiInquirableImpl {

    @Override
    @SuppressWarnings("rawtypes")
    public BusinessObject getBusinessObject(Map fieldValues) {
        JobContract jobObj = null;

        if (StringUtils.isNotBlank((String) fieldValues.get("hrJobId"))) {
            jobObj = HrServiceLocator.getJobService().getJob((String) fieldValues.get("hrJobId"));
        } else if (fieldValues.containsKey("jobNumber") && fieldValues.containsKey("effectiveDate") && fieldValues.containsKey("principalId")) {
        	String jobNumberVal = (String) fieldValues.get("jobNumber");
        	Long jobNumber = jobNumberVal != null ? Long.valueOf(jobNumberVal) : null;
            String principalId = (String) fieldValues.get("principalId");
            String effDate = (String) fieldValues.get("effectiveDate");
            LocalDate effectiveDate = StringUtils.isBlank(effDate) ? LocalDate.now() : TKUtils.formatDateString(effDate);
            jobObj = HrServiceLocator.getJobService().getJob(principalId, jobNumber, effectiveDate);
        } else {
            jobObj = (Job) super.getBusinessObject(fieldValues);
        }

        return jobObj;
    }
}
