package org.kuali.hr.core;

import org.kuali.rice.krad.bo.TransientBusinessObjectBase;

public class KPMEAttributes extends TransientBusinessObjectBase {

	private static final long serialVersionUID = -4021553727571865228L;
	
	private String workArea;
	private String department;
	private String location;
	private String position;
	
	public String getWorkArea() {
		return workArea;
	}
	
	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}