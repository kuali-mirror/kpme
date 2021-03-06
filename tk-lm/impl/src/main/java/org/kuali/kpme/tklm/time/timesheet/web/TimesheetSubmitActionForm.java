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
package org.kuali.kpme.tklm.time.timesheet.web;

import org.kuali.kpme.core.web.KPMEForm;

public class TimesheetSubmitActionForm extends KPMEForm {

    private String action;
    private String documentId;
    private String selectedDept;
    private String selectedPayCalendarGroup;
    private String selectedWorkArea;
    private String selectedPayPeriod;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

	public String getSelectedDept() {
		return selectedDept;
	}

	public void setSelectedDept(String selectedDept) {
		this.selectedDept = selectedDept;
	}

	public String getSelectedPayCalendarGroup() {
		return selectedPayCalendarGroup;
	}

	public void setSelectedPayCalendarGroup(String selectedPayCalendarGroup) {
		this.selectedPayCalendarGroup = selectedPayCalendarGroup;
	}

	public String getSelectedWorkArea() {
		return selectedWorkArea;
	}

	public void setSelectedWorkArea(String selectedWorkArea) {
		this.selectedWorkArea = selectedWorkArea;
	}

	public String getSelectedPayPeriod() {
		return selectedPayPeriod;
	}

	public void setSelectedPayPeriod(String selectedPayPeriod) {
		this.selectedPayPeriod = selectedPayPeriod;
	}
    
}
