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
package org.kuali.hr.core.web;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.core.HrConstants;

public class BatchJobActionForm extends HrForm {

	private static final long serialVersionUID = -8603015460600023900L;

	private static final List<String> BATCH_JOB_NAMES = new ArrayList<String>();

    private String selectedBatchJob;
    private String hrPyCalendarEntryId;
    private String leavePlan;
    
    static {
    	BATCH_JOB_NAMES.add(HrConstants.BATCH_JOB_NAMES.INITIATE);
    	BATCH_JOB_NAMES.add(HrConstants.BATCH_JOB_NAMES.END_PAY_PERIOD);
    	BATCH_JOB_NAMES.add(HrConstants.BATCH_JOB_NAMES.END_REPORTING_PERIOD);
    	BATCH_JOB_NAMES.add(HrConstants.BATCH_JOB_NAMES.EMPLOYEE_APPROVAL);
    	BATCH_JOB_NAMES.add(HrConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL);
    	BATCH_JOB_NAMES.add(HrConstants.BATCH_JOB_NAMES.MISSED_PUNCH_APPROVAL);
    }

    public List<String> getBatchJobNames() {
        return BATCH_JOB_NAMES;
    }
    
    public String getSelectedBatchJob() {
        return selectedBatchJob;
    }

    public void setSelectedBatchJob(String selectedBatchJob) {
        this.selectedBatchJob = selectedBatchJob;
    }

    public String getHrPyCalendarEntryId() {
        return hrPyCalendarEntryId;
    }

    public void setHrPyCalendarEntryId(String hrPyCalendarEntryId) {
        this.hrPyCalendarEntryId = hrPyCalendarEntryId;
    }

	public String getLeavePlan() {
		return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}
    
    

}