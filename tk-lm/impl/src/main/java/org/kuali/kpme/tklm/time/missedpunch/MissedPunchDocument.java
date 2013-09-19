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
package org.kuali.kpme.tklm.time.missedpunch;

import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.task.Task;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.tklm.api.time.missedpunch.MissedPunchDocumentContract;
import org.kuali.rice.krad.document.TransactionalDocumentBase;

import java.sql.Timestamp;
import java.util.Date;

public class MissedPunchDocument extends TransactionalDocumentBase implements MissedPunchDocumentContract {

	private static final long serialVersionUID = -8759488155644037099L;
	
	private String tkMissedPunchId;
	
	private MissedPunch missedPunch = new MissedPunch();

    private transient Job jobObj;
    private transient WorkArea workAreaObj;
    private transient Department departmentObj;
    private transient Task taskObj;
	public String getTkMissedPunchId() {
		return tkMissedPunchId;
	}

	public void setTkMissedPunchId(String tkMissedPunchId) {
		this.tkMissedPunchId = tkMissedPunchId;
	}

	public MissedPunch getMissedPunch() {
		return missedPunch;
	}

	public void setMissedPunch(MissedPunch missedPunch) {
		this.missedPunch = missedPunch;
	}


    //helper methods!!!!
    public String getPrincipalId() {
        return missedPunch.getPrincipalId();
    }

    public String getTimesheetDocumentId() {
        return missedPunch.getTimesheetDocumentId();
    }

    public Long getJobNumber() {
        return missedPunch.getJobNumber();
    }

    public Long getWorkArea() {
        return missedPunch.getWorkArea();
    }

    public Long getTask() {
        return missedPunch.getTask();
    }

    public Date getActionDateTime() {
        return null;
    }

    public String getClockAction() {
        return missedPunch.getClockAction();
    }

    public String getTkClockLogId() {
        return missedPunch.getTkClockLogId();
    }

    public Timestamp getTimestamp() {
        return missedPunch.getTimestamp();
    }

    public String getDepartment() {
        return missedPunch.getDepartment();
    }

    /*public Date getRelativeEffectiveDate() {
        return missedPunch.getRelativeEffectiveDate();
    }*/

    public Task getTaskObj() {
        return taskObj;
    }

    public Job getJobObj() {
        return jobObj;
    }

    public WorkArea getWorkAreaObj() {
        return workAreaObj;
    }

    public Department getDepartmentObj() {
        return departmentObj;
    }
}
