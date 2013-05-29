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
package org.kuali.kpme.tklm.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.web.KPMEForm;

public abstract class ApprovalForm extends KPMEForm {

	private static final long serialVersionUID = -8943916755511889801L;
	
	private String selectedPayCalendarGroup;
    private String selectedDept;
    private String selectedWorkArea;
  
    private List<String> payCalendarGroups = new LinkedList<String>();
    private List<String> departments = new ArrayList<String>();
    private Map<Long,String> workAreaDescr = new HashMap<Long,String>();
    
	/**
     * Used for ajax dynamic row updating
     */
    private String outputString;

    public String getSelectedPayCalendarGroup() {
        return selectedPayCalendarGroup;
    }

    public void setSelectedPayCalendarGroup(String selectedPayCalendarGroup) {
        this.selectedPayCalendarGroup = selectedPayCalendarGroup;
    }

    public String getSelectedDept() {
        return selectedDept;
    }

    public void setSelectedDept(String selectedDept) {
        this.selectedDept = selectedDept;
    }
    
    public String getSelectedWorkArea() {
        return selectedWorkArea;
    }

    public void setSelectedWorkArea(String selectedWorkArea) {
        this.selectedWorkArea = selectedWorkArea;
    }
    
    public List<String> getPayCalendarGroups() {
        return payCalendarGroups;
    }

    public void setPayCalendarGroups(List<String> payCalendarGroups) {
        this.payCalendarGroups = payCalendarGroups;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

	public Map<Long,String> getWorkAreaDescr() {
		return workAreaDescr;
	}

	public void setWorkAreaDescr(Map<Long,String> workAreaDescr) {
		this.workAreaDescr = workAreaDescr;
	}
	
    public String getOutputString() {
        return outputString;
    }

    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }

}