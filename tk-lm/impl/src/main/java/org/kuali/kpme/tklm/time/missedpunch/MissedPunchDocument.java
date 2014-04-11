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
package org.kuali.kpme.tklm.time.missedpunch;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.kpme.tklm.api.time.missedpunch.MissedPunchDocumentContract;
import org.kuali.rice.krad.document.TransactionalDocumentBase;

public class MissedPunchDocument extends TransactionalDocumentBase implements MissedPunchDocumentContract {

	private static final long serialVersionUID = -8759488155644037099L;
	
	private String tkMissedPunchId;
	
	private MissedPunchBo missedPunch = new MissedPunchBo();

    private transient HrGroupKeyBo groupKey;
    private transient JobBo jobObj;
    private transient WorkAreaBo workAreaObj;
    private transient DepartmentBo departmentObj;
    private transient TaskBo taskObj;
	public String getTkMissedPunchId() {
		return tkMissedPunchId;
	}

	public void setTkMissedPunchId(String tkMissedPunchId) {
		this.tkMissedPunchId = tkMissedPunchId;
	}

	public MissedPunchBo getMissedPunch() {
		return missedPunch;
	}

	public void setMissedPunch(MissedPunchBo missedPunch) {
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

    public String getGroupKeyCode() {
        return  missedPunch.getGroupKeyCode();
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

    public HrGroupKeyBo getGroupKey() {
        return groupKey;
    }
    public TaskBo getTaskObj() {
        return taskObj;
    }

    public JobBo getJobObj() {
        return jobObj;
    }

    public WorkAreaBo getWorkAreaObj() {
        return workAreaObj;
    }

    public DepartmentBo getDepartmentObj() {
        return departmentObj;
    }
    
    @Override
    public List<Assignment> getAssignments() {
    	List<Assignment> assignments = new ArrayList<Assignment>();
    	if( (getMissedPunch() != null) && (getMissedPunch().getActionFullDateTime() != null) ){
    		assignments =  HrServiceLocator.getAssignmentService().getAssignments(getPrincipalId(), 
    													getMissedPunch().getActionFullDateTime().toLocalDate());
    	}
        return assignments; 
    }
}
