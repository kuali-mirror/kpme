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

public abstract class CalendarApprovalForm extends CalendarForm {
	
	private static final long serialVersionUID = -173408280988754540L;
	
	public static final String ORDER_BY_PRINCIPAL = "principalName";
    public static final String ORDER_BY_DOCID = "documentId";
    public static final String ORDER_BY_STATUS = "Status";
    public static final String ORDER_BY_WORKAREA = "WorkArea";

    private List<String> payCalendarGroups = new LinkedList<String>();
    private String selectedPayCalendarGroup;
    private String selectedDept;
    private String selectedWorkArea;

    private Map<Long,String> workAreaDescr = new HashMap<Long,String>();

    private String outputString;

    private String searchField;
    private String searchTerm;

    private List<String> departments = new ArrayList<String>();
    private Integer resultSize = 0;

    public String getOutputString() {
        return outputString;
    }

    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<String> getPayCalendarGroups() {
        return payCalendarGroups;
    }

    public void setPayCalendarGroups(List<String> payCalendarGroups) {
        this.payCalendarGroups = payCalendarGroups;
    }

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

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public int getResultSize() {
        return resultSize;
    }

    public void setResultSize(Integer resultSize) {
        this.resultSize = resultSize;
    }

    public String getSelectedWorkArea() {
        return selectedWorkArea;
    }

    public void setSelectedWorkArea(String selectedWorkArea) {
        this.selectedWorkArea = selectedWorkArea;
    }

	public Map<Long,String> getWorkAreaDescr() {
		return workAreaDescr;
	}

	public void setWorkAreaDescr(Map<Long,String> workAreaDescr) {
		this.workAreaDescr = workAreaDescr;
	}

}