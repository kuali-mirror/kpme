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
package org.kuali.kpme.tklm.time.batch;

import java.util.Date;

import org.kuali.kpme.tklm.common.BatchJobService;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.krad.bo.TransientBusinessObjectBase;
import org.quartz.JobDetail;

public class BatchJobStatus extends TransientBusinessObjectBase {

    // private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BatchJobStatus.class);

	private Date startDate;
	private Date endDate;
	private Date scheduledDate;
    private JobDetail jobDetail;
    private String hrCalendarEntryId;
    private final BatchJobService batchJobService = TkServiceLocator.getBatchJobService();

    // for DD purposes only
    public BatchJobStatus() {
    }

    public BatchJobStatus(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }

    public String getName() {
        return jobDetail.getName();
    }

    public String getGroup() {
        return jobDetail.getGroup();
    }

    public String getFullName() {
        return jobDetail.getGroup() + "." + jobDetail.getName();
    }

    public String getStatus() {
        if (isRunning()) {
            return BatchJobService.RUNNING_JOB_STATUS_CODE;
        }
        String tempStatus = batchJobService.getStatus(jobDetail);
        return tempStatus;
    }

    public boolean isRunning() {
        return batchJobService.isJobRunning(getName());
    }

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getHrCalendarEntryId() {
		return hrCalendarEntryId;
	}

	public void setHrCalendarEntryId(String hrCalendarEntryId) {
		this.hrCalendarEntryId = hrCalendarEntryId;
	}
}
