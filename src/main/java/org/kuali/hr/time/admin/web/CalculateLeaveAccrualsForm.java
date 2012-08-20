package org.kuali.hr.time.admin.web;

import org.kuali.hr.time.base.web.TkForm;

public class CalculateLeaveAccrualsForm extends TkForm {

	private static final long serialVersionUID = -841015862054021534L;
	
	private String principalName;
    private String startDate;
	private String endDate;
	
    public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	
    public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}