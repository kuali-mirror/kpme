package org.kuali.hr.core.role;

public enum KPMERoleMemberAttribute {
	
	WORK_AREA ("workArea"),
	DEPARTMENT ("department"),
	LOCATION ("location"),
	POSITION ("position");
	
	private String roleMemberAttributeName;
	
	private KPMERoleMemberAttribute(String roleMemberAttributeName) {
		this.roleMemberAttributeName = roleMemberAttributeName;
	}

	public String getRoleMemberAttributeName() {
		return roleMemberAttributeName;
	}

	public void setRoleMemberAttributeName(String roleMemberAttributeName) {
		this.roleMemberAttributeName = roleMemberAttributeName;
	}

}